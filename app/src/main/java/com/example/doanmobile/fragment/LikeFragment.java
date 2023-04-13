package com.example.doanmobile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.doanmobile.R;
import com.example.doanmobile.adapter.LikedListAdapter;
import com.example.doanmobile.interfaceData.CartTotalListener;
import com.example.doanmobile.model.ProductLiked;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class LikeFragment extends Fragment {


    private GridView gridViewLikedList;
    private Button btnLiked;
    private ArrayList<ProductLiked> likedProducts ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        gridViewLikedList = getView().findViewById(R.id.gridViewLikedList);
        btnLiked = getView().findViewById(R.id.addBtn);


        LikedListAdapter adapter = new LikedListAdapter(getlikedProducts(), getActivity().getApplicationContext());
        adapter.setLikedTotalListener(this);
        gridViewLikedList.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like, container, false);
//        btnLiked = view.findViewById(R.id.btnLiked);
//        btnLiked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Check out activity
//                Toast.makeText(getContext(), "for checkout!", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

    public ArrayList<ProductLiked> getlikedProducts() {
        // Lấy dữ liệu từ Shared Preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LikedPrefs", Context.MODE_PRIVATE);
        String likedJson = sharedPreferences.getString("liked", "");


        // Chuyển đổi từ JSON sang danh sách đối tượng ProductCart
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProductLiked>>() {
        }.getType();
        likedProducts = gson.fromJson(likedJson, type);


        // Kiểm tra nếu danh sách chưa tồn tại, khởi tạo danh sách mới
        if (likedProducts == null) {
            likedProducts = new ArrayList<>();

            // Lưu lại danh sách sản phẩm sau khi xóa vào Shared Preferences
            String updatedLikedJson = gson.toJson(likedProducts);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("liked", updatedLikedJson);
            editor.apply();
        }

        return likedProducts;
    }
}