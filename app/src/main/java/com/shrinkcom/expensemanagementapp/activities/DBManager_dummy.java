package com.shrinkcom.expensemanagementapp.activities;/*
package com.shrinkcom.expensemanagementapp.activities;

*/
/**
 * Created by anupamchugh on 19/10/15.
 *//*

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void insert(String _id_video,String videourlpath,String loginuserid , String video_name,String videotype) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHandler._ID_VIDEO, _id_video);
        contentValue.put(DatabaseHandler.VIDEOURLPATH, videourlpath);
        contentValue.put(DatabaseHandler.LOGINUSERID, loginuserid);
        contentValue.put(DatabaseHandler.VIDEO_NAME, video_name);
        contentValue.put(DatabaseHandler.VIDEO_TYPE, videotype);

        database.insert(DatabaseHandler.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch(String vifeotype) {

        String query = "select * from "+DatabaseHandler.TABLE_NAME+" where "+DatabaseHandler.VIDEO_TYPE+"='"+vifeotype+"'";
       // String query = "select * from "+DatabaseHandler.TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

       // String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
       // Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor fetchVDOby(String vifeotype , String video_id) {

        String query = "select * from "+DatabaseHandler.TABLE_NAME+" where "+DatabaseHandler.VIDEO_TYPE+"='"+vifeotype+"'";
        // String query = "select * from "+DatabaseHandler.TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

        // String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
        // Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    public Cursor fetch2() {

     */
