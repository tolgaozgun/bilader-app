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


public class FollowersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList< User > followerList;
    private UserAdapter adapter;
    private String userId;

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

        getFollowers(recyclerView, getIntent().getStringExtra( "user_id" ));


    }

    public ArrayList< User > getFollowers(RecyclerView recyclerView, String userId) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put("following_id", userId);
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

                        followerList = new ArrayList< User >();
                        keys = object.getJSONObject( "users" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "users" ).getJSONObject( key );
                            name = tempJson.getString( "name" );
                            avatarURL = tempJson.getString( "avatar_url" );
                            id = tempJson.getString( "id" );
                            followerList.add( new User(name, avatarURL, id) );
                        }
                    }
                    printView(recyclerView);
                    Toast.makeText( FollowersActivity.this,
                            object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    printView(recyclerView);
                    Toast.makeText( FollowersActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                printView(recyclerView);
            }
        }, RequestType.FOLLOWERS, params, this );

        return null;
    }

    private void printView( RecyclerView recyclerView ) {
        adapter = new UserAdapter( this, followerList );
        recyclerView.setAdapter( adapter );
    }
}
