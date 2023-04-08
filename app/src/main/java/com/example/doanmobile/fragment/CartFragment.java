package com.example.doanmobile.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doanmobile.R;
import com.example.doanmobile.adapter.CartAdapter;
import com.example.doanmobile.model.Cart;
import com.example.doanmobile.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    public static ListView listCart;
    static TextView txtCartSubTotal, txtCartTotal;
    public static TextView txtNoted;
    Button btnCharge, btnContinue;
    CartAdapter cartAdapter;
    ImageButton btnDelItem;
    public static ImageView imgNoted;
    public static long total = 0;
    Context context;

    public static ArrayList<Cart> arrayCart = new ArrayList<Cart>();

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String fullName, Context context) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString("name", fullName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listCart = view.findViewById(R.id.listCart);
        txtCartSubTotal = view.findViewById(R.id.txtCartSubTotal);
        txtNoted = view.findViewById(R.id.txtNoted);
        txtCartTotal = view.findViewById(R.id.txtCartTotal);
        btnCharge = view.findViewById(R.id.btnCharge);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnDelItem = view.findViewById(R.id.btnDelItem);
        imgNoted = view.findViewById(R.id.imgNoted);

        //User - online

        //Keep buying
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeAct = new Intent(context, HomeFragment.class);
                startActivity(homeAct);
            }
        });

        //Checkout
        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayCart.isEmpty()) {
                    Toast.makeText(context.getApplicationContext(), "Giỏ hàng của bạn trống!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận đặt hàng");
                    builder.setMessage("Bạn có chắc muốn đặt đơn hàng này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            //Order History
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });

        cartAdapter = new CartAdapter(context, arrayCart);
        listCart.setAdapter(cartAdapter);
        checkEmptyCart();
        totalCart();

        return view;
    }

    //Tính tổng giá trị giỏ hàng
    public static void totalCart() {
        total = 0;
        for (int i = 0; i < arrayCart.size(); i++) {
            total += Long.parseLong(arrayCart.get(i).getPrice());
        }
        String totalCart = total + " đ";
        txtCartSubTotal.setText(totalCart);
        txtCartTotal.setText((total + 0) + " đ");
    }
    private void checkEmptyCart() {
        if (arrayCart.isEmpty()) {
            cartAdapter.notifyDataSetChanged();
            txtNoted.setVisibility(View.VISIBLE);
            imgNoted.setVisibility(View.VISIBLE);
            listCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txtNoted.setVisibility(View.INVISIBLE);
            imgNoted.setVisibility(View.INVISIBLE);
            listCart.setVisibility(View.VISIBLE);
        }
    }

    //Add to cart
    public static void AddToCart(String receiveIdPro, int quantity) {
        //Tìm id của sp => truy vấn rồi add vào giỏ hàng
        if (arrayCart.size() > 0) { //Nếu chưa tồn tại item thì thêm vào giỏ, ngược lại thì chỉ thêm số lượng
            boolean flag = false; //Biến KT item tồn tại
            for (int i = 0; i < arrayCart.size(); i++) {
                if (arrayCart.get(i).getId().equals(receiveIdPro)) {
                    int oldQuantity = arrayCart.get(i).getQuantity();
                    String oldPrice = arrayCart.get(i).getPrice();
                    arrayCart.get(i).setQuantity(arrayCart.get(i).getQuantity() + quantity);
                    arrayCart.get(i).setPrice(((arrayCart.get(i).getQuantity()
                            * Long.parseLong(oldPrice)) / oldQuantity) + "");
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dRef = fDatabase.getReference("Products");
                Query query = dRef.orderByChild("proId").equalTo(receiveIdPro);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot listPro : snapshot.getChildren()) {
                            Product product = listPro.getValue(Product.class);
                            Cart item = new Cart(String.valueOf(product.getId()),
                                    product.getImg(),
                                    product.getName(),
                                    Long.parseLong(String.valueOf(product.getPrice())) * quantity + "",
                                    quantity);
                            arrayCart.add(item);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        } else {
            FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fDatabase.getReference("Products");
            Query query = dRef.orderByChild("proId").equalTo(receiveIdPro);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot listPro : snapshot.getChildren()) {
                        Product product = listPro.getValue(Product.class);
                        Cart item = new Cart(String.valueOf(product.getId()),
                                product.getImg(),
                                product.getName(),
                                Long.parseLong(String.valueOf(product.getPrice())) * quantity + "",
                                quantity);
                        arrayCart.add(item);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}