package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.Volley;
import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.MultipartUtility;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.controllers.FollowersActivity;
import com.breakdown.bilader.controllers.FollowingActivity;
import com.breakdown.bilader.controllers.LoginActivity;
import com.breakdown.bilader.controllers.MyProductsActivity;
import com.breakdown.bilader.controllers.SettingsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A class that makes connection between its layout and data and additionally
 * allows to user move between fragments and activities
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class MyProfileFragment extends Fragment {
    private Activity context;
    private Button followersButton;
    private Button myProductsButton;
    private Button followingButton;
    private Button settingsButton;
    private Button logOutButton;
    private String imageUrl;
    private ProgressDialog loadingBar;
    private SharedPreferences sharedPreferences;
    private String currentUserId;
    private TextView nameView;
    private ImageView avatarView;
    private Uri imageUri;

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
        View view;

        super.onCreateView( inflater, container, savedInstanceState );

        view = inflater.inflate( R.layout.fragment_myprofile, container,
                false );
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this.getContext() );
        currentUserId = sharedPreferences.getString( "id", "" );
        avatarView = view.findViewById( R.id.image_myprofile_avatar );
        nameView = view.findViewById( R.id.text_myprofile_fullname );

        updateInfo();

        context = getActivity();

        return view;
    }

    //todo
    // update profile image after user changes
    private void updateInfo() {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "user_id", currentUserId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                String avatarUrl;
                String name;

                try {
                    if ( object.getBoolean( "success" ) ) {
                        name = object.getJSONObject( "user" ).getString(
                                "name" );
                        avatarUrl =
                                object.getJSONObject( "user" ).getString(
                                        "avatar_url" );
                        nameView.setText( name );
                        Picasso.get().load( avatarUrl ).fit().centerCrop().into( avatarView );
                    } else {
                        name = "ERROR";
                        nameView.setText( name );
                    }
                } catch ( JSONException e ) {
                    name = "ERROR";
                    nameView.setText( name );
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                String name;
                name = "ERROR";
                nameView.setText( name );

            }
        }, RequestType.USER, params, getContext(), true );
    }

    /**
     * enables activity to be run
     */
    public void onStart() {
        super.onStart();

        avatarView.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                Intent galery;
                galery = new Intent();
                galery.setType( "image/*" );
                galery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galery, "Select"
                        + " Picture" ), 1 );
            }
        } );

        myProductsButton = context.findViewById( R.id.myProductsButton );
        myProductsButton.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                //create an Intent object
                Intent intent;

                intent = new Intent( context, MyProductsActivity.class );

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
                Intent intent;

                intent = new Intent( context, FollowersActivity.class );

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
                Intent intent;

                intent = new Intent( context, FollowingActivity.class );

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
                Intent intent;

                intent = new Intent( context, SettingsActivity.class );

                //start the second activity
                startActivity( intent );
            }
        } );
    }

    private void logOut() {
        Intent intent;
        // TODO: log user out
        /*
        loadingBar.setTitle( "log out" );
        loadingBar.setMessage( "Logging out!" );
        loadingBar.setCanceledOnTouchOutside( false );
        loadingBar.show();*/
        sharedPreferences.edit().remove( "id" ).apply();
        sharedPreferences.edit().remove( "session_token" ).apply();

        intent = new Intent( context, LoginActivity.class );
        startActivity( intent );
    }
    // Todo
    // update profile image in server too

    /**
     * Called when an activity that is launched exits, it gives the requestCode
     * to started it with, the resultCode it returned, and any additional data
     * from it
     *
     * @param requestCode: is the int object that allows to identify who this
     *                     result came from.
     * @param resultCode:  is the int object that is returned by the child
     *                     activity through its setResult().
     * @param data:        If non-null, this intent is being used to return
     *                     result data to the caller
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 1 && resultCode == -1 ) {
            Uri imageUri;
            imageUri = data.getData();

            imageUrl = getPath( imageUri );
            String file_extn =
                    imageUrl.substring( imageUrl.lastIndexOf( "." ) + 1 );
            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap( context.getContentResolver(), imageUri );
                Picasso.get().load( imageUri ).fit().centerCrop().into( avatarView );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            try {
                if ( file_extn.equals( "img" ) || file_extn.equals( "jpg" ) || file_extn.equals( "jpeg" ) || file_extn.equals( "gif" ) || file_extn.equals( "png" ) ) {
                    uploadFile( "http://88.99.11.149:8080/server" +
                            "/MultipartServlet", imageUrl );
                } else {
                    //NOT IN REQUIRED FORMAT
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

    }

    public String getPath( Uri uri ) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query( uri, proj,
                null, null, null );
        if ( cursor.moveToFirst() ) {
            int column_index =
                    cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
            res = cursor.getString( column_index );
        }
        cursor.close();
        return res;
    }

    private void uploadFile( String requestURL, final String fileName ) {
        StringBuffer responseString;
        JSONObject json;
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );

        String charset = "UTF-8";
        File uploadFile1 = new File( fileName );

        try {
            MultipartUtility multipart = new MultipartUtility( requestURL,
                    charset );
            multipart.addFilePart( "file", uploadFile1 );

            List< String > response = multipart.finish();
            responseString = new StringBuffer();
            for ( String str : response ) {
                responseString.append( str );
            }
            json = new JSONObject( responseString.toString() );
            imageUrl = json.getString( "url" );
            sendPhotoRequest( imageUrl );
        } catch ( IOException | JSONException ex ) {
            System.out.println( "ERROR: " + ex.getMessage() );
            ex.printStackTrace();
        }
    }

    private void sendPhotoRequest( String imageURL ) {
        HashMap<String, String> params;
        params = new HashMap<String, String>();
        params.put("avatar_url", imageURL);
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( context, object.getString( "message" ), Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( context, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( context, message, Toast.LENGTH_SHORT ).show();

            }
        }, RequestType.UPDATE_PHOTO, params, getContext(), true);
    }
}
