package com.example.khalil.pixidustwebapi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.khalil.pixidustwebapi.Activities.JobDetail;
import com.example.khalil.pixidustwebapi.Entities.FileResponse;
import com.example.khalil.pixidustwebapi.Entities.Interfaces.IJobDetail;
import com.example.khalil.pixidustwebapi.Entities.JobSavingResponse;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;
import com.example.khalil.pixidustwebapi.Utils.ApiConnection;
import com.example.khalil.pixidustwebapi.Utils.WriteFilesToLocalStorage;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Khalil on 1/30/2018.
 */

public class FilesFragment extends Fragment {
    public static RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        recyclerView= (RecyclerView) inflater.inflate(
                R.layout.filesrecyclerview, container, false);
        ArrayList<SpecificJobDetail.JobsFiles> filesList = (ArrayList<SpecificJobDetail.JobsFiles>) getArguments().getSerializable("FilesList");
        FilesFragment.ContentAdapter adapter = new FilesFragment.ContentAdapter(recyclerView.getContext(),filesList);
        recyclerView.setAdapter(adapter);
        // Set padding for Tiles
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView dowloadimageView;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.file_view, parent, false));
            imageView = (ImageView) itemView.findViewById(R.id.imgfile);
            name = (TextView) itemView.findViewById(R.id.filename);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<FilesFragment.ViewHolder> implements IJobDetail {
        // Set numbers of List in RecyclerView.
        ArrayList<SpecificJobDetail.JobsFiles> jobsList;
        Context context;
        public ContentAdapter(Context paramContext, ArrayList<SpecificJobDetail.JobsFiles> paramFilesList) {
            jobsList  = paramFilesList;
            context=paramContext;
        }

        @Override
        public FilesFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilesFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(FilesFragment.ViewHolder holder, int position) {
            final SpecificJobDetail.JobsFiles dataModel =  jobsList.get(position);
            switch(dataModel.fileMymeType) {
                case "text/plain":
                    holder.imageView.setBackgroundResource(R.drawable.textfileicon);
                    break;
                case "application/pdf":
                    holder.imageView.setBackgroundResource(R.drawable.pdficon);
            }
            holder.name.setText(dataModel.FileName);

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            SpecificJobDetail.JobsFiles dataModel2 = jobsList.get(position);
                            if(!CheckFilePath(dataModel2)){
                                JSONObject object = new JSONObject();
                                try {
                                    object.put("ID", dataModel2.ID);
                                    object.put("FK_TemplateID", dataModel2.FK_Template);
                                    object.put("EmployeeID", JobDetail.specificJobDetail.lstJobDetail.get(0).EmployeeID);
                                } catch (Exception ex) {
                                }
                                ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/DownloadFile", object.toString(), recyclerView.getContext(), "FileResponse", null,"");
                                apiConnection.jobDetail = ContentAdapter.this;
                                apiConnection.execute();
                            }
                            else{
                                String url = getUrl(dataModel2);
                                File file = new File(url);
                                Uri uri = Uri.fromFile(file);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                if(url.toString().contains(".pdf")) {
                                    intent.setDataAndType(uri, "application/pdf");
                                } else if(url.toString().contains(".txt")) {
                                    intent.setDataAndType(uri, "text/plain");
                                }
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                context.startActivity(intent);
                            }
                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
        public String getUrl(SpecificJobDetail.JobsFiles jobDetail){
            String filename =WriteFilesToLocalStorage.GetFileName(jobDetail.FileName);
            String foldername = "" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateName + "_" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateID;
            String url = Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles/" + foldername + "/" + filename;
            return url;
        }
        public boolean CheckFilePath(SpecificJobDetail.JobsFiles jobDetail){
            boolean IsFileExist=false;
            String filename =WriteFilesToLocalStorage.GetFileName(jobDetail.FileName);
            String foldername = "" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateName + "_" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateID;
            File file = new File(Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles/" + foldername + "/" + filename);
            if (file.exists()) {
                IsFileExist= true;
            }
            return IsFileExist;
        }
        @Override
        public void GetJobDetail(SpecificJobDetail jobDetail) {

        }

        @Override
        public void GetVideoUrl(String RTSPURL) {

        }

        @Override
        public void GetCheckDropQuestionResponse(JobSavingResponse productsavingres, String Type) {

        }


        @Override
        public void DownloadJobDetail(SpecificJobDetail jobDetail) {

        }

        @Override
        public void GetFileResponse(FileResponse fileResponse) {
                try {
                    byte[] decodedBytes = Base64.decodeBase64(fileResponse.Base64Str.getBytes());
                    WriteFilesToLocalStorage.writeByteArraysToFile(fileResponse.FileName, decodedBytes);
                    Toast.makeText(recyclerView.getContext(), "File Saved", Toast.LENGTH_LONG).show();
                    String filename =WriteFilesToLocalStorage.GetFileName(fileResponse.FileName);
                    String foldername = "" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateName + "_" + JobDetail.specificJobDetail.lstJobDetail.get(0).TemplateID;
                    String url = Environment.getExternalStorageDirectory() + "/PixieDustProjectFiles/" + foldername + "/" + filename;
                    File file = new File(url);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if(url.toString().contains(".pdf")) {
                        intent.setDataAndType(uri, "application/pdf");
                    } else if(url.toString().contains(".txt")) {
                        intent.setDataAndType(uri, "text/plain");
                    }
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        @Override
        public void GetFileResponse(JobSavingResponse fileResponse, int Count) {

        }

        @Override
        public void NoJobAssigned() {

        }


        @Override
        public int getItemCount() {
            return jobsList.size();
        }
    }
}
