
package com.example.khalil.pixidustwebapi.Adapters;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.widget.ArrayAdapter;

import com.example.khalil.pixidustwebapi.Entities.ProjectInformation;
import com.example.khalil.pixidustwebapi.R;

/*
 Created by Khalil on 1/19/2018.
*/
public class CustomAdapter extends ArrayAdapter<ProjectInformation> {
    ArrayList<ProjectInformation> dataSet;
    Context context;
    private static LayoutInflater inflater=null;
    // View lookup cache
    public class ViewHolder{
        TextView JobID;
        TextView ClientName;
        TextView StoreName;
        TextView LocationName;
        TextView datetext;
        TextView projectName;
        TextView txtviewtime;
    }
    public CustomAdapter(Context mainActivity, ArrayList<ProjectInformation> prgmNameList) {

        // TODO Auto-generated constructor stub
        super(mainActivity, R.layout.project_list, prgmNameList);
        dataSet=prgmNameList;

        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        ProjectInformation dataModel = getItem(position);
        viewHolder = new ViewHolder();
        convertView = inflater.inflate(R.layout.project_list, null);
        /*Set IDS of textview and imageview to ViewHolderObject*/
        viewHolder.projectName = (TextView) convertView.findViewById(R.id.projectName);
        viewHolder.txtviewtime = (TextView) convertView.findViewById(R.id.txtviewtime);
        viewHolder.datetext = (TextView) convertView.findViewById(R.id.datetext);
        viewHolder.ClientName = (TextView) convertView.findViewById(R.id.clientName);
        viewHolder.StoreName = (TextView) convertView.findViewById(R.id.storename);
        viewHolder.LocationName = (TextView) convertView.findViewById(R.id.LocationName);

        result=convertView;
        convertView.setTag(viewHolder);
        /*Set txt and image to controls*/
        viewHolder.ClientName.setText(dataModel.ClientName);
        viewHolder.StoreName.setText(dataModel.StoreName);
        viewHolder.LocationName.setText(dataModel.LocationName);
        viewHolder.projectName.setText(dataModel.TemplateName);
        viewHolder.datetext.setText(dataModel.strJobDate);
        viewHolder.txtviewtime.setText(dataModel.strJobTime);
        // Return the completed view to render on screen
        return convertView;
    }
}


