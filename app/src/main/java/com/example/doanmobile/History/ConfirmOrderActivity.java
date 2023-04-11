package com.example.doanmobile.History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanmobile.R;

public class ConfirmOrderActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextAddress;
    Button btnConfirm;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextUsername);
        editTextAddress = findViewById(R.id.editTextAddress);

        btnConfirm = findViewById(R.id.btnSignup);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Your Order is compeleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}