/*   String query = "select * from "+DatabaseHandler.TABLE_NAME+" where "+DatabaseHandler.VIDEO_TYPE+"="+vifeotype;
        Cursor cursor = database.rawQuery(query,null);*//*


        String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
        Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchoflinevideousingvideoid(String videoid) {

        String suert = "select * from "+DatabaseHandler.TABLE_NAME+" where "+DatabaseHandler._ID_VIDEO+"="+videoid;
        Cursor cursor  =  database.rawQuery(suert,null);

       // String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
      //  Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    // TODO: 12/17/2019  update video using videoid


    public int update(long _id, String _id_video, String videourlpath , String video_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler._ID_VIDEO, _id_video);
        contentValues.put(DatabaseHandler.VIDEOURLPATH, videourlpath);
        contentValues.put(DatabaseHandler.VIDEO_NAME, video_name);
        int i = database.update(DatabaseHandler.TABLE_NAME, contentValues, DatabaseHandler.ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHandler.TABLE_NAME, DatabaseHandler.ID + "=" + _id, null);
    }

    public void deleteAll() {
    database.execSQL("delete from "+ DatabaseHandler.TABLE_NAME);
    }

    public int checkAvailablesVideo(String serviceid) {

        Cursor cursor = null;
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler._ID_VIDEO + "='" + serviceid + "'";
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

    // TODO: 11/15/2019  all method for action document

// document Insert method
    public void insertDocument(String doc_id,String doc_path,String userid , String doc_name ,String  doc_type) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHandler._ID_DOCUMENTID, doc_id);
        contentValue.put(DatabaseHandler.DOCUMENT_PATH, doc_path);
        contentValue.put(DatabaseHandler.USERID, userid);
        contentValue.put(DatabaseHandler.DOC_NAME,doc_name);
        contentValue.put(DatabaseHandler.DOCUMENT_TYPE,doc_type);

        database.insert(DatabaseHandler.TABLE_NAME_DOCUMENT, null, contentValue);
    }

    //document fatch method

    public Cursor fetchDoc(String doc_type) {

        String query = "select * from "+DatabaseHandler.TABLE_NAME_DOCUMENT+" where "+DatabaseHandler.DOCUMENT_TYPE+"='"+doc_type+"'";
        Cursor cursor = database.rawQuery(query,null);

        // String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
        // Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchDocument() {
        String[] columns = new String[] { DatabaseHandler.ID_AUTO, DatabaseHandler._ID_DOCUMENTID, DatabaseHandler.DOCUMENT_PATH, DatabaseHandler.USERID ,DatabaseHandler.DOC_NAME };
        Cursor cursor = database.query(DatabaseHandler.TABLE_NAME_DOCUMENT, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor fetchDucumentUsingType(String vifeotype) {

        String query = "select * from "+DatabaseHandler.TABLE_NAME_DOCUMENT+" where "+DatabaseHandler.DOCUMENT_TYPE+"='"+vifeotype+"'";
        // String query = "select * from "+DatabaseHandler.TABLE_NAME;
        Log.e("QUERRYYYY",">>"+query);
        Cursor cursor = database.rawQuery(query,null);

        // String[] columns = new String[] { DatabaseHandler.ID, DatabaseHandler._ID_VIDEO, DatabaseHandler.VIDEOURLPATH, DatabaseHandler.LOGINUSERID ,DatabaseHandler.VIDEO_NAME };
        // Cursor cursor = database.query(DatabaseHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //  document Update method

    public int updateDocument(long _id, String _id_doc, String doc_path , String doc_name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler._ID_VIDEO, _id_doc);
        contentValues.put(DatabaseHandler.DOCUMENT_PATH, doc_path);
        contentValues.put(DatabaseHandler.DOC_NAME, doc_name);
        int i = database.update(DatabaseHandler.TABLE_NAME_DOCUMENT, contentValues, DatabaseHandler.ID_AUTO + " = " + _id, null);
        return i;
    }

    //document delete method

    public void deleteDoc(long _id) {
        database.delete(DatabaseHandler.TABLE_NAME_DOCUMENT, DatabaseHandler.ID_AUTO+ "=" + _id, null);
    }

//document delete all method

    public void deleteAllDoc() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_NAME_DOCUMENT);
    }

    //check availibility for document method

    public int checkAvailablesDocument(String serviceid) {

        Cursor cursor = null;
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_NAME_DOCUMENT + " WHERE " + DatabaseHandler._ID_DOCUMENTID + "='" + serviceid + "'";


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


    // TODO: 11/15/2019  all method for action GET DELEGATE

    public void insertDelegate(String delivery_id,String name,String job_title , String palce_work , String department , String email , String se_id , String faci_id , String cou_id , String phone , String gender ) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHandler.DELEVERY_ID, delivery_id);
        contentValue.put(DatabaseHandler.NAME, name);
        contentValue.put(DatabaseHandler.JOB_TITLE, job_title);
        contentValue.put(DatabaseHandler.PLACE_WORK,palce_work);
        contentValue.put(DatabaseHandler.DEPARTMENT, department);
        contentValue.put(DatabaseHandler.EMAIL, email);
        contentValue.put(DatabaseHandler.SE_ID,se_id);
        contentValue.put(DatabaseHandler.FACI_ID, faci_id);
        contentValue.put(DatabaseHandler.COU_ID, cou_id);

        contentValue.put(DatabaseHandler.PHONE, phone);
        contentValue.put(DatabaseHandler.GENDER, gender);

        database.insert(DatabaseHandler.TABLE_NAME_GET_DELEGATE, null, contentValue);
    }


    public int updateSessionid(String reeviuessessionid,String updatesessionid) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.SE_ID,updatesessionid);
        int i = database.update(DatabaseHandler.TABLE_NAME_GET_DELEGATE, contentValues, DatabaseHandler.SE_ID + " = " + reeviuessessionid, null);
        return i;
    }



    public Cursor fatchAllDelegate() {

        String[] columns = new String[] { DatabaseHandler.ID_AUTO_DELEGATE,
                DatabaseHandler.DELEVERY_ID, DatabaseHandler.NAME ,
                DatabaseHandler.JOB_TITLE ,  DatabaseHandler.PLACE_WORK
                ,DatabaseHandler.DEPARTMENT ,DatabaseHandler.EMAIL ,DatabaseHandler.SE_ID,
                DatabaseHandler.FACI_ID ,DatabaseHandler.COU_ID,DatabaseHandler.PHONE,DatabaseHandler.GENDER};
          Cursor cursor = database.query(DatabaseHandler.TABLE_NAME_GET_DELEGATE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    // delte Query
    public void deleteDelegate(long id) {
        database.delete(DatabaseHandler.TABLE_NAME_GET_DELEGATE, DatabaseHandler.ID_AUTO_DELEGATE+ "=" + id, null);
    }


    public void deleteAllDelegates() {

        database.execSQL("delete from "+ DatabaseHandler.TABLE_NAME_GET_DELEGATE);
    }

    // fatch delegates

    //document fatch method
    public Cursor fetchDelegates() {

        String[] columns = new String[] { DatabaseHandler.ID_AUTO_DELEGATE, DatabaseHandler.DELEVERY_ID, DatabaseHandler.NAME, DatabaseHandler.JOB_TITLE ,DatabaseHandler.PLACE_WORK
                ,DatabaseHandler.DEPARTMENT  ,DatabaseHandler.EMAIL ,DatabaseHandler.SE_ID ,DatabaseHandler.FACI_ID ,DatabaseHandler.COU_ID  };

        Cursor cursor = database.query(DatabaseHandler.TABLE_NAME_GET_DELEGATE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }





    public Cursor fetch_delegate_by_values_old(String d_faci_id ,String d_ci_id  , String s_id ){
        String query = "select * from "+DatabaseHandler.TABLE_NAME_GET_DELEGATE+" where "+DatabaseHandler.FACI_ID+"="+d_faci_id+ " and " + DatabaseHandler.COU_ID+"="+d_ci_id+" and "+DatabaseHandler.SE_ID+"="+s_id;
        */
