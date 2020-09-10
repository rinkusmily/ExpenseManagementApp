package com.shrinkcom.expensemanagementapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.shrinkcom.expensemanagementapp.activities.AccessPIN;
import com.shrinkcom.expensemanagementapp.activities.AppLifecycleHandler;
import com.shrinkcom.expensemanagementapp.activities.FingerPrintLock;
import com.shrinkcom.expensemanagementapp.activities.Home;
import com.shrinkcom.expensemanagementapp.activities.HomeTutorial;
import com.shrinkcom.expensemanagementapp.activities.LifeCycleDelegate;
import com.shrinkcom.expensemanagementapp.activities.Login;
import com.shrinkcom.expensemanagementapp.activities.PinLockActivity;
import com.shrinkcom.expensemanagementapp.activities.PinLockActivityConfirm;
import com.shrinkcom.expensemanagementapp.activities.SplashScreen;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.Locale;

public class MyApplication extends MultiDexApplication implements LifeCycleDelegate {
    private Locale locale = null;
    private static final String TAG = "MyApplication";
    private static MyApplication mInstance;

    public static volatile Handler applicationHandler = null;


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = new UserSession(getBaseContext()).getLanguage();

        AppLifecycleHandler lifeCycleHandler = new AppLifecycleHandler(this);
        registerLifecycleHandler(lifeCycleHandler);

        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        super.onCreate();
        mInstance = this;


        applicationHandler = new Handler(getInstance().getMainLooper());

        //NativeLoader.initNativeLibs(App.getInstance());


        // init Simplify SDK with public api key stored in metadata
        try {
            Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;

            // init simplify api key
            //  String apiKey = "sbpb_ZWI1YTE4MGItYzIzOS00ZDRhLTg2NTEtNDczZTBjYjgzYzUz";
            //bundle.getString("com.simplify.android.sdk.apiKey", null);
         /*   if (apiKey != null) {

            }*/

            // init android pay public key
            String androidPayPublicKey = bundle.getString("com.simplify.android.sdk.androidPayPublicKey", null);
            if (androidPayPublicKey != null) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
    }

    @Override
    public void onAppBackgrounded() {
        Log.d("DIKSHA", "App in background");
    }

    @Override
    public void onAppForegrounded() {
        Log.d("DIKSHA", "App in onAppForegrounded()");


        String pin_status = new UserSession(MyApplication.this).readPrefs(UserSession.STATUS_PINLOCK);
        String touch_status = new UserSession(MyApplication.this).readPrefs(UserSession.STAUS_TOUCH_LOCK);
        String otp_screen_status = new UserSession(MyApplication.this).readPrefs(UserSession.OTP_SCREEN_VALUE);


        if(otp_screen_status.equals("false")){


        }else {

            if(pin_status.equals("true")){

                Intent intent = new Intent(MyApplication.this , AccessPIN.class);
                intent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }else if(touch_status.equals("true")){
                Intent intent = new Intent(MyApplication.this , FingerPrintLock.class);
                intent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }else {

           /* String userid = UserSession.getInstance(MyApplication.this).readPrefs(UserSession.PREFS_USER_ID);
            Log.e("useridread",">>>>>"+userid);
            if (userid.equals(" ") || userid.isEmpty()) {
                Intent i = new Intent(MyApplication.this, Login.class);
                i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            } else {

                UserSession userSession = new UserSession(MyApplication.this);
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);

                if(dnt_show_msg.equals("1")){

                    Intent i = new Intent(MyApplication.this, Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                else{
                    Intent i = new Intent(MyApplication.this, HomeTutorial.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }*/
            }

        }





    }

    private void registerLifecycleHandler( AppLifecycleHandler  lifeCycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler);
        registerComponentCallbacks(lifeCycleHandler);
    }
}