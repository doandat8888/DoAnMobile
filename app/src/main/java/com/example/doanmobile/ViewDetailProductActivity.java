package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ViewDetailProductActivity extends AppCompatActivity {
    ImageView productImg;
    TextView productName, productPrice, productDescription, total, quantity;
    Button btnRaise, btnDecrease, btnAddToCart;
    String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_product);

        //Lấy thông tin chi tiết sản phẩm
        productId = getIntent().getStringExtra("productId");

        productImg = findViewById(R.id.detailProductImg);
        productDescription = findViewById(R.id.txtProductDescription);
        productName = findViewById(R.id.txtProductName);
        productPrice = findViewById(R.id.txtProductPrice);
        total = findViewById(R.id.txtTotal);
        Intent intent = getIntent();
        productName.setText(intent.getStringExtra("name"));
        Picasso.get().load(intent.getStringExtra("imageSrc")).resize(300, 300).centerCrop().into(productImg);
        productDescription.setText(intent.getStringExtra("description"));
        productPrice.setText("$ " + intent.getIntExtra("price", 0));

        btnRaise = findViewById(R.id.btnRaise);
        btnDecrease = findViewById(R.id.btnDecrease);
        quantity = findViewById(R.id.txtQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        //CART
        btnAddToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addingToCartList();
            }
        });

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
    private void addingToCartList() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productId", productId);
        cartMap.put("productName", productName.getText().toString());
        cartMap.put("productPrice", productPrice.getText().toString());
        cartMap.put("productQuantity",quantity.getText().toString());
        cartMap.put("productDescription", productDescription.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);

        cartListRef.child("Products").child(productId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ViewDetailProductActivity.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ViewDetailProductActivity.this, PageActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}