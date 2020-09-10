
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
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.ToggleButton;

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
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.carlistPojo.CARLISTPOJO;
import com.shrinkcom.expensemanagementapp.Pojo.carlistPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.Home;
import com.shrinkcom.expensemanagementapp.activities.HomeTutorial;
import com.shrinkcom.expensemanagementapp.activities.MainTotalBalanceAcitvity;
import com.shrinkcom.expensemanagementapp.activities.SelectDateExportActivity;
import com.shrinkcom.expensemanagementapp.activities.UpdateCarList;
import com.shrinkcom.expensemanagementapp.activities.UpdateEntryCar;
import com.shrinkcom.expensemanagementapp.adaptor.AllListAdapter;
import com.shrinkcom.expensemanagementapp.adaptor.CartListAdapter;
import com.shrinkcom.expensemanagementapp.model.CartModel;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CarListFragment extends Fragment {

    View view;
    RecyclerView cart_recycler_view;
    Context context;
    private CartListAdapter cartAdapter;
    private List<CartModel> cartModelList;


    View view_filter_date;

    View view_select_date;

    DBManager dbManager;
     CARLISTPOJO expenseListPojo;
    String file_type;

    String filter_date_txt;

    ToggleButton tggl_finnished , tggl_incoced, tggle_paid_out;
    int finnished_count=0 , invoiced_count=0 , paid_out_count=0;

    private List<User> notifyModels;

    String main_date;

    String param_car_id , param_type , param_status;

    EditText edt_fastsearch;

    ImageView img_home;
    TextView edt_share;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_car_list, container, false);

        cart_recycler_view=view.findViewById(R.id.cart_recycler_view);

        tggl_finnished = view.findViewById(R.id.tggl_finnished);
        tggl_incoced = view.findViewById(R.id.tggl_incoced);
        tggle_paid_out = view.findViewById(R.id.tggle_paid_out);
        edt_fastsearch = view.findViewById(R.id.edt_fastsearch);

        img_home = view.findViewById(R.id.img_home);
        edt_share = view.findViewById(R.id.edt_share);

        context = getActivity();
        cartModelList = new ArrayList<>();
        //Allnotifylist();
        notifyModels = new ArrayList<>();
       // staticData();  // it is for demo this will remove after api integration


        filter_date_txt = getString(R.string.filter_date);



        main_date = new UserSession(getActivity()).readPrefs(UserSession.MAIN_DATE);
        Log.v("DIKSHA","MAIN DATE>>"+main_date);





        ((MainTotalBalanceAcitvity)getActivity()).setFragmentRefreshListener(new MainTotalBalanceAcitvity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                Log.v("DIKSHA","fragment refrash 2");
            }
        });


        filter();

        //  fetchDataOffline();

        if(main_date.equals(filter_date_txt)){

            fetchDataOfflineAll();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }else {
            fetchDataOffline();
            Log.v("ALL_DATE_SHOW",">>"+main_date);
        }


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

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserSession userSession = new UserSession(getActivity());
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);
              /*  Intent i = new Intent(getActivity(), Home.class);

                startActivity(i);
                getActivity().finish();*/

                getActivity().finish();

     /*           if(dnt_show_msg.equals("1")){

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

                String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

                Intent intent = new Intent(getActivity() , SelectDateExportActivity.class);
                intent.putExtra("transaction_type","car");
                intent.putExtra("list_id",list_id);


                startActivity(intent);


                /*checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        9000);*/


            }
        });


        tggl_finnished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finnished_count==0){
                    finnished_count=1;

                    Log.v("DIKSHA","finnished_count>>"+finnished_count);

                    filtwer();

                }
                else {

                    finnished_count=0;
                    Log.v("DIKSHA","finnished_count>>"+finnished_count);
                   // CarListFilterAPI();
                //   fetchDataOffline();


                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }else {
                        fetchDataOffline();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }
                    filtwer();

                }

            }
        });


        tggl_incoced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invoiced_count==0){
                    invoiced_count=1;

                    Log.v("DIKSHA","invoiced_count>>"+invoiced_count);
                    filtwer();

                }else {
                    invoiced_count=0;

                  // fetchDataOffline();

                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }else {
                        fetchDataOffline();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }

                    Log.v("DIKSHA","invoiced_count>>"+invoiced_count);
                    filtwer();

                }

            }
        });

        tggle_paid_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paid_out_count==0){
                    paid_out_count=1;

                    Log.v("DIKSHA","invoiced_count>>"+paid_out_count);

                    filtwer();

                }else {

                    paid_out_count=0;
                // fetchDataOffline();


                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }else {
                        fetchDataOffline();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }

                    filtwer();

                    Log.v("DIKSHA","invoiced_count>>"+paid_out_count);

                }

            }
        });

        return  view;
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


    public  void filter(){




    }









