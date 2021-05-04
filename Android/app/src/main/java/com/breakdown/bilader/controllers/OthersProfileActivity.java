package com.breakdown.bilader.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.fragments.OnSaleFragment;
import com.breakdown.bilader.fragments.ReviewsFragment;
import com.breakdown.bilader.models.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OthersProfileActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager2 viewPager2;

    private ImageView profilePhoto;
    private TextView userNameText;
    private TextView numberOfFollowers;
    private TextView numberOfFollowings;
    private TextView numberOfReviews;
    private TextView activityTitle;
    private TextView numberOfProducts;
    private EditText contentOfTheReview; //?

    private Button follow;
    private Button sendMessage;
    private Button reportButton;
    private ReviewsFragment fragmentForReview;
    private OnSaleFragment fragmentForSale;

    private ArrayList< Fragment > fragmentList = new ArrayList<>();
    private ArrayList< String > fragmentTitleList = new ArrayList<>();

    private User currentUser;
    private LinearLayout followingView;
    private LinearLayout followersView;
    private LinearLayout salesView;
    private Gson gson;
    private Bundle bundle;
    private String userId;
    private String userAvatar;
    private String userName;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_othersprofile );

        tableLayout = findViewById( R.id.others_profile_tab_layout );
        viewPager2 = findViewById( R.id.others_profile_view_pager );
        numberOfReviews =
                findViewById( R.id.text_other_profile_reviews_in_parantheses );
        numberOfFollowers =
                findViewById( R.id.text_others_profile_follower_amount );
        numberOfFollowings =
                findViewById( R.id.text_others_profile_following_number );
        userNameText = findViewById( R.id.text_others_profile_user_name );
        profilePhoto = findViewById( R.id.image_others_profile_avatar );
        follow = findViewById( R.id.button_others_profile_follow );
        sendMessage = findViewById( R.id.button_others_profile_chat );
        followingView = findViewById( R.id.followingView );
        followersView = findViewById( R.id.followersView );
        salesView = findViewById( R.id.saleView );
        activityTitle = findViewById( R.id.othersProfileTitle );
        reportButton = findViewById( R.id.reportButton );

        fragmentForReview = new ReviewsFragment();
        fragmentForSale = new OnSaleFragment();
        if ( getIntent().hasExtra( "user" ) ) {
            gson = new Gson();
            currentUser = gson.fromJson( getIntent().getStringExtra( "user" )
                    , User.class );
        } else if ( getIntent().hasExtra( "user_id" ) ) {
            userName = getIntent().getStringExtra( "user_name" );
            userId = getIntent().getStringExtra( "user_id" );
            userAvatar = getIntent().getStringExtra( "user_avatar" );
            activityTitle.setText( userName + "'s Profile" );
            currentUser = new User( userName, userAvatar, userId );
        }
        bundle = new Bundle();
        bundle.putString( "user_id", currentUser.getId() );
        fragmentForSale.setArguments( bundle );
        fragmentForReview.setArguments( bundle );

        fragmentList.add( fragmentForSale );
        fragmentList.add( fragmentForReview );

        MyViewpagerAdapter adapter = new MyViewpagerAdapter( this );

        viewPager2.setAdapter( adapter );

        fragmentTitleList.add( "On sale" );
        fragmentTitleList.add( "Reviews" );


        new TabLayoutMediator( tableLayout, viewPager2,
                ( tab, position ) -> tab.setText( fragmentTitleList.get( position ) + ( "" ) ) ).attach();
        userNameText.setText( currentUser.getName() );

        Picasso.get().load( currentUser.getAvatar() ).fit().centerCrop().into( profilePhoto );

        userReviews();
        getSoldCount();
        getFollowersCount( numberOfFollowers );
        getFollowingsCount( numberOfFollowings );
        //getReviewCount();

        reportButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent newIntent;
                newIntent = new Intent( OthersProfileActivity.this,
                        ReportActivity.class );
                newIntent.putExtra( "id", currentUser.getId() );
                newIntent.putExtra( "title", currentUser.getName() );
                newIntent.putExtra( "image_url", currentUser.getAvatar() );
                newIntent.putExtra( "report-type", 0 );
                startActivity( newIntent );
            }
        } );

        //TODO
        follow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                currentUser.follow( OthersProfileActivity.this );
            }
        } );

        sendMessage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                String myJson;
                intent = new Intent( OthersProfileActivity.this,
                        PrivateChatActivity.class );
                gson = new Gson();
                myJson = gson.toJson( currentUser );

                intent.putExtra( "user", myJson );
                startActivity( intent );
            }
        } );

        followingView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( OthersProfileActivity.this,
                        FollowingActivity.class );
                intent.putExtra( "user_id", currentUser.getId() );
                startActivity( intent );
            }
        } );


        followersView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( OthersProfileActivity.this,
                        FollowersActivity.class );
                intent.putExtra( "user_id", currentUser.getId() );
                startActivity( intent );
            }
        } );

    }

    /**
     * Sets the followings count of the current user.
     */
    public void getFollowingsCount( TextView numberOfFollowings ) {

        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "user_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println( "ccc" );
                        numberOfFollowings.setText( String.valueOf( object.getInt( "count" ) ) );
                    }
                } catch ( JSONException e ) {
                }
            }

            @Override
            public void onFail( String message ) {
            }
        }, RequestType.FOLLOWING_COUNT, params, this, false );

    }


    /**
     * Sets the followers count of the current user.
     */
    public void getFollowersCount( TextView numberOfFollowers ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "following_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        numberOfFollowers.setText( String.valueOf( object.getInt( "count" ) ) );
                    }
                } catch ( JSONException e ) {
                }
            }

            @Override
            public void onFail( String message ) {
            }
        }, RequestType.FOLLOWERS_COUNT, params, this, false );
    }

    /**
     * Shows the reviews of the current user.
     */
    public void userReviews() {
        //TODO

    }

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

