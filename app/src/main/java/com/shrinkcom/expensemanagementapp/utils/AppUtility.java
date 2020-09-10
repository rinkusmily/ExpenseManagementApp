package com.shrinkcom.expensemanagementapp.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class AppUtility {
  public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static boolean checkPermission(final Context context) {
    int currentAPIVersion = Build.VERSION.SDK_INT;
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
          alertBuilder.setCancelable(true);
          alertBuilder.setTitle("Permission necessary");
          alertBuilder.setMessage("External storage permission is necessary");
          alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
              ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
          });
          AlertDialog alert = alertBuilder.create();
          alert.show();

        } else {
          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void updateResources(Activity context, String language) {
    try {
      SessionManager sessionManager = new SessionManager(context);
      Resources res = context.getResources();
      DisplayMetrics dm = res.getDisplayMetrics();
      android.content.res.Configuration conf = res.getConfiguration();

      if (!TextUtils.isEmpty(sessionManager.getLanguage())) {
        if (sessionManager.getLanguage().equalsIgnoreCase("english")) {
          conf.setLocale(new Locale("en"));

        } else {
          conf.setLocale(new Locale("ar"));

        }
      } else {
        String defaultDeviceLanguage = Locale.getDefault().getDisplayLanguage();
        sessionManager.setLanguage("ar");
        conf.setLocale(new Locale("ar"));
      }
      res.updateConfiguration(conf, dm);
      NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancel(100);

    } catch (Exception e) {

      Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }


/*  public static void logoutUser(final Context mContext) {

    new android.app.AlertDialog.Builder(mContext)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Are you sure you want to logout this App ?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Token(mContext);


                      *//*  Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        mContext.sendBroadcast(broadcastIntent);*//*
              }

            })
            .setNegativeButton("No", null)
            .show();
  }*/



  public static void downloadFileFromURL(Context context,String url)
  {

    DownloadFile asyncTask2 = new DownloadFile();
    asyncTask2.executeNetworkCall(context, url);
  }


  public static void downloadFileFromURL2(Context context,String url)
  {

    DownloadFile2 asyncTask2 = new DownloadFile2();
    asyncTask2.executeNetworkCall(context, url);
  }

}