/*

    public void filtwer(){

        List<User> listM = new ArrayList<>();

        listM.clear();



        for (int i = 0; i < notifyModels.size(); i++) {





            Log.v("COMDITIOMSSS", "finished >>" + notifyModels.get(i).getFinishedStatus());
            Log.v("COMDITIOMSSS", "finished >>" + finnished_count);
            Log.v("COMDITIOMSSS", "invoiced >>" + notifyModels.get(i).getInvoiceStatus());
            Log.v("COMDITIOMSSS", "invoiced >>" + invoiced_count);
            Log.v("COMDITIOMSSS", "paid_out >>" + notifyModels.get(i).getPaidoutStatus());
            Log.v("COMDITIOMSSS", "paid_out >>" + paid_out_count);


            if (finnished_count == 1 && invoiced_count == 1 && paid_out_count == 1) {


                if ((notifyModels.get(i).getFinishedStatus() == 1) && (notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getPaidoutStatus() == 1)) {

                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", "f==in==p>>" + paid_out_count);

                    listM.add(user);


                }


            } else if (invoiced_count == 1 && finnished_count == 1 &&paid_out_count == 0) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getFinishedStatus() == 1)&& (notifyModels.get(i).getPaidoutStatus() == 0)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " f==in>>" + paid_out_count);

                    listM.add(user);


                }
            }*/
