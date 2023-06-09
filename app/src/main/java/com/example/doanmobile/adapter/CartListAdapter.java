package com.example.doanmobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.R;
import com.example.doanmobile.interfaceData.CartTotalListener;
import com.example.doanmobile.model.ProductCart;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {
    private static ArrayList<ProductCart> productCartList;
    private Context context;
    private CartTotalListener cartTotalListener;


    public CartListAdapter() {}

    public CartListAdapter(ArrayList<ProductCart> productCartList, Context context) {
        this.productCartList = productCartList;
        this.context = context;
    }
    public void setCartTotalListener(CartTotalListener listener) {
        this.cartTotalListener = listener;
    }
    @Override
    public int getCount() {
        return productCartList.size();
    }


    @Override
    public Object getItem(int position) {
        return productCartList.get(position);
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
            convertView = inflater.inflate(R.layout.item_cart, null);
            dataItem.imgItemCart = convertView.findViewById(R.id.imgItemCart);
            dataItem.txtCartItemName = convertView.findViewById(R.id.txtCartItemName);
            dataItem.txtCartItemPrice = convertView.findViewById(R.id.txtCartItemPrice);
            dataItem.editTxtQuantity = convertView.findViewById(R.id.editTxtQuantity);
            dataItem.btnMinus = convertView.findViewById(R.id.btnMinus);
            dataItem.btnPlus = convertView.findViewById(R.id.btnPlus);
            dataItem.btnDelItem = convertView.findViewById(R.id.btnDelItem);
            convertView.setTag(dataItem);
        } else {
            dataItem = (MyView) convertView.getTag();
        }


        DecimalFormat myFormatter = new DecimalFormat("###,###");
        int price = Integer.parseInt(productCartList.get(position).getPrice());
        int quantity = Integer.parseInt(productCartList.get(position).getQuantity());
        total(productCartList);
        Picasso.get().load(productCartList.get(position).getImage()).resize(256, 256).centerCrop().into(dataItem.imgItemCart);
        dataItem.txtCartItemName.setText(productCartList.get(position).getName());
        dataItem.txtCartItemPrice.setText(myFormatter.format(price));
        dataItem.editTxtQuantity.setText(myFormatter.format(quantity));



        //Xóa sản phẩm khỏi giỏ hàng
        dataItem.btnDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeProductCart(position)) {
                    Toast.makeText(context, "Remove successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
                // Cập nhật lại tổng tiền
                total(productCartList);
            }
        });

        //Tăng, giảm số lượng
        dataItem.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(dataItem.editTxtQuantity.getText().toString());
                int currentPirce = price;
                int newQuantity = currentQuantity + 1;
                dataItem.editTxtQuantity.setText(myFormatter.format(newQuantity));
                productCartList.get(position).setQuantity(String.valueOf(newQuantity));
                total(productCartList);
                updateCartData();
            }
        });

        dataItem.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(dataItem.editTxtQuantity.getText().toString());
                int currentPirce = price;
                int newQuantity = currentQuantity - 1;
                dataItem.editTxtQuantity.setText(myFormatter.format(newQuantity));
                productCartList.get(position).setQuantity(String.valueOf(newQuantity));

                if(newQuantity == 0){
                    removeProductCart(position);
                }
                total(productCartList);
                updateCartData();
            }
        });

        return convertView;
    }

    //Tính tổng tiền của giỏ hàng
    public int total(ArrayList<ProductCart> productCartList ) {
        int total = 0;
        for (int i = 0;i<productCartList.size();i++) {
            total += Integer.parseInt(productCartList.get(i).getPrice()) * Integer.parseInt(productCartList.get(i).getQuantity());
        }
        cartTotalListener.onCartTotalChanged(total);
        return total;
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
        public TextView txtCartItemName, txtCartItemPrice;
        public ImageView imgItemCart;
        public Button btnMinus, btnPlus;
        public ImageButton btnDelItem;
        public EditText editTxtQuantity;
    }

    //Xóa sản phẩm khỏi giỏ hàng
    public boolean removeProductCart(int position) {
        if (productCartList != null && position >= 0 && position < productCartList.size()) {
            productCartList.remove(position);
            updateCartData();
            return true;
        } else {
            return false;
        }
    }

    //Cập nhật giỏ hàng
    public void updateCartData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String updatedCartJson = gson.toJson(productCartList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart", updatedCartJson);
        editor.apply();
        notifyDataSetChanged();
    }

    //Rỗng giỏ hàng
    public void clearCart() {
        productCartList.clear();
    }
}
