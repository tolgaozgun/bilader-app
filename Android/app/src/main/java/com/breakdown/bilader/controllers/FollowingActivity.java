package com.breakdown.bilader.controllers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.UserAdapter;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.*;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * A class that makes connection between its layout and data
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class FollowingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList< User > followingList;
    private UserAdapter adapter;

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
        setContentView( R.layout.activity_following );
        setSupportActionBar( findViewById( R.id.wishlistToolBar ) );

        recyclerView = findViewById( R.id.followingRecycler );
        recyclerView.setHasFixedSize( true );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        /*
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

        followingList = new ArrayList<>();
        followingList.add( user1 );
        followingList.add( user2 );
        followingList.add( user3 );
        followingList.add( user4 );
        followingList.add( user5 );
        followingList.add( user6 );
        followingList.add( user7 );
        followingList.add( user8 );
        followingList.add( user9 );
        followingList.add( user10 );
        followingList.add( user11 );
        followingList.add( user12 );*/

        adapter = new UserAdapter( this, followingList );
        recyclerView.setAdapter( adapter );

        getFollowings( recyclerView, getIntent().getStringExtra( "user_id" ) );

    }

    public ArrayList< User > getFollowings( RecyclerView recyclerView,
                                            String userId ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "user_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                String avatarURL;
                String name;
                String id;
                Iterator< String > keys;
                JSONObject tempJson;
                try {
                    if ( object.getBoolean( "success" ) ) {

                        followingList = new ArrayList< User >();
                        keys = object.getJSONObject( "users" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "users" ).getJSONObject( key );
                            name = tempJson.getString( "name" );
                            avatarURL = tempJson.getString( "avatar_url" );
                            id = tempJson.getString( "id" );
                            followingList.add( new User( name, avatarURL,
                                    id ) );
                        }
                    }
                    printView( recyclerView );
                    Toast.makeText( FollowingActivity.this,
                            object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    printView( recyclerView );
                    Toast.makeText( FollowingActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                printView( recyclerView );
            }
        }, RequestType.FOLLOWINGS, params, this, false );

        return null;
    }

    private void printView( RecyclerView recyclerView ) {
        adapter = new UserAdapter( this, followingList );
        recyclerView.setAdapter( adapter );
    }

}
