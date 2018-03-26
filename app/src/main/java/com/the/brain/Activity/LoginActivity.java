package com.the.brain.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.the.brain.AppController;
import com.the.brain.R;
import com.the.brain.Utils.Constans;
import com.the.brain.Utils.GlobalElements;
import com.the.brain.Utils.Utils;
import com.the.brain.Utils.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edt_uname, edt_pwd;
    private Button btn_login;
    private TextView txt_forgot;
    TelephonyManager telephonyManager;
    private Spinner language_spin;
    String language;
    private String IMEI, Model_no, Device_brand, OS_version, SDK_version;
    private Button chagelan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitUI();

    }

    private void InitUI() {
        edt_uname = (EditText) findViewById(R.id.edt_uname);
        chagelan_btn = (Button) findViewById(R.id.chagelan_btn);
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        language_spin = (Spinner) findViewById(R.id.language_spin);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        chagelan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changelanguage(language_spin.getSelectedItemPosition());
            }
        });
        btn_login.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                IMEI = telephonyManager.getDeviceId();
                Model_no = android.os.Build.MODEL;
                Device_brand = android.os.Build.BRAND;
                OS_version = android.os.Build.VERSION.RELEASE;
                SDK_version = String.valueOf(android.os.Build.VERSION.SDK_INT);

                if (IMEI == null && IMEI.equals("")) {
                    Toast.makeText(this, "Try Again Can't Find Data", Toast.LENGTH_SHORT).show();
                }
                else if(!Validation.isValid(Validation.BLANK_CHECK,edt_uname.getText().toString())){
                    edt_uname.setError("Mobile Number is Required");
                }
                else if(!Validation.isValid(Validation.MOBILE,edt_uname.getText().toString())){
                    edt_uname.setError("Mobile Number is not valid");
                }
                else if(!Validation.isValid(Validation.BLANK_CHECK,edt_pwd.getText().toString())){
                    edt_pwd.setError("Password Required");
                }
                else {
                    if (GlobalElements.isConnectingToInternet(LoginActivity.this)) {
                        Load_DashBoard();
                    } else {
                        GlobalElements.showDialog(LoginActivity.this);
                    }
                }
                break;
            case R.id.txt_forgot:
                Open_ForgotPopUP();
                break;
        }

    }

    private void Open_ForgotPopUP() {
        Toast.makeText(this, "You need to Contact Your Supervisor", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        InitUI();
    }

    public void changelanguage(int pos) {
        try {
            if (pos == 0) {
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                onConfigurationChanged(config);

            } else if (pos == 1) {
                String languageToLoad = "hi"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                onConfigurationChanged(config);
            } else if (pos == 2) {
                String languageToLoad = "gu"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                onConfigurationChanged(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Load_DashBoard() {
        if (edt_uname.getText().toString() != null && edt_pwd.getText().toString() != null) {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            Call<ResponseBody> call = AppController.getInstance().getApiInterface().postLogin(edt_uname.getText().toString(), edt_pwd.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    String resposeString;
                    if (response.isSuccessful()) {
                        try {
                            resposeString = response.body().string();
                            JSONObject jsonObject = new JSONObject(resposeString);
                            if (jsonObject.getBoolean("status")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                Utils.getSharedPreference(LoginActivity.this).edit().putBoolean(Constans.PREFERENCE_IS_LOGGED_IN, false).apply();

                                Utils.getSharedPreference(LoginActivity.this).edit().putString(Constans.PREFERENCE_USER_ID, jsonObject1.getString("id")).apply();
                                Utils.getSharedPreference(LoginActivity.this).edit().putString(Constans.PREFERENCE_COMPANY_NAME, jsonObject1.getString("company_name")).apply();
                                Utils.getSharedPreference(LoginActivity.this).edit().putString(Constans.PREFERENCE_EMAIL, jsonObject1.getString("email")).apply();
                                Utils.getSharedPreference(LoginActivity.this).edit().putString(Constans.PREFERENCE_COUNTRY_CODE, jsonObject1.getString("country")).apply();
                                Utils.getSharedPreference(LoginActivity.this).edit().putString(Constans.PREFERENCE_STATE_CODE, jsonObject1.getString("state")).apply();
                                Toast.makeText(LoginActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent start_DashBoard = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(start_DashBoard);
                                finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        } else {
            edt_uname.setError("Please Enter Somethig");
            edt_pwd.setError("PLease Enter Password");
        }
    }
}
