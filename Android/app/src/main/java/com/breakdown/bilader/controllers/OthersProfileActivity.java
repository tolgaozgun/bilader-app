package com.breakdown.bilader.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.adapters.ReviewsAdapter;
import com.breakdown.bilader.fragments.OnSaleFragment;
import com.breakdown.bilader.fragments.ReviewsFragment;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.Review;
import com.breakdown.bilader.models.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class OthersProfileActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager2 viewPager2;

    private ImageView profilePhoto;
    private TextView userName;
    private TextView numberOfFollowers;
    private TextView numberOfFollowings;
    private TextView numberOfReviews;
    private TextView numberOfProducts;
    private EditText contentOfTheReview; //?

    private Button follow;
    private Button sendMessage;
    private Button reportButton;
    private Button submitReport; //?
    private User userOne;


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

        getUserInfo();
        userProducts();
        userReviews();
        getSoldCount();
        getFollowersCount();
        getFollowingsCount();

        /*reportButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent newIntent;

                newIntent = new Intent( OthersProfileActivity.this, ReportActivity.class );

                // To be continue...
                newIntent.putExtra( "userName", "Hello" );
                startActivity( newIntent );
            }
        } );*/
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
     * Sets user information of the current user.
     *
     */
    public void getUserInfo() {
        //TODO
        //userName.setText(userOne.getUserName());
        //numberOfReviews.setText(userReviews.size());
        // profilePhoto
        // review number
    }

    /**
     * Shows the products of the current user.
     *
     */
    public void userProducts() {
        //TODO

    }

    /**
     * Shows the reviews of the current user.
     *
     */
    public void userReviews() {
        //TODO

    }

    //?????
    /**
     * Shows the number of soles done of the current user.
     *
     */
    public void getSoldCount(){
        //TODO
    }

    //??????
    public boolean isFollowing( User user) {
        //TODO
        /* if the user is followed --> follow.setText("following")
           if the user is not followed --> follow.setText("follow");
         */

        return true;
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

