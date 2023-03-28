package com.example.doanmobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doanmobile.PageActivity;
import com.example.doanmobile.R;
import com.example.doanmobile.adapter.CategoryListAdapter;
import com.example.doanmobile.adapter.ProductListAdapter;
import com.example.doanmobile.model.Account;
import com.example.doanmobile.model.Category;
import com.example.doanmobile.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

    ImageSlider imageSlider;
    RecyclerView productViewer, categoryViewer;
    TextView txtWelcome;
    ImageView userImgView;
    androidx.appcompat.widget.SearchView searchView;
    String type;
    Context context;

    ArrayList<Product> productList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String fullName, Context context) {
        HomeFragment fragment = new HomeFragment();
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
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if(bundle != null) {
            String userFullname = bundle.getString("name");
            txtWelcome.setText("Hello " + userFullname);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        imageSlider = view.findViewById(R.id.imageSliderHome);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        userImgView = view.findViewById(R.id.imageViewSrc);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();

        //Lấy thông tin người dùng và loại sản phẩm
        Bundle bundle = getArguments();
        if(bundle != null) {
            String userFullname = bundle.getString("name");
            txtWelcome.setText("Hello " + userFullname);
            String img = bundle.getString("img");
            Picasso.get().load(img).resize(300, 300).centerCrop().into(userImgView);
            type = bundle.getString("type");
        }

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.slideone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slidetwo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slidethree, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slidefour, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

//        CategoryListAdapter mAdapter = new CategoryListAdapter(getDataCategoryList(), getContext());
//
//        mAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(String data) {
//                System.out.println("Category: " + data);
//            }
//        });



        getProductList(view, productViewer, productList, "");
        getCategoryList(view);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchResult(newText, productList);
                return true;
            }
        });
        return view;
    }

    public void getProductList(View view, RecyclerView productRecyclerView, ArrayList<Product> productList, String typeProduct) {

        ArrayList<Product> productByCategoryList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        context = getContext();
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    productList.add(product);
                }
                if(context != null) {
                    productViewer = view.findViewById(R.id.recyclerViewProductList);
                    ProductListAdapter adapter = new ProductListAdapter(productList, getContext());
                    productViewer.setLayoutManager(new LinearLayoutManager(context));
                    productViewer.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getCategoryList(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Categories").addValueEventListener(new ValueEventListener() {
            ArrayList<Category> categoryList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Category category = snap.getValue(Category.class);
                    categoryList.add(category);
                }
                categoryViewer = view.findViewById(R.id.recyclerViewCategoryList);
                CategoryListAdapter adapter = new CategoryListAdapter(view, productViewer, categoryList, context);
                categoryViewer.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                categoryViewer.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getProductByCategory(View view, RecyclerView productRecyclerView, ArrayList<Product> productList, String typeProduct, Context context) {
        ArrayList<Product> productByCategoryList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    if(product.getType().equals(typeProduct)) {
                        productByCategoryList.add(product);
                    }
                }
                ProductListAdapter adapter = new ProductListAdapter(productByCategoryList, context);
                productRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<Category> getDataCategoryList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        ArrayList<Category> categoryList = new ArrayList<>();
        databaseReference.child("Categories").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Category category = snap.getValue(Category.class);
                    categoryList.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return categoryList;
    }

    public void getSearchResult(String newText, ArrayList<Product> productList){
        ArrayList<Product> searchResult = new ArrayList<>();
        for (Product product : productList){
            if (product.getName().toLowerCase().contains(newText.toLowerCase())){
                searchResult.add(product);
            }
        }
        if (productList.isEmpty()){
            return;
        }else{
            ProductListAdapter adapter = new ProductListAdapter(searchResult, getContext());
            productViewer.setAdapter(adapter);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}