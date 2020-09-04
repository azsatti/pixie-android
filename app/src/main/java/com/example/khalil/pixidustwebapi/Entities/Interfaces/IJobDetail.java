package com.example.khalil.pixidustwebapi.Entities.Interfaces;

import com.example.khalil.pixidustwebapi.Entities.FileResponse;
import com.example.khalil.pixidustwebapi.Entities.JobSavingResponse;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;

/**
 * Created by Khalil on 1/26/2018.
 */

public interface IJobDetail {
    void GetJobDetail(SpecificJobDetail jobDetail);
    void GetVideoUrl(String RTSPURL);
    void GetCheckDropQuestionResponse(JobSavingResponse productsavingres,String Type);
    void DownloadJobDetail(SpecificJobDetail jobDetail);
    void GetFileResponse(FileResponse fileResponse);
    void GetFileResponse(JobSavingResponse fileResponse,int Count);
    void NoJobAssigned();
}
