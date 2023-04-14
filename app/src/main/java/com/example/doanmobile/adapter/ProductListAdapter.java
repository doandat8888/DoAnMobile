package com.example.doanmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.text.Layout;
import android.text.TextUtils;
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
import com.example.doanmobile.ViewDetailProductActivity;
import com.example.doanmobile.fragment.CartFragment;
import com.example.doanmobile.model.Product;
import com.example.doanmobile.model.ProductCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private ArrayList<Product> productList;
    private Context context;


    public ProductListAdapter(ArrayList<Product> productList, Context context) {
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
        final Product product = productList.get(position);
        holder.name.setText(product.getName());
        Picasso.get().load(product.getImg()).resize(300, 300).centerCrop().into(holder.img);
        holder.price.setText("$" + product.getPrice());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tạo đối tượng productCart
                ProductCart productCart = new ProductCart(String.valueOf(product.getId()), product.getName(), String.valueOf(product.getPrice()), "1", product.getImg(), System.currentTimeMillis() + (15 * 60 * 1000), true);
                //Lưu đối tượng productCart vào SharedPreferences
                addToCart(productCart);
                Toast.makeText(context, "Add to cart success", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetailProductActivity.class);
                intent.putExtra("id",String.valueOf(product.getId()));
                intent.putExtra("imageSrc", product.getImg());
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView img, btnAdd;
        public TextView price;
        public RelativeLayout productList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            img = itemView.findViewById(R.id.imageViewSrc);
            price = itemView.findViewById(R.id.productPrice);
            productList = itemView.findViewById(R.id.productListView);
            btnAdd = itemView.findViewById(R.id.addBtn);
        }
    }


    //CART - thêm sản phẩm vào giỏ hàng
    private void addToCart(ProductCart product) {
        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        // Lấy giá trị dạng JSON từ SharedPreferences
        String cartJson = sharedPreferences.getString("cart", "");
        List<ProductCart> cartList = new ArrayList<>();

        // Chuyển đổi dữ liệu JSON thành danh sách sản phẩm
        if (!TextUtils.isEmpty(cartJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductCart>>() {}.getType();
            cartList = gson.fromJson(cartJson, type);
        }

        boolean isProductExistInCart = false;
        for (ProductCart item : cartList) {
            if (item.getId().equals(product.getId())) {
                int quantity = Integer.parseInt(item.getQuantity()) + Integer.parseInt(product.getQuantity());
                item.setQuantity(String.valueOf(quantity));
                isProductExistInCart = true;
                break;
            }
        }

        // Thêm sản phẩm mới vào danh sách
        if (!isProductExistInCart) {
            cartList.add(product);
        }

        // Chuyển danh sách sản phẩm thành chuỗi JSON
        Gson gson = new Gson();
        String newCartJson = gson.toJson(cartList);

        // Lưu danh sách sản phẩm mới vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart", newCartJson);
        editor.apply();
    }
}