/*  String query = "select * from "+DatabaseHandler.TABLE_ADD_DELEGATE+" where "+DatabaseHandler.DEL_TOK_ID+"="+d_token;*//*


        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    //____________________ get data  ino data base to run the app in offline mode_________________

    // TODO: 12/12/2019  actionper5form course table

    public void inserCoursedata(String courseid,String coursetitle,String coursedisp  , String courseaddtype  ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.COURSE_ID, courseid);
        contentValue.put(DatabaseHandler.COURSE_ADDTYPE, courseaddtype);
        contentValue.put(DatabaseHandler.TITLE, coursetitle);
        contentValue.put(DatabaseHandler.COURSE_DESCRIPTION,coursedisp);

        database.insert(DatabaseHandler.TABLE_COURSENAME, null, contentValue);
    }

    // TODO: 12/12/2019  fatch course
    public Cursor fatchCourse(int count) {
        count = count*10;
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_COURSENAME+" LIMIT "+count+",10";
        Log.e("SELECTQUERYY",">>"+selectQuery);
        Cursor cursor      = database.rawQuery(selectQuery, null);

       // String[] columns = new String[] { DatabaseHandler.COURSE_AUTO_ID, DatabaseHandler.COURSE_ID, DatabaseHandler.TITLE ,DatabaseHandler.COURSE_ADDTYPE, DatabaseHandler.COURSE_DESCRIPTION };
        //Cursor cursor = database.query(DatabaseHandler.TABLE_COURSENAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    // TODO: 12/14/2019  pagination on course

    public  Cursor fatchPageOnCourseData(String countlimit){
          //SELECT * FROM Students LIMIT 4,3;
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_COURSENAME+"LIMIT"+countlimit+",10";
        Cursor cursor      = database.rawQuery(selectQuery, null);
        return  cursor;
    }


    public void deleteAllCourse() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_COURSENAME);
    }
    //__________________________add feed back data  [0]___________________________________



    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table

    public void inserAddFeedbackdata(String addfeedback_id,String addfeedback_name ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.ADD_FEEDBACK_DATA_ID, addfeedback_id);
        contentValue.put(DatabaseHandler.ADD_FEEDBACK_DATA_NAME, addfeedback_name);

        database.insert(DatabaseHandler.TABLE_ADD_FEEDBACK_DATA, null, contentValue);
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchAddFeedBackData() {

        String[] columns = new String[] { DatabaseHandler.ADD_FEEDBACK_DATA_AUTO_ID, DatabaseHandler.ADD_FEEDBACK_DATA_ID, DatabaseHandler.ADD_FEEDBACK_DATA_NAME};
        Cursor cursor = database.query(DatabaseHandler.TABLE_ADD_FEEDBACK_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllAddFeedBackData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_ADD_FEEDBACK_DATA);
    }


    //______________________Admin Data_______________________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertAdminData(String adminid,String adminusername , String adminemail  ,String adminpassword  ,String adminstatus ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.ADMINDATA_ID, adminid);
        contentValue.put(DatabaseHandler.ADMIN_DATA_USERNAME, adminusername);
        contentValue.put(DatabaseHandler.ADMIN_DATA_EMAIL_ID, adminemail);
        contentValue.put(DatabaseHandler.ADMIN_DATA_PASSWORD, adminpassword);
        contentValue.put(DatabaseHandler.ADMIN_DATA_STATUS, adminstatus);
        database.insert(DatabaseHandler.TABLE_ADMINDATA, null, contentValue);
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchAdminData() {

        String[] columns = new String[] { DatabaseHandler.ADMINDATA_AUTO_ID, DatabaseHandler.ADMINDATA_ID, DatabaseHandler.ADMIN_DATA_USERNAME , DatabaseHandler.ADMIN_DATA_EMAIL_ID ,  DatabaseHandler.ADMIN_DATA_PASSWORD , DatabaseHandler.ADMIN_DATA_STATUS};
        Cursor cursor = database.query(DatabaseHandler.TABLE_ADMINDATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllAdminData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_ADMINDATA);
    }

    //________________________________delegate data_________________________________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertDelegateData(String delegate_id,String course_id , String session_id  ,String add_type  ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.DELEGATE_DATA_ID, delegate_id);
        contentValue.put(DatabaseHandler.DELEGATE_DATA_COURSE_ID, course_id);
        contentValue.put(DatabaseHandler.DELEGATE_DATA_SESSION_ID, session_id);
        contentValue.put(DatabaseHandler.DELEGATE_ADD_TYPE, add_type);

        database.insert(DatabaseHandler.TABLE_DELEGATE_DATA, null, contentValue);
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchDelegateData() {

        String[] columns = new String[] { DatabaseHandler.DELEGATE_DATA_AUTO_ID, DatabaseHandler.DELEGATE_DATA_ID, DatabaseHandler.DELEGATE_DATA_COURSE_ID , DatabaseHandler.DELEGATE_DATA_SESSION_ID ,  DatabaseHandler.DELEGATE_ADD_TYPE };
        Cursor cursor = database.query(DatabaseHandler.TABLE_DELEGATE_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllDelegatedataData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELEGATE_DATA);
    }



    //________________________________delivery data_________________________________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertDeleviryData(String deleviry_id,String prac_session , String s_id  ,String c_id , String faci_type ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.DELEVERY_ID, deleviry_id);
        contentValue.put(DatabaseHandler.DELEVIRY_DATA_PRAC_SESSION, prac_session);
        contentValue.put(DatabaseHandler.DELEVIRY_DATA_S_ID, s_id);
        contentValue.put(DatabaseHandler.DELEVIRY_DATA_CID, c_id);
        contentValue.put(DatabaseHandler.DELEVIRY_DATA_FACI_TYPE, faci_type);
        database.insert(DatabaseHandler.TABLE_DELEVIRY_DATA, null, contentValue);
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
     public Cursor fatchDeleviryData() {

        String[] columns = new String[] { DatabaseHandler.DELEVIRY_DATA_AUTO_ID, DatabaseHandler.DELEVERY_ID, DatabaseHandler.DELEVIRY_DATA_PRAC_SESSION , DatabaseHandler.DELEVIRY_DATA_S_ID ,  DatabaseHandler.DELEVIRY_DATA_CID , DatabaseHandler.DELEVIRY_DATA_FACI_TYPE };
        Cursor cursor = database.query(DatabaseHandler.TABLE_DELEVIRY_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllDelevirydData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELEVIRY_DATA);
    }


    //________________________________delivery Feedback data_________________________________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertDeleviryFeedBackData(String de_feed_id,String d_delegate_feedback , String d_delegate_desc  ,String del_delegate_id , String d_facilitator_feedback , String d_facilitator_desc , String fac_id , String sess_id ,String cours_id ,String feedback_for,String feedbackname) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.D_FEED_ID, de_feed_id);
        contentValue.put(DatabaseHandler.D_DELEGATE_FEEDBACK, d_delegate_feedback);
        contentValue.put(DatabaseHandler.D_DELEGATE_DESC, d_delegate_desc);
        contentValue.put(DatabaseHandler.DEL_DELEGATE_ID, del_delegate_id);
        contentValue.put(DatabaseHandler.D_FACILITATOR_FEEDBACK, d_facilitator_feedback);
        contentValue.put(DatabaseHandler.D_FACILITATOR_DESCP, d_facilitator_desc);
        contentValue.put(DatabaseHandler.FAC_ID, fac_id);
        contentValue.put(DatabaseHandler.SESS_ID, sess_id);
        contentValue.put(DatabaseHandler.DELIVIRY_COURSE_ID, cours_id);
        contentValue.put(DatabaseHandler.FEEDBACK_FOR,  feedback_for);
        contentValue.put(DatabaseHandler.FEEDBACK_FOR_NAME,  feedbackname);
        database.insert(DatabaseHandler.TABLE_DELEVIRY_FEEDBACK_DATA, null, contentValue);

    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchDeleviryfeedbackData() {

        String[] columns = new String[] { DatabaseHandler.DELEVIRY_FEEDBACK_DATA_AUTO_ID, DatabaseHandler.D_FEED_ID,
                DatabaseHandler.D_DELEGATE_FEEDBACK , DatabaseHandler.D_DELEGATE_DESC ,  DatabaseHandler.DEL_DELEGATE_ID
                , DatabaseHandler.D_FACILITATOR_FEEDBACK , DatabaseHandler.D_FACILITATOR_DESCP ,DatabaseHandler.FAC_ID
        ,DatabaseHandler.SESS_ID, DatabaseHandler.DELIVIRY_COURSE_ID , DatabaseHandler.FEEDBACK_FOR,DatabaseHandler.FEEDBACK_FOR_NAME};

        Cursor cursor = database.query(DatabaseHandler.TABLE_DELEVIRY_FEEDBACK_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllDeleviry_feedBackData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELEVIRY_FEEDBACK_DATA);
    }


    //______________________insert  prepration data (facilator->course->session->prepration_____________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertPreprationData(String p_id,String i_objective , String tr_resource  ,String action_list , String s_id , String c_id , String fa_ass_type ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.P_ID, p_id);
        contentValue.put(DatabaseHandler.I_OBJECTIVE, i_objective);
        contentValue.put(DatabaseHandler.TR_RESOURCE, tr_resource);
        contentValue.put(DatabaseHandler.ACTIONLIST, action_list);
        contentValue.put(DatabaseHandler.S_ID, s_id);
        contentValue.put(DatabaseHandler.C_ID, c_id);
        contentValue.put(DatabaseHandler.FA_ADD_TYPE, fa_ass_type);


        try {
            database.insert(DatabaseHandler.TABLE_PREPRATION_DATA, null, contentValue);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // TODO: 12/14/2019  get preparetion data us\ing courseid

    public Cursor preparedatausingid(String courseid ,String session_id){

        String query = "select * from "+DatabaseHandler.TABLE_PREPRATION_DATA+" where "+DatabaseHandler.C_ID+"="+courseid+" and "+DatabaseHandler.S_ID+"="+session_id;




        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(query,null);


        return cursor;
    }



    public Cursor preparedatausingid2(String courseid){

        String query = "select * from "+DatabaseHandler.TABLE_PREPRATION_DATA+" where "+DatabaseHandler.C_ID+"="+courseid;




        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(query,null);


        return cursor;
    }



    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchPreprationData() {
        String[] columns = new String[] { DatabaseHandler.PREPRATION_AUTO_ID, DatabaseHandler.P_ID,
                DatabaseHandler.I_OBJECTIVE , DatabaseHandler.TR_RESOURCE ,  DatabaseHandler.ACTIONLIST , DatabaseHandler.S_ID
        , DatabaseHandler.C_ID ,  DatabaseHandler.FA_ADD_TYPE };

        Cursor cursor = database.query(DatabaseHandler.TABLE_PREPRATION_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void delete_preprationData() {

        database.execSQL("delete from "+ DatabaseHandler.TABLE_PREPRATION_DATA);
    }



    //______________________insert  seesion data(facilator->course->session_____________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertSessionData(String session_id,String title_s , String s_containt  ,String s_desc ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.SESSION_ID, session_id);
        contentValue.put(DatabaseHandler.TITLE_S, title_s);
        contentValue.put(DatabaseHandler.S_CONTAINT, s_containt);
        contentValue.put(DatabaseHandler.S_DESC, s_desc);

        database.insert(DatabaseHandler.TABLE_SESSION_DATA, null, contentValue);

    }


    // TODO: 12/14/2019  checkavailability
    public  int checkavailability(String sessionid){
        Cursor cursor = null;
        String sql = "SELECT * FROM " + DatabaseHandler.TABLE_SESSION_DATA + " WHERE " + DatabaseHandler.SESSION_ID + "='" + sessionid + "'";
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

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchSession_Data(String sessionid_prep) {

        String query = "select * from "+DatabaseHandler.TABLE_SESSION_DATA+" where "+DatabaseHandler.SESSION_ID+"="+sessionid_prep ;

        Cursor cursor = database.rawQuery(query,null);


        */
