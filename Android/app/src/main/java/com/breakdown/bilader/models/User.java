package com.breakdown.bilader.models;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.toolbox.Volley;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.stfalcon.chatkit.commons.models.IUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that represents the user,sets its properties and when needed
 * manipulate it.
 *
 * @author breakDown
 * @version 14.04.2021
 */
public class User implements Serializable, IUser {

    private String userName;
    private String userAvatar;
    private String userId;

    /**
     * Constructor for User class
     *
     * @param userName   String value of username.
     * @param userAvatar String value of avatar URL.
     * @param userId     String value of user id.
     */
    public User( String userName, String userAvatar, String userId ) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userId = userId;
    }

    /**
     * Returns the username of the current user
     *
     * @return String value of username.
     */
    @Override
    public String getName() {
        return userName;
    }

    /**
     * Returns the avatar URL of the current user
     *
     * @return String value of avatar URL.
     */
    @Override
    public String getAvatar() {
        return userAvatar;
    }

    /**
     * Returns the id number of the current user
     *
     * @return String value of the user id
     */
    @Override
    public String getId() {
        return userId;
    }


    /**
     * Adds current user to the following people list for the user in the
     * parameter. The person who did the action is provided in the parameter.
     */
    public void follow( VolleyCallback callback, Context context ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "following_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                    }
                    Toast.makeText( context, object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( context, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                }
                callback.onSuccess( object );
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( context, message, Toast.LENGTH_SHORT ).show();
                callback.onFail( message );
            }
        }, RequestType.FOLLOW, params, context, true );
    }

    @NonNull
    @Override
    /**
     * Returns user's name to identify user.
     */ public String toString() {
        return userName;
    }

}
