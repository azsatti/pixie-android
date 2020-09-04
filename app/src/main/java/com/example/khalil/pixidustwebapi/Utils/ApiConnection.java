
package com.example.khalil.pixidustwebapi.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khalil.pixidustwebapi.Adapters.CustomAdapter;
import com.example.khalil.pixidustwebapi.Entities.FileResponse;
import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;
import com.example.khalil.pixidustwebapi.Entities.Interfaces.IJobDetail;
import com.example.khalil.pixidustwebapi.Entities.LoginUser;
import com.example.khalil.pixidustwebapi.Entities.JobSavingResponse;
import com.example.khalil.pixidustwebapi.Entities.ProjectInformation;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.Activities.Projects;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.khalil.pixidustwebapi.Activities.MainActivity.txtInvalidUser;

/**
 * Created by Khalil on 1/23/2018.
 */


public class ApiConnection extends AsyncTask<Void, Void, BaseClass> {
    String URL;
    String StrJsonObject;
    String methodName;
    String Type;
    public static Context currActivity;
    static ProgressDialog progDialog;
    public static ProgressDialog jobSavingProgDialog;
    ListView lstView;
    public IJobDetail jobDetail = null;
    public IJobDetail uploadType;

    public ApiConnection(String uri, String strJsonObject, Context currentActivity, String parammethodName, ListView lst, String type) {
        URL = uri;
        lstView = lst;
        StrJsonObject = strJsonObject;
        currActivity = currentActivity;
        methodName = parammethodName;
        Type = type;
    }

