package com.example.khalil.pixidustwebapi.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import com.example.khalil.pixidustwebapi.Activities.JobDetail;
import com.example.khalil.pixidustwebapi.Entities.ProjectInformation;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Khalil on 1/30/2018.
 */

public class FeedReaderDbConn {

    private static final String Database_Name = "PixieDustDB";
    private static final String Database_Table_Users = "Users";
    private static final String Database_Table_Job_Details = "JobsDetail";
    private static final String Database_Table_Templates = "Templates";
    private static final String Database_Table_TemplatesFiles = "TemplatesFiles";
    private static final String Database_Table_JobsQuestions = "JobsQuestions";
    private static final String Database_Table_AnswerChoice = "AnswerChoice";
    private static final String Database_Table_JobProduct = "JobProduct";
    private static final String Database_Table_ClientStoreLocationProductStock = "ClientStoreLocationProductStock";
    private static final String Database_Table_EmployeeJobDetail = "EmployeeJobDetail";

    private static final int Database_Version = 9;
    /*    Login Table Columns*/
    public static final String ID = "ID";
    public static final String LoginEmail = "LoginEmail";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String LastLogin = "LastLogin";
    public static final String UserType = "UserType";
    public static final String Password = "Password";
    public static final String DisplayName = "DisplayName";
    public static final String IsActive = "IsActive";
    public static final String DateCreated = "DateCreated";
    /* Login Table Columns*/

    /* JobDetail Table Columns*/
    public static final String JobDetailID = "JobDetailID";
    public static final String EmployeeID = "EmployeeID";
    public static final String TemplateID = "TemplateID";
    public static final String ClientName = "ClientName ";
    public static final String StoreName = "StoreName";
    public static final String LocationName = "LocationName";
    public static final String JobTemplateName = "JobTemplateName";
    public static final String ClientStoreTemplateID = "ClientStoreTemplateID";
    public static final String JobDate = "JobDate";
    public static final String JobTime = "JobTime";
    public static final String JobStatusName = "JobStatusName";
    public static final String JobSynced = "JobSynced";
    /* JobDetail Table Columns*/

    /*Template Table Columns*/
    public static final String TemplatesID = "TemplatesID";
    public static final String TemplateName = "TemplateName";
    public static final String ProjectBrief = "ProjectBrief";
    /*Template Table Columns*/

    /*Template Files Table Columns*/
    public static final String TemplatesFilesID = "TemplatesFilesID";
    public static final String FK_TemplateID = "FK_TemplateID";
    public static final String FilePath = "FilePath";
    public static final String FileMymeType = "FileMymeType";
    /*Template Files Table Columns*/

    /*JobsQuestions Table Columns*/
    public static final String JobsQuestionID = "JobsQuestionID";
    public static final String Questions_FK_TemplateID = "Questions_FK_TemplateID";
    public static final String QuestionTitle = "QuestionTitle";
    public static final String QuestionType = "QuestionType";
    public static final String QuestionSortOrder = "QuestionSortOrder";
    public static final String QuestionID = "QuestionID";
    /*JobsQuestions Files Table Columns*/

    /*AnswerChoice Table Columns*/
    public static final String AnswerChoicePrimaryID = "AnswerChoicePrimaryID";
    public static final String AnswerChoiceID = "AnswerChoiceID";
    public static final String AnswerChoice_FK_TemplateID = "AnswerChoice_FK_TemplateID";
    public static final String Title = "Title";
    public static final String AnswerChoiceSortOrder = "AnswerChoiceSortOrder";
    public static final String JobsQuestions_AnswerChoiceID = "JobsQuestions_AnswerChoiceID";
    /*AnswerChoice  Table Columns*/

    /*JobProduct Table Columns*/
    public static final String JobProductPrimaryID = "JobProductPrimaryID";
    public static final String ProductID = "ProductID";
    public static final String ProductsSortOrder = "ProductsSortOrder";
    public static final String JobProduct_FK_TemplateID = "JobProduct_FK_TemplateID";
    public static final String ProductTitle = "ProductTitle";
    public static final String ProductType = "ProductType";
    public static final String StockLevel = "StockLevel";
    public static final String CSPLID = "CSPLID";
    /*JobProduct  Table Columns*/


    //ClientStoreLocationProductStock Table Columns Start
    public static final String ClientStoreLocationProductStockID = "ClientStoreLocationProductStockID";
    public static final String FK_ClientStoreTemplateProducts = "FK_ClientStoreTemplateProducts";
    public static final String ProductStockLevel = "StockLevel";
    public static final String DateChecked = "DateChecked";
    public static final String FK_Job = "FK_Job";
    //ClientStoreLocationProductStock Table Columns End