/* else if (paid_out_count == 1 && finnished_count == 1) {


                        if ((notifyModels.get(i).getPaidoutStatus() == 1) && (notifyModels.get(i).getFinishedStatus() == 1)) {


                            User user = new User();

                            user.setCarId(notifyModels.get(i).getCarId());
                            user.setDate(notifyModels.get(i).getDate());
                            user.setModel(notifyModels.get(i).getModel());
                            user.setNote(notifyModels.get(i).getNote());
                            user.setPlateNo(notifyModels.get(i).getPlateNo());
                            user.setPrice(notifyModels.get(i).getPrice());
                            user.setIcon(notifyModels.get(i).getIcon());
                            user.setImage(notifyModels.get(i).getImage());
                            user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                            user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                            user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                            Log.v("3RDCONDITION", "f==p>>" + paid_out_count);

                            listM.add(user);


                        }
                    }*//*

            else if ( finnished_count == 1&& invoiced_count == 0 && paid_out_count == 1) {


                if ((notifyModels.get(i).getFinishedStatus() == 1) && (notifyModels.get(i).getInvoiceStatus() == 0)&&(notifyModels.get(i).getPaidoutStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", "f==p>>" + paid_out_count);

                    listM.add(user);


                }
            }
            else if (invoiced_count == 1 && paid_out_count == 1 && finnished_count == 0) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getPaidoutStatus() == 1 )&&  (notifyModels.get(i).getFinishedStatus() == 0 )) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }




            else if (finnished_count == 1 && invoiced_count ==  0 && paid_out_count ==0) {


                if ((notifyModels.get(i).getFinishedStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }


            else if (finnished_count == 1 && invoiced_count ==  0 && paid_out_count ==1) {


                if ((notifyModels.get(i).getFinishedStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }


            else if (finnished_count == 1) {


                if ((notifyModels.get(i).getFinishedStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }


            else if (invoiced_count == 1  ) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }




            else if (paid_out_count == 1 ) {


                if ((notifyModels.get(i).getPaidoutStatus() == 1)) {


                    User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }




        }




        if(listM.size()>0){
            adapterCalling(listM , context);
        }else {

            if(notifyModels.size()>0){

                adapterCalling(notifyModels , context);
            }else {

                //Toast.makeText(getActivity())
            }
        }
    }

*/





    public void filtwer(){


        int status_for_3 =0;

        List<User> listM = new ArrayList<>();

        listM.clear();



        for (int i = 0; i < notifyModels.size(); i++) {

            User user = new User();

            Log.v("COMDITIOMSSS", "finished >>" + notifyModels.get(i).getFinishedStatus());
            Log.v("COMDITIOMSSS", "finished >>" + finnished_count);
            Log.v("COMDITIOMSSS", "invoiced >>" + notifyModels.get(i).getInvoiceStatus());
            Log.v("COMDITIOMSSS", "invoiced >>" + invoiced_count);
            Log.v("COMDITIOMSSS", "paid_out >>" + notifyModels.get(i).getPaidoutStatus());
            Log.v("COMDITIOMSSS", "paid_out >>" + paid_out_count);


            if (finnished_count == 1 && invoiced_count == 1 && paid_out_count == 1) {
                Log.v("3RDCONDITION", "invoiced_count" + invoiced_count);
                Log.v("3RDCONDITION", "finnished_count" + finnished_count);
                Log.v("3RDCONDITION", "paid_out_count" + paid_out_count);


                if ((notifyModels.get(i).getFinishedStatus() == 1) && (notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getPaidoutStatus() == 1)) {

              //      User user = new User();




                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", "f==in==p>>" + paid_out_count);

                    listM.add(user);

                    status_for_3 = 0;


                }else {

                    status_for_3 = 1;
                }


            } else if (invoiced_count == 1 && finnished_count == 1  && paid_out_count == 0 ) {




                if ((notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getFinishedStatus() == 1)) {


                  // User user = new User();
                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " f==in>>" + paid_out_count);
                    listM.add(user);


                }


            }
            else if ( finnished_count == 1&& paid_out_count == 1 && invoiced_count == 0 ) {

                Log.v("3RDCONDITION", "invoiced_count" + invoiced_count);
                Log.v("3RDCONDITION", "finnished_count" + finnished_count);
                Log.v("3RDCONDITION", "paid_out_count" + paid_out_count);


                if ((notifyModels.get(i).getFinishedStatus() == 1) &&(notifyModels.get(i).getPaidoutStatus() == 1) &&(notifyModels.get(i).getInvoiceStatus() == 0)) {


                   // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", "f==p>>" + paid_out_count);

                    listM.add(user);


                }
            }

            else if ( finnished_count ==  0 && invoiced_count ==  1 && paid_out_count ==0) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1)) {


                   // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }

            else if ( finnished_count ==  0 && invoiced_count ==  1 && paid_out_count ==1) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getPaidoutStatus() == 1) ) {


                    // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }



            else if (finnished_count ==  0 && invoiced_count ==  1 && paid_out_count ==0) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1)) {


                    // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }

            else if (invoiced_count ==  1 ) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1)) {


                    // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }

            else if (invoiced_count ==  1 && paid_out_count ==1) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1) && (notifyModels.get(i).getPaidoutStatus() == 1) ) {


                   // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }


            else if (finnished_count == 1) {


                if ((notifyModels.get(i).getFinishedStatus() == 1)) {


                   // User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("c", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }


            else if (invoiced_count == 1  ) {


                if ((notifyModels.get(i).getInvoiceStatus() == 1)) {


                 //   User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());

                    Log.v("3RDCONDITION", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }

            else if (paid_out_count == 1 ) {


                if ((notifyModels.get(i).getPaidoutStatus() == 1)) {


                  //  User user = new User();

                    user.setCarId(notifyModels.get(i).getCarId());
                    user.setDate(notifyModels.get(i).getDate());
                    user.setModel(notifyModels.get(i).getModel());
                    user.setNote(notifyModels.get(i).getNote());
                    user.setPlateNo(notifyModels.get(i).getPlateNo());
                    user.setPrice(notifyModels.get(i).getPrice());
                    user.setIcon(notifyModels.get(i).getIcon());
                    user.setImage(notifyModels.get(i).getImage());
                    user.setFinishedStatus(notifyModels.get(i).getFinishedStatus());
                    user.setInvoiceStatus(notifyModels.get(i).getInvoiceStatus());
                    user.setPaidoutStatus(notifyModels.get(i).getPaidoutStatus());
                    Log.v("3RDCONDITION", " in==p>>" + paid_out_count);

                    listM.add(user);


                }
            }
        }

        if(listM.size()>0){
             if(status_for_3==1){

               // listM.clear();
                 adapterCalling(listM, context);

             }else {

                 adapterCalling(listM, context);
             }
        }else {

            if(notifyModels.size()>0){
                if(status_for_3==1){

                   // notifyModels.clear();
                    adapterCalling(notifyModels , context);

                }else {
                    adapterCalling(notifyModels , context);
                }


            }else {

                //Toast.makeText(getActivity())
            }
        }
    }
    public  void  adapterCalling(final  List<User> listM, final Context context){



        final String user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);
        String date = new UserSession(getActivity()).readPrefs(UserSession.DATE_FOR_FRAG);
        final String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

        cartAdapter = new CartListAdapter(context, listM, new RecyclerButtonclick() {
            @Override
            public void onItemClick(int position, int type) {


                if (type == 10) {


                }


                if (type == 1) {


                  //  Toast.makeText(context,"BOX_UPDATE",Toast.LENGTH_SHORT).show();
                    param_car_id = "" + notifyModels.get(position).getCarId();
                    param_type = "finished";

                                         /*   for(int i=0 ; i<notifyModels.size();i++){

                                                if(notifyModels.get(0).getUser().get(i).getFinishedStatus()==1){
                                                    notifyModels.add(expenseListPojo);
                                                }

                                            }

                                            cartAdapter.notifyDataSetChanged();*/

                    if (notifyModels.get(position).getFinishedStatus() == 0) {
                        param_status = "1";





                    } else {
                        param_status = "0";







                    }


                    dbManager.UPDATE_PAYMENT_BOX_FINISH(user_id , param_car_id , list_id,param_status ,"finish");



                    tggl_finnished.setChecked(false);
                    finnished_count =0;
                    invoiced_count=0;
                    paid_out_count = 0 ;
                    tggle_paid_out.setChecked(false);
                    tggl_incoced.setChecked(false);
                 //   fetchDataOffline();

                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);

                    }else {

                        fetchDataOffline();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }


                   filtwer();


                } else if (type == 2) {
                    Log.e("NOTIFILISTSIZE",">>>"+notifyModels.size() +" type  -  "+type);
                    param_car_id = "" + notifyModels.get(position).getCarId();
                    param_type = "invoice";

                    if (notifyModels.get(position).getInvoiceStatus() == 0) {
                        param_status = "1";


                    } else {


                        param_status = "0";

                    }
                   // carListUpdateAPI();

                    dbManager.UPDATE_PAYMENT_BOX_INVOICE(user_id , param_car_id , list_id,param_status ,"invoice");


                    tggl_finnished.setChecked(false);
                    finnished_count =0;
                    invoiced_count=0;
                    paid_out_count = 0 ;
                    tggle_paid_out.setChecked(false);
                    tggl_incoced.setChecked(false);
                   // fetchDataOffline();


                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }else {
                       // fetchDataOffline();


                        if(main_date.equals(filter_date_txt)){

                            fetchDataOfflineAll();
                            Log.v("ALL_DATE_SHOW",">>"+main_date);
                        }else {
                            fetchDataOffline();
                            Log.v("ALL_DATE_SHOW",">>"+main_date);
                        }



                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }


                  filtwer();





                } else if (type == 3) {
                    param_car_id = "" + notifyModels.get(position).getCarId();
                    param_type = "paid_out";

                    if (notifyModels.get(position).getPaidoutStatus() == 0) {


                        param_status = "1";


                    } else {
                        param_status = "0";

                    }
                    dbManager.UPDATE_PAYMENT_PAID_OUT(user_id , param_car_id , list_id,param_status ,"paid out");



                    tggl_finnished.setChecked(false);
                    finnished_count =0;
                    invoiced_count=0;
                    paid_out_count = 0 ;
                    tggle_paid_out.setChecked(false);
                    tggl_incoced.setChecked(false);


                  //  fetchDataOffline();

                    if(main_date.equals(filter_date_txt)){

                        fetchDataOfflineAll();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }else {
                        fetchDataOffline();
                        Log.v("ALL_DATE_SHOW",">>"+main_date);
                    }


                    filtwer();

                }

            }
        });
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

        //filtwer();



    }




