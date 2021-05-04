package com.breakdown.bilader.controllers;

import android.app.Activity;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the registration activity of the user. Verifies
 * the email of the user and creates an account.
 *
 * @author Deniz Gökçen
 * @author Tolga Özgün
 * @version 13.04.2021
 */

public class RegisterActivity extends Activity {
    private Button signUpRegisterButton;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPasswordOnce;
    private EditText inputPasswordAgain;
    private ProgressDialog loadingBar;
    private ImageView inputAvatar;
    private String avatarURL;
    private Uri imageUri;

    /**
     * Initializes the UI properties and sets an action to each of them
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle
     *                           contains the data it most recently supplied in
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        signUpRegisterButton = ( Button ) findViewById( R.id.button_sign_up_register );
        inputName = ( EditText ) findViewById( R.id.editText_full_name );
        inputEmail = ( EditText ) findViewById( R.id.editText_mail_register );
        inputPasswordOnce = ( EditText ) findViewById( R.id.editText_password_register );
        inputPasswordAgain = ( EditText ) findViewById( R.id.editText_password_again_register );
        loadingBar = new ProgressDialog( this );
        inputAvatar = ( ImageView ) findViewById( R.id.image_avatar_register );

        inputAvatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent galery;

                galery = new Intent();
                galery.setType( "image/*" );
                galery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galery, "Select" + " Picture" ), 1 );
            }
        } );

        signUpRegisterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                registerAccount();
            }
        } );
    }

    /**
     * Creates an account to register the user.
     */
    private void registerAccount() {
        String name;
        String email;
        String passwordOne;
        String passwordTwo;

        name = inputName.getText().toString();
        email = inputEmail.getText().toString();
        passwordOne = inputPasswordOnce.getText().toString();
        passwordTwo = inputPasswordAgain.getText().toString();

        /**
         * Name, email and password slots can't be empty and the two
         * passwords must match.
         */
        if ( TextUtils.isEmpty( name ) ) {
            Toast.makeText( this, "Please enter name!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( email ) ) {
            Toast.makeText( this, "Please enter email!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( passwordOne ) ) {
            Toast.makeText( this, "Please enter password!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( passwordTwo ) ) {
            Toast.makeText( this, "Please enter password again!", Toast.LENGTH_SHORT ).show();
        } else if ( !passwordOne.equals( passwordTwo ) ) {
            Toast.makeText( this, "Passwords don't match!", Toast.LENGTH_SHORT ).show();
        } else {
            loadingBar.setTitle( "sign up" );
            loadingBar.setMessage( "Please wait while we check your " + "credentials!" );
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            //TODO: AVATAR URL
            validateEmail( name, email, passwordOne, avatarURL );
        }
    }

    /**
     * Validates the email.
     *
     * @param name      the name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     * @param avatarURL the avatar of the user
     */
    private void validateEmail( String name, final String email, String password, String avatarURL ) {
        final String JSON_SUCCESS_PATH = "success";
        final String JSON_MESSAGE_PATH = "message";
        final String JSON_VERIFICATION_SUCCESS_PATH = "verification_success";
        final String JSON_VERIFICATION_MESSAGE_PATH = "verification_message";
        final VolleyCallback callback;
        SharedPreferences sharedPreferences;
        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "name", name );
        params.put( "email", email );
        params.put( "password", password );
        params.put( "avatar_url", avatarURL );

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

        callback = new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject json ) {
                try {

                    String verificationMessage;
                    String message;
                    if ( json == null ) {
                        message = "Connection error.";
                        verificationMessage = message;
                    } else {
                        if ( json.getBoolean( JSON_SUCCESS_PATH ) && json.getBoolean( JSON_VERIFICATION_SUCCESS_PATH ) ) {
                            Intent intent;
                            intent = new Intent( RegisterActivity.this, VerificationActivity.class );
                            startActivity( intent );
                        }
                        message = json.getString( JSON_MESSAGE_PATH );
                        verificationMessage = json.getString( JSON_VERIFICATION_MESSAGE_PATH );
                    }
                    Toast.makeText( RegisterActivity.this, message, Toast.LENGTH_SHORT ).show();
                    Toast.makeText( RegisterActivity.this, verificationMessage, Toast.LENGTH_SHORT ).show();
                    loadingBar.dismiss();
                } catch ( JSONException e ) {
                    Toast.makeText( RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onFail( String message ) {
                loadingBar.dismiss();
                //Toast.makeText( LoginActivity.this, message,
                //       Toast.LENGTH_SHORT ).show();

            }
        };

        HttpAdapter.getRequestJSON( callback, RequestType.REGISTER, params, RegisterActivity.this, false );
    }

    /**
     * Called when an activity that is launched exits, it gives the requestCode to
     * started it with, the resultCode it returned, and any additional data from it
     *
     * @param requestCode:        is the int object that allows to identify who
     *                            this result came from.
     * @param resultCode:         is the int object that is returned by the child
     *                            activity through its setResult().
     * @param data:               If non-null, this intent is being used to return
     *                            result data to the caller
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );



        /*if ( requestCode == 1 && resultCode == -1 ) {
            imageUri = data.getData();

            String filePath = getPath( imageUri );
            String file_extn =
                    filePath.substring( filePath.lastIndexOf( "." ) + 1 );


            try {
                if ( file_extn.equals( "img" ) || file_extn.equals( "jpg" ) || file_extn.equals( "jpeg" ) || file_extn.equals( "gif" ) || file_extn.equals( "png" ) ) {
                    uploadImage( new URI( filePath ) );
                } else {
                    //NOT IN REQUIRED FORMAT
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }*/
    }

    /*public String getPath( Uri uri ) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query( uri, proj,
                null, null, null );
        if ( cursor.moveToFirst() ) {
            int column_index =
                    cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
            res = cursor.getString( column_index );
        }
        cursor.close();
        return res;
    }


    private void uploadImage( URI uri ) {
        JSONObject json;
        File sourceFile = new File( uri.getPath() );
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );

        try {
            final com.squareup.okhttp.MediaType MEDIA_TYPE_PNG =
                    com.squareup.okhttp.MediaType.parse( "image/*" );

            com.squareup.okhttp.RequestBody requestBody =
                    new MultipartBuilder().type( MultipartBuilder.FORM ).addFormDataPart( "image", "image.png", com.squareup.okhttp.RequestBody.create( MEDIA_TYPE_PNG, sourceFile ) ).build();

            com.squareup.okhttp.Request request =
                    new com.squareup.okhttp.Request.Builder().url( "http://88"
                            + ".99.11.149:8080/server/index.jsp" ).put( requestBody ).addHeader( "Content-Type", "application/x-www-formurlencoded" ).build();

            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.Response response =
                    client.newCall( request ).execute();


            System.out.println( "Response data " + response );
            json = new JSONObject(response.toString());
            avatarURL = json.getString( "url" );


        } catch ( Exception e ) {

            System.out.println( "Response error is" + e );

        }
    }*/
}