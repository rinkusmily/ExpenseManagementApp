package com.shrinkcom.expensemanagementapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.fileListPojo.FileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.GetFileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.AllListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.HomeFileListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.HomeFileListAdapter2;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.NetworkUtil;
import com.shrinkcom.expensemanagementapp.utils.SwipeToDeleteCallback;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PREFS_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PREFS_USER_ID;

public class HomeList2 extends AppCompatActivity implements View.OnClickListener {
   // ToggleButton toggle_hometut;
  //  ImageView imv_addhometut;
    EditText edt_search;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    RelativeLayout linear_layout;

    TextView tv_user_name;

    Calendar c;
    String formattedDate="";
    SimpleDateFormat df;
    RecyclerView file_list_recycle_view;

    View view;

    Context context;
    private HomeFileListAdapter2 allListAdapter;
    private List<User> all_model_lists;
    String main_date;


    JSONArray jsonArray_catergory;
    JSONArray jsonArray_home_list;
    JSONArray jsonArray_payment;
    JSONArray jsonArray_transaction;
    JSONArray jsonArray_user;

    JSONObject jsonObject = new JSONObject();

    List<String> flist;



    DBManager dbManager;

    String user_id ;


    TextView tv_to_date ,tv_from_date ,tv_to_day , tv_weekly , tv_montholy;
    Button btn_export_data;
    ToggleButton tb_notify;

    String list_id;

    String file_type;

    LinearLayout ll_vids_month_option;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;


    Calendar c2;

