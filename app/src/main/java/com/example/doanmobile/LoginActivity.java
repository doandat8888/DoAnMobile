package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    Intent intent, forCheckout;
    Button btnLogin;
    EditText username;
    EditText password;
    Context context;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.txtSignup);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin(username.getText().toString(), password.getText().toString());
            }
        });
    }
    //Đăng nhập
    public void handleLogin(String username, String password) {
        if(username != "" && password != "") {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();

            databaseReference.child("Accounts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean success = false;
                    for(DataSnapshot snap : snapshot.getChildren()) {

                        Account accountLogin = snap.getValue(Account.class);
                        if(accountLogin.getUsername().equals(username) && accountLogin.getPassword().equals(password)) {
                            //Đưa thông tin người dùng vào trang chủ
                            intent = new Intent(getApplicationContext(), PageActivity.class);
                            intent.putExtra("name", accountLogin.getName());
                            intent.putExtra("img", accountLogin.getImg());
                            intent.putExtra("phoneNumber", accountLogin.getPhoneNumber());
                            intent.putExtra("key", snap.getKey());

                            sp = getSharedPreferences("UserInfoPref", getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor spedit = sp.edit();
                            spedit.putString("name", accountLogin.getName());
                            spedit.putString("username", accountLogin.getUsername());
                            spedit.putString("phoneNumber", accountLogin.getPhoneNumber());
                            spedit.clear();
                            spedit.apply();

                            startActivity(intent);
                            success = true;
                            break;
                        }
                    }
                    if(success == false) {
                        Toast.makeText(LoginActivity.this, "User does not exist in this system. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            Toast.makeText(LoginActivity.this, "Missing user information. Please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}