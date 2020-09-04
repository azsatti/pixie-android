package com.example.khalil.pixidustwebapi.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalil.pixidustwebapi.Adapters.FilesFragment;
import com.example.khalil.pixidustwebapi.Adapters.ProductFragment;
import com.example.khalil.pixidustwebapi.Adapters.QuestionFragment;
import com.example.khalil.pixidustwebapi.Adapters.VideoFragment;
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
import com.example.khalil.pixidustwebapi.Utils.WriteFilesToLocalStorage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobDetail extends AppCompatActivity implements IJobDetail, View.OnClickListener {
    ListView productLstView;
    TabLayout tabs;
    View adjustSaveBtn;
    String strJobID = "";
    public static SpecificJobDetail specificJobDetail;
    Button downlaod;
    Button btnCancel;
    static String fileSavingType;
    static int EmployeeID;
    Button btnSave;
    static Context jobdetailContext;
    MenuItem syncItem;
    public final JSONObject object = new JSONObject();
    StringBuilder sbSingleLine = new StringBuilder();
    StringBuilder sbProductSaving = new StringBuilder();
    StringBuilder sbDropCheckbox = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        int JobID;
        jobdetailContext = this;
        Intent intent = getIntent();
        strJobID = intent.getStringExtra("JobID");
        JobID = Integer.parseInt(strJobID);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);

        downlaod = (Button) findViewById(R.id.btnDownload);
        btnSave = (Button) findViewById(R.id.btnSave);
        //SetButtonClickListener
        downlaod.setOnClickListener(this); // calling onClick() method
        btnSave.setOnClickListener(this); // calling onClick() method
        adjustSaveBtn = findViewById(R.id.adjustSaveBtn);
        //SetButtonsClickListener
        if (!CheckInternetConnection.IsNetworkAvailable(this)) {
            downlaod.setVisibility(View.GONE);
            adjustSaveBtn.setVisibility(View.VISIBLE);
            FeedReaderDbConn con = new FeedReaderDbConn(this);
            con.open();
            SpecificJobDetail specifilJobDetail = con.GetJobDetail(JobID);
            GetJobDetail(specifilJobDetail);
        } else {
            try {
                object.put("ID", JobID);
                object.put("EmployeeID", null);
            } catch (Exception ex) {
            }
            ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/GetJobToSubmit", object.toString(), this, "SpecificJob", productLstView, "");
            apiConnection.jobDetail = this;
            apiConnection.execute();
        }
    }

    @Override
    public void GetJobDetail(SpecificJobDetail jobDetail) {
        try {
            TextView jobTime = (TextView) findViewById(R.id.jobTime);
            TextView jobDate = (TextView) findViewById(R.id.jobDate);
            TextView retailname = (TextView) findViewById(R.id.retailname);
            TextView merchendisername = (TextView) findViewById(R.id.merchendisername);
            jobTime.setText(jobDetail.lstJobDetail.get(0).strJobTime);
            jobDate.setText(jobDetail.lstJobDetail.get(0).strJobDate);
            retailname.setText(jobDetail.lstJobDetail.get(0).StoreName);
            merchendisername.setText(jobDetail.lstJobDetail.get(0).EmployeeName);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            specificJobDetail = jobDetail;
            Adapter adapter = new Adapter(getSupportFragmentManager());
       /*Products Bundle*/
            Bundle productBundle = new Bundle();
            productBundle.putSerializable("ProductsList", specificJobDetail.lstJobsProducts);
            ProductFragment productfragment = new ProductFragment();
            productfragment.setArguments(productBundle);
        /*Question Bundle*/
            Bundle quesionBundle = new Bundle();
            quesionBundle.putSerializable("QuestionList", specificJobDetail.lstJobsQuestions);
            QuestionFragment questionFragment = new QuestionFragment();
            questionFragment.setArguments(quesionBundle);
        /*Question Bundle*/
        /*filesBundle Bundle*/
            Bundle fileBundle = new Bundle();
            fileBundle.putSerializable("FilesList", specificJobDetail.lstJobsFiles);
            FilesFragment filesfragment = new FilesFragment();
            filesfragment.setArguments(fileBundle);
        /*filesBundle Bundle*/
        /*Video Bundle*/
            Bundle videoBundle = new Bundle();
            videoBundle.putString("VideoUrl", specificJobDetail.lstJobDetail.get(0).VideoPath);
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(videoBundle);
        /*filesBundle Bundle*/
        /*Add To Adapter*/
            adapter.addFragment(questionFragment, "Questions");
            adapter.addFragment(productfragment, "Products");
            adapter.addFragment(filesfragment, "Files");
            adapter.addFragment(videoFragment, "Video");
        /*Add To Adapter*/
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);
        } catch (Exception ex) {
            String exeception = ex.getMessage();
        }
    }

    @Override
    public void onClick(View v) {
        ApiConnection apiConnection;
        switch (v.getId()) {
            case R.id.btnDownload:
                downlaod.setTextColor(Color.parseColor("#ffffff"));
                downlaod.setBackgroundColor(Color.parseColor("#40e0d0"));
                btnSave.setTextColor(Color.parseColor("#40e0d0"));
                btnSave.setBackgroundColor(Color.parseColor("#e1e1e1"));
                Toast.makeText(jobdetailContext, "Job Downloaded Successfully", Toast.LENGTH_LONG);
                apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/DownloadforOfflineMode", object.toString(), JobDetail.this, "DownloadforOfflineMode", null, "");
                apiConnection.jobDetail = JobDetail.this;
                apiConnection.execute();
                // do your code
                break;
            case R.id.btnSave:
                btnSave.setTextColor(Color.parseColor("#ffffff"));
                btnSave.setBackgroundColor(Color.parseColor("#40e0d0"));
                downlaod.setTextColor(Color.parseColor("#40e0d0"));
                downlaod.setBackgroundColor(Color.parseColor("#e1e1e1"));
                if (!CheckInternetConnection.IsNetworkAvailable(this)) {
                    FeedReaderDbConn con = new FeedReaderDbConn(this);
                    con.open();
                    if (!AreProductsEmpty()) {
                        if (!AreQuestionsEmpty()) {
                            LocalProductSaving(con);
                        } else {
                            ShowConfirmBox("Questions", this, con, "Are you sure you want to save job without questions?");
                        }
                    } else {
                        if (!AreQuestionsEmpty()) {
                            ShowConfirmBox("Products", this, con, "Are you sure you want to save job without products?");
                        } else {
                            ShowAlertBox(this);
                        }
                    }

                } else {
                        if (!AreProductsEmpty()) {
                            if (!AreQuestionsEmpty()) {
                                LiveProductSaving();
                                LiveQuestionSaving();
                                LiveQuestionsAndProductSaving();
                            } else {
                                ShowConfirmBox("LiveQuestion", this, null, "Are you sure you want to save job without questions?");
                            }
                        } else {
                            if (!AreQuestionsEmpty()) {
                                ShowConfirmBox("LiveProducts", this, null, "Are you sure you want to save job without products?");
                            } else {
                                ShowAlertBox(this);
                            }
                        }
                }
                break;
            default:
                break;
        }
    }

    public boolean AreProductsEmpty() {
        boolean IsEmpty = true;
        for (String object : ProductFragment.ProductsData) {
            if (object != null && !object.isEmpty()) {
                IsEmpty = false;
                break;
            }
        }
        return IsEmpty;
    }

    public void ShowConfirmBox(final String Type, final Activity context, final FeedReaderDbConn con, String message) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialouge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        TextView TextView01 = (TextView) dialog.findViewById(R.id.TextView01);
        text.setText(message);
        text.setTextColor(Color.WHITE);
        TextView01.setTextColor(Color.WHITE);
        Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
        Button btnno = (Button) dialog.findViewById(R.id.btnno);
        Button btnok = (Button) dialog.findViewById(R.id.btnok);
        btnok.setVisibility(View.GONE);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type.equals("Questions")) {
                    dialog.dismiss();
                    Long productreponse = LocalProductSaving(con);
                    if (productreponse > 0) {
                        con.open();
                        con.MarkJobAsSynced(specificJobDetail.lstJobDetail.get(0).JobID);
                        con.close();
                    }
                } else if (Type.equals("Products")) {
                    if (!AreQuestionsEmpty()) {
                        dialog.dismiss();
                        long questionSavingResponse = LocalQuestionSaving(con);
                        if (questionSavingResponse > 0) {
                            con.open();
                            con.MarkJobAsSynced(specificJobDetail.lstJobDetail.get(0).JobID);
                            con.close();
                        }
                    } else {
                        con.close();
                        dialog.dismiss();
                    }
                } else if (Type.equals("LiveQuestion")) {
                    LiveProductSaving();
                    LiveQuestionsAndProductSaving();
                    dialog.dismiss();
                } else if (Type.equals("LiveProducts")) {
                    LiveQuestionSaving();
                    LiveQuestionsAndProductSaving();
                    dialog.dismiss();
                }

            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type.equals("Questions")) {
                    con.DeleteLocalProducts(specificJobDetail.lstJobDetail.get(0).JobID);
                    con.close();
                } else if (Type.equals("Products")) {
                    con.close();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Display display = context.getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // deprecated
        int height = display.getHeight();
        lp.width = (int) (width * 0.90);
        dialog.getWindow().setAttributes(lp);
    }

    public void ShowAlertBox(final Activity context) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialouge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        TextView TextView01 = (TextView) dialog.findViewById(R.id.TextView01);
        text.setText("Please select questions or product to complete job");
        text.setTextColor(Color.WHITE);
        TextView01.setTextColor(Color.WHITE);
        Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
        Button btnno = (Button) dialog.findViewById(R.id.btnno);
        Button btnok = (Button) dialog.findViewById(R.id.btnok);
        btnyes.setVisibility(View.GONE);
        btnno.setVisibility(View.GONE);
        btnok.setVisibility(View.VISIBLE);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Display display = context.getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // deprecated
        int height = display.getHeight();
        lp.width = (int) (width * 0.90);
        dialog.getWindow().setAttributes(lp);
    }

    public long LocalProductSaving(FeedReaderDbConn con) {
        ProgressDialog progDialog = ProgressDialog.show(this, "Submitting Job", "Please wait while submitting your job");
        progDialog.show();
        long ProductSavingResponse = 1;
        Integer productsCount = 0;
        Integer FK_ClientStoreTemplate = 0;
        Integer ProductID = 0;
        for (String object : ProductFragment.ProductsData) {
            if (ProductSavingResponse != -1) {
                if (object != null && !object.isEmpty()) {
                    ProductID = ProductFragment.productsList.get(productsCount).ID;
                    FK_ClientStoreTemplate = ProductFragment.productsList.get(productsCount).FK_ClientStoreTemplate;
                    ProductSavingResponse = con.LocalProductSaving(ProductID, Integer.parseInt(object), specificJobDetail.lstJobDetail.get(0).JobID);
                    productsCount++;
                }
            } else {
                Toast.makeText(this, "Some error occured during job submittion", Toast.LENGTH_LONG).show();
                con.DeleteLocalProducts(specificJobDetail.lstJobDetail.get(0).JobID);
                break;
            }
        }
        if (ProductSavingResponse > 0) {
            if (!AreQuestionsEmpty()) {
                long questionSavingResponse = LocalQuestionSaving(con);
                if (questionSavingResponse > 0) {
                    Toast.makeText(this, "Job Submitted Successfully", Toast.LENGTH_LONG).show();
                    con.open();
                    con.MarkJobAsSynced(specificJobDetail.lstJobDetail.get(0).JobID);
                    con.close();
                    progDialog.hide();
                } else {
                    Toast.makeText(this, "Some Error Occured During Saving Job", Toast.LENGTH_LONG).show();
                    progDialog.hide();
                }
            }
        }
        con.close();
        if (ProductSavingResponse > 0) {
            Toast.makeText(this, "Job Submitted Successfully", Toast.LENGTH_LONG).show();
            con.MarkJobAsSynced(specificJobDetail.lstJobDetail.get(0).JobID);
            progDialog.hide();
        } else {
            Toast.makeText(this, "Some Error Occured During Saving Job", Toast.LENGTH_LONG).show();
            progDialog.hide();
        }
        return ProductSavingResponse;
    }

    public boolean AreQuestionsEmpty() {
        boolean IsEmpty = true;
        for (String object : QuestionFragment.QuestionsAnswers) {
            if (object != null && !object.isEmpty()) {
                IsEmpty = false;
                break;
            }
        }
        return IsEmpty;
    }

    public long LocalQuestionSaving(FeedReaderDbConn con) {
        Integer questionType = 0;
        Integer questionCount = 0;
        Integer questionID = 0;
        StringBuilder sbSingleLine = new StringBuilder();
        StringBuilder sbDropCheckbox = new StringBuilder();
        long ReponseID = 0;
        for (String Question : QuestionFragment.QuestionsAnswers) {
            if (ReponseID != -1) {
                if (Question != null && !Question.isEmpty()) {
                    questionType = QuestionFragment.questionList.get(questionCount).QuestionType;
                    questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                    switch (questionType) {
                        case 1:
                            questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                            ReponseID = con.LocalQuestionSaving(specificJobDetail.lstJobDetail.get(0).JobID, questionID, 0, Question, questionType);
                            break;
                        case 2:
                            questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                            ReponseID = con.LocalQuestionSaving(specificJobDetail.lstJobDetail.get(0).JobID, questionID, 0, Question, questionType);
                            break;
                        case 3:
                            questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                            ReponseID = con.LocalQuestionSaving(specificJobDetail.lstJobDetail.get(0).JobID, questionID, Integer.parseInt(Question), "", questionType);
                            break;
                        case 4:
                            questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                            ReponseID = con.LocalQuestionSaving(specificJobDetail.lstJobDetail.get(0).JobID, questionID, Integer.parseInt(Question), "", questionType);
                            break;
                        case 5:
                            questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                            ReponseID = con.LocalQuestionSaving(specificJobDetail.lstJobDetail.get(0).JobID, questionID, 0, Question, questionType);
                            break;
                    }
                }
                questionCount++;
            } else {
                Toast.makeText(this, "Some Error Occured During Saving Job", Toast.LENGTH_LONG);
                con.DeleteLocalProducts(specificJobDetail.lstJobDetail.get(0).JobID);
                con.DeleteLocalQuestions(specificJobDetail.lstJobDetail.get(0).JobID);
                break;
            }
        }
        con.close();
        if (ReponseID > 0) {
            Toast.makeText(this, "Job Submitted Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Some Error Occured During Submitting your Job", Toast.LENGTH_LONG).show();
        }
        return ReponseID;
    }


    public void LiveProductSaving(int ProductID, int productsCount, int empID, String Type) {
        Integer questionType = 0;
        Integer questionCount = 0;
        Integer questionID = 0;
        EmployeeID = empID;

        if (Type == "DirectUploading") {
            LiveProductSaving();
            LiveQuestionSaving();
            LiveQuestionsAndProductSaving();
        } else {
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
                    productSavingRequest.put("EmployeeID", empID);
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
    }

    public void LiveProductSaving() {
        int ProductID = 0;
        int productsCount = 0;
        for (String object : ProductFragment.ProductsData) {
            if (object != null && !object.isEmpty()) {
                ProductID = ProductFragment.productsList.get(productsCount).ID;
                int StockID = ProductFragment.productsList.get(productsCount).CSLPSID;
                String commaseperatedstr = ProductID + "," + object + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + StockID + "|";
                sbProductSaving.append(commaseperatedstr);
            }
            productsCount++;
        }
    }

    public void LiveQuestionSaving() {
        Integer questionType = 0;
        Integer questionCount = 0;
        Integer questionID = 0;
        for (String object : QuestionFragment.QuestionsAnswers) {
            if (object != null && !object.isEmpty()) {
                questionType = QuestionFragment.questionList.get(questionCount).QuestionType;
                switch (questionType) {
                    case 1:
                        questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                        String strSingleLine = object + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + questionID + "|";
                        sbSingleLine.append(strSingleLine);
                        break;
                    case 2:
                        questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                        String strMultiLine = object + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + questionID + "|";
                        sbSingleLine.append(strMultiLine);
                        break;
                    case 3:
                        questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                        String strcheckbox = object + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + questionID + "|";
                        sbDropCheckbox.append(strcheckbox);
                        break;
                    case 4:
                        questionID = QuestionFragment.questionList.get(questionCount).QuestionID;
                        String strdropdown = object + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + questionID + "|";
                        sbDropCheckbox.append(strdropdown);
                        break;
                }
            }
            questionCount++;
        }
    }

    public void LiveQuestionsAndProductSaving() {
        try {
            JSONObject productSavingRequest = new JSONObject();
            productSavingRequest.put("strproductsdetail", sbProductSaving.toString());
            productSavingRequest.put("strquestionSigmultilineDetail", sbSingleLine.toString());
            productSavingRequest.put("strquestioncheckdropquestDetail", sbDropCheckbox.toString());
            productSavingRequest.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
            ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/UpdateProductStock", productSavingRequest.toString(), JobDetail.jobdetailContext, "SaveProductStock", null, "DirectUploading");
            apiConnection.jobDetail = this;
            apiConnection.execute();
        } catch (JSONException ex) {
            String exception = ex.getMessage();
        }
    }

    @Override
    public void GetCheckDropQuestionResponse(JobSavingResponse productsavingres, String Type) {
        if (productsavingres.ID > 0) {
            int IsLast = 0;
            int Count = 0;
            fileSavingType = Type;
            boolean IsQuestionType = false;
            if (Type == "DirectUploading") {
                if (IsFileQuestionExist(0, null, "DirectUploading")) {
                    for (Count = Count; Count < QuestionFragment.questionList.size(); Count++) {
                        String fileName = GetFileName(Count);
                        if (fileName != null && !fileName.isEmpty()) {
                            int questionType = QuestionFragment.questionList.get(Count).QuestionType;
                            JSONObject uploadFileRequest = new JSONObject();
                            if (questionType == 5) {
                                String imgBase64 = ConvertPathIntoBase64.ConvertPathIntoBase64(fileName);
                                imgBase64 = imgBase64.substring(imgBase64.indexOf(",") + 1);
                                try {
                                    String fIleName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
                                    String strSingleLine = fIleName + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + QuestionFragment.questionList.get(Count).QuestionID + "|";
                                    uploadFileRequest.put("strquestionDetail", strSingleLine);
                                    uploadFileRequest.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                                    uploadFileRequest.put("imageBase64", imgBase64);
                                    uploadFileRequest.put("JobID", specificJobDetail.lstJobDetail.get(0).JobID);
                                    uploadFileRequest.put("QuestionID", QuestionFragment.questionList.get(Count).QuestionID);
                                    uploadFileRequest.put("fileName", fIleName);
                                } catch (Exception ex) {
                                }
                                if (!IsFileQuestionExist(Count + 1, null, "liveuploading")) {
                                    IsLast = 1;
                                    try {
                                        uploadFileRequest.put("IsLast", 1);
                                    } catch (Exception e) {
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
                    }
                } else {
                    JSONObject Markjobascomplete = new JSONObject();
                    try {
                        Markjobascomplete.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                        Markjobascomplete.put("JobID", specificJobDetail.lstJobDetail.get(0).JobID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/MarkJobComplete", Markjobascomplete.toString(), this, "MarkJobComplete", null, "");
                    apiConnection.jobDetail = this;
                    apiConnection.execute();
                    btnSave.setEnabled(false);
                    downlaod.setEnabled(false);
                }
            } else {
                FeedReaderDbConn localdbconnection = new FeedReaderDbConn(this);
                localdbconnection.open();
                ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions = localdbconnection.GetLocalStoredJobQuestions();
                if (IsFileQuestionExist(0, lstJobQuestions, "sync")) {
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
                                uploadFileRequest.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                                uploadFileRequest.put("imageBase64", imgBase64);
                                uploadFileRequest.put("JobID", jobQuestions.jobID);
                                uploadFileRequest.put("QuestionID", jobQuestions.QuestionID);
                                uploadFileRequest.put("fileName", fIleName);

                            } catch (Exception ex) {
                            }
                            if (!IsFileQuestionExist(Count + 1, lstJobQuestions, "sync")) {
                                IsLast = 1;
                                try {
                                    uploadFileRequest.put("IsLast", 1);
                                } catch (Exception e) {
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
                        Markjobascomplete.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                        Markjobascomplete.put("jobsIDs", jobsId.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/MarkMultipleJobCompleted", Markjobascomplete.toString(), this, "MarkMultipleJobCompleted", null, "");
                    apiConnection.jobDetail = this;
                    apiConnection.execute();
                    btnSave.setEnabled(false);
                    downlaod.setEnabled(false);
                }
            }
        } else {
            ApiConnection.jobSavingProgDialog.hide();
            Toast.makeText(ApiConnection.currActivity, "Some Error Occured During Uploading Job", Toast.LENGTH_LONG);
        }
    }

    public boolean IsFileQuestionExist(int Count, ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions, String Type) {
        boolean IsExist = false;
        if (Type == "sync") {
            for (Count = Count; Count < lstJobQuestions.size(); Count++) {
                if (lstJobQuestions.get(Count).QuestionType == 5) {
                    IsExist = true;
                    return IsExist;
                }
            }
            return IsExist;
        } else {
            for (Count = Count; Count < QuestionFragment.questionList.size(); Count++) {
                int questionType = QuestionFragment.questionList.get(Count).QuestionType;
                if (questionType == 5) {
                    try {
                        String result = QuestionFragment.QuestionsAnswers[Count];
                        if (result != null && !result.isEmpty()) {
                            IsExist = true;
                            return IsExist;
                        }
                    } catch (Exception ex) {
                        IsExist = false;
                    }
                }
            }
            return IsExist;
        }
    }


    @Override
    public void GetFileResponse(JobSavingResponse fileResponse, int Count) {
        int IsLast = 0;
        boolean IsQuestionType = false;
        if (fileResponse.ID > 0) {
            if (fileSavingType == "DirectUploading") {
                if (IsFileQuestionExist(Count, null, "liveuploading")) {
                    for (Count = Count; Count < QuestionFragment.questionList.size(); Count++) {
                        int questionType = QuestionFragment.questionList.get(Count).QuestionType;
                        JSONObject uploadFileRequest = new JSONObject();
                        if (questionType == 5) {
                            String fileName = GetFileName(Count);
                            if (fileName != null && !fileName.isEmpty()) {
                                String imgBase64 = ConvertPathIntoBase64.ConvertPathIntoBase64(fileName);
                                imgBase64 = imgBase64.substring(imgBase64.indexOf(",") + 1);
                                try {
                                    String fIleName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                                    String strSingleLine = fIleName + "," + specificJobDetail.lstJobDetail.get(0).JobID + "," + QuestionFragment.questionList.get(Count).QuestionID + "|";
                                    uploadFileRequest.put("strquestionDetail", strSingleLine);
                                    uploadFileRequest.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                                    uploadFileRequest.put("imageBase64", imgBase64);
                                    uploadFileRequest.put("JobID", specificJobDetail.lstJobDetail.get(0).JobID);
                                    uploadFileRequest.put("QuestionID", QuestionFragment.questionList.get(Count).QuestionID);
                                    uploadFileRequest.put("fileName", fIleName);
                                } catch (Exception ex) {
                                }
                                if (!IsFileQuestionExist(Count + 1, null, "liveuploading")) {
                                    IsLast = 1;
                                    try {
                                        uploadFileRequest.put("IsLast", 1);
                                    } catch (Exception e) {
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
                    }
                } else {
                    ApiConnection.jobSavingProgDialog.hide();
                    Toast.makeText(ApiConnection.currActivity, "Job Submitted Successfully", Toast.LENGTH_LONG).show();
                    btnSave.setEnabled(false);
                    downlaod.setEnabled(false);
                }
            } else {
                if (fileResponse.ID > 0) {
                    FeedReaderDbConn localdbconnection = new FeedReaderDbConn(this);
                    localdbconnection.open();
                    ArrayList<SpecificJobDetail.JobsQuestions> lstJobQuestions = localdbconnection.GetLocalStoredJobQuestions();
                    if (IsFileQuestionExist(Count, lstJobQuestions, "sync")) {
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
                                    uploadFileRequest.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                                    uploadFileRequest.put("imageBase64", imgBase64);
                                    uploadFileRequest.put("JobID", lstJobQuestions.get(Count).jobID);
                                    uploadFileRequest.put("QuestionID", lstJobQuestions.get(Count).QuestionID);
                                    uploadFileRequest.put("fileName", fIleName);

                                } catch (Exception ex) {
                                }
                                if (!IsFileQuestionExist(Count + 1, lstJobQuestions, "sync")) {
                                    IsLast = 1;
                                    try {
                                        uploadFileRequest.put("IsLast", 1);
                                    } catch (Exception e) {
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
                            Markjobascomplete.put("EmployeeID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                            Markjobascomplete.put("jobsIDs", jobsId.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/MarkMultipleJobCompleted", Markjobascomplete.toString(), this, "MarkMultipleJobCompleted", null, "");
                        apiConnection.jobDetail = this;
                        apiConnection.execute();
                        btnSave.setEnabled(false);
                        downlaod.setEnabled(false);
                    }
                } else {
                    ApiConnection.jobSavingProgDialog.hide();
                    Toast.makeText(ApiConnection.currActivity, "Some Error Occured During Syncing Jobs Job", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            ApiConnection.jobSavingProgDialog.hide();
            Toast.makeText(ApiConnection.currActivity, "Some Error Occured During Uploading Job", Toast.LENGTH_LONG).show();
        }
    }

    public String GetFileName(int Count) {
        try {
            String fileanme = QuestionFragment.QuestionsAnswers[Count];
            return fileanme;
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public void NoJobAssigned() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Projects.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("UserID", "" + specificJobDetail.lstJobDetail.get(0).EmployeeID);
        startActivity(intent);
        finish();
    }

    @Override
    public void DownloadJobDetail(SpecificJobDetail jobDetail) {
        FeedReaderDbConn con = new FeedReaderDbConn(this);
        con.open();
        ArrayList<ProjectInformation> jobs = con.GETALLJOBS();
        Integer TemplateID = con.GetTemplateID(jobDetail.lstJobDetail.get(0).TemplateID);
        if (TemplateID <= 0) {
            con.insertTemplateData(jobDetail.lstJobDetail.get(0).TemplateID, jobDetail.lstJobDetail.get(0).TemplateName, jobDetail.lstJobDetail.get(0).Brief);
            for (int i = 0; i < jobDetail.lstJobsFiles.size(); i++) {
                con.insertTemplateFilesData(jobDetail.lstJobDetail.get(0).TemplateID, jobDetail.lstJobsFiles.get(i).FileName, jobDetail.lstJobsFiles.get(i).fileMymeType);
                byte[] decodedBytes = Base64.decodeBase64(jobDetail.lstJobsFiles.get(i).DownloadFileBase64Content.getBytes());
                try {
                    WriteFilesToLocalStorage.writeByteArraysToFile(jobDetail.lstJobsFiles.get(i).FileName, decodedBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //region Insert Templates Questions
            for (int i = 0; i < jobDetail.lstJobsQuestions.size(); i++) {
                long questionID = con.InsertTemplatesQuestions(jobDetail.lstJobDetail.get(0).TemplateID, jobDetail.lstJobsQuestions.get(i).QuestionName, jobDetail.lstJobsQuestions.get(i).QuestionType, jobDetail.lstJobsQuestions.get(i).JobQuestionSortOrder, jobDetail.lstJobsQuestions.get(i).QuestionID);
                if (jobDetail.lstJobsQuestions.get(i).QuestionType == 3 || jobDetail.lstJobsQuestions.get(i).QuestionType == 4) {
                    for (int questionanswer = 0; questionanswer < jobDetail.lstJobsQuestions.get(i).QustionsAnswers.size(); questionanswer++) {
                        long questionanswerID = con.InsertQuestionAnswerChoice(jobDetail.lstJobDetail.get(0).TemplateID, jobDetail.lstJobsQuestions.get(i).QustionsAnswers.get(questionanswer).ID, jobDetail.lstJobsQuestions.get(i).QustionsAnswers.get(questionanswer).Title, jobDetail.lstJobsQuestions.get(i).QustionsAnswers.get(questionanswer).SortOrder, jobDetail.lstJobsQuestions.get(i).QustionsAnswers.get(questionanswer).FK_Question);
                    }
                }
            }
            //endregion

            //region Insert Templates Products
            for (int jobProduct = 0; jobProduct < jobDetail.lstJobsProducts.size(); jobProduct++) {
                long productsID = con.InsertTemplatesProducts(jobDetail.lstJobsProducts.get(jobProduct).ID, jobDetail.lstJobsProducts.get(jobProduct).SortOrder, jobDetail.lstJobsProducts.get(jobProduct).FK_ClientStoreTemplate, jobDetail.lstJobsProducts.get(jobProduct).ProductName, jobDetail.lstJobsProducts.get(jobProduct).ProductType, jobDetail.lstJobsProducts.get(jobProduct).StockLevel, jobDetail.lstJobsProducts.get(jobProduct).CSLPSID);
            }
            //endregion
        }
        con.insertJobDetailData(jobDetail.lstJobDetail.get(0).JobID, jobDetail.lstJobDetail.get(0).TemplateName, jobDetail.lstJobDetail.get(0).EmployeeID, jobDetail.lstJobDetail.get(0).TemplateID, jobDetail.lstJobDetail.get(0).ClientName, jobDetail.lstJobDetail.get(0).StoreName, jobDetail.lstJobDetail.get(0).LocationName, jobDetail.lstJobDetail.get(0).ClientStoreTemplateID, jobDetail.lstJobDetail.get(0).strJobDate, jobDetail.lstJobDetail.get(0).JobStatusName, false, jobDetail.lstJobDetail.get(0).strJobDate, jobDetail.lstJobDetail.get(0).strJobTime);
        Toast.makeText(ApiConnection.currActivity, "Job Downloaded Successfully", Toast.LENGTH_LONG).show();
        downlaod.setEnabled(false);
        con.close();
    }

    @Override
    public void GetFileResponse(FileResponse fileResponse) {

    }

    @Override
    public void GetVideoUrl(String RTSPURL) {
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
                LiveProductSaving(0, 0, specificJobDetail.lstJobDetail.get(0).EmployeeID, "syncing");
            } catch (Exception ex) {
                String Message = ex.getMessage();
            }
        }
        if (id == R.id.id_Login) {
            if (CheckInternetConnection.IsNetworkAvailable(this)) {
                JSONObject object = new JSONObject();
                try {
                    object.put("ID", specificJobDetail.lstJobDetail.get(0).EmployeeID);
                } catch (Exception ex) {
                }
                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/Login/Logout", object.toString(), this, "Logout", null, "");
                apiConnection.execute();
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        if (id == android.R.id.home) {
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, Projects.class);
            intent.putExtra("UserID", "" + specificJobDetail.lstJobDetail.get(0).EmployeeID);
            startActivity(intent);
            return true;
        }
        return true;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
