package com.example.doanmobile.adapter;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.R;
import com.example.doanmobile.model.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Product[] productList;
    private Context context;

    public ProductListAdapter(Product[] productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = productList[position];
        holder.name.setText(product.getName());
        holder.img.setImageResource(product.getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello product");
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.length;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView img;
        public TextView price;
        public RelativeLayout productList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            img = itemView.findViewById(R.id.imageViewSrc);
            price = itemView.findViewById(R.id.productPrice);
            productList = itemView.findViewById(R.id.productListView);
        }
    }
}