/*
        String[] columns = new String[] { DatabaseHandler.SESSION_AUTO_ID, DatabaseHandler.SESSION_ID,
                DatabaseHandler.TITLE_S , DatabaseHandler.S_CONTAINT ,  DatabaseHandler.S_DESC};

        Cursor cursor = database.query(DatabaseHandler.TABLE_SESSION_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }*//*

        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllSessionData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_SESSION_DATA);
    }



    //_____________________________ video data___________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertfa_videoData(String fa_video_id,String fav_title , String fa_v  ,String pre_id ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.FA_VIDEO_ID, fa_video_id);
        contentValue.put(DatabaseHandler.FA_VIDEO_TITLE, fav_title);
        contentValue.put(DatabaseHandler.FA_V, fa_v);
        contentValue.put(DatabaseHandler.PRE_ID, pre_id);

        database.insert(DatabaseHandler.TABLE_FA_VIDEO_DATA, null, contentValue);

    }


    // TODO: 12/14/2019  get preparetion data us\ing courseid

    public Cursor fatch_videoby_pid(String pre_id){
        String query = "select * from "+DatabaseHandler.TABLE_FA_VIDEO_DATA+" where "+DatabaseHandler.PRE_ID+"="+pre_id;

        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatch_fa_video_data() {
        String[] columns = new String[] { DatabaseHandler.FA_VIDEO_AUTO_ID, DatabaseHandler.FA_VIDEO_ID,
                DatabaseHandler.FA_VIDEO_TITLE , DatabaseHandler.FA_V ,  DatabaseHandler.PRE_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_FA_VIDEO_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllfa_videodata() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_FA_VIDEO_DATA);
    }


    public  int  updatevideourl(String url,String videoid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.FA_V, url);

        int i = database.update(DatabaseHandler.TABLE_FA_VIDEO_DATA, contentValues, DatabaseHandler.FA_VIDEO_ID + " = " + videoid, null);
        return i;
    }



    //_____________________________ fa doc data___________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertfa_doc_Data(String fa_doc_id,String fa_doc_title , String fa_doc  ,String doc_pre_id ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.FA_DOC_ID, fa_doc_id);
        contentValue.put(DatabaseHandler.FA_DOC_TITLE, fa_doc_title);
        contentValue.put(DatabaseHandler.FA_D, fa_doc);
        contentValue.put(DatabaseHandler.FA_DOC_PRE_ID, doc_pre_id);

        database.insert(DatabaseHandler.TABLE_FA_DOC_DATA, null, contentValue);

    }

    // TODO: 12/14/2019  get preparetion data us\ing courseid



    public  int  updateDoc_url_id(String url,String doc_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.FA_D, url);

        int i = database.update(DatabaseHandler.TABLE_FA_DOC_DATA, contentValues, DatabaseHandler.FA_DOC_ID + " = " + doc_id, null);
        return i;
    }





    public Cursor fatch_doc_by_pid(String pre_id){

        String query = "select * from "+DatabaseHandler.TABLE_FA_DOC_DATA+" where "+DatabaseHandler.FA_DOC_PRE_ID+"="+pre_id;

        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatch_fa_doc_data() {


        String[] columns = new String[] { DatabaseHandler.FA_DOC_AUTO_ID, DatabaseHandler.FA_DOC_ID,
                DatabaseHandler.FA_DOC_TITLE , DatabaseHandler.FA_D ,  DatabaseHandler.FA_DOC_PRE_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_FA_DOC_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllfa_DOC_data() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_FA_DOC_DATA);
    }





    //_____________________________ office_detail data  (CONTACT)___________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insert_office_Data(String office_id ,String office_email , String office_phone  ,String office_skype_id ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.OFFICE_DETAIL_ID, office_id);
        contentValue.put(DatabaseHandler.OFFICE_DETAIL_EMAIL, office_email);
        contentValue.put(DatabaseHandler.OFFICE_DETAIL_PHONE, office_phone);
        contentValue.put(DatabaseHandler.OFFICE_SKYPE_ID, office_skype_id);

        database.insert(DatabaseHandler.TABLE_OFFICE_DETAIL, null, contentValue);

    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatch_office_data() {
        String[] columns = new String[] { DatabaseHandler.OFFICE_DETAIL_AUTO_ID, DatabaseHandler.OFFICE_DETAIL_ID,
                DatabaseHandler.OFFICE_DETAIL_EMAIL , DatabaseHandler.OFFICE_DETAIL_PHONE ,  DatabaseHandler.OFFICE_SKYPE_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_OFFICE_DETAIL, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void delete_office_data() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_OFFICE_DETAIL);
    }




    //_____________________________ delivery video data___________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insert_delivery_video_data(String de_video_id ,String de_videio_title , String de_video  ,String de_delivery_id ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.DE_VIDEO_ID, de_video_id);
        contentValue.put(DatabaseHandler.DE_VIDEO_TITLE, de_videio_title);
        contentValue.put(DatabaseHandler.DE_VIDEO, de_video);
        contentValue.put(DatabaseHandler.DILEVIRY_ID, de_delivery_id);

        database.insert(DatabaseHandler.TABLE_DELEVIRY_VIDEO_DATA, null, contentValue);

    }

    // TODO: 12/14/2019  get preparetion data us\ing courseid




    public  int  updatevideourl_delivery(String url,String videoid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.DE_VIDEO, url);

        int i = database.update(DatabaseHandler.TABLE_DELEVIRY_VIDEO_DATA, contentValues, DatabaseHandler.DE_VIDEO_ID + " = " + videoid, null);
        return i;
    }




    public Cursor fatch_delivery_vieo_data_by_de_id(String delivery_id){
        String query = "select * from "+DatabaseHandler.TABLE_DELEVIRY_VIDEO_DATA+" where "+DatabaseHandler.DILEVIRY_ID+"="+delivery_id;

        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatch_delivery_video_data() {

        String[] columns = new String[] { DatabaseHandler.DELEVIRY_VIDEO_DATA_AUTO_ID, DatabaseHandler.DE_VIDEO_ID,
                DatabaseHandler.DE_VIDEO_TITLE , DatabaseHandler.DE_VIDEO ,  DatabaseHandler.DILEVIRY_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_DELEVIRY_VIDEO_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void delete_all_delivery_video_data() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELEVIRY_VIDEO_DATA);
    }



    //________________________________delivery QUIZ data_________________________________________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertQUIZ(String quiz_id,String quiz , String quiz_answer  ,String del_id ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.QUIZ_ID, quiz_id);
        contentValue.put(DatabaseHandler.QUIZ, quiz);
        contentValue.put(DatabaseHandler.QUIZ_ANSWER, quiz_answer);
        contentValue.put(DatabaseHandler.DEL_ID, del_id);

        database.insert(DatabaseHandler.TABLE_DELEVIRY_QUIZ_DATA, null, contentValue);

    }



    public Cursor fatch_delivery_quiz_data_by_de_id(String delivery_id){
        String query = "select * from "+DatabaseHandler.TABLE_DELEVIRY_QUIZ_DATA+" where "+DatabaseHandler.DEL_ID+"="+delivery_id;

        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }








    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchQUIZ() {
        String[] columns = new String[] { DatabaseHandler.DELEVIRY_QUIZ_DATA_AUTO_ID, DatabaseHandler.QUIZ_ID,
                DatabaseHandler.QUIZ , DatabaseHandler.QUIZ_ANSWER ,  DatabaseHandler.DEL_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_DELEVIRY_QUIZ_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAllQUIZ() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELEVIRY_QUIZ_DATA);
    }



    //______________________insert  prepration data (facilator->course->session->prepration_____________________


    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insertDeliveryData(String d_id,String prac_session , String  d_s_id  ,String d_c_id ,  String de_faci_type , String lesson_tittle, String prac_time , String video_time ,String quiz_time) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.D_ID, d_id);
        contentValue.put(DatabaseHandler.DELIVERY_PRAC_SESSION, prac_session);
        contentValue.put(DatabaseHandler.DELIVERY_S_ID, d_s_id);
        contentValue.put(DatabaseHandler.DELIVERY_C_ID, d_c_id);
        contentValue.put(DatabaseHandler.DELIVERY_FACI_TYPE, de_faci_type);

        contentValue.put(DatabaseHandler.DELIVERY_LESSON_TITLE, lesson_tittle);
        contentValue.put(DatabaseHandler.DELIVERY_PRAC_TIME, prac_time);
        contentValue.put(DatabaseHandler.DELIVERY_VIDEO_TIME, video_time);
        contentValue.put(DatabaseHandler.DELIVERY_QUIZE_TIME, quiz_time);

     try {
            database.insert(DatabaseHandler.TABLE_DELIVERY_DATA, null, contentValue);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // TODO: 12/14/2019  get delivery data data us\ing courseid


*/
/*    public Cursor fatch_delivery_data_using_course_id(String courseid , String session_id){
        String query = "select * from "+DatabaseHandler.TABLE_DELIVERY_DATA+" where "+DatabaseHandler.DELIVERY_C_ID+"="+courseid+"&"+DatabaseHandler.DELIVERY_S_ID+"="+session_id;

        Log.v("QUIZDATAQUERY",">>"+query);

        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }*//*







    public Cursor fatch_delivery_data_using_course_id(Context context,String session_id ,String courseid){

        String query = "select * from "+DatabaseHandler.TABLE_DELIVERY_DATA+" where "+DatabaseHandler.DELIVERY_C_ID+"="+courseid+" and " +DatabaseHandler.DELIVERY_S_ID+"="+session_id;





        dbHelper = new DatabaseHandler(context);

        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(query,null);


        return cursor;
    }





    public Cursor fatch_delivery_data_using_course_id_1(String courseid ){
        String query = "select * from "+DatabaseHandler.TABLE_DELIVERY_DATA+" where "+DatabaseHandler.DELIVERY_C_ID+"="+courseid;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }


    public Cursor fatch_delivery_data_using_sid(String sessionid ){
        String query = "select * from "+DatabaseHandler.TABLE_DELIVERY_DATA+" where "+DatabaseHandler.DELIVERY_S_ID+"="+sessionid;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }



   */