/*
    void filter(String text){
        List<User> temp = new ArrayList();
        for(User d: notifyModels){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getListName().toLowerCase().contains(text) || d.getListName().toUpperCase().contains(text)){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+all_model_lists);
            }
            allListAdapter }
        //update recyclerview
        .updateList(temp);
    }*/



    void filter2(String text){
        List<User> temp = new ArrayList();
        for(User d: notifyModels){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getModel().toLowerCase().contains(text.toLowerCase()) || d.getModel().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+cartModelList);
            }
            else  if(d.getPlateNo().toLowerCase().contains(text.toLowerCase()) || d.getPlateNo().toUpperCase().contains(text.toUpperCase())){
                temp.add(d);

                Log.v("DIKSHA","LISTTTT!!  temp  >>"+temp.size());
                Log.v("DIKSHA","LISTTTT!!"+cartModelList);
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

                            Log.v("EXPORT_RESPONSE222", ">>" + url);


                            AppUtility.downloadFileFromURL(getActivity(), url);





                            //for soenload file

                            //   AppUtility.downloadFileFromURL(getApplicationContext(),jo.optString());

                            //  }

                        } catch (Exception e) {
                            Log.v("EXPORT_RESPONSE222", "ERROR>>" + e);
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

            String data =edt_share.getText().toString().trim();

            Log.v("TAG_DIKSHA"," edit text value >>>"+data);

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

            Log.v("AUTO_FILE_ID2233",">>"+user_id);

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

        //    Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id , list_id , "car" );

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE_TYPE(user_id , list_id ,main_date.trim(),"car");

            int i=0;

            notifyModels.clear();

            CARLISTPOJO fileListPojo = new CARLISTPOJO();

            if(cursor.moveToFirst()) {

                do {
                    Log.v("AUTO_FILE_ID223344",">>"+user_id);
                    String payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    String list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    String payment_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    String payment_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String payment_category_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));
                    String paymentCategoy_name  = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));

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



                    Log.v("AUTO_FILE_ID_222",">>"+payment_note);
                    Log.v("AUTO_FILE_ID_222",">>"+payment_id);


                    List<User> userlist = new ArrayList<>();
                    User user = new User();
                    user.setCarId(Integer.parseInt(payment_id));
                    user.setUserId(Integer.parseInt(payment_user_id));
                    user.setList_id(Integer.parseInt(list_id2));
                    user.setPrice(payment_price);
                    user.setDate(payment_date);
                    user.setCategory_id(Integer.parseInt(payment_category_id));
                    user.setNote(payment_note);
                    user.setType(payment_type);
                    user.setCreatedAt(payment_careated_at);
                    user.setType_name(paymentCategoy_name);
                    user.setPlateNo(pay_plate_no);
                    user.setFinishedStatus(Integer.parseInt(finished_status));
                    user.setInvoiceStatus(Integer.parseInt(invoiced_status));
                    user.setPaidoutStatus(Integer.parseInt(paid_out_status));
                    user.setModel(pay_model);
                    user.setIcon(pay_icon);
                    user.setCompany(company);
                    user.setType_name(paymentCategoy_name);




                    userlist .add(user);

                    fileListPojo.setUser(notifyModels);

                    notifyModels.add(user);

                    fileListPojo.setUser(notifyModels);

                } while (cursor.moveToNext());

                i++;

               adapterCalling(notifyModels , context);

            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","qwqwqwqwqwqwqw>>>>"+e);
        }

    }

    public  void fetchDataOfflineAll(){


        dbManager = new DBManager(getActivity());
        dbManager.open();

        String  user_id = new UserSession(getActivity()).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {

            notifyModels = new ArrayList<>();

            Log.v("AUTO_FILE_ID2233",">>"+user_id);

            String list_id = new UserSession(getActivity()).readPrefs(UserSession.HOME_LIST_ID);

            //    Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id , list_id , "car" );

            Cursor cursor = dbManager.FETCH_PAYMENTS_BY_DATE_TYPE_ALL_DATE_DATA(user_id , list_id ,"car");

            int i=0;

            notifyModels.clear();

            CARLISTPOJO fileListPojo = new CARLISTPOJO();

            if(cursor.moveToFirst()) {

                do {
                    Log.v("AUTO_FILE_ID223344",">>"+user_id);
                    String payment_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_PAYMENT_ID));
                    String list_id2 = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_LIST_ID));
                    String payment_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_USER_ID));
                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));
                    String payment_date = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAYMENT_DATE));
                    String payment_category_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_CATEGORY_ID));
                    String paymentCategoy_name  = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TYPE_NAME));

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



                    Log.v("AUTO_FILE_ID_222",">>"+payment_note);
                    Log.v("AUTO_FILE_ID_222",">>"+payment_id);


                    List<User> userlist = new ArrayList<>();
                    User user = new User();
                    user.setCarId(Integer.parseInt(payment_id));
                    user.setUserId(Integer.parseInt(payment_user_id));
                    user.setList_id(Integer.parseInt(list_id2));
                    user.setPrice(payment_price);
                    user.setDate(payment_date);
                    user.setCategory_id(Integer.parseInt(payment_category_id));
                    user.setNote(payment_note);
                    user.setType(payment_type);
                    user.setCreatedAt(payment_careated_at);
                    user.setType_name(paymentCategoy_name);
                    user.setPlateNo(pay_plate_no);
                    user.setFinishedStatus(Integer.parseInt(finished_status));
                    user.setInvoiceStatus(Integer.parseInt(invoiced_status));
                    user.setPaidoutStatus(Integer.parseInt(paid_out_status));
                    user.setModel(pay_model);
                    user.setIcon(pay_icon);
                    user.setCompany(company);
                    user.setType_name(paymentCategoy_name);




                    userlist .add(user);

                    fileListPojo.setUser(notifyModels);

                    notifyModels.add(user);

                    fileListPojo.setUser(notifyModels);

                } while (cursor.moveToNext());

                i++;

                adapterCalling(notifyModels , context);

            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","qwqwqwqwqwqwqw>>>>"+e);
        }

    }

    @Override
    public void onResume() {
        super.onResume();



    }
}
