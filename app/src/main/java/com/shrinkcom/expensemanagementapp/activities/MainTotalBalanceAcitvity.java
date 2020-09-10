package com.shrinkcom.expensemanagementapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.expensemanagementapp.Database.DBManager;
import com.shrinkcom.expensemanagementapp.Database.DatabaseHandler;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.PaymentList;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Expence;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Payment;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Taking;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Total;
import com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo.Totalamount;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.adaptor.PaymentListAdapter;
import com.shrinkcom.expensemanagementapp.fragment.AllListFragment;
import com.shrinkcom.expensemanagementapp.fragment.BalanceFragment;
import com.shrinkcom.expensemanagementapp.fragment.CarListFragment;
import com.shrinkcom.expensemanagementapp.fragment.ExpenseFragment;
import com.shrinkcom.expensemanagementapp.fragment.PaymentsFragment;
import com.shrinkcom.expensemanagementapp.utils.ApiService;
import com.shrinkcom.expensemanagementapp.utils.AppUtility;
import com.shrinkcom.expensemanagementapp.utils.LocaleHelper;
import com.shrinkcom.expensemanagementapp.utils.StaticVariablesStorage;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.facebook.internal.Utility.isNullOrEmpty;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.DATE_FOR_FRAG;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_FILE_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.HOME_LIST_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.MAIN_DATE;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.PREFS_USER_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class MainTotalBalanceAcitvity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imv_addex, img_home;
    ImageView tv_edit_name, img_open_calender;
    EditText tv_entrycar1;
    TextView tv_set_date, edt_share;
    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout ll_fileter_date;


    LinearLayout all_date_select_layout;

    int date_count =0;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    String file_type;

    int price_of_car=0;
    int price_of_payment=0;
    int price_of_expence=0;
    int price_of_total=0;
    int no_of_car=0;

    String filter_date_txt ;


    View  view_filter_date;
    View view_all;
    View view_select_date;




    TextView tv_taking, tv_expense, tv_payment, tv_balance , tv_select_custome_date;
    String name="";
    LinearLayout ll_select_date ,select_fileter_layout;


    Calendar c;
    String formattedDate;
    SimpleDateFormat df;
    DBManager dbManager;

    String trans_auto_id;

    String currency, symbol ,name1, list_id;

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }



    private FragmentRefreshListener fragmentRefreshListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense_list);

        String s = "200";
        String s2 = "4.000";

        float d = Float.parseFloat(s)+Float.parseFloat(s2);

        Log.v("DIKSHAAA","DOUB:E>>"+d);

        currency = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(CURENCY_ONSELECT);
        symbol   =new UserSession(MainTotalBalanceAcitvity.this).readPrefs(SYMBOL_ONSELECT);
        list_id   =new UserSession(MainTotalBalanceAcitvity.this).readPrefs(HOME_LIST_ID);
        name1     = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(PTREF_LIST_NAME);

         filter_date_txt = getString(R.string.filter_date);


        Log.v("DIKSHA","INTENT_DATA  currency >>"+currency);
        Log.v("DIKSHA","INTENT_DATA  symbol >>"+symbol);
        Log.v("DIKSHA","INTENT_DATA  list_id >>"+list_id);
        Log.v("DIKSHA","INTENT_DATA  list_id >>"+name1);



        new UserSession(MainTotalBalanceAcitvity.this).writePrefs(HOME_LIST_ID ,list_id);


        imv_addex = (ImageView) findViewById(R.id.imv_addex);
        img_home = (ImageView) findViewById(R.id.img_home);

        tv_taking = findViewById(R.id.tv_taking);
        tv_payment = findViewById(R.id.tv_payment);
        tv_expense = findViewById(R.id.tv_expenses);
        tv_balance = findViewById(R.id.tv_balance);
        img_open_calender = findViewById(R.id.img_open_calender);
        tv_set_date = findViewById(R.id.tv_set_date);
        edt_share = findViewById(R.id.edt_share);
        tv_select_custome_date = findViewById(R.id.tv_select_custome_date);
        ll_fileter_date = findViewById(R.id.ll_fileter_date);
        select_fileter_layout = findViewById(R.id.select_fileter_layout);


        // log to show slected date data

        Log.v("Diksha " , " select date >>>"+ tv_set_date.getText().toString().trim());

        ll_select_date = findViewById(R.id.ll_select_date);

        all_date_select_layout = findViewById(R.id.all_date_select_layout);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        createViewPager(viewPager);

        tv_edit_name = findViewById(R.id.tv_edit_name);
        tv_entrycar1 = findViewById(R.id.tv_entrycar1);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);




        createTabIcons();

        String name_file = new UserSession(getApplicationContext()).readPrefs(PTREF_LIST_NAME);

        Log.v("DIKSHA", "FILER_NAME>>" + name_file);

        if (!name_file.equals("") && !name_file.equals(null)) {

            tv_entrycar1.setText(name_file);
        }
        else {
            tv_entrycar1.setText("HAIL NOTE");
        }

      if (price_of_expence == 0) {

            tv_expense.setText(symbol + 0);
        } else {
            tv_expense.setText(symbol + price_of_expence);
        }


        if (price_of_payment == 0) {

            tv_payment.setText(symbol + 0);
        } else {
            tv_payment.setText(symbol + price_of_payment);
        }

        if (price_of_car == 0) {

            tv_taking.setText(symbol + 0);
        } else {
            tv_taking.setText(symbol + price_of_car);
        }

        price_of_total = price_of_car - price_of_expence;

        if (price_of_total == 0) {

            tv_balance.setText(symbol + 0);
        } else {
            tv_balance.setText(symbol + price_of_total);
        }



        //__________________________________________data base management____________________________________


        imv_addex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE, "");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME, "");
                new UserSession(getApplicationContext()).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME, "");
                Intent intent2 = new Intent(MainTotalBalanceAcitvity.this, AddEntryCar.class);
                startActivity(intent2);
                finish();

            }
        });


        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserSession userSession = new UserSession(MainTotalBalanceAcitvity.this);
                String dnt_show_msg =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME1);

                if(dnt_show_msg.equals("1")){

                    Intent i = new Intent(MainTotalBalanceAcitvity.this, Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else{

                 /*   Intent i = new Intent(MainTotalBalanceAcitvity.this, HomeTutorial.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();*/


                    Intent i = new Intent(MainTotalBalanceAcitvity.this, Home.class);
                    i.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }


            }
        });
        tv_entrycar1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    Log.i("EDITTTTT","Enter pressed");
                    name = tv_entrycar1.getText().toString();

                    if (!name.equals("") && !name.equals(null)) {

                        tv_entrycar1.setText(name);
                    } else {
                        tv_entrycar1.setText("HAIL NOTE");
                    }
                }
                return false;
            }
        });

        tv_entrycar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialog();

                //viewPager.getAdapter().notifyDataSetChanged();
                tv_entrycar1.setEnabled(true);
                tv_entrycar1.setFocusable(true);
                tv_entrycar1.isEnabled();
                tv_entrycar1.isEnabled();
                tv_entrycar1.hasWindowFocus();
                tv_entrycar1.isCursorVisible();
                tv_entrycar1.setCursorVisible(true);

                //tv_entrycar1.setText("");
            }
        });

        edt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);


            }
        });

        //  tv_entrycar1.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tv_entrycar1.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //tv_entrycar1.setText("");


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                name = tv_entrycar1.getText().toString();

                //  new UserSession(getApplicationContext()).writePrefs(EXPENSE_FILE_NAME, name);


                String  user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);


                dbManager.UPDATE_HOME_LIST(user_id,list_id,tv_entrycar1.getText().toString());
                new UserSession(getApplicationContext()).writePrefs(PTREF_LIST_NAME, tv_entrycar1.getText().toString());

                Log.v("DIKSHA","kahs"+tv_entrycar1.getText().toString());
                Log.v("DIKSHA","kahs"+user_id);
                Log.v("DIKSHA","kahs"+list_id);
                Log.v("DIKSHA","kahs"+tv_entrycar1.getText().toString());

            }
        });

        tv_entrycar1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tv_entrycar1.setCursorVisible(false);
                }
                return false;
            }
        });


        tv_entrycar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //viewPager.getAdapter().notifyDataSetChanged();
                tv_entrycar1.setEnabled(true);
                tv_entrycar1.setFocusable(true);
               // tv_entrycar1.setText("");

            }
        });


        // get currunt date

        c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());

        tv_set_date.setText(formattedDate);
        tv_set_date.setText(filter_date_txt);

        new UserSession(this).writePrefs(DATE_FOR_FRAG , tv_set_date.getText().toString());


       // String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param = sdf2.format(c.getTime());


        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE, date_param);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {




            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                //viewPager.getAdapter().notifyDataSetChanged();
            }

        };

        img_open_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             //old functionatity remove