    //EmployeeJobDetail Table Columns Start
    public static final String EmployeeJobDetailID = "EmployeeJobDetailID";
    public static final String FK_EmployeeJobs = "FK_EmployeeJobs";
    public static final String FK_QuestionTemplate = "FK_QuestionTemplate";
    public static final String FK_answerChoice = "FK_answerChoice";
    public static final String answerValue = "answerValue";
    public static final String JobQuestionType = "JobQuestionType";

    //EmployeeJobDetail Table Columns End

    private FeedReaderDbHelper helper;
    private final Context ourcontext;
    private SQLiteDatabase database;

    private static class FeedReaderDbHelper extends SQLiteOpenHelper {
        public FeedReaderDbHelper(Context context) {
            super(context, Database_Name, null, Database_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Database_Table_Users + "(" +
                    ID + " INTEGER NOT NULL PRIMARY KEY, " +
                    LoginEmail + " TEXT, " +
                    FirstName + " TEXT, " +
                    LastName + " TEXT, " +
                    LastLogin + " DATETIME, " +
                    UserType + " INTEGER, " +
                    Password + " TEXT, " +
                    DisplayName + " TEXT, " +
                    IsActive + " BIT, " +
                    DateCreated + " TEXT);"
            );

            db.execSQL("CREATE TABLE " + Database_Table_Job_Details + "(" +
                    JobDetailID + " INTEGER NOT NULL PRIMARY KEY , " +
                    EmployeeID + " INTEGER, " +
                    TemplateID + " INTEGER, " +
                    ClientName + " TEXT, " +
                    StoreName + " TEXT, " +
                    LocationName + " TEXT, " +
                    JobTemplateName + " TEXT, " +
                    ClientStoreTemplateID + " INTEGER, " +
                    JobDate + " TEXT, " +
                    JobTime + " TEXT, " +
                    JobStatusName + " TEXT, " +
                    JobSynced + " BIT);"
            );

            db.execSQL("CREATE TABLE " + Database_Table_Templates + "(" +
                    TemplatesID + " INTEGER NOT NULL PRIMARY KEY, " +
                    TemplateName + " TEXT, " +
                    ProjectBrief + " TEXT);"
            );

            db.execSQL("CREATE TABLE " + Database_Table_TemplatesFiles + "(" +
                    TemplatesFilesID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    FK_TemplateID + " INTEGER, " +
                    FileMymeType + " TEXT, " +
                    FilePath + " TEXT);"
            );
            db.execSQL("CREATE TABLE " + Database_Table_JobsQuestions + "(" +
                    JobsQuestionID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    QuestionID + " INTEGER, " +
                    Questions_FK_TemplateID + " INTEGER, " +
                    QuestionTitle + " TEXT, " +
                    QuestionType + " INTEGER, " +
                    QuestionSortOrder + " INTEGER);"
            );
            db.execSQL("CREATE TABLE " + Database_Table_AnswerChoice + "(" +
                    AnswerChoicePrimaryID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    AnswerChoiceID + " INTEGER, " +
                    AnswerChoice_FK_TemplateID + " INTEGER, " +
                    Title + " TEXT, " +
                    AnswerChoiceSortOrder + " INTEGER, " +
                    JobsQuestions_AnswerChoiceID + " INTEGER);"
            );
            db.execSQL("CREATE TABLE " + Database_Table_JobProduct + "(" +
                    JobProductPrimaryID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    ProductID + " INTEGER, " +
                    ProductsSortOrder + " INTEGER, " +
                    JobProduct_FK_TemplateID + " INTEGER, " +
                    ProductTitle + " TEXT, " +
                    ProductType + " TEXT, " +
                    StockLevel + " INTEGER, " +
                    CSPLID + " TEXT);"
            );
            db.execSQL("CREATE TABLE " + Database_Table_ClientStoreLocationProductStock + "(" +
                    ClientStoreLocationProductStockID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    FK_ClientStoreTemplateProducts + " INTEGER, " +
                    ProductStockLevel + " INTEGER, " +
                    DateChecked + " TEXT, " +
                    FK_Job + " INTEGER);"
            );
            db.execSQL("CREATE TABLE " + Database_Table_EmployeeJobDetail + "(" +
                    EmployeeJobDetailID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    FK_EmployeeJobs + " INTEGER, " +
                    FK_QuestionTemplate + " INTEGER, " +
                    FK_answerChoice + " INTEGER, " +
                    JobQuestionType + " INTEGER, " +
                    answerValue + " TEXT);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_Users);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_Job_Details);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_Templates);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_TemplatesFiles);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_JobProduct);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_AnswerChoice);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_JobsQuestions);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_ClientStoreLocationProductStock);
            db.execSQL("DROP TABLE IF EXISTS " + Database_Table_EmployeeJobDetail);
            onCreate(db);
        }
    }

    public FeedReaderDbConn(Context context) {
        ourcontext = context;
    }

