package com.example.khalil.pixidustwebapi.Entities;

import com.example.khalil.pixidustwebapi.Entities.Interfaces.BaseClass;

/**
 * Created by Khalil on 2/2/2018.
 */

public class FileResponse implements BaseClass<FileResponse> {
    public int FK_Template;
    public String FileName;
    public String Base64Str;
    public String MymeType;
    @Override
    public int GetId() {
        return 0;
    }

    @Override
    public FileResponse[] SetList(FileResponse[] lstgeneric) {
        return new FileResponse[0];
    }

    @Override
    public FileResponse GetSpecificProperties(FileResponse detail) {
        return null;
    }

    @Override
    public FileResponse[] GetList() {
        return new FileResponse[0];
    }

    @Override
    public void SetId(int i) {

    }
}
