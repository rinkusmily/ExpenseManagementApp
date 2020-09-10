package com.shrinkcom.expensemanagementapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private static UserSession _instance = null;
    private static SharedPreferences _sPrefs,_sPrefs2 = null;
    private static SharedPreferences.Editor _editor = null;

    private static final String EXPENSE_APP_PREFS = "PRVIDERBydiksha";
    private static final String EXPENSE_APP_PREFS_2 = "PROVIDERBydikshaS";

    public static final String PREFS_USER_ID = "user_id";
    public static final String PREF_DEVICE_ID = "device_id";
    public static final String PREFS_NAME = "name";
    public static final String PREFS_EMAIL = "email";
    public static final String PREFS_PHONE = "phone";
    public static final String PTREF_CURRENCY = "currency";
    public static final String PTREF_LIST_NAME = "list_name";

    public static final String PREFS_ADDRESS = "address";
    public static final String PREFS_PROFILE_IMAGE = "profile_img";
    public static final String LOGIN_BY = "login_by";
    public static final String EXPENSE_FILE_NAME = "filename";
    public static final String EXPENSE_PAYMENT_TYPE = "type";
    public static final String EXPENSE_PAYMENT_TYPE_NAME = "p_name";
    public static final String CATEGORY_NAME_D = "cname";
    public static final String CAREGORY_IDD = "iddd";
    public static final String EXPENCE_IMAGE_VALUE = "img_val";
    public static final String MAIN_DATE = "main_date";

    public static final String DONT_SHOW_MSG_HOME1 = "dont_show_msg_home_one";
    public static final String DONT_SHOW_MSG_HOME2 = "dont_show_msg_home_tow";
    public static final String PREFER_LANGUAGE = "language";
    public static final String MOB = "mob";
    public static final String PINLOCK = "pinlock";
    public static final String STATUS_PINLOCK = "status_pinlock";
    public static final String STAUS_TOUCH_LOCK = "status_touch_lock";

    public static final String CURENCY = "currency2";
    public static final String CURENCY_SETTING = "curr_setting";
    public static final String CURENCY_ON_SELECT = "currency";
    public static final String CURENCY_SYMBOL = "currency_1";
    public static final String CURENCY_ONSELECT = "currency_2";
    public static final String SYMBOL_ONSELECT = "symbol_3";
    public static final String APPERIENCE_STATUS = "apperience_status";
    public static final String HOME_LIST_ID = "home_list_id";

    public static final String BACKUP_FILE = "backup";
    public static final String DATE_FOR_FRAG = "date_frag";
    public static final String OTP_SCREEN_VALUE = "false";


    public static final boolean PREFFS_USERSESSIOIN = true;


    Context mContext;


    public static final String PREFS_PROVIDER_SERVICE_ID = "Provider_Service_id";

    public UserSession() {
    }

    public UserSession(Context context) {
        mContext = context;
        _sPrefs = context.getSharedPreferences(EXPENSE_APP_PREFS,
                Context.MODE_PRIVATE);

        _sPrefs2 = context.getSharedPreferences(EXPENSE_APP_PREFS_2,
                Context.MODE_PRIVATE);
    }

    public static UserSession getInstance(Context context) {
        if (_instance == null) {
            _instance = new UserSession(context);
        }
        return _instance;
    }

    public String readPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writePrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }

    public void clearPrefs() {
        _editor = _sPrefs.edit();
        _editor.clear();
        _editor.commit();
    }

    public boolean readBooleanPrefs(String pref_name) {
        return _sPrefs.getBoolean(pref_name, false);
    }

    public void writeBooleanPrefs(String pref_name, boolean pref_val) {
        _editor = _sPrefs.edit();
        _editor.putBoolean(pref_name, pref_val);
        _editor.commit();
    }

    public String readDefaultLangPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writeDefaultLangPrefs(String pref_name) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_name);
        _editor.commit();
    }

    public String readLatLngPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "0.0");
    }

    public void writeLatLngPrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }

    public String readBackupPrefs(String pref_name) {
        return _sPrefs2.getString(pref_name, "");
    }

    public void writeBackupPrefs(String pref_name, String pref_val) {
        SharedPreferences.Editor _editor = _sPrefs2.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(EXPENSE_APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }




    public void setLanguage(String mob) {

        _editor = _sPrefs.edit();
        _editor.putString(PREFER_LANGUAGE, mob);
        _editor.commit();
    }

    public String getLanguage() {
        return _sPrefs.getString(PREFER_LANGUAGE, "en");
    }

}