    public FeedReaderDbConn open() {
        helper = new FeedReaderDbHelper(ourcontext);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public long insertUserData(int UserID, String loginEmail, String firstName, String lastName, String lastLogin, int userType, String password, String displayName, boolean isActive) {
        String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(ID, UserID);
        cv.put(LoginEmail, loginEmail);
        cv.put(FirstName, firstName);
        cv.put(LastName, lastName);
        cv.put(LastLogin, lastLogin);
        cv.put(UserType, userType);
        cv.put(Password, password);
        cv.put(DisplayName, displayName);
        cv.put(IsActive, isActive);
        cv.put(DateCreated, datetime);

        long returnid = database.insert(Database_Table_Users, null, cv);
        return returnid;

    }

    /*User Related Method*/
    public String getAllUserData() {
        String[] columns = new String[]{ID, LoginEmail, FirstName, LastName, LastLogin, UserType, Password, DisplayName, IsActive, DateCreated};
        Cursor c = database.query(Database_Table_Users, columns, null, null, null, null, null);
        String result = "";

        int iID = c.getColumnIndex(ID);
        int iloginEmail = c.getColumnIndex(LoginEmail);
        int ifirstName = c.getColumnIndex(FirstName);
        int ilastName = c.getColumnIndex(LastName);
        int ilastLogin = c.getColumnIndex(LastLogin);
        int iuserType = c.getColumnIndex(UserType);
        int ipassword = c.getColumnIndex(Password);
        int idisplayName = c.getColumnIndex(DisplayName);
        int iisActive = c.getColumnIndex(IsActive);
        int idateCreated = c.getColumnIndex(DateCreated);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iID) + ":" + c.getString(iloginEmail) + ":" + c.getString(ifirstName) + ":" + c.getString(ilastName) + ":" + c.getString(ilastLogin) + ":" + c.getString(iuserType) + ":" + c.getString(ipassword) + ":" + c.getString(idisplayName) + ":" + c.getString(iisActive) + ":" + c.getString(idateCreated) + "\n";
        }

        return result;
    }

    public String[] getSingleUserData(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{ID, LoginEmail, FirstName, LastName, LastLogin, UserType, Password, DisplayName, IsActive, DateCreated};
        Cursor c = database.query(Database_Table_Users, columns, ID + "=" + id + "", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            String iID = c.getString(c.getColumnIndex(ID));
            String iloginEmail = c.getString(c.getColumnIndex(LoginEmail));
            String ifirstName = c.getString(c.getColumnIndex(FirstName));
            String ilastName = c.getString(c.getColumnIndex(LastName));
            String ilastLogin = c.getString(c.getColumnIndex(LastLogin));
            String iuserType = c.getString(c.getColumnIndex(UserType));
            String ipassword = c.getString(c.getColumnIndex(Password));
            String idisplayName = c.getString(c.getColumnIndex(DisplayName));
            String iisActive = c.getString(c.getColumnIndex(IsActive));
            String idateCreated = c.getString(c.getColumnIndex(DateCreated));

            String values[] = {iID, iloginEmail, ifirstName, ilastName, ilastLogin, iuserType, ipassword, idisplayName, iisActive, idateCreated};
            return values;
        }
        return null;
    }

