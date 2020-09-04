package com.example.khalil.pixidustwebapi.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalil.pixidustwebapi.Entities.ProjectInformation;
import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;

import java.util.ArrayList;

/**
 * Created by Khalil on 1/26/2018.
 */

public class ProductsAdapter extends ArrayAdapter<SpecificJobDetail.JobsProducts> {
    ArrayList<SpecificJobDetail.JobsProducts> productsList;
    Context context;
    public static LayoutInflater inflator;
    public class ViewHolder{
        TextView StockLevel;
        TextView ProductName;
    }
    public ProductsAdapter(Context JobDetailActivity, ArrayList<SpecificJobDetail.JobsProducts> productlst) {

        // TODO Auto-generated constructor stub
        super(JobDetailActivity, R.layout.products_list, productlst);
        productsList=productlst;
        context=JobDetailActivity;
        inflator = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        SpecificJobDetail.JobsProducts dataModel = getItem(position);
        viewHolder = new ViewHolder();
        convertView = inflator.inflate(R.layout.products_list, null);
                /*Set IDS of textview and imageview to ViewHolderObject*/
        viewHolder.ProductName = (TextView) convertView.findViewById(R.id.productDescription);
        viewHolder.StockLevel = (TextView) convertView.findViewById(R.id.stockValue);
        result=convertView;
        convertView.setTag(viewHolder);
         /*Set txt and image to controls*/
        viewHolder.ProductName.setText(dataModel.ProductName);
        viewHolder.StockLevel.setText(""+dataModel.StockLevel+"");
        return convertView;
        // Return the completed view to render on screen
    }
}