    String formattedDate2;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    String to_date , from_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        initview();
        // get currunt date
        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

    }

    @SuppressLint("WrongViewCast")
    private void initview() {

        /*toggle_hometut=findViewById(R.id.tb_notify);
        imv_addhometut=findViewById(R.id.imv_addhome);*/

        edt_search=findViewById(R.id.edt_search);
        linear_layout=findViewById(R.id.linear_layout);
        tv_user_name=findViewById(R.id.tv_user_name);

        file_list_recycle_view=findViewById(R.id.file_list_recycle_view);
        //populateRecyclerView();

        context = this;
        all_model_lists = new ArrayList<>();

        /* imv_addhometut.setOnClickListener(this);
        toggle_hometut.setOnClickListener(this);*/



        // enableSwipeToDeleteAndUndo();

        String user_name = new UserSession(HomeList2.this).readPrefs(PREFS_NAME);
      user_id = new UserSession(HomeList2.this).readPrefs(PREFS_USER_ID);

        Log.v("DIKSHA","USERNAME "+user_name);


         tv_user_name.setText(user_name);

          edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
                Log.v("DIKSHA","LISTTTT!!!!!!!!!!!!!!!"+editable.toString());



            }
        });



        showOfflineData();




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

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.tb_notify:

                Intent intent3=new Intent(HomeList2.this,Settings.class);
                startActivity(intent3);
                break;

        }
    }



    @Override
    protected void onResume() {
        super.onResume();



    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(HomeList2.this, HomeList2.class);  //your class
        startActivity(i);
        finish();

    }

   void filter(String text){
        List<User> temp = new ArrayList();
        for(User d: all_model_lists){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getListName().toLowerCase().contains(text.toLowerCase()) || d.getListName().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }
        }
        //update recyclerview
        allListAdapter.updateList(temp);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


    private void Export_as_file_API(){

        final ProgressDialog progressDialog = new ProgressDialog(HomeList2.this);
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

                                Toast.makeText(getApplicationContext(),R.string.downloading,Toast.LENGTH_SHORT).show();


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                // TypeCarList expenseListPojo = gson.fromJson(response, TypeCarList.class);

                                JSONObject jo=new JSONObject(response);

                                String url = jo.optString("link");

                                Log.v("EXPORT_RESPONSE",">>"+url);


                                AppUtility.downloadFileFromURL2(context,url);


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
                        Log.e("ERRRRRR",">>>"+error);
                        Toast.makeText(HomeList2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {



            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(HomeList2.this).readPrefs(UserSession.PREFS_USER_ID);
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("from_date",formattedDate);
                params.put("to_date", formattedDate);
                params.put("type", file_type);
                params.put("list_id", list_id);
                params.put("transaction_type", "all");
                Log.e("DIKSHA", "EXPORT_REQUSEST"+params);
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(HomeList2.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param =sdf2.format(myCalendar.getTime());


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

        String date_param =sdf2.format(myCalendar.getTime());


        // createViewPager(viewPager);
        /*  if(getFragmentRefreshListener()!=null){
            getFragmentRefreshListener().onRefresh();
        }
*/
        tv_to_date.setText(""+sdf.format(myCalendar.getTime()));

        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE,date_param);
        //   viewPager.getAdapter().notifyDataSetChanged();



    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final User item = allListAdapter.getData(position);

                allListAdapter.removeItem(position);


               // DeleteListApi(position);

                Log.v("SEBY",">>>>"+position);

                int list_id = all_model_lists.get(position).getListId();
                Snackbar snackbar = Snackbar
                        .make(linear_layout, "Item was removed from the list.", Snackbar.LENGTH_LONG);


                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        allListAdapter.restoreItem(item, position);
                        file_list_recycle_view.scrollToPosition(position);


                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(file_list_recycle_view);
    }



    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(HomeList2.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(HomeList2.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(HomeList2.this,
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
                Toast.makeText(HomeList2.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(HomeList2.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeList2.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(HomeList2.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    public void showSelevtFileDialog(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(HomeList2.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog,null);

        ImageView img_pdf = (ImageView)mView.findViewById(R.id.img_pdf);
        ImageView img_csv = (ImageView)mView.findViewById(R.id.img_csv);
        ImageView img_close_setting = (ImageView)mView.findViewById(R.id.img_close_setting);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);


        img_close_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                file_type ="";

                if(!file_type.equals("")){
                    Export_as_file_API();
                }else {



                }

                alertDialog.dismiss();

            }
        });


        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                file_type ="pdf";

                if(!file_type.equals("")){
                    Export_as_file_API();
                }else {

                }

                alertDialog.dismiss();



            }
        });
        img_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                file_type ="csv";


                if(!file_type.equals("")){
                    Export_as_file_API();
                }else {

                }

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }




    public  void  showOfflineData(){

        dbManager = new DBManager(this);
        dbManager.open();

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            all_model_lists = new ArrayList<>();

            Cursor cursor = dbManager.FETCH_HOME_LIST(user_id);
            int i=0;

            all_model_lists.clear();

            GetFileListPojo fileListPojo = new GetFileListPojo();

            if(cursor.moveToFirst()) {

                do {


                    String auto_file_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_ID));
                    String file_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_NAME));
                    String user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_USER_ID));
                    String currency = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CURRUNCY));
                    String date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_DATE));
                    String backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.BACKUP_SATATUS));
                    String list_id2 = ""+Integer.parseInt(auto_file_id);
                    String    list_name2 =file_name;

                    Log.v("AUTO_FILE_ID_2",">>"+auto_file_id);
                    Log.v("AUTO_FILE_ID_2",">>"+file_name);
                    Log.v("AUTO_FILE_ID_2",">>"+user_id);
                    Log.v("AUTO_FILE_ID_2",">>"+currency);
                    Log.v("AUTO_FILE_ID_2",">>"+date);
                    Log.v("AUTO_FILE_ID_2",">>"+backup_status);


                    List<User> userlist = new ArrayList<>();
                    User user = new User();
                    user.setListId(Integer.parseInt(auto_file_id));
                    user.setListName(file_name);
                    user.setCurrency(currency);
                    user.setDate(date);
                    user.setListName(file_name);
                    user.setUserId(Integer.parseInt(user_id));


                    userlist .add(user);
                    fileListPojo.setUser(userlist);


                    all_model_lists.add(user);

                } while (cursor.moveToNext());

                i++;



                allListAdapter = new HomeFileListAdapter2(context, all_model_lists, new RecyclerbuttonClick() {
                    @Override
                    public void onItemClick(int position, int type) {

                        Log.v("SEBY","ADAPOER>>>>"+position);
                        list_id = ""+all_model_lists.get(position).getListId();

                        checkPermission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                STORAGE_PERMISSION_CODE);

                        //showSelevtFileDialog();

                        syncDelegate();
                    }
                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context){
                    @Override

                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {

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
                file_list_recycle_view.setLayoutManager(mLayoutManager);
                file_list_recycle_view.setItemAnimator(new DefaultItemAnimator());
                file_list_recycle_view.setAdapter(allListAdapter);


            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","ADAPOER>>>>"+e);
        }

    }







    //Sync_data_export data _________________________





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
                    object.put("backup_status",backup_status);
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
                    object.put("backup_status", category_backup_status);

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



                    object1.put("payment_id",auto_payment_id);

                    object1.put("price",payment_price);
                    object1.put("date",payment_date);
                    object1.put("category_id",payment_category_id);
                    object1.put("list_id",payment_list_id);
                    object1.put("user_id",user_idd);
                    object1.put("note",paymenrt_note);
                    object1.put("created_at",payment_created_at);
                    object1.put("plate_no",payment_plate_no);
                    object1.put("model",pay_model);
                    object1.put("finished_status",finished_status);
                    object1.put("name",type_name);
                    object1.put("invoice_status",invoice_status);
                    object1.put("paidout_status",paid_out_status);
                    object1.put("icon",payment_icon);
                    object1.put("company",company);
                    object1.put("type",payment_type);

                    jsonArray_payment.put(object1);

                    Log.e("SYNKKKK","SYNC PAYMENT>> size "+object1);



                }while (cursoragain.moveToNext());
            } } catch (JSONException e) {

            Log.e("SYNKKKK","SYNC HOME PAYMENT>> size "+e);
            e.printStackTrace();
        }


        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";


        }else {

            String userid = UserSession.getInstance(HomeList2.this).readPrefs(UserSession.PREFS_USER_ID);

            if (userid.equals("")){

            }else {
                SyncFacilitatorFeedback();
            }
        }


    }
    public void  SyncFacilitatorFeedback() {

     /*   final ProgressDialog progressDialog = new ProgressDialog(HomeList2.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SYNCK_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YYYYYYYYYYYYYY",">>"+response);
                      //  getAllData();

                        showSelevtFileDialog();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    progressDialog.dismiss();

                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(HomeList2.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        final RequestQueue requestQueue = Volley.newRequestQueue(HomeList2.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);







    }

    //==================================================


















}
