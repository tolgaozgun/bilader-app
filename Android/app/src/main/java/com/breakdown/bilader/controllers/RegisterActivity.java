package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    private Button signUpRegisterButton;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPasswordOnce;
    private EditText inputPasswordAgain;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        signUpRegisterButton =
                ( Button ) findViewById( R.id.button_sign_up_register );
        inputName = ( EditText ) findViewById( R.id.editText_full_name );
        inputEmail = ( EditText ) findViewById( R.id.editText_mail_register );
        inputPasswordOnce =
                ( EditText ) findViewById( R.id.editText_password_register );
        inputPasswordAgain =
                ( EditText ) findViewById( R.id.editText_password_again_register );
        loadingBar = new ProgressDialog( this );

        signUpRegisterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                registerAccount();
            }
        } );
    }

    private void registerAccount() {
        String name;
        String email;
        String passwordOne;
        String passwordTwo;

        name = inputName.getText().toString();
        email = inputEmail.getText().toString();
        passwordOne = inputPasswordOnce.getText().toString();
        passwordTwo = inputPasswordAgain.getText().toString();

        if ( TextUtils.isEmpty( name ) ) {
            Toast.makeText( this, "Please enter name!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( email ) ) {
            Toast.makeText( this, "Please enter email!", Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( passwordOne ) ) {
            Toast.makeText( this, "Please enter password!",
                    Toast.LENGTH_SHORT ).show();
        } else if ( TextUtils.isEmpty( passwordTwo ) ) {
            Toast.makeText( this, "Please enter password again!",
                    Toast.LENGTH_SHORT ).show();
        } else if ( !passwordOne.equals( passwordTwo ) ) {
            Toast.makeText( this, "Passwords don't match!",
                    Toast.LENGTH_SHORT ).show();
        } else {
            loadingBar.setTitle( "sign up" );
            loadingBar.setMessage( "Please wait while we check your " +
                    "credentials!" );
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            //TODO: AVATAR URL
            validateEmail( name, email, passwordOne, "" );
        }
    }

    private void validateEmail( String name, final String email,
                                String password, String avatarURL ) {
        final String JSON_SUCCESS_PATH = "success";
        final String JSON_MESSAGE_PATH = "message";
        final VolleyCallback callback;
        SharedPreferences sharedPreferences;
        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "name", name );
        params.put( "email", email );
        params.put( "password", password );
        params.put( "avatar_url", avatarURL );

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );

        callback = new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject json ) {
                try {
                    String token;
                    String message;
                    if ( json == null ) {
                        message = "Connection error.";
                    } else {
                        if ( json.getBoolean( JSON_SUCCESS_PATH ) ) {
                            Intent intent;
                            intent = new Intent( RegisterActivity.this,
                                    VerificationActivity.class );
                            startActivity( intent );
                        }
                        message = json.getString( JSON_MESSAGE_PATH );
                    }
                    Toast.makeText( RegisterActivity.this, message,
                            Toast.LENGTH_SHORT ).show();
                    loadingBar.dismiss();
                } catch ( JSONException e ) {
                    Toast.makeText( RegisterActivity.this, e.getMessage(),
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

        HttpAdapter.getRequestJSON( callback, RequestType.REGISTER, params,
                RegisterActivity.this );
    }
}