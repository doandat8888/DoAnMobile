package com.example.doanmobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doanmobile.R;
import com.example.doanmobile.adapter.ProductListAdapter;
import com.example.doanmobile.model.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    RecyclerView recyclerView;
    TextView txtWelcome;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String fullName) {
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

        //Lấy thông tin người dùng
        Bundle bundle = getArguments();
        if(bundle != null) {
            String userFullname = bundle.getString("name");
            txtWelcome.setText("Hello " + userFullname);
        }



        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.slideone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slideone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slideone, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slideone, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        Product[] productList = new Product[] {
                new Product("Pizza hải sản Pesto xanh", R.drawable.pizza1, 25),
                new Product("Pizza hải sản Pesto xanh", R.drawable.pizza1, 25),
                new Product("Pizza hải sản Pesto xanh", R.drawable.pizza1, 25),
                new Product("Pizza hải sản Pesto xanh", R.drawable.pizza1, 25),
        };

        recyclerView = view.findViewById(R.id.recyclerViewProductList);
        ProductListAdapter adapter = new ProductListAdapter(productList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}