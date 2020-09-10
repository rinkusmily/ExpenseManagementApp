package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;

public class PymentTypeActivity extends AppCompatActivity {
    ImageView img_cancel_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyment_type);

        img_cancel_type = findViewById(R.id.img_cancel_type);

        img_cancel_type.setOnClickListener(new View.OnClickListener() {
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
