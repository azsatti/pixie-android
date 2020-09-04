package com.example.khalil.pixidustwebapi.Adapters;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalil.pixidustwebapi.Entities.SpecificJobDetail;
import com.example.khalil.pixidustwebapi.R;

import java.util.ArrayList;

/**
 * Created by Khalil on 1/29/2018.
 */

public class ProductFragment extends Fragment {
    public static String[] ProductsData;
    public static ArrayList<SpecificJobDetail.JobsProducts> productsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        productsList = (ArrayList<SpecificJobDetail.JobsProducts>) getArguments().getSerializable("ProductsList");
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext(),productsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(productsList.size());
        ProductsData=new String[productsList.size()];
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stock;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.products_list, parent, false));
            stock = (TextView) itemView.findViewById(R.id.stockValue);
            name = (TextView) itemView.findViewById(R.id.productDescription);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<SpecificJobDetail.JobsProducts> productsList;
        Context context;
        public ContentAdapter(Context paramContext, ArrayList<SpecificJobDetail.JobsProducts> paramproductslist) {
            productsList  = paramproductslist;
            context=paramContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            SpecificJobDetail.JobsProducts dataModel =  productsList.get(position);
            holder.stock.setText("");
            holder.name.setText(dataModel.ProductName);
            holder.stock.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    //setting data to array, when changed
                    // this is a semplified example in the actual app i save the text
                    // in  a .txt in the external storage

                    ProductsData[position] = s.toString();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    Integer value=after;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Editable value=s;

                }
            });
        }
        @Override
        public int getItemCount() {
            return productsList.size();
        }
    }
}
