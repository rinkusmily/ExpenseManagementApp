package com.shrinkcom.expensemanagementapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NetworkChangeReceiver extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        mContext = context;
        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";
        }else {
            Callerverapi();
        }
       // Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }


    void Callerverapi(){

        String urllocation = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyAdBPKwVB1d64dXfVZZquuriNJa02v5v2M&address=Indore";
        Log.e("Locationurlll",">>>"+urllocation);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urllocation.replace(" ", "%20"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RINKU", ">>RESPONSE " + response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("RINKU", ">>>> " + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mContext).add(stringRequest);
    }
}