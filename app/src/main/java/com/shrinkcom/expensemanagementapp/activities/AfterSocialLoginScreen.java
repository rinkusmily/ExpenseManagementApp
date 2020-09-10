package com.shrinkcom.expensemanagementapp.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.SpinnerAdapter;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AfterSocialLoginScreen extends AppCompatActivity  implements View.OnClickListener , AdapterView.OnItemSelectedListener {


    //Spinner spinner_currency;

    private Boolean mAllowSelectionFiring = false;
    String currecy2="USD";


    Calendar c;
    String formattedDate;
    SimpleDateFormat df;


    TextView tv_currency ,tv_continue;

    Spinner spn_language;

    String currecy_symbol="USD $" , currecy="USD" , symbol="$";


    String STATUS;
    final CharSequence[] values= {"English(India)", "SPANISH" };

    Context context;
    Activity activity;
    String currency;

    DBManager dbManager;

    UserSession userSession ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_social_login_screen);

        context= this;

        // get currunt date

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());


        userSession = new UserSession(AfterSocialLoginScreen.this);

        tv_continue = findViewById(R.id.tv_continue);
        tv_currency = findViewById(R.id.tv_currency);
        spn_language = findViewById(R.id.spn_language);

        if (tv_currency.getText().toString().trim().equals("")) {

            tv_currency.setText("USA $");
            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_SYMBOL , currecy_symbol);
        }

       /* if (spn_language.getItemAtPosition(0)) {

            updateViews("en");
            Log.v("DIKSHA","getItemAtPosition(0)>>"+"getItemAtPosition(0)");
            STATUS="1";*/

           // updateText();
     String user_id=   new UserSession(AfterSocialLoginScreen.this).readPrefs(UserSession.PREFS_USER_ID );

        getAllData(user_id);



        tv_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AfterSocialLoginScreen.this , CuruencySelecterAcitivity.class);
                startActivityForResult(intent,2000);
            }
        });


        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String dntshow_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);
                //updateCurrencyAPI();


                if (tv_currency.getText().toString().trim().equals("")) {

                    tv_currency.setText("USA $");
                    new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_SYMBOL , currecy_symbol);
                }
                UserSession.getInstance(AfterSocialLoginScreen.this).writePrefs(UserSession.PTREF_CURRENCY, tv_currency.getText().toString());
                 if(dntshow_msg.equals("1")){

                     Intent intent = new Intent(AfterSocialLoginScreen.this, Home.class);
                     startActivity(intent);
                     finish();

                 }else {

                     Intent intent = new Intent(AfterSocialLoginScreen.this, Home.class);
                     startActivity(intent);
                     finish();


                  /* Intent intent = new Intent(AfterSocialLoginScreen.this, HomeTutorial.class);
                     startActivity(intent);
                     finish();*/

                 }


            }

        });


