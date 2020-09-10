package com.shrinkcom.expensemanagementapp.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.all.All;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.ExpenseListPojo;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.Home;
import com.shrinkcom.expensemanagementapp.activities.HomeTutorial;
import com.shrinkcom.expensemanagementapp.activities.MainTotalBalanceAcitvity;
import com.shrinkcom.expensemanagementapp.activities.SelectDateExportActivity;
import com.shrinkcom.expensemanagementapp.activities.TypesofPayment;
import com.shrinkcom.expensemanagementapp.adaptor.AllListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.TypePaymentAdapter;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AllListFragment extends Fragment {


    View view;
    RecyclerView all_recycler_view;
    Context context;
    private AllListAdapter allListAdapter;
    private List<User> all_model_lists;
    String main_date;
    EditText edit_fast_search;

    String filter_date_txt;

    ImageView img_home;
    String file_type;
    TextView edt_share;

    DBManager dbManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_all_list, container, false);

        all_recycler_view=view.findViewById(R.id.All_recycler_view);
        edit_fast_search=view.findViewById(R.id.edt_fastsearch);

        img_home = view.findViewById(R.id.img_home);
        edt_share = view.findViewById(R.id.edt_share);

        context = getActivity();
        all_model_lists = new ArrayList<User>();
        //AllnotifylistAllnotifylist();

        //staticData();  //it is for demo this will remove after api integration

        main_date = new UserSession(getActivity()).readPrefs(UserSession.MAIN_DATE);
        Log.v("DIKSHA","MAIN DATE>>"+main_date);

        ((MainTotalBalanceAcitvity)getActivity()).setFragmentRefreshListener(new MainTotalBalanceAcitvity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                Log.v("DIKSHA","fragment refrash3");
            }
        });

      //  submitRegister();

        //fetchDataOffline();

        filter_date_txt = getString(R.string.filter_date);

        if(main_date.equals(filter_date_txt)){

            fetchDataOfflineALL_DATE();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }else {
            fetchDataOffline();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }


        edit_fast_search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    String text = edit_fast_search.getText().toString();
                    int textlength = edit_fast_search.getText().length();

                 //   allListAdapter.filter(text, textlength);

                    filter(s.toString());
                    Log.v("DIKSHA","LISTTTT!!!!!!!!!!!!!!!"+s.toString());

                    if (text.equalsIgnoreCase(""))
                    {

                    }
                    else {

                    }
                } catch (Exception e) {
                    Log.v("DIKSHA",">>>>Error>>>>"+e);
                }
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserSession userSession = new UserSession(getActivity());
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);

                getActivity().finish();

           /*     if(dnt_show_msg.equals("1")){

                    Intent i = new Intent(getActivity(), Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                else{

                    Intent i = new Intent(getActivity(), Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }*/


            }
        });




        edt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        9000);*/
                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

               Intent intent = new Intent(getActivity() , SelectDateExportActivity.class);
                intent.putExtra("transaction_type","all");
                intent.putExtra("list_id",list_id);

               startActivity(intent);


            }
        });

        return view;

    }




    public void showSelevtFileDialog(View view) {
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        ImageView img_pdf = (ImageView) mView.findViewById(R.id.img_pdf);
        ImageView img_csv = (ImageView) mView.findViewById(R.id.img_csv);
        ImageView img_close_setting = (ImageView) mView.findViewById(R.id.img_close_setting);
        alert.setView(mView);
        final android.app.AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);


        img_close_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                file_type = "";

                if (!file_type.equals("")) {
                    Export_as_file_API();
                } else {


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
                    Log.e("DIKSHA", "FILRTYPE"+file_type );


                } else {
                    Log.e("DIKSHA", "FILRTYPE"+file_type );
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
                    Log.e("DIKSHA", "FILRTYPE"+file_type );
                } else {
                    Log.e("DIKSHA", "FILRTYPE" + file_type);
                }

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void Export_as_file_API() {

 /*       final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_EXPORT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "EXPORT_RESPONSE" + response);

                        try {
                         /*   if (progressDialog != null) {

                                progressDialog.dismiss();*/

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                Toast.makeText(getActivity(), R.string.downloading, Toast.LENGTH_SHORT).show();


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                // TypeCarList expenseListPojo = gson.fromJson(response, TypeCarList.class);

                                JSONObject jo = new JSONObject(response);

                                String url = jo.optString("link");

                                Log.v("EXPORT_RESPONSE", ">>" + url);


                                AppUtility.downloadFileFromURL(getActivity(), url);





                                //for soenload file

                                //   AppUtility.downloadFileFromURL(getApplicationContext(),jo.optString());

                          //  }

                        } catch (Exception e) {

                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                   //     progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {


            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);
                String date = new UserSession(getActivity()).readPrefs(UserSession.DATE_FOR_FRAG);
                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);



                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("from_date", date);
                params.put("to_date", date);
                params.put("type", file_type);
                params.put("list_id", list_id);

                Log.e("DIKSHA", "EXPORT_REQUSEST" + params);

                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }
