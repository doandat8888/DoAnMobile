package com.example.doanmobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.adapter.CartInCheckoutAdapter;
import com.example.doanmobile.adapter.CartListAdapter;
import com.example.doanmobile.adapter.ProductListAdapter;
import com.example.doanmobile.model.Invoice;
import com.example.doanmobile.model.InvoiceDetail;
import com.example.doanmobile.model.Product;
import com.example.doanmobile.model.ProductCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity{
    TextView txtTotal;
    GridView gridViewCartInCheckout;
    Button btn_confirm;
    private static ArrayList<Product> productList = new ArrayList<>();
    /*Spinner spinner_paymentMethod;
    EditText editText_recipientName, editText_shipToAddress;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Array chứa các sản phẩm trong cart
        ArrayList<ProductCart> cartProducts = getCartProducts();
        getProductList();


        //Setup
        gridViewCartInCheckout = findViewById(R.id.gridViewCartInCheckout);
        txtTotal = findViewById(R.id.txt_total_cart);
        btn_confirm = findViewById(R.id.btn_confirm);
        /*editText_recipientName = findViewById(R.id.editText_recipientName);
        editText_shipToAddress = findViewById(R.id.editText_shipToAddress);
        spinner_paymentMethod = findViewById(R.id.spinner_paymentMethod);*/
        /*ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.payment_methods, R.layout.spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_paymentMethod.setAdapter(arrayAdapter);*/

        CartInCheckoutAdapter adapter = new CartInCheckoutAdapter(cartProducts, getApplicationContext());
        gridViewCartInCheckout.setAdapter(adapter);

        Intent intent = getIntent();
        DecimalFormat myFormatter = new DecimalFormat("###,###");

        int cartTotal = intent.getIntExtra("total", 0);
        txtTotal.setText(myFormatter.format(cartTotal));

        //Lấy số lượng đơn đặt đang có mặt trong database
        final int[] numberOfEntry = {0};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Invoices");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    numberOfEntry[0]++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Nhấn nút Confirm
        btn_confirm.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("UserInfoPref", getApplicationContext().MODE_PRIVATE);
            String accountName = sp.getString("name", "");
            String accountUserName = sp.getString("username", "");
            String accountPhoneNumber = sp.getString("phoneNumber", "");
            /*String recipientName = editText_recipientName.getText().toString();
            String shipToAddress = editText_shipToAddress.getText().toString();
            String paymentMethod = spinner_paymentMethod.getSelectedItem().toString();*/

            /*Log.d("recip: ", recipientName);
            Log.d("address: ", shipToAddress);
            Log.d("payment: ", paymentMethod);
            Log.d("acname: ", accountName);
            Log.d("accusername: ", accountUserName);
            Log.d("accphone: ", accountPhoneNumber);*/

            confirmCheckout(cartProducts,numberOfEntry[0] + 1, accountName, accountUserName, accountPhoneNumber, cartTotal);
            Toast.makeText(getApplicationContext(), "Checkout success", Toast.LENGTH_SHORT).show();
        });
    }

    private void confirmCheckout(ArrayList<ProductCart> cartProducts, int invoiceId, String accountName, String accountUserName, String accountPhoneNumber, int cartTotal) {

        DatabaseReference refInvoice = FirebaseDatabase.getInstance().getReference().child("Invoices");
        DatabaseReference refDetail = FirebaseDatabase.getInstance().getReference().child("InvoiceDetails");
        DatabaseReference refProduct = FirebaseDatabase.getInstance().getReference().child("Products");

        Invoice invoice = new Invoice(invoiceId, accountName, accountUserName, accountPhoneNumber, cartTotal);
        refInvoice.push().setValue(invoice);

        for (ProductCart item : cartProducts){
            int price = Integer.valueOf(item.getPrice());
            int quantity = Integer.valueOf(item.getQuantity());
            InvoiceDetail detail = new InvoiceDetail(invoiceId, item.getName(), quantity, price, price * quantity, cartTotal);

            for (Product toBeChanged : productList){
                if (toBeChanged.getId().equals(item.getId())){

                    /*Product product = new Product(toBeChanged.getId(), toBeChanged.getName(), toBeChanged.getDescription(), toBeChanged.getImg(),
                            price, toBeChanged.getType(), toBeChanged.getQuantity() - quantity);*/
                    refProduct.child(toBeChanged.getId()).child("quantity").setValue(toBeChanged.getQuantity() - quantity);

                    /*Map<String, Object> updateValues = product.toMap();
                    refProduct.child(toBeChanged.getId()).updateChildren(updateValues);*/
                }
            }

            refDetail.push().setValue(detail);
        }

        // Xóa giỏ hàng hiện tại
        CartListAdapter adapter = new CartListAdapter(getCartProducts(), getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Refresh the view
        adapter.clearCart();
        adapter.notifyDataSetChanged();
        gridViewCartInCheckout.setAdapter(null);
        txtTotal.setText("0");
        finish();
    }

    public String getBoughtProductKey(DatabaseReference refProduct, String boughtId){
        final String[] key = {""};

        refProduct.orderByChild("id").equalTo(boughtId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    key[0] = snap.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return key[0];
    }

    public void getProductList() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    productList.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<ProductCart> getCartProducts() {
        ArrayList<ProductCart> cartProducts = new ArrayList<>();
        // Lấy dữ liệu từ Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
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
}