package com.shrinkcom.expensemanagementapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.NetworkUtil;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.MAIN_DATE;

public class SelectDateExportActivity extends AppCompatActivity {

    TextView tv_to_date ,tv_from_date ,tv_to_day , tv_weekly , tv_montholy;
    Button btn_export_data;
    ToggleButton tb_notify;

    String list_id;

    String file_type;

    LinearLayout ll_vids_month_option;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    Context context;
    Calendar c , c2;
    String formattedDate;
    String formattedDate2;
    SimpleDateFormat df;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    String to_date , from_date;



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


    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private static String file_url;

    String transaction_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_export);

        tv_to_date = findViewById(R.id.tv_to_date);
        tv_from_date = findViewById(R.id.tv_from_date);
        btn_export_data = findViewById(R.id.btn_export_data);
        tb_notify = findViewById(R.id.tb_notify);
        tv_to_day = findViewById(R.id.tv_to_day);
        tv_weekly = findViewById(R.id.tv_weekly);
        tv_montholy = findViewById(R.id.tv_monthaly);
        ll_vids_month_option = findViewById(R.id.ll_vids_month_option);
        context=this;





        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE);


        user_id = new UserSession(SelectDateExportActivity.this).readPrefs(UserSession.PREFS_USER_ID);
        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());


        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date daysBeforeDate = cal.getTime();



        c2 = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate2 = df.format(daysBeforeDate);
        Intent intent = getIntent();

        list_id =intent.getStringExtra("list_id");



        Intent intent1 = getIntent();

        transaction_type = intent.getStringExtra("transaction_type");


        syncDelegate();



        tv_to_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                to_date =formattedDate;

                from_date=formattedDate;
                ll_vids_month_option.setVisibility(View.GONE);


                if(to_date.equals("")){

                    Toast.makeText(getApplicationContext(),"Please select from date",Toast.LENGTH_SHORT).show();

                }else if(from_date.equals("")){

                    Toast.makeText(getApplicationContext(),"Please select to date",Toast.LENGTH_SHORT).show();

                }else {
                   // Export_as_file_API();

                    showSelevtFileDialog(tv_to_day);
                }



            }
        });

        tv_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ll_vids_month_option.setVisibility(View.GONE);

             //   tv_to_day = from_date

                to_date =formattedDate;

                from_date=formattedDate2;

              //  Export_as_file_API();
                showSelevtFileDialog(tv_weekly);


            }
        });

        tv_montholy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_vids_month_option.setVisibility(View.VISIBLE);

            }
        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
                //viewPager.getAdapter().notifyDataSetChanged();
            }

        };

        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO Auto-generated method stub
                    new DatePickerDialog(SelectDateExportActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();


                       /*   to_date = tv_to_date.getText().toString();
                          from_date = tv_from_date.getText().toString();*/

            }
        });

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



        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // TODO Auto-generated method stub
                new DatePickerDialog(SelectDateExportActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        btn_export_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String to_date =tv_from_date.getText().toString();
                String from_date =tv_to_date.getText().toString();

               if(to_date.equals("")){

                   Toast.makeText(getApplicationContext(),"Please select from date",Toast.LENGTH_SHORT).show();

               }else if(from_date.equals("")){

                   Toast.makeText(getApplicationContext(),"Please select to date",Toast.LENGTH_SHORT).show();

               }else {
                  // Export_as_file_API();

                   showSelevtFileDialog(btn_export_data);
               }


            }
        });
        tb_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }


    private void updateLabel() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param =sdf2.format(myCalendar.getTime());

        from_date = sdf2.format(myCalendar.getTime());


        // createViewPager(viewPager);
        /*  if(getFragmentRefreshListener()!=null){
            getFragmentRefreshListener().onRefresh();
        }
*/

        tv_from_date.setText(""+sdf.format(myCalendar.getTime()));
        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE,date_param);
        //   viewPager.getAdapter().notifyDataSetChanged();

    }
    private void updateLabel2() {


        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param =sdf2.format(myCalendar2.getTime());


        // createViewPager(viewPager);
        /*  if(getFragmentRefreshListener()!=null){
            getFragmentRefreshListener().onRefresh();
        }
*/
        tv_to_date.setText(""+sdf.format(myCalendar2.getTime()));

        to_date = ""+sdf.format(myCalendar2.getTime());



        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE,date_param);
        //   viewPager.getAdapter().notifyDataSetChanged();



    }
    private void Export_as_file_API(){

        final ProgressDialog progressDialog = new ProgressDialog(SelectDateExportActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_EXPORT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        Log.e("DIKSHA", "EXPORT_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {

                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                               Toast.makeText(getApplicationContext(), R.string.downloading,Toast.LENGTH_SHORT).show();


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                               // TypeCarList expenseListPojo = gson.fromJson(response, TypeCarList.class);

                                JSONObject jo=new JSONObject(response);

                                String url = jo.optString("link");

                                Log.v("EXPORT_RESPONSE",">>"+url);


                                AppUtility.downloadFileFromURL(context,url);

                            //    openFolder();
                                //for soenload file

                             //   AppUtility.downloadFileFromURL(getApplicationContext(),jo.optString());

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

                        Toast.makeText(SelectDateExportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {



            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(SelectDateExportActivity.this).readPrefs(UserSession.PREFS_USER_ID);
                String list_id = new UserSession(SelectDateExportActivity.this).readPrefs(UserSession.HOME_LIST_ID);
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("from_date",from_date);
                params.put("to_date", to_date);
                params.put("type", file_type);
                params.put("list_id", list_id);
                params.put("transaction_type", transaction_type);
                Log.e("DIKSHA", "EXPORT_REQUSEST"+params);
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(SelectDateExportActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }



    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(SelectDateExportActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(SelectDateExportActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(SelectDateExportActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SelectDateExportActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(SelectDateExportActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SelectDateExportActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(SelectDateExportActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public void showSelevtFileDialog(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(SelectDateExportActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        ImageView img_pdf = (ImageView) mView.findViewById(R.id.img_pdf);
        ImageView img_csv = (ImageView) mView.findViewById(R.id.img_csv);
        ImageView img_close_setting = (ImageView) mView.findViewById(R.id.img_close_setting);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);


        img_close_setting.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                file_type = "";

                if(!file_type.equals("")) {
                    Export_as_file_API();
                }

                else {

                }

                alertDialog.dismiss();
            }
        });


        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                file_type = "pdf";

                if (!file_type.equals("")) {
                    Export_as_file_API();


                } else {

                }

                alertDialog.dismiss();


            }
        });
        img_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                file_type = "csv";


                if (!file_type.equals("")) {
                    Export_as_file_API();
                } else {

                }

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void openFolder() {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  File.separator + "Hail Note" + File.separator);
        intent.setDataAndType(uri, "text/pdf/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));
        Log.v("DIKSHAAA","AAAA>>"+uri);





    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }




    /*---------------------------------------------------------------*/



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

            String userid = UserSession.getInstance(SelectDateExportActivity.this).readPrefs(UserSession.PREFS_USER_ID);

            if (userid.equals("")){


            }else {
                SyncFacilitatorFeedback();


            }
        }


    }
    public void  SyncFacilitatorFeedback() {
/*
        final ProgressDialog progressDialog = new ProgressDialog(SelectDateExportActivity.this);
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
                       // progressDialog.dismiss();

                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(SelectDateExportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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

                    Log.v("DATA_SYNCCC","DATA SYNC SUCCESSFULLYYY"+params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(SelectDateExportActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);







    }
    private void getAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(SelectDateExportActivity.this);
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

                                JSONObject jo=new JSONObject(response);

                                Log.v("DIKSHAAAAA","jo.getInt  >>>"+jo.getInt("status"));
                                if (jo.getInt("status")==1)
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

                                        Log.v("DIKSHA","INSERT_CATEGORY  ");
                                    }

                                    for(int i =0; i< fileListPojo.getData().getList().size();i++){

                                        String user_id = ""+fileListPojo.getData().getList().get(i).getUserId();
                                        String list_name = ""+fileListPojo.getData().getList().get(i).getListName();
                                        String currency = ""+fileListPojo.getData().getList().get(i).getCurrency();
                                        String date = ""+fileListPojo.getData().getList().get(i).getDate();
                                        String backup_status = ""+fileListPojo.getData().getList().get(i).getBackupStatus();
                                        String list_id = ""+fileListPojo.getData().getList().get(i).getListId();


                                        dbManager.INSERT_HOME_LIST(user_id,list_id,list_name,currency ,date ,backup_status);

                                        Log.v("DIKSHA","INSERT_HOME_LIST  ");
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
                                        Log.v("DIKSHA","INSERT_MAIN_TOTAL_TANSACTION  ");

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

                                        Log.v("DIKSHA","INSERT_ALL_PAYMENTS  ");
                                    }
                                    Log.v("DIKSHA",">DATAAAsA   SUCCESS>>>");

                                }
                                else
                                {
                                    Toast.makeText(SelectDateExportActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));
                            e.printStackTrace();
                            Log.v("DIKSHA",">DATAAAsA>>>"+e);
                            Log.v("DIKSHA",">DIVYAAAAA>>>"+e);

                        }


                        // TODO: 7/3/2020  call next activity


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(SelectDateExportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        final RequestQueue requestQueue = Volley.newRequestQueue(SelectDateExportActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }




    //______________________________________________________________________________________________



    /*---------------------------------------------------------------*/





}
