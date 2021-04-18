package com.breakdown.bilader.controllers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.fragments.AddNewProductFragment;
import com.breakdown.bilader.fragments.HomeFragment;
import com.breakdown.bilader.fragments.MyProfileFragment;
import com.breakdown.bilader.fragments.NotificationsFragment;
import com.breakdown.bilader.fragments.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BiltraderActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_biltrader );
        BottomNavigationView bottomNav = findViewById( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener( navListener );
        //I added this if statement to keep the selected fragment when
        // rotating the device
        if ( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new HomeFragment() ).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
            Fragment selectedFragment = null;
            switch ( item.getItemId() ) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new WishlistFragment();
                    break;
                case R.id.nav_add:
                    selectedFragment = new AddNewProductFragment();
                    break;
                case R.id.nav_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.nav_person:
                    selectedFragment = new MyProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, selectedFragment ).commit();
            return true;
        }
    };
}
