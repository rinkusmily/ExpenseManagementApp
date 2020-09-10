package com.shrinkcom.expensemanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import android.widget.Toast;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.SpinnerAdapter;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class CuruencySelecterAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Boolean mAllowSelectionFiring = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curuency_selecter_acitivity);


        ArrayList<String> spinnerAdapterData = new ArrayList<>();
      String[] spinnerItemsArray = getResources().getStringArray(R.array.items);

        ArrayList<String> items= getCountries("common_currency.json");


        Collections.addAll(spinnerAdapterData, spinnerItemsArray);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.adapter_spinner, spinnerAdapterData, getResources());

        Spinner itemList = (Spinner) findViewById(R.id.edt_currency);
        itemList.setAdapter(adapter);

        itemList.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (mAllowSelectionFiring) {
            int selectedItemPosition = Integer.parseInt(view.getTag(R.string.meta_position).toString().trim());
            String selectedItemTitle = view.getTag(R.string.meta_title).toString().trim();

            Toast.makeText(getApplicationContext(),   selectedItemTitle, Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.putExtra("currency",selectedItemTitle);
            setResult(2000,intent);
            finish();

        } else {
            mAllowSelectionFiring = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }



    private ArrayList<String> getCountries(String fileName){
        JSONArray jsonArray=null;
        ArrayList<String> cList=new ArrayList<String>();
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray=new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getJSONObject(i).getString("cname"));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return cList;
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }



}
