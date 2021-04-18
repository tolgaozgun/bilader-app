package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.FollowersActivity;
import com.breakdown.bilader.controllers.FollowingActivity;
import com.breakdown.bilader.controllers.MyProductsActivity;
import com.breakdown.bilader.controllers.MyProfileActivity;
import com.breakdown.bilader.models.Product;

public class MyProfileFragment extends Fragment {
    Activity context;
    private Button followersButton;
    private Button myProductsButton;
    private Button followingButton;

    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        super.onCreateView( inflater, container, savedInstanceState );
        View view = inflater.inflate( R.layout.fragment_myprofile, container,
                false );
        context = getActivity();
        return view;
    }

    public void onStart() {
        super.onStart();
        myProductsButton = context.findViewById( R.id.myProductsButton );
        myProductsButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, MyProductsActivity.class );
                //start the second activity
                startActivity( intent );
            }

        } );

        followersButton = context.findViewById( R.id.followersButton );
        followersButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, FollowersActivity.class );
                //start the second activity
                startActivity( intent );
            }
        } );

        followingButton = context.findViewById( R.id.followingButton );
        followingButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, FollowingActivity.class );
                //start the second activity
                startActivity( intent );
            }
        } );
    }

}
