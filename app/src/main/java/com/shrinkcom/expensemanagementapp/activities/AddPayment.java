package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;


import static com.shrinkcom.expensemanagementapp.utils.UserSession.HOME_LIST_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.MAIN_DATE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class AddPayment extends AppCompatActivity implements View.OnClickListener {

    TextView tv_entrycarpay,tv_expensepay,tv_okpay , date_tv ,tv_type , tv_amount_changer , date_automatic;
    ImageView img_close_payment_scrn , img_right , tv_type_of_payments;
    TextView tv_expences;
    String category_id;
    final Calendar myCalendar = Calendar.getInstance();
    Calendar c;
    String formattedDate;
    SimpleDateFormat df;
    TextView tv_entrycar1;
    EditText edt_amount , edt_notes;
    String currency , symbol;
    String   user_id,list_id_2;
    DBManager dbManager;
    String image_name;
//    LinearLayout ll_entryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tv_entrycar1 = findViewById(R.id.tv_entrycar1);
        intiview();

      String  type_txt = getString(R.string.type);
        tv_type.setText(type_txt);

         currency = new UserSession(AddPayment.this).readPrefs(CURENCY_ONSELECT);
       symbol = new UserSession(AddPayment.this).readPrefs(SYMBOL_ONSELECT);
        list_id_2 = new UserSession(AddPayment.this).readPrefs(HOME_LIST_ID);
        String   name1 = new UserSession(AddPayment.this).readPrefs(SYMBOL_ONSELECT);
        user_id = new UserSession(AddPayment.this).readPrefs(UserSession.PREFS_USER_ID);

           Intent intent = getIntent();


        String type_image = intent.getStringExtra("type_image");


        String name_file = new UserSession(getApplicationContext()).readPrefs(PTREF_LIST_NAME);

        Log.v("DIKSHA", "FILER_NAME>>" + name_file);

        if (!name_file.equals("") && !name_file.equals(null)) {

            tv_entrycar1.setText(name_file);
        } else {
            tv_entrycar1.setText("HAIL NOTE");
        }

    }

    private void intiview() {


        tv_entrycarpay=findViewById(R.id.tv_entrycarpay);
        tv_expensepay=findViewById(R.id.tv_expensepay);
        tv_okpay=findViewById(R.id.tv_okpay);
        img_close_payment_scrn=findViewById(R.id.img_close_payment_scrn);
        img_right=findViewById(R.id.img_right);
        tv_type=findViewById(R.id.tv_type);
        date_automatic=findViewById(R.id.date_automatic);
      //  ll_entryone=findViewById(R.id.ll_entryone);
        tv_type_of_payments=findViewById(R.id.tv_type_of_payments);
        date_tv=findViewById(R.id.date_tv);
        date_tv=findViewById(R.id.date_tv);
        edt_notes=findViewById(R.id.edt_notes);
        edt_amount=findViewById(R.id.edt_amount);
        tv_amount_changer=findViewById(R.id.tv_amount_changer);

        tv_entrycarpay.setOnClickListener(this);
        tv_expensepay.setOnClickListener(this);
        tv_okpay.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_close_payment_scrn.setOnClickListener(this);
      //  ll_entryone.setOnClickListener(this);
        tv_type_of_payments.setOnClickListener(this);
        tv_amount_changer.setOnClickListener(this);



        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText("Date "+formattedDate);

        symbol = new UserSession(AddPayment.this).readPrefs(SYMBOL_ONSELECT);

        tv_amount_changer.setText(symbol + "0");

        edt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tv_amount_changer.setText(symbol+" "+edt_amount.getText().toString());

            }
        });




        //___________________________________________



        // get currunt date

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText("Date "+formattedDate);

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
                new DatePickerDialog(AddPayment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }

        });






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





    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_entrycarpay:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent1=new Intent(AddPayment.this, AddEntryCar.class);
                startActivity(intent1);
               overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
                finish();
                break;
            case R.id.tv_expensepay:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent2=new Intent(AddPayment.this, AddExpenses.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
               // overridePendingTransition(R.anim.rotation,0);
                finish();
                break;
            case R.id.tv_okpay:
                validate();

                break;


            case R.id.img_close_payment_scrn:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent4=new Intent(AddPayment.this, MainTotalBalanceAcitvity.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.img_right:
              validate();
                break;

         /*   case R.id.ll_entryone:
                Intent intent6=new Intent(AddPayment.this, PymentTypeActivity.class);
                startActivity(intent6);
                finish();
                break;*/

            case R.id.tv_type_of_payments:
                Intent intent7=new Intent(AddPayment.this, TypesofPayment.class);
                startActivity(intent7);
              //  finish();
                break;
            }

    }


    /*validation*/
    private void validate(){
        String  type_txt = getString(R.string.type);


        String amount  = edt_amount.getText().toString().trim();
        String type = tv_type.getText().toString().trim();
        String note = edt_notes.getText().toString().trim();

        if (amount.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.please_enter_amount,Toast.LENGTH_SHORT).show();

            return;
        }

        else  if (type.equals(type_txt)) {

            Toast.makeText(getApplicationContext(),R.string.please_select_type,Toast.LENGTH_SHORT).show();

            return;
        }




        else {
            //addPaymentApi();
            insertData();

        }
    }


    private void addPaymentApi() {
/*        final ProgressDialog progressDialog = new ProgressDialog(AddPayment.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_PAYMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "ADD_PAYMENT_RESPONSE"+response);



                        try {
                          /*  if (progressDialog != null) {
                                progressDialog.dismiss();*/


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();


                            //    EntryCarPojo entryCarPojo = gson.fromJson(response, EntryCarPojo.class);
                              JSONObject jo=new JSONObject(response);

                                if (jo.getInt("status")==1)
                                {
                                    //Token();
                                   new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                                   new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");

                                    Intent intent3=new Intent(AddPayment.this, MainTotalBalanceAcitvity.class);

                                    startActivity(intent3);
                                    finish();
                                }
                                else

                                {
                                    Toast.makeText(AddPayment.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                          //  }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  progressDialog.dismiss();

                        Toast.makeText(AddPayment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                String[] separated = (""+date_tv.getText().toString()).split("Date");
                String date_txt= ""+separated[0];
                String  date = ""+separated[1];

                if(category_id.equals(null)){

                    category_id="";

                }

                String user_id = new UserSession(AddPayment.this).readPrefs(UserSession.PREFS_USER_ID);

                HashMap<String, String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("note", edt_notes.getText().toString());
                params.put("price", edt_amount.getText().toString());
                params.put("category_id",category_id);
                params.put("list_id",list_id_2);

                params.put("date", date);

                Log.e("DIKSHA", "ADD_EXPENSES_REQUEST"+params);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(AddPayment.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();




    String a = new UserSession(getApplicationContext()).readPrefs(UserSession.EXPENSE_PAYMENT_TYPE);
        String name = new UserSession(getApplicationContext()).readPrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME);
        image_name = new UserSession(getApplicationContext()).readPrefs(UserSession.EXPENCE_IMAGE_VALUE);
        category_id =a;
        if(!a.equals("") && !a.equals(null)){


            try
            {


                File f=new File("your file name");
    //id is some like R.drawable.b_image
                if(name.equals("check")){


                    @SuppressLint("ResourceType") InputStream inputStream = getResources().openRawResource(R.drawable.ic_check);
                    OutputStream out=new FileOutputStream(f);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=inputStream.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    inputStream.close();

                }else{
                    @SuppressLint("ResourceType") InputStream inputStream = getResources().openRawResource(R.drawable.bank_tran);

                    OutputStream out=new FileOutputStream(f);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=inputStream.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    inputStream.close();
                }


            }
            catch (IOException e){}


            tv_type.setText(name);
        }
        else {
            String  type_txt = getString(R.string.type);
            tv_type.setText(type_txt);
        }
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }



    public void insertData(){



        dbManager = new DBManager(AddPayment.this);
        dbManager.open();

        String[] separated = (""+date_tv.getText().toString()).split("Date");

        String price = edt_amount.getText().toString().trim();
        String  date = ""+separated[1];
        String category_idd = ""+category_id;
        String note = edt_notes.getText().toString().trim();
        String type = "payment";
        String created_at = formattedDate;

        String finished = "0";
        String invoiced = "0";
        String paidout = "0";
        String icon = image_name;

        String type_name = tv_type.getText().toString().trim();

        dbManager.INSERT_ALL_PAYMENTS(list_id_2,user_id, price ,date.trim() ,category_idd ,note,type , created_at, "0" , "0" , "0" , "0" , "0" , icon,"", type_name);

        //Token();
        Intent intent3=new Intent(AddPayment.this, MainTotalBalanceAcitvity.class);

        startActivity(intent3);
        finish();

    }

}
