package com.example.khalil.pixidustwebapi.Adapters;

import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.khalil.pixidustwebapi.Entities.FileResponse;
import com.example.khalil.pixidustwebapi.Entities.Interfaces.IJobDetail;
import com.example.khalil.pixidustwebapi.Entities.JobSavingResponse;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;

/**
 * Created by Khalil on 1/30/2018.
 */

public class VideoFragment extends Fragment implements IJobDetail {
    public WebView VideoView;
    View view;
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.video_view, container, false);
        VideoView = (WebView) view.findViewById(R.id.videoView);
        String strVideoUrl = getArguments().getString("VideoUrl");
        VideoView.loadUrl(strVideoUrl);
        VideoView.getSettings().setJavaScriptEnabled(true);
        VideoView.setWebChromeClient(new WebChromeClient());
        VideoView.getSettings().setPluginState(WebSettings.PluginState.ON);
      /*  ApiConnection apiConnection = new ApiConnection("http://www.pixiedustdatasystem.com/MobileWebApi/api/EmployeeJobs/GetJobToSubmit",strVideoUrl,getContext(),"Video",null);
       try {
           apiConnection.jobDetail=this;
           apiConnection.execute();
       }
       catch (Exception ex)
       {
           String str=ex.getMessage();
       }*/
        return view;
    }
    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            // Your code goes here
            return true;
        }
    };
    @Override
    public void GetJobDetail(SpecificJobDetail jobDetail) {
    }
    public void GetVideoUrl(String RTSPURL){
/*        VideoView.setVideoURI(Uri.parse(RTSPURL));
        MediaController mc = new MediaController(view.getContext());
        VideoView.setMediaController(mc);*//**//*
        VideoView.setOnErrorListener(mOnErrorListener);
        VideoView.requestFocus();
        VideoView.start();
        mc.show();*/

    }

    @Override
    public void GetCheckDropQuestionResponse(JobSavingResponse productsavingres, String Type) {

    }

    @Override
    public void DownloadJobDetail(SpecificJobDetail jobDetail) {

    }

    @Override
    public void GetFileResponse(FileResponse fileResponse) {

    }

    @Override
    public void GetFileResponse(JobSavingResponse fileResponse, int Count) {

    }

    @Override
    public void NoJobAssigned() {

    }
}


/*   public static class ViewHolder extends RecyclerView.ViewHolder {
        public VideoView VideoView;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.video_view, parent, false));
            VideoView = (VideoView) itemView.findViewById(R.id.videoView);
        }
    }*/
    /**
     * Adapter to display recycler view.
     */
  /*  public static class ContentAdapter extends RecyclerView.Adapter<VideoFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.
        String strVideoUrl;
        Context context;
        public ContentAdapter(Context paramContext, String VideoUrl) {
            strVideoUrl  = VideoUrl;
            context=paramContext;
        }

        @Override
        public VideoFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VideoFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(VideoFragment.ViewHolder holder, int position) {
            strVideoUrl =  strVideoUrl;
            holder.VideoView.setVideoPath(strVideoUrl);
        }

           @Override
           public int getItemCount() {
           return 0;
       }*/
/*    }
}*/
