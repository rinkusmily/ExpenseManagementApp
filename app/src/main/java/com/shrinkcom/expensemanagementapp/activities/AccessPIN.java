package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.Pinview;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class AccessPIN extends AppCompatActivity {

    ImageView btnback;
    String getOTP ;
    String otp;
    String  email;
    String user_id ;
    Context mContext;
    Activity activity;

    ImageView btnnext;
    TextView tv_ok ,forgot_pin_lock;
    Pinview pinview1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_p_i_n);


        // to change status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        tv_ok = findViewById(R.id.tv_ok);
        forgot_pin_lock = findViewById(R.id.forgot_pin_lock);

        mContext=activity=this;

        final Intent intent = getIntent();

        //   getOTP = intent.getExtras().getString("otp");
        //   email = intent.getExtras().getString("email");
        //  Log.v("TAG" , "email" +  email);
        //   Log.v("TAG" , "get otp " +  getOTP);

        pinview1 = findViewById(R.id.pinView);

        pinview1.setTextSize(20);

    /*    pinview1.setTextColor(Color.WHITE);
        pinview1.setInputType(Pinview.InputType.PASSWORD);
        pinview1.showCursor(true);*/


        pinview1 = findViewById(R.id.pinView);
        pinview1.setTextSize(12);
        pinview1.setTextColor(Color.WHITE);
        /*pinview1.setClickable(true);
        pinview1.setFocusable(true);*/
      //  pinview1.key



/*

        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // Toast.makeText(OtpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp =  pinview.getValue();

                Log.v("TAG" , "get otp " +  otp);

            }
        });
*/

        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // Toast.makeText(OtpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp =  pinview.getValue();
            }
        });

  forgot_pin_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(AccessPIN.this, ForgotPinLock.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(i);

            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pin_session = new UserSession(AccessPIN.this).readPrefs(UserSession.PINLOCK);


                Log.v("TAG" , "get otp " +  otp);



                String pin_status = UserSession.getInstance(AccessPIN.this).readPrefs(UserSession.STATUS_PINLOCK);

                Log.v("DIKSHA_PINSATUS" , ">>>"+pin_status);


                if(otp.equals(pin_session)){
                    String userid = UserSession.getInstance(AccessPIN.this).readPrefs(UserSession.PREFS_USER_ID);
                    Log.e("useridread",">>>>>"+userid);
                    if (userid.equals(" ") || userid.isEmpty()) {
                        Intent i = new Intent(AccessPIN.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(i);

                    } else {

                        Intent i = new Intent(AccessPIN.this, Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                       // startActivity(i);
                        startActivity(i);

                        //   UserSession userSession = new UserSession(AccessPIN.this);
                        //  String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);


                        /*   if(dnt_show_msg.equals("1")){

                         *//*  Intent i = new Intent(AccessPIN.this, Home.class);
                            startActivity(i);*//*

                            finish();
                        }

                        else{

                            finish();
                        }*/

                    }

                }else {

                    Toast.makeText(getApplicationContext(),"Incorrect Pin",Toast.LENGTH_SHORT).show();
                }
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
