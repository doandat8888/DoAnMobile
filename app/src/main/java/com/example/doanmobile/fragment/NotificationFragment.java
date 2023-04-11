package com.example.doanmobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doanmobile.R;
import com.example.doanmobile.adapter.NotificationAdapter;
import com.example.doanmobile.model.MyNotification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    //private NotificationAdapter notificationAdapter;
    final ArrayList<MyNotification> mNotification = new ArrayList<MyNotification>();

    TextView txtView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        mNotification.add(new MyNotification(R.drawable.bellicon, "WELCOME", "Have a good, fun working day and delicious lunch!"));
        mNotification.add(new MyNotification(R.drawable.drink, "NEW DRINK", "Be the first one who try!"));
        mNotification.add(new MyNotification(R.drawable.hamburger, "SALE!!", "40% Discount for who order pizza size XXL"));

        recyclerView = view.findViewById(R.id.recyclerViewNotif);
        NotificationAdapter notificationAdapter = new NotificationAdapter(mNotification, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(notificationAdapter);


        Log.d("Size", String.valueOf(mNotification.size()));
        return view;
    }
}
















