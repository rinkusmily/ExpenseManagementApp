package com.shrinkcom.expensemanagementapp.fragment;/*
package com.shrinkcom.expensemanagementapp.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.educationapp.DelegateActivity.HomeActivity;
import com.shrinkcom.educationapp.DelegateActivity.LoginActivity;
import com.shrinkcom.educationapp.FacilitatorActivity.FacilitatorActivity;
import com.shrinkcom.educationapp.FacilitatorActivity.FacilitatorCourseActivity;
import com.shrinkcom.educationapp.FacilitatorActivity.FacilitatorvideoActivity;
import com.shrinkcom.educationapp.FacilitatorActivity.PreprationDelivery;
import com.shrinkcom.educationapp.R;
import com.shrinkcom.educationapp.beans.Adddelegateresponse.com.shrinkcom.educationapp.Adddelegateresponse;
import com.shrinkcom.educationapp.storage.DBManager;
import com.shrinkcom.educationapp.storage.DatabaseHandler;
import com.shrinkcom.educationapp.utility.AppUtils;
import com.shrinkcom.educationapp.utility.SessionManager;
import com.shrinkcom.educationapp.utility.URLs;
import com.shrinkcom.educationapp.utility.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView tvlogoff;
    AlertDialog.Builder builder;
    TextView tvsamedelegate,diffrentdelegate , tv_sync_data;
    DBManager dbManager;

    List<String> synk_offline_delegate_list;

    List<String> daligate_name_list;
    List<String> daligate_id_list;

    String c_id,c_name,s_id,s_name;


    String faci_id , dc_id , ds_id , dtoken;
    JSONArray jsonArray_daligate;
    JSONArray jsonArray_feedback;
    List<String> flist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        toolbar = findViewById(R.id.mToolbar);
        tvlogoff = findViewById(R.id.tv_logoff);
        tvsamedelegate=findViewById(R.id.same_delegate);
        diffrentdelegate=findViewById(R.id.diffrent_delegate);
        tv_sync_data=findViewById(R.id.tv_sync_data);

        daligate_name_list = new ArrayList<>();
        daligate_id_list = new ArrayList<>();


        daligate_name_list.add("Delegate Name");
        daligate_id_list.add("0");



        dbManager = new DBManager(this);
        dbManager.open();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Intent intent=new Intent(SendActivity.this, FacilitatorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                new SessionManager(SendActivity.this).setdelegatelist(daligate_name_list);
                new SessionManager(SendActivity.this).setdelegateIDlist(daligate_id_list);

                finish();
                startActivity(intent);
            }
        });



        tvlogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbManager.deleteAlladdDelagate();
                dbManager.deleteAllDelegates();
                new SessionManager(SendActivity.this).setCallTYPE(URLs.DAYIGATETYPEDEFFRTENT);
                new SessionManager(SendActivity.this).saveValueForSession("");
                Intent intent=new Intent(SendActivity.this, FacilitatorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                new SessionManager(SendActivity.this).setdelegatelist(daligate_name_list);
                new SessionManager(SendActivity.this).setdelegateIDlist(daligate_id_list);


                finish();
                startActivity(intent);

               // showDialogMenu();
            }
        });

        tvsamedelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SessionManager(SendActivity.this).setCallTYPE(URLs.DAYIGATETYPESAME);

                if (AppUtils.isNetworkConnected(SendActivity.this)) {
                    getDelegetApi();
                    new SessionManager(SendActivity.this).saveValueForSession("1");

                }else {

                    insertDelegateinOffline();
                    new SessionManager(SendActivity.this).saveValueForSession("1");

                }




            }
        });



        diffrentdelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SessionManager(SendActivity.this).setCallTYPE(URLs.DAYIGATETYPEDEFFRTENT);
                dbManager.deleteAlladdDelagate();
                dbManager.deleteAllDelegates();
                Intent intent=new Intent(SendActivity.this, FacilitatorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                new SessionManager(SendActivity.this).setdelegatelist(daligate_name_list);
                new SessionManager(SendActivity.this).setdelegateIDlist(daligate_id_list);
                new SessionManager(SendActivity.this).saveValueForSession("");

                finish();
                startActivity(intent);
            }
        });



        tv_sync_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //synk all saved (in Local database)delegate here to the server

                if(AppUtils.isNetworkConnected(SendActivity.this)){

                    syncDelegate();
                }else {

                    AppUtils.showAlert(SendActivity.this ,"Please on internet connectivity");
                }


            }


        });



        faci_id =new SessionManager(SendActivity.this).getUserSession().getUserID();
        dc_id=new SessionManager(SendActivity.this).getCourseID();
        ds_id=new SessionManager(SendActivity.this).getSessionid();
        dtoken =new SessionManager(SendActivity.this).getoken();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(SendActivity.this, FacilitatorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        new SessionManager(SendActivity.this).setdelegatelist(daligate_name_list);
        new SessionManager(SendActivity.this).setdelegateIDlist(daligate_id_list);

        finish();
        startActivity(intent);
    }

    private void insertDelegateinOffline() {

        new SessionManager(SendActivity.this).setCallTYPE(URLs.DAYIGATETYPESAME);
        c_id  =  new SessionManager(SendActivity.this).getCourseID();
        Log.e("COURSEID","hnkfdjg>> "+ c_id);
       // Intent intent = new Intent(SendActivity.this, ContinueWithSameDelegate.class);
        Intent intent = new Intent(SendActivity.this, FacilitatorCourseActivity.class);
        intent.putExtra("course_id", c_id);
        intent.putExtra("course_name", c_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);


*/
/*
        dbManager.deleteAllDelegates();
        dbManager.open();
        Cursor cursor = dbManager.fetch_delegate_by_values(faci_id, dc_id, ds_id, dtoken);


        Log.v("DIKSHA", "CURSOR SIZE VIdeo" + cursor.getPosition());

            if (cursor.moveToFirst()) {

                do {

                    c_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_COU_ID));
                    c_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_NAME));

                    String str_delivery_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DELIVERY_ID));
                    String str_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_NAME));
                    String str_job_title = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_JOB_TITLE));
                    String str_place_work = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_PLACE_OF_WORK));
                    String str_department = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_DEPARTMENT));
                    String str_email = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_U_EMAIL));
                    String str_session_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_SE_ID));
                    String str_course_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_COU_ID));
                    String str_faci_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_FACI_ID));

                    Log.v("DIKSHA", "TOKEN_NO_OFFLINE>" + faci_id);
                   // dbManager.insertDelegate(str_delivery_id, str_name, str_job_title, str_place_work, str_department, str_email, str_session_id, str_faci_id, str_course_id);
                }
                while (cursor.moveToNext());

                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();






            } else {

                Toast.makeText(SendActivity.this, "No data Found", Toast.LENGTH_SHORT).show();



            }*//*




    }

    private void showDialogMenu () {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("messege").setTitle("title");

            builder.setMessage("Are you sure you want Logout ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(SendActivity.this, LoginActivity.class));

                           */
