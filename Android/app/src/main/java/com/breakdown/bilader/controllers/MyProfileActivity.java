package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.breakdown.bilader.R;

public class MyProfileActivity extends Activity {

    // TextView userName (getName()) (getInstance()??)
    // ImageView profilePhoto

    private Button myProductsButton;
    private Button followersButton;
    private Button followingsButton;
    private Button settingsButton;
    private Button logOutButton;

    // findViewById()s

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        myProductsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Intent newIntent = new Intent( MyProfileActivity.this, MyProductsActivity.class );

                startActivity( newIntent );
            }
        } );

        followersButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Intent newIntent = new Intent( MyProfileActivity.this, FollowersActivity.class );

                startActivity( newIntent );
            }
        } );

        followingsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Intent newIntent = new Intent( MyProfileActivity.this, FollowingActivity.class );

                startActivity( newIntent );
            }
        } );

        settingsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                Intent newIntent = new Intent( MyProfileActivity.this, SettingsActivity.class );

                startActivity( newIntent );
            }
        } );

        // logout? (direct log in screen ?)
    }
}
