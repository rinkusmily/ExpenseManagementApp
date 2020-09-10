package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class AppearanceActivity extends AppCompatActivity {
    Context context;
    Activity activity;

    ImageView img_close_setting;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;

        userSession = new UserSession(context);

       /* if (InitApplication.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/


        String apprience_status = userSession.readPrefs(UserSession.APPERIENCE_STATUS);


        if (!apprience_status.equals("")) {


            if (apprience_status.equals("dark")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


            }

        }



        setContentView(R.layout.activity_appearance);



        SwitchCompat switchCompat = findViewById(R.id.switchCompat);
        img_close_setting = findViewById(R.id.img_close_setting);


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            switchCompat.setChecked(true);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    userSession.writePrefs(UserSession.APPERIENCE_STATUS, "dark");

                    Log.v("DIKSHA",">>"+"lioght");


                    InitApplication.getInstance().setIsNightModeEnabled(true , context);
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);

                } else {

                    Log.v("DIKSHA",">>"+"dark");

                    userSession.writePrefs(UserSession.APPERIENCE_STATUS, "light");
                    InitApplication.getInstance().setIsNightModeEnabled(false , context);
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }


            }
        });


        img_close_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                finish();
            }
        });


    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }

}
