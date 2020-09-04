package com.example.khalil.pixidustwebapi.Entities;

import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;

/**
 * Created by Khalil on 2/1/2018.
 */

public class JobSavingResponse implements BaseClass<JobSavingResponse> {
    public String ResponseMessage;
    public int ID;

    @Override
    public int GetId() {
        return 0;
    }

    public void SetId(int Id) { ID = Id; }

    @Override
    public JobSavingResponse[] SetList(JobSavingResponse[] lstgeneric) {
        return new JobSavingResponse[0];
    }

    @Override
    public JobSavingResponse GetSpecificProperties(JobSavingResponse detail) {
        return null;
    }

    @Override
    public JobSavingResponse[] GetList() {
        return new JobSavingResponse[0];
    }
}
