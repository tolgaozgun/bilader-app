package com.breakdown.bilader.controllers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.breakdown.bilader.R;
import com.breakdown.bilader.fragments.OnSaleFragment;
import com.breakdown.bilader.fragments.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OtherProfileActivityByYahya extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager2 viewPager2;

    private ArrayList< Fragment > fragmentList = new ArrayList<>();
    private ArrayList< String > fragmentTitleList = new ArrayList<>();

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_othersprofilebyyahya );

        tableLayout = findViewById( R.id.others_profile_tab_layout );
        viewPager2 = findViewById( R.id.others_profile_view_pager );

        fragmentList.add( new OnSaleFragment() );
        fragmentList.add( new ReviewsFragment() );

        MyViewpagerAdapter adapter = new MyViewpagerAdapter( this );

        viewPager2.setAdapter( adapter );

        fragmentTitleList.add( "On sale" );
        fragmentTitleList.add( "Revıews" );


        new TabLayoutMediator( tableLayout, viewPager2, ( tab, position ) -> tab.setText( fragmentTitleList.get( position ) + ("")) ).attach();
    }

    private class MyViewpagerAdapter extends FragmentStateAdapter {

        public MyViewpagerAdapter( @NonNull FragmentActivity fragmentActivity ) {
            super( fragmentActivity );
        }

        @NonNull
        @Override
        public Fragment createFragment( int position ) {
            return fragmentList.get( position );
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}

