package com.shrinkcom.expensemanagementapp.activities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;

public class InitApplication extends Application {
    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;

Context context ;
    private static InitApplication singleton=null ;

    public static InitApplication getInstance() {

        if(singleton != null)
        {

        }
        else {

            singleton = new InitApplication();

        }
        return singleton;

    }

    @Override
    public void onCreate(){

        super.onCreate();
        context=getApplicationContext();
        singleton = this;
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false);

    }

    public boolean isNightModeEnabled() {

        return isNightModeEnabled;

    }

    public void setIsNightModeEnabled(boolean isNightModeEnabled,Context context) {

        this.isNightModeEnabled = isNightModeEnabled;

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();

    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


}
