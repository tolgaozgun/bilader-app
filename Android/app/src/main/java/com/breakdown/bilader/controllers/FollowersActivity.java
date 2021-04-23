package com.breakdown.bilader.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.FollowersAdapter;
import com.breakdown.bilader.adapters.FollowersAdapter;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.*;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that makes connection between its layout and data
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */


public class FollowersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList< User > followerList;
    private FollowersAdapter adapter;

    /**
     * this is the method where most initialization made such as UI and widgets
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_followers );

        recyclerView = findViewById( R.id.followersRecycler );
        recyclerView.setHasFixedSize( true );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        // sample users created for testing
        User user1 = new User( "Yahya Demirel", "mail@mail.com", "avatar_male"
                , "1" );
        User user2 = new User( "Burcu Kaplan", "mail@mail.com",
                "avatar_female", "2" );
        User user3 = new User( "Korhan Kaya", "mail@mail.com", "avatar_male",
                "3" );
        User user4 = new User( "Deniz Gökçen", "mail@mail.com",
                "avatar_female", "4" );
        User user5 = new User( "Tolga Özgün", "mail@mail.com", "avatar_male",
                "5" );
        User user6 = new User( "Burak Yıldır", "mail@mail.com", "avatar_male"
                , "6" );
        User user7 = new User( "Mansur Yavaş", "mail@mail.com", "avatar_male"
                , "7" );
        User user8 = new User( "Okan Tekman", "mail@mail.com",
                "avatar_no_gender", "8" );
        User user9 = new User( "Salim Çıracı", "mail@mail.com", "avatar_male"
                , "9" );
        User user10 = new User( "Mustafa Nakeeb", "mail@mail.com",
                "avatar_male", "10" );
        User user11 = new User( "Kenan Demir", "mail@mail.com",
                "avatar_no_gender", "11" );
        User user12 = new User( "Berşan Özgür", "mail@mail.com",
                "avatar_no_gender", "12" );

        followerList = new ArrayList<>();
        followerList.add( user1 );
        followerList.add( user2 );
        followerList.add( user3 );
        followerList.add( user4 );
        followerList.add( user5 );
        followerList.add( user6 );
        followerList.add( user7 );
        followerList.add( user8 );
        followerList.add( user9 );
        followerList.add( user10 );
        followerList.add( user11 );
        followerList.add( user12 );

        //followerList = getFollowers();

        adapter = new FollowersAdapter( this, followerList );
        recyclerView.setAdapter( adapter );


    }

    public ArrayList< User > getFollowers() {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        //TODO
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.FOLLOWERS, params, this );
        showUsers();
    }
}
