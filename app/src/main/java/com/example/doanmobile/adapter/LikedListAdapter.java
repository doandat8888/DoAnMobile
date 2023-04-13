package com.example.doanmobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.R;
import com.example.doanmobile.fragment.LikeFragment;
import com.example.doanmobile.interfaceData.LikedListener;
import com.example.doanmobile.model.ProductLiked;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LikedListAdapter extends BaseAdapter {
    private static ArrayList<ProductLiked> productLikedList;
    private Context context;
    private LikedListener likedListener;


    public LikedListAdapter(ArrayList<ProductLiked> productLikedList, Context context) {
        this.productLikedList = productLikedList;
        this.context = context;
    }
    public void setLikedTotalListener(LikeFragment likeFragment) {
        this.likedListener = likedListener;
    }
    @Override
    public int getCount() {
        return productLikedList.size();
    }


    @Override
    public Object getItem(int position) {
        return productLikedList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataItem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            dataItem = new MyView();
            convertView = inflater.inflate(R.layout.liked_product, null);
            dataItem.imgItemCart = convertView.findViewById(R.id.imgItemCart);
            dataItem.txtLikedItemName = convertView.findViewById(R.id.txtLikedItemName);
            dataItem.txtLikedItemPrice = convertView.findViewById(R.id.txtLikedItemPrice);
            dataItem.btnLiked = convertView.findViewById(R.id.btnLiked);
            convertView.setTag(dataItem);
        } else {
            dataItem = (MyView) convertView.getTag();
        }


        DecimalFormat myFormatter = new DecimalFormat("###,###");
        Picasso.get().load(productLikedList.get(position).getImage()).resize(256, 256).centerCrop().into(dataItem.imgItemCart);
        dataItem.txtLikedItemName.setText(productLikedList.get(position).getName());
        dataItem.txtLikedItemPrice.setText(productLikedList.get(position).getPrice());



        //Xóa
        dataItem.btnLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeLikedProduct(position)) {
                    Toast.makeText(context, "Remove successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public String myFormat(int number, int mode) {
        DecimalFormat myFormatter = new DecimalFormat("###,###");

        if (mode == 1)
            return String.valueOf(number);
        if (mode == 2)
            return myFormatter.format(number);
        return "";
    }


    private static class MyView {
        public TextView txtLikedItemName, txtLikedItemPrice;
        public ImageView imgItemCart;
        public ImageButton btnLiked;
    }

    public boolean removeLikedProduct(int position) {
        if (productLikedList != null && position >= 0 && position < productLikedList.size()) {
            productLikedList.remove(position);

            // Save the updated cartProducts list back to shared preferences
            updateLikedData();
            return true;
        } else {
            return false;
        }
    }

    private void updateLikedData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LikedPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String updatedLikedJson = gson.toJson(productLikedList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("liked", updatedLikedJson);
        editor.apply();
        notifyDataSetChanged();
    }
}
