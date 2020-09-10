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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.MainActivity;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;
import com.shrinkcom.expensemanagementapp.utils.VolleySingleton;
import com.shrinkcom.expensemanagementapp.volley.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.ADD_CAR_CATEGORY;
import static com.shrinkcom.expensemanagementapp.utils.ApiService.ADD_PAYMENT_CATEGORY;
import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;


public class AddTypePayment extends AppCompatActivity {

    ImageView img_cancel_type, pich_img , img_icon , img_right;

    EditText edt_name;
    String name;
    String image;
    String id;

    String updatetime;
    Context _context;
    Activity activity;
    String temp_category_id;
    DBManager dbManager;
    int temp_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        img_cancel_type = findViewById(R.id.img_cancel_type);
        pich_img = findViewById(R.id.pich_img);
        img_icon = findViewById(R.id.img_icon);
        edt_name = findViewById(R.id.edt_name);
        img_right = findViewById(R.id.img_right);

        _context=activity=this;


        Intent intent = getIntent();

        name =intent.getStringExtra("name");
        temp_category_id =intent.getStringExtra("id_category");

        image =intent.getStringExtra("image");

        Log.v("DIKSHA_SHA "," name>>"+name);

        Log.v("DIKSHA_SHA "," id>>"+temp_category_id);
        edt_name.setText(name);


/*
        Glide.with(this)
                .load(EXPENSE_TYPE_IMAGE+image).into(img_icon);
*/

        img_cancel_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AddTypePayment.this ,AddPayment.class);
                startActivity(intent);


            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_name.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(),"Please Enter catrgoty name.",Toast.LENGTH_SHORT);

                }else {
                   // sendDataApi();
                    insertData();

                }

            }
        });




        pich_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showAlertDialog();

                Intent intent = new Intent(AddTypePayment.this , MainActivity.class);
                startActivityForResult(intent,1000);

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

    private void sendDataApi() {
        final ProgressDialog progressDialog = new ProgressDialog(_context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ADD_PAYMENT_CATEGORY, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.e("upload image", "--->" + resultResponse);
                Intent intent = new Intent(AddTypePayment.this , TypesofPayment.class);
                startActivity(intent);
                finish();


                try {
                    if (progressDialog != null) {
                        progressDialog.dismiss();


                        JSONObject jsonObject = new JSONObject(resultResponse);

                        int result = jsonObject.optInt("status");

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

                String user_id = new UserSession(AddTypePayment.this).readPrefs(UserSession.PREFS_USER_ID);
                HashMap<String, String> params = new HashMap<>();

                //   params.put("category_id",temp_category_id);
                params.put("user_id",user_id);
                params.put("name",edt_name.getText().toString().trim());
                //Log.e("sendvalue", params.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {

                Drawable myDrawable = img_icon.getDrawable();
                BitmapDrawable drawable = (BitmapDrawable) img_icon.getDrawable();

                Bitmap bitmap = drawable.getBitmap();

                //  Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                Map<String, DataPart> params = new HashMap<>();
                params.put("avatar", new DataPart(updatetime + "tapkap_a.png",imageInByte, "image/png"));
                Log.e("sendvalue", params.toString());
                Log.e("sendvalue2", ""+imageInByte);
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

    public void insertData(){


        dbManager = new DBManager(AddTypePayment.this);
        dbManager.open();

        String user_id = new UserSession(AddTypePayment.this).readPrefs(UserSession.PREFS_USER_ID);
        String name =  edt_name.getText().toString().trim();
        int icon = temp_img;

        // date = date.replace("/","s");


        dbManager.INSERT_CATEGORY(user_id,name ,""+icon ,"1" ,"payment");

        finish();

    }


}
