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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.Pinview;

public class PinLockActivity2 extends AppCompatActivity {


    ImageView btnback;
    String getOTP ;
    String otp;
    String  email;
    String user_id ;
    Context mContext;
    Activity activity;
    Pinview pinview1 ;
    ImageView btnnext;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_pin_lock);

        // to change status bar color

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        btnnext = findViewById(R.id.btnnext);

        mContext=activity=this;



        final Intent intent = getIntent();

     //   getOTP = intent.getExtras().getString("otp");
     //   email = intent.getExtras().getString("email");
        //  Log.v("TAG" , "email" +  email);
     //   Log.v("TAG" , "get otp " +  getOTP);

        pinview1 = findViewById(R.id.pinView);

        pinview1.setTextSize(15);

        pinview1.setTextColor(Color.WHITE);
       // pinview1.setInputType(Pinview.InputType.NUMBER);
      //  pinview1.setInputType(Pinview.InputType.PASSWORD);
        pinview1.showCursor(true);



        pinview1.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                // Toast.makeText(OtpActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                otp =  pinview.getValue();

                Log.v("TAG" , "get otp " +  otp);

            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("TAG" , "get otp " +  otp);

                Intent intent = new Intent(PinLockActivity2.this ,PinLockActivityConfirm2.class);
                intent.putExtra("pin",otp);
                startActivity(intent);
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
