package com.shrinkcom.expensemanagementapp.Database;

/**
 * Created by Diksha on 25/4/2020.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.shrinkcom.expensemanagementapp.Database.DatabaseHandler.TABLE_ADD_CATEGORY;
import static com.shrinkcom.expensemanagementapp.Database.DatabaseHandler.TABLE_MAIN_TOTAL;
import static com.shrinkcom.expensemanagementapp.Database.DatabaseHandler.TABLE_NAME_HOME_LIST;
import static com.shrinkcom.expensemanagementapp.Database.DatabaseHandler.TABLE_NAME_PAYMENT;

public class DBManager {

    private DatabaseHandler dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHandler(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {

        dbHelper.close();
    }

    //_________________________________________ADD Home lIST____________________________________________________

    //INSERT ADD CAR
    public long INSERT_HOME_LIST(String user_id  , String home_list_id , String list_name ,String currency ,String date ,String backup_status) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHandler.HOME_USER_ID, user_id);
        contentValue.put(DatabaseHandler.HOME_LIST_ID, home_list_id);
        contentValue.put(DatabaseHandler.HOME_LIST_NAME, list_name);
        contentValue.put(DatabaseHandler.CURRUNCY, currency);
        contentValue.put(DatabaseHandler.HOME_DATE, date);
        contentValue.put(DatabaseHandler.BACKUP_SATATUS, backup_status);

      long id =   database.insert(TABLE_NAME_HOME_LIST, null, contentValue);

      return id;
    }




    public Cursor fatch_all_home_list() {

        String[] columns = new String[] { DatabaseHandler.AUTO_HOME_LIST_ID, DatabaseHandler.HOME_USER_ID,
                DatabaseHandler.HOME_LIST_ID , DatabaseHandler.HOME_LIST_NAME
                , DatabaseHandler.CURRUNCY , DatabaseHandler.HOME_DATE , DatabaseHandler.BACKUP_SATATUS};

        Cursor cursor = database.query(DatabaseHandler.TABLE_NAME_HOME_LIST, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //FATCH ADD CAR
    public Cursor FETCH_HOME_LIST(String user_id) {

        String query = "select * from "+ TABLE_NAME_HOME_LIST+" where "+ DatabaseHandler.HOME_USER_ID+"='"+user_id+"'";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public  int  UPDATE_HOME_LIST(String user_id,String home_list_id , String listname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.HOME_LIST_NAME, listname);

        Log.v("dataA ","upadte list name >>");
        int i = database.update(TABLE_NAME_HOME_LIST, contentValues, DatabaseHandler.HOME_LIST_ID+"='"+home_list_id+"'"+" and "+ DatabaseHandler.HOME_USER_ID+"='"+user_id+"'", null);

        return i;
    }


    //DELETE ALL ADDED CAR
    public void DELETE_HOME_LIST() {

        database.execSQL("delete from "+ TABLE_NAME_HOME_LIST);
    }
    //DELETE ALL ADDED CAR
    public void DELETE_File_by_user_id(String  user_id , String list_id) {


       database.execSQL("delete from "+ DatabaseHandler.TABLE_NAME_HOME_LIST +" where "+ DatabaseHandler.HOME_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.HOME_LIST_ID+"='"+list_id+"'");
    }

    //DELETE ALL ADDED CAR
    public void DELETE_FILE_TRANSACTION(String  user_id , String list_id) {


        database.execSQL("delete from "+ DatabaseHandler.TABLE_MAIN_TOTAL +" where "+ DatabaseHandler.TRA_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.TRA_LIST_ID+"='"+list_id+"'");
    }
    //DELETE ALL ADDED CAR
    public void DELETE_FILE_PAYMENT(String  user_id , String list_id) {

        database.execSQL("delete from "+ TABLE_NAME_PAYMENT +" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'");
    }





    //_________________________________________ADD CATEGORY____________________________________________________



    //INSERT ADD CARS
    public void INSERT_CATEGORY(String cat_user_id  , String category_name , String category_image ,String cat_backup_status ,String category_type) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.CA_USER_ID, cat_user_id);
        contentValue.put(DatabaseHandler.CATEGORY_NAME, category_name);
        contentValue.put(DatabaseHandler.CATEGORY_IMAGE, category_image);
        contentValue.put(DatabaseHandler.CA_BACKUP_STATUS, cat_backup_status);
        contentValue.put(DatabaseHandler.TYPE, category_type);
        long insertedvalue =   database.insert(TABLE_ADD_CATEGORY, null, contentValue);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.CATEGORY_LIST_ID, insertedvalue);
        int i = database.update(TABLE_ADD_CATEGORY, contentValues, DatabaseHandler.AUTO_CATEGORY_ID  + " = " +  insertedvalue,null);
        Log.e("UPDATEDATATAA",">>"+i);

    }


    public void INSERTSERVERCATEGORY(String cat_user_id  , String category_name , String category_image ,String cat_backup_status ,String category_type, String categoryid) {



        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.CA_USER_ID, cat_user_id);
        contentValue.put(DatabaseHandler.CATEGORY_NAME, category_name);
        contentValue.put(DatabaseHandler.CATEGORY_IMAGE, category_image);
        contentValue.put(DatabaseHandler.CA_BACKUP_STATUS, cat_backup_status);
        contentValue.put(DatabaseHandler.TYPE, category_type);
        contentValue.put(DatabaseHandler.CATEGORY_LIST_ID, categoryid);
        database.insert(TABLE_ADD_CATEGORY, null, contentValue);


    }


    public Cursor fatch_all_category() {

        String[] columns = new String[] { DatabaseHandler.CATEGORY_LIST_ID, DatabaseHandler.CA_USER_ID,
                DatabaseHandler.CATEGORY_NAME , DatabaseHandler.CATEGORY_IMAGE
                , DatabaseHandler.CA_BACKUP_STATUS , DatabaseHandler.TYPE};

        Cursor cursor = database.query(TABLE_ADD_CATEGORY, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }




    //FATCH ADD CAR
    public Cursor FETCH_CATEGORY_LIST_TO_SYNC(String user_id) {

        String query = "select * from "+ TABLE_ADD_CATEGORY+" where "+ DatabaseHandler.CA_USER_ID+"='"+user_id+"'" ;
        Cursor cursor = database.rawQuery(query,null);



        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    //FATCH ADD CAR
    public Cursor FETCH_CATEGORY_LIST(String user_id , String  cet_type) {

        String query = "select * from "+ TABLE_ADD_CATEGORY+" where "+ DatabaseHandler.CA_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.TYPE+"='"+cet_type+"'" ;
        Cursor cursor = database.rawQuery(query,null);



        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  int  UPDATE_CATEGORY(String user_id,String cat_lsit_id , String category_id ,String category_name , String category_image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.CATEGORY_NAME, category_name);
        contentValues.put(DatabaseHandler.CATEGORY_IMAGE, category_image);
        int i = database.update(TABLE_ADD_CATEGORY, contentValues, DatabaseHandler.AUTO_CATEGORY_ID  + " = " +  category_id +"&"+ DatabaseHandler.CA_USER_ID + " = " +  user_id, null);

        return i;
    }

    //DELETE ALL ADDED CAR
    public void DELETE_CATEGORY() {

        database.execSQL("delete from "+ TABLE_ADD_CATEGORY);
    }


    //DELETE ALL ADDED CAR
    public void DELETE_CATEGORY_BY_USER(String  user_id , String type , String category_id) {

        database.execSQL("delete from "+ TABLE_ADD_CATEGORY +" where "+ DatabaseHandler.CA_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.TYPE+"='"+type+"'" + " and "+ DatabaseHandler.CATEGORY_LIST_ID+"='"+category_id+"'");
    }

    public  int  UPDATE_CATEGORY(String user_id,String category_id, String category_name ,String category_img){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.CATEGORY_NAME, category_name);
        contentValues.put(DatabaseHandler.CATEGORY_IMAGE, category_img);

        int i = database.update(TABLE_ADD_CATEGORY, contentValues, DatabaseHandler.CA_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.CATEGORY_LIST_ID+"='"+category_id+"'", null);

        return i;
    }


    //_________________________________________TANSACTION_TABLE____________________________________________________





    //INSERT ADD CAR
    public void INSERT_MAIN_TOTAL_TANSACTION( String tra_user_id  ,String list_id,String payment_id ,String tran_price , String type ,String created_at ) {
        ContentValues contentValue = new ContentValues();


        contentValue.put(DatabaseHandler.TRA_USER_ID, tra_user_id);
        contentValue.put(DatabaseHandler.TRA_LIST_ID, list_id);
        contentValue.put(DatabaseHandler.TRA_PAYMENT_ID, "");
        contentValue.put(DatabaseHandler.TRA_PRICE, tran_price);
        contentValue.put(DatabaseHandler.TRA_TYPE, type);
        contentValue.put(DatabaseHandler.TRA_CREATED_AT, created_at);


         Log.v("SEBYYYYYYYY","DATA>>>"+tra_user_id);
         Log.v("SEBYYYYYYYY","DATA>>>"+list_id);
         Log.v("SEBYYYYYYYY","DATA>>>"+payment_id);
         Log.v("SEBYYYYYYYY","DATA>>>"+tran_price);
         Log.v("SEBYYYYYYYY","DATA>>>"+type);
     long data =    database.insert(TABLE_MAIN_TOTAL, null, contentValue);
        Log.v("SEBYYYYYYYY","DATA>>>data   "+data);
    }



    //FATCH ADD CAR
    public Cursor getTransactuionTotal(String user_id , String  list_id) {

        String query = "select * from "+ TABLE_MAIN_TOTAL+" where "+ DatabaseHandler.TRA_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.TRA_LIST_ID+"='"+list_id+"'" ;
        Cursor cursor = database.rawQuery(query,null);



        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //FATCH ADD CAR
    public Cursor FETCH_MAIN_TOTAL_TANSACTION(String user_id ) {

        String query = "select * from "+ TABLE_MAIN_TOTAL+" where "+ DatabaseHandler.TRA_USER_ID+"='"+user_id+"'";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  int  UPDATE_TOTAL_TANSACTION(String user_id,String auto_trans_id , String lisst_id, String cate_type ,String price){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.TRA_PRICE, price);

        int i = database.update(TABLE_MAIN_TOTAL, contentValues, DatabaseHandler.AUTO_TANSCATION_ID + "=\"" + auto_trans_id + "\"", null);

        return i;
    }

    //DELETE ALL ADDED CAR
    public void DELETE_TOTAL_TRANSACTION() {

        database.execSQL("delete from "+ TABLE_MAIN_TOTAL);
    }






    public int checkAvailablesChakra_id(String list_id) {

        Cursor cursor = null;
        String sql = "SELECT * FROM " + TABLE_MAIN_TOTAL + " WHERE " + DatabaseHandler.TRA_LIST_ID + "='" + list_id + "'";
        cursor = database.rawQuery(sql, null);



        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();

            Log.v("DIKSHA","TRANSACTION  return 1>>"+"");
            return 1;
        } else {
            //PID Not Found
            cursor.close();

            Log.v("DIKSHA","TRANSACTION  return 0 >>"+"");
            return 0;
        }



    }


    public  int  update_trans(String user_id,String list_id ,String  trans_id,String price , String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.TRA_PRICE, price);

        int i = database.update(TABLE_MAIN_TOTAL, contentValues, DatabaseHandler.TRA_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.TRA_LIST_ID+"='"+list_id+"'"+" and "+ DatabaseHandler.TRA_TYPE+"='"+type+"'" +" and "+ DatabaseHandler.AUTO_TANSCATION_ID+"='"+trans_id+"'", null);

        return i;
    }


    public Cursor fatch_all_trans() {

      /*  String[] columns = new String[] { DatabaseHandler.AUTO_TANSCATION_ID, DatabaseHandler.TRA_USER_ID,
                DatabaseHandler.TRA_LIST_ID , DatabaseHandler.TRA_PAYMENT_ID
                , DatabaseHandler.TRA_PRICE , DatabaseHandler.TRA_TYPE ,DatabaseHandler.TRA_CREATED_AT,};

        Cursor cursor = database.query(DatabaseHandler.TABLE_MAIN_TOTAL, columns, null, null, null, null, null);
      */
        Cursor  cursor = database.rawQuery("select * from "+ TABLE_MAIN_TOTAL,null);
      if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //_________________________________________ADD Home lIST____________________________________________________





    //INSERT ADD CAR
    public void INSERT_ALL_PAYMENTS(String payent_list_id  , String payment_user_id ,
                                     String payment_price ,String payment_date ,String payment_category_id ,
                                     String payment_note ,String payment_type ,String payment_careated_at ,
                                     String pay_plate_no , String pay_model , String finished_status,
                                     String invoiced_status,String paid_out_status,String payment_icon , String company , String type_name

                                     ) {
        ContentValues contentValue = new ContentValues();


        contentValue.put(DatabaseHandler.PAY_LIST_ID, payent_list_id);
        contentValue.put(DatabaseHandler.PAY_USER_ID, payment_user_id);
        contentValue.put(DatabaseHandler.PAY_PRICE, payment_price);
        contentValue.put(DatabaseHandler.PAYMENT_DATE, payment_date);
        contentValue.put(DatabaseHandler.PAY_CATEGORY_ID, payment_category_id);
        contentValue.put(DatabaseHandler.PAY_NOTE, payment_note);
        contentValue.put(DatabaseHandler.PAY_TYPE, payment_type);
        contentValue.put(DatabaseHandler.PAYE_CREATED_AT, payment_careated_at);
        contentValue.put(DatabaseHandler.PAY_PLATE_NO, pay_plate_no);
        contentValue.put(DatabaseHandler.PAY_MODEL, pay_model);
        contentValue.put(DatabaseHandler.FINISHED_STATUS, finished_status);
        contentValue.put(DatabaseHandler.INVOICE_STATUS, invoiced_status);
        contentValue.put(DatabaseHandler.PAID_OUT_STATUS, paid_out_status);
        contentValue.put(DatabaseHandler.PAY_ICON, payment_icon);
        contentValue.put(DatabaseHandler.COMPANY, company);
        contentValue.put(DatabaseHandler.TYPE_NAME, type_name);

        database.insert(TABLE_NAME_PAYMENT, null, contentValue);
    }






  /*  //FATCH ADD CAR
    public Cursor FETCH_AQLL_PAYMENTS(String user_id, String list_id , String date_1) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);
        Log.v("DIKSHA_dataaa",">>>"+date_1);

        String query = "select * from "+ DatabaseHandler.TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'" + " and "+ DatabaseHandler.PAY_DATE+"='"+date_1+"'";
        Cursor cursor = database.rawQuery(query,null);
        Log.v("DIKSHA_dataaa","222>>>"+ DatabaseHandler.PAY_DATE);

        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }*/



    //FATCH ADD CAR
    public Cursor FETCH_AQLL_PAYMENTS(String user_id, String list_id ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'";
        Cursor cursor = database.rawQuery(query,null);


        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //FATCH ADD CAR
    public Cursor FETCH_PAYMENTS_BY_DATE(String user_id, String list_id ,String sdate ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'" +" and "+ DatabaseHandler.PAYMENT_DATE+"='"+sdate+"'";
        Cursor cursor = database.rawQuery(query,null);

        Log.v("DIKSHA_dataaa","sdate>>>"+sdate);
        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    //FATCH ADD CAR
    public Cursor FETCH_PAYMENTS_BY_DATE_ALL_DATE(String user_id, String list_id ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'";
        Cursor cursor = database.rawQuery(query,null);


        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    //FATCH ADD CAR
    public Cursor FETCH_PAYMENTS_BY_DATE_TYPE(String user_id, String list_id ,String date , String type ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'" +" and "+ DatabaseHandler.PAYMENT_DATE+"='"+date+"'" +" and "+ DatabaseHandler.PAY_TYPE+"='"+type+"'";

        Cursor cursor = database.rawQuery(query,null);


        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //FATCH ADD CAR
    public Cursor FETCH_PAYMENTS_BY_DATE_TYPE_ALL_DATE_DATA(String user_id, String list_id  , String type ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'" +" and "+ DatabaseHandler.PAY_TYPE+"='"+type+"'";

        Cursor cursor = database.rawQuery(query,null);


        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //FATCH ADD CAR
    public Cursor FETCH_ALL_BY_TYPE(String user_id, String list_id, String type ) {

        Log.v("DIKSHA_dataaa",">>>"+user_id);
        Log.v("DIKSHA_dataaa",">>>"+list_id);


        String query = "select * from "+ TABLE_NAME_PAYMENT+" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+ " and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'"+ " and "+ DatabaseHandler.PAY_TYPE+"='"+type+"'";
        Cursor cursor = database.rawQuery(query,null);


        Log.v("DIKSHA_QUERY",">>>"+query);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }




    public Cursor fatch_all_payment() {


      /*  String[] columns = new String[] { DatabaseHandler.AUTO_PAYMENT_ID, DatabaseHandler.PAY_PRICE,
                DatabaseHandler.PAYMENT_DATE , DatabaseHandler.PAY_CATEGORY_ID
                , DatabaseHandler.PAY_LIST_ID , DatabaseHandler.PAY_NOTE , DatabaseHandler.PAY_TYPE  , DatabaseHandler.PAYE_CREATED_AT
        ,DatabaseHandler.PAY_PLATE_NO ,DatabaseHandler.PAY_MODEL ,DatabaseHandler.FINISHED_STATUS , DatabaseHandler.INVOICE_STATUS
                ,DatabaseHandler.PAID_OUT_STATUS , DatabaseHandler.PAY_ICON , DatabaseHandler.COMPANY , DatabaseHandler.TYPE_NAME
        };

        Cursor cursor = database.query(TABLE_NAME_PAYMENT, columns, null, null, null, null, null);
       */
        Cursor  cursor = database.rawQuery("select * from "+TABLE_NAME_PAYMENT,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }








    public  int  UPDATE_PAYMENT(String user_id,String payment_id,String list_id,String category_id, String price ,String date ,String model , String plate  , String compny_name ,   String type , String note ,String category_name ,String category_img ,String finished_status,String invoice_status ,String paidout_status){
        ContentValues contentValues = new ContentValues();


        contentValues.put(DatabaseHandler.PAY_PRICE, price);
        contentValues.put(DatabaseHandler.PAYMENT_DATE, date);
        contentValues.put(DatabaseHandler.PAY_CATEGORY_ID, category_id);
        contentValues.put(DatabaseHandler.PAY_LIST_ID, list_id);
        contentValues.put(DatabaseHandler.PAY_NOTE, note);
        contentValues.put(DatabaseHandler.PAY_TYPE, type);
        contentValues.put(DatabaseHandler.PAYE_CREATED_AT, date);
        contentValues.put(DatabaseHandler.PAY_PLATE_NO, plate);
        contentValues.put(DatabaseHandler.PAY_MODEL, model);
        contentValues.put(DatabaseHandler.FINISHED_STATUS, finished_status);
        contentValues.put(DatabaseHandler.INVOICE_STATUS, invoice_status);
        contentValues.put(DatabaseHandler.PAID_OUT_STATUS, paidout_status);
        contentValues.put(DatabaseHandler.PAY_ICON, category_img);
        contentValues.put(DatabaseHandler.COMPANY, compny_name);
        contentValues.put(DatabaseHandler.TYPE_NAME, category_name);



        int i = database.update(TABLE_NAME_PAYMENT, contentValues, DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'"+" and "+ DatabaseHandler.AUTO_PAYMENT_ID+"='"+payment_id+"'", null);

        return i;
    }





    public  int  UPDATE_PAYMENT_BOX_FINISH(String user_id,String payment_id,String list_id,String finished_status,String type){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHandler.FINISHED_STATUS, finished_status);


        int i = database.update(TABLE_NAME_PAYMENT, contentValues, DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'"+" and "+ DatabaseHandler.AUTO_PAYMENT_ID+"='"+payment_id+"'", null);

        return i;
    }

       public  int  UPDATE_PAYMENT_BOX_INVOICE(String user_id,String payment_id,String list_id,String invoice_status ,String type){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHandler.INVOICE_STATUS, invoice_status);





        int i = database.update(TABLE_NAME_PAYMENT, contentValues, DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'"+" and "+ DatabaseHandler.AUTO_PAYMENT_ID+"='"+payment_id+"'", null);

        return i;
    }

    public  int  UPDATE_PAYMENT_PAID_OUT(String user_id,String payment_id,String list_id,String paidout_status ,String type){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHandler.PAID_OUT_STATUS, paidout_status);




        int i = database.update(TABLE_NAME_PAYMENT, contentValues, DatabaseHandler.PAY_USER_ID+"='"+user_id+"'"+" and "+ DatabaseHandler.PAY_LIST_ID+"='"+list_id+"'"+" and "+ DatabaseHandler.AUTO_PAYMENT_ID+"='"+payment_id+"'", null);

        return i;
    }



    public  int  UPDATE_FILE_NAME(String user_id,String home_list_id , String listname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.HOME_LIST_NAME, listname);
        int i = database.update(TABLE_NAME_PAYMENT, contentValues, DatabaseHandler.HOME_USER_ID + " = " +  user_id +"&"+ DatabaseHandler.HOME_LIST_ID + " = " +  home_list_id, null);

        return i;
    }

    //DELETE ALL ADDED CAR
    public void DELETE_ALL_PAYMENTS() {

        database.execSQL("delete from "+ TABLE_NAME_PAYMENT);
    }


    public void DELETE_PAYEMNT_ROW(String  user_id , String payment_id) {


        database.execSQL("delete from "+ TABLE_NAME_PAYMENT +" where "+ DatabaseHandler.PAY_USER_ID+"='"+user_id+"'" + " and "+ DatabaseHandler.AUTO_PAYMENT_ID+"='"+payment_id+"'");
    }






    //this is for referense it will be delete after complete
/*

    //________________________________________SET CHAKRA TEXT_______________________________________________________


    //INSERT Profile Image
    // this method is used to fatch at insert user mood and display it on calender
    public void insert_ChakraText(String chakra_id , String crown_text , String thrid_eye_text , String throat_text ,String heart_text , String solar_text , String sacral_text , String root_text){

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.CHAKRA_USER_ID, chakra_id);
        contentValue.put(DatabaseHandler.CROWN_TEXT, crown_text);
        contentValue.put(DatabaseHandler.THIRD_EYE_TEXT, thrid_eye_text);
        contentValue.put(DatabaseHandler.THROAT_TEXT, throat_text);
        contentValue.put(DatabaseHandler.HEART_TEXT, heart_text);
        contentValue.put(DatabaseHandler.SOLAR_TEXT, solar_text);
        contentValue.put(DatabaseHandler.SACRAL_TEXT, sacral_text);
        contentValue.put(DatabaseHandler.ROOT_TEXT, root_text);

        database.insert(DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT, null, contentValue);

    }

    //Fetch Profile Image
    public Cursor fatch_ChakrasText(String chakra_user_id) {

        String query = "select * from "+ DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT+" where "+ DatabaseHandler.CHAKRA_USER_ID+"='"+chakra_user_id+"'";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //Delete all Profile Image
    public void delete_AllChaktaText() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT);
    }


    public int checkAvailablesChakra_id(String chakra_id) {

        Cursor cursor = null;
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT + " WHERE " + DatabaseHandler.CHAKRA_USER_ID + "='" + chakra_id + "'";
        cursor = database.rawQuery(sql, null);



        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();
            return 1;
        } else {
            //PID Not Found
            cursor.close();
            return 0;
        }
    }

  public  int  update_ChakrasText_Crown(String chakra_user_id,String crown_text){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.CROWN_TEXT, crown_text);
        //int i = database.update(DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT, contentValues, DatabaseHandler.CHAKRA_USER_ID + " = " +  chakra_user_id , null);
        int i = database.update(DatabaseHandler.TABLE_NAME_CHAKRAS_TEXT, contentValues, DatabaseHandler.CHAKRA_USER_ID + "=\"" + chakra_user_id + "\"", null);

        return i;
    }*/







}
