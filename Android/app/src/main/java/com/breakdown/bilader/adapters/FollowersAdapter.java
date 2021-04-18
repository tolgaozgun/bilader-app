package com.breakdown.bilader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.*;
import java.util.*;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowerHolder>{
    private Context mContext;
    private ArrayList<User> followers;

    public FollowersAdapter(Context mContext, ArrayList<User> followers) {
        this.mContext = mContext;
        this.followers = followers;
    }

    public class FollowerHolder extends RecyclerView.ViewHolder {
        public ImageView imageFollowerScreenAvatar;
        public TextView textUserName;

        public FollowerHolder(@NonNull View itemView) {
            super(itemView);
            imageFollowerScreenAvatar = itemView.findViewById(R.id.image_follower_screen_avatar);
            textUserName = itemView.findViewById(R.id.text_user_name);
        }
    }

    @NonNull
    @Override
    public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_followers,parent,false);

        return new FollowerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerHolder holder, int position) {
        User follower =followers.get(position);
        holder.textUserName.setText(follower.getUserName());
        holder.imageFollowerScreenAvatar.setImageResource(mContext.getResources().getIdentifier(follower.getUserAvatar(),"drawable",mContext.getPackageName()));

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

}
