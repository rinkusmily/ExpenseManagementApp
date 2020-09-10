package com.shrinkcom.expensemanagementapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class SplashScreen2 extends AppCompatActivity {

    TextView tv_continue;
    UserSession userSession;
    Context context;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        userSession = new UserSession(SplashScreen2.this);


        String apprience_status = userSession.readPrefs(UserSession.APPERIENCE_STATUS);


        if (!apprience_status.equals("")) {


            if (apprience_status.equals("dark")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


            }

        }





        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_screen);
        tv_continue = findViewById(R.id.tv_continue);


    /*   if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            userSession.writePrefs(UserSession.APPERIENCE_STATUS, "dark");

        if (!apprience_status.equals("")) {


            if (apprience_status.equals("dark")) {
                InitApplication.getInstance().setIsNightModeEnabled(true, context);


            } else {

                InitApplication.getInstance().setIsNightModeEnabled(false, context);


            }

        }*/

         //   userSession.writePrefs(UserSession.APPERIENCE_STATUS, "dark");


        String pin_status = UserSession.getInstance(SplashScreen2.this).readPrefs(UserSession.STATUS_PINLOCK);

        Log.v("DIKSHA_PINSATUS" , ">>>"+pin_status);




            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {



                    String userid = UserSession.getInstance(SplashScreen2.this).readPrefs(UserSession.PREFS_USER_ID);
                    Log.e("useridread",">>>>>"+userid);
                    if (userid.equals(" ") || userid.isEmpty()) {
                        Intent i = new Intent(SplashScreen2.this, Login.class);
                        i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {

                        UserSession userSession = new UserSession(SplashScreen2.this);
                        String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);

                        if(dnt_show_msg.equals("1")){

                            Intent i = new Intent(SplashScreen2.this, Home.class);
                            i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }
                        else{

                            Intent i = new Intent(SplashScreen2.this, Home.class);
                            i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();


                           /* Intent i = new Intent(SplashScreen2.this, HomeTutorial.class);
                            i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();*/
                        }

                    }

                /*Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);*/

                }
            }, SPLASH_TIME_OUT);
        }












    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }

}
