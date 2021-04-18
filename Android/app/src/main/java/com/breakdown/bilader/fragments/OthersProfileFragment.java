package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.User;

public class OthersProfileFragment extends Fragment {

    private ImageView profilePhoto;
    private TextView userName;
    private EditText numberOfFollowers;
    private EditText numberOfFollowings;
    private TextView numberOfProducts;
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewReviews;
    private Button onSale;
    private Button reviews;
    private Button follow;
    private Button sendMessage;
    private User userOne;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        View view;
        // this is required for variable view might not have been initialized
        // for now.
        view = null;

        view = inflater.inflate(R.layout.activity_othersprofile, container, false);

        profilePhoto = view.findViewById(R.id.profilePhoto);
        onSale = view.findViewById(R.id.onSale);
        reviews = view.findViewById(R.id.reviews);
        follow = view.findViewById(R.id.follow);

        getUserInfo();
        userProducts();
        userReviews();
        getProductCount();
        getFollowersCount();
        getFollowingsCount();

        recyclerViewProducts.setVisibility( View.VISIBLE );
        recyclerViewReviews.setVisibility( View.GONE );

        onSale.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                recyclerViewProducts.setVisibility( View.VISIBLE );
                recyclerViewReviews.setVisibility( View.GONE );
            }
        } );

        reviews.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                recyclerViewProducts.setVisibility( View.GONE );
                recyclerViewReviews.setVisibility( View.VISIBLE );
            }
        } );

        return view;
    }

    /**
     * Sets user information of the current user.
     *
     */
    public void getUserInfo() {
        userName.setText(userOne.getUserName());
        // profilePhoto
        // rating?
    }

    /**
     * Sets the followings count of the current user.
     *
     */
    public void getFollowingsCount() {
        //TODO
        //numberOfFollowings.setText( count);
    }

    /**
     * Sets the followers count of the current user.
     *
     */
    public void getFollowersCount() {
        //TODO
        //numberOfFollowers.setText( count);
    }

    /**
     * Shows the products of the current user.
     *
     */
    public void userProducts() {
        //TODO
    }

    public void userReviews() {
        //TODO
    }

    /**
     * Sets the product count of the current user.
     *
     */
    public void getProductCount() {
        //TODO
        //numberOfProducts.setText( count);
    }

}