    @Override
    protected BaseClass doInBackground(Void... params) {
        switch (methodName) {
            case "Login": {
                BaseClass loginUser = new LoginUser();
                loginUser = ConnectToApi(URL, StrJsonObject, methodName);
                return loginUser;
            }
            case "Employeejobs": {
                BaseClass jobsList = new LoginUser();
                jobsList = ConnectToApi(URL, StrJsonObject, methodName);
                return jobsList;
            }
            case "SpecificJob": {
                BaseClass jobsList = new SpecificJobDetail();
                jobsList = ConnectToApi(URL, StrJsonObject, methodName);
                return jobsList;
            }
            case "Video": {
                StrJsonObject = RTSP.getUrlVideoRTSP(StrJsonObject);
                return null;
            }
            case "SaveProductStock": {
                BaseClass productsavingresponse = new JobSavingResponse();
                productsavingresponse = ConnectToApi(URL, StrJsonObject, methodName);
                return productsavingresponse;
            }
            case "Savedropdowncheckbox": {
                BaseClass productsavingresponse = new JobSavingResponse();
                productsavingresponse = ConnectToApi(URL, StrJsonObject, methodName);
                return productsavingresponse;
            }
            case "SaveSingleMultiLineQuestions": {
                BaseClass productsavingresponse = new JobSavingResponse();
                productsavingresponse = ConnectToApi(URL, StrJsonObject, methodName);
                return productsavingresponse;
            }
            case "DownloadforOfflineMode": {
                BaseClass jobsList = new SpecificJobDetail();
                jobsList = ConnectToApi(URL, StrJsonObject, methodName);
                return jobsList;
            }
            case "FileResponse": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
            case "UploadJobImages": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
            case "FileNameSavingRequest": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
            case "MarkMultipleJobCompleted": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
            case "MarkJobComplete": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
            case "Logout": {
                BaseClass fileResponse = new FileResponse();
                fileResponse = ConnectToApi(URL, StrJsonObject, methodName);
                return fileResponse;
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        switch (methodName) {
            case "Login": {
                progDialog = ProgressDialog.show(currActivity, "Signing in",
                        "Please wait while we are signing you in..");
                break;
            }
            case "Employeejobs": {
                progDialog = ProgressDialog.show(currActivity, "Loading Jobs", "Please Wait While Loading Jobs");
                break;
            }
            case "SpecificJob": {
                progDialog = ProgressDialog.show(currActivity, "Job Detail", "Please Wait While Loading Job Detail");
                break;
            }
            case "Video": {
                progDialog = ProgressDialog.show(currActivity, "Loading Vido", "Please Wait While Loading Video");
                break;
            }
            case "SaveProductStock": {
                progDialog = ProgressDialog.show(currActivity, "Submitting Job", "Please wait while submitting your job");
                jobSavingProgDialog = progDialog;
                break;
            }
            case "DownloadforOfflineMode": {
                progDialog = ProgressDialog.show(currActivity, "Downloading Job Detail", "Please Wait While Downloading Job Detail");
                break;
            }
            case "FileResponse": {
                progDialog = ProgressDialog.show(currActivity, "File Downloading", "Please Wait While Downloading File");
                break;
            }
        }
    }

    @Override
    protected void onPostExecute(BaseClass result) {
        if (result != null) {
            switch (methodName) {
                case "Login": {
                    LoginUser loginUser = (LoginUser) result;
                    int ID = result.GetId();
                    if (ID == 0) {
                        txtInvalidUser.setText("Invalid UserName and Password");
                        txtInvalidUser.setVisibility(View.VISIBLE);
                    } else {
                        UserSessionToken.SetUserToken(loginUser.StoreUserToken);
                        Intent intent = new Intent(currActivity, Projects.class);
                        intent.putExtra("UserID", "" + ID + "");
                        txtInvalidUser.setVisibility(View.INVISIBLE);
                        currActivity.startActivity(intent);
                    }
                    progDialog.hide();
                    break;
                }
                case "Employeejobs": {
                    BaseClass<ProjectInformation> result1 = result;
                    FeedReaderDbConn con = new FeedReaderDbConn(currActivity);
                    con.open();
                    ArrayList<ProjectInformation> jobslist = con.GETALLJOBS();
                    ProjectInformation[] lstProjectInfo = result1.GetList();
                    ArrayList<ProjectInformation> jobsDetail = new ArrayList<ProjectInformation>(Arrays.asList(lstProjectInfo));
                    ArrayList<ProjectInformation> tempJobsDetail = new ArrayList<ProjectInformation>(Arrays.asList(lstProjectInfo));
                    for (int Count = 0; Count < tempJobsDetail.size(); Count++) {
                        if (con.GetSpedificJob(tempJobsDetail.get(Count).ID))
                            jobsDetail.remove(tempJobsDetail.get(Count));
                    }
                    tempJobsDetail = null;
                    con.close();
                    if (jobsDetail.size() > 0) {
                        lstView.setAdapter(new CustomAdapter(currActivity, jobsDetail));
                    } else {
                        jobDetail.NoJobAssigned();
                    }
                    progDialog.hide();
                    break;
                }
                case "SpecificJob": {
                    BaseClass<SpecificJobDetail> result1 = result;
                    SpecificJobDetail specificJobDetail = new SpecificJobDetail();
                    specificJobDetail = result1.GetSpecificProperties(specificJobDetail);
                    jobDetail.GetJobDetail(specificJobDetail);
                    progDialog.hide();
                    break;
                }
                case "Video": {
                    jobDetail.GetVideoUrl(StrJsonObject);
                    progDialog.hide();
                    break;
                }
                case "SaveProductStock": {
                    JobSavingResponse SavingResponse = (JobSavingResponse) result;
                    jobDetail.GetCheckDropQuestionResponse(SavingResponse, Type);
                    break;
                }
                case "DownloadforOfflineMode": {
                    BaseClass<SpecificJobDetail> result1 = result;
                    SpecificJobDetail specificJobDetail = new SpecificJobDetail();
                    specificJobDetail = result1.GetSpecificProperties(specificJobDetail);
                    jobDetail.DownloadJobDetail(specificJobDetail);
                    progDialog.hide();
                    break;
                }
                case "FileResponse": {
                    BaseClass<FileResponse> result1 = result;
                    FileResponse fileResponse = new FileResponse();
                    fileResponse = (FileResponse) result1;
                    jobDetail.GetFileResponse(fileResponse);
                    progDialog.hide();
                    break;
                }
                case "UploadJobImages": {
                    JobSavingResponse SavingResponse = (JobSavingResponse) result;
                    List<String> fileSavingloopparam = Arrays.asList(Type.split(","));
                    if (Integer.parseInt(fileSavingloopparam.get(1)) > 0) {
                        jobSavingProgDialog.hide();
                    }
                    jobDetail.GetFileResponse(SavingResponse, Integer.parseInt(fileSavingloopparam.get(0)));
                    break;
                }
                case "MarkMultipleJobCompleted": {
                    JobSavingResponse SavingResponse = (JobSavingResponse) result;
                    ApiConnection.jobSavingProgDialog.hide();
                    Toast.makeText(currActivity, "Jobs Synced Successfully", Toast.LENGTH_LONG).show();
                    FeedReaderDbConn dbconection = new FeedReaderDbConn(currActivity);
                    dbconection.open();
                    dbconection.DeleteJobsafterSyncing();
                    dbconection.close();
                    break;
                }
                case "MarkJobComplete": {
                    JobSavingResponse SavingResponse = (JobSavingResponse) result;
                    ApiConnection.jobSavingProgDialog.hide();
                    Toast.makeText(currActivity, "Jobs Saved Successfully", Toast.LENGTH_LONG).show();
                    break;
                }
                case "Logout": {
                    JobSavingResponse SavingResponse = (JobSavingResponse) result;
                    break;
                }
            }
        } else {
            Toast.makeText(currActivity, "Some Error Occured", Toast.LENGTH_LONG).show();
        }
    }

    public BaseClass ConnectToApi(String url, String strojbect, String methodName) {
        RestClient client = new RestClient(url);
        client.AddHeader("Content-type", "application/json");
        client.AddHeader("UserKey", "EmployeeWebApi");
        client.AddHeader("UserToken", UserSessionToken.GetUserToken());
        client.AddParam(strojbect);
        try {
            ExecuteResult execRes = client
                    .Execute(RestClient.RequestMethod.POST);
            // Now Login call can be proceed further if login button is
            // clicked again
            if (execRes.ResponseCode == 201 || execRes.ResponseCode == 200) {
                Gson gson = new Gson();
                switch (methodName) {
                    case "Login": {
                        LoginUser loginUser = new LoginUser();
                        loginUser = gson.fromJson(execRes.ResponseMessage, LoginUser.class);
                        FeedReaderDbConn con = new FeedReaderDbConn(currActivity);
                        con.open();
                        String[] arr = con.getSingleUserData(loginUser.ID);
                        if (arr == null) {
                            con.insertUserData(loginUser.ID, loginUser.LoginEmail, loginUser.FirstName, loginUser.LastName, loginUser.LastLogin, loginUser.FK_UserType, loginUser.Password, loginUser.DisplayName, loginUser.IsActive);
                           }
                        con.close();
                        return loginUser;
                    }
                    case "Employeejobs": {
                        /*    gsonbuilder.registerTypeAdapter(Date.class, new DateDeserilizer());*/
                        BaseClass<ProjectInformation> projectInfo = new ProjectInformation();
                        ProjectInformation[] lstjobinfo = gson.fromJson(execRes.ResponseMessage, ProjectInformation[].class);
                        projectInfo.SetList(lstjobinfo);
                        return projectInfo;
                    }
                    case "SpecificJob": {
                        /*    gsonbuilder.registerTypeAdapter(Date.class, new DateDeserilizer());*/
                        BaseClass<SpecificJobDetail> specificJobDetail = new SpecificJobDetail();
                        specificJobDetail = gson.fromJson(execRes.ResponseMessage, SpecificJobDetail.class);
                        return specificJobDetail;
                    }
                    case "SaveProductStock": {
                        BaseClass<JobSavingResponse> jobSavingResponse = new JobSavingResponse();
                        jobSavingResponse = gson.fromJson(execRes.ResponseMessage, JobSavingResponse.class);
                        return jobSavingResponse;
                    }
                    case "DownloadforOfflineMode": {
                        BaseClass<SpecificJobDetail> specificJobDetail = new SpecificJobDetail();
                        specificJobDetail = gson.fromJson(execRes.ResponseMessage, SpecificJobDetail.class);
                        return specificJobDetail;
                    }
                    case "FileResponse": {
                        BaseClass<FileResponse> fileResponse = new FileResponse();
                        fileResponse = gson.fromJson(execRes.ResponseMessage, FileResponse.class);
                        return fileResponse;
                    }
                    case "UploadJobImages": {
                        BaseClass<JobSavingResponse> fileResponse = new JobSavingResponse();
                        fileResponse = gson.fromJson(execRes.ResponseMessage, JobSavingResponse.class);
                        return fileResponse;
                    }
                    case "MarkMultipleJobCompleted": {
                        BaseClass<JobSavingResponse> fileResponse = new JobSavingResponse();
                        fileResponse = gson.fromJson(execRes.ResponseMessage, JobSavingResponse.class);
                        return fileResponse;
                    }
                    case "MarkJobComplete": {
                        BaseClass<JobSavingResponse> fileResponse = new JobSavingResponse();
                        fileResponse.SetId(1);
                        //fileResponse = gson.fromJson(execRes.ResponseMessage, JobSavingResponse.class);
                        return fileResponse;
                    }
                    case "Logout": {
                        BaseClass<JobSavingResponse> fileResponse = new JobSavingResponse();
                        fileResponse = gson.fromJson(execRes.ResponseMessage, JobSavingResponse.class);
                        return fileResponse;
                    }
                }
            } else {
                ApiConnection.progDialog.hide();
                Toast.makeText(ApiConnection.currActivity, "Some error occured 001", Toast.LENGTH_LONG);
            }
        }
        catch (Exception exception) {
            String msg = exception.getMessage();
        }
        return null;
    }
}

