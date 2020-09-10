package com.shrinkcom.expensemanagementapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diksha on 25/4/2020.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // DATA BASE
    // Database Information
    static final String DB_NAME = "AUTODB.DB";
    // database version
    static final int DB_VERSION = 1;





    //_________________________________ADD HOME LIST_________________________________________


    //TABLE FOR ADD CAR


    public static final String TABLE_NAME_HOME_LIST = "table_Add_list";
    public static final String AUTO_HOME_LIST_ID = "auto_home_list_id";
    public static final String HOME_USER_ID = "home_user_id";
    public static final String HOME_LIST_ID = "home_list_id";
    public static final String HOME_LIST_NAME = "home_list_name";
    public static final String CURRUNCY = "currency";
    public static final String HOME_DATE = "homedate";
    public static final String BACKUP_SATATUS = "backup_status";



    //TABLE FIELDS
    // Creating table

    private static final String CREATE_TABLE_ADD_LIST= "create table " + TABLE_NAME_HOME_LIST + "("
            + AUTO_HOME_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HOME_USER_ID + " TEXT, "
            + HOME_LIST_ID + " TEXT, "
            + HOME_LIST_NAME + " TEXT, "
            + CURRUNCY + " TEXT, "
            + HOME_DATE + " TEXT, "
            + BACKUP_SATATUS + " TEXT);";




    //_________________________________CATEGORY_TABLE_________________________________________


    //TABLE FOR ADD CAR


    public static final String TABLE_ADD_CATEGORY= "category_table";
    public static final String AUTO_CATEGORY_ID = "auto_category_id";
    public static final String CA_USER_ID = "user_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_IMAGE = "category_image";
    public static final String CA_BACKUP_STATUS = "ca_backup_satatus";
    public static final String CATEGORY_LIST_ID = "category_list_id";
    public static final String TYPE = "type";




    //TABLE FIELDS
    // Creating table

    private static final String CREATE_ADD_CATEGORY= "create table " + TABLE_ADD_CATEGORY + "("
            + AUTO_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CA_USER_ID + " TEXT, "
            + CATEGORY_NAME + " TEXT, "
            + HOME_LIST_NAME + " TEXT, "
            + CATEGORY_IMAGE + " TEXT, "
            + CA_BACKUP_STATUS + " TEXT, "
            + TYPE + " TEXT, "
            + CATEGORY_LIST_ID + " TEXT);";






    //_________________________________MAIN_TOTAL_SCREEN________________________________________


    //TABLE FOR ADD CAR



    public static final String TABLE_MAIN_TOTAL= "transaction_table";
    public static final String AUTO_TANSCATION_ID = "auto_trans_id";
    public static final String TRA_USER_ID = "tra_user_id";
    public static final String TRA_PRICE = "tra_price";
    public static final String TRA_TYPE = "tra_type";
    public static final String TRA_CREATED_AT = "tra_create_id";
    public static final String TRA_PAYMENT_ID = "tra_patyment_id";
    public static final String TRA_LIST_ID = "tra_list_id";




    //TABLE FIELDS
    // Creating table

    private static final String CREATE_MAIN_TOTAL_TABLE= "create table " + TABLE_MAIN_TOTAL + "("
            + AUTO_TANSCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRA_USER_ID + " TEXT, "
            + TRA_PRICE + " TEXT, "
            + TRA_TYPE + " TEXT, "
            + TRA_CREATED_AT + " TEXT, "
            + TRA_PAYMENT_ID + " TEXT, "
            + TRA_LIST_ID + " TEXT);";




    //_________________________________ADD_PAYMENT_ALL( PAYEMNT <>< EXPENCE < ENTRY CAR)________________________________________


    //TABLE FOR ADD CAR


    public static final String TABLE_NAME_PAYMENT= "table_name_payment";
    public static final String AUTO_PAYMENT_ID = "auto_paument_id";
    public static final String PAY_LIST_ID = "payent_list_id";
    public static final String PAY_USER_ID = "payment_user_id";
    public static final String PAY_PRICE = "payment_price";
    public static final String PAYMENT_DATE = "payment_s_date";
    public static final String PAY_CATEGORY_ID = "payment_category_id";
    public static final String PAY_NOTE = "payment_note";
    public static final String PAY_TYPE = "payment_type";
    public static final String PAYE_CREATED_AT = "payment_careated_at";  // no uses
    public static final String PAY_PLATE_NO = "pay_plate_no";
    public static final String PAY_MODEL = "pay_model";
    public static final String FINISHED_STATUS = "finished_status";
    public static final String INVOICE_STATUS = "invoiced_status";
    public static final String PAID_OUT_STATUS = "paid_out_status";
    public static final String PAY_ICON = "poayment_icon";
    public static final String COMPANY = "company";
    public static final String TYPE_NAME = "type_name";





    //TABLE FIELDS
    // Creating table

    private static final String CREATE_PAYMENT_TABLE= "create table " + TABLE_NAME_PAYMENT + "("
            + AUTO_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PAY_LIST_ID + " TEXT, "
            + PAY_USER_ID + " TEXT, "
            + PAY_PRICE + " TEXT, "
            + PAYMENT_DATE + " TEXT, "
            + PAY_CATEGORY_ID + " TEXT, "
            + PAY_NOTE + " TEXT, "
            + PAY_TYPE + " TEXT, "
            + PAYE_CREATED_AT + " TEXT, "
            + PAY_PLATE_NO + " TEXT, "
            + PAY_MODEL + " TEXT, "
            + FINISHED_STATUS + " TEXT, "
            + INVOICE_STATUS + " TEXT, "
            + PAID_OUT_STATUS + " TEXT, "
            + PAY_ICON + " TEXT, "
            + COMPANY + " TEXT, "
            + TYPE_NAME + " TEXT);";



   /* //_________________________________ADD CAR_________________________________________


     //TABLE FOR ADD CAR


    public static final String TABLE_NAME_ADD_CAR = "table_add_car";
    public static final String AUTO_ADD_CAR = "auto_add_car_id";
    public static final String ADD_CAR_USER_ID = "add_car_user_id";
    public static final String ADD_CAR_AMOUNT = "add_car_amount";
    public static final String ADD_CAR_DATE = "addcar_date";
    public static final String ADD_CAR_MODEL = "add_car_model";
    public static final String ADD_CAR_PLATE_NO = "add_car_plate_no";
    public static final String ADD_CAR_NOTES = "add_car_notes";


     //TABLE FIELDS
    // Creating table

    private static final String CREATE_TABLE_ADD_CAR = "create table " + TABLE_NAME_ADD_CAR + "("
             + AUTO_ADD_CAR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + ADD_CAR_USER_ID + " TEXT, "
             + ADD_CAR_AMOUNT + " TEXT, "
             + ADD_CAR_DATE + " TEXT, "
             + ADD_CAR_MODEL + " TEXT, "
             + ADD_CAR_PLATE_NO + " TEXT, "
             + ADD_CAR_NOTES + " TEXT);";


//____________________________________________ADD PAYMENT______________________________________________________

    //TABLE FOR ADD PAYMENT
    // createing table for user profile it will call at the time of registration
    public static final String TABLE_NAME_ADD_PAYMENT = "table_add_payment";
    public static final String AUTO_ADD_PAYMENT_ID = "auto_add_payment_id";
    public static final String ADD_PAYMENT_USER_ID = "add_payment_user_id";
    public static final String ADD_PAYMENT_AMOUNT = "add_payment_amount";
    public static final String ADD_PAYMENT_DATE = "add_payment_date";
    public static final String ADD_PAYMENT_TYPE = "add_payment_type";
    public static final String ADD_PAYMENT_NOTE = "add_payment_note";

    //TABLE FIELDS
    // Creating table
    private static final String CREATE_TABLE_ADD_PAYMENT = "create table " + TABLE_NAME_ADD_PAYMENT + "("
            + AUTO_ADD_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ADD_PAYMENT_USER_ID + " TEXT, "
            + ADD_PAYMENT_AMOUNT + " TEXT, "
            + ADD_PAYMENT_DATE + " TEXT, "
            + ADD_PAYMENT_TYPE + " TEXT, "
            + ADD_PAYMENT_NOTE + " TEXT);";





    //____________________________________________ADD EXPENSES______________________________________________________

    //TABLE FOR ADD PAYMENT
    // createing table for user profile it will call at the time of registration
    public static final String TABLE_NAME_ADD_EXPENSES = "table_add_expenses";
    public static final String AUTO_ADD_EXPENSES_ID = "auto_add_expenses_id";
    public static final String ADD_EXPENSES_USER_ID = "add_expenses_user_id";
    public static final String ADD_EXPENSES_AMOUNT = "add_expenses_amount";
    public static final String ADD_EXPENSES_DATE = "add_expenses_date";
    public static final String ADD_EXPENSES_TYPE = "add_expenses_type";
    public static final String ADD_EXPENSES_NOTE = "add_expenses_note";

    //TABLE FIELDS
    // Creating table
    private static final String CREATE_TABLE_ADD_EXPENSES = "create table " + TABLE_NAME_ADD_EXPENSES + "("
            + AUTO_ADD_EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ADD_EXPENSES_USER_ID + " TEXT, "
            + ADD_EXPENSES_AMOUNT + " TEXT, "
            + ADD_EXPENSES_DATE + " TEXT, "
            + ADD_EXPENSES_TYPE + " TEXT, "
            + ADD_EXPENSES_NOTE + " TEXT);";


    //____________________________________________ALl List______________________________________________________

    //TABLE FOR ADD PAYMENT
    // createing table for user profile it will call at the time of registration
    public static final String TABLE_NAME_ALL_LIST = "table_name_all_list";
    public static final String AUTO_ALL_LIST_ID = "auto_all_list_id";
    public static final String ALL_LIST_USER_ID = "all_list_user_id";
    public static final String ALL_LIST_AMOUNT = "all_list_amount";
    public static final String ALL_LIST_DATE = "all_list_date";
    public static final String ALL_LIST_TYPE = "all_list_type";
    public static final String ALL_LIST_NOTE = "all_list_note";
    public static final String ALL_LIST_MAIN_TYPE = "all_list_main_type";

    //TABLE FIELDS
    // Creating table
    private static final String CREATE_TABLE_ALl_LISR = "create table " + TABLE_NAME_ALL_LIST + "("
            + AUTO_ALL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALL_LIST_USER_ID + " TEXT, "
            + ALL_LIST_AMOUNT + " TEXT, "
            + ALL_LIST_DATE + " TEXT, "
            + ALL_LIST_TYPE + " TEXT, "
            + ALL_LIST_NOTE + " TEXT, "
            + ALL_LIST_MAIN_TYPE + " TEXT);";



    //____________________________________________TOTAL_BALANCE______________________________________________________

    //TABLE FOR ADD PAYMENT
    // createing table for user profile it will call at the time of registration
    public static final String TABLE_NAME_TOTAL_BALANCE= "table_total_balance";
    public static final String AUTO_TOTAL_BALANCE_ID = "auto_total_balance_id";
    public static final String TOTAL_BALANCE_USER_ID = "total_balance_user_id";
    public static final String TOTAL_BALANCE_DATE = "total_balance_date";

    public static final String TOTAL_TAKINGS = "total_takings";
    public static final String TOTAL_EXPENSES = "total_expenses";
    public static final String TOTAL_PAYMENTS = "total_payments";
    public static final String TOTAL_BALANCE = "total_balance";


    //TABLE FIELDS
    // Creating table
    private static final String CREATE_TABLE_TOTAL_BALANCE = "create table " + TABLE_NAME_TOTAL_BALANCE + "("
            + AUTO_TOTAL_BALANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TOTAL_BALANCE_USER_ID + " TEXT, "
            + TOTAL_BALANCE_DATE + " TEXT, "
            + TOTAL_TAKINGS + " TEXT, "
            + TOTAL_EXPENSES + " TEXT, "
            + TOTAL_PAYMENTS + " TEXT, "
            + TOTAL_BALANCE + " TEXT);";
*/

    //__________________________________________________________________________________________________



    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     /*   db.execSQL(CREATE_TABLE_ADD_CAR);
        db.execSQL(CREATE_TABLE_ADD_PAYMENT);
        db.execSQL(CREATE_TABLE_ADD_EXPENSES);
        db.execSQL(CREATE_TABLE_ALl_LISR);
        db.execSQL(CREATE_TABLE_TOTAL_BALANCE);*/
        db.execSQL(CREATE_TABLE_ADD_LIST);
        db.execSQL(CREATE_ADD_CATEGORY);
        db.execSQL(CREATE_MAIN_TOTAL_TABLE);
        db.execSQL(CREATE_PAYMENT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

/*        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ADD_CAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ADD_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ADD_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ALL_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TOTAL_BALANCE);*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOME_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADD_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_TOTAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PAYMENT);

        onCreate(db);

    }






}
