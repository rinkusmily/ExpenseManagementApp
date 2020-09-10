package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Pojo.LoginPojo;
import com.shrinkcom.expensemanagementapp.Pojo.registerPojo.RegisterPojo;
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity  implements View.OnClickListener  {

    EditText edt_email_phn , edt_pin ;
    TextView tv_sign_in ,tv_forgot_password, tv_enterguac , tv_terms2 , tv_sign2;

    String name , mobile_no , email , confirm_email , pin , confirm_pin;
    AlertDialog.Builder builder;
    String firebase_token ="";


    Context context;
    Activity activity;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        dbManager = new DBManager(this);
        dbManager.open();
        context = activity= this;

        initView();

    }

    private void initView() {


        Log.e("APPTOKAN ",">>> "+ FirebaseInstanceId.getInstance().getToken());

        firebase_token = FirebaseInstanceId.getInstance().getToken();

        edt_email_phn = findViewById(R.id.edt_email_phn);
        edt_pin = findViewById(R.id.edt_pin);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_sign2 = findViewById(R.id.tv_sign2);

        tv_sign_in = findViewById(R.id.tv_sign_in);
        tv_sign_in.setOnClickListener(this);
        tv_sign2.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_sign_in:

                validation();

                break;

            case R.id.tv_forgot_password:

                Intent intent = new Intent(SigninActivity.this, ForgotPin.class);
                startActivity(intent);

                break;
            case R.id.tv_sign2:

                Intent intent3 = new Intent(SigninActivity.this, Register.class);
                startActivity(intent3);
                finish();

                break;

        }

    }

    public boolean emailValidator(String email) {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }


    void validation() {


        email = edt_email_phn.getText().toString().trim();

        pin = edt_pin.getText().toString().trim();


        if (email.isEmpty()) {
            edt_email_phn.setError(getString(R.string.enter_email_phn));
            edt_email_phn.requestFocus();

        } else if (pin.isEmpty()) {
            edt_pin.setError(getString(R.string.enter_pin));
            edt_pin.requestFocus();

        } else {

            submitLogin();
        }
    }


    //this is for normal signin
    private void submitLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SIGN_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.v("DIKSHA", "LOGIN_RESPONSE" + response);
                                JSONObject jo=new JSONObject(response);


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                RegisterPojo loginPojo = gson.fromJson(response, RegisterPojo.class);

                                if (jo.getInt("status")==1)
                                {


                                    String email = loginPojo.getUser().get(0).getEmail();
                                    String id = ""+loginPojo.getUser().get(0).getUserId();
                                    String name = loginPojo.getUser().get(0).getName();
                                    String mobile = loginPojo.getUser().get(0).getPhone();

                                    Log.e("Email",">>>>>>"+email);
                                    Log.e("IDDDDDD",">>>>>>"+id);
                                    Log.e("NAMEEEEE",">>>>>>"+name);


                                    UserSession.getInstance(SigninActivity.this).writePrefs(UserSession.PREFS_EMAIL, email);
                                    UserSession.getInstance(SigninActivity.this).writePrefs(UserSession.PREFS_USER_ID, id);
                                    UserSession.getInstance(SigninActivity.this).writePrefs(UserSession.PREFS_NAME, name);
                                    UserSession.getInstance(SigninActivity.this).writePrefs(UserSession.PREFS_PHONE, mobile);

                                    UserSession.getInstance(SigninActivity.this).writePrefs(UserSession.PTREF_CURRENCY, loginPojo.getUser().get(0).getCurrency());
                                    Log.e("NAMEEEEE",">>>>>> wiwithsym "+loginPojo.getUser().get(0).getCurrency());
                                    new UserSession(SigninActivity.this).writePrefs(UserSession.CURENCY , loginPojo.getUser().get(0).getCurrency_word());
                                    new UserSession(SigninActivity.this).writePrefs(UserSession.CURENCY_SYMBOL , loginPojo.getUser().get(0).getCurrency());



                                    dbManager = new DBManager(SigninActivity.this);
                                    dbManager.open();

                                    String user_id = new UserSession(SigninActivity.this).readPrefs(UserSession.PREFS_USER_ID);
                                    String curre = new UserSession(SigninActivity.this).readPrefs(UserSession.CURENCY_SYMBOL);

                                    dbManager.INSERT_CATEGORY(user_id,"Hail",""+R.drawable.hail,"1","car" );
                                    dbManager.INSERT_CATEGORY(user_id,"Parking dent",""+R.drawable.parnking_dent,"1","car" );


                                    dbManager.INSERT_CATEGORY(user_id,"Food",""+R.drawable.food,"1","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Tool",""+R.drawable.tool,"1","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Hotel",""+R.drawable.hotel,"1","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Fuel",""+R.drawable.fuel,"1","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Ticket",""+R.drawable.tickets,"1","expence" );


                                    dbManager.INSERT_CATEGORY(user_id,"Cash",""+R.drawable.cash,"1","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Paycheck",""+R.drawable.paycheck,"1","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Bank Transfer",""+R.drawable.bank_tran,"1","payment" );







                                    getAllData(id);

                                }
                                else
                                {
                                    Toast.makeText(SigninActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(SigninActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("pin", pin);
                params.put("token",""+firebase_token);



                Log.e("DIKSHA", "LOGIN_REQUEST" + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }



    private void getAllData(final String user_id) {
        final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JJJJJJJJJJJJ", "ADD_LISTsdfds_RESPONSE"+response);

                        try {
                            dbManager = new DBManager(SigninActivity.this);
                            dbManager.open();

                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                GetAllData fileListPojo = gson.fromJson(response, GetAllData.class);

                                JSONObject jo=new JSONObject(response);


                                if (jo.getInt("status")==1)
                                {
                                    dbManager.DELETE_CATEGORY();
                                    dbManager.DELETE_HOME_LIST();
                                    dbManager.DELETE_TOTAL_TRANSACTION();
                                    dbManager.DELETE_ALL_PAYMENTS();


                                    for(int i =0; i< fileListPojo.getData().getCategory().size();i++){

                                        String user_id = fileListPojo.getData().getCategory().get(i).getUserId();
                                        String cat_name = fileListPojo.getData().getCategory().get(i).getName();
                                        String image = fileListPojo.getData().getCategory().get(i).getImage();
                                        String cat_type = fileListPojo.getData().getCategory().get(i).getType();
                                        String cat_id = ""+fileListPojo.getData().getCategory().get(i).getCatId();
                                        String backup_satatus = ""+fileListPojo.getData().getCategory().get(i).getBackupStatus();
                                        String categoryId = ""+fileListPojo.getData().getCategory().get(i).getCategoryId();

                                        dbManager.INSERTSERVERCATEGORY(user_id, cat_name, image, backup_satatus, cat_type,categoryId);


                                    }

                                    for(int i =0; i< fileListPojo.getData().getList().size();i++){

                                        String user_id = ""+fileListPojo.getData().getList().get(i).getUserId();
                                        String list_name = fileListPojo.getData().getList().get(i).getListName();
                                        String currency = fileListPojo.getData().getList().get(i).getCurrency();
                                        String date = fileListPojo.getData().getList().get(i).getDate();
                                        String backup_status = ""+fileListPojo.getData().getList().get(i).getBackupStatus();
                                        String list_id = ""+fileListPojo.getData().getList().get(i).getListId();


                                        dbManager.INSERT_HOME_LIST(user_id,list_id,list_name,currency ,date ,backup_status);


                                    }



                                    for(int i =0; i< fileListPojo.getData().getTransaction().size();i++){

                                        String user_id = ""+fileListPojo.getData().getTransaction().get(i).getUserId();
                                        String date = fileListPojo.getData().getTransaction().get(i).getCreatedAt();
                                        String price = fileListPojo.getData().getTransaction().get(i).getPrice();
                                        String type = fileListPojo.getData().getTransaction().get(i).getType();
                                        String list_id = ""+fileListPojo.getData().getTransaction().get(i).getListId();
                                        String tans_id = ""+fileListPojo.getData().getTransaction().get(i).getTransId();
                                        // String pay_id = ""+fileListPojo.getData().getTransaction().get(i).getPaymentId();
                                        dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id,list_id,"",price ,type ,date);


                                    }




                                    for(int i =0; i< fileListPojo.getData().getPayment().size();i++){

                                        String user_id = ""+fileListPojo.getData().getPayment().get(i).getUserId();
                                        String date = fileListPojo.getData().getPayment().get(i).getDate();
                                        String price = fileListPojo.getData().getPayment().get(i).getPrice();
                                        String type = fileListPojo.getData().getPayment().get(i).getType();
                                        String list_id = ""+fileListPojo.getData().getPayment().get(i).getListId();
                                        String c_id = ""+fileListPojo.getData().getPayment().get(i).getCategoryId();
                                        String pay_id = ""+fileListPojo.getData().getPayment().get(i).getPaymentId();
                                        String comnpany = ""+fileListPojo.getData().getPayment().get(i).getCompany();
                                        String finish = ""+fileListPojo.getData().getPayment().get(i).getFinishedStatus();
                                        String invoice = ""+fileListPojo.getData().getPayment().get(i).getInvoiceStatus();
                                        String paid = ""+fileListPojo.getData().getPayment().get(i).getPaidoutStatus();
                                        String icon = ""+fileListPojo.getData().getPayment().get(i).getIcon();
                                        String note = ""+fileListPojo.getData().getPayment().get(i).getNote();
                                        String name = ""+fileListPojo.getData().getPayment().get(i).getName();
                                        String creates = ""+fileListPojo.getData().getPayment().get(i).getCreatedAt();
                                        String plat_no = ""+fileListPojo.getData().getPayment().get(i).getPlateNo();
                                        String model = ""+fileListPojo.getData().getPayment().get(i).getModel();


                                        if(date.contains("T")){



                                            String date_new[] = date.split("T");

                                            date = date_new[0];

                                        }

                                        dbManager.INSERT_ALL_PAYMENTS(list_id,user_id,price,date ,c_id,note ,type, creates ,plat_no , model, finish, invoice,paid , icon, comnpany,name );


                                    }
                                    Log.v("DIKSHA",">DATAAAsA   SUCCESS>>>");

                                }
                                else
                                {


                                    Toast.makeText(SigninActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            e.printStackTrace();
                            Log.v("DIKSHA",">DATAAAsA>>>"+e);

                        }



                        Intent intent = new Intent(SigninActivity.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);

                      /*  Intent intent = new Intent(SigninActivity.this, HomeTutorial.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);*/
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();



                        Intent intent = new Intent(SigninActivity.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);

                       /* Intent intent =  new Intent(SigninActivity.this, HomeTutorial.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
*/
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id",""+user_id);
                Log.e("DIKSHA", "PPPPPPPPP_GETTTTTTTTTT"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

}
