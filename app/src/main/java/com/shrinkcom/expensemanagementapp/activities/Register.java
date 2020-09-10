
package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.shrinkcom.expensemanagementapp.MainActivity;
import com.shrinkcom.expensemanagementapp.Pojo.LoginPojo;
import com.shrinkcom.expensemanagementapp.Pojo.registerPojo.RegisterPojo;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity  implements View.OnClickListener , AdapterView.OnItemSelectedListener{
    EditText edt_nameac ,edt_emailac , edt_mobile , edt_conemailac ,edt_pinac , edt_conpinac ,tv_country_code;

    //Spinner spinner_currency;

    private Boolean mAllowSelectionFiring = false;
    String currecy2="USD";
    String symbol="$";
    String currency_with_symbol="USD $";

    TextView tv_createac ,tv_terms1, tv_terms2 , tv_sign2 ,title_createac ,tv_currency , tv_sign1;
    String STATUS;
    final CharSequence[] values= {"English(India)", "SPANISH" };
    Spinner itemList;
    String name , mobile_no , email , confirm_email , pin , confirm_pin , c_code;
    Context context;
    Activity activity;
    String currency;

    UserSession userSession ;
    DBManager dbManager;

    String firebase_token ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = activity= this;

        userSession = new UserSession(this);

        initView();

        firebase_token = FirebaseInstanceId.getInstance().getToken();
        languageSelector();

    }

    private void languageSelector() {


        ArrayList<String> spinnerAdapterData = new ArrayList<>();
        String[] spinnerItemsArray = getResources().getStringArray(R.array.items2);

        ArrayList<String> items= getCountries("common_currency.json");


        Collections.addAll(spinnerAdapterData, spinnerItemsArray);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.adapter_spinner, spinnerAdapterData, getResources());

        itemList = (Spinner) findViewById(R.id.spn_language);
        itemList.setAdapter(adapter);

        itemList.setOnItemSelectedListener(Register.this);


    }


    private void initView() {

        edt_nameac = findViewById(R.id.edt_nameac);
        edt_emailac = findViewById(R.id.edt_emailac);
        edt_conemailac = findViewById(R.id.edt_conemailac);
        edt_pinac = findViewById(R.id.edt_pinac);
        edt_conpinac = findViewById(R.id.edt_conpinac);
        edt_mobile = findViewById(R.id.edt_mobile);
        tv_country_code = findViewById(R.id.tv_country_code);
        title_createac = findViewById(R.id.title_createac);
        tv_currency = findViewById(R.id.tv_currency);
        tv_terms1 = findViewById(R.id.tv_terms1);
        tv_sign1 = findViewById(R.id.tv_sign1);
        tv_sign2 = findViewById(R.id.tv_sign2);

        tv_createac = findViewById(R.id.tv_createac);
        // tv_enterguac = findViewById(R.id.tv_enterguac);
        tv_terms2 = findViewById(R.id.tv_terms2);
        tv_sign2 = findViewById(R.id.tv_sign2);

        tv_createac.setOnClickListener(this);
        tv_sign2.setOnClickListener(this);
        tv_currency.setOnClickListener(this);


        String sring = userSession.readPrefs(UserSession.MOB);
        tv_country_code.setText(sring);


        tv_country_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.v("DIKSHA","beforeTextChanged>>"+"beforeTextChanged");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("DIKSHA","onTextChanged>>"+"onTextChanged");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("DIKSHA","afterTextChanged>>"+"afterTextChanged");

                String mob = tv_country_code.getText().toString();


            }
        });







    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_createac:

                validation();

                break;

            case R.id.tv_enterguac:


                break;

            case R.id.tv_terms2:


                break;

            case R.id.tv_currency:

                Intent intent = new Intent(Register.this , CuruencySelecterAcitivity.class);
                startActivityForResult(intent,2000);
                break;

            case R.id.tv_sign2:


                Intent intent_sign_in = new Intent(Register.this,SigninActivity.class);

                startActivity(intent_sign_in);



                break;

        }

    }


    private void updateViews(String languageCode) {

        userSession.setLanguage(languageCode);
        Locale mylocal = new Locale(languageCode);
        Context context = LocaleHelper.setLocale(Register.this, languageCode);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();

        Log.v("DIKSHA","USERSESSION"+languageCode);

        new UserSession(this).setLanguage(languageCode);
        conf.locale = mylocal;
        resources.updateConfiguration(conf, dm);

       /* String mob=tv_country_code.getText().toString();
        String sub_str = null;



        if(mob.length()>=2){

            System.out.println(mob.substring(0,2));
            sub_str =mob.substring(0,2);

        }else{
            sub_str="";

        }



        if(mob.length()==2) {
            if (sub_str.equals("34") || sub_str.equals("54")) {


                userSession.writePrefs(UserSession.MOB, tv_country_code.getText().toString());
                title_createac.setText(getString(R.string.Createac));
          *//*      finish();
                startActivity(getIntent());
                overridePendingTransition(0,0);*//*

                tv_createac.setText(R.string.createaccount);
                title_createac.setText(R.string.Createac);
                tv_createac.setText(R.string.create);




                //  startActivity(new Intent(Register.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }else if (mob.equals("0") || mob.equals("1") || mob.equals("2") || mob.equals("3") || mob.equals("4") || mob.equals("5") || mob.equals("6") || mob.equals("7") ||
                    mob.equals("8") || mob.equals("9")) {


            } else if ((!sub_str.equals("34") || !sub_str.equals("54"))) {

                userSession.writePrefs(UserSession.MOB, tv_country_code.getText().toString());

                tv_createac.setText(R.string.createaccount);
                title_createac.setText(R.string.Createac);
                tv_createac.setText(R.string.create);

         *//*       finish();
                startActivity(getIntent());
                overridePendingTransition(0,0);*//*

            }
        }*/
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

        name = edt_nameac.getText().toString().trim();
        mobile_no = edt_mobile.getText().toString().trim();
        email = edt_emailac.getText().toString().trim();
        confirm_email = edt_conemailac.getText().toString().trim();
        pin = edt_pinac.getText().toString().trim();
        confirm_pin=edt_conpinac.getText().toString().trim();
        c_code=tv_country_code.getText().toString().trim();

        if (name.isEmpty())
        {
            edt_nameac.setError(getString(R.string.enter_name));
            edt_nameac.requestFocus();

        }
        else   if (c_code.isEmpty()) {
            tv_country_code.setError(getString(R.string.enter_c_code));
            tv_country_code.requestFocus();
        }
        else   if (mobile_no.isEmpty()) {
            edt_mobile.setError(getString(R.string.enter_phone));
            edt_mobile.requestFocus();
        }
        else if (!emailValidator(email)) {
            edt_emailac.setError(getString(R.string.enter_email));
            edt_emailac.requestFocus();
        }
        else if (confirm_email.isEmpty())
        {
            edt_conemailac.setError(getString(R.string.enter_confirm_email));
            edt_conemailac.requestFocus();

        }
        else  if (!emailValidator(confirm_email)) {
            edt_conemailac.setError(getString(R.string.enter_confirm_email));
            edt_conemailac.requestFocus();
        }
        else  if (!email.equalsIgnoreCase(confirm_email)){

            edt_conemailac.setError(("Email dont match"));
            edt_conemailac.requestFocus();
        }
        else  if (pin.isEmpty()) {
            edt_pinac.setError(getString(R.string.enter_pin));
            edt_pinac.requestFocus();

        }else if (confirm_pin.isEmpty()) {

            edt_conpinac.setError(getString(R.string.enter_confirm_pin));
            edt_conpinac.requestFocus();


        }else if (!confirm_pin.equals(pin)) {

            edt_conpinac.setError(getString(R.string.pin_not_match));
            edt_conpinac.requestFocus();


        }


        else {

            submitRegister();
        }
    }



        /*else {
            if (!emailValidator(email)) {
                edt_emailac.setError(getString(R.string.enter_email));
                edt_emailac.requestFocus();
            }
            else {
                if (pin.isEmpty()) {
                    edt_pinac.setError(getString(R.string.enter_pin));
                    edt_pinac.requestFocus();

                }
                else {

                    if (confirm_pin.isEmpty()) {
                        edt_conpinac.setError(getString(R.string.enter_confirm_pin));
                        edt_conpinac.requestFocus();

                    }
                    else {

                        if (mobile_no.isEmpty()) {
                            edt_mobile.setError(getString(R.string.enter_phone));
                            edt_mobile.requestFocus();
                        }
                        else {
                            submitRegister();
                        }

                    }


                }

            }
        }
*/


    private void submitRegister() {

        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.e("msg========", "REGISTER_RESPONSE  :  " + response);

                                JSONObject jo=new JSONObject(response);


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                RegisterPojo registerPojo = gson.fromJson(response, RegisterPojo.class);

                                if (jo.getInt("status")==1)
                                {
                                    //Token();


                                    String email = registerPojo.getUser().get(0).getEmail();
                                    String id = ""+registerPojo.getUser().get(0).getUserId();
                                    String name = ""+registerPojo.getUser().get(0).getName();
                                    String mobile = registerPojo.getUser().get(0).getPhone();
                                    String currency_2 = registerPojo.getUser().get(0).getCurrency();
                                    Log.e("Email",">>>>>>"+email);
                                    Log.e("IDDDDDD",">>>>>>"+id);

                                    Log.e("namename",">>>name>>>"+name);
                                    UserSession.getInstance(Register.this).writePrefs(UserSession.PREFS_EMAIL, email);
                                    UserSession.getInstance(Register.this).writePrefs(UserSession.PREFS_USER_ID, id);
                                    UserSession.getInstance(Register.this).writePrefs(UserSession.PREFS_NAME, name);
                                    UserSession.getInstance(Register.this).writePrefs(UserSession.PREFS_PHONE, mobile);
                                    UserSession.getInstance(Register.this).writePrefs(UserSession.PTREF_CURRENCY, currency_2);




                                    Log.e("NAMEEEEE",">>>>>> currecy2    "+currecy2);
                                    Log.e("NAMEEEEE",">>>>>> currency_with_symbol    "+currency_with_symbol);


                                    new UserSession(Register.this).writePrefs(UserSession.CURENCY , currecy2);
                                    new UserSession(Register.this).writePrefs(UserSession.CURENCY_SYMBOL , currency_with_symbol);







                                    dbManager = new DBManager(Register.this);
                                    dbManager.open();

                                    String user_id = new UserSession(Register.this).readPrefs(UserSession.PREFS_USER_ID);
                                    String curre = new UserSession(Register.this).readPrefs(UserSession.CURENCY_SYMBOL);

                                    dbManager.INSERT_CATEGORY(user_id,"Hail",""+R.drawable.hail,"0","car" );
                                    dbManager.INSERT_CATEGORY(user_id,"Parking dent",""+R.drawable.parnking_dent,"1","car" );


                                    dbManager.INSERT_CATEGORY(user_id,"Food",""+R.drawable.food,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Tool",""+R.drawable.tool,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Hotel",""+R.drawable.hotel,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Fuel",""+R.drawable.fuel,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Ticket",""+R.drawable.tickets,"0","expence" );


                                    dbManager.INSERT_CATEGORY(user_id,"Cash",""+R.drawable.cash,"0","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Paycheck",""+R.drawable.paycheck,"0","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Bank Transfer",""+R.drawable.bank_tran,"0","payment" );





                                    Intent intent =  new Intent(Register.this, Home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();


/*
                                    Intent intent =  new Intent(Register.this, HomeTutorial.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();*/
                                }
                                else
                                {
                                    Toast.makeText(Register.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("pin", pin);
                params.put("phone", mobile_no);
                params.put("currency", currency_with_symbol);
                params.put("currency_word", currecy2);
                params.put("token", ""+firebase_token);


                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        userSession.writePrefs(UserSession.MOB,"");
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode ==2000) {
            //  String newText = data.getStringExtra(PUBLIC_STATIC_STRING_IDENTIFIER);
            // TODO Update your TextView.

            currency_with_symbol =  data.getStringExtra("currency");
            tv_currency.setText(currency_with_symbol);







            String[] separated = currency_with_symbol.split(" ");

            currecy2= separated[0];
            symbol = separated[1];

            Log.v("DIKSHA_09","currecy>>>"+currecy2);
            Log.v("DIKSHA_09","symbol>>>"+symbol);





            Log.v("DIKSHA_SHARMA",">>"+currency_with_symbol);

        }
    }


    //language selector

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (mAllowSelectionFiring) {
            int selectedItemPosition = Integer.parseInt(view.getTag(R.string.meta_position).toString().trim());
            String selectedItemTitle = view.getTag(R.string.meta_title).toString().trim();

            Toast.makeText(getApplicationContext(),   selectedItemTitle, Toast.LENGTH_LONG).show();

            Log.v("DIKSHA","LANGUAGE>>"+selectedItemTitle);



            if(selectedItemTitle.equals("Español")){

                updateViews("es");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);

                updateText();




            }
            else if(selectedItemTitle.equals("português")){

                updateViews("es");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"pt");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);

                updateText();

            }
            else if(selectedItemTitle.equals("Italiano")){

                updateViews("it");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);

                updateText();

            }

            else if(selectedItemTitle.equals("Deutsche")){

                updateViews("de");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);

                updateText();

            }

            else if(selectedItemTitle.equals("Français")){

                updateViews("fr");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);



                updateText();
            }

            else if(selectedItemTitle.equals("Türk")){

                updateViews("tr");
                STATUS="0";
                Log.v("DIKSHA","Language>>"+"es");

                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA","get language"+language);


                updateText();

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


        title_createac.setText(R.string.Createac);
        tv_createac.setText(R.string.create);
        tv_terms1.setText(R.string.bycreateaccount);
        tv_terms2.setText(R.string.terms);
        tv_sign1.setText(R.string.alreadyacc);
        tv_sign2.setText(R.string.signin);

        edt_nameac.setHint(R.string.entername);
        tv_country_code.setHint(R.string.country_code);
        edt_mobile.setHint(R.string.mobile_phn_txt);

        edt_emailac.setHint(R.string.enteremail);
        edt_conemailac.setHint(R.string.confirmemail);

        tv_currency.setHint(R.string.select_currency);
        //    itemList.setTooltipText(geR.string.mobile_phn_txt);


        edt_pinac.setHint(R.string.enterpin);
        edt_conpinac.setHint(R.string.enterpin);
    }


}




