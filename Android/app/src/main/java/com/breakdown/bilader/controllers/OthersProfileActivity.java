package com.breakdown.bilader.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class OthersProfileActivity extends Fragment {

    private ImageView profilePhoto;
    private TextView userName;
    private TextView numberOfFollowers;
    private TextView numberOfFollowings;
    private TextView numberOfProducts;
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewReviews;
    private Button onSale;
    private Button reviews;
    private Button follow;
    private Button sendMessage;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        //view.findViewById()s

        userProducts();
        userReviews();
        getProductCount();
        getFollowersCount();
        getFollowingsCount();

        recyclerViewProducts.setVisibility(View.VISIBLE);
        recyclerViewReviews.setVisibility(View.GONE);

        onSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewProducts.setVisibility(View.VISIBLE);
                recyclerViewReviews.setVisibility(View.GONE);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewProducts.setVisibility(View.GONE);
                recyclerViewReviews.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void userProducts(){
        //TODO
    }

    private void userReviews(){
        //TODO
    }

    private void getProductCount(){
        //TODO
        //numberOfProducts.setText( count);
    }

    private void getFollowersCount(){
        //TODO
        //numberOfFollowers.setText( count);
    }

    private void getFollowingsCount(){
        //TODO
        //numberOfFollowings.setText( count);
    }

    //checkFollowingStatus()

}
