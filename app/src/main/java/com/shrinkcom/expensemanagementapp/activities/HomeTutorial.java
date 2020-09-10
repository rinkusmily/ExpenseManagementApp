package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class HomeTutorial extends AppCompatActivity implements View.OnClickListener {


    TextView tv_dontshow;
    ToggleButton toggle_configure_setting;
    ImageView imv_addhometut;

    int tgl_setting_count=0;

    DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tutorial);
        initview();
    }

    private void initview() {

        tv_dontshow=findViewById(R.id.tv_dontshow);
        toggle_configure_setting=findViewById(R.id.toggle_configure_setting);
        imv_addhometut=findViewById(R.id.imv_addhometut);
        tv_dontshow.setOnClickListener(this);
        toggle_configure_setting.setOnClickListener(this);
        imv_addhometut.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_dontshow:

                UserSession userSession = new UserSession(this);
                userSession.writePrefs(userSession.DONT_SHOW_MSG_HOME1,"1");

                Intent intent1=new Intent(HomeTutorial.this,TutorialList.class);
                startActivity(intent1);

                break;


                case R.id.imv_addhometut:
            /*    Intent intent2=new Intent(HomeTutorial.this, Home.class);
                startActivity(intent2);*/
                break;
            case R.id.toggle_configure_setting:

                if(tgl_setting_count==0){

                    tgl_setting_count=1;

                    Intent intent3=new Intent(HomeTutorial.this,Settings.class);
                    startActivity(intent3);
                    overridePendingTransition( R.anim.appear, R.anim.slide_out_up );

                }
               else {
                    tgl_setting_count=0;
                    finish();

                }
                break;

        }
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


}
