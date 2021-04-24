package com.breakdown.bilader.controllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.breakdown.bilader.models.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private ReviewsFragment fragmentForReview;
    private OnSaleFragment fragmentForSale;


    private ArrayList< Fragment > fragmentList = new ArrayList<>();
    private ArrayList< String > fragmentTitleList = new ArrayList<>();

    private User currentUser;
    private Gson gson;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_othersprofilebyyahya );

        tableLayout = findViewById( R.id.others_profile_tab_layout );
        viewPager2 = findViewById( R.id.others_profile_view_pager );
        numberOfReviews =
                findViewById( R.id.text_other_profile_reviews_in_parantheses );
        userName = findViewById( R.id.text_others_profile_user_name );
        profilePhoto = findViewById( R.id.image_others_profile_avatar );

        fragmentForReview = new ReviewsFragment();
        fragmentForSale = new OnSaleFragment();

        fragmentList.add( fragmentForSale );
        fragmentList.add( fragmentForReview );

        MyViewpagerAdapter adapter = new MyViewpagerAdapter( this );

        viewPager2.setAdapter( adapter );

        fragmentTitleList.add( "On sale" );
        fragmentTitleList.add( "Revıews" );


        new TabLayoutMediator( tableLayout, viewPager2,
                ( tab, position ) -> tab.setText( fragmentTitleList.get( position ) + ( "" ) ) ).attach();
        gson = new Gson();
        currentUser = gson.fromJson( getIntent().getStringExtra( "user" ),
                User.class );
        userName.setText( currentUser.getUserName() );

        Picasso.get().load( currentUser.getUserAvatar() ).fit().centerInside().into( profilePhoto );

        getUserInfo();
        userProducts();
        userReviews();
        getSoldCount();
        getFollowersCount();
        getFollowingsCount();
        //getReviewCount();

        /*reportButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent newIntent;

                newIntent = new Intent( OthersProfileActivity.this,
                ReportActivity.class );

                // TODO
                newIntent.putExtra( "userName", "Hello" );
                startActivity( newIntent );
            }
        } );*/

        //TODO
        /*follow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

            }
        } );*/

        //TODO
        /*sendMessage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent newIntent;

                newIntent = new Intent( OthersProfileActivity.this,
                PrivateChatActivity.class );

                startActivity( newIntent );
            }
        } );*/


    }

    /**
     * Sets the followings count of the current user.
     */
    public void getFollowingsCount() {
        //TODO
        //numberOfFollowings.setText( count);
    }

    /**
     * Sets the followers count of the current user.
     */
    public void getFollowersCount() {
        //TODO
        //numberOfFollowers.setText( count);
    }

    /**
     * Sets user information of the current user.
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
     */
    public void userProducts() {
        //TODO

    }

    /**
     * Shows the reviews of the current user.
     */
    public void userReviews() {
        //TODO

    }

    //?????

    /**
     * Shows the number of soles done of the current user.
     */
    public void getSoldCount() {
        //TODO
    }


    /**
     * Shows the number of reviews done of the current user.
     */
    public void getReviewCount() {
        numberOfReviews.setText( "(" + fragmentForReview.getReviewNumber() +
                "Reviews )" );
    }

    //??????
    public boolean isFollowing( User user ) {
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

