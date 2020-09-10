package com.shrinkcom.expensemanagementapp.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.MainActivity;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.ImageAdapter;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;
import com.shrinkcom.expensemanagementapp.utils.VolleySingleton;
import com.shrinkcom.expensemanagementapp.volley.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.MainActivity.PUBLIC_STATIC_STRING_IDENTIFIER;
import static com.shrinkcom.expensemanagementapp.MainActivity.STATIC_INTEGER_VALUE;
import static com.shrinkcom.expensemanagementapp.MainActivity.prgmImages;
import static com.shrinkcom.expensemanagementapp.utils.ApiService.EDIT_CAR_TYPE;
import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;


public class EditCarType extends AppCompatActivity {

    ImageView img_cancel_type , pich_img , img_icon ,img_right;
    EditText edt_name ;
    String name;
    String image;
    String id;
    String updatetime;
    Context _context;
    Activity activity;
    String temp_category_id;

    DBManager dbManager;

    int temp_img;
    String temp_img_name;


    public static int [] prgmImagess={R.drawable.cio_ic_mastercard,R.drawable.food,
            R.drawable.fork,R.drawable.transportation,R.drawable.food_tray,
            R.drawable.tool, R.drawable.people,R.drawable.cash,R.drawable.hailstorm
            ,R.drawable.shopping ,R.drawable.gift , R.drawable.bank_tran ,R.drawable.cash ,
            R.drawable.hail2, R.drawable.hospital , R.drawable.hotel , R.drawable.ic_check,R.drawable.parnking_dent
            ,R.drawable.payment ,R.drawable.museum ,R.drawable.debit_card ,R.drawable.euro,
            R.drawable.dollar, R.drawable.bill ,  R.drawable.shopping ,R.drawable.dish};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        _context=activity=this;
        img_cancel_type = findViewById(R.id.img_cancel_type);
        pich_img = findViewById(R.id.pich_img);
        pich_img = findViewById(R.id.pich_img);
        edt_name = findViewById(R.id.edt_name);
        img_icon = findViewById(R.id.img_icon);
        img_right = findViewById(R.id.img_right);


        dbManager = new DBManager(EditCarType.this);
        dbManager.open();
        final Intent intent = getIntent();

        name =intent.getStringExtra("name");
        temp_category_id =intent.getStringExtra("id_category");

        image =intent.getStringExtra("image");

        Log.v("DIKSHA_SHA "," name>>"+name);

        Log.v("DIKSHA_SHA "," id>>"+temp_category_id);
        edt_name.setText(name);

        img_icon.setImageResource(Integer.parseInt(image));


       /* Glide.with(this)
                .load(EXPENSE_TYPE_IMAGE+image).into(img_icon);*/

/*
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(this)

                .setDefaultRequestOptions(requestOptions)
                .load(EXPENSE_TYPE_IMAGE+image).into(img_icon);
*/



        img_cancel_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(EditCarType.this ,AddEntryCar.class);
                startActivity(intent);

            }
        });

        pich_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditCarType.this , MainActivity.class);
                startActivityForResult(intent,1000);

            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_name.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(),"Please Enter catrgoty name.",Toast.LENGTH_SHORT);

                }
                else {

                    if (temp_img == 0) {


                        temp_img =Integer.parseInt(image);


                      //  Toast.makeText(getApplicationContext(), "Please Select catrgoty icon.", Toast.LENGTH_SHORT);

                    } else {

                        String user_id = new UserSession(EditCarType.this).readPrefs(UserSession.PREFS_USER_ID);
                        dbManager.UPDATE_CATEGORY(user_id, temp_category_id, edt_name.getText().toString(), "" + temp_img);

                        Intent intent1 = new Intent(EditCarType.this, TypesofEntryCar.class);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        updatetime = String.valueOf(System.currentTimeMillis());
                if (resultCode ==1000) {
                  //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
                    // TODO Update your TextView.

                  temp_img =  data.getIntExtra("image_icon",-1);
                    img_icon.setImageResource(temp_img);

                    Log.v("DIKSHA_SHARMA",">>"+temp_img);

                }
    }


    @Override
    protected void onResume() {
        super.onResume();



/*
        if(PUBLIC_STATIC_STRING_IDENTIFIER.equals("idid")){
            updatetime = String.valueOf(System.currentTimeMillis());
            Log.v("DIKSHA_SHA "," RESUME EDIT B>>"+STATIC_INTEGER_VALUE);

           // img_icon.setImageResource(prgmImagess[STATIC_INTEGER_VALUE]);

            // Glide.with(this).load(prgmImagess[STATIC_INTEGER_VALUE]).into(img_icon);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(img_icon.getDrawable());
            requestOptions.error(img_icon.getDrawable());

            Glide.with(this)

                    .setDefaultRequestOptions(requestOptions)
                    .setDefaultRequestOptions(requestOptions)
                    .load(EXPENSE_TYPE_IMAGE+image).into(img_icon);*/


    }

    private void sendDataApi() {
        final ProgressDialog progressDialog = new ProgressDialog(_context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, EDIT_CAR_TYPE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.e("upload image", "--->" + resultResponse);



                Log.e("dikshaaaaaa", "DIKSHAAAA"+response);

                Intent intent = new Intent(EditCarType.this , TypesofEntryCar.class);
                startActivity(intent);
                finish();

                try {
                    if (progressDialog != null) {
                        progressDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(resultResponse);

                        int result = jsonObject.optInt("result");

                        JSONArray array = jsonObject.getJSONArray("userData");
                        finish();
                        if(result==1){
                            finish();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.e("upload image", "---->" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Log.e("upload image", "---->" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(EditCarType.this).readPrefs(UserSession.PREFS_USER_ID);
                HashMap<String, String> params = new HashMap<>();

                  params.put("category_id",temp_category_id);

                //params.put("user_id",user_id);
                  params.put("name",edt_name.getText().toString().trim());
                  Log.e("sendvalue", params.toString());

                return params;

            }

            @Override
            protected Map<String, DataPart> getByteData() {

                Drawable myDrawable = img_icon.getDrawable();
                BitmapDrawable drawable = (BitmapDrawable) img_icon.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

              //Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                Map<String, DataPart> params = new HashMap<>();

                params.put("avatar", new DataPart(updatetime + "tapkap_a.png",imageInByte, "image/png"));

                Log.e("sendvalue", params.toString());
                Log.e("sendvalue2", ""+ imageInByte);

                return params;
            }
        };

        VolleySingleton.getInstance(_context).addToRequestQueue(multipartRequest);
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


}
