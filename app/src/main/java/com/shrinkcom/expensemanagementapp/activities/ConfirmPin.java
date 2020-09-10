package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.Pinview;

public class ConfirmPin extends AppCompatActivity {

    ImageView btnback,btnnext;
    String getOTP ;
    String otp;
    String  email;
    String user_id ;
    Context mContext;
    Activity activity;
    Pinview pinview1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);




        mContext=activity=this;
        Intent intent = getIntent();
        getOTP = intent.getExtras().getString("otp");
        email = intent.getExtras().getString("email");

        Log.v("TAG" , "email" +  email);
        Log.v("TAG" , "get otp " +  getOTP);

        pinview1 = findViewById(R.id.pinView);
        pinview1.setTextSize(12);
        pinview1.setTextColor(Color.WHITE);
        pinview1.showCursor(true);

        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // Toast.makeText(OtpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp =  pinview.getValue();
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
