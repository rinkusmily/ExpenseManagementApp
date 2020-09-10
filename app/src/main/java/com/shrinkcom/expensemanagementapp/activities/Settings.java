package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.BackupPojo.BackupPojo;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.fileListPojo.FileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.GetFileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.AllListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.HomeFileListAdapter;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.NetworkChangeReceiver;
import com.shrinkcom.expensemanagementapp.utils.NetworkUtil;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Settings extends AppCompatActivity  implements View.OnClickListener{


    ImageView img_close_setting ;
    LinearLayout layout_sign_out;

    String currecy_symbol="";
    String currecy="";
    String symbol="";

    Calendar c;
    String backup_file;
    String formattedDate;
    SimpleDateFormat df;

    long list_id = 0;

    NetworkChangeReceiver MyReceiver;
    ToggleButton toggle_hometut;


    String faci_id , dc_id , ds_id , dtoken;
    JSONArray jsonArray_catergory;
    JSONArray jsonArray_home_list;
    JSONArray jsonArray_payment;
    JSONArray jsonArray_transaction;
    JSONArray jsonArray_user;

    JSONObject jsonObject = new JSONObject();

    List<String> flist;

    String user_id;
    DBManager dbManager;




    AlertDialog.Builder builder;
    TextView tv_security , tv_Appirense, tv_export , tv_currency , tv_change_language , img_curr_symbol , tv_backup, tv_restore;

    Context context;
    Activity activity;


    final static String[] items = {"$ - US Dollar", "€ - Euro", "£ - British Pound","IRN - IRN ","A$ - Australian Dollar", " CA$ - Canadian Dollar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_settings);
        context=this;
        initView();

        user_id = new UserSession(Settings.this).readPrefs(UserSession.PREFS_USER_ID);
        symbol = new UserSession(this).readPrefs(UserSession.CURENCY_SYMBOL);
        String currency = new UserSession(this).readPrefs(UserSession.CURENCY);

        Log.v("DIKSHASHARMA",">>>>>>"+currency);

        if(currecy.equals("Euro")){
            img_curr_symbol.setBackgroundResource(R.drawable.ic_euro);
        }
        if(currecy.equals("lira")){
            img_curr_symbol.setBackgroundResource(R.drawable.ic_turkish_lira);
        }
        if(currecy.equals("USD")){
            img_curr_symbol.setBackgroundResource(R.drawable.ic_doller_usd);
        }


        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        backup_file = new UserSession(this).readPrefs(UserSession.BACKUP_FILE);

        MyReceiver = new NetworkChangeReceiver();
        broadcastIntent();





        showOfflineData();



    }

    private void initView() {

        img_close_setting=findViewById(R.id.img_close_setting);
        layout_sign_out=findViewById(R.id.layout_sign_out);
        tv_security=findViewById(R.id.tv_security);
        tv_Appirense=findViewById(R.id.tv_Appirense);
        tv_export=findViewById(R.id.tv_export);
        tv_currency=findViewById(R.id.tv_currency);
        tv_change_language=findViewById(R.id.tv_change_language);
        img_curr_symbol=findViewById(R.id.img_curr_symbol);
        tv_backup=findViewById(R.id.tv_backup);
        tv_restore=findViewById(R.id.tv_restore);
        toggle_hometut=findViewById(R.id.tb_notify);


        img_close_setting.setOnClickListener(this);
        layout_sign_out.setOnClickListener(this);
        tv_security.setOnClickListener(this);
        tv_Appirense.setOnClickListener(this);
        tv_export.setOnClickListener(this);
        tv_currency.setOnClickListener(this);
        tv_change_language.setOnClickListener(this);
        tv_backup.setOnClickListener(this);
        tv_restore.setOnClickListener(this);
        toggle_hometut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_close_setting:
                Intent intent1=new Intent(Settings.this, Home.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.tv_security:
                Intent intent2=new Intent(Settings.this, SecuriryActiviry.class);
                startActivity(intent2);
               // finish();
                break;

           case R.id.tv_Appirense:

               Intent intent3=new Intent(Settings.this, AppearanceActivity.class);
               startActivity(intent3);

               // finish();
               break;

               case R.id.tv_export:

               Intent intent4=new Intent(Settings.this, HomeList2.class);
               startActivity(intent4);

               // finish();

               break;


               case R.id.tv_currency:

               Intent intent5=new Intent(Settings.this, CuruencySelecterAcitivity.class);

                   startActivityForResult(intent5,2000);

                   // finish();

               break;


            case R.id.tv_change_language:

                Intent intent6=new Intent(Settings.this, LanguageSelectAcitivity.class);
                startActivity(intent6);

                 finish();

                break;



                case R.id.tv_backup:


                BackupAPI();
                break;



            case R.id.tv_restore:

                if(backup_file.isEmpty()){

                    Toast.makeText(getApplicationContext(),R.string.no_backup,Toast.LENGTH_SHORT).show();

                }else {
                    RestoreApi();

                   // getAllData();
                }


                break;


                case R.id.layout_sign_out:

                //showDialogMenu();

                syncDelegate2();

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context,gso);
                googleSignInClient.signOut();

                break;

                case R.id.tb_notify:

            finish();
                break;

        }



    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode ==2000) {
            //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
            // TODO Update your TextView.




            currecy_symbol =  data.getStringExtra("currency");

            Log.v("DIKSHA_09","CURENCY ON SELECT>>>"+currecy_symbol);

            String[] separated = currecy_symbol.split(" ");

           currecy= separated[0];
           symbol = separated[1];

           Log.v("DIKSHA_09","currecy>>>"+currecy);
           Log.v("DIKSHA_09","symbol>>>"+symbol);

           new UserSession(Settings.this).writePrefs(UserSession.CURENCY , currecy);
           new UserSession(Settings.this).writePrefs(UserSession.CURENCY_SYMBOL , currecy_symbol);
           new UserSession(Settings.this).writePrefs(UserSession.PTREF_CURRENCY , currecy_symbol);

           new UserSession(Settings.this).writePrefs(UserSession.PTREF_CURRENCY , currecy_symbol);

           if(currecy.equals("Euro")){
               img_curr_symbol.setBackgroundResource(R.drawable.ic_euro);
           }
            if(currecy.equals("lira")){
                img_curr_symbol.setBackgroundResource(R.drawable.ic_turkish_lira);
            }
            if(currecy.equals("USD")){
                img_curr_symbol.setBackgroundResource(R.drawable.ic_doller_usd);
            }

            // this will contain "Fruit"
            // this will contain " they taste good"
            //  tv_currency.setText(currency);

            updateCurrencyAPI();
            //user_id,currency




            list_id = list_id+1;
            //    list_id =   dbManager.INSERT_HOME_LIST(user_id,""+list_id,"HAIL NOTE" ,symbole ,formattedDate ,"1");
            list_id =   dbManager.INSERT_HOME_LIST(user_id,""+list_id,"HAIL NOTE" ,currecy_symbol ,formattedDate ,"0");


        }
    }








    // this dialog is use for logout
    private void showDialogMenu() {
        builder = new AlertDialog.Builder(context);
        builder.setMessage("").setTitle("");

        builder.setMessage(R.string.are_your_sure)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserSession.getInstance(context).logout();
                        Intent intent = new Intent(context, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);


                        dbManager.DELETE_ALL_PAYMENTS();
                        dbManager.DELETE_HOME_LIST();
                        dbManager.DELETE_CATEGORY();
                        dbManager.DELETE_TOTAL_TRANSACTION();


                        new UserSession(Settings.this).writePrefs(UserSession.PREFS_USER_ID,"");
                        new UserSession(Settings.this).writePrefs(UserSession.PREFS_EMAIL,"");
                        new UserSession(Settings.this).writePrefs(UserSession.PREFS_NAME,"");
                        new UserSession(Settings.this).writePrefs(UserSession.PREFER_LANGUAGE,"");
                        new UserSession(Settings.this).writePrefs(UserSession.PTREF_LIST_NAME,"");
                        new UserSession(Settings.this).writePrefs(UserSession.HOME_LIST_ID,"");


                        new UserSession(Settings.this).writePrefs(UserSession.PREFS_PHONE,"");
                        new UserSession(Settings.this).writePrefs(UserSession.PREF_DEVICE_ID,"");
                        new UserSession(Settings.this).writePrefs(UserSession.DONT_SHOW_MSG_HOME1,"");
                        new UserSession(Settings.this).writePrefs(UserSession.HOME_LIST_ID,"");
                        new UserSession(Settings.this).writePrefs(UserSession.STAUS_TOUCH_LOCK,"");
                        new UserSession(Settings.this).writePrefs(UserSession.STAUS_TOUCH_LOCK,"");
                        new UserSession(Settings.this).writePrefs(UserSession.HOME_LIST_ID,"");
                        new UserSession(Settings.this).writePrefs(UserSession.APPERIENCE_STATUS,"");

                        /*  Toast.makeText(context, R.string.clicked_yes,
                                Toast.LENGTH_SHORT).show();*/


                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        /*Toast.makeText(getContext(), R.string.clicked_no,
                                Toast.LENGTH_SHORT).show();*/
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(R.string.logout);
        // alert.setIcon(R.drawable.logonew);
        alert.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        updateText();

    }


    private void updateCurrencyAPI() {

      /*  final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.UPDATE_CURRENCY_SETTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        Log.e("DIKSHA", "UPDATE_CURRENCY_RESPONSE"+response);

                        try {
                           /* if (progressDialog != null) {

                                progressDialog.dismiss();*/


                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();



                            JSONObject jo=new JSONObject(response);
                            if (jo.getInt("status")==1)
                            {

                                // CartModel cartModel_1 = new CartModel();



                            }
                            else
                            {
                                // Toast.makeText(getActivity(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            //}
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();

                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                String user_id = new UserSession(Settings.this).readPrefs(UserSession.PREFS_USER_ID);
                String curre = new UserSession(Settings.this).readPrefs(UserSession.CURENCY_SYMBOL);

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("date", formattedDate);
                params.put("currency", curre);
                //params.put("list_id",list_id);
                Log.e("DIKSHA", "UPDATE_CURRENCY_REQURST"+params);
                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void RestoreApi() {

      /*  final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.RESTORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        Log.e("DIKSHA", "UPDATE_CURRENCY_RESPONSE"+response);



                        try {
                           /* if (progressDialog != null) {

                                progressDialog.dismiss();*/


                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();



                            JSONObject jo=new JSONObject(response);
                            if (jo.getInt("status")==1)
                            {

                                // CartModel cartModel_1 = new CartModel();

                                getAllData();


                            }
                            else
                            {
                                // Toast.makeText(getActivity(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            //}
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();

                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                String user_id = new UserSession(Settings.this).readPrefs(UserSession.PREFS_USER_ID);
                String curre = new UserSession(Settings.this).readPrefs(UserSession.CURENCY_SYMBOL);

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("file_name", backup_file);
                Log.e("DIKSHA", "UPDATE_CURRENCY_REQURST"+params);
                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void BackupAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(Settings.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BACK_UP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        Log.e("DIKSHA", "UPDATE_CURRENCY_RESPONSE"+response);

                        try {

                            if (progressDialog != null) {

                                progressDialog.dismiss();


                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                                BackupPojo backupPojo = gson.fromJson(response, BackupPojo.class);

                            JSONObject jo=new JSONObject(response);
                            if (jo.getInt("status")==1)
                            {

                                // CartModel cartModel_1 = new CartModel();

                                syncDelegate();


                                 backup_file = backupPojo.getUser().get(0).getBackupFile();

                                new UserSession(Settings.this).writePrefs(UserSession.BACKUP_FILE,backup_file);

                            Toast.makeText(getApplicationContext(),"backup completed",Toast.LENGTH_SHORT).show();


                            }
                            else
                            {
                                // Toast.makeText(getActivity(), jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                        // progressDialog.dismiss();

                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                String user_id = new UserSession(Settings.this).readPrefs(UserSession.PREFS_USER_ID);
                String curre = new UserSession(Settings.this).readPrefs(UserSession.CURENCY_SYMBOL);

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                Log.e("DIKSHA", "UPDATE_CURRENCY_RESPONSE"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(MyReceiver);
    }



    /// sync data get data__________________________________________________________________________





    public void syncDelegate() {


        String transaction_id=""  , user_id="" ,  car_id="" , price=""
                , type="" , date="" ,payment_id="" , list_id="";

        // fatch feddback of delegate frtom local database
        try {

            dbManager = new DBManager(this);
            dbManager.open();
            Cursor cursor = dbManager.fatch_all_trans();
            jsonArray_transaction = new JSONArray();


            if (cursor.moveToFirst()) {
                do {

                    transaction_id = ""+cursor.getInt(cursor.getColumnIndex(DatabaseHandler.AUTO_TANSCATION_ID));
                    user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_USER_ID));
                    price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PRICE));
                    type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_TYPE));
                    date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_CREATED_AT));
                    payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PAYMENT_ID));
                    list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_LIST_ID));


                    JSONObject object = new JSONObject();
                    object.put("trans_id", transaction_id);
                    object.put("user_id", user_id);
                    object.put("price", price);
                    object.put("type", type);
                    object.put("created_at", date);
                    object.put("payment_id", payment_id);
                    object.put("list_id", list_id);

                    jsonArray_transaction.put(object);

                    Log.e("SYNKKKK","SYNC TRANSACTION>> size "+object);
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {

            e.printStackTrace();

            Log.e("SYNKKKK","SYNC TRANSACTION ERROR>> size "+e);
        }

        jsonArray_user= new JSONArray();


        String home_list_id , user_id_2 ,home_list_name ,currency , home_date ,backup_status ;

        //first fatch all  delegates from the server

        try {


            dbManager = new DBManager(this);
            dbManager.open();

            Cursor cursor = dbManager.fatch_all_home_list();
            jsonArray_home_list = new JSONArray();
            if (cursor.moveToFirst()){
                do {


                    String[] columns = new String[] { DatabaseHandler.AUTO_HOME_LIST_ID, DatabaseHandler.HOME_USER_ID,
                            DatabaseHandler.HOME_LIST_ID , DatabaseHandler.HOME_LIST_NAME
                            , DatabaseHandler.CURRUNCY , DatabaseHandler.HOME_DATE , DatabaseHandler.BACKUP_SATATUS};


                    // home_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_HOME_LIST_ID));
                    home_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_ID));

                    user_id_2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_USER_ID));
                    home_list_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_NAME));
                    currency = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CURRUNCY));
                    home_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_DATE));
                    backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.BACKUP_SATATUS));


                    JSONObject object = new JSONObject();
                    object.put("user_id",user_id_2);
                    object.put("list_id",home_list_id);
                    object.put("list_name",home_list_name);
                    object.put("currency",currency);
                    // object.put("backup_status",backup_status);
                    object.put("date",home_date);


                    jsonArray_home_list.put(object);


                    Log.e("SYNKKKK","SYNC HOME>> size "+object);

                }while (cursor.moveToNext());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


        String category_auto_id=""  , category_user_id="" ,  categoty_name="" , categoty_image=""
                , category_backup_status="" , category_type="";

        // fatch feddback of delegate frtom local database
        try {


            Cursor cursor = dbManager.fatch_all_category();
            jsonArray_catergory = new JSONArray();
            flist = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {

                    category_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_LIST_ID));
                    category_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_USER_ID));
                    categoty_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_NAME));
                    categoty_image = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_IMAGE));
                    category_backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_BACKUP_STATUS));
                    category_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE));


                    JSONObject object = new JSONObject();
                    object.put("category_id", category_auto_id);
                    object.put("user_id", category_user_id);
                    object.put("name", categoty_name);
                    object.put("image", categoty_image);
                    object.put("type", category_type);
                    //  object.put("backup_status", category_backup_status);

                    jsonArray_catergory.put(object);


                    Log.e("SYNKKKK","SYNC CATEGORY>> size "+object);

                } while (cursor.moveToNext());
            }





        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SYNKKKK","ERRRRORRR>>"+""+e);

            Log.e("SYNKKKK","SYNC HOME ERROR>> size "+e);
        }


        String auto_payment_id="" , payment_price="" , payment_date="" , payment_category_id="" , payment_list_id="" , paymenrt_note="" , payment_type="" , payment_created_at="",
                payment_plate_no="" , pay_model="" ,finished_status="" , invoice_status="" , paid_out_status="" , payment_icon="" , company="" , type_name="";

        try {
            jsonArray_payment = new JSONArray();
            Cursor cursoragain  = dbManager.fatch_all_payment();
            Log.e("RINKUDATA",">>>"+cursoragain.getCount());
            if (cursoragain.moveToFirst()){
                do {
                    auto_payment_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    payment_price = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    payment_date = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String user_idd = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    payment_category_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));
                    payment_list_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    paymenrt_note = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_NOTE));
                    payment_type = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_TYPE));
                    payment_created_at = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAYE_CREATED_AT));
                    payment_plate_no = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_PLATE_NO));
                    pay_model = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_MODEL));
                    finished_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.FINISHED_STATUS));
                    invoice_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.INVOICE_STATUS));
                    paid_out_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAID_OUT_STATUS));
                    payment_icon = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_ICON));
                    company = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.COMPANY));
                    type_name = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.TYPE_NAME));

                    JSONObject object1 = new JSONObject();


                    Log.e("SYNKKKK","SYNC PAYMENT payment_id>> size "+auto_payment_id);
                    Log.e("SYNKKKK","SYNC PAYMENT price>> size "+payment_price);
                    Log.e("SYNKKKK","SYNC PAYMENT date>> size "+payment_date);

                    Log.e("SYNKKKK","SYNC PAYMENT user_id>> size "+user_id);
                    Log.e("SYNKKKK","SYNC PAYMENT created_at>> size "+payment_category_id);
                    Log.e("SYNKKKK","SYNC PAYMENT plate_no>> size "+payment_plate_no);
                    Log.e("SYNKKKK","SYNC PAYMENT plate_no>> size "+payment_plate_no);
                    Log.e("SYNKKKK","SYNC PAYMENT type>> size "+type);
                    Log.e("SYNKKKK","SYNC PAYMENT name>> size "+type_name);
                    Log.e("SYNKKKK","SYNC PAYMENT user_type>> size "+payment_type);


                    object1.put("company",company);
                    object1.put("name",type_name);
                    object1.put("payment_id",auto_payment_id);
                    object1.put("price",payment_price);
                    object1.put("date",payment_date);
                    object1.put("category_id",payment_category_id);
                    object1.put("note",paymenrt_note);
                    object1.put("user_id",user_idd);
                    object1.put("type",payment_type);
                    object1.put("created_at",payment_created_at);
                    object1.put("plate_no",payment_plate_no);
                    object1.put("model",pay_model);
                    object1.put("finished_status",finished_status);
                    object1.put("paidout_status",paid_out_status);
                    object1.put("invoice_status",invoice_status);
                    object1.put("icon",payment_icon);
                    object1.put("list_id",payment_list_id);
                    jsonArray_payment.put(object1);

                    Log.e("SYNKKKK","SYNC PAYMENT>> size "+object1);
                }while (cursoragain.moveToNext());
            }
        } catch (JSONException e) {

            Log.e("SYNKKKK","SYNC HOME PAYMENT>> size "+e);
            e.printStackTrace();
        }


        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";



        }else {

            String userid = UserSession.getInstance(Settings.this).readPrefs(UserSession.PREFS_USER_ID);

            if (userid.equals("")){


            }else {
                SyncFacilitatorFeedback();
            }
        }


    }
    public void  SyncFacilitatorFeedback() {

     /*   final ProgressDialog progressDialog = new ProgressDialog(Settings.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SYNCK_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YYYYYYYYYYYYYY",">>"+response);
                       // getAllData();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();

                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<>();

                JSONObject object  = new JSONObject();

                try {


                    object.put("category",jsonArray_catergory);
                    object.put("list",jsonArray_home_list);
                    object.put("payment",jsonArray_payment);
                    object.put("transaction",jsonArray_transaction);
                    object.put("user",jsonArray_user);




                    params.put("data",""+object);
                    params.put("user_id",user_id);

                    Log.v("DIKSHA","PPPPPPPPP_SYNKKKKKK >>>"+params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);







    }


    public void  SyncFacilitatorFeedback2() {

     /*   final ProgressDialog progressDialog = new ProgressDialog(Settings.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SYNCK_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YYYYYYYYYYYYYY",">>"+response);
                        // getAllData();

                        showDialogMenu();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();

                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<>();

                JSONObject object  = new JSONObject();

                try {


                    object.put("category",jsonArray_catergory);
                    object.put("list",jsonArray_home_list);
                    object.put("payment",jsonArray_payment);
                    object.put("transaction",jsonArray_transaction);
                    object.put("user",jsonArray_user);




                    params.put("data",""+object);
                    params.put("user_id",user_id);

                    Log.v("DIKSHA","PPPPPPPPP_SYNKKKKKK >>>"+params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);







    }

    private void getAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(Settings.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JJJJJJJJJJJJ", "ADD_LISTsdfds_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                GetAllData fileListPojo = gson.fromJson(response, GetAllData.class);

                                JSONObject jo = new JSONObject(response);

                                Log.v("DIKSHAAAAA", "jo.getInt  >>>" + jo.getInt("status"));
                                if (jo.getInt("status") == 1) {

                                    dbManager = new DBManager(Settings.this);
                                    dbManager.open();

                                    dbManager.DELETE_CATEGORY();
                                    dbManager.DELETE_HOME_LIST();
                                    dbManager.DELETE_TOTAL_TRANSACTION();
                                    dbManager.DELETE_ALL_PAYMENTS();



                                    if (fileListPojo.getData().getCategory().size() > 0) {


                                        for (int i = 0; i < fileListPojo.getData().getCategory().size(); i++) {

                                            String user_id = "" + fileListPojo.getData().getCategory().get(i).getUserId();
                                            String cat_name = "" + fileListPojo.getData().getCategory().get(i).getName();
                                            String image = "" + fileListPojo.getData().getCategory().get(i).getImage();
                                            String cat_type = "" + fileListPojo.getData().getCategory().get(i).getType();
                                            String cat_id = "" + fileListPojo.getData().getCategory().get(i).getCatId();
                                            String backup_satatus = "" + fileListPojo.getData().getCategory().get(i).getBackupStatus();
                                            String categoryId = "" + fileListPojo.getData().getCategory().get(i).getCategoryId();

                                            dbManager.INSERTSERVERCATEGORY(user_id, cat_name, image, backup_satatus, cat_type,categoryId);

                                            Log.v("DIKSHA", "INSERT_CATEGORY  ");
                                        }

                                    } else {
                                        dbManager.INSERT_CATEGORY(user_id, "Hail", "" + R.drawable.hail, "0", "car");
                                        dbManager.INSERT_CATEGORY(user_id, "Parking dent", "" + R.drawable.parnking_dent, "1", "car");


                                        dbManager.INSERT_CATEGORY(user_id, "Food", "" + R.drawable.food, "0", "expence");
                                        dbManager.INSERT_CATEGORY(user_id, "Tool", "" + R.drawable.tool, "0", "expence");
                                        dbManager.INSERT_CATEGORY(user_id, "Hotel", "" + R.drawable.hotel, "0", "expence");
                                        dbManager.INSERT_CATEGORY(user_id, "Fuel", "" + R.drawable.fuel, "0", "expence");
                                        dbManager.INSERT_CATEGORY(user_id, "Ticket", "" + R.drawable.tickets, "0", "expence");


                                        dbManager.INSERT_CATEGORY(user_id, "Cash", "" + R.drawable.cash, "0", "payment");
                                        dbManager.INSERT_CATEGORY(user_id, "Paycheck", "" + R.drawable.paycheck, "0", "payment");
                                        dbManager.INSERT_CATEGORY(user_id, "Bank Transfer", "" + R.drawable.bank_tran, "0", "payment");


                                    }

                                    for (int i = 0; i < fileListPojo.getData().getList().size(); i++) {

                                        String user_id = "" + fileListPojo.getData().getList().get(i).getUserId();
                                        String list_name = "" + fileListPojo.getData().getList().get(i).getListName();
                                        String currency = "" + fileListPojo.getData().getList().get(i).getCurrency();
                                        String date = "" + fileListPojo.getData().getList().get(i).getDate();
                                        String backup_status = "" + fileListPojo.getData().getList().get(i).getBackupStatus();
                                        String list_id = "" + fileListPojo.getData().getList().get(i).getListId();


                                        dbManager.INSERT_HOME_LIST(user_id, list_id, list_name, currency, date, backup_status);

                                        Log.v("DIKSHA", "INSERT_HOME_LIST  ");
                                    }


                                    for (int i = 0; i < fileListPojo.getData().getTransaction().size(); i++) {

                                        String user_id = "" + fileListPojo.getData().getTransaction().get(i).getUserId();
                                        String date = "" + fileListPojo.getData().getTransaction().get(i).getCreatedAt();
                                        String price = "" + fileListPojo.getData().getTransaction().get(i).getPrice();
                                        String type = "" + fileListPojo.getData().getTransaction().get(i).getType();
                                        String list_id = "" + fileListPojo.getData().getTransaction().get(i).getListId();
                                        String tans_id = "" + fileListPojo.getData().getTransaction().get(i).getTransId();
                                        String pay_id = "" + fileListPojo.getData().getTransaction().get(i).getPaymentId();


                                        dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id, list_id, pay_id, price, type, date);
                                        Log.v("DIKSHA", "INSERT_MAIN_TOTAL_TANSACTION  ");

                                    }


                                    for (int i = 0; i < fileListPojo.getData().getPayment().size(); i++) {

                                        String user_id = "" + fileListPojo.getData().getPayment().get(i).getUserId();
                                        String date = "" + fileListPojo.getData().getPayment().get(i).getDate();
                                        String price = "" + fileListPojo.getData().getPayment().get(i).getPrice();
                                        String type = "" + fileListPojo.getData().getPayment().get(i).getType();
                                        String list_id = "" + fileListPojo.getData().getPayment().get(i).getListId();
                                        String c_id = "" + fileListPojo.getData().getPayment().get(i).getCategoryId();
                                        String pay_id = "" + fileListPojo.getData().getPayment().get(i).getPaymentId();
                                        String comnpany = "" + fileListPojo.getData().getPayment().get(i).getCompany();
                                        String finish = "" + fileListPojo.getData().getPayment().get(i).getFinishedStatus();
                                        String invoice = "" + fileListPojo.getData().getPayment().get(i).getInvoiceStatus();
                                        String paid = "" + fileListPojo.getData().getPayment().get(i).getPaidoutStatus();
                                        String icon = "" + fileListPojo.getData().getPayment().get(i).getIcon();
                                        String note = "" + fileListPojo.getData().getPayment().get(i).getNote();
                                        String name = "" + fileListPojo.getData().getPayment().get(i).getName();
                                        String creates = "" + fileListPojo.getData().getPayment().get(i).getCreatedAt();
                                        String plat_no = "" + fileListPojo.getData().getPayment().get(i).getPlateNo();
                                        String model = "" + fileListPojo.getData().getPayment().get(i).getModel();

                                        Log.v("RRRRRR", ">   list_id>>" + list_id);
                                        Log.v("RRRRRR", ">   user_id>>" + user_id);
                                        Log.v("RRRRRR", ">   price>>" + price);
                                        Log.v("RRRRRR", ">   date>>" + date);
                                        Log.v("RRRRRR", ">   c_id>>" + c_id);
                                        Log.v("RRRRRR", ">   note>>" + note);
                                        Log.v("RRRRRR", ">   type>>" + type);
                                        Log.v("RRRRRR", ">   creates>>" + creates);
                                        Log.v("RRRRRR", ">   plat_no>>" + plat_no);
                                        Log.v("RRRRRR", ">   model>>" + model);
                                        Log.v("RRRRRR", ">   finish>>" + finish);
                                        Log.v("RRRRRR", ">   invoice>>" + invoice);
                                        Log.v("RRRRRR", ">   paid>>" + paid);
                                        Log.v("RRRRRR", ">   icon>>" + icon);
                                        Log.v("RRRRRR", ">   comnpany>>" + comnpany);
                                        Log.v("RRRRRR", ">   name>>" + name);


                                        if(date.contains("T")){



                                            String date_new[] = date.split("T");

                                            date = date_new[0];

                                        }


                                        dbManager.INSERT_ALL_PAYMENTS(list_id, user_id, price, date, c_id, note, type, creates, plat_no, model, finish, invoice, paid, icon, comnpany, name);

                                        Log.v("DIKSHA", "INSERT_ALL_PAYMENTS  ");
                                    }
                                    Log.v("DIKSHA", ">DATAAAsA   SUCCESS>>>");

                                } else {
                                    Toast.makeText(Settings.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                            } catch(Exception e){
                                //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));
                                e.printStackTrace();
                                Log.v("DIKSHA", ">DATAAAsA>>>" + e);
                                Log.v("DIKSHA", ">DIVYAAAAA>>>" + e);

                            }

                            // TODO: 7/3/2020  call next activity

                        Toast.makeText(Settings.this, R.string.restore_successfully, Toast.LENGTH_SHORT).show();


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  progressDialog.dismiss();

                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }) {
            @Override
            protected Map<String, String> getParams() {




                HashMap<String, String> params = new HashMap<>();

                Log.e("DIKSHA", "ADD_LIST_REQUEST"+params);

                params.put("user_id",""+user_id);



                Log.e("DIKSHA", "PPPPPPPPP_GETTTTTTTTTT"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Settings.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }




    //_____________________________________fatch offline data__________________________________________________


    public  void  showOfflineData(){

        dbManager = new DBManager(this);
        dbManager.open();

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {



            Cursor cursor = dbManager.FETCH_HOME_LIST(user_id);
            int i=0;

            if(cursor.moveToFirst()) {

                do {



                    String auto_file_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_HOME_LIST_ID));
                    list_id = Long.parseLong(auto_file_id);
                   // list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_ID));
                    String file_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_NAME));
                    String user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_USER_ID));
                    String currency = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CURRUNCY));
                    String date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_DATE));
                    String backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.BACKUP_SATATUS));
                    //  list_id2 = Integer.parseInt(auto_file_id);

                   Log.v("DIKSHAAA" , " last inserted list id"+ list_id);


                } while (cursor.moveToNext());

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","uuuuuuuuuuuuuu>>>>"+e);
        }

    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }

    public void updateText(){

        tv_currency.setText(R.string.currency);
        tv_Appirense.setText(R.string.Appearance);
        tv_security.setText(R.string.Security);
        tv_backup.setText(R.string.Backup);
        tv_restore.setText(R.string.Restore);



    }






    //__________________________synk before logout__________________________


    public void syncDelegate2() {


        String transaction_id=""  , user_id="" ,  car_id="" , price=""
                , type="" , date="" ,payment_id="" , list_id="";

        // fatch feddback of delegate frtom local database
        try {

            dbManager = new DBManager(this);
            dbManager.open();
            Cursor cursor = dbManager.fatch_all_trans();
            jsonArray_transaction = new JSONArray();


            if (cursor.moveToFirst()) {
                do {

                    transaction_id = ""+cursor.getInt(cursor.getColumnIndex(DatabaseHandler.AUTO_TANSCATION_ID));
                    user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_USER_ID));
                    price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PRICE));
                    type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_TYPE));
                    date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_CREATED_AT));
                    payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PAYMENT_ID));
                    list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_LIST_ID));


                    JSONObject object = new JSONObject();
                    object.put("trans_id", transaction_id);
                    object.put("user_id", user_id);
                    object.put("price", price);
                    object.put("type", type);
                    object.put("created_at", date);
                    object.put("payment_id", payment_id);
                    object.put("list_id", list_id);

                    jsonArray_transaction.put(object);

                    Log.e("SYNKKKK","SYNC TRANSACTION>> size "+object);
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {

            e.printStackTrace();

            Log.e("SYNKKKK","SYNC TRANSACTION ERROR>> size "+e);
        }

        jsonArray_user= new JSONArray();


        String home_list_id , user_id_2 ,home_list_name ,currency , home_date ,backup_status ;

        //first fatch all  delegates from the server

        try {


            dbManager = new DBManager(this);
            dbManager.open();

            Cursor cursor = dbManager.fatch_all_home_list();
            jsonArray_home_list = new JSONArray();
            if (cursor.moveToFirst()){
                do {


                    String[] columns = new String[] { DatabaseHandler.AUTO_HOME_LIST_ID, DatabaseHandler.HOME_USER_ID,
                            DatabaseHandler.HOME_LIST_ID , DatabaseHandler.HOME_LIST_NAME
                            , DatabaseHandler.CURRUNCY , DatabaseHandler.HOME_DATE , DatabaseHandler.BACKUP_SATATUS};


                    // home_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_HOME_LIST_ID));
                    home_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_ID));

                    user_id_2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_USER_ID));
                    home_list_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_NAME));
                    currency = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CURRUNCY));
                    home_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_DATE));
                    backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.BACKUP_SATATUS));


                    JSONObject object = new JSONObject();
                    object.put("user_id",user_id_2);
                    object.put("list_id",home_list_id);
                    object.put("list_name",home_list_name);
                    object.put("currency",currency);
                    // object.put("backup_status",backup_status);
                    object.put("date",home_date);


                    jsonArray_home_list.put(object);


                    Log.e("SYNKKKK","SYNC HOME>> size "+object);

                }while (cursor.moveToNext());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


        String category_auto_id=""  , category_user_id="" ,  categoty_name="" , categoty_image=""
                , category_backup_status="" , category_type="";

        // fatch feddback of delegate frtom local database
        try {


            Cursor cursor = dbManager.fatch_all_category();
            jsonArray_catergory = new JSONArray();
            flist = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {

                    category_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_LIST_ID));
                    category_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_USER_ID));
                    categoty_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_NAME));
                    categoty_image = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_IMAGE));
                    category_backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_BACKUP_STATUS));
                    category_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE));


                    JSONObject object = new JSONObject();
                    object.put("category_id", category_auto_id);
                    object.put("user_id", category_user_id);
                    object.put("name", categoty_name);
                    object.put("image", categoty_image);
                    object.put("type", category_type);
                    //  object.put("backup_status", category_backup_status);

                    jsonArray_catergory.put(object);


                    Log.e("SYNKKKK","SYNC CATEGORY>> size "+object);

                } while (cursor.moveToNext());
            }





        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SYNKKKK","ERRRRORRR>>"+""+e);

            Log.e("SYNKKKK","SYNC HOME ERROR>> size "+e);
        }


        String auto_payment_id="" , payment_price="" , payment_date="" , payment_category_id="" , payment_list_id="" , paymenrt_note="" , payment_type="" , payment_created_at="",
                payment_plate_no="" , pay_model="" ,finished_status="" , invoice_status="" , paid_out_status="" , payment_icon="" , company="" , type_name="";

        try {
            jsonArray_payment = new JSONArray();
            Cursor cursoragain  = dbManager.fatch_all_payment();
            Log.e("RINKUDATA",">>>"+cursoragain.getCount());
            if (cursoragain.moveToFirst()){
                do {
                    auto_payment_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    payment_price = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    payment_date = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String user_idd = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    payment_category_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));
                    payment_list_id = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    paymenrt_note = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_NOTE));
                    payment_type = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_TYPE));
                    payment_created_at = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAYE_CREATED_AT));
                    payment_plate_no = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_PLATE_NO));
                    pay_model = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_MODEL));
                    finished_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.FINISHED_STATUS));
                    invoice_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.INVOICE_STATUS));
                    paid_out_status = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAID_OUT_STATUS));
                    payment_icon = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.PAY_ICON));
                    company = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.COMPANY));
                    type_name = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.TYPE_NAME));

                    JSONObject object1 = new JSONObject();


                    Log.e("SYNKKKK","SYNC PAYMENT payment_id>> size "+auto_payment_id);
                    Log.e("SYNKKKK","SYNC PAYMENT price>> size "+payment_price);
                    Log.e("SYNKKKK","SYNC PAYMENT date>> size "+payment_date);

                    Log.e("SYNKKKK","SYNC PAYMENT user_id>> size "+user_id);
                    Log.e("SYNKKKK","SYNC PAYMENT created_at>> size "+payment_category_id);
                    Log.e("SYNKKKK","SYNC PAYMENT plate_no>> size "+payment_plate_no);
                    Log.e("SYNKKKK","SYNC PAYMENT plate_no>> size "+payment_plate_no);
                    Log.e("SYNKKKK","SYNC PAYMENT type>> size "+type);
                    Log.e("SYNKKKK","SYNC PAYMENT name>> size "+type_name);
                    Log.e("SYNKKKK","SYNC PAYMENT user_type>> size "+payment_type);


                    object1.put("company",company);
                    object1.put("name",type_name);
                    object1.put("payment_id",auto_payment_id);
                    object1.put("price",payment_price);
                    object1.put("date",payment_date);
                    object1.put("category_id",payment_category_id);
                    object1.put("note",paymenrt_note);
                    object1.put("user_id",user_idd);
                    object1.put("type",payment_type);
                    object1.put("created_at",payment_created_at);
                    object1.put("plate_no",payment_plate_no);
                    object1.put("model",pay_model);
                    object1.put("finished_status",finished_status);
                    object1.put("paidout_status",paid_out_status);
                    object1.put("invoice_status",invoice_status);
                    object1.put("icon",payment_icon);
                    object1.put("list_id",payment_list_id);
                    jsonArray_payment.put(object1);

                    Log.e("SYNKKKK","SYNC PAYMENT>> size "+object1);
                }while (cursoragain.moveToNext());
            }
        } catch (JSONException e) {

            Log.e("SYNKKKK","SYNC HOME PAYMENT>> size "+e);
            e.printStackTrace();
        }


        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";



        }else {

            String userid = UserSession.getInstance(Settings.this).readPrefs(UserSession.PREFS_USER_ID);

            if (userid.equals("")){


            }else {
                SyncFacilitatorFeedback2();
            }
        }


    }


    //________________________________________________________________________

}
