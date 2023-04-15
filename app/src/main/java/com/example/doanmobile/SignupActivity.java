package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.adapter.ProductListAdapter;
import com.example.doanmobile.model.Account;
import com.example.doanmobile.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;

public class SignupActivity extends AppCompatActivity {
    TextView signIn;
    Intent intent;
    EditText fullName, username, password, avatarImg, phoneNumber;
    Button signUp;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullName = findViewById(R.id.editTextName);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        avatarImg = findViewById(R.id.editTextImg);
        phoneNumber = findViewById(R.id.editTextPhoneNumber);
        signUp = findViewById(R.id.btnSignup);

        signIn = findViewById(R.id.txtSignin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup(fullName.getText().toString(), username.getText().toString(), password.getText().toString(), avatarImg.getText().toString(), phoneNumber.getText().toString());
            }
        });
    }

    public void handleSignup(String fullName, String username, String password, String avartarImg, String phoneNumber) {
        if(!fullName.isEmpty() && !username.isEmpty() && !password.isEmpty() && !avartarImg.isEmpty() && !phoneNumber.isEmpty() ) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            int id = 1;
            Account account = new Account(fullName, password, phoneNumber, username, avartarImg);
            final int[] count = {0};
            databaseReference.child("Accounts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()) {
                        Account user = snap.getValue(Account.class);
                        if(user.getUsername().equals(username)) {
                            count[0]++;
                        }
                    }
                    if(count[0] == 0) {
                        //databaseReference.child("Accounts").removeValue();
                        databaseReference.child("Accounts").push().setValue(account)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignupActivity.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignupActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }else {
                        Toast.makeText(SignupActivity.this, "User has existed in the system. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //System.out.println("Check account: " + checkAccount(username));
            //checkAccount(username);
        }else {
            Toast.makeText(SignupActivity.this, "Missing user info", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAccount(String username) {
        final int[] count = {0};
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Account user = snap.getValue(Account.class);
                    if(user.getUsername().equals(username)) {
                        count[0]++;
                    }
                }
                System.out.println(count[0]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}