package com.shrinkcom.expensemanagementapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.SpinnerAdapter;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class LanguageSelectAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Boolean mAllowSelectionFiring = false;
    UserSession userSession ;
    String STATUS;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select_activity);

        context = this;

        userSession = new UserSession(this);

        languageSelector();


    }


    private void languageSelector() {

        ArrayList<String> spinnerAdapterData = new ArrayList<>();
        String[] spinnerItemsArray = getResources().getStringArray(R.array.items2);

        ArrayList<String> items= getCountries("common_currency.json");


        Collections.addAll(spinnerAdapterData, spinnerItemsArray);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.adapter_spinner, spinnerAdapterData, getResources());

        Spinner itemList = (Spinner) findViewById(R.id.spn_language);
        itemList.setAdapter(adapter);

        itemList.setOnItemSelectedListener(LanguageSelectAcitivity.this);


    }

    //language selector

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (mAllowSelectionFiring) {
            int selectedItemPosition = Integer.parseInt(view.getTag(R.string.meta_position).toString().trim());
            String selectedItemTitle = view.getTag(R.string.meta_title).toString().trim();

            Toast.makeText(getApplicationContext(), selectedItemTitle, Toast.LENGTH_LONG).show();

            Log.v("DIKSHA", "LANGUAGE>>" + selectedItemTitle);


            if (selectedItemTitle.equals("Español")) {



                updateViews("es");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language  >>" + language);



                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            } else if (selectedItemTitle.equals("português")) {


                updateViews("pt");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);


                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            } else if (selectedItemTitle.equals("Italiano")) {


                updateViews("it");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);


                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            } else if (selectedItemTitle.equals("Deutsche")) {


                updateViews("de");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);


                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            } else if (selectedItemTitle.equals("Français")) {


                updateViews("fr");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);


                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            } else if (selectedItemTitle.equals("Türk")) {

                updateViews("tr");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language" + language);
                Intent intent6=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent6);
                finish();

            }

            else if (selectedItemTitle.equals("English")) {

                updateViews("en");
                STATUS = "0";


                String language = new UserSession(context).getLanguage();
                Log.v("DIKSHA", "get language>>" + language);
                Intent intent8=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent8);
                finish();

            }


            else {
                updateViews("en");

                STATUS = "0";
                Intent intent9=new Intent(  LanguageSelectAcitivity.this, Settings.class);
                startActivity(intent9);

                finish();
            }

        } else {
            mAllowSelectionFiring = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private ArrayList<String> getCountries(String fileName) {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<String>();
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {


                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getJSONObject(i).getString("cname"));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException je) {
            je.printStackTrace();
        }
        return cList;
    }


    private void updateViews(String languageCode) {

        userSession.setLanguage(languageCode);
        Locale mylocal = new Locale(languageCode);
        Context context = LocaleHelper.setLocale(LanguageSelectAcitivity.this, languageCode);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();

        Log.v("DIKSHA", "USERSESSION" + languageCode);

        new UserSession(this).setLanguage(languageCode);
        conf.locale = mylocal;
        resources.updateConfiguration(conf, dm);
    }


    //language change
   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language "+base);
    }
*/


}
