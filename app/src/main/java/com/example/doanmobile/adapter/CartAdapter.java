package com.example.doanmobile.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanmobile.R;
import com.example.doanmobile.fragment.CartFragment;
import com.example.doanmobile.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> cartArr;

    public CartAdapter(Context context, ArrayList<Cart> cartArr) {
        this.cartArr = cartArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartArr.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CartViewHolder {
        public TextView txtCartItemName, txtCartItemPrice;
        public ImageView imgItemCart, imgNoted;
        public Button btnMinus, btnPlus;
        public ImageButton btnDelItem;
        public EditText editTxtQuantity;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CartViewHolder cartViewHolder = null;
        if (view == null) {
            cartViewHolder = new CartViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, null);
            cartViewHolder.txtCartItemName = view.findViewById(R.id.txtCartItemName);
            cartViewHolder.txtCartItemPrice = view.findViewById(R.id.txtCartItemPrice);
            cartViewHolder.imgItemCart = view.findViewById(R.id.imgItemCart);
            cartViewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            cartViewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            cartViewHolder.btnDelItem = view.findViewById(R.id.btnDelItem);
            cartViewHolder.editTxtQuantity = view.findViewById(R.id.editTxtQuantity);
            cartViewHolder.imgNoted = view.findViewById(R.id.imgNoted);
            view.setTag(cartViewHolder);
        } else {
            cartViewHolder = (CartViewHolder) view.getTag();
        }

        Cart cart = (Cart) getItem(position);
        cartViewHolder.txtCartItemName.setText(cart.getName());
        cartViewHolder.txtCartItemPrice.setText(cart.getPrice() + "đ");
        Picasso.get().load(cart.getImg()).resize(cartViewHolder.imgItemCart.getWidth(), 100).into(cartViewHolder.imgItemCart);
        cartViewHolder.editTxtQuantity.setText(cart.getQuantity() + "");

        CartViewHolder finalViewHolder = cartViewHolder;
        cartViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityNew = 1;
                if (finalViewHolder.editTxtQuantity.getText().toString().isEmpty()) {
                    quantityNew = 1;
                } else {
                    quantityNew = Integer.parseInt(finalViewHolder.editTxtQuantity.getText().toString()) + 1;
                }
                int quantityCurrent = CartFragment.arrayCart.get(position).getQuantity();
                String price = CartFragment.arrayCart.get(position).getPrice();
                CartFragment.arrayCart.get(position).setQuantity(quantityNew);
                Long newPrice = (Long.parseLong(price) * quantityNew) / quantityCurrent;
                CartFragment.arrayCart.get(position).setPrice(String.valueOf(newPrice));
                finalViewHolder.editTxtQuantity.setText(CartFragment.arrayCart.get(position).getQuantity() + "");
                finalViewHolder.txtCartItemPrice.setText(CartFragment.arrayCart.get(position).getPrice() + "đ");
                CartFragment.totalCart();
            }
        });
        cartViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityNew = 1;
                if (finalViewHolder.editTxtQuantity.getText().toString().isEmpty()) {
                    quantityNew = 1;
                } else {
                    int quantity = Integer.parseInt(finalViewHolder.editTxtQuantity.getText().toString());
                    if (quantity > 1) {
                        quantityNew = Integer.parseInt(finalViewHolder.editTxtQuantity.getText().toString()) - 1;
                    }
                    int quantityCurrent = CartFragment.arrayCart.get(position).getQuantity();
                    String price = CartFragment.arrayCart.get(position).getPrice();
                    CartFragment.arrayCart.get(position).setQuantity(quantityNew);
                    Long newPrice = (Long.parseLong(price) * quantityNew) / quantityCurrent;
                    CartFragment.arrayCart.get(position).setPrice(String.valueOf(newPrice));
                    finalViewHolder.editTxtQuantity.setText(CartFragment.arrayCart.get(position).getQuantity() + "");
                    finalViewHolder.txtCartItemPrice.setText(CartFragment.arrayCart.get(position).getPrice() + "đ");
                    CartFragment.totalCart();
                }
            }
        });
        cartViewHolder.btnDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa món hàng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        CartFragment.arrayCart.remove(position);
                        notifyDataSetChanged();
                        CartFragment.totalCart();
                        if (CartFragment.arrayCart.size() == 0) {
                            CartFragment.txtNoted.setVisibility(View.VISIBLE);
                            CartFragment.listCart.setVisibility(View.INVISIBLE);
                            CartFragment.imgNoted.setVisibility(View.VISIBLE);
                            notifyDataSetChanged();
                        } else {
                            CartFragment.txtNoted.setVisibility(View.INVISIBLE);
                            CartFragment.imgNoted.setVisibility(View.INVISIBLE);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        return view;
    }
}