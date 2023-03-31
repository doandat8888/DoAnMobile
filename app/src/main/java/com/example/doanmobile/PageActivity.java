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
import com.example.doanmobile.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PageActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    TextView txtWelcome;
    String userFullName, userImg, phoneNumber, keyUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        Intent intent = getIntent();
        userFullName = intent.getStringExtra("name");
        userImg = intent.getStringExtra("img");
        phoneNumber = intent.getStringExtra("phoneNumber");
        keyUser = intent.getStringExtra("key");
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new Fragment();
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.home:
                    bundle.putString("name", userFullName);
                    bundle.putString("img", userImg);
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
                case R.id.user:
                    bundle.putString("name", userFullName);
                    bundle.putString("img", userImg);
                    bundle.putString("phoneNumber", phoneNumber);
                    bundle.putString("key", keyUser);
                    selectedFragment = new UserFragment();
                    selectedFragment.setArguments(bundle);
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, selectedFragment).commit();
            return true;
        }
    };

}