package com.example.khalil.pixidustwebapi.Activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalil.pixidustwebapi.Activities.JobDetail;
import com.example.khalil.pixidustwebapi.Adapters.CustomAdapter;
import com.example.khalil.pixidustwebapi.Adapters.QuestionFragment;
import com.example.khalil.pixidustwebapi.Entities.FileResponse;
import com.example.khalil.pixidustwebapi.Entities.Interfaces.IJobDetail;
import com.example.khalil.pixidustwebapi.Entities.JobSavingResponse;
import com.example.khalil.pixidustwebapi.Entities.ProjectInformation;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;
import com.example.khalil.pixidustwebapi.Utils.ApiConnection;
import com.example.khalil.pixidustwebapi.Utils.CheckInternetConnection;
import com.example.khalil.pixidustwebapi.Utils.ConvertPathIntoBase64;
import com.example.khalil.pixidustwebapi.Utils.FeedReaderDbConn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Projects extends AppCompatActivity implements IJobDetail {
    ArrayList<ProjectInformation> lstprojectInformation = new ArrayList<ProjectInformation>();
    Context context;
    MenuItem syncItem;
    public static int UserID = 0;
    ListView lstView;
    static LinearLayout lynrlyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        String strUserID;
        Intent intent = getIntent();
        strUserID = intent.getStringExtra("UserID");
        UserID = Integer.parseInt(strUserID);
        lstView = (ListView) findViewById(R.id.listView);
        lynrlyt = (LinearLayout) findViewById(R.id.NoJobLynrlyt);
        lstView.setOnItemClickListener(new ListClickHandler());
        if (CheckInternetConnection.IsNetworkAvailable(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("FK_Employee", UserID);
                object.put("FK_ClientStoreTemplate", null);
                object.put("FK_Client", null);
            } catch (Exception ex) {
            }
            ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/GetAllJobs", object.toString(), this, "Employeejobs", lstView, "");
            apiConnection.jobDetail = this;

            apiConnection.execute();
        } else {
            ProgressDialog progDialog = ProgressDialog.show(this, "Loading Jobs", "Please Wait While Loading Jobs");
            FeedReaderDbConn con = new FeedReaderDbConn(this);
            con.open();
            ArrayList<ProjectInformation> lstProjectInformation = con.GetEmployeeTemplates(UserID);
            if (lstProjectInformation.size() > 0) {
                lstView.setAdapter(new CustomAdapter(this, lstProjectInformation));
            } else {
                lynrlyt.setVisibility(View.VISIBLE);
                lstView.setVisibility(View.GONE);
            }
            progDialog.hide();
        }
    }

    public void CallToSpecificJobActivity(int ID) {
        try {
            Intent intent = new Intent(this, JobDetail.class);
            // add the selected text item to our intent.
            intent.putExtra("JobID", "" + ID + "");
            startActivity(intent);
        } catch (Exception exception) {
            String message = exception.getMessage();
        }
    }

    @Override
    public void GetJobDetail(SpecificJobDetail jobDetail) {

    }

    @Override
    public void GetVideoUrl(String RTSPURL) {

    }

    @Override
    public void GetCheckDropQuestionResponse(JobSavingResponse productsavingres, String Type) {
        if (productsavingres.ID > 0) {
            int IsLast = 0;
            int Count = 0;
            FeedReaderDbConn localdbconnection = new FeedReaderDbConn(this);
            localdbconnection.open();
            ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions = localdbconnection.GetLocalStoredJobQuestions();
            if (IsFileQuestionExist(0, lstJobQuestions)) {
                for (SpecificJobDetail.JobsQuestions jobQuestions : lstJobQuestions) {
                    int questionType = jobQuestions.QuestionType;
                    JSONObject uploadFileRequest = new JSONObject();
                    Count = Count + 1;
                    if (questionType == 5) {
                        String imgBase64 = ConvertPathIntoBase64.ConvertPathIntoBase64(jobQuestions.AnswerValue);
                        imgBase64 = imgBase64.substring(imgBase64.indexOf(",") + 1);
                        try {
                            String fIleName = jobQuestions.AnswerValue.substring(jobQuestions.AnswerValue.lastIndexOf("/"), jobQuestions.AnswerValue.length());
                            String strSingleLine = fIleName + "," + jobQuestions.jobID + "," + jobQuestions.QuestionID + "|";
                            uploadFileRequest.put("strquestionDetail", strSingleLine);
                            uploadFileRequest.put("EmployeeID", UserID);
                            uploadFileRequest.put("imageBase64", imgBase64);
                            uploadFileRequest.put("JobID", jobQuestions.jobID);
                            uploadFileRequest.put("QuestionID", jobQuestions.QuestionID);
                            uploadFileRequest.put("fileName", fIleName);

                        } catch (Exception ex) {
                        }
                        if (!IsFileQuestionExist(Count + 1, lstJobQuestions)) {
                            IsLast = 1;
                            try {
                                uploadFileRequest.put("IsLast", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/UploadJobsImages", uploadFileRequest.toString(), this, "UploadJobImages", null, "" + Count + "," + IsLast);
                        apiConnection.jobDetail = this;
                        apiConnection.execute();
                        break;
                    }
                }
            } else {
                ArrayList<ProjectInformation> lstjobsid = localdbconnection.GetSyncedJobs();
                StringBuilder jobsId = new StringBuilder();
                for (ProjectInformation job : lstjobsid) {
                    String jobid = job.ID + ",";
                    jobsId.append(jobid);
                }
                JSONObject Markjobascomplete = new JSONObject();
                try {
                    Markjobascomplete.put("EmployeeID", UserID);
                    Markjobascomplete.put("jobsIDs", jobsId.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/MarkMultipleJobCompleted", Markjobascomplete.toString(), this, "MarkMultipleJobCompleted", null, "");
                apiConnection.jobDetail = this;
                apiConnection.execute();
            }
        } else {
            ApiConnection.jobSavingProgDialog.hide();
            Toast.makeText(this, "Some Error Occured During Syncing Jobs Job", Toast.LENGTH_LONG).show();
        }
    }

    public boolean IsFileQuestionExist(int Count, ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions) {
        boolean IsExist = false;
        for (Count = Count; Count < lstJobQuestions.size(); Count++) {
            if (lstJobQuestions.get(Count).QuestionType == 5) {
                IsExist = true;
                return IsExist;
            }
        }
        return IsExist;
    }

    @Override
    public void DownloadJobDetail(SpecificJobDetail jobDetail) {

    }

    @Override
    public void GetFileResponse(FileResponse fileResponse) {

    }

    @Override
    public void GetFileResponse(JobSavingResponse fileResponse, int Count) {
        if (fileResponse.ID > 0) {
            int IsLast = 0;
            FeedReaderDbConn localdbconnection = new FeedReaderDbConn(this);
            localdbconnection.open();
            ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions = localdbconnection.GetLocalStoredJobQuestions();
            if (IsFileQuestionExist(Count, lstJobQuestions)) {
                for (Count = Count; Count < lstJobQuestions.size(); Count++) {
                    int questionType = lstJobQuestions.get(Count).QuestionType;
                    JSONObject uploadFileRequest = new JSONObject();
                    if (questionType == 5) {
                        String imgBase64 = ConvertPathIntoBase64.ConvertPathIntoBase64(lstJobQuestions.get(Count).AnswerValue);
                        imgBase64 = imgBase64.substring(imgBase64.indexOf(",") + 1);
                        try {
                            String fIleName = lstJobQuestions.get(Count).AnswerValue.substring(lstJobQuestions.get(Count).AnswerValue.lastIndexOf("/") + 1);
                            String strSingleLine = fIleName + "," + lstJobQuestions.get(Count).jobID + "," + lstJobQuestions.get(Count).QuestionID + "|";
                            uploadFileRequest.put("strquestionDetail", strSingleLine);
                            uploadFileRequest.put("EmployeeID", UserID);
                            uploadFileRequest.put("imageBase64", imgBase64);
                            uploadFileRequest.put("JobID", lstJobQuestions.get(Count).jobID);
                            uploadFileRequest.put("QuestionID", lstJobQuestions.get(Count).QuestionID);
                            uploadFileRequest.put("fileName", fIleName);

                        } catch (Exception ex) {
                        }
                        if (!IsFileQuestionExist(Count + 1, lstJobQuestions)) {
                            IsLast = 1;
                            try {
                                uploadFileRequest.put("IsLast", 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Count = Count + 1;
                        ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/UploadJobsImages", uploadFileRequest.toString(), this, "UploadJobImages", null, "" + Count + "," + IsLast);
                        apiConnection.jobDetail = this;
                        apiConnection.execute();
                        break;
                    }
                }
            } else {
                ArrayList<ProjectInformation> lstjobsid = localdbconnection.GetSyncedJobs();
                StringBuilder jobsId = new StringBuilder();
                for (ProjectInformation job : lstjobsid) {
                    String jobid = job.ID + ",";
                    jobsId.append(jobid);
                }
                JSONObject Markjobascomplete = new JSONObject();
                try {
                    Markjobascomplete.put("EmployeeID", UserID);
                    Markjobascomplete.put("jobsIDs", jobsId.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/MarkMultipleJobCompleted", Markjobascomplete.toString(), this, "MarkMultipleJobCompleted", null, "");
                apiConnection.jobDetail = this;
                apiConnection.execute();
            }
        } else {
            ApiConnection.jobSavingProgDialog.hide();
            Toast.makeText(this, "Some Error Occured During Syncing Jobs Job", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void NoJobAssigned() {
        lynrlyt.setVisibility(View.VISIBLE);
        lstView.setVisibility(View.GONE);
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            ProjectInformation projectInfo = (ProjectInformation) adapter.getItemAtPosition(position);
            CallToSpecificJobActivity(projectInfo.ID);
            // create intent to start another activity
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu manu) {
        getMenuInflater().inflate(R.menu.main, manu);
        syncItem = (MenuItem) manu.findItem(R.id.id_Sync);
        if (!CheckInternetConnection.IsNetworkAvailable(this)) {
            syncItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_Sync) {
            try {
                JobDetail jobDetail = new JobDetail();
                SyncLocalDataToLive();
            } catch (Exception ex) {
                String Message = ex.getMessage();
            }
        } else if (id == R.id.id_Login) {
            if (CheckInternetConnection.IsNetworkAvailable(this)) {
                JSONObject object = new JSONObject();
                try {
                    object.put("ID", UserID);
                } catch (Exception ex) {
                }
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/Login/Logout", object.toString(), this, "Logout", null, "");
                apiConnection.execute();
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return true;
    }

    public void SyncLocalDataToLive() {
        int productsCount = 0;
        int questionCount = 0;
        Integer questionType = 0;
        StringBuilder sbSingleLine = new StringBuilder();
        StringBuilder sbProductSaving = new StringBuilder();
        StringBuilder sbDropCheckbox = new StringBuilder();
        FeedReaderDbConn con = new FeedReaderDbConn(this);
        con.open();
        ArrayList<SpecificJobDetail.JobsProducts> lstJobProducts = con.GetLocalStoredJobProducts();
        ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions = con.GetLocalStoredJobQuestions();
        if (lstJobProducts.size() > 0 || lstJobQuestions.size() > 0) {
            if (lstJobProducts.size() > 0) {
                for (SpecificJobDetail.JobsProducts jobProduct : lstJobProducts) {
                    String commaseperatedstr = jobProduct.ID + "," + jobProduct.StockLevel + "," + jobProduct.JobID + "," + jobProduct.CSLPSID + "|";
                    sbProductSaving.append(commaseperatedstr);
                    productsCount++;
                }
            }
            if (lstJobQuestions.size() > 0) {
                for (SpecificJobDetail.JobsQuestions jobQuestions : lstJobQuestions) {
                    questionType = jobQuestions.QuestionType;
                    switch (questionType) {
                        case 1:
                            String strSingleLine = jobQuestions.AnswerValue + "," + jobQuestions.jobID + "," + jobQuestions.QuestionID + "|";
                            sbSingleLine.append(strSingleLine);
                            break;
                        case 2:
                            String strMultiLine = jobQuestions.AnswerValue + "," + jobQuestions.jobID + "," + jobQuestions.QuestionID + "|";
                            sbSingleLine.append(strMultiLine);
                            break;
                        case 3:
                            String strcheckbox = jobQuestions.FK_AnswerChoice + "," + jobQuestions.jobID + "," + jobQuestions.QuestionID + "|";
                            sbDropCheckbox.append(strcheckbox);
                            break;
                        case 4:
                            String strdropdown = jobQuestions.FK_AnswerChoice + "," + jobQuestions.jobID + "," + jobQuestions.QuestionID + "|";
                            sbDropCheckbox.append(strdropdown);
                            break;
                    }
                    questionCount++;
                }
            }
            try {
                JSONObject productSavingRequest = new JSONObject();
                productSavingRequest.put("strproductsdetail", sbProductSaving.toString());
                productSavingRequest.put("strquestionSigmultilineDetail", sbSingleLine.toString());
                productSavingRequest.put("strquestioncheckdropquestDetail", sbDropCheckbox.toString());
                productSavingRequest.put("EmployeeID", UserID);
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/UpdateProductStock", productSavingRequest.toString(), this, "SaveProductStock", null, "Syncing");
                apiConnection.jobDetail = this;
                apiConnection.execute();
            } catch (Exception ex) {
                String message = ex.getMessage();
            }
        } else {
            Toast.makeText(this, "There is no job for syncing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        StartLoginActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    public void StartLoginActivity() {
        if (CheckInternetConnection.IsNetworkAvailable(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("ID", UserID);
            } catch (Exception ex) {
            }
            ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/Login/Logout", object.toString(), this, "Logout", null, "");
            apiConnection.execute();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
