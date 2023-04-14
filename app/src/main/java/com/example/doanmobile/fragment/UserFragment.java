package com.example.doanmobile.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doanmobile.LoginActivity;
import com.example.doanmobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    EditText userFullName, userPhoneNumber;
    ImageView imgUser;
    Button btnUpdate;
    String key;


    public UserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String fullname) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString("name", fullname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userFullName = view.findViewById(R.id.editTextFullname);
        userPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        imgUser = view.findViewById(R.id.userImg);
        btnUpdate = view.findViewById(R.id.btnUpdateInfo);

        //Lấy thông tin người dùng
        Bundle bundle = getArguments();
        if(bundle != null) {
            String userFullname = bundle.getString("name");
            userFullName.setText(userFullname);
            String phoneNumber = bundle.getString("phoneNumber");
            userPhoneNumber.setText(phoneNumber);
            String img = bundle.getString("img");
            Picasso.get().load(img).resize(300, 300).centerCrop().into(imgUser);
            key = bundle.getString("key");
            System.out.println("Key user: " + key);
        }

        //Cập nhật thông tin người dùng
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfoUser(userFullName.getText().toString(), userPhoneNumber.getText().toString());
            }
        });
        return view;
    }

    public void updateInfoUser(String fullName, String phoneNumber) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Accounts").child(key).child("name").setValue(fullName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Update user info successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Co loi xay ra");
            }
        });
        databaseReference.child("Accounts").child(key).child("phoneNumber").setValue(phoneNumber).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("Cap nhat thong tin thanh cong");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Co loi xay ra");
            }
        });;
    }
}