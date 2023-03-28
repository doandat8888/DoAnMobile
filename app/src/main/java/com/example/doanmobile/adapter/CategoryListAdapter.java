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

    ArrayList<Product> productList = new ArrayList<>();



    private ArrayList<Category> categoryList;
    private Context context;

    private RecyclerView productViewer;
    private View view;

    public CategoryListAdapter(View view, RecyclerView productViewer, ArrayList<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        this.productViewer = productViewer;
        this.view = view;
    }


    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.category_list, parent, false);
        CategoryListAdapter.ViewHolder viewHolder = new CategoryListAdapter.ViewHolder(listItem);
        viewHolder = new ViewHolder(listItem);
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
                HomeFragment homeFragment = new HomeFragment();
                System.out.println("Context category list: " + context);
                //homeFragment.getProductList(view, productViewer, productList, category.getName());
                homeFragment.getProductByCategory(view, productViewer, productList, category.getName(), context);
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryTxt);
            categoryListView = itemView.findViewById(R.id.categoryListView);
            img = itemView.findViewById(R.id.categoryImg);

        }
    }
}