/*
    private void staticData(){

        allListAdapter = new AllListAdapter(context, all_model_lists);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

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


        All_model_list cartModel_1 = new All_model_list();
        cartModel_1.setDate("event date");
        cartModel_1.setVehicle_name("Example of car");
        cartModel_1.setVehicle_number("show the plate or VIN number");
        cartModel_1.setPrice("$8500");


        all_model_lists.add(cartModel_1);


        All_model_list cartModel_2 = new All_model_list();
        cartModel_2.setDate("event date");
        cartModel_2.setVehicle_name("Example of car");
        cartModel_2.setVehicle_number("show the plate or VIN number");
        cartModel_2.setPrice("$8500");


        all_model_lists.add(cartModel_2);


        all_recycler_view.setLayoutManager(mLayoutManager);
        all_recycler_view.setItemAnimator(new DefaultItemAnimator());
        all_recycler_view.setAdapter(allListAdapter);



    }*/





    void filter(String text){
        List<User> temp = new ArrayList();
        for(User d: all_model_lists){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text.toLowerCase()) || d.getName().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }else  if(d.getType_name().toLowerCase().contains(text.toLowerCase()) || d.getName().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }

            else  if(d.getModel().toLowerCase().contains(text.toLowerCase()) || d.getModel().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }

            else  if(d.getPlateNo().toLowerCase().contains(text.toLowerCase()) || d.getPlateNo().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }
        }
        //update recyclerview
        allListAdapter.updateList(temp);
    }





    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission},
                    requestCode);


            showSelevtFileDialog(edt_share);

        } else {
            Toast.makeText(getActivity(),
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();

            showSelevtFileDialog(edt_share);

        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == 9000) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(getActivity(),
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == 9000) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(getActivity(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public  void fetchDataOffline(){


        dbManager = new DBManager(getActivity());
        dbManager.open();

        String  user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            all_model_lists = new ArrayList<>();

            Log.v("AUTO_FILE_ID2233","main_date>>"+main_date);

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE(user_id ,list_id,main_date.trim());
            int i=0;

            all_model_lists.clear();

            AllListPojo fileListPojo = new AllListPojo();

            if(cursor.moveToFirst()) {

                do {

                    Log.v("AUTO_FILE_ID223344",">>"+user_id);
                    String payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    String list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    String payment_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    String payment_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String payment_category_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));

                    String payment_note = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_NOTE));
                    String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_TYPE));
                    String payment_careated_at = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYE_CREATED_AT));

                    String pay_plate_no = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PLATE_NO));
                    String pay_model = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_MODEL));
                    String finished_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FINISHED_STATUS));
                    String invoiced_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.INVOICE_STATUS));
                    String paid_out_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAID_OUT_STATUS));
                    String pay_icon = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_ICON));
                    String company = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COMPANY));
                    String type_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));


                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+list_id2);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_user_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_price);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_date);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_category_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_note);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_type);

                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_careated_at);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_plate_no);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_model);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+finished_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+invoiced_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+paid_out_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_icon);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+company);



                    List<User> userlist = new ArrayList<>();
                     User user = new User();
                     user.setPaymentId(Integer.parseInt(payment_id));
                     user.setUserId(Integer.parseInt(payment_user_id));
                     user.setList_id(Integer.parseInt(list_id2));
                     user.setPrice(payment_price);
                     user.setDate(payment_date);
                     user.setCategoryId(Integer.parseInt(payment_category_id));
                     user.setNote(payment_note);
                     user.setType(payment_type);
                     user.setCreatedAt(payment_careated_at);
                     user.setPlateNo(pay_plate_no);
                     user.setName(pay_plate_no);
                     user.setFinishedStatus(Integer.parseInt(finished_status));
                     user.setInvoiceStatus(Integer.parseInt(invoiced_status));
                     user.setPaidoutStatus(Integer.parseInt(paid_out_status));
                     user.setModel(pay_model);
                     user.setIcon(pay_icon);
                     user.setCompany(company);
                     user.setType_name(type_name);




                    userlist .add(user);

                    fileListPojo.setUser(userlist);

                    all_model_lists.add(user);

                } while (cursor.moveToNext());

                i++;

                allListAdapter = new AllListAdapter(context, all_model_lists);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
                    @Override

                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

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
                all_recycler_view.setLayoutManager(mLayoutManager);
                all_recycler_view.setItemAnimator(new DefaultItemAnimator());
                all_recycler_view.setAdapter(allListAdapter);




            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","yuyuyuyuyuy>>>>"+e);
        }

    }


    public  void fetchDataOfflineALL_DATE(){


        dbManager = new DBManager(getActivity());
        dbManager.open();

        String  user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            all_model_lists = new ArrayList<>();

            Log.v("AUTO_FILE_ID2233","main_date>>"+main_date);

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE_ALL_DATE(user_id ,list_id);
            int i=0;

            all_model_lists.clear();

            AllListPojo fileListPojo = new AllListPojo();

            if(cursor.moveToFirst()) {

                do {

                    Log.v("AUTO_FILE_ID223344",">>"+user_id);
                    String payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    String list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    String payment_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    String payment_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String payment_category_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));

                    String payment_note = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_NOTE));
                    String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_TYPE));
                    String payment_careated_at = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYE_CREATED_AT));

                    String pay_plate_no = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PLATE_NO));
                    String pay_model = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_MODEL));
                    String finished_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FINISHED_STATUS));
                    String invoiced_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.INVOICE_STATUS));
                    String paid_out_status = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAID_OUT_STATUS));
                    String pay_icon = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_ICON));
                    String company = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COMPANY));
                    String type_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));


                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+list_id2);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_user_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_price);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_date);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_category_id);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_note);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_type);

                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+payment_careated_at);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_plate_no);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_model);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+finished_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+invoiced_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+paid_out_status);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+pay_icon);
                    Log.v("DIKSHA","FATCH_PAYMENT_DATA>>"+company);



                    List<User> userlist = new ArrayList<>();
                    User user = new User();
                    user.setPaymentId(Integer.parseInt(payment_id));
                    user.setUserId(Integer.parseInt(payment_user_id));
                    user.setList_id(Integer.parseInt(list_id2));
                    user.setPrice(payment_price);
                    user.setDate(payment_date);
                    user.setCategoryId(Integer.parseInt(payment_category_id));
                    user.setNote(payment_note);
                    user.setType(payment_type);
                    user.setCreatedAt(payment_careated_at);
                    user.setPlateNo(pay_plate_no);
                    user.setName(pay_plate_no);
                    user.setFinishedStatus(Integer.parseInt(finished_status));
                    user.setInvoiceStatus(Integer.parseInt(invoiced_status));
                    user.setPaidoutStatus(Integer.parseInt(paid_out_status));
                    user.setModel(pay_model);
                    user.setIcon(pay_icon);
                    user.setCompany(company);
                    user.setType_name(type_name);




                    userlist .add(user);

                    fileListPojo.setUser(userlist);

                    all_model_lists.add(user);

                } while (cursor.moveToNext());

                i++;

                allListAdapter = new AllListAdapter(context, all_model_lists);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
                    @Override

                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

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
                all_recycler_view.setLayoutManager(mLayoutManager);
                all_recycler_view.setItemAnimator(new DefaultItemAnimator());
                all_recycler_view.setAdapter(allListAdapter);




            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","yuyuyuyuyuy>>>>"+e);
        }

    }
}
