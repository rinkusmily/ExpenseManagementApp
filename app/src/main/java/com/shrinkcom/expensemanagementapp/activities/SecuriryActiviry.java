package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class SecuriryActiviry extends AppCompatActivity implements View.OnClickListener {

    TextView tv_pin_lock , tv_fingre_lock;
    ToggleButton tggl_pin_lock , tggl_touch_lock;
    String touch_lock ,pinLock ,pinLock_code;
    int pin_count , touch_count;
    ImageView img_close_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securiry_activiry);


        touch_lock =  new UserSession(SecuriryActiviry.this).readPrefs(UserSession.STAUS_TOUCH_LOCK);
        pinLock =  new UserSession(SecuriryActiviry.this).readPrefs(UserSession.STATUS_PINLOCK);
        pinLock_code =  new UserSession(SecuriryActiviry.this).readPrefs(UserSession.PINLOCK);






        initView();


        img_close_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void initView() {

        tv_pin_lock = findViewById(R.id.tv_pin_lock);
        tv_fingre_lock = findViewById(R.id.tv_fingre_lock);


        tggl_pin_lock=findViewById(R.id.tggl_pin_lock);
        tggl_touch_lock=findViewById(R.id.tggl_touch_lock);
        img_close_setting=findViewById(R.id.img_close_setting);


        tggl_pin_lock.setOnClickListener(this);
        tggl_touch_lock.setOnClickListener(this);
        img_close_setting.setOnClickListener(this);




        if(!pinLock_code.equals("")){

            tggl_pin_lock.setChecked(true);
            tggl_touch_lock.setChecked(false);

        }else {
            tggl_pin_lock.setChecked(false);

            new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK,"");


        }




        if(touch_lock.equals("true")){

            tggl_touch_lock.setChecked(true);
            tggl_pin_lock.setChecked(false);

        }else {
            tggl_touch_lock.setChecked(false);


        }












        tggl_pin_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Log.d("alarmCheck","ALARM SET TO TRUE");


                    String pin_lock = new UserSession(SecuriryActiviry.this).readPrefs(UserSession.PINLOCK);


                    if (pin_lock.equals("")) {


                        showPopup();




                    } else {

                        new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK, "true");
                        new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STAUS_TOUCH_LOCK, "false");


                        tggl_touch_lock.setChecked(true);
                        tggl_pin_lock.setClickable(true);
                    }

                }
                else
                {
                    Log.d("alarmCheck","ALARM SET TO FALSE");
                    if(pinLock_code==""){
                        Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();
                        tggl_pin_lock.setChecked(false);

                       // tggl_pin_lock.setClickable(false);
                    }


                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK,"");
                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.PINLOCK,"");

                    tggl_pin_lock.setChecked(false);

                }

            }
        });




        tggl_touch_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STAUS_TOUCH_LOCK, "true");
                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK, "false");

                    tggl_pin_lock.setChecked(false);


                } else {
                    Log.d("alarmCheck", "ALARM SET TO FALSE");

                    Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();
                    tggl_touch_lock.setChecked(false);
                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STAUS_TOUCH_LOCK,"");

                }

            }
        });










        tv_pin_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(SecuriryActiviry.this, PinLockActivity.class);
                startActivity(intent1);
                finish();

                tggl_touch_lock.setChecked(false);


            }
        });


        tv_fingre_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(SecuriryActiviry.this, FingerPrintLock.class);
                startActivity(intent1);
                finish();
                tggl_pin_lock.setChecked(false);

            }

        });

    }



    private void showPopup () {
        AlertDialog.Builder alert = new AlertDialog.Builder(SecuriryActiviry.this);
        alert.setMessage("You have to set pin first.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        tggl_touch_lock.setChecked(false);

                        Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();

                        Intent intent1=new Intent(SecuriryActiviry.this, PinLockActivity.class);
                        startActivity(intent1);
                        finish();


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tggl_pin_lock.setChecked(false);
            }
        });

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


          /*  case R.id.tggl_touch_lock:
                //tggl_touch_lock.setChecked(true);

                if (tggl_touch_lock.isChecked() == true) {

                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STAUS_TOUCH_LOCK, "true");
                    new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK, "false");

                    tggl_pin_lock.setChecked(false);



                } else {
                    Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();
                    tggl_touch_lock.setChecked(false);
                }


                break;

            case R.id.tggl_pin_lock:

              //  tggl_pin_lock.setChecked(true);

                if (tggl_pin_lock.isChecked() == true) {

                    String pin_lock = new UserSession(SecuriryActiviry.this).readPrefs(UserSession.PINLOCK);


                    if (pin_lock.equals("")) {

                        Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();

                    } else {

                        new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STATUS_PINLOCK, "true");
                        new UserSession(SecuriryActiviry.this).writePrefs(UserSession.STAUS_TOUCH_LOCK, "false");
                        ;

                        tggl_touch_lock.setChecked(false);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please set access pin first", Toast.LENGTH_SHORT).show();

                    tggl_pin_lock.setChecked(false);
                }

                break;*/

        }
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


}
