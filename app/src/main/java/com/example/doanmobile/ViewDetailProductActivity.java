package com.example.doanmobile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanmobile.model.ProductCart;
import com.example.doanmobile.model.ProductLiked;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewDetailProductActivity extends AppCompatActivity {
    ImageView productImg;
    TextView productName, productPrice, productDescription, total, quantity;
    Button btnRaise, btnDecrease, btnAddToCart;
    String productId;
    String imgUrl;
    String pname;
    Integer pprice;
    ImageView heart_icon_inactive, heart_icon_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_product);

        //Lấy thông tin chi tiết sản phẩm
        productImg = findViewById(R.id.detailProductImg);
        productDescription = findViewById(R.id.txtProductDescription);
        productName = findViewById(R.id.txtProductName);
        productPrice = findViewById(R.id.txtProductPrice);
        total = findViewById(R.id.txtTotal);

        Intent intent = getIntent();
        pname = intent.getStringExtra("name");
        productName.setText(pname);
        imgUrl = intent.getStringExtra("imageSrc");
        Picasso.get().load(imgUrl).resize(300, 300).centerCrop().into(productImg);
        productDescription.setText(intent.getStringExtra("description"));
        pprice = intent.getIntExtra("price", 0);
        productPrice.setText("$ " + pprice);
        productId = getIntent().getStringExtra("id");

        btnRaise = findViewById(R.id.btnRaise);
        btnDecrease = findViewById(R.id.btnDecrease);
        quantity = findViewById(R.id.txtQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        //CART
        addToCartView();

        //Hiển thị tổng tiền
        double price = intent.getIntExtra("price", 0);
        int quantityProduct = Integer.parseInt(quantity.getText().toString());
        double totalMoney = price * quantityProduct;
        total.setText("$ " + totalMoney);

        //Tăng, giảm sản phẩm
        btnRaise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productQuantity = Integer.parseInt(quantity.getText().toString());
                productQuantity = productQuantity + 1;
                quantity.setText(""+productQuantity);
                double price = intent.getIntExtra("price", 0);
                calcTotalAmount(price, productQuantity);
            }
        });

        //Heart icon
        heart_icon_inactive = findViewById(R.id.heart_icon_inactive);
        heart_icon_active = findViewById(R.id.heart_icon_active);

        heart_icon_active.setVisibility(View.INVISIBLE);

        heart_icon_active.setOnClickListener(v -> {
            heart_icon_active.setVisibility(View.INVISIBLE);
            heart_icon_inactive.setVisibility(View.VISIBLE);
        });

        heart_icon_inactive.setOnClickListener(v -> {
            heart_icon_inactive.setVisibility(View.INVISIBLE);
            heart_icon_active.setVisibility(View.VISIBLE);

            ProductLiked liked = new ProductLiked(productId, pname, String.valueOf(pprice), imgUrl, true);
            addToLikedView(liked);
        });

        //Check xem sản phẩm có được like không
        List<String> likedID = getCurrentLikedProductsID();

        if (likedID.contains(productId)){
            heart_icon_active.setVisibility(View.VISIBLE);
            heart_icon_inactive.setVisibility(View.INVISIBLE);
        }

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(quantity.getText().toString()) > 1) {
                    int productQuantity = Integer.parseInt(quantity.getText().toString());
                    productQuantity = productQuantity - 1;
                    quantity.setText(""+productQuantity);
                    double price = intent.getIntExtra("price", 0);
                    calcTotalAmount(price, productQuantity);
                }
            }
        });
    }

    //Tính tổng tiền
    public void calcTotalAmount(double price, int quantity) {
        double totalMoney = price * quantity;
        total.setText("$ " + totalMoney);
    }

    //CART
    private void addToCartView() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thời gian hiện tại
                long currentTimeMillis = System.currentTimeMillis();
                // Tính thời gian hết hạn (15 phút sau thời điểm hiện tại)
                long expiryTimeMillis = currentTimeMillis + (15 * 60 * 1000);
                ProductCart productCart = new ProductCart(productId,pname,String.valueOf(pprice),
                        quantity.getText().toString(),imgUrl,expiryTimeMillis,true);
                if (productCart != null) {
                    addToCart(productCart);
                    Toast.makeText(ViewDetailProductActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewDetailProductActivity.this, "Add to cart failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };

    public void addToCart(ProductCart product) {
        // Lấy danh sách sản phẩm trong giỏ hàng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        // Lấy giá trị dạng JSON từ SharedPreferences
        String cartJson = sharedPreferences.getString("cart", "");
        List<ProductCart> cartList = new ArrayList<>();


        // Chuyển đổi dữ liệu JSON thành danh sách sản phẩm
        if (!TextUtils.isEmpty(cartJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductCart>>() {
            }.getType();
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


        if (!isProductExistInCart) {
            // Thêm sản phẩm mới vào danh sách
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

    private List<String> getCurrentLikedProductsID(){
        SharedPreferences sharedPreferences = getSharedPreferences("LikedPrefs", Context.MODE_PRIVATE);
        // Lấy giá trị dạng JSON từ SharedPreferences
        String likedJson = sharedPreferences.getString("liked", "");
        List<ProductLiked> likedList = new ArrayList<>();
        List<String> likedID = new ArrayList<>();

        // Chuyển đổi dữ liệu JSON thành danh sách sản phẩm
        if (!TextUtils.isEmpty(likedJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductLiked>>() {}.getType();
            likedList = gson.fromJson(likedJson, type);
        }

        for (int i = 0; i < likedList.size(); i++){
            String id = likedList.get(i).getId();
            likedID.add(id);
        }

        return likedID;
    }

    //Liked Fragment
    public void addToLikedView(ProductLiked product) {
        // Lấy danh sách favorite từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LikedPrefs", Context.MODE_PRIVATE);
        // Lấy giá trị dạng JSON từ SharedPreferences
        String likedJson = sharedPreferences.getString("liked", "");
        List<ProductLiked> likedList = new ArrayList<>();

        // Chuyển đổi dữ liệu JSON thành danh sách sản phẩm
        if (!TextUtils.isEmpty(likedJson)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductLiked>>() {}.getType();
            likedList = gson.fromJson(likedJson, type);
        }

        // Thêm sản phẩm mới vào danh sách
        likedList.add(product);

        // Chuyển danh sách sản phẩm thành chuỗi JSON
        Gson gson = new Gson();
        String newCartJson = gson.toJson(likedList);

        // Lưu danh sách sản phẩm mới vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("liked", newCartJson);
        editor.apply();
    }
}