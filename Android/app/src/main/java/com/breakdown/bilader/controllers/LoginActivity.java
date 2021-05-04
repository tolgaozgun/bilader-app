package com.breakdown.bilader.controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.NotificationAdapter;
import com.breakdown.bilader.adapters.NotificationService;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the login activity of the user. Allows access
 * after checking the credentials and saves the instance.
 *
 * @author Deniz Gökçen
 * @author Tolga Özgün
 * @version 13.04.2021
 */

public class LoginActivity extends Activity {
    private EditText inputEmail;
    private EditText inputPassword;
    private Button signUpButton;
    private Button logInButton;
    private TextView forgotPassword;
    private ProgressDialog loadingBar;
    private final String SESSION_TOKEN_KEY = "session_token";
    private final String USER_ID_KEY = "id";
    private String token;
    private String userId;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private HashMap< String, String > requestParams;

    /**
     * Initializes the UI properties and sets an action to each of them
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle
     *                           contains the data it most recently supplied in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );
        logInButton = ( Button ) findViewById( R.id.buttonLogIn );
        signUpButton = ( Button ) findViewById( R.id.buttonSignUp );
        inputEmail = ( EditText ) findViewById( R.id.editTextEmail );
        inputPassword = ( EditText ) findViewById( R.id.editTextPassword );
        forgotPassword = ( TextView ) findViewById( R.id.forgotPassword );
        loadingBar = new ProgressDialog( this );
        intent = getIntent();

        if ( intent != null && intent.hasExtra( "email" ) ) {
            inputEmail.setText( intent.getStringExtra( "email" ) );
        }

        startService( new Intent( this, NotificationService.class ) );

        // Check if there is an ongoing session.
        requestParams = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Intent intent;
                try {
                    if ( object != null && object.getBoolean( "success" ) ) {
                        Toast.makeText( LoginActivity.this,
                                object.getString( "message" ),
                                Toast.LENGTH_SHORT ).show();
                        intent = new Intent( LoginActivity.this,
                                BiltraderActivity.class );
                        intent.putExtra( "goBack", false );
                        startActivity( intent );
                        finish();
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.TOKEN, requestParams, this, false );


        // buttonEffect( logInButton, 0xe01c79e4 );
        // buttonEffect( signUpButton, 0xe01c79e4 );

        /**
         * Redirects the user to register activity when clicked on the button
         * "Sign Up".
         */
        signUpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( LoginActivity.this,
                        RegisterActivity.class );
                startActivity( intent );
            }
        } );

        /**
         * Redirects the user to forgot password activity when clicked on the
         * button "Forgot Password".
         */
        forgotPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( LoginActivity.this,
                        ForgotPasswordActivity.class );
                startActivity( intent );
            }
        } );

        logInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                loginUser();
            }
        } );
    }

    /**
     * Logs user in if the email & password combination exists in the database.
     */
    private void loginUser() {
        String email;
        String password;

        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();

        /**
         * Email and password slots can't be empty.
         */
        if ( TextUtils.isEmpty( email ) ) {
            Toast.makeText( this, "Please enter email!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( password ) ) {
            Toast.makeText( this, "Please enter password!",
                    Toast.LENGTH_SHORT ).show();
        } else {
            loadingBar.setTitle( "log in" );
            loadingBar.setMessage( "Please wait while we check your " +
                    "credentials!" );
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            allowAccessToAccount( email, password );
        }
    }

    /**
     * Stop when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // We do not want users to go back to any previous activity from
        // LoginActivity.
        return;
    }

    private void allowAccessToAccount( String email, String password ) {
        final String JSON_SUCCESS_PATH = "success";
        final String JSON_VERIFIED_PATH = "verified-error";
        final String JSON_TOKEN_PATH = "token";
        final String JSON_MESSAGE_PATH = "message";
        final String JSON_USER_ID_PATH = "id";
        final VolleyCallback callback;
        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "email", email );
        params.put( "password", password );


        callback = new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject json ) {
                try {

                    Intent intent;
                    String token;
                    String message;
                    String userId;
                    if ( json == null ) {
                        message = "Connection error.";
                    } else {
                        if ( json.getBoolean( JSON_SUCCESS_PATH ) ) {
                            token = json.getString( JSON_TOKEN_PATH );
                            userId = json.getString( JSON_USER_ID_PATH );
                            sharedPreferences.edit().putString( SESSION_TOKEN_KEY, token ).apply();
                            sharedPreferences.edit().putString( USER_ID_KEY,
                                    userId ).apply();

                            saveUserInstance( userId );

                            intent = new Intent( LoginActivity.this,
                                    BiltraderActivity.class );
                            startActivity( intent );
                        }

                        if ( json.getBoolean( JSON_VERIFIED_PATH ) ) {
                            intent = new Intent( LoginActivity.this,
                                    VerificationActivity.class );
                            startActivity( intent );
                        }

                        message = json.getString( JSON_MESSAGE_PATH );
                    }
                    Toast.makeText( LoginActivity.this, message,
                            Toast.LENGTH_SHORT ).show();
                    loadingBar.dismiss();
                } catch ( JSONException e ) {
                    Toast.makeText( LoginActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
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

        HttpAdapter.getRequestJSON( callback, RequestType.LOGIN, params,
                LoginActivity.this, false );
    }

    private void saveUserInstance( String userId ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "user_id", userId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                String name;
                String avatarUrl;
                Gson gson;
                String myJson;
                User user;
                try {
                    if ( object.getBoolean( "success" ) ) {

                        name = object.getJSONObject( "user" ).getString(
                                "name" );
                        avatarUrl =
                                object.getJSONObject( "user" ).getString(
                                        "avatar_url" );
                        user = new User( name, avatarUrl, userId );
                        gson = new Gson();
                        myJson = gson.toJson( user );
                        sharedPreferences.edit().putString( "current_user",
                                myJson ).apply();
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.USER, params, this, false );

    }

}


