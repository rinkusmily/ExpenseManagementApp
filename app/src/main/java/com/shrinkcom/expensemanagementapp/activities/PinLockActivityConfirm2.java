package com.shrinkcom.expensemanagementapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.Pinview;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class PinLockActivityConfirm2 extends AppCompatActivity {


    ImageView btnback,btnnext;
    String getOTP ;
    String otp;
    String  email;
    String user_id ;
    Context mContext;
    Activity activity;
    Pinview pinview1 ;

    TextView tv_save;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_confirm_pin);

        // to change status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        tv_save = findViewById(R.id.tv_save);

        mContext=activity=this;

        Intent intent = getIntent();

      getOTP = intent.getExtras().getString("otp");
     //   email = intent.getExtras().getString("email");
        //  Log.v("TAG" , "email" +  email);

        getOTP = intent.getExtras().getString("pin");
       Log.v("TAG" , "get otp " +  getOTP);

        pinview1 = findViewById(R.id.pinView);

        pinview1.setTextSize(15);
      //  pinview1.setInputType(Pinview.InputType.NUMBER);

        pinview1.setTextColor(Color.WHITE);
     //   pinview1.setInputType(Pinview.InputType.PASSWORD);
        pinview1.showCursor(true);



        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // Toast.makeText(OtpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp =  pinview.getValue();

                Log.v("TAG" , "get otp " +  otp);

            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("TAG" , "get otp " +  otp);


                if (getOTP.equals(otp)) {

                    new UserSession(PinLockActivityConfirm2.this).writePrefs(UserSession.PINLOCK,otp);
                    new UserSession(PinLockActivityConfirm2.this).writePrefs(UserSession.STATUS_PINLOCK,"true");
                    new UserSession(PinLockActivityConfirm2.this).writePrefs(UserSession.OTP_SCREEN_VALUE,"true");

                    Intent intent = new Intent(PinLockActivityConfirm2.this ,Home.class);
                    startActivity(intent);
                    finish();


                }else {

                    Toast.makeText(getApplicationContext(),R.string.pin_not_match,Toast.LENGTH_SHORT).show();


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
