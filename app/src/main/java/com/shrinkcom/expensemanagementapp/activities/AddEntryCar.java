package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.shrinkcom.expensemanagementapp.Pojo.entryCarPojo.EntryCarPojo;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.StaticVariablesStorage;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.DATE_FOR_FRAG;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.MAIN_DATE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class AddEntryCar extends AppCompatActivity implements View.OnClickListener{

    TextView tv_payment,tv_expences,tv_okpay , date_tv , tv_type , tv_amount_changer, date_automatic;
    ImageView img_close_payment_scrn , img_right;
    EditText edt_amount , edt_model , edt_plate_vin , edt_notes ,edt_company;

    LinearLayout type_layout;
    ImageView tv_type_of_entry_car;
    String category_id;
    String image_name;

    Calendar c;
    String formattedDate;
    SimpleDateFormat df;

    String currency , symbol , user_id , list_id;
    TextView tv_entrycar1;
    final Calendar myCalendar = Calendar.getInstance();

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry_car);

        currency = new UserSession(AddEntryCar.this).readPrefs(CURENCY_ONSELECT);
        symbol = new UserSession(AddEntryCar.this).readPrefs(SYMBOL_ONSELECT);
        user_id = new UserSession(AddEntryCar.this).readPrefs(UserSession.PREFS_USER_ID);
        list_id = new UserSession(AddEntryCar.this).readPrefs(UserSession.HOME_LIST_ID);



        intiview();
        String  type_txt = getString(R.string.type);


        tv_type.setText(type_txt);

        tv_entrycar1 = findViewById(R.id.tv_entrycar1);

        String name_file = new UserSession(getApplicationContext()).readPrefs(PTREF_LIST_NAME);

        Log.v("DIKSHA", "FILER_NAME>>" + name_file);

        if (!name_file.equals("") && !name_file.equals(null)) {

            tv_entrycar1.setText(name_file);
        } else {
            tv_entrycar1.setText("HAIL NOTE");
        }

        //___________________________________________
        // get currunt date




        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText("Date "+formattedDate);

        Log.v("DIKSHA_DATE","DATE"+formattedDate);

       // new UserSession(this).writePrefs(DATE_FOR_FRAG , date_tv.getText().toString());


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param = sdf2.format(c.getTime());


        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE, date_param);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                //viewPager.getAdapter().notifyDataSetChanged();
            }

        };

        date_automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO Auto-generated method stub
                new DatePickerDialog(AddEntryCar.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }

        });

        tv_type.setText(StaticVariablesStorage.CATECORY_TYPE);
        image_name =StaticVariablesStorage.CATEGORY_IMAGE;

        //_________________________________________

    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param = sdf2.format(myCalendar.getTime());


        // createViewPager(viewPager);
        /*  if(getFragmentRefreshListener()!=null){
            getFragmentRefreshListener().onRefresh();
        }
*/
        date_tv.setText("Date  " + sdf.format(myCalendar.getTime()));



        //   viewPager.getAdapter().notifyDataSetChanged();


    }

    private void intiview() {


        tv_payment=findViewById(R.id.tv_payment);
        tv_expences=findViewById(R.id.tv_Expenses);
        tv_okpay=findViewById(R.id.tv_okpay);
        img_right=findViewById(R.id.img_right);
        img_close_payment_scrn=findViewById(R.id.img_close_payment_scrn);
        date_tv=findViewById(R.id.date_tv);
        tv_type_of_entry_car=findViewById(R.id.tv_type_of_entry_car);
        tv_type=findViewById(R.id.tv_type);
        tv_amount_changer=findViewById(R.id.tv_amount_changer);
        date_automatic=findViewById(R.id.date_automatic);

        edt_amount=findViewById(R.id.edt_amount);
        edt_model=findViewById(R.id.edt_model);
        edt_plate_vin=findViewById(R.id.edt_plate_vin);
        edt_notes=findViewById(R.id.edt_notes);
        type_layout=findViewById(R.id.ll_entryone);
        edt_company=findViewById(R.id.edt_company);


        tv_payment.setOnClickListener(this);
        type_layout.setOnClickListener(this);
        tv_expences.setOnClickListener(this);
        tv_okpay.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_close_payment_scrn.setOnClickListener(this);
        tv_type_of_entry_car.setOnClickListener(this);
        tv_amount_changer.setOnClickListener(this);
       // type_layout.setOnClickListener(this);

        tv_amount_changer.setText(symbol + "0");


        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText(formattedDate);

        edt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tv_amount_changer.setText(symbol+edt_amount.getText().toString());

            }
        });

        tv_type.setText(StaticVariablesStorage.CATECORY_TYPE);





    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_payment:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent1=new Intent(AddEntryCar.this, AddPayment.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
                // overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
                finish();
                break;



            case R.id.tv_Expenses:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent2=new Intent(AddEntryCar.this, AddExpenses.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
                // overridePendingTransition(R.anim.rotation,0);
                finish();
                break;


            case R.id.tv_okpay:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                validate();
                break;


            case R.id.img_close_payment_scrn:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent4=new Intent(AddEntryCar.this, MainTotalBalanceAcitvity.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.tv_type_of_entry_car:
                Intent intent5=new Intent(AddEntryCar.this, TypesofEntryCar.class);
                startActivity(intent5);


                break;

            case R.id.img_right:
              /*  Intent intent5=new Intent(AddEntryCar.this, MainTotalBalanceAcitvity.class);
                startActivity(intent5);
                finish();*/

                validate();
                break;


        }


    }


    /*validation*/
    private void validate(){

        String  type_txt = getString(R.string.type);


        String amount  = edt_amount.getText().toString().trim();
        String model = edt_model.getText().toString().trim();
        String plate_no = edt_plate_vin.getText().toString().trim();
        String type = tv_type.getText().toString().trim();
        String note = edt_notes.getText().toString().trim();
        String company = edt_company.getText().toString().trim();

        if (amount.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.please_enter_amount,Toast.LENGTH_SHORT).show();

            return;
        } else  if (model.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.please_enter_model,Toast.LENGTH_SHORT).show();

            return;
        }
        else  if (plate_no.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.please_enter_plate_or_vin_no,Toast.LENGTH_SHORT).show();

            return;
        }

       /* else  if (company.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.please_enter_company,Toast.LENGTH_SHORT).show();

            return;
        }*/
        else  if (type.equals(type_txt)) {

            Toast.makeText(getApplicationContext(),R.string.please_select_type,Toast.LENGTH_SHORT).show();

            return;
        }



        else {

            insertData();

           // addEntryCarAPI();
           // dbManager.insertAdd_Car(us);


        }
    }

    private void addEntryCarAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(AddEntryCar.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ENTRY_CAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "ADD_ENTRY_CAR_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                EntryCarPojo entryCarPojo = gson.fromJson(response, EntryCarPojo.class);

                                JSONObject jo=new JSONObject(response);

                                if (jo.getInt("status")==1)
                                {
                                    //Token();
                                    Intent intent3=new Intent(AddEntryCar.this, MainTotalBalanceAcitvity.class);

                                    startActivity(intent3);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(AddEntryCar.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(AddEntryCar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                String[] separated = (""+date_tv.getText().toString()).split("Date");

                String date_txt= ""+separated[0];
                String  date = ""+separated[1];



               String user_id = new UserSession(AddEntryCar.this).readPrefs(UserSession.PREFS_USER_ID);
               String list_id = new UserSession(AddEntryCar.this).readPrefs(UserSession.HOME_LIST_ID);
                HashMap<String, String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("note", edt_notes.getText().toString());
                params.put("price", edt_amount.getText().toString());
                params.put("plate_no", edt_plate_vin.getText().toString());
                params.put("model", edt_model.getText().toString());
                params.put("category_id",category_id);
                params.put("list_id",list_id);
                params.put("date", date);

                Log.e("DIKSHA", "ADD_ENTRY_CAR_REQUEST"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(AddEntryCar.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String a = new UserSession(getApplicationContext()).readPrefs(UserSession.CAREGORY_IDD);
        String name2 = new UserSession(getApplicationContext()).readPrefs(UserSession.CATEGORY_NAME_D);
         image_name = new UserSession(getApplicationContext()).readPrefs(UserSession.EXPENCE_IMAGE_VALUE);
        category_id =a;

        Log.v("Joy", "languwedsghfage "+image_name);
        Log.v("Joy", "languwedsghfage "+name2);
        Log.v("Joy", "languwedsghfage "+a);

        String  type_txt = getString(R.string.type);


        if(!name2.equals("")){
            tv_type.setText(name2);

        }else {

            if (!a.equals(type_txt) && !a.equals(null)) {

                tv_type.setText(name2);

            } else {


                tv_type.setText(StaticVariablesStorage.CATECORY_TYPE);
                tv_type.setText(name2);
                tv_type.setText(type_txt);
            }
        }
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }




    public void insertData(){


        dbManager = new DBManager(AddEntryCar.this);
        dbManager.open();

        String[] separated = (""+date_tv.getText().toString()).split("Date");

         String price = edt_amount.getText().toString().trim();
         String  date = ""+separated[1];
         String category_idd = ""+category_id;
         String note = edt_notes.getText().toString().trim();
         String type = "car";
         String created_at = formattedDate;
         String plate_no = edt_plate_vin.getText().toString().trim();
         String model = edt_model.getText().toString().trim();
         String finished = "0";
         String invoiced = "0";
         String paidout = "0";
         String icon = image_name;
         String company = edt_company.getText().toString().trim();;
         String type_name = tv_type.getText().toString().trim();

      // date = date.replace("/","s");

        dbManager.INSERT_ALL_PAYMENTS(list_id,user_id, price ,date.trim() ,category_idd ,note,type , created_at, plate_no , model , finished , invoiced , paidout , icon , company , type_name);

        //Token();
        Intent intent3=new Intent(AddEntryCar.this, MainTotalBalanceAcitvity.class);

        startActivity(intent3);
        finish();

    }

}
