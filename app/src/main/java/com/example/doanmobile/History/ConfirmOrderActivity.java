package com.example.doanmobile.History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.R;
import com.example.doanmobile.interfaceData.CartTotalListener;
import com.example.doanmobile.model.ProductCart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConfirmOrderActivity extends AppCompatActivity implements CartTotalListener{
    TextView  txtTotalCart;
    Button btnReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        txtTotalCart = findViewById(R.id.txt_total_cart);
        btnReturnHome = findViewById(R.id.btnReturnHome);

        Integer cartTotal = getIntent().getIntExtra("cartTotal", 0);
        onCartTotalChanged(Integer.valueOf(cartTotal));

        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return Home
                finish();
            }
        });
    }

    public void onCartTotalChanged(int total) {
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        txtTotalCart.setText(myFormatter.format(total));
    }
}