package com.example.doanmobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.doanmobile.History.ConfirmOrderActivity;
import com.example.doanmobile.LoginActivity;
import com.example.doanmobile.R;
import com.example.doanmobile.adapter.CartListAdapter;
import com.example.doanmobile.interfaceData.CartTotalListener;
import com.example.doanmobile.model.ProductCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class CartFragment extends Fragment implements CartTotalListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CartFragment() {}


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private GridView gridViewCart;
    private TextView txtQuantity,txtTotal;
    private Button btnCheckOut;
    private ArrayList<ProductCart> cartProducts ;
    private  CartTotalListener cartTotalListener;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        gridViewCart = getView().findViewById(R.id.gridViewCart);
        txtQuantity = getView().findViewById(R.id.editTxtQuantity);
        txtTotal = getView().findViewById(R.id.txt_total_cart);


        CartListAdapter adapter = new CartListAdapter(getCartProducts(), getActivity().getApplicationContext());
        adapter.setCartTotalListener(this);
        gridViewCart.setAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        btnCheckOut = view.findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cartTotal = getCartTotal();
                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                intent.putExtra("cartTotal", cartTotal);
                startActivity(intent);

            }
        });
        return view;
    }


    public ArrayList<ProductCart> getCartProducts() {
        // Lấy dữ liệu từ Shared Preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        String cartJson = sharedPreferences.getString("cart", "");


        // Chuyển đổi từ JSON sang danh sách đối tượng ProductCart
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProductCart>>() {
        }.getType();
        cartProducts = gson.fromJson(cartJson, type);


        // Kiểm tra nếu danh sách chưa tồn tại, khởi tạo danh sách mới
        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }


        // Kiểm tra và xóa các sản phẩm đã hết hạn
        if (cartProducts != null && !cartProducts.isEmpty()) {
            long currentTimeMillis = System.currentTimeMillis();
            Iterator<ProductCart> iterator = cartProducts.iterator();
            while (iterator.hasNext()) {
                ProductCart productCart = iterator.next();
                if (productCart.getExpiryTimeMillis() <= currentTimeMillis) {
                    iterator.remove();
                } else if (!productCart.isStatus()) {
                    iterator.remove();
                }
            }


            // Lưu lại danh sách sản phẩm sau khi xóa vào Shared Preferences
            String updatedCartJson = gson.toJson(cartProducts);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cart", updatedCartJson);
            editor.apply();
        }


        return cartProducts;
    }


    @Override
    public void onCartTotalChanged(int total) {
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        txtTotal.setText(myFormatter.format(total));
    }

    public double getCartTotal() {
        double totalPrice = 0;
        for (ProductCart productCart : cartProducts) {
            totalPrice += Double.parseDouble(productCart.getPrice()) * Double.parseDouble(productCart.getQuantity());
        }
        return totalPrice;
    }
}