package com.example.doanmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.R;
import com.example.doanmobile.model.MyNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MyNotification> mNotification;

    public NotificationAdapter(ArrayList<MyNotification> mNotification, Context mContext) {
        this.mContext = mContext;
        this.mNotification = mNotification;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MyNotification myNotification = mNotification.get(i);
        viewHolder.image.setImageResource(myNotification.getImageResource());
        viewHolder.notice.setText(myNotification.getNotice());
        viewHolder.content.setText(myNotification.getContent());
    }

    @Override
    public int getItemCount() {
        return mNotification.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView notice, content, title;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            notice = itemView.findViewById(R.id.notice);
            content = itemView.findViewById(R.id.content);
        }
    }
}