/* public Cursor fatch_delivery_data_using_Session_id(String courseid){
        String query = "select * from "+DatabaseHandler.TABLE_DELIVERY_DATA+" where "+DatabaseHandler.DELIVERY_S_ID+"="+courseid;
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }*//*



    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatchDeliveryData() {
        String[] columns = new String[] { DatabaseHandler.DELIVERY_AUTO_ID, DatabaseHandler.D_ID,
                DatabaseHandler.DELIVERY_PRAC_SESSION , DatabaseHandler.DELIVERY_S_ID ,  DatabaseHandler.DELIVERY_C_ID , DatabaseHandler.DELIVERY_FACI_TYPE};

        Cursor cursor = database.query(DatabaseHandler.TABLE_DELIVERY_DATA, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void delete_DeliveryData() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_DELIVERY_DATA);
    }



    //______________________________________ADD DELEGATE_______________________________________




    // TODO: 12/12/2019  actionper5form course table

    public void insert_add_delegate(String delivery_id, String del_name, String delk_job_title, String del_place_of_work
  , String del_department , String del_email ,String del_se_id , String del_faci_id , String del_cou_id , String del_tok_id ,String phone , String gender ) {



        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.DELIVERY_ID, delivery_id);
        contentValue.put(DatabaseHandler.DEL_U_NAME, del_name);
        contentValue.put(DatabaseHandler.DEL_U_JOB_TITLE, delk_job_title);
        contentValue.put(DatabaseHandler.DEL_U_PLACE_OF_WORK, del_place_of_work);
        contentValue.put(DatabaseHandler.DEL_U_DEPARTMENT, del_department);
        contentValue.put(DatabaseHandler.DEL_U_EMAIL, del_email);
        contentValue.put(DatabaseHandler.DEL_SE_ID, del_se_id);
        contentValue.put(DatabaseHandler.DEL_FACI_ID, del_faci_id);
        contentValue.put(DatabaseHandler.DEL_COU_ID, del_cou_id);
        contentValue.put(DatabaseHandler.DEL_TOK_ID, del_tok_id);

        contentValue.put(DatabaseHandler.DEL_PHONE, phone);
        contentValue.put(DatabaseHandler.DEL_GENDER, gender);

        try {
            database.insert(DatabaseHandler.TABLE_ADD_DELEGATE, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public Cursor fetch_delegate_by_values(String d_faci_id ,String d_ci_id  , String s_id , String d_token){
        String query = "select * from "+DatabaseHandler.TABLE_ADD_DELEGATE+" where "+DatabaseHandler.DEL_FACI_ID+"="+d_faci_id+ " and " + DatabaseHandler.DEL_COU_ID+"="+d_ci_id+" and "+DatabaseHandler.DEL_SE_ID+"="+s_id +" and "+DatabaseHandler.DEL_TOK_ID+"="+d_token;
      */