    public String[] getSingleUserData(String email, String pswd) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{ID, LoginEmail, FirstName, LastName, LastLogin, UserType, Password, DisplayName, IsActive, DateCreated};
        Cursor c = database.query(Database_Table_Users, columns, "(" + LoginEmail + " = '" + email + "' AND " + Password + " = '" + pswd + "')", null, null, null, null);
        //String[] params = {"%" + email + "%", "%" + pswd + "%"};
        //String query = "SELECT * FROM Database_Table_Users WHERE LoginEmail like ? and Password like ? ";
        //Cursor c = database.rawQuery(query, params);
        if (c.getCount() > 0) {
            c.moveToFirst();
            String iID = c.getString(c.getColumnIndex(ID));
            String iloginEmail = c.getString(c.getColumnIndex(LoginEmail));
            String ifirstName = c.getString(c.getColumnIndex(FirstName));
            String ilastName = c.getString(c.getColumnIndex(LastName));
            String ilastLogin = c.getString(c.getColumnIndex(LastLogin));
            String iuserType = c.getString(c.getColumnIndex(UserType));
            String ipassword = c.getString(c.getColumnIndex(Password));
            String idisplayName = c.getString(c.getColumnIndex(DisplayName));
            String iisActive = c.getString(c.getColumnIndex(IsActive));
            String idateCreated = c.getString(c.getColumnIndex(DateCreated));

            String values[] = {iID, iloginEmail, ifirstName, ilastName, ilastLogin, iuserType, ipassword, idisplayName, iisActive, idateCreated};
            return values;
        }
        return null;
    }
    /*User Related Method*/


    /*Templates Related Method*/

    public long insertTemplateData(int ID, String templateName, String projectBrief) {

        ContentValues cv = new ContentValues();
        cv.put(TemplatesID, ID);
        cv.put(TemplateName, templateName);
        cv.put(ProjectBrief, projectBrief);

        long returnid = database.insert(Database_Table_Templates, null, cv);
        return returnid;

    }

    public int GetTemplateID(int id) {
        // TODO Auto-generated method stub
        String templateID = "";
        String[] columns = new String[]{TemplatesID, TemplateName, ProjectBrief};
        Cursor c = database.query(Database_Table_Templates, columns, TemplatesID + "=" + id + "", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            templateID = c.getString(c.getColumnIndex(TemplatesID));
        }
        if (templateID != null && !templateID.isEmpty()) {
            return Integer.parseInt(templateID);
        } else {
            return 0;
        }
    }

    //region GetEmployeeTemplates
    public ArrayList<ProjectInformation> GetEmployeeTemplates(int EmpID) {

        String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail"
                + " WHERE jobDetail."
                + EmployeeID + " = " + EmpID + " AND " + JobSynced + "=0";
        ArrayList<ProjectInformation> lstproject = new ArrayList<ProjectInformation>();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ProjectInformation projectInformation = new ProjectInformation();
                    projectInformation.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobDetailID)));
                    String employeeID = cursor.getString(cursor.getColumnIndex(EmployeeID));
                    projectInformation.ClientName = cursor.getString(3);
                    projectInformation.StoreName = cursor.getString(cursor.getColumnIndex(StoreName));
                    projectInformation.LocationName = cursor.getString(cursor.getColumnIndex(LocationName));
                    projectInformation.strJobDate = cursor.getString(cursor.getColumnIndex(JobDate));
                    projectInformation.strJobTime = cursor.getString(cursor.getColumnIndex(JobTime));
                    projectInformation.TemplateName = cursor.getString(cursor.getColumnIndex(JobTemplateName));
                    lstproject.add(projectInformation);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return lstproject;
    }

    //endregion
    public void DeleteTemplate(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_Templates, TemplatesID + "=" + templateID, null);
    }

    /*Templates Related Method*/


    /*Templates Files Related Method*/
    public long insertTemplateFilesData(int fk_TemplateID, String filePath, String MypeType) {
        ContentValues cv = new ContentValues();
        cv.put(FK_TemplateID, fk_TemplateID);
        cv.put(FilePath, filePath);
        cv.put(FileMymeType, MypeType);
        long returnid = database.insert(Database_Table_TemplatesFiles, null, cv);
        return returnid;
    }

    public void DeleteTemplateFiles(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_TemplatesFiles, FK_TemplateID + "=" + templateID, null);
    }

    /*Templates Files Related Method*/

    /*Templates Jobs Related Method*/
    public long insertJobDetailData(int JobID, String templatename, int employeeID, int templateID, String clientName, String storeName, String locationName, int clientStoreTemplateID, String jobDate, String jobStatusName, boolean jobSynced, String jobdate, String jobTime) {
        ContentValues cv = new ContentValues();
        cv.put(JobDetailID, JobID);
        cv.put(EmployeeID, employeeID);
        cv.put(TemplateID, templateID);
        cv.put(ClientName, clientName);
        cv.put(StoreName, storeName);
        cv.put(LocationName, locationName);
        cv.put(JobTemplateName, templatename);
        cv.put(ClientStoreTemplateID, clientStoreTemplateID);
        cv.put(JobDate, jobDate);
        cv.put(JobStatusName, jobStatusName);
        cv.put(JobSynced, 0);
        cv.put(JobDate, jobdate);
        cv.put(JobTime, jobTime);
        long returnid = database.insert(Database_Table_Job_Details, null, cv);
        return returnid;
    }


    //region Local Storage GetJobDetail Search Start
    public SpecificJobDetail GetJobDetail(int jobID) {

      /*  String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail, "
                + Database_Table_JobsQuestions + " jobQuestion," + Database_Table_TemplatesFiles + " templatesFiles,"
                + Database_Table_JobProduct + " jobProducts," + Database_Table_Users + " users where jobDetail."
                + JobDetailID + "=" + jobID + " OR jobQuestion."
                + Questions_FK_TemplateID + "= jobDetail." + TemplateID + " OR jobProducts." + JobProduct_FK_TemplateID + "= jobDetail."
                + TemplateID + " AND users." + ID + "= jobDetail."
                + EmployeeID + " OR templatesFiles." + FK_TemplateID + "=jobDetail." + TemplateID;*/
        String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail," + Database_Table_Users + " users " +
                "where jobDetail." +
                JobDetailID + "=" + jobID + " AND users."
                + ID + "= jobDetail." + EmployeeID + "";

        SpecificJobDetail specificJobDetail = new SpecificJobDetail();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    //JobDetail Start
                    SpecificJobDetail.JobsDetail jobDetail = specificJobDetail.new JobsDetail();
                    jobDetail.JobID = Integer.parseInt(cursor.getString(0));
                    jobDetail.EmployeeID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(EmployeeID)));
                    jobDetail.TemplateID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TemplateID)));
                    jobDetail.LocationName = cursor.getString(cursor.getColumnIndex(LocationName));
                    jobDetail.StoreName = cursor.getString(cursor.getColumnIndex(StoreName));
                    jobDetail.strJobDate = cursor.getString(cursor.getColumnIndex(JobDate));
                    jobDetail.strJobTime = cursor.getString(cursor.getColumnIndex(JobTime));
                    jobDetail.EmployeeName = cursor.getString(cursor.getColumnIndex(DisplayName));
                    jobDetail.TemplateName=cursor.getString(cursor.getColumnIndex(JobTemplateName));
                    specificJobDetail.lstJobDetail.add(jobDetail);
                    //JobDetail End
                    String GetQuestions = "SELECT  * FROM " + Database_Table_JobsQuestions + " ProjectQuestions where ProjectQuestions." + Questions_FK_TemplateID + "=" + specificJobDetail.lstJobDetail.get(0).TemplateID;
                    String GetProducts = "SELECT  * FROM " + Database_Table_JobProduct + " ProjectProducts where ProjectProducts." + JobProduct_FK_TemplateID + "=" + specificJobDetail.lstJobDetail.get(0).TemplateID;
                    String GetFiles = "SELECT  * FROM " + Database_Table_TemplatesFiles + " ProjectFiles where ProjectFiles." + FK_TemplateID + "=" + specificJobDetail.lstJobDetail.get(0).TemplateID;

                    //JobsQuestions
                    Cursor QuestionCursor = database.rawQuery(GetQuestions, null);
                    specificJobDetail.lstJobsQuestions = GetJobsQuestions(QuestionCursor);
                    //JobsQuestions

                    //JobProducts Start
                    Cursor ProductsCursor = database.rawQuery(GetProducts, null);
                    specificJobDetail.lstJobsProducts = GetJobsProducts(ProductsCursor);
                    //JobsProducts End

                    //JobFiles Start
                    Cursor FilesCursor = database.rawQuery(GetFiles, null);
                    specificJobDetail.lstJobsFiles = GetJobsFiles(FilesCursor);
                    break;
                }
            }
            //JobFiles End
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return specificJobDetail;
    }

    //Local Storage QuestionDetail Search Start
    public ArrayList<SpecificJobDetail.JobsQuestions> GetJobsQuestions(Cursor cursor) {
        SpecificJobDetail specificJobDetail = new SpecificJobDetail();
        ArrayList<SpecificJobDetail.JobsQuestions> lstJobsQuestions = new ArrayList<SpecificJobDetail.JobsQuestions>();
        while (cursor.moveToNext()) {
            if (lstJobsQuestions.size() == 0 || FindDuplicatedQuestions(lstJobsQuestions, Integer.parseInt(cursor.getString(cursor.getColumnIndex(QuestionID))))) {
                SpecificJobDetail.JobsQuestions jobsQuestions = specificJobDetail.new JobsQuestions();
                jobsQuestions.QuestionID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(QuestionID)));
                jobsQuestions.QuestionTemplateID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Questions_FK_TemplateID)));
                jobsQuestions.QuestionType = Integer.parseInt(cursor.getString(cursor.getColumnIndex(QuestionType)));
                jobsQuestions.JobQuestionSortOrder = Integer.parseInt(cursor.getString(cursor.getColumnIndex(QuestionSortOrder)));
                jobsQuestions.QuestionName = cursor.getString(cursor.getColumnIndex(QuestionTitle));
                if (jobsQuestions.QuestionType == 4 || jobsQuestions.QuestionType == 3) {
                    jobsQuestions.QustionsAnswers = GetQuestionAnswers(jobsQuestions.QuestionID, jobsQuestions.QuestionTemplateID);
                }
                lstJobsQuestions.add(jobsQuestions);
            }
        }
        return lstJobsQuestions;
    }

    public ArrayList<SpecificJobDetail.QustionsAnswers> GetQuestionAnswers(int QuestionID, int TemplateID) {
        String MY_QUERY = "SELECT  * FROM " + Database_Table_AnswerChoice + " answerschoice "
                + "WHERE answerschoice." + JobsQuestions_AnswerChoiceID + "=" + QuestionID + " AND answerschoice." + AnswerChoice_FK_TemplateID + "=" + TemplateID + "";

        ArrayList<SpecificJobDetail.QustionsAnswers> lstanswersChoice = new ArrayList<SpecificJobDetail.QustionsAnswers>();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SpecificJobDetail specificJobDetail = new SpecificJobDetail();
                    SpecificJobDetail.QustionsAnswers questionAnswers = specificJobDetail.new QustionsAnswers();
                    questionAnswers.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(AnswerChoiceID)));
                    questionAnswers.FK_Question = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobsQuestions_AnswerChoiceID)));
                    questionAnswers.QustionsAnswersTemplateID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(AnswerChoice_FK_TemplateID)));
                    questionAnswers.Title = cursor.getString(cursor.getColumnIndex(Title));
                    questionAnswers.SortOrder = Integer.parseInt(cursor.getString(cursor.getColumnIndex(AnswerChoiceSortOrder)));
                    lstanswersChoice.add(questionAnswers);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return lstanswersChoice;
    }

    public boolean FindDuplicatedQuestions(ArrayList<SpecificJobDetail.JobsQuestions> jobQuestions, int ID) {

        for (int i = 0; i < jobQuestions.size(); i++) {
            if (jobQuestions.get(i).QuestionID == ID) {
                return false;
            }
        }
        return true;
    }
    //Local Storage QuestionDetail search END


    //Local Storage JobProducts Search Start
    public ArrayList<SpecificJobDetail.JobsProducts> GetJobsProducts(Cursor cursor) {
        SpecificJobDetail specificJobDetail = new SpecificJobDetail();
        ArrayList<SpecificJobDetail.JobsProducts> lstJobsProducts = new ArrayList<SpecificJobDetail.JobsProducts>();
        while (cursor.moveToNext()) {
            if (lstJobsProducts.size() == 0 || FindDuplicatedProducts(lstJobsProducts, Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductID))))) {
                {
                    SpecificJobDetail.JobsProducts jobsProducts = specificJobDetail.new JobsProducts();
                    jobsProducts.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductID)));
                    jobsProducts.SortOrder = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductsSortOrder)));
                    jobsProducts.FK_ClientStoreTemplate = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobProduct_FK_TemplateID)));
                    jobsProducts.ProductName = cursor.getString(cursor.getColumnIndex(ProductTitle));
                    jobsProducts.ProductType = cursor.getString(cursor.getColumnIndex(ProductType));
                    jobsProducts.StockLevel = Integer.parseInt(cursor.getString(cursor.getColumnIndex(StockLevel)));
                    jobsProducts.CSLPSID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CSPLID)));
                    lstJobsProducts.add(jobsProducts);
                }
            }
        }
        return lstJobsProducts;
    }

    public boolean FindDuplicatedProducts(ArrayList<SpecificJobDetail.JobsProducts> jobProducts, int ID) {

        for (int i = 0; i < jobProducts.size(); i++) {
            if (jobProducts.get(i).ID == ID) {
                return false;
            }
        }
        return true;
    }
    //Local Storage JobProducts Search END


    //Local Storage JobFiles Search Start
    public ArrayList<SpecificJobDetail.JobsFiles> GetJobsFiles(Cursor cursor) {
        SpecificJobDetail specificJobDetail = new SpecificJobDetail();
        ArrayList<SpecificJobDetail.JobsFiles> lstJobsFiles = new ArrayList<SpecificJobDetail.JobsFiles>();
        while (cursor.moveToNext()) {
            if (lstJobsFiles.size() == 0 || FindDuplicatedFiles(lstJobsFiles, cursor.getString(cursor.getColumnIndex(FilePath)))) {
                {
                    SpecificJobDetail.JobsFiles jobFiles = specificJobDetail.new JobsFiles();
                    jobFiles.FileName = cursor.getString(cursor.getColumnIndex(FilePath));
                    jobFiles.fileMymeType = cursor.getString(cursor.getColumnIndex(FileMymeType));
                    lstJobsFiles.add(jobFiles);
                }
            }
        }
        return lstJobsFiles;
    }

    public boolean FindDuplicatedFiles(ArrayList<SpecificJobDetail.JobsFiles> jobProducts, String name) {

        for (int i = 0; i < jobProducts.size(); i++) {
            String FileName = jobProducts.get(i).FileName;
            if (FileName.equals(name)) {
                return false;
            }
        }
        return true;
    }
    //Local Storage JobFiles Search Star

    //Local Storage Jobs Search End


    public void DeleteTemplateJobs(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_Job_Details, TemplateID + "=" + templateID, null);
    }
    /*Templates Jobs Related Method*/


    /*Templates Quesitons Related Method*/
    public long InsertTemplatesQuestions(int FK_TemplateID, String Title, int Type, int SortOrder, int QuesID) {
        String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(QuestionID, QuesID);
        cv.put(Questions_FK_TemplateID, FK_TemplateID);
        cv.put(QuestionTitle, Title);
        cv.put(QuestionType, Type);
        cv.put(QuestionSortOrder, SortOrder);
        long returnid = database.insert(Database_Table_JobsQuestions, null, cv);
        return returnid;
    }

    public long InsertQuestionAnswerChoice(Integer FK_TemplateID, Integer AnswerChID, String TtLe, Integer SortOrder, int FK__QuestionID) {
        String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(AnswerChoiceID, AnswerChID);
        cv.put(AnswerChoice_FK_TemplateID, FK_TemplateID);
        cv.put(Title, TtLe);
        cv.put(AnswerChoiceSortOrder, SortOrder);
        cv.put(JobsQuestions_AnswerChoiceID, FK__QuestionID);
        long returnid = database.insert(Database_Table_AnswerChoice, null, cv);
        return returnid;
    }

    public void DeleteTemplateQuestions(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_JobsQuestions, Questions_FK_TemplateID + "=" + templateID, null);
    }

    public void DeleteTemplateAnswerChoice(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_AnswerChoice, AnswerChoice_FK_TemplateID + "=" + templateID, null);
    }
    /*Templates Quesitons Related Method*/

    /*Templates Products Related Methods Start*/
    public long InsertTemplatesProducts(Integer prodID, Integer SortOrder, Integer FK_TemplateID, String TtLe, String Type, Integer stockLevel, int CSPL) {
        String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(ProductID, prodID);
        cv.put(ProductsSortOrder, SortOrder);
        cv.put(JobProduct_FK_TemplateID, FK_TemplateID);
        cv.put(ProductTitle, TtLe);
        cv.put(ProductType, Type);
        cv.put(StockLevel, stockLevel);
        cv.put(CSPLID, CSPL);
        long returnid = database.insert(Database_Table_JobProduct, null, cv);
        return returnid;
    }

    //region DeleteTemplateProducts
    public void DeleteTemplateProducts(long templateID) {
        // TODO Auto-generated method stub
        database.delete(Database_Table_JobProduct, JobProduct_FK_TemplateID + "=" + templateID, null);
    }
    //endregion

    //region LocalProductSaving
    public long LocalProductSaving(int ParamFK_ClientStoreTemplateProducts, int ParamProductStockLevel, int ParamFK_Job) {
        String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues cv = new ContentValues();
        cv.put(FK_ClientStoreTemplateProducts, ParamFK_ClientStoreTemplateProducts);
        cv.put(ProductStockLevel, ParamProductStockLevel);
        cv.put(DateChecked, datetime);
        cv.put(FK_Job, ParamFK_Job);
        long returnid = database.insert(Database_Table_ClientStoreLocationProductStock, null, cv);
        return returnid;
    }
    //endregion

    //region DeleteLocalQuestions
    public void DeleteLocalProducts(int ParamFK_EmployeeJobs) {
        database.delete(Database_Table_ClientStoreLocationProductStock, FK_Job + "=" + ParamFK_EmployeeJobs, null);
    }
    //endregion

    //region GetLocalStoredJobProducts
    public ArrayList<SpecificJobDetail.JobsProducts> GetLocalStoredJobProducts() {
        String MY_QUERY = "SELECT  * FROM " + Database_Table_ClientStoreLocationProductStock + "";

        ArrayList<SpecificJobDetail.JobsProducts> lstJobsProducts = new ArrayList<SpecificJobDetail.JobsProducts>();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SpecificJobDetail specificJobDetail = new SpecificJobDetail();
                    SpecificJobDetail.JobsProducts jobProduct = specificJobDetail.new JobsProducts();
                    jobProduct.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FK_ClientStoreTemplateProducts)));
                    jobProduct.StockLevel = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductStockLevel)));
                    jobProduct.JobID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FK_Job)));
                    jobProduct.strDateChecked = cursor.getString(cursor.getColumnIndex(DateChecked));
                    lstJobsProducts.add(jobProduct);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return lstJobsProducts;
    }
    //endregion

    /*Templates Products Related Method End*/

    //region LocalQuestionSaving
    public long LocalQuestionSaving(int ParamFK_EmployeeJobs, int ParamFK_QuestionTemplate, int ParamFK_AnswerChoice, String paramAnswerValue, int QuestionType) {
        ContentValues cv = new ContentValues();
        cv.put(FK_EmployeeJobs, ParamFK_EmployeeJobs);
        cv.put(FK_QuestionTemplate, ParamFK_QuestionTemplate);
        cv.put(FK_answerChoice, ParamFK_AnswerChoice);
        cv.put(JobQuestionType, QuestionType);
        cv.put(answerValue, paramAnswerValue);
        long returnid = database.insert(Database_Table_EmployeeJobDetail, null, cv);
        return returnid;
    }

    //endregion
    //region DeleteLocalQuestions
    public void DeleteLocalQuestions(int ParamFK_EmployeeJobs) {
        database.delete(Database_Table_EmployeeJobDetail, FK_EmployeeJobs + "=" + ParamFK_EmployeeJobs, null);
    }

    public void MarkJobAsSynced(int jobID) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(JobSynced, 1);
        try {
            int updatedid = database.update(Database_Table_Job_Details, cv, JobDetailID + "=" + jobID, null);
        } catch (Exception exception) {
            String str = exception.getMessage();
        }
    }


    //region GetLocalStoredJobProducts
    public ArrayList<SpecificJobDetail.JobsQuestions> GetLocalStoredJobQuestions() {
        String MY_QUERY = "SELECT  * FROM " + Database_Table_EmployeeJobDetail + "";

        ArrayList<SpecificJobDetail.JobsQuestions> lstJobsQuestions = new ArrayList<SpecificJobDetail.JobsQuestions>();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SpecificJobDetail specificJobDetail = new SpecificJobDetail();
                    SpecificJobDetail.JobsQuestions jobsQuestions = specificJobDetail.new JobsQuestions();
                    jobsQuestions.QuestionID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FK_QuestionTemplate)));
                    jobsQuestions.jobID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FK_EmployeeJobs)));
                    jobsQuestions.FK_AnswerChoice = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FK_answerChoice)));
                    jobsQuestions.AnswerValue = cursor.getString(cursor.getColumnIndex(answerValue));
                    jobsQuestions.QuestionType = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobQuestionType)));
                    lstJobsQuestions.add(jobsQuestions);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return lstJobsQuestions;
    }
    //endregion
    //endregion

    public void DeleteJobsafterSyncing() {

        if (GetNonSyncedJobs() < 1) {
            database.delete(Database_Table_Templates, null, null);
            database.delete(Database_Table_TemplatesFiles, null, null);
            database.delete(Database_Table_Job_Details, null, null);
            database.delete(Database_Table_JobsQuestions, null, null);
            database.delete(Database_Table_AnswerChoice, null, null);
            database.delete(Database_Table_JobProduct, null, null);
            database.delete(Database_Table_ClientStoreLocationProductStock, null, null);
            database.delete(Database_Table_EmployeeJobDetail, null, null);

        } else {
            DeleteAllSavedJobsAfterSync();
        }
    }

    public int GetNonSyncedJobs() {
        String countQuery = "SELECT  * FROM " + Database_Table_Job_Details + " where " + JobSynced + "=0";
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        return count;
    }

    public ArrayList<ProjectInformation> GetSyncedJobs() {
      ArrayList<ProjectInformation> lstProjectInfo=new ArrayList<ProjectInformation>();
        String countQuery = "SELECT  * FROM " + Database_Table_Job_Details + " where " + JobSynced + "=1";
        Cursor cursor = database.rawQuery(countQuery, null);
        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ProjectInformation projectInformation = new ProjectInformation();
                    projectInformation.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobDetailID)));
                    lstProjectInfo.add(projectInformation);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return  lstProjectInfo;
    }

    public void DeleteAllSavedJobsAfterSync() {
        String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail WHERE jobDetail." + JobSynced + "=1";
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ProjectInformation projectInformation = new ProjectInformation();
                    projectInformation.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobDetailID)));
                    database.execSQL("DELETE FROM " + Database_Table_ClientStoreLocationProductStock + " WHERE " + FK_Job + "=" + projectInformation.ID + "");
                    database.execSQL("DELETE FROM " + Database_Table_EmployeeJobDetail + " WHERE " + FK_EmployeeJobs + "=" + projectInformation.ID + "");
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
    }

    //region GetEmployeeTemplates
    public ArrayList<ProjectInformation> GETALLJOBS() {

        String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail";

        ArrayList<ProjectInformation> lstproject = new ArrayList<ProjectInformation>();
        try {
            Cursor cursor = database.rawQuery(MY_QUERY, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ProjectInformation projectInformation = new ProjectInformation();
                    projectInformation.ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(JobDetailID)));
                    String employeeID = cursor.getString(cursor.getColumnIndex(EmployeeID));
                    projectInformation.ClientName = cursor.getString(3);
                    projectInformation.StoreName = cursor.getString(cursor.getColumnIndex(StoreName));
                    projectInformation.LocationName = cursor.getString(cursor.getColumnIndex(LocationName));
                    projectInformation.strJobDate = cursor.getString(cursor.getColumnIndex(JobDate));
                    projectInformation.strJobTime = cursor.getString(cursor.getColumnIndex(JobTime));
                    projectInformation.TemplateName = cursor.getString(cursor.getColumnIndex(JobTemplateName));
                    lstproject.add(projectInformation);
                }
            }
        } catch (Exception err) {
            Exception ex = err;
            String Name = err.getMessage();
        }
        return lstproject;
    }

    //region GetEmployeeTemplates
    public boolean GetSpedificJob(int JobID) {
        boolean IsExist = false;
        String MY_QUERY = "SELECT  * FROM " + Database_Table_Job_Details + " jobDetail WHERE " + JobDetailID + " =" + JobID;
        ArrayList<ProjectInformation> alljobs = GETALLJOBS();
        for (int Count = 0; Count < alljobs.size(); Count++) {
            if (alljobs.get(Count).ID == JobID) {
                IsExist = true;
                break;
            }
        }
        return IsExist;
    }
}