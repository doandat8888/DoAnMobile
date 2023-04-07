package com.example.doanmobile.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.Interface.ItemClickListener;
import com.example.doanmobile.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productPrice, productQty;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.iv_cartImg);
        productName = itemView.findViewById(R.id.txt_cartName);
        productPrice = itemView.findViewById(R.id.txt_cartPrice);
        productQty = itemView.findViewById((R.id.txt_cartQuantity));
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
    public void setItemClickListener(com.denzcoskun.imageslider.interfaces.ItemClickListener itemClickListener){
        this.itemClickListener = (ItemClickListener) itemClickListener;
    }
}
