package com.the.brain.Utils;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CRAFT BOX on 10/7/2016.
 */
public class GlobalElements extends Application {

    public static String message_pack_name="Vetstore";
    public static String directory="Vetstore";
    public static String document_directory="/Vetstore/document/";

    public static String totalcredit,totaldebit,totalmargin,totalbalance;
    public static int count=0;
    public static int pos=0;
    public static File file=null;

    public static String u="user";
    public static String p="user";
    public static String as="0";

    public static String aU="admin";
    public static String aPD="admin";
    public static String aas="1";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
//
//    public static void overrideFonts_Roboto_Regular(final Context context, final View v) {
//        try {
//            if (v instanceof ViewGroup) {
//                ViewGroup vg = (ViewGroup) v;
//                for (int i = 0; i < vg.getChildCount(); i++) {
//                    View child = vg.getChildAt(i);
//                    overrideFonts_Roboto_Regular(context, child);
//                }
//            }
//            else if (v instanceof TextView) {
//                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf"));
//            }
//        } catch (Exception e) {
//        }
//    }
   /* public static void overrideFontsRobertoBold(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFontsRobertoBold(context, child);
                }
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(FontFamily.process(context, R.raw.roboto_bold));
            }
            else if (v instanceof EditText) {
                ((EditText) v).setTypeface(FontFamily.process(context, R.raw.roboto_bold));
            }
            else if (v instanceof TextView) {
                ((TextView) v).setTypeface(FontFamily.process(context, R.raw.roboto_bold));
            }
        } catch (Exception e) {
        }
    }
*/
    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
                else
                {
                    NetworkInfo info1 = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (info1.isConnected()) {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static void showDialog(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Set Dialog Title
        alertDialog.setTitle("Internet Connection");
        // Set Dialog Message
        alertDialog.setMessage("Please check your internet connection ..");
        // Set OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        // Show Alert Message
        alertDialog.show();
    }


    public static boolean compareDate(String fromDate, String formatOfDate) {
        boolean success = false;
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //Date date1 = sdf.parse("31-03-2016");
            Date date1 = sdf.parse(""+fromDate);
            Date date2 = sdf.parse(""+sdf.format(new Date()));
            if(date1.equals(date2)){
                success=true;
            }
            else
            {
                success=false;
            }
        }catch(ParseException ex){
            ex.printStackTrace();
            return success;
        }
        return success;
    }

    public static String FirstRemoveZero(String text)
    {
        text = text.replaceFirst ("^0-*", "");
        return text;
    }

    public static String getNumber(String text)
    {
        try {
            String numberOnly= text.replaceAll("[^0-9]", "");
            return numberOnly;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        // Messages.debugOnLog("getDateToString","-->"+dateFormat.format(date));
        return dateFormat.format(date);
    }

    public static Calendar newCalendar = Calendar.getInstance();
    public static SimpleDateFormat cu_date_time = new SimpleDateFormat("dd-MM-yyyy hh;mm;ss a");
    public static String datetime = cu_date_time.format(newCalendar.getTime());

    public static SimpleDateFormat cu_date = new SimpleDateFormat("dd-MM-yyyy");
    public static String current_date = cu_date.format(newCalendar.getTime());

    public static String DecimalFormat(String value)
    {
        DecimalFormat doubleFormat = new DecimalFormat("#.##");
        doubleFormat.setMinimumFractionDigits(2);
        value=doubleFormat.format(Double.parseDouble(value));
        return value;
    }

    public static boolean versionCheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

}
