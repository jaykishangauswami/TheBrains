package com.the.brain.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.the.brain.Adapter.AccountAdapter;
import com.the.brain.Adapter.ChatMessageAdapter;
import com.the.brain.Adapter.InventoryAdapter;
import com.the.brain.AppController;
import com.the.brain.Model.ChatMessage;
import com.the.brain.Model.ModelAccountChild;
import com.the.brain.Model.ModelAccounts;
import com.the.brain.Model.ModelInventory;
import com.the.brain.Model.ModelInventoryChild;
import com.the.brain.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AccountAdapter ExpAdapter;
    InventoryAdapter inventoryAdapter;
    private ArrayList<ModelAccounts> ExpListItems;
    ArrayList<ModelInventory>Inventorylist;
    private ExpandableListView accountlist;
    private ExpandableListView elv_Inventory;
    private LinearLayout linear_chat;
    private RecyclerView mRecyclerView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    ArrayList<Image> images = new ArrayList<>();
    ArrayList<File>imagefiles=new ArrayList<>();
    private ChatMessageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitUi();

        //todo account
        ExpListItems = SetRecharge();
        ExpAdapter = new AccountAdapter(Dashboard.this, ExpListItems);
        accountlist.setAdapter(ExpAdapter);
        accountlist.setGroupIndicator(null);
        accountlist.setChildIndicator(null);
        accountlist.setChildDivider(getResources().getDrawable(R.color.white));
        accountlist.setDivider(getResources().getDrawable(R.color.white));
        accountlist.setDividerHeight(0);
        accountlist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;
                for (int i = 0; i < accountlist.getChildCount(); i++) {
                    height += accountlist.getChildAt(i).getMeasuredHeight();
                    height += accountlist.getDividerHeight();
                }
                accountlist.getLayoutParams().height = (height+6)*7;
            }
        });

        // Listview Group collapsed listener
        accountlist.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                accountlist.getLayoutParams().height = 50;
            }
        });

        //todo inventory
        Inventorylist = SetInventory();
        inventoryAdapter = new InventoryAdapter(Dashboard.this, Inventorylist);
        elv_Inventory.setAdapter(ExpAdapter);
        elv_Inventory.setGroupIndicator(null);
        elv_Inventory.setChildIndicator(null);
        elv_Inventory.setChildDivider(getResources().getDrawable(R.color.white));
        elv_Inventory.setDivider(getResources().getDrawable(R.color.white));
        elv_Inventory.setDividerHeight(0);
        elv_Inventory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;
                for (int i = 0; i < elv_Inventory.getChildCount(); i++) {
                    height += elv_Inventory.getChildAt(i).getMeasuredHeight();
                    height += elv_Inventory.getDividerHeight();
                }
                elv_Inventory.getLayoutParams().height = (height+6)*7;
            }
        });

        // Listview Group collapsed listener
        elv_Inventory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                elv_Inventory.getLayoutParams().height = 50;
            }
        });













        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private ArrayList<ModelAccounts> SetRecharge() {
        String group_names[] = {getResources().getString(R.string.elv_acc_title)};

        String accounts[] = {getResources().getString(R.string.elv_acc_title_cust),getResources().getString(R.string.elv_acc_title_suppl),getResources().getString(R.string.elv_acc_title_allacc)};


        ArrayList<ModelAccounts> list = new ArrayList<ModelAccounts>();

        ArrayList<ModelAccountChild> ch_list;

        int size=accounts.length;
        int j = 0;

        for (String group_name : group_names) {
            ModelAccounts gru = new ModelAccounts();
            gru.setTitle(group_name);

            ch_list = new ArrayList<ModelAccountChild>();
            for (j = 0; j < size; j++) {
                ModelAccountChild ch = new ModelAccountChild();
                ch.setCtitle(accounts[j]);
                ch_list.add(ch);
            }
                gru.setItems(ch_list);
                list.add(gru);
                size = size + accounts.length;


        }
        return list;

    }



    private ArrayList<ModelInventory> SetInventory() {
        String group_names[] = {getResources().getString(R.string.elv_inv_title)};

        String accounts[] = {getResources().getString(R.string.elv_inv_title_item),getResources().getString(R.string.elv_inv_title_mesurement),getResources().getString(R.string.elv_inv_title_all_ser)};


        ArrayList<ModelInventory> list = new ArrayList<ModelInventory>();

        ArrayList<ModelInventoryChild> ch_list;

        int size=accounts.length;
        int j = 0;

        for (String group_name : group_names) {
            ModelInventory gru = new ModelInventory();
            gru.setTitle(group_name);

            ch_list = new ArrayList<ModelInventoryChild>();
            for (j = 0; j < size; j++) {
                ModelInventoryChild ch = new ModelInventoryChild();
                ch.setCtitle(accounts[j]);
                ch_list.add(ch);
            }
            gru.setItems(ch_list);
            list.add(gru);
            size = size + accounts.length;


        }
        return list;

    }
    private void InitUi() {
        accountlist=(ExpandableListView)findViewById(R.id.elv_account);
        elv_Inventory=(ExpandableListView)findViewById(R.id.elv_Inventory);
        linear_chat=(LinearLayout)findViewById(R.id.linear_chat);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mRecyclerView.setAdapter(mAdapter);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                requestUploadSurvey();
                sendMessage(message);
                mEditTextMessage.setText("");
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });
        linear_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,ChatActivity.class));
                finish();
            }
        });

    }

    private void requestUploadSurvey() {


        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[imagefiles.size()];

        for (int index = 0; index < imagefiles.size(); index++) {
            Log.d("Data", "requestUploadSurvey: survey image " + index + "  " + imagefiles.get(index).getAbsolutePath());
            File file = new File(imagefiles.get(index).getAbsolutePath());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("SurveyImage", file.getName(), surveyBody);
        }

//        final WebServicesAPI webServicesAPI = RetrofitManager.getInstance().getRetrofit().create(WebServicesAPI.class);
        Call<ResponseBody> surveyResponse = null;
        if (surveyImagesParts != null) {
            surveyResponse = AppController.getInstance().getApiInterface().uploadSurvey(surveyImagesParts);
        }
        surveyResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String image=response.body().string();
                    Toast.makeText(Dashboard.this, ""+image, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }

    private void start() {


        ImagePicker.with(this)
                .setFolderMode(true)
                .setCameraOnly(false)
                .setFolderTitle("Album")
                .setMultipleMode(true)
                .setSelectedImages(images)
                .setMaxSize(10).setShowCamera(true)
                .setKeepScreenOn(true)
                .start();

    }
    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false,"");
        mAdapter.add(chatMessage);

        mimicOtherMessage(message);
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false,"");
        mAdapter.add(chatMessage);

        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void sendImage(String imagepath) {
        ChatMessage chatMessage = new ChatMessage(null, true, true,imagepath);
        mAdapter.add(chatMessage);

        mimicOtherImage(imagepath);
    }

    private void mimicOtherImage(String imagepath) {
        ChatMessage chatMessage = new ChatMessage(null, false, true,imagepath);
        mAdapter.add(chatMessage);

        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagefiles.clear();
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            for(int i=0;i<images.size();i++){
                String imagepath=images.get(i).getPath();
                File f=new File (imagepath);
                imagefiles.add(f);
                sendImage(imagepath);
            }

//            String path = null;
//            for (int i = 0;i<images.size();i++)
//            {
//                File f = new File(images.get(i).getPath());
//                Picasso.with(AddProductActivity.this)
//                        .load(f)
//                        .into(img_main);
//            }

            // do your logic here...
        }
        super.onActivityResult(requestCode, resultCode, data);  // THIS METHOD SHOULD BE HERE so that ImagePicker works with fragment
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