/*
                ll_select_date.setVisibility(View.VISIBLE);
                all_date_select_layout.setVisibility(View.VISIBLE);*/


                if(date_count==0 ){

                    date_count= 1;

                    ll_select_date.setVisibility(View.VISIBLE);
                    all_date_select_layout.setVisibility(View.VISIBLE);
                    select_fileter_layout.setVisibility(View.VISIBLE);


                }else {

                    date_count=0;

                    ll_select_date.setVisibility(View.GONE);
                    all_date_select_layout.setVisibility(View.GONE);
                    select_fileter_layout.setVisibility(View.GONE);


                }



            }

        });


        img_open_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //old functionatity remove

/*
                ll_select_date.setVisibility(View.VISIBLE);
                all_date_select_layout.setVisibility(View.VISIBLE);*/


                if(date_count==0 ){

                    date_count= 1;

                    ll_select_date.setVisibility(View.VISIBLE);
                    all_date_select_layout.setVisibility(View.VISIBLE);
                    select_fileter_layout.setVisibility(View.VISIBLE);


                }else {

                    date_count=0;

                    ll_select_date.setVisibility(View.GONE);
                    all_date_select_layout.setVisibility(View.GONE);
                    select_fileter_layout.setVisibility(View.GONE);


                }



            }

        });




        tv_select_custome_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("ALL_DATE_SHOW",">>");

                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(DATE_FOR_FRAG , filter_date_txt);
                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(MAIN_DATE , filter_date_txt);
                tv_set_date.setText("All");


                // TODO Auto-generated method stub
                new DatePickerDialog(MainTotalBalanceAcitvity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();




            }
        });



        ll_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("ALL_DATE_SHOW",">>");

                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(DATE_FOR_FRAG , filter_date_txt);
                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(MAIN_DATE , filter_date_txt);
                tv_set_date.setText("All");


                // TODO Auto-generated method stub
                new DatePickerDialog(MainTotalBalanceAcitvity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });



        if(tv_set_date.getText().toString().equals(filter_date_txt)){



            new UserSession(this).writePrefs(DATE_FOR_FRAG , tv_set_date.getText().toString());
            new UserSession(getApplicationContext()).writePrefs(MAIN_DATE, tv_set_date.getText().toString());
        }

        //submitRegister();



        all_date_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("ALL_DATE_SHOW",">>");

                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(DATE_FOR_FRAG , filter_date_txt);
                new UserSession(MainTotalBalanceAcitvity.this).writePrefs(MAIN_DATE , filter_date_txt);
                tv_set_date.setText("All");


                ll_select_date.setVisibility(View.GONE);
                all_date_select_layout.setVisibility(View.GONE);
                select_fileter_layout.setVisibility(View.GONE);


            }
        });


    }


    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }


    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainTotalBalanceAcitvity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainTotalBalanceAcitvity.this,
                    new String[]{permission},
                    requestCode);


            showSelevtFileDialog(edt_share);

        } else {
            Toast.makeText(MainTotalBalanceAcitvity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();

            showSelevtFileDialog(edt_share);

        }
    }






    private void createTabIcons() {



        final TextView tabfour = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, null);
        tabfour.setText(R.string.all);
        tabLayout.getTabAt(4).setCustomView(tabfour);


        final TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, null);
        tabTwo.setText(R.string.payments);
        tabLayout.getTabAt(2).setCustomView(tabTwo);

        final TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, null);
        tabThree.setText(R.string.car);
        tabLayout.getTabAt(3).setCustomView(tabThree);

        final TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, null);
        tabOne.setText(R.string.balance2);
        tabLayout.getTabAt(1).setCustomView(tabOne);


        final TextView tab_zero = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, null);
        tabOne.setText(R.string.expenses);
        tabLayout.getTabAt(0).setCustomView(tab_zero);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {




                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                    Log.v("DIKSHA_09", ">>" + tab.getPosition());

                    if (tab.getPosition() == 0) {

                        tab_zero.setTextColor(getColor(R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tab_zero);

                    } else {
                        tab_zero.setTextColor(getColor(R.color.lightgrey));
                        tabLayout.getTabAt(0).setCustomView(tab_zero);

                    }
                    if (tab.getPosition() == 1) {

                        tabOne.setTextColor(getColor(R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabOne);
                    } else {

                        tabOne.setTextColor(getColor(R.color.lightgrey));
                        tabLayout.getTabAt(1).setCustomView(tabOne);
                    }

                    if (tab.getPosition() == 2) {

                        tabTwo.setTextColor(getColor(R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabTwo);
                    } else {

                        tabTwo.setTextColor(getColor(R.color.lightgrey));
                        tabLayout.getTabAt(2).setCustomView(tabTwo);
                    }

                    if (tab.getPosition() == 3) {
                        tabThree.setTextColor(getColor(R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabThree);
                    } else {

                        tabThree.setTextColor(getColor(R.color.lightgrey));
                        tabLayout.getTabAt(3).setCustomView(tabThree);

                    }

                    if (tab.getPosition() == 4) {
                        tabfour.setTextColor(getColor(R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabfour);
                    } else {

                        tabfour.setTextColor(getColor(R.color.lightgrey));
                        tabLayout.getTabAt(4).setCustomView(tabfour);

                    }

                } else {


                    Log.v("DIKSHA_09", ">>" + tab.getPosition());

                    if (tab.getPosition() == 0) {

                        tab_zero.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tab_zero);

                    } else {
                        tab_zero.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgrey));
                        tabLayout.getTabAt(0).setCustomView(tab_zero);

                    }
                    if (tab.getPosition() == 1) {

                        tabOne.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabOne);
                    } else {

                        tabOne.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgrey));
                        tabLayout.getTabAt(1).setCustomView(tabOne);
                    }

                    if (tab.getPosition() == 2) {

                        tabTwo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabTwo);
                    } else {

                        tabTwo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgrey));
                        tabLayout.getTabAt(2).setCustomView(tabTwo);
                    }

                    if (tab.getPosition() == 3) {
                        tabThree.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabThree);
                    } else {

                        tabThree.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgrey));
                        tabLayout.getTabAt(3).setCustomView(tabThree);

                    }

                    if (tab.getPosition() == 4) {
                        tabfour.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black2));
                        tabLayout.getTabAt(tab.getPosition()).setCustomView(tabfour);
                    } else {

                        tabfour.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgrey));
                        tabLayout.getTabAt(4).setCustomView(tabfour);

                    }



                }




            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BalanceFragment(), getString(R.string.balance2));

        adapter.addFrag(new ExpenseFragment(), getString(R.string.expenses));

        adapter.addFrag(new PaymentsFragment(), getString(R.string.payments));
        adapter.addFrag(new CarListFragment(), getString(R.string.car));
        adapter.addFrag(new AllListFragment(), getString(R.string.all));


        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }
    }


    public void dialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainTotalBalanceAcitvity.this, R.style.AlertDialogStyle);
        alertDialog.setTitle("Change Name");
        alertDialog.setMessage("Enter a new name");

        final EditText input = new EditText(MainTotalBalanceAcitvity.this);
        // input.setBackgroundResource(R.color.colorPrimary);

        input.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_light), PorterDuff.Mode.SRC_ATOP);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.edit);





        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        name = input.getText().toString();

                        new UserSession(getApplicationContext()).writePrefs(EXPENSE_FILE_NAME, name);

                        if (name.equals("")) {
                            tv_entrycar1.setText("HAIL NOTE");
                        } else {
                            tv_entrycar1.setText(name);
                        }
                        //  createViewPager(viewPager);


                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        alertDialog.show();


    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String myFormat2 = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        String date_param = sdf2.format(myCalendar.getTime());



        tv_set_date.setText("Date  " + sdf.format(myCalendar.getTime()));

        new UserSession(this).writePrefs(DATE_FOR_FRAG , sdf.format(myCalendar.getTime()));

        new UserSession(getApplicationContext()).writePrefs(MAIN_DATE, date_param);
        //   viewPager.getAdapter().notifyDataSetChanged();

        ll_select_date.setVisibility(View.GONE);
        all_date_select_layout.setVisibility(View.GONE);
        select_fileter_layout.setVisibility(View.GONE);


    }


    public interface FragmentRefreshListener {
        void onRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        price_of_car=0;
        price_of_payment=0;
        price_of_expence=0;
        price_of_total=0;
        no_of_car=0;

        String user_id = new UserSession(getApplicationContext()).readPrefs(PREFS_USER_ID);

        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();

        // dbManager.DELETE_TOTAL_TRANSACTION();

        new UserSession(MainTotalBalanceAcitvity.this).writePrefs(CURENCY_ONSELECT, currency);
        new UserSession(MainTotalBalanceAcitvity.this).writePrefs(SYMBOL_ONSELECT ,symbol);
        new UserSession(MainTotalBalanceAcitvity.this).writePrefs(HOME_LIST_ID ,list_id);
        new UserSession(MainTotalBalanceAcitvity.this).writePrefs(PTREF_LIST_NAME ,name1);

        dbManager.DELETE_TOTAL_TRANSACTION();

        fetchDataOfflineEpaymnet();
        fetchDataOfflineExpense();
        fetchDataOfflineCAR();


        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();


        Log.v("AUTO_FILE_ID22", ">>" + user_id);
        try {
            Log.v("AUTO_FILE_ID2233", ">>wih" + user_id);


            Cursor cursor = dbManager.getTransactuionTotal(user_id, list_id);
            int i = 0;
            Log.v("AUTO_FILE_ID2233", ">>" + cursor.getCount());

            PaymentList fileListPojo = new PaymentList();

            if (cursor.moveToFirst()) {

                do {

                    Log.v("AUTO_FILE_ID223344", ">>" + user_id);
                    trans_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_TANSCATION_ID));
                    Log.v("SEBY", "price_of_payment>>>>" + price_of_payment);

                }
                while(cursor.moveToNext());

            }


        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY", "hhhhhhhhh>>>>" + e);
        }


        fetchDataOfflineTRANSACTIONTOTAL();

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (!tv_entrycar1.getText().toString().equals("")) {

         //   tv_entrycar1.setText(name);
        } else {
            tv_entrycar1.setText("HAIL NOTE");
        }


    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Joy", "language " + base);
    }


    private void Export_as_file_API() {

        final ProgressDialog progressDialog = new ProgressDialog(MainTotalBalanceAcitvity.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.ADD_EXPORT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "EXPORT_RESPONSE" + response);

                        try {
                            if (progressDialog != null) {

                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                Toast.makeText(getApplicationContext(), R.string.downloading, Toast.LENGTH_SHORT).show();


                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                // TypeCarList expenseListPojo = gson.fromJson(response, TypeCarList.class);

                                JSONObject jo = new JSONObject(response);

                                String url = jo.optString("link");

                                Log.v("EXPORT_RESPONSE", ">>" + url);


                                AppUtility.downloadFileFromURL(MainTotalBalanceAcitvity.this, url);


                                openFolder();


                                //for soenload file

                                //   AppUtility.downloadFileFromURL(getApplicationContext(),jo.optString());

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

                        Toast.makeText(MainTotalBalanceAcitvity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {


            @Override
            protected Map<String, String> getParams() {

                String user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);


                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("from_date", tv_set_date.getText().toString());
                params.put("to_date", tv_set_date.getText().toString());
                params.put("type", file_type);
                params.put("list_id", list_id);

                Log.e("DIKSHA", "EXPORT_REQUSEST" + params);

                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(MainTotalBalanceAcitvity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


    public void showSelevtFileDialog(View view) {
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MainTotalBalanceAcitvity.this);
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

                if(!file_type.equals("")) {
                    Export_as_file_API();
                }

                else {

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


    public void openFolder() {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  File.separator + "Hail Note" + File.separator);
        intent.setDataAndType(uri, "text/pdf/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));
        Log.v("DIKSHAAA","AAAA>>"+uri);





    }



    public  void fetchDataOfflineCAR(){


        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();

        String  user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {



            Log.v("AUTO_FILE_ID2233",">>"+user_id);




            Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id , list_id , "car");
            int i=0;



            PaymentList fileListPojo = new PaymentList();

            if(cursor.moveToFirst()) {

                do {

                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));


                    price_of_car =  price_of_car + Integer.parseInt(payment_price);
                    Log.v("SEBY","price_of_car>>>>"+price_of_car);
                    i++;

                    Log.v("PRICEEEEE","EXPENCE_FATCH_CAR(TAKINGS)_PRICE IN>>>>"+price_of_car);

                } while (cursor.moveToNext());



                no_of_car=i;


                Log.v("PRICEEEEE","EXPENCE_FATCH_CAR(TAKINGS)_PRICE OUT>>>>"+price_of_car);

                dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id , list_id , "" , ""+price_of_car , "income" ,formattedDate );


            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","jjjjjjjjjjjjjj"+e);
        }












    }





    public  void fetchDataOfflineExpense(){

        price_of_expence =0;


        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();

        String  user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {



            Log.v("AUTO_FILE_ID2233",">>"+user_id);



            Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id , list_id , "expence");
            int i=0;



            PaymentList fileListPojo = new PaymentList();

            if(cursor.moveToFirst()) {

                do {
                    Log.v("AUTO_FILE_ID223344",">>"+user_id);

                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));

                    price_of_expence =  price_of_expence + Integer.parseInt(payment_price);

                    //Log.v("DIKSHAEXPE$NCE","price_of_expence>>>>"+price_of_expence);

                    Log.v("PRICEEEEE","EXPENCE_FATCH_EXPENCE_PRICE IN>>>>"+price_of_expence);

                } while (cursor.moveToNext());

                i++;


                Log.v("PRICEEEEE","EXPENCE_FATCH_EXPENCE_PRICE OUT>>>>"+price_of_expence);

                dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id , list_id , "" , ""+price_of_expence , "debit" ,formattedDate );



            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","mmmmmmmmmmmmmmmm"+e);
        }




    }



    public  void fetchDataOfflineEpaymnet(){

        price_of_payment=0;


        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();

        String  user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);

        Log.v("AUTO_FILE_ID22",">>"+user_id);
        try {



            Log.v("AUTO_FILE_ID2233",">>"+user_id);



            Cursor cursor = dbManager.FETCH_ALL_BY_TYPE(user_id , list_id , "payment");
            int i=0;



            PaymentList fileListPojo = new PaymentList();

            if(cursor.moveToFirst()) {

                do {

                    String payment_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PAY_PRICE));


                    price_of_payment =  price_of_payment + Integer.parseInt(payment_price);

                    Log.v("PRICEEEEE","EXPENCE_FATCH_PAYEMNT_PRICE IN>>>>"+price_of_payment);

                } while (cursor.moveToNext());

                i++;


                Log.v("PRICEEEEE","EXPENCE_FATCH_PAYEMNT_PRICE OUT>>>>"+price_of_payment);
                dbManager.INSERT_MAIN_TOTAL_TANSACTION(user_id , list_id , "" , ""+price_of_payment , "credit" ,formattedDate );


            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY","pppppppppppppp>>>>"+e);
        }





    }

    public  void fetchDataOfflineTRANSACTIONTOTAL() {


        dbManager = new DBManager(MainTotalBalanceAcitvity.this);
        dbManager.open();

        String user_id = new UserSession(MainTotalBalanceAcitvity.this).readPrefs(UserSession.PREFS_USER_ID);


        try {

            Cursor cursor = dbManager.getTransactuionTotal(user_id, list_id);
            int i = 0;


            PaymentList fileListPojo = new PaymentList();

            if (cursor.moveToFirst()) {

                do {


                    trans_auto_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AUTO_TANSCATION_ID));
                    String tra_list_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_LIST_ID));
                    String tra_user_id = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_USER_ID));
                    String trans_price = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_PRICE));
                    String tran_type = cursor.getString(cursor.getColumnIndex(DatabaseHandler.TRA_TYPE));


                    if (tran_type.equals("income")) {

                        price_of_car = Integer.parseInt(trans_price);
                    } else if (tran_type.equals("debit")) {
                        price_of_expence =  + Integer.parseInt(trans_price);

                    } else if (tran_type.equals("credit")) {

                        price_of_payment =  + Integer.parseInt(trans_price);

                    }


                } while (cursor.moveToNext());




                if (price_of_expence == 0) {

                    tv_expense.setText(symbol + 0);
                } else {
                    tv_expense.setText(symbol + price_of_expence);
                }


                if (price_of_payment == 0) {

                    tv_payment.setText(symbol + 0);
                } else {
                    tv_payment.setText(symbol + price_of_payment);
                }

                if (price_of_car == 0) {

                    tv_taking.setText(symbol + 0);
                } else {
                    tv_taking.setText(symbol + price_of_car);
                }

                price_of_total = price_of_car - price_of_expence;

                if (price_of_total == 0) {

                    tv_balance.setText(symbol + 0);
                } else {
                    tv_balance.setText(symbol + price_of_total);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.v("SEBY", "ooooooooooo>>>>" + e);
        }
    }

}