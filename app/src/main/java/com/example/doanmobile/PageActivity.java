package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.doanmobile.fragment.CartFragment;
import com.example.doanmobile.fragment.HomeFragment;
import com.example.doanmobile.fragment.LikeFragment;
import com.example.doanmobile.fragment.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PageActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    TextView txtWelcome;
    String userFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        Intent intent = getIntent();
        userFullName = intent.getStringExtra("name");
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new Fragment();
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.home:
                    bundle.putString("name", userFullName);
                    selectedFragment = new HomeFragment();
                    selectedFragment.setArguments(bundle);
                    break;
                case R.id.cart:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.heart:
                    selectedFragment = new LikeFragment();
                    break;
                case R.id.notification:
                    selectedFragment = new NotificationFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, selectedFragment).commit();
            return true;
        }
    };

}