/*
        for payment Table Type:
        payment
                expence
        car
        for Transaction Table Type:
        credit
                debit
        income*/


        languageSelector();



    }

    private void languageSelector() {

        ArrayList<String> spinnerAdapterData = new ArrayList<>();
        String[] spinnerItemsArray = getResources().getStringArray(R.array.items2);

        ArrayList<String> items= getCountries("common_currency.json");


        Collections.addAll(spinnerAdapterData, spinnerItemsArray);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.adapter_spinner, spinnerAdapterData, getResources());

        Spinner itemList = (Spinner) findViewById(R.id.spn_language);
        itemList.setAdapter(adapter);

        itemList.setOnItemSelectedListener(AfterSocialLoginScreen.this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode ==2000) {
            //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
            // TODO Update your TextView.

            currecy_symbol =  data.getStringExtra("currency");

            String[] separated = currecy_symbol.split(" ");

            currecy= separated[0];
            symbol = separated[1];

            Log.v("DIKSHA_09","currecy>>>"+currecy);
            Log.v("DIKSHA_09","symbol>>>"+symbol);

            tv_currency.setText(currecy_symbol);

            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_SYMBOL , currecy_symbol);
            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_ON_SELECT , currecy);
            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_SYMBOL , symbol);
            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.PTREF_CURRENCY , currecy_symbol);



         /*   new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY , currecy);
            new UserSession(AfterSocialLoginScreen.this).writePrefs(UserSession.CURENCY_SYMBOL , currecy_symbol);*/



            // this will contain "Fruit"
            // this will contain " they taste good"
            //  tv_currency.setText(currency);


            //user_id,currency

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (mAllowSelectionFiring) {
            int selectedItemPosition = Integer.parseInt(view.getTag(R.string.meta_position).toString().trim());
            String selectedItemTitle = view.getTag(R.string.meta_title).toString().trim();

            Toast.makeText(getApplicationContext(),   selectedItemTitle, Toast.LENGTH_LONG).show();

            Log.v("DIKSHA","LANGUAGE>>"+selectedItemTitle);



            if (selectedItemTitle.equals("Español")) {



                updateViews("es");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);





            } else if (selectedItemTitle.equals("português")) {


                updateViews("pt");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "pt");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);




            } else if (selectedItemTitle.equals("Italiano")) {


                updateViews("it");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);




            } else if (selectedItemTitle.equals("Deutsche")) {


                updateViews("de");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);




            } else if (selectedItemTitle.equals("Français")) {


                updateViews("fr");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);




            } else if (selectedItemTitle.equals("Türk")) {

                updateViews("tr");
                STATUS = "0";
                Log.v("DIKSHA", "Language>>" + "es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);


            }
            else {
                updateViews("en");
                Log.v("DIKSHA","Language>>"+"en");
                STATUS="1";

                updateText();
            }




        } else {
            mAllowSelectionFiring = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }



    private ArrayList<String> getCountries(String fileName){
        JSONArray jsonArray=null;
        ArrayList<String> cList=new ArrayList<String>();
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray=new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getJSONObject(i).getString("cname"));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return cList;
    }


    public  void updateText(){


    }


    @Override
    public void onClick(View v) {

    }



/*
    private void updateCurrencyAPI() {

      */
/*  final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*//*


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.UPDATE_CURRENCY_SETTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        Log.e("DIKSHA", "UPDATE_CURRENCY_RESPONSE"+response);


                        try {
                           */
/* if (progressDialog != null) {

                                progressDialog.dismiss();*//*



                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();



                            JSONObject jo=new JSONObject(response);
                            if (jo.getInt("status")==1)
                            {

                                // CartModel cartModel_1 = new CartModel();


                                Intent intent = new Intent(AfterSocialLoginScreen.this , HomeTutorial.class);
                                startActivity(intent);
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

                        Toast.makeText(AfterSocialLoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                String user_id = new UserSession(AfterSocialLoginScreen.this).readPrefs(UserSession.PREFS_USER_ID);
                String curre = new UserSession(AfterSocialLoginScreen.this).readPrefs(UserSession.CURENCY_SYMBOL);

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("date", formattedDate);
                params.put("currency", currecy_symbol);
                params.put("list_id","");
                Log.e("DIKSHA", "UPDATE_CURRENCY_REQURST"+params);
                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(AfterSocialLoginScreen.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

*/





    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }




    //language selector






    private void updateViews(String languageCode) {

        userSession.setLanguage(languageCode);
        Locale mylocal = new Locale(languageCode);
        Context context = LocaleHelper.setLocale(AfterSocialLoginScreen.this, languageCode);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();

        Log.v("DIKSHA", "USERSESSION" + languageCode);

        new UserSession(this).setLanguage(languageCode);
        conf.locale = mylocal;
        resources.updateConfiguration(conf, dm);

    }




    private void getAllData(final String user_id) {

        final ProgressDialog progressDialog = new ProgressDialog(AfterSocialLoginScreen.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JJJJJJJJJJJJ", "ADD_LISTsdfds_RESPONSE"+response);

                        try {
                            dbManager = new DBManager(AfterSocialLoginScreen.this);
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
                                        Log.v("OKKKKK",">AUTO_FILE_ID list_name >>>"+list_name);
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


                                    Toast.makeText(AfterSocialLoginScreen.this, jo.optString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            Log.v("DIKSHA",">DATAAAsA>>>"+e);

                        }



                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


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
        final RequestQueue requestQueue = Volley.newRequestQueue(AfterSocialLoginScreen.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

}
