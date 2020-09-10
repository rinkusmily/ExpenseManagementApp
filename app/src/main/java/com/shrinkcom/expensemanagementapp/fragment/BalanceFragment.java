package com.shrinkcom.expensemanagementapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.PaymentList;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Total;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Totalamount;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.Home;
import com.shrinkcom.expensemanagementapp.activities.HomeTutorial;
import com.shrinkcom.expensemanagementapp.activities.MainTotalBalanceAcitvity;
import com.shrinkcom.expensemanagementapp.activities.SelectDateExportActivity;
import com.shrinkcom.expensemanagementapp.adaptor.PaymentListAdapter;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.StaticVariablesStorage;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;


public class BalanceFragment extends Fragment {




    String currency, symbol;

    private List<PaymentList> notifyModels;
    TextView tv_total_car , tv_total_exp ,tv_no_of_repair_car, tv_final_score_exp , tv_total_payments;

    EditText edit_fast_search;

    ImageView img_home;
    String file_type;
    TextView edt_share;

    DBManager dbManager;


    View view;



       int payment = 0;
        int expence =0;
        int taking = 0;
        int total = 0;
        int no_of_car = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_balance, container, false);

        tv_total_car= view.findViewById(R.id.tv_total_car);
        tv_total_exp= view.findViewById(R.id.tv_total_exp);
        tv_final_score_exp= view.findViewById(R.id.tv_final_score_exp);
        tv_total_payments= view.findViewById(R.id.tv_total_payments);
        tv_no_of_repair_car= view.findViewById(R.id.tv_no_of_repair_car);



        img_home = view.findViewById(R.id.img_home);
        edt_share = view.findViewById(R.id.edt_share);

        currency = new UserSession(getActivity()).readPrefs(CURENCY_ONSELECT);
        symbol = new UserSession(getActivity()).readPrefs(SYMBOL_ONSELECT);


        UserSession userSession = new UserSession(getActivity());
        String list_id =  userSession.readPrefs(userSession.HOME_LIST_ID);

         //TotalApi();





        total =  taking - expence;

        if (taking==0) {

            tv_total_car.setText(symbol + 0);
        } else {
            tv_total_car.setText(symbol + taking);
        }


        if (payment == 0) {

            tv_total_payments.setText(symbol + 0);
        } else {
            tv_total_payments.setText(symbol + payment);
        }


        if (expence == 0) {

            tv_total_exp.setText(symbol + 0);
        } else {
            tv_total_exp.setText(symbol + expence);
        }


        if (total == 0) {

            tv_final_score_exp.setText(symbol + 0);
        } else {
            tv_final_score_exp.setText(symbol + total);
        }

        if (no_of_car == 0) {

            tv_no_of_repair_car.setText("x" + 0);
        } else {
            tv_no_of_repair_car.setText("x" + no_of_car);
        }



        //_____________________________________________________________________


        fetchDataOfflineCAR();


            dbManager = new DBManager(getActivity());
            dbManager.open();

            String user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);

            Log.v("AUTO_FILE_ID22", ">>" + user_id);
            try {


                Log.v("AUTO_FILE_ID2233", ">>" + user_id);


                Cursor cursor = dbManager.getTransactuionTotal(user_id, list_id);
                int i = 0;


                PaymentList fileListPojo = new PaymentList();

                if (cursor.moveToFirst()) {

                    do {
                        Log.v("AUTO_FILE_ID223344", ">>" + user_id);
                        String trans_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_TANSCATION_ID));
                        String tra_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_LIST_ID));
                        String tra_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_USER_ID));
                        String trans_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PRICE));
                        String tran_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_TYPE));


                        if (tran_type.equals("income")) {

                            taking = Integer.parseInt(trans_price);
                        } else if (tran_type.equals("debit")) {

                            expence = Integer.parseInt(trans_price);

                        } else if (tran_type.equals("credit")) {

                            payment =  Integer.parseInt(trans_price);

                        }


                    } while (cursor.moveToNext());

                    i++;




                    total =  taking - expence;

                    if (taking==0) {

                        tv_total_car.setText(symbol + 0);
                    } else {
                        tv_total_car.setText(symbol + taking);
                    }


                    if (payment == 0) {

                        tv_total_payments.setText(symbol + 0);
                    } else {
                        tv_total_payments.setText(symbol + payment);
                    }


                    if (expence == 0) {

                        tv_total_exp.setText(symbol + 0);
                    } else {
                        tv_total_exp.setText(symbol + expence);
                    }


                    if (total == 0) {

                        tv_final_score_exp.setText(symbol + 0);
                    } else {
                        tv_final_score_exp.setText(symbol + total);
                    }

                    if (no_of_car == 0) {

                        tv_no_of_repair_car.setText("x" + 0);
                    } else {
                        tv_no_of_repair_car.setText("x" + no_of_car);
                    }



                }
            } catch (Exception e) {
                e.printStackTrace();

                Log.v("SEBY", "qqqqqqqqwwwwwwww>>>>" + e);
            }









        //_____________________________________________________________________












         img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserSession userSession = new UserSession(getActivity());
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);

                getActivity().finish();

              /*  Intent i = new Intent(getActivity(), Home.class);
           //     i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/

              /*  if(dnt_show_msg.equals("1")){

                    Intent i = new Intent(getActivity(), Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                else{

                    Intent i = new Intent(getActivity(), Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
*/

            }
        });





        edt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

                Intent intent = new Intent(getActivity() , SelectDateExportActivity.class);
                intent.putExtra("transaction_type","all");
                intent.putExtra("list_id",list_id);

                startActivity(intent);


               /* checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        9000);
*/

            }
        });


        return view;
    }














    public  void fetchDataOfflineCAR() {


        dbManager = new DBManager(getActivity());
        dbManager.open();

        String user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);
        String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

        Log.v("AUTO_FILE_ID22", ">>" + user_id);
        try {


            Log.v("AUTO_FILE_ID2233", ">>" + user_id);


            Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id, list_id, "car");
            int i = 0;


            PaymentList fileListPojo = new PaymentList();

            if (cursor.moveToFirst()) {

                do {
                    Log.v("AUTO_FILE_ID223344", ">>" + user_id);
                    String payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    String list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    String payment_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));


                    i++;

                } while (cursor.moveToNext());


                no_of_car = i;


            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY", "ADAPOER>>>>" + e);
        }


    }

        /* private void TotalApi() {

      *//*  final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*//*

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.TOTAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("DIKSHA", "TOTAL_API_RESPONCE" + response);

                        try {
                        *//*    if (progressDialog != null) {

                                progressDialog.dismiss();*//*

                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            Totalamount expenseListPojo = gson.fromJson(response, Totalamount.class);

                            JSONObject jo = new JSONObject(response);
                            if (jo.getInt("status") == 1) {

                               *//* tv_total_car.setText("" + expenseListPojo.getTaking().get(0).getTotal());
                                tv_total_exp.setText("" + expenseListPojo.getExpences().get(0).getTotal());
                                tv_final_score_exp.setText("" +expenseListPojo.getTotal().get(0).getTotal());
                                tv_total_payments.setText("" + expenseListPojo.getPayment().get(0).getTotal());
                                tv_no_of_repair_car.setText("*" + expenseListPojo.getTotalRepairedCar());
*//*


                                if (expenseListPojo.getTaking().get(0).getTotal() == (null)) {

                                    tv_total_car.setText(symbol + 0);
                                } else {
                                    tv_total_car.setText(symbol + expenseListPojo.getTaking().get(0).getTotal());
                                }


                                if (expenseListPojo.getPayment().get(0).getTotal() == (null)) {

                                    tv_total_payments.setText(symbol + 0);
                                } else {
                                    tv_total_payments.setText(symbol + expenseListPojo.getPayment().get(0).getTotal());
                                }


                                if (expenseListPojo.getPayment().get(0).getTotal() == (null)) {

                                    tv_total_exp.setText(symbol + 0);
                                } else {
                                    tv_total_exp.setText(symbol + expenseListPojo.getExpences().get(0).getTotal());
                                }


                                if (expenseListPojo.getTotal().get(0).getTotal() == (null)) {

                                    tv_final_score_exp.setText(symbol + 0);
                                } else {
                                    tv_final_score_exp.setText(symbol + expenseListPojo.getTotal().get(0).getTotal());
                                }

                                if (expenseListPojo.getTotalRepairedCar() == (null)) {

                                    tv_no_of_repair_car.setText("*" + 0);
                                } else {
                                    tv_no_of_repair_car.setText("*" + expenseListPojo.getTotalRepairedCar());
                                }


                            } else {

                                //    Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            //  }
                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));
                            Log.v("EROOR", ">>" + e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

                String user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("list_id", list_id);


                Log.e("DIKSHA", "ADD_PAYMENT_REQUEST" + params);
                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }*/

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



}
