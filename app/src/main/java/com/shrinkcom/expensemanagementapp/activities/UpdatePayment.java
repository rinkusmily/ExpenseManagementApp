package com.shrinkcom.expensemanagementapp.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
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
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.HOME_LIST_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.MAIN_DATE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class UpdatePayment extends AppCompatActivity implements View.OnClickListener {

    TextView tv_entrycarpay,tv_expensepay,tv_okpay , tv_entrycar1, date_tv ,tv_type , tv_amount_changer , date_automatic;
    ImageView img_close_payment_scrn , img_right , tv_type_of_payments;
    TextView tv_expences;
    String category_id , cat_name;

    final Calendar myCalendar = Calendar.getInstance();
    Calendar c;

    String formattedDate;
    SimpleDateFormat df;

    EditText edt_amount , edt_notes;
    String currency , symbol;
    String   list_id_2;

    String payment_id2 , plate_no , amount,model , note;

    DBManager dbManager;
    String image_name;
    String date="";

    ImageView img_delete_data ;

//    LinearLayout ll_entryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();

        note = intent.getStringExtra("note");
        amount = intent.getStringExtra("amount");
        payment_id2 = ""+intent.getIntExtra("car_id", 0);
        category_id = ""+intent.getIntExtra("category_id", 0);
        date = intent.getStringExtra("date");
        image_name = intent.getStringExtra("cat_image");
        cat_name = intent.getStringExtra("category_name");


        Log.v("SEBY","image_name >>>"+image_name);
        Log.v("SEBY"," category name >>>"+cat_name);
        Log.v("SEBY"," category_id >>>"+category_id);
        Log.v("SEBY"," date >>>"+date);

        intiview();


        String  type_txt = getString(R.string.type);
        tv_type.setText(type_txt);

        String currency = new UserSession(UpdatePayment.this).readPrefs(CURENCY_ONSELECT);
        String   symbol = new UserSession(UpdatePayment.this).readPrefs(SYMBOL_ONSELECT);
           list_id_2 = new UserSession(UpdatePayment.this).readPrefs(HOME_LIST_ID);
        String   name1 = new UserSession(UpdatePayment.this).readPrefs(SYMBOL_ONSELECT);

        tv_amount_changer.setText(symbol+amount);

        tv_entrycar1 = findViewById(R.id.tv_entrycar1);

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
        date_automatic=findViewById(R.id.date_automatic);
        img_right=findViewById(R.id.img_right);
        tv_type=findViewById(R.id.tv_type);
        img_delete_data=findViewById(R.id.img_delete_data);
      //  ll_entryone=findViewById(R.id.ll_entryone);
        tv_type_of_payments=findViewById(R.id.tv_type_of_payments);
        date_tv=findViewById(R.id.date_tv);
        date_tv=findViewById(R.id.date_tv);
        edt_notes=findViewById(R.id.edt_notes);
        edt_amount=findViewById(R.id.edt_amount);
        tv_amount_changer=findViewById(R.id.tv_amount_changer);

        //tv_entrycarpay.setOnClickListener(this);
       // tv_expensepay.setOnClickListener(this);

        tv_okpay.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_close_payment_scrn.setOnClickListener(this);
      //  ll_entryone.setOnClickListener(this);

        tv_type_of_payments.setOnClickListener(this);
        tv_amount_changer.setOnClickListener(this);

        img_delete_data.setVisibility(View.VISIBLE);
        img_delete_data.setOnClickListener(this);

        tv_type.setText(cat_name);
        edt_amount.setText(amount);
        edt_notes.setText(note);

        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText("Date "+formattedDate);





        Date date_new = new Date();

        String dtStart =date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date_new = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(date_new);

        date_tv.setText("Date "+formattedDate);



        // get currunt date

       /* c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        date_tv.setText("Date "+formattedDate);*/

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


        edt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tv_amount_changer.setText("$"+edt_amount.getText().toString());

            }
        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

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
                new DatePickerDialog(UpdatePayment.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }

        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_entrycarpay:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent1=new Intent(UpdatePayment.this, AddEntryCar.class);
                startActivity(intent1);
               overridePendingTransition(R.anim.rotation,R.anim.rotate_in);
                finish();

                break;
            case R.id.tv_expensepay:
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,"");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,"");
                Intent intent2=new Intent(UpdatePayment.this, AddExpenses.class);
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
                Intent intent4=new Intent(UpdatePayment.this, MainTotalBalanceAcitvity.class);
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
                Intent intent7=new Intent(UpdatePayment.this, TypesofPayment.class);
                startActivity(intent7);
              //  finish();
                break;



            case R.id.img_delete_data:


                showDialogMenu();

                break;
            }

    }

    AlertDialog.Builder builder;

    // this dialog is use for logout
    private void showDialogMenu() {

        dbManager = new DBManager(UpdatePayment.this);
        dbManager.open();
        builder = new AlertDialog.Builder(UpdatePayment.this);
        builder.setMessage("").setTitle("");

        builder.setMessage(R.string.are_your_sure_want_delete)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String user_id = new UserSession(UpdatePayment.this).readPrefs(UserSession.PREFS_USER_ID);
                        dbManager.DELETE_PAYEMNT_ROW(user_id, payment_id2);

                        //   dbManager.DELETE_PAYMENT_ROW(user_id,payment_id2);

                        Toast.makeText(UpdatePayment.this , R.string.delete_msg,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdatePayment.this , MainTotalBalanceAcitvity.class);
                        startActivity(intent);


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
        alert.setTitle(R.string.delete);
        // alert.setIcon(R.drawable.logonew);
        alert.show();
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
           // addPaymentApi();

            Update_();

        }
    }


    private void addPaymentApi() {
/*        final ProgressDialog progressDialog = new ProgressDialog(AddPayment.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.UPDATE_PAYMRNT,
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

                                    finish();
                                }
                                else

                                {
                                    Toast.makeText(UpdatePayment.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(UpdatePayment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                if(category_id.equals(null)){

                    category_id="";

                }

                String user_id = new UserSession(UpdatePayment.this).readPrefs(UserSession.PREFS_USER_ID);

                HashMap<String, String> params = new HashMap<>();

                params.put("payment_id", payment_id2);
                params.put("note", edt_notes.getText().toString());
                params.put("price", edt_amount.getText().toString());
                params.put("category_id",category_id);
                params.put("list_id",list_id_2);

                params.put("date", formattedDate);

                Log.e("DIKSHA", "ADD_EXPENSES_REQUEST"+params);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(UpdatePayment.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


  /*  @Override
    protected void onResume() {
        super.onResume();
        String  type_txt = getString(R.string.type);



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


            tv_type.setText(cat_name);
        }
        else {
            tv_type.setText(type_txt);
        }
    }*/







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



    public    void Update_(){

      //  public  int  UPDATE_PAYMENT(String user_id,String payment_id,String category_id, String price ,String date ,String model , String plate  , String compny_name ,   String type , String note ,String category_name ,String category_img ,String finished_status,String invoice_status ,String paidout_status){

     String   list_id_2 = new UserSession(UpdatePayment.this).readPrefs(HOME_LIST_ID);
        String   name1 = new UserSession(UpdatePayment.this).readPrefs(SYMBOL_ONSELECT);
      String  user_id = new UserSession(UpdatePayment.this).readPrefs(UserSession.PREFS_USER_ID);

            dbManager = new DBManager(UpdatePayment.this);
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

            // date = date.replace("/","s");

            dbManager.UPDATE_PAYMENT(user_id, payment_id2 ,list_id_2, category_id, price ,date.trim() ,model ,plate_no,"" ,"payment" , note, tv_type.getText().toString() , image_name , finished , invoiced , paidout );

            //Token();
            Intent intent3=new Intent(UpdatePayment.this, MainTotalBalanceAcitvity.class);

            startActivity(intent3);
            finish();


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
        date_tv.setText("Date  "+ sdf.format(myCalendar.getTime()));



        //   viewPager.getAdapter().notifyDataSetChanged();


    }


}