/* dbManager.deleteAllCourse();
                            dbManager.deleteAllSessionData();
                            dbManager.delete_preprationData();
                            dbManager.deleteAllfa_videodata();
                            dbManager.deleteAllfa_DOC_data();
                            dbManager.delete_office_data();
                            dbManager.delete_all_delivery_video_data();
                            dbManager.deleteAllQUIZ();
                            dbManager.delete_DeliveryData();
                            dbManager.deleteAllAddFeedBackData();
                            dbManager.delete_all_token();*//*


                            finish();
                            Toast.makeText(getApplicationContext(), "You clicked yes",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "You clicked no",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Logout ");
            alert.setIcon(R.drawable.educationsmall);
            alert.show();
        }



        private void getDelegetApi() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.e("RESPONSEEEEGAL", ">>" + response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("result")==1) {

                                JSONArray user_data = obj.getJSONArray("userData");

                                Log.e("jksdhkjsggtgert",">>"+user_data.length());


                                DBManager dbManager = new DBManager(SendActivity.this);
                                dbManager.open();
                                dbManager.deleteAllDelegates();


                                for(int i = 0; i<user_data.length();i++){

                                    JSONObject user_data_obj = user_data.getJSONObject(i);
                                    c_id = user_data_obj.optString("cou_id");
                                    c_name = user_data_obj.optString("name");

                                    String str_delivery_id = user_data_obj.optString("delivery_id");
                                    String str_name = user_data_obj.optString("name");
                                    String str_job_title = user_data_obj.optString("job_title");
                                    String str_place_work = user_data_obj.optString("place_work");
                                    String str_department = user_data_obj.optString("department");
                                    String str_email = user_data_obj.optString("email");
                                    String str_session_id = user_data_obj.optString("se_id");
                                    String str_course_id = user_data_obj.optString("cou_id");
                                    String str_faci_id =  user_data_obj.optString("faci_ID");
                                    String phone =  user_data_obj.optString("phone");
                                    String gender =  user_data_obj.optString("gender");



                                    dbManager.insertDelegate(str_delivery_id,str_name,str_job_title,str_place_work ,str_department,str_email,str_session_id ,str_faci_id,str_course_id,phone,gender);

                                }

                                Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(SendActivity.this, ContinueWithSameDelegate.class);
                                intent.putExtra("course_id",c_id);
                                intent.putExtra("course_name",c_name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);

                            } else {
                                Toast.makeText(SendActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      }

                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action","get_add_delegate");
                params.put("fac_id",""+new SessionManager(SendActivity.this).getUserSession().getUserID());
                params.put("c_id",new SessionManager(SendActivity.this).getCourseID());
                params.put("s_id",""+new SessionManager(SendActivity.this).getSessionid());
                params.put("token",""+new SessionManager(SendActivity.this).getoken());
               // params.put("token",""+jsonArraydalifate);
                Log.e("SENDVALUEggg", ">>" + params);
                return params;




            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



// this method is used to send saved delegate to the server
    public void syncDelegate() {

        String delivery_id , str_name ,str_job_title ,str_place_work , str_department ,str_email ,str_session_id ,str_faci_id ,str_course_id ,str_phone , str_gender;

        //first fatch all  delegates from the server

        try {

            */
