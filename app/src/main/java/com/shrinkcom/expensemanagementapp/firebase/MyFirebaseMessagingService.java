package com.shrinkcom.expensemanagementapp.firebase;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.syncPojo.GetAllData;
import com.shrinkcom.expensemanagementapp.R;

import com.shrinkcom.expensemanagementapp.activities.SplashScreen;
import com.shrinkcom.expensemanagementapp.services.Restarter;
import com.shrinkcom.expensemanagementapp.services.YourService;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.NetworkUtil;
import com.shrinkcom.expensemanagementapp.utils.UserSession;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaerv.akva";


    UserSession userSession;
    Context context;
    private static int SPLASH_TIME_OUT = 1000;

    DBManager dbManager;

    String user_id ;

    Intent mServiceIntent;

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
    YourService mYourService;

    JSONObject jsonObject = new JSONObject();

    List<String> flist;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "onMessageReceived: "+remoteMessage );
        String title = remoteMessage.getData().get("message");


        context=this;

        Log.e(TAG, "MYVALUEEE:"+remoteMessage.getData());

        user_id = UserSession.getInstance(this).readPrefs(UserSession.PREFS_USER_ID);
        //{message={"message":"You can Take Backup!!"}}


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "MYVALUEEE: CALL DATA ");
              //  SyncFacilitatorFeedback();

                syncDelegate();
            }
        });



     /*   mYourService = new YourService();
        mServiceIntent = new Intent(this, mYourService.getClass());
        if (!isMyServiceRunning(mYourService.getClass())) {
            startService(mServiceIntent);
        }*/

      //  Intent intent = new Intent(MyFirebaseMessagingService.this , SynkdataActivity.class);
      // startActivity(intent);


    }

/*    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }


    @Override
    public void onDestroy() {
        //stopService(mServiceIntent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        Log.i ("Service status", "Still Running");
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();



    }*/
    class LooperThread extends Thread {
        public Handler mHandler;

        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                public void handleMessage(Message msg) {

                    backgroundTask task = new backgroundTask();
                    task.execute("");

                }
            };

            Looper.loop();
        }
    }


    private class backgroundTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

           // SyncFacilitatorFeedback();

            syncDelegate();

            return "Success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }







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



            if (user_id.equals("")){


                Intent  intent = new Intent(MyFirebaseMessagingService.this , SplashScreen.class);
                startActivity(intent);


            }else {
                SyncFacilitatorFeedback();
            }
        }


    }





    public void  SyncFacilitatorFeedback() {




        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.SYNCK_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YYYYYYYYYYYYYY",">>"+response);
                    //   getAllData();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();


                        Log.e("ERRORRRR",">>>>"+error);
                        Toast.makeText(MyFirebaseMessagingService.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        final RequestQueue requestQueue = Volley.newRequestQueue(MyFirebaseMessagingService.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }

    private void getAllData() {
      /*  final ProgressDialog progressDialog = new ProgressDialog(MyFirebaseMessagingService.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.GET_ALL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JJJJJJJJJJJJ", "ADD_LISTsdfds_RESPONSE"+response);

                        try {
                           /* if (progressDialog != null) {
                                progressDialog.dismiss();*/

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            GetAllData fileListPojo = gson.fromJson(response, GetAllData.class);

                            JSONObject jo=new JSONObject(response);

                            Log.v("DIKSHAAAAA","jo.getInt  >>>"+jo.getInt("status"));
                            if (jo.getInt("status")==1)
                            {
                                dbManager.open();
                                dbManager.DELETE_CATEGORY();
                                dbManager.DELETE_HOME_LIST();
                                dbManager.DELETE_TOTAL_TRANSACTION();
                                dbManager.DELETE_ALL_PAYMENTS();


                                if(fileListPojo.getData().getCategory().size()>0){


                                    for(int i =0; i< fileListPojo.getData().getCategory().size();i++) {

                                        String user_id = "" + fileListPojo.getData().getCategory().get(i).getUserId();
                                        String cat_name = "" + fileListPojo.getData().getCategory().get(i).getName();
                                        String image = "" + fileListPojo.getData().getCategory().get(i).getImage();
                                        String cat_type = "" + fileListPojo.getData().getCategory().get(i).getType();
                                        String cat_id = "" + fileListPojo.getData().getCategory().get(i).getCatId();
                                        String backup_satatus = "" + fileListPojo.getData().getCategory().get(i).getBackupStatus();
                                        String categoryId = "" + fileListPojo.getData().getCategory().get(i).getCategoryId();

                                        dbManager.INSERTSERVERCATEGORY(user_id, cat_name, image, backup_satatus, cat_type,categoryId);
                                        Log.v("DIKSHA", "INSERT_CATEGORY  ");
                                    }

                                }else{
                                    dbManager.INSERT_CATEGORY(user_id,"Hail",""+R.drawable.hail,"0","car" );
                                    dbManager.INSERT_CATEGORY(user_id,"Parking dent",""+R.drawable.parnking_dent,"1","car" );


                                    dbManager.INSERT_CATEGORY(user_id,"Food",""+R.drawable.food,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Tool",""+R.drawable.tool,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Hotel",""+R.drawable.hotel,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Fuel",""+R.drawable.fuel,"0","expence" );
                                    dbManager.INSERT_CATEGORY(user_id,"Ticket",""+R.drawable.tickets,"0","expence" );


                                    dbManager.INSERT_CATEGORY(user_id,"Cash",""+R.drawable.cash,"0","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Paycheck",""+R.drawable.paycheck,"0","payment" );
                                    dbManager.INSERT_CATEGORY(user_id,"Bank Transfer",""+R.drawable.bank_tran,"0","payment" );


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

                                    Log.v("RRRRRR",">   list_id>>"+list_id);
                                    Log.v("RRRRRR",">   user_id>>"+user_id);
                                    Log.v("RRRRRR",">   price>>"+price);
                                    Log.v("RRRRRR",">   date>>"+date);
                                    Log.v("RRRRRR",">   c_id>>"+c_id);
                                    Log.v("RRRRRR",">   note>>"+note);
                                    Log.v("RRRRRR",">   type>>"+type);
                                    Log.v("RRRRRR",">   creates>>"+creates);
                                    Log.v("RRRRRR",">   plat_no>>"+plat_no);
                                    Log.v("RRRRRR",">   model>>"+model);
                                    Log.v("RRRRRR",">   finish>>"+finish);
                                    Log.v("RRRRRR",">   invoice>>"+invoice);
                                    Log.v("RRRRRR",">   paid>>"+paid);
                                    Log.v("RRRRRR",">   icon>>"+icon);
                                    Log.v("RRRRRR",">   comnpany>>"+comnpany);
                                    Log.v("RRRRRR",">   name>>"+name);



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
                                Toast.makeText(MyFirebaseMessagingService.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            //   }
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
                        // progressDialog.dismiss();

                        Toast.makeText(MyFirebaseMessagingService.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        final RequestQueue requestQueue = Volley.newRequestQueue(MyFirebaseMessagingService.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

}
