package com.example.khalil.pixidustwebapi.Utils;

import android.os.Environment;

import com.example.khalil.pixidustwebapi.Activities.JobDetail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Khalil on 2/4/2018.
 */

public class WriteFilesToLocalStorage {
    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {
        fileName =GetFileName(fileName);
        String foldername = "" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateName + "_" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateID;
        File file = new File(Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles" );
        if(!file.exists()){
            if(file.mkdir()){
                System.out.print("Success");
            }
        }
        file = new File(Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles/" + foldername);
        if(!file.exists()){
            if(file.mkdir()){
                System.out.print("Success");
            }
        }
        file = new File(Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles/" + foldername + "/" + fileName);
        if(!file.exists()) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(content);
            bos.flush();
            bos.close();
        }
    }
    public static String GetFileName(String filename){
        int pos = filename.indexOf(".");
        String FileNameWithOutExtension="";
        String extenstion="";
        if (pos > 0) {
            FileNameWithOutExtension = filename.substring(0, pos-1);
            extenstion=filename.substring(pos+1,filename.length());
        }
        filename = FileNameWithOutExtension + "_" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateID+"."+extenstion;
        return  filename;
    }
}
