package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPIN extends AppCompatActivity {

    EditText edt_new_pin, edt_conf_new_pin;
    TextView tv_createpin;

    String new_pin , confirm_pin;

    Context context ;
    Activity activity;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_p_i_n);

        Intent intent = getIntent();

         user_id = intent.getStringExtra("user_id");

        context = activity= this;

        initView();

        tv_createpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });


    }

    private void initView() {

        edt_new_pin = findViewById(R.id.edt_newpin);
        edt_conf_new_pin = findViewById(R.id.edt_connewpin);
        tv_createpin = findViewById(R.id.tv_createpin);


    }

    void validation() {

        new_pin = edt_new_pin.getText().toString().trim();
        confirm_pin=edt_conf_new_pin.getText().toString().trim();


        if (new_pin.isEmpty())
        {
            edt_new_pin.setError(getString(R.string.enter_new_pin));
            edt_new_pin.requestFocus();

        }
        else   if (confirm_pin.isEmpty()) {
            edt_conf_new_pin.setError(getString(R.string.enter_confirm_pin));
            edt_conf_new_pin.requestFocus();
        }

        else  if (!new_pin.equalsIgnoreCase(confirm_pin)){

            edt_conf_new_pin.setError(getString(R.string.pin_not_match));
            edt_conf_new_pin.requestFocus();
        }
        else {

            rsetPinAPI();
        }
    }


    private void rsetPinAPI() {


        final ProgressDialog progressDialog = new ProgressDialog(ResetPIN.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.RESET_PIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.e("DIKSHA", "RESET_PIN_RESPONSE >>" + response);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1)
                                {
                                    //Token();
                                    Intent intent =  new Intent(ResetPIN.this, Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {

                                    Log.e("DIKSHA", "ERROR_MESSAGE_RESTPIN >>" +  jo.getString("message"));
                                    Toast.makeText(ResetPIN.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(ResetPIN.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("DIKSHA", "ERROR_MESSAGE_RESTPIN >>" +  error.getMessage());
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("pin", confirm_pin);



                Log.e("DIKSHA", "RESET_PIN_REQUEST" + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(ResetPIN.this);
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


