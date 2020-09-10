package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

public class TutorialList extends AppCompatActivity implements View.OnClickListener {

    TextView tv_dontshow;
    ImageView imv_homelisttut;
    String currency, symbol , name_1, list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_list);

        Intent intent = getIntent();

        currency = intent.getStringExtra("curr");
        symbol = intent.getStringExtra("sym");
        list_id = intent.getStringExtra("list_id");
        name_1 = intent.getStringExtra("name");


        Log.v("DIKSHA","INTENT_DATA  currency >>"+currency);
        Log.v("DIKSHA","INTENT_DATA  symbol >>"+symbol);
        Log.v("DIKSHA","INTENT_DATA  list_id >>"+list_id);
        Log.v("DIKSHA","INTENT_DATA  list_id >>"+name_1);


        initview();



    }

    private void initview() {

        tv_dontshow=findViewById(R.id.tv_dontshow);
        imv_homelisttut=findViewById(R.id.imv_homelisttut);

        imv_homelisttut.setOnClickListener(this);
        tv_dontshow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imv_homelisttut:
                Intent intent2=new Intent(TutorialList.this, Home.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.tv_dontshow:

                UserSession userSession = new UserSession(this);
                userSession.writePrefs(userSession.DONT_SHOW_MSG_HOME2,"1");

                Intent intent3=new Intent(TutorialList.this, Home.class);
                startActivity(intent3);
                finish();
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
