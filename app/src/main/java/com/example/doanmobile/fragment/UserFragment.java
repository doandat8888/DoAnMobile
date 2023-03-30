package com.example.doanmobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.doanmobile.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    EditText userFullName, userPhoneNumber;
    ImageView imgUser;

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

        //Lấy thông tin người dùng và loại sản phẩm
        Bundle bundle = getArguments();
        if(bundle != null) {
            String userFullname = bundle.getString("name");
            userFullName.setText(userFullname);
            String phoneNumber = bundle.getString("phoneNumber");
            userPhoneNumber.setText(phoneNumber);
            String img = bundle.getString("img");
            Picasso.get().load(img).resize(300, 300).centerCrop().into(imgUser);

        }
        return view;
    }
}