package com.example.doanmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.PageActivity;
import com.example.doanmobile.R;
import com.example.doanmobile.ViewDetailProductActivity;
import com.example.doanmobile.fragment.HomeFragment;
import com.example.doanmobile.model.Category;
import com.example.doanmobile.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private ArrayList<Category> categoryList;
    private Context context;

    public CategoryListAdapter(ArrayList<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.category_list, parent, false);
        CategoryListAdapter.ViewHolder viewHolder = new CategoryListAdapter.ViewHolder(listItem, mListener);
        viewHolder = new ViewHolder(listItem, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.name.setText(category.getName());
        Picasso.get().load(category.getImg()).resize(300, 300).centerCrop().into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("mListener: " + mListener);
                if(mListener != null) {
                    mListener.onItemClick(category.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CardView categoryListView;
        public ImageView img;

        private OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            name = itemView.findViewById(R.id.categoryTxt);
            categoryListView = itemView.findViewById(R.id.categoryListView);
            img = itemView.findViewById(R.id.categoryImg);

        }
    }
}
