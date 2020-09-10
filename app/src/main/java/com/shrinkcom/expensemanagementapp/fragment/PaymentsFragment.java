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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.expensesLISTPojo.EXPENSELIST;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.PaymentList;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.User;
import com.shrinkcom.expensemanagementapp.R;

import com.shrinkcom.expensemanagementapp.activities.Home;
import com.shrinkcom.expensemanagementapp.activities.HomeTutorial;
import com.shrinkcom.expensemanagementapp.activities.MainTotalBalanceAcitvity;
import com.shrinkcom.expensemanagementapp.activities.SelectDateExportActivity;
import com.shrinkcom.expensemanagementapp.adaptor.ExpenseListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.PaymentListAdapter;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PaymentsFragment extends Fragment {

    private List<User> notifyModels;

    View view;
    RecyclerView cart_recycler_view;
    Context context;
    private PaymentListAdapter cartAdapter;
    String main_date;


    ImageView img_home;
    TextView edt_share;
    String file_type;

    DBManager dbManager;

    String filter_date_txt;

    EditText edt_fastsearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payments, container, false);

        cart_recycler_view=view.findViewById(R.id.rv_payments);
        edt_fastsearch=view.findViewById(R.id.edt_fastsearch);

        img_home = view.findViewById(R.id.img_home);
        edt_share = view.findViewById(R.id.edt_share);


        context = getActivity();


        filter_date_txt = getString(R.string.filter_date);

        //Allnotifylist();
        notifyModels = new ArrayList<User>();
        // staticData();  // it is for demo this will remove after api integration

        //submitRegister();
       // fetchDataOffline();





        main_date = new UserSession(getActivity()).readPrefs(UserSession.MAIN_DATE);
        Log.v("DIKSHA","MAIN DATE>>"+main_date);

        ((MainTotalBalanceAcitvity)getActivity()).setFragmentRefreshListener(new MainTotalBalanceAcitvity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                Log.v("DIKSHA","fragment refrash3");
            }
        });


        edt_fastsearch.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    String text = edt_fastsearch.getText().toString();
                    int textlength = edt_fastsearch.getText().length();

                    //   allListAdapter.filter(text, textlength);


                    filter2(s.toString());
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



        if(main_date.equals(filter_date_txt)){

            fetchDataOffline_all_date();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }else {
            fetchDataOffline();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserSession userSession = new UserSession(getActivity());
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);


               /* Intent i = new Intent(getActivity(), Home.class);

                startActivity(i);
                getActivity().finish();*/


                getActivity().finish();


 /*               if(dnt_show_msg.equals("1")){

                    Intent i = new Intent(getActivity(), Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                else{

                    Intent i = new Intent(getActivity(), HomeTutorial.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }*/


            }
        });




        edt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

                Intent intent = new Intent(getActivity() , SelectDateExportActivity.class);
                intent.putExtra("transaction_type","payment");
                intent.putExtra("list_id",list_id);

                startActivity(intent);


          /*      checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        9000);
*/

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


    void filter2(String text){
        List<User> temp = new ArrayList();
        for(User d: notifyModels){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getType_name().toLowerCase().contains(text.toLowerCase()) || d.getType_name().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());

            }
        }
        //update recyclerview
        cartAdapter.updateList(temp);
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

            notifyModels = new ArrayList<>();

            Log.v("AUTO_FILE_ID2233","main_date>>"+main_date.trim());

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE_TYPE(user_id , list_id , main_date.trim(),"payment");
            int i=0;

            notifyModels.clear();

            PaymentList fileListPojo = new PaymentList();

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
                    String icon = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_ICON));
                    String type_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));

                    Log.v("AUTO_FILE_ID_222",">>"+payment_note);
                    Log.v("AUTO_FILE_ID_222",">>"+payment_id);


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
                    user.setIcon(icon);

                    user.setType_name(type_name);



                    userlist .add(user);

                    fileListPojo.setUser(userlist);

                    notifyModels.add(user);

                } while (cursor.moveToNext());

                i++;

                cartAdapter = new PaymentListAdapter(context, notifyModels);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()) {
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
                cart_recycler_view.setLayoutManager(mLayoutManager);
                cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
                cart_recycler_view.setAdapter(cartAdapter);




            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","xxxxxxxxx>>>>"+e);
        }

    }





    public  void fetchDataOffline_all_date(){


        dbManager = new DBManager(getActivity());
        dbManager.open();

        String  user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            notifyModels = new ArrayList<>();

            Log.v("AUTO_FILE_ID2233","main_date>>"+main_date.trim());

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE_TYPE_ALL_DATE_DATA(user_id , list_id ,"payment");
            int i=0;

            notifyModels.clear();

            PaymentList fileListPojo = new PaymentList();

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
                    String icon = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_ICON));
                    String type_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));

                    Log.v("AUTO_FILE_ID_222",">>"+payment_note);
                    Log.v("AUTO_FILE_ID_222",">>"+payment_id);


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
                    user.setIcon(icon);

                    user.setType_name(type_name);



                    userlist .add(user);

                    fileListPojo.setUser(userlist);

                    notifyModels.add(user);

                } while (cursor.moveToNext());

                i++;

                cartAdapter = new PaymentListAdapter(context, notifyModels);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()) {
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
                cart_recycler_view.setLayoutManager(mLayoutManager);
                cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
                cart_recycler_view.setAdapter(cartAdapter);




            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","xxxxxxxxx>>>>"+e);
        }

    }






}
