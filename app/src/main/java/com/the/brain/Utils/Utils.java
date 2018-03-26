package com.the.brain.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by jaypoojara on 15-02-2018.
 */

public class Utils {

    public static void replaceFragment(android.support.v4.app.Fragment fragment, String Tag,
                                       boolean isBackStack, boolean isMenuSupport,
                                       FragmentManager fragmentManager, int id) {
        FragmentManager fm = fragmentManager;
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setHasOptionsMenu(isMenuSupport);
        ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        ft.replace(id, fragment, Tag);
        if (isBackStack) {
            ft.addToBackStack(Tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public static int getWidthOfScreen(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            ((Activity) mContext).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
        } catch (Exception e) {
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    @SuppressLint("NewApi")
    public static void setDatePickerDialog(Context context, final EditText editText) {


        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate;
        try {
            inputDate = dateFormat.parse(dateFormat.format(new Date()));
            dateDialog.getDatePicker().setMaxDate(inputDate.getTime());

            dateDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(Constans.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isEmptyEditText(EditText edt, String msg) {
        if (edt.getText().toString().isEmpty()) {
            edt.setError(msg);
            edt.requestFocus();
            return true;
        }
        return false;
    }
}
