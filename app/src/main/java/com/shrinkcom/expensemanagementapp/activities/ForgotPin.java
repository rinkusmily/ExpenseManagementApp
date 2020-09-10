package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.shrinkcom.expensemanagementapp.Pojo.LoginPojo;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPin extends AppCompatActivity implements View.OnClickListener {

    EditText edt_email;
    TextView tv_send;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        init_view();
    }

    private void init_view() {
        edt_email = findViewById(R.id.edt_email);
        tv_send = findViewById(R.id.tv_send);

        edt_email.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_send:

                if(validation()){
                    submitForgetPassword();
                }




                break;

        }




    }


    public boolean validation(){

      email = edt_email.getText().toString().trim();

        if (email.isEmpty()) {
            edt_email.setError(getString(R.string.enter_email_phn));
            edt_email.requestFocus();
            return false;

        }

        return true;
    }





    //this is for normal signin
    private void submitForgetPassword() {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPin.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.FORGOT_PIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.v("DIKSHA", "FORGOT_PASS_RESPONSE" + response);
                                JSONObject jo=new JSONObject(response);


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                if (jo.getInt("status")==1)
                                {

                                    Intent intent = new Intent(ForgotPin.this,OTPActvity.class);
                                    intent.putExtra("emailaddress",email);
                                    startActivity(intent);

                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(ForgotPin.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(ForgotPin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();

                params.put("email", email);




                Log.e("DIKSHA", "FORGOT_PASS_REQUEST" + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(ForgotPin.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }


}
