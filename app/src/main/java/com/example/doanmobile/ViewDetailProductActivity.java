package com.example.doanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewDetailProductActivity extends AppCompatActivity {
    ImageView productImg;
    TextView productName, productPrice, productDescription, total, quantity;
    Button btnRaise, btnDecrease;
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
        productName.setText(intent.getStringExtra("name"));
        Picasso.get().load(intent.getStringExtra("imageSrc")).resize(300, 300).centerCrop().into(productImg);
        productDescription.setText(intent.getStringExtra("description"));
        productPrice.setText("$ " + intent.getIntExtra("price", 0));

        btnRaise = findViewById(R.id.btnRaise);
        btnDecrease = findViewById(R.id.btnDecrease);
        quantity = findViewById(R.id.txtQuantity);

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
}