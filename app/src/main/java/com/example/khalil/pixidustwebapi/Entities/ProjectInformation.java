package com.example.khalil.pixidustwebapi.Entities;

import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;

/**
 * Created by Khalil on 1/23/2018.
 */

public class ProjectInformation implements BaseClass<ProjectInformation> {
    public int ID;
    public String ClientName;
    public String StoreName;
    public String LocationName;
    public String TemplateName;
    public String strJobDate;
    public String strJobTime;
    public ProjectInformation[] lstProjects;

    public int GetId() {
        return ID;
    }

    public ProjectInformation[] SetList(ProjectInformation[] lstgeneric) {
        lstProjects = lstgeneric;
        return lstProjects;
    }

    public ProjectInformation[] GetList() {
        return this.lstProjects;
    }

    @Override
    public void SetId(int i) {

    }

    public ProjectInformation GetSpecificProperties(ProjectInformation detail) {
        return null;

    }
}