/*  String query = "select * from "+DatabaseHandler.TABLE_ADD_DELEGATE+" where "+DatabaseHandler.DEL_TOK_ID+"="+d_token;*//*

        Log.e("CURCERRRqq",">>"+query);
        Cursor cursor = database.rawQuery(query,null);
        return cursor;
    }






    public Cursor fatch_add_delegate() {

        String[] columns = new String[] { DatabaseHandler.ADD_DELEGATE_AUTO_ID, DatabaseHandler.DELIVERY_ID,
                DatabaseHandler.DEL_U_NAME , DatabaseHandler.DEL_U_JOB_TITLE ,  DatabaseHandler.DEL_U_PLACE_OF_WORK ,
                DatabaseHandler.DEL_U_DEPARTMENT ,
                DatabaseHandler.DEL_U_EMAIL ,
                DatabaseHandler.DEL_SE_ID ,
                DatabaseHandler.DEL_FACI_ID ,
                DatabaseHandler.DEL_COU_ID ,
                DatabaseHandler.DEL_TOK_ID};

        Cursor cursor = database.query(DatabaseHandler.TABLE_ADD_DELEGATE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void deleteAlladdDelagate() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_ADD_DELEGATE);
    }




    //_________________________________end__________________________________________________



    //______________________________________GET TOKEN___________________________________________________




    // TODO: 12/12/2019  actionper5form Add Feed Back DATA table
    public void insert_get_token(String token_t_id ,String token_f_id , String token_s_id  ,String token_c_id ,String token_no ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.TOKEN_T_ID, token_t_id);
        contentValue.put(DatabaseHandler.TOKEN_F_ID, token_f_id);
        contentValue.put(DatabaseHandler.TOKEN_S_ID, token_s_id);
        contentValue.put(DatabaseHandler.TOKEN_C_ID, token_c_id);
        contentValue.put(DatabaseHandler.TOKEN_NO, token_no);

        database.insert(DatabaseHandler.TABLE_GET_TOKEN, null, contentValue);


    }

    // TODO: 12/12/2019  fatch Add Feed Back DATA
    public Cursor fatch_token() {

        String[] columns = new String[] { DatabaseHandler.TOKEN_AUTO_ID, DatabaseHandler.TOKEN_T_ID,
                DatabaseHandler.TOKEN_F_ID , DatabaseHandler.TOKEN_S_ID ,  DatabaseHandler.TOKEN_C_ID ,
                DatabaseHandler.TOKEN_NO};

        Cursor cursor = database.query(DatabaseHandler.TABLE_GET_TOKEN, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }


    public Cursor fetch_token_by_cid_and_sid(String c_id ,String s_id , String faci_id){


        String query = "select * from "+DatabaseHandler.TABLE_GET_TOKEN+" where "+DatabaseHandler.TOKEN_C_ID+"="+c_id+ " and " + DatabaseHandler.TOKEN_S_ID+"="+s_id+" and "+DatabaseHandler.TOKEN_F_ID+"="+faci_id;
        Log.e("CURCERRRqq",">>"+query);
        Cursor cursor = database.rawQuery(query,null);
        return cursor;

    }




    // TODO: 12/12/2019  delete Add Feed Back DATA
    public void delete_all_token() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_GET_TOKEN);
    }





    //_________________________________end__________________________________________________




    //terms  and condition
    public void insert_terms_and_condition(String id , String content) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.T_AND_C_ID, id);
        contentValue.put(DatabaseHandler.CONTENT, content);


        database.insert(DatabaseHandler.TABLE_TERMS_AND_CONDITION, null, contentValue);


    }

    public Cursor fatch_terms_and_condition() {

        String[] columns = new String[] { DatabaseHandler.T_AND_C_AUTO_ID, DatabaseHandler.T_AND_C_ID,
                DatabaseHandler.CONTENT};

        Cursor cursor = database.query(DatabaseHandler.TABLE_TERMS_AND_CONDITION, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }



    public void delete_terms_and_condition() {
        database.execSQL("delete from "+ DatabaseHandler.TABLE_TERMS_AND_CONDITION);
    }















}
*/
