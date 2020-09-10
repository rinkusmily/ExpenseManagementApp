package com.shrinkcom.expensemanagementapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.LoginActivity;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Pojo.GoogleLoginPojo;
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;
import com.shrinkcom.expensemanagementapp.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.auth.GoogleAuthProvider.getCredential;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView title_login , tv_sign2;
    TextView tv_create_account;
    RelativeLayout rl_google;
    private static final int RC_SIGN_IN = 1;
    protected GoogleSignInClient mGoogleSignInClient;
    protected FirebaseAuth mAuth;
    String emailfb,first_name,strname;
    String UserEmail, Password,email;

    String firebase_token ="";
    LoginButton loginButton;
    CallbackManager callbackManager;

    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DBManager(this);
        dbManager.open();
        FirebaseApp.initializeApp(getApplicationContext());

        //  callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        setupGoogleSigninClient();
        initview();
        Date date = new Date();

    }

    private void initview() {


            Log.e("APPTOKAN ",">>> "+FirebaseInstanceId.getInstance().getToken());

              firebase_token = FirebaseInstanceId.getInstance().getToken();

        title_login=findViewById(R.id.title_login);
        tv_create_account=findViewById(R.id.tv_create_account);
        tv_sign2=findViewById(R.id.tv_sign2);
        rl_google=findViewById(R.id.rl_google);
        title_login.setOnClickListener(this);
        tv_create_account.setOnClickListener(this);
        tv_sign2.setOnClickListener(this);
        rl_google.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title_login:
              /*  Intent intentlogin=new Intent(Login.this,HomeTutorial.class);
                startActivity(intentlogin);*/
                break;

            case R.id.tv_create_account:
                new UserSession().writePrefs(UserSession.MOB,"");
                Intent intent_create_account =new Intent(Login.this,Register.class);
                startActivity(intent_create_account);
                break;

            case R.id.tv_sign2:
                Intent intent_sign_in =new Intent(Login.this,SigninActivity.class);
                startActivity(intent_sign_in);
                break;

            case R.id.rl_google:
               /* Intent intent_google =new Intent(Login.this,HomeTutorial.class);
                startActivity(intent_google);*/

                performSignIn();
                break;

        }
    }

    public void performSignIn() {

        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInWithGoogle(account);


            } catch (ApiException e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void setupGoogleSigninClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_Client_Id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signInWithGoogle(GoogleSignInAccount acct) {
        if (acct != null) {
            Log.d("DIKSHA", "firebaseAuthWithGoogle:" + acct.getId());
            AuthCredential credential = getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("error", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                strname =    mAuth.getCurrentUser().getDisplayName();
                                email =    mAuth.getCurrentUser().getEmail();
                                String phome =    mAuth.getCurrentUser().getPhoneNumber();


                                gmaillogin();
                                // Toast.makeText(Login.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            } else {

                                Log.w("DIKSHA", "signInWithCredential:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

//        mAuth.GoogleSignInClient.signOut(mGoogleSignInClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//
//                    }
//                });
    }


    public void gmaillogin(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SOCIAL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSSEEEEEGmailLogin",">>>"+response);
                        pDialog.hide();
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            GoogleLoginPojo googleloginpojo = gson.fromJson(response, GoogleLoginPojo.class);

                            if (googleloginpojo.getStatus() == 1) {
                                //  Token();



                                UserSession.getInstance(Login.this).writePrefs(UserSession.PREFS_USER_ID, googleloginpojo.getUser().get(0).getUserId());
                                UserSession.getInstance(Login.this).writePrefs(UserSession.PREFS_EMAIL, googleloginpojo.getUser().get(0).getEmail());
                                UserSession.getInstance(Login.this).writePrefs(UserSession.PREFS_NAME, googleloginpojo.getUser().get(0).getName());
                                UserSession.getInstance(Login.this).writePrefs(UserSession.PREFS_PHONE, googleloginpojo.getUser().get(0).getPhone());
                                //    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_USERPROFILE, facebookLoginPojo.getResponse().getAvatar());
                                UserSession.getInstance(Login.this).writePrefs(UserSession.LOGIN_BY, googleloginpojo.getUser().get(0).getLoginBy());

                                getAllData(googleloginpojo.getUser().get(0).getUserId());


                            } else {
                                Toast.makeText(Login.this,"LOgin Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", ""+email);
                params.put("username", ""+strname);
                params.put("login_by", "google");
                params.put("currency", "");
                params.put("token", ""+firebase_token);

                Log.e("paramss",">>"+params);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

   /* //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }*/

    static  int i =0001;
    public  static  void registration (Date date ){

        final DecimalFormat decimalFormat = new DecimalFormat("000");
        System.out.println(decimalFormat.format(i));

        Log.v("DIKSHA",">>"+i);

        SimpleDateFormat date_b = new SimpleDateFormat("ddMMyy");
        String myDate = date_b.format(date);

        String formatValue = String.format("%04d", i);

        String rn = "AAA"+""+myDate+formatValue;
        i++;

        Log.v("DIKSHA",">>  "+rn);
        Log.v("DIKSHA","formatValue>>"+formatValue);
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }



    private void getAllData(final String user_id) {

        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JJJJJJJJJJJJ", "ADD_LISTsdfds_RESPONSE"+response);

                        try {
                            dbManager = new DBManager(Login.this);
                            dbManager.open();
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                GetAllData fileListPojo = gson.fromJson(response, GetAllData.class);

                                JSONObject jo=new JSONObject(response);

                                Log.v("DIKSHAAAAA","jo.getInt  >>>"+jo.getInt("status"));

                                if (jo.optInt("status")==1)
                                {


                                    dbManager.DELETE_CATEGORY();
                                    dbManager.DELETE_HOME_LIST();
                                    dbManager.DELETE_TOTAL_TRANSACTION();
                                    dbManager.DELETE_ALL_PAYMENTS();


                                    for(int i =0; i< fileListPojo.getData().getCategory().size();i++){

                                        String user_id = ""+fileListPojo.getData().getCategory().get(i).getUserId();
                                        String cat_name = ""+fileListPojo.getData().getCategory().get(i).getName();
                                        String image = ""+fileListPojo.getData().getCategory().get(i).getImage();
                                        String cat_type = ""+fileListPojo.getData().getCategory().get(i).getType();
                                        String cat_id = ""+fileListPojo.getData().getCategory().get(i).getCatId();
                                        String backup_satatus = ""+fileListPojo.getData().getCategory().get(i).getBackupStatus();
                                        String categoryId = ""+fileListPojo.getData().getCategory().get(i).getCategoryId();

                                        dbManager.INSERTSERVERCATEGORY(user_id, cat_name, image, backup_satatus, cat_type,categoryId);

                                        Log.v("OKKKKK",">GETCVATEFORY user_id >>>"+user_id );
                                        Log.v("OKKKKK",">GETCVATEFORY cat_name >>>"+cat_name);
                                        Log.v("OKKKKK",">GETCVATEFORY image>>>"+image);
                                        Log.v("OKKKKK",">GETCVATEFORY cat_id>>>"+cat_id);
                                        Log.v("OKKKKK",">GETCVATEFORY backup_satatus >>>"+backup_satatus);
                                    }

                                    for(int i =0; i< fileListPojo.getData().getList().size();i++){

                                        String user_id = ""+fileListPojo.getData().getList().get(i).getUserId();
                                        String list_name = ""+fileListPojo.getData().getList().get(i).getListName();
                                        String currency = ""+fileListPojo.getData().getList().get(i).getCurrency();
                                        String date = ""+fileListPojo.getData().getList().get(i).getDate();
                                        String backup_status = ""+fileListPojo.getData().getList().get(i).getBackupStatus();
                                        String list_id = ""+fileListPojo.getData().getList().get(i).getListId();


                                        dbManager.INSERT_HOME_LIST(user_id,list_id,list_name,currency ,date ,backup_status);
                                        Log.v("OKKKKK",">GETCVATEFORY user_id >>>"+user_id );
                                        Log.v("OKKKKK",">GETCVATEFORY list_name >>>"+list_name);
                                        Log.v("OKKKKK",">GETCVATEFORY currency>>>"+currency);
                                        Log.v("OKKKKK",">GETCVATEFORY date>>>"+date);
                                        Log.v("OKKKKK",">GETCVATEFORY list_id >>>"+list_id);

                                    }
                                    for(int i =0; i< fileListPojo.getData().getTransaction().size();i++){

                                        String user_id = ""+fileListPojo.getData().getTransaction().get(i).getUserId();
                                        String date = ""+fileListPojo.getData().getTransaction().get(i).getCreatedAt();
                                        String price = ""+fileListPojo.getData().getTransaction().get(i).getPrice();
                                        String type = ""+fileListPojo.getData().getTransaction().get(i).getType();
                                        String list_id = ""+fileListPojo.getData().getTransaction().get(i).getListId();
                                        String tans_id = ""+fileListPojo.getData().getTransaction().get(i).getTransId();
                                        String pay_id = ""+fileListPojo.getData().getTransaction().get(i).getPaymentId();



                                        dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id,list_id,pay_id,price ,type ,date);


                                        Log.v("OKKKKK",">TRANSACTION user_id >>>"+user_id );
                                        Log.v("OKKKKK",">TRANSACTION list_name >>>"+date);
                                        Log.v("OKKKKK",">TRANSACTION currency>>>"+price);
                                        Log.v("OKKKKK",">TRANSACTION date>>>"+type);
                                        Log.v("OKKKKK",">TRANSACTION list_id >>>"+list_id);

                                    }

                                    for(int i =0; i< fileListPojo.getData().getPayment().size();i++){

                                        String user_id = ""+fileListPojo.getData().getPayment().get(i).getUserId();
                                        String date = ""+fileListPojo.getData().getPayment().get(i).getDate();
                                        String price = ""+fileListPojo.getData().getPayment().get(i).getPrice();
                                        String type = ""+fileListPojo.getData().getPayment().get(i).getType();
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


                                        Log.v("OKKKKK",">PAYMENT user_id >>>"+user_id );
                                        Log.v("OKKKKK",">PAYMENT list_name >>>"+date);
                                        Log.v("OKKKKK",">PAYMENT currency>>>"+price);
                                        Log.v("OKKKKK",">PAYMENT date>>>"+type);
                                        Log.v("OKKKKK",">PAYMENT list_id >>>"+list_id);
                                        Log.v("OKKKKK",">PAYMENT list_id >>>"+name);
                                        Log.v("OKKKKK",">PAYMENT plat_no >>>"+plat_no);
                                        Log.v("OKKKKK",">PAYMENT model >>>"+model);


                                    }

                                    Log.v("DIKSHA",">DATAAAsA   SUCCESS>>>");

                                }
                                else
                                {


                                    Toast.makeText(Login.this, jo.optString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            Log.v("DIKSHA",">DATAAAsA>>>"+e);

                        }

                        Intent intent = new Intent(Login.this, AfterSocialLoginScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Intent intent = new Intent(Login.this, AfterSocialLoginScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);

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
        final RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



}
