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
import com.breakdown.bilader.fragments.OthersProfileFragment;
import com.breakdown.bilader.fragments.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A class that makes connection between main fragments and allows to
 * make a transition between these fragments
 *
 * @author Yahya Eren Demirel
 * @version 17.04.2021
 */

public class BiltraderActivity extends AppCompatActivity {

    /**
     * this is the method where most initialization made such as UI and widgets
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_biltrader );

        BottomNavigationView bottomNav;

        bottomNav = findViewById( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener( navListener );
        // shows home fragment at the opening
        if ( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new HomeFragment() ).commit();
        }

    }

    // Listener for handling selection events on bottom navigation items.
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        /**
         * Called when an item in the bottom navigation menu is selected.
         * @param item is the selected item
         * @return true to display the item as the selected item and false if
         * the item should not be selected.
         */
        @Override
        public boolean onNavigationItemSelected( @NonNull MenuItem item ) {

            Fragment selectedFragment;

            selectedFragment = null;

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
            //  that defines the location where the fragment should be placed
            //  within the activity's
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, selectedFragment ).commit();
            return true;
        }
    };
}
