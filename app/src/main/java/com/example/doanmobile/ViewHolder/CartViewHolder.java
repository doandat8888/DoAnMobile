package com.example.doanmobile.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.example.doanmobile.Interface.ItemClickListner;
import com.example.doanmobile.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productPrice, productQty;
    public ImageView imageView;
    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.iv_cartImg);
        productName = itemView.findViewById(R.id.txt_cartName);
        productPrice = itemView.findViewById(R.id.txt_cartPrice);
        productQty = itemView.findViewById((R.id.txt_cartQuantity));
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListner = (ItemClickListner) itemClickListener;
    }
}
