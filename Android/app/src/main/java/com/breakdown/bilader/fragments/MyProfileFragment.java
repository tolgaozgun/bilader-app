package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.FollowersActivity;
import com.breakdown.bilader.controllers.FollowingActivity;
import com.breakdown.bilader.controllers.LoginActivity;
import com.breakdown.bilader.controllers.MyProductsActivity;
import com.breakdown.bilader.controllers.SettingsActivity;

/**
 * A class that makes connection between its layout and data and additionally
 * allows to user move between fragments and activities
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class MyProfileFragment extends Fragment {
    Activity context;
    private Button followersButton;
    private Button myProductsButton;
    private Button followingButton;
    private Button settingsButton;
    private Button logOutButton;
    private ProgressDialog loadingBar;
    private SharedPreferences sharedPreferences;
    private String currentUserId;
    private TextView name;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater            is the LayoutInflater object that can be used
     *                            to inflate any views in the fragment
     * @param container:          If non-null, this is the parent view that the
     *                            fragment's UI should be attached to. The
     *                            fragment should not add the view itself, but
     *                            this can be used to generate the LayoutParams
     *                            of the view.
     * @param savedInstanceState: If non-null, this fragment is being
     *                            re-constructed from a previous saved state as
     *                            given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        super.onCreateView( inflater, container, savedInstanceState );
        View view = inflater.inflate( R.layout.fragment_myprofile, container,
                false );
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this.getContext() );
        currentUserId = sharedPreferences.getString( "id", "" );
        context = getActivity();
        return view;
    }

    /**
     * enables activity to be run
     */
    public void onStart() {
        super.onStart();

        /*name = ( TextView ) context.findViewById( R.id
        .text_myprofile_fullname );
        name.setText( user.getName() );*/

        myProductsButton = context.findViewById( R.id.myProductsButton );
        myProductsButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, MyProductsActivity.class );
                //start the second activity
                startActivity( intent );
            }

        } );

        followersButton = context.findViewById( R.id.followersButton );
        followersButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, FollowersActivity.class );
                intent.putExtra( "user_id", currentUserId );
                //start the second activity
                startActivity( intent );
            }
        } );

        followingButton = context.findViewById( R.id.followingButton );
        followingButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, FollowingActivity.class );
                intent.putExtra( "user_id", currentUserId );
                //start the second activity
                startActivity( intent );
            }
        } );

        logOutButton = context.findViewById( R.id.logOutButton );
        logOutButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder( context );
                builder.setTitle( "Confirmation!" ).
                        setMessage( "Are you sure you want to logout?" );
                builder.setPositiveButton( "Yes",
                        new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        logOut();
                    }
                } );
                builder.setNegativeButton( "No",
                        new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        dialog.cancel();
                    }
                } );

                AlertDialog logOutAlert = builder.create();
                logOutAlert.show();
            }
        } );

        settingsButton = context.findViewById( R.id.settingButton );
        settingsButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                //create an Intent object
                Intent intent = new Intent( context, SettingsActivity.class );
                //start the second activity
                startActivity( intent );
            }
        } );
    }

    private void logOut() {
        // TODO: log user out
        /*
        loadingBar.setTitle( "log out" );
        loadingBar.setMessage( "Logging out!" );
        loadingBar.setCanceledOnTouchOutside( false );
        loadingBar.show();*/
        sharedPreferences.edit().remove( "id" ).apply();
        sharedPreferences.edit().remove( "session_token" ).apply();

        Intent intent = new Intent( context, LoginActivity.class );
        startActivity( intent );
    }
}
