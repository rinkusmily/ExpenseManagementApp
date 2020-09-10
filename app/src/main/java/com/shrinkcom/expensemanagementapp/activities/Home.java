package com.shrinkcom.expensemanagementapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.entryCarPojo.EntryCarPojo;

import com.shrinkcom.expensemanagementapp.Pojo.fileListPojo.FileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.GetFileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.TypeCarList;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.AllListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.HomeFileListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.TypeEntryCarAdapter;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CATEGORY_NAME_D;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENCE_IMAGE_VALUE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_PAYMENT_TYPE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.HOME_LIST_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PREFS_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class Home extends AppCompatActivity implements View.OnClickListener {


    ToggleButton toggle_hometut;
    ImageView imv_addhometut;
    EditText edt_search;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    RelativeLayout linear_layout;

    TextView tv_user_name;
    long list_id = 0;
    String list_id2;
    String list_name2;
    AlertDialog.Builder builder;









    DBManager dbManager;

    String user_id ;



    List<String> synk_offline_delegate_list;

    List<String> daligate_name_list;
    List<String> daligate_id_list;

    String c_id,c_name,s_id,s_name;


    String faci_id , dc_id , ds_id , dtoken;
    JSONArray jsonArray_catergory;
    JSONArray jsonArray_home_list;
    JSONArray jsonArray_payment;
    JSONArray jsonArray_transaction;
    JSONArray jsonArray_user;

    JSONObject jsonObject = new JSONObject();

    List<String> flist;


    Calendar c;
    String formattedDate="";
    SimpleDateFormat df;
    RecyclerView file_list_recycle_view;

    View view;

    Context context;
    private HomeFileListAdapter allListAdapter;
    private List<User> all_model_lists;
    String main_date;

    String symbole ,curency2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initview();
        // get currunt date

        user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);
        symbole = new UserSession(Home.this).readPrefs(UserSession.PTREF_CURRENCY);
        curency2 = new UserSession(Home.this).readPrefs(UserSession.PTREF_CURRENCY);

        Log.v("AUTO_FILE_ID",">>"+curency2);
        Log.v("AUTO_FILE_ID",">>"+user_id);
        Log.v("AUTO_FILE_ID",">>"+symbole);

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

    }


    @SuppressLint("WrongViewCast")
    private void initview() {

        dbManager = new DBManager(this);
        dbManager.open();

        toggle_hometut=findViewById(R.id.tb_notify);
        imv_addhometut=findViewById(R.id.imv_addhome);
        edt_search=findViewById(R.id.edt_search);
        linear_layout=findViewById(R.id.linear_layout);
        tv_user_name=findViewById(R.id.tv_user_name);

        file_list_recycle_view=findViewById(R.id.file_list_recycle_view);

        //populateRecyclerView();


        imv_addhometut.setOnClickListener(this);
        toggle_hometut.setOnClickListener(this);


        context = this;
        all_model_lists = new ArrayList<>();


        //GetFileListAPI();

        context  = this;

        user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);

        fetchcategory();
        showOfflineData();

        enableSwipeToDeleteAndUndo();

        String user_name = new UserSession(Home.this).readPrefs(PREFS_NAME);

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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imv_addhome:
                //  Add_List_API();

                list_id = list_id+1;
            //    list_id =   dbManager.INSERT_HOME_LIST(user_id,""+list_id,"HAIL NOTE" ,symbole ,formattedDate ,"1");
                list_id =   dbManager.INSERT_HOME_LIST(user_id,""+list_id,"HAIL NOTE" ,symbole ,formattedDate ,"0");

                Log.v("DDDDDDDDDDDDDDDDDDDD",">>"+list_id);


                fetchcategory();
                showOfflineData();

                String type_txt = getString(R.string.type);
                new UserSession(context).writePrefs(EXPENSE_PAYMENT_TYPE,"");
                new UserSession(context).writePrefs(CATEGORY_NAME_D,type_txt);
                new UserSession(context).writePrefs(EXPENCE_IMAGE_VALUE,"");

                /*UserSession.getInstance(Home.this).writePrefs(UserSession.HOME_LIST_ID, list_id);
                UserSession.getInstance(Home.this).writePrefs(UserSession.PTREF_LIST_NAME, list_name);*/


                // UserSession.getInstance(SigninActivity.this).writeBooleanPrefs(UserSession.PREFS_USERSESSIOIN,true);

                UserSession userSession = new UserSession(Home.this);
                String dnt_show_msg  =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME2);



                String[] separated = (""+symbole).split(" ");

                String currecy1= ""+separated[0];
                String  symbol1 = ""+separated[1];

                Log.v("DIKSHA_09","currecy22>>>"+currecy1);
                Log.v("DIKSHA_09","symbol22>>>"+symbol1);


                if(dnt_show_msg.equals("1")){

                    new UserSession(context).writePrefs(CURENCY_ONSELECT, currecy1);
                    new UserSession(context).writePrefs(SYMBOL_ONSELECT ,symbol1);
                    new UserSession(context).writePrefs(HOME_LIST_ID ,""+list_id2);
                    new UserSession(context).writePrefs(PTREF_LIST_NAME ,list_name2);

                    Intent i = new Intent(Home.this, MainTotalBalanceAcitvity.class);
                    i.putExtra("curr",currecy1);
                    i.putExtra("list_id",list_id2);
                    Log.v("DIKSHA_09","list_id2>>>"+list_id2);
                    i.putExtra("sym",symbol1);
                    startActivity(i);
                    // finish();
                }
                else{





                    new UserSession(context).writePrefs(CURENCY_ONSELECT, currecy1);
                    new UserSession(context).writePrefs(SYMBOL_ONSELECT ,symbol1);
                    new UserSession(context).writePrefs(HOME_LIST_ID ,""+list_id2);
                    new UserSession(context).writePrefs(PTREF_LIST_NAME ,list_name2);

                    Intent i = new Intent(Home.this, MainTotalBalanceAcitvity.class);
                    i.putExtra("curr",currecy1);
                    i.putExtra("list_id",list_id2);
                    Log.v("DIKSHA_09","list_id2>>>"+list_id2);
                    i.putExtra("sym",symbol1);
                    startActivity(i);


                   /* Intent intent2=new Intent(Home.this, TutorialList.class);
                    startActivity(intent2);
                    finish();*/
                }
                break;
            case R.id.tb_notify:

                Intent intent3=new Intent(Home.this,Settings.class);
                startActivity(intent3);
                break;
        }
    }



    private void GetFileListAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_FILE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "ADD_LIST_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                GetFileListPojo getFileListPojo = gson.fromJson(response, GetFileListPojo.class);

                                JSONObject jo=new JSONObject(response);

                                if (jo.getInt("status")==1)
                                {
                                    for(int i =0;i <getFileListPojo.getUser().size();i++){

                                    /* cartModel_1.setTv_date(expenseListPojo.getUser().get(i).getDate());
                                        cartModel_1.setTv_vehicle_name(expenseListPojo.getUser().get(i).getModel());
                                        cartModel_1.setTv_vehicle_number(expenseListPojo.getUser().get(i).getPlateNo());
                                        cartModel_1.setTv_paid_out(expenseListPojo.getUser().get(i).getPrice());
                                        cartModel_1.setPayment_status(""+expenseListPojo.getUser().get(i).getFinishedStatus());*/


                                        all_model_lists.add(getFileListPojo.getUser().get(i));




                                    }

                                    allListAdapter = new HomeFileListAdapter(context, all_model_lists, new RecyclerbuttonClick() {
                                        @Override
                                        public void onItemClick(int position, int type) {

                                            Log.v("SEBY","aaaaaaaaaaaaaa"+position);
                                            list_id = all_model_lists.get(position).getListId();
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
                                else
                                {
                                    Toast.makeText(Home.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);
                String curency = new UserSession(Home.this).readPrefs(UserSession.CURENCY_SYMBOL);
                HashMap<String, String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("list_name", "");
                params.put("currency",curency);
                params.put("date", formattedDate);


                Log.e("DIKSHA", "ADD_LIST_REQUEST"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



/*    private void Add_List_API() {
        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "ADD_LIST_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                FileListPojo fileListPojo = gson.fromJson(response, FileListPojo.class);

                                JSONObject jo=new JSONObject(response);

                                if (jo.getInt("status")==1)
                                {


                                    onRestart();

                                    String list_id = ""+fileListPojo.getUser().get(0).getListId();
                                    String list_name = fileListPojo.getUser().get(0).getListName();
                                    String currency = fileListPojo.getUser().get(0).getCurrency();




                                    UserSession.getInstance(Home.this).writePrefs(UserSession.HOME_LIST_ID, list_id);
                                    UserSession.getInstance(Home.this).writePrefs(UserSession.PTREF_LIST_NAME, list_name);


                                    // UserSession.getInstance(SigninActivity.this).writeBooleanPrefs(UserSession.PREFS_USERSESSIOIN,true);

                                    UserSession userSession = new UserSession(Home.this);
                                    String dnt_show_msg  =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME2);



                                    String[] separated = (""+currency).split(" ");

                                    String currecy1= ""+separated[0];
                                    String  symbol1 = ""+separated[1];

                                    Log.v("DIKSHA_09","currecy22>>>"+currecy1);
                                    Log.v("DIKSHA_09","symbol22>>>"+symbol1);


                                    if(dnt_show_msg.equals("1")){

                                        Intent i = new Intent(Home.this, MainTotalBalanceAcitvity.class);
                                        i.putExtra("curr",currecy1);
                                        i.putExtra("list_id",list_id);
                                        i.putExtra("sym",symbol1);
                                        startActivity(i);
                                        finish();
                                    }

                                    else{
                                        Intent intent2=new Intent(Home.this, TutorialList.class);
                                        startActivity(intent2);
                                        finish();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(Home.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);
                String curency = new UserSession(Home.this).readPrefs(UserSession.CURENCY_SYMBOL);
                String curency2 = new UserSession(Home.this).readPrefs(UserSession.CURENCY);

                Log.e("DIKSHA", "curency_009"+curency);
                Log.e("DIKSHA", "curency_002"+curency2);


                HashMap<String, String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("list_name", "");
                params.put("currency",curency);
                params.put("date", formattedDate);


                Log.e("DIKSHA", "ADD_LIST_REQUEST"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    private void DeleteListApi(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.DELETE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "ADD_LIST_RESPONSE"+response);

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                               // FileListPojo fileListPojo = gson.fromJson(response, FileListPojo.class);

                                JSONObject jo=new JSONObject(response);

                                if (jo.getInt("status")==1)
                                {



                                }
                                else
                                {
                                    Toast.makeText(Home.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {




                HashMap<String, String> params = new HashMap<>();

                Log.e("DIKSHA", "ADD_LIST_REQUEST"+params);

                params.put("list_id", ""+list_id);



                Log.e("DIKSHA", "ADD_LIST_REQUEST"+params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }*/




    @Override
    protected void onResume() {
        super.onResume();



    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(Home.this, Home.class);  //your class
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

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }
    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }




    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {







                builder = new AlertDialog.Builder(context);
                builder.setMessage("").setTitle("");

                builder.setMessage(R.string.are_you_sure_to_delete_this_file)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                //perform action

                                //_____________________________________________________________






                                final int position = viewHolder.getAdapterPosition();
                                final User item = allListAdapter.getData(position);
                                int list_id_4= all_model_lists.get(position).getListId();


                                // DeleteListApi(position);
                                Log.v("SEBY",">>>>"+position);
                                Log.v("SEBY",">>>>"+list_id);
                                Log.v("SEBY",">>>>"+user_id);
                                Log.v("GGGGGGGGGGGGGG",">>>>"+list_id_4);

                                dbManager.DELETE_File_by_user_id(user_id , ""+list_id_4);
                                dbManager.DELETE_FILE_TRANSACTION(user_id , ""+list_id_4);
                                dbManager.DELETE_FILE_PAYMENT(user_id , ""+list_id_4);



                                allListAdapter.removeItem(position);


                                allListAdapter.notifyDataSetChanged();


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




                                //_____________________________________________________________



                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button


                             allListAdapter.notifyDataSetChanged();


                                dialog.cancel();
                        /*Toast.makeText(getContext(), R.string.clicked_no,
                                Toast.LENGTH_SHORT).show();*/
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(R.string.alart);
                // alert.setIcon(R.drawable.logonew);
                alert.show();





            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(file_list_recycle_view);
    }






    // this dialog is use for logout
    private void showDialogMenu() {

    }

    public  void  showOfflineData(){


        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            all_model_lists = new ArrayList<>();

            Cursor cursor = dbManager.FETCH_HOME_LIST(user_id);
            int i=0;

            all_model_lists.clear();

            GetFileListPojo fileListPojo = new GetFileListPojo();

            if(cursor.moveToFirst()) {

                do {


                    String auto_file_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_HOME_LIST_ID));
                    list_id = Long.parseLong(auto_file_id);
                    list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_ID));
                    String file_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_LIST_NAME));
                    String user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_USER_ID));
                    String currency = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CURRUNCY));
                    String date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.HOME_DATE));
                    String backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.BACKUP_SATATUS));
                    //  list_id2 = Integer.parseInt(auto_file_id);
                    list_name2 =file_name;

                    Log.v("AUTO_FILE_ID_2",">>"+list_id2);
                    Log.v("SSSSSSSSSSSSS",">>"+list_id2);
                    Log.v("AUTO_FILE_ID_2",">>"+auto_file_id);
                    Log.v("AUTO_FILE_ID_2",">>"+file_name);
                    Log.v("AUTO_FILE_ID_2",">>"+user_id);
                    Log.v("AUTO_FILE_ID_2",">>"+currency);
                    Log.v("AUTO_FILE_ID_2",">>"+date);
                    Log.v("AUTO_FILE_ID_2",">>"+backup_status);


                    List<User> userlist = new ArrayList<>();
                    User user = new User();
                    user.setListId(Integer.valueOf(list_id2));
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



                allListAdapter = new HomeFileListAdapter(context, all_model_lists, new RecyclerbuttonClick() {
                    @Override
                    public void onItemClick(int position, int type) {

                        Log.v("SEBY","ADAPOER>>>>"+position);
                        Log.v("SEBY","ADAPOER>>>>"+position);

                        list_id = all_model_lists.get(position).getListId();

                        Log.v("SEBY","GGGGGGGGGGGGGG>>>>"+list_id);
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

            Log.v("SEBY","uuuuuuuuuuuuuu>>>>"+e);
        }

    }












    //SYNC_CODE__________________________






  /*  private void getAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
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


                                if (jo.getInt("status")==1)
                                {
                                    dbManager.DELETE_CATEGORY();
                                    dbManager.DELETE_HOME_LIST();
                                    dbManager.DELETE_TOTAL_TRANSACTION();
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

                                        dbManager.INSERT_CATEGORY(user_id,cat_name,image,backup_satatus ,cat_type);

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
                                        String pay_id = ""+fileListPojo.getData().getTransaction().get(i).getPaymentId();


                                        dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id,list_id,pay_id,price ,type ,date);


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


                                        dbManager.INSERT_ALL_PAYMENTS(list_id,user_id,price,date ,c_id,note ,type, creates ,plat_no , model, finish, invoice,paid , icon, comnpany,name );


                                    }
                                    Log.v("DIKSHA",">DATAAAsA   SUCCESS>>>");

                                }
                                else
                                {
                                    Toast.makeText(Home.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            Log.v("DIKSHA",">DATAAAsA>>>"+e);

                        }


                        // TODO: 7/3/2020  call next activity
                       // callLaunchActivity();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // Toast.makeText(SplashScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                       // callLaunchActivity();
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
        final RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }




















*/







/*

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


                    home_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_HOME_LIST_ID));
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

                    category_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_CATEGORY_ID));
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



                    object1.put("payment_id",auto_payment_id);

                    object1.put("price",payment_price);
                    object1.put("date",payment_date);
                    object1.put("category_id",payment_category_id);
                    object1.put("list_id",payment_list_id);
                    object1.put("user_id",user_idd);
                    object1.put("note",paymenrt_note);
                    object1.put("user_type",payment_type);
                    object1.put("created_at",payment_created_at);
                    object1.put("plate_no",payment_plate_no);
                    object1.put("model",pay_model);
                    object1.put("finished_status",finished_status);

                    object1.put("invoice_status",invoice_status);
                    object1.put("paidout_status",paid_out_status);
                    object1.put("icon",payment_icon);
                    object1.put("company",company);
                    object1.put("type",type_name);

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

          //  callLaunchActivity();
        }else {


            getAllData();


            SyncFacilitatorFeedback();

        }


    }

    public void  SyncFacilitatorFeedback() {

        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SYNCK_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YYYYYYYYYYYYYY",">>"+response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();




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
        final RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);







    }
*/


    //_______________________________



    public  void fetchcategory() {

        {


            dbManager = new DBManager(this);
            dbManager.open();

            String user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);

            Log.v("AUTO_FILE_ID22", ">>" + user_id);
            try {


                Cursor cursor = dbManager.FETCH_CATEGORY_LIST_TO_SYNC(user_id);



                if (cursor.moveToFirst()) {

                    do {

                        String cat_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_USER_ID));
                        String cat_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_LIST_ID));
                        String category_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_NAME));
                        String category_image = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CATEGORY_IMAGE));
                        String cat_backup_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.CA_BACKUP_STATUS));
                        String category_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE));


                        Log.v("  SEBY ", "TABLE IS NOT EMPTY>>" );



                        List<com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User> userlist = new ArrayList<>();
                        com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User user = new com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User();
                        user.setCarCategoryId(Integer.parseInt(cat_auto_id));
                        user.setImage(category_image);
                        user.setName(category_name);
                        user.setUser_id(Integer.parseInt(cat_user_id));

                        userlist.add(user);


                    } while (cursor.moveToNext());


                }else{


                    user_id = new UserSession(Home.this).readPrefs(UserSession.PREFS_USER_ID);
                    String curre = new UserSession(Home.this).readPrefs(UserSession.CURENCY_SYMBOL);

                    dbManager.INSERT_CATEGORY(user_id,"Hail",""+R.drawable.hail,"0","car" );
                    dbManager.INSERT_CATEGORY(user_id,"Parking dent",""+R.drawable.parnking_dent,"0","car" );


                    dbManager.INSERT_CATEGORY(user_id,"Food",""+R.drawable.food,"0","expence" );
                    dbManager.INSERT_CATEGORY(user_id,"Tool",""+R.drawable.tool,"0","expence" );
                    dbManager.INSERT_CATEGORY(user_id,"Hotel",""+R.drawable.hotel,"0","expence" );
                    dbManager.INSERT_CATEGORY(user_id,"Fuel",""+R.drawable.fuel,"0","expence" );
                    dbManager.INSERT_CATEGORY(user_id,"Ticket",""+R.drawable.tickets,"0","expence" );


                    dbManager.INSERT_CATEGORY(user_id,"Cash",""+R.drawable.cash,"0","payment" );
                    dbManager.INSERT_CATEGORY(user_id,"Paycheck",""+R.drawable.paycheck,"0","payment" );
                    dbManager.INSERT_CATEGORY(user_id,"Bank Transfer",""+R.drawable.bank_tran,"0","payment" );

                    Log.v("SEBY", "TABLE IS EMPTY INSERT DATA>>>>" );


                }
            } catch (Exception e) {
                e.printStackTrace();

                Log.v("AUTO_FILE_ID22", ">>" + e);


            }

        }
    }









}
