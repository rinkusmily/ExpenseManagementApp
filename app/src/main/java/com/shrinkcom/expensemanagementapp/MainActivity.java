package com.shrinkcom.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.GridView;

import com.shrinkcom.expensemanagementapp.activities.EditCarType;
import com.shrinkcom.expensemanagementapp.adaptor.CustomAdapter;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {
    GridView gv;
    Context context;
    ArrayList prgmName;
    Image img_right;

    public static String PUBLIC_STATIC_STRING_IDENTIFIER = "identifire" ;
    public static int STATIC_INTEGER_VALUE ;
    public static String [] prgmNameList={"1","2","3","4","5","6","7","8","9","10" ,"11","12","13","14","15","16","17" ,"18","19","20","21","22","23","24","25"
            ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32" ,"33" ,"34" ,"35" ,"36" ,"37" ,"38"
            ,"39" ,"40" ,"41" ,"42" ,"43" ,"44" ,"45" ,"46" ,"47"};



    public static String [] img_name={"img_1","img_2","img_3","img_4","img_5","img_6","img_7","img_8","img_9","img_10" ,"img_11","img_12","img_13","img_14","img_15","img_16","img_17" ,"img_18","img_19","img_20","img_821","img_22","img_23","img_24","img_25"
            ,"img_26" ,"img_27" ,"img_28" ,"img_29" ,"img_30" ,"img_31" ,"img_32" ,"img_33" ,"img_34" ,"img_35" ,"img_36" ,"img_37" ,"img_38"
            ,"img_39" ,"img_40" ,"img_41" ,"img_42" ,"img_43" ,"img_44" ,"img_45" ,"img_46" ,"img_47"};
    public static int [] prgmImages={R.drawable.img_1,R.drawable.img_2,
            R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7,R.drawable.img_8,R.drawable.img_9
           ,R.drawable.img_10 ,R.drawable.img_11 , R.drawable.img_12 ,R.drawable.img_13 ,
            R.drawable.img_14, R.drawable.img_15 , R.drawable.img_16 , R.drawable.img_17,R.drawable.img_18
            ,R.drawable.img_19 ,R.drawable.img_20 ,R.drawable.img_21 ,R.drawable.img_22,
            R.drawable.img_23, R.drawable.img_24 ,  R.drawable.img_25 ,R.drawable.img_26,
            R.drawable.img_27, R.drawable.img_28 ,  R.drawable.img_29 ,R.drawable.img_30,
            R.drawable.img_31, R.drawable.img_32 ,  R.drawable.img_33 ,R.drawable.img_34 ,
            R.drawable.img_35, R.drawable.img_36 ,  R.drawable.img_37 ,R.drawable.img_38 ,
            R.drawable.img_39, R.drawable.img_40 ,  R.drawable.img41 ,  R.drawable.img42 ,R.drawable.img43 ,
            R.drawable.img44, R.drawable.img45 ,  R.drawable.img46 ,R.drawable.img47 ,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages , new RecyclerButtonclick() {
            @Override
            public void onItemClick(int position, int type) {

                   Intent intent = new Intent();
                   intent.putExtra("image_icon",prgmImages[position]);
                   intent.putExtra("image_name",img_name[position]);
                   setResult(1000,intent);
                   finish();


                   /* STATIC_INTEGER_VALUE=position;
                    Intent i = new Intent(DarkLightMode.this, EditCarType.class);
                    startActivityForResult(i, STATIC_INTEGER_VALUE);
                    PUBLIC_STATIC_STRING_IDENTIFIER ="idid";
                    Log.v("DIKSHA_SHA "," mainactivity>>"+STATIC_INTEGER_VALUE);
                    finish();*/


            }
        }));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.button_menu, menu);
        return true;
    }

    public Bitmap drawableToBitmap(PictureDrawable pd) {
        Bitmap bm = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        canvas.drawPicture(pd.getPicture());
        return bm;
    }

}
