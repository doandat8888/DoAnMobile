package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.ViewHolder.CartViewHolder;
import com.example.doanmobile.model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton btnReturn;
    private Button btnConfirmOrder;
    private Button btnDeleteItemCart;
    private TextView txtTotalAmount;
    private double priceTotal = 0.0;
    String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);

        productId = getIntent().getStringExtra("productId");
        rvCart = findViewById(R.id.rv_cart);
        rvCart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(layoutManager);

        btnReturn = findViewById(R.id.btn_Return);
        txtTotalAmount = findViewById(R.id.txt_cartTotal);

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(priceTotal));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        cartListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    btnConfirmOrder.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                        holder.productQty.setText(model.getQuantity());
                        holder.productPrice.setText(model.getPrice());
                        holder.productName.setText(model.getName());

                        double iProductPrice = Double.valueOf(model.getPrice())*Double.valueOf(model.getQuantity());
                        priceTotal+=iProductPrice;
                        txtTotalAmount.setText(String.valueOf(priceTotal));

                        btnDeleteItemCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{
                                        "Edit",
                                        "Remove"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Do you want to remove this pizza?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i==0){
                                            Intent intent = new Intent(CartActivity.this, ViewDetailProductActivity.class);
                                            intent.putExtra("productId",model.getId() );
                                            startActivity(intent);
                                        }

                                        if(i==1){
                                            cartListRef.child("Products")
                                                    .child(model.getId())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(CartActivity.this, PageActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        rvCart.setAdapter(adapter);
        adapter.startListening();
    }
}


