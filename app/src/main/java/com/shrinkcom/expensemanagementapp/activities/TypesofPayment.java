package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.ExpenseListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.TypePaymentPojo;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.TypeExpenseAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.TypePaymentAdapter;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypesofPayment extends AppCompatActivity {

    RecyclerView cart_recycler_view;
    ImageView img_cancel_type , img_config_payment;
    private List<com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.User> notifyModels;
    Context context;
    private TypePaymentAdapter cartAdapter;
    private List<ExpenseListPojo> cartModelList;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typesof_payment);


        img_cancel_type = findViewById(R.id.img_cancel_type);
        cart_recycler_view=findViewById(R.id.match_parent_recycle_view);
        img_config_payment=findViewById(R.id.img_config_payment);

        context = this;

        cartModelList = new ArrayList<>();

     //   submitRegister();
        notifyModels = new ArrayList<com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.User>();

        img_cancel_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        img_config_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TypesofPayment.this ,ConfigTypesofPayment.class);
                startActivity(intent);
            }
        });
    }




/*    private void submitRegister() {

        final ProgressDialog progressDialog = new ProgressDialog(TypesofPayment.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.PAYEMENT_TYPE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        Log.e("DIKSHA", "GET_EXPENSE_TYPE"+response);
                        notifyModels.clear();

                        try {
                            if (progressDialog != null) {

                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                ExpenseListPojo expenseListPojo = gson.fromJson(response, ExpenseListPojo.class);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1)
                                {

                                    for(int i =0;i <expenseListPojo.getUser().size();i++){


                                       *//* cartModel_1.setTv_date(expenseListPojo.getUser().get(i).getDate());
                                        cartModel_1.setTv_vehicle_name(expenseListPojo.getUser().get(i).getModel());
                                        cartModel_1.setTv_vehicle_number(expenseListPojo.getUser().get(i).getPlateNo());
                                        cartModel_1.setTv_paid_out(expenseListPojo.getUser().get(i).getPrice());
                                        cartModel_1.setPayment_status(""+expenseListPojo.getUser().get(i).getFinishedStatus());*//*

                                        notifyModels.add(expenseListPojo);
                                    }
                                    cartAdapter = new TypePaymentAdapter(context, notifyModels, new RecyclerbuttonClick() {
                                        @Override
                                        public void onItemClick(int position, int type) {

                                            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();


                                          *//*  Intent i = new Intent(getApplicationContext(), AddPayment.class);
                                            startActivityForResult(i, type);*//*
                                            finish();

                                        }
                                    });
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()){
                                        @Override
                                        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                                            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {

                                                private static final float SPEED = 500f;

                                                @Override
                                                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                                    return SPEED / displayMetrics.densityDpi;
                                                }

                                            };
                                            smoothScroller.setTargetPosition(position);
                                            startSmoothScroll(smoothScroller);
                                        }

                                    };
                                    cart_recycler_view.setLayoutManager(mLayoutManager);
                                    cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
                                    cart_recycler_view.setAdapter(cartAdapter);



                                }
                                else
                                {
                                    Toast.makeText(TypesofPayment.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(TypesofPayment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {



            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(TypesofPayment.this).readPrefs(UserSession.PREFS_USER_ID);
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                Log.e("DIKSHA", "ADD_ENTRY_CAR_REQUEST"+params);
                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(TypesofPayment.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        //submitRegister();
        fetchDataOffline();

    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }







    public  void fetchDataOffline(){


        dbManager = new DBManager(this);
        dbManager.open();

        String  user_id = new UserSession(TypesofPayment.this).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            notifyModels = new ArrayList<>();

            Cursor cursor = dbManager.FETCH_CATEGORY_LIST(user_id , "payment");
            int i=0;

            notifyModels.clear();

            TypePaymentPojo fileListPojo = new TypePaymentPojo();

            if(cursor.moveToFirst()) {

                do {

                    String cat_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_USER_ID));
                    String cat_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_LIST_ID));
                    String category_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_NAME));
                    String category_image = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_IMAGE));
                    String cat_backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_BACKUP_STATUS));
                    String category_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE));


                    Log.v("AUTO_FILE_ID_222",">>"+cat_user_id);
                    Log.v("AUTO_FILE_ID_222",">>"+category_type);


                    List<com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.User> userlist = new ArrayList<>();
                    com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.User user = new com.shrinkcom.expensemanagementapp.Pojo.paymentTypePojo.User();
                    user.setCategoryId(Integer.parseInt(cat_auto_id));
                    user.setImage(category_image);
                    user.setName(category_name);
                    user.setUser_id(Integer.parseInt(cat_user_id));



                    userlist .add(user);

                    fileListPojo.setUser(userlist);

                    notifyModels.add(user);

                } while (cursor.moveToNext());

                i++;

                cartAdapter = new TypePaymentAdapter(context, notifyModels, new RecyclerbuttonClick() {
                    @Override
                    public void onItemClick(int position, int type) {

                      //  Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();


                        finish();

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()){
                    @Override
                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {

                            private static final float SPEED = 500f;

                            @Override
                            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                return SPEED / displayMetrics.densityDpi;
                            }

                        };
                        smoothScroller.setTargetPosition(position);
                        startSmoothScroll(smoothScroller);
                    }

                };
                cart_recycler_view.setLayoutManager(mLayoutManager);
                cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
                cart_recycler_view.setAdapter(cartAdapter);


            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","ADAPOER>>>>"+e);
        }

    }

}
