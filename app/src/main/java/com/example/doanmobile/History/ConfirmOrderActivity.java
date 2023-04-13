package com.example.doanmobile.History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanmobile.R;
import com.example.doanmobile.fragment.HomeFragment;


public class ConfirmOrderActivity extends AppCompatActivity{
    TextView  txtTotalCart;
    Button btnReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        btnReturnHome = (Button) findViewById(R.id.btnReturnHome);
        txtTotalCart = findViewById(R.id.txt_total_cart);

        Intent intent = getIntent();
        String total = intent.getStringExtra("total");
        txtTotalCart.setText(total);
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });
    }
}