package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPActvity extends AppCompatActivity {

    private EditText edt_otp;
    TextView tv_nextotp;
    String UserOtp, useremail;
    ImageView imv_backotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactvity);

        initViews();
        useremail = getIntent().getStringExtra("emailaddress");
    }

    private void initViews() {
        edt_otp=findViewById(R.id.edt_otp);
        tv_nextotp=findViewById(R.id.tv_nextotp);
        imv_backotp=findViewById(R.id.imv_backotp);

        imv_backotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActvity.this, ForgotPin.class);
                startActivity(intent);
            }
        });

        tv_nextotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) {
                    return;
                }
                OtpCheck();
            }
        });

    }

    boolean validation() {
        boolean valid = true;
        UserOtp = edt_otp.getText().toString().trim();

        if (TextUtils.isEmpty(UserOtp)) {
            edt_otp.setError(getString(R.string.title_enterotp));
            valid = false;
        } else {
            edt_otp.setError(null);

        }
        return valid;
    }

    private void OtpCheck() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.VARIFY_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("status") == 1) {


                                JSONArray sportsArray = obj.getJSONArray("user");

                                JSONObject firstSport = sportsArray.getJSONObject(0);

                                String user_id= firstSport.getString("user_id");

                                Intent intent = new Intent(OTPActvity.this, ResetPIN.class);
                                intent.putExtra("email", useremail);
                                intent.putExtra("user_id",user_id);
                                startActivity(intent);
                                finish();
                                Toast.makeText(OTPActvity.this, R.string.otp_successfully_verify, Toast.LENGTH_SHORT).show();

                            } else {

                                Log.v("OTPPP","OTP_MESSAGE"+  obj.getString("message"));

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                          //  Utilitynew.UserAlert(OTPActvity.this,(getString(R.string.tv_internet)));

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", "" + useremail);
                params.put("otp", "" +UserOtp );
                Log.e("SENDVALUE", ">>" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(OTPActvity.this);
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