/*synk_offline_delegate_list = new ArrayList<>();*//*




            dbManager = new DBManager(this);
            dbManager.open();

            Cursor cursor = dbManager.fatchAllDelegate();
            jsonArray_daligate = new JSONArray();
            if (cursor.moveToFirst()){
                do {

                     delivery_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DELEVERY_ID));
                     str_name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.NAME));
                     str_job_title = cursor.getString(cursor.getColumnIndex(DatabaseHandler.JOB_TITLE));
                     str_place_work = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PLACE_WORK));
                     str_department = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEPARTMENT));
                     str_email = cursor.getString(cursor.getColumnIndex(DatabaseHandler.EMAIL));
                     str_session_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.SE_ID));
                     str_faci_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FACI_ID));
                     str_course_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COU_ID));
                     str_phone = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PHONE));
                     str_gender = cursor.getString(cursor.getColumnIndex(DatabaseHandler.GENDER));

                     Log.e("genderrrr",">>"+str_gender);
                    JSONObject object = new JSONObject();
                    object.put("key",delivery_id);
                    object.put("name",str_name);
                    object.put("job_title",str_job_title);
                    object.put("place_work",str_place_work);
                    object.put("department",str_department);
                    object.put("email",str_email);
                    object.put("sessionid",str_session_id);
                    object.put("faci_id",str_faci_id);
                    object.put("cid",str_course_id);
                    object.put("phone",""+str_phone);
                    object.put("gender",""+str_gender);


                    jsonArray_daligate.put(object);

                }while (cursor.moveToNext());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


        String de_feed_id=""  , d_delegate_feedback="" ,  d_delegate_desc=""
                , del_delegate_id="" , d_facilitator_feedback="" ,  d_facilitator_desc ="",
                fac_id ="",  sess_id ="", cours_id ="", feedback_for="" ;

        // fatch feddback of delegate frtom local database
        try {


            Cursor cursor = dbManager.fatchDeleviryfeedbackData();
            jsonArray_feedback = new JSONArray();
            flist = new ArrayList<>();
            if (cursor.moveToFirst()){
                do {

                    de_feed_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.D_FEED_ID));
                    d_delegate_feedback = cursor.getString(cursor.getColumnIndex(DatabaseHandler.D_DELEGATE_FEEDBACK));
                    d_delegate_desc = cursor.getString(cursor.getColumnIndex(DatabaseHandler.D_DELEGATE_DESC));
                    del_delegate_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DEL_DELEGATE_ID));
                    fac_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FAC_ID));
                    sess_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.SESS_ID));
                    cours_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.DELIVIRY_COURSE_ID));
                    feedback_for = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FEEDBACK_FOR));
                     String  fecilatordesp = cursor.getString(cursor.getColumnIndex(DatabaseHandler.D_FACILITATOR_DESCP));
                     String  feedback_fornme = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FEEDBACK_FOR_NAME));

                    JSONObject object = new JSONObject();
                    object.put("key",de_feed_id);
                    object.put("del_feedback",d_delegate_feedback);
                    object.put("del_feedback_desc",d_delegate_desc);
                    object.put("del_delegate_id",feedback_fornme);
                    object.put("fac_id",fac_id);
                    object.put("sess_id",sess_id);
                    object.put("cid",cours_id);
                    object.put("user_type",feedback_for);

                    jsonArray_feedback.put(object);

                }while (cursor.moveToNext());
            }





        Cursor cursoragain  = dbManager.fatchDeleviryfeedbackData();

        if (cursoragain.moveToFirst()){
            do {



               String feedback_forid = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.FEEDBACK_FOR));
                String  fecilatordesp = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.D_FACILITATOR_DESCP));
                String  feedback_fornme = cursoragain.getString(cursoragain.getColumnIndex(DatabaseHandler.FEEDBACK_FOR_NAME));

                JSONObject object1 = new JSONObject();


                object1.put("del_feedback_desc",fecilatordesp);
                object1.put("del_delegate_id",feedback_fornme);
                object1.put("user_type",feedback_forid);

                jsonArray_feedback.put(object1);

            }while (cursoragain.moveToNext());
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SyncFacilitatorFeedback(jsonArray_feedback);




    }
    //sync data facilitator data to  the  serever
     private void  SyncFacilitatorFeedback(final JSONArray feedback) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SFRRRRESPONSEE",">>"+response);
                        progressDialog.dismiss();
                        Toast.makeText(SendActivity.this, "Synk Data Success", Toast.LENGTH_SHORT).show();
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("data",""+jsonArray_daligate);
                params.put("feedback",""+feedback);
                params.put("action","offline_datasink");
                Log.e("SENDVALUEE",">> SENDvalue "+params);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }















    }



*/
