package com.example.khalil.pixidustwebapi.Entities;

import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Khalil on 1/25/2018.
 */

public class SpecificJobDetail implements BaseClass<SpecificJobDetail>,Serializable {
    public int ID;

    public String jobTime;

    public String jobDate;

    public String retailname;

    public String merchendisername;

    public ArrayList<JobsDetail> lstJobDetail;

    public ArrayList<JobsQuestions> lstJobsQuestions;

    public ArrayList<JobsProducts> lstJobsProducts;

    public ArrayList<JobsFiles> lstJobsFiles;

    public SpecificJobDetail() {
        lstJobDetail = new ArrayList<>();
        lstJobDetail = new ArrayList<>();
        lstJobsQuestions = new ArrayList<JobsQuestions>();
        lstJobsProducts = new ArrayList<JobsProducts>();
        lstJobsFiles = new ArrayList<JobsFiles>();
    }

    public int GetId() {
        return ID;
    }

    public SpecificJobDetail GetSpecificProperties(SpecificJobDetail detail) {
        detail.jobDate = this.lstJobDetail.get(0).strJobDate;
        detail.jobTime = this.lstJobDetail.get(0).strJobDate;
        detail.retailname = this.lstJobDetail.get(0).StoreName;
        detail.merchendisername = this.lstJobDetail.get(0).EmployeeName;
        detail.lstJobDetail = this.lstJobDetail;
        detail.lstJobsProducts = this.lstJobsProducts;
        detail.lstJobsQuestions = this.lstJobsQuestions;
        detail.lstJobsFiles = this.lstJobsFiles;
        return detail;
    }

    public SpecificJobDetail[] SetList(SpecificJobDetail[] lstgeneric) {
        return null;
    }

    public SpecificJobDetail[] GetList() {
        return null;
    }

    @Override
    public void SetId(int i) {

    }

    public class JobsDetail implements Serializable {

        public int JobID;

        public int ClientID;

        public String JobStatusName;

        public String StoreName;

        public String strCompletionDate;

        public String strJobDate;

        public String strJobTime;

        public String EmployeeName;

        public int EmployeeID;

        public int ClientStoreTemplateID;

        public boolean IsReminerEmailSent;

        public String ClientName;

        public String LocationName;

        public String TemplateName;

        public String PhotoPath;

        public String Brief;

        public int TemplateID;

        public String VideoPath;
    }

    public class JobsQuestions implements Serializable  {

        public int QuestionTemplateID;

        public int QuestionID;

        public int JobQuestionSortOrder;
        public int jobID;
        public String QuestionName;
        public int QuestionType;
        public int FK_AnswerChoice;
        public String AnswerChoiceValue;
        public String AnswerValue;
        public ArrayList<QustionsAnswers> QustionsAnswers;

        public JobsQuestions() {
            QustionsAnswers = new ArrayList<QustionsAnswers>();
        }
    }

    public class QustionsAnswers implements Serializable  {
        public int ID;
        public String Title;
        public int SortOrder;
        public int FK_Question;
        public int QustionsAnswersTemplateID;
    }

    public class JobsProducts implements Serializable  {
        public int ID;
        public int SortOrder;
        public String ProductType;
        public int FK_ClientStoreTemplate;
        public String ProductName;
        public int StockLevel;
        public int CSLPSID;
        public int JobID;
        public String strDateChecked;

    }

    public class JobsFiles implements Serializable  {
        public int ID;
        public int FK_Template;
        public String FileName;
        public String DownloadFileBase64Content;
        public String fileMymeType;
        public String fileName;

    }

}
