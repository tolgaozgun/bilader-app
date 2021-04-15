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
import com.breakdown.bilader.adapters.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private EditText inputEmail;
    private EditText inputPassword;
    private Button signUpButton;
    private Button logInButton;
    private TextView forgotPassword;
    private ProgressDialog loadingBar;
    private final String SESSION_TOKEN_KEY = "SESSION_TOKEN";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        logInButton = ( Button ) findViewById( R.id.buttonLogIn );
        signUpButton = ( Button ) findViewById( R.id.buttonSignUp );
        inputEmail = ( EditText ) findViewById( R.id.editTextEmail );
        inputPassword = ( EditText ) findViewById( R.id.editTextPassword );
        forgotPassword = ( TextView ) findViewById( R.id.forgotPassword );
        loadingBar = new ProgressDialog( this );

        // buttonEffect( logInButton, 0xe01c79e4 );
        // buttonEffect( signUpButton, 0xe01c79e4 );

        signUpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( LoginActivity.this,
                        RegisterActivity.class );
                startActivity( intent );
            }
        } );

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

    private void loginUser() {
        String email;
        String password;

        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();

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

    private void allowAccessToAccount( String email, String password ) {
        SharedPreferences sharedPreferences;
        Map< String, String > params;
        JSONObject json;
        String token;
        String message;
        params = new HashMap< String, String >();
        params.put( "email", email );
        params.put( "password", password );

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );

        json = HttpAdapter.getRequestJSON( RequestType.LOGIN, params,
                LoginActivity.this );
        try {
            if ( json.getBoolean( "success" ) ) {
                token = json.getString( "token" );
                sharedPreferences.edit().putString( SESSION_TOKEN_KEY, token );
            }
            message = json.getString( "message" );
            Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
        } catch ( JSONException e ) {
            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
        }
    }

    /**
     * adds on press effects for buttons
     * @param button that is going to have the effect
     * @param colorHexCode that is hex value of the press color effect
     */
    /**public static void buttonEffect( View button, int colorHexCode )
     {
     button.setOnTouchListener( new View.OnTouchListener()
     {
     @SuppressLint( "ClickableViewAccessibility")
     public boolean onTouch( View v, MotionEvent event )
     {
     switch ( event.getAction() )
     {
     case MotionEvent.ACTION_DOWN:
     {
     v.getBackground().setColorFilter( colorHexCode, PorterDuff.Mode.SRC_ATOP );
     v.invalidate();
     break;
     }
     case MotionEvent.ACTION_UP:
     {
     v.getBackground().clearColorFilter();
     v.invalidate();
     break;
     }
     }
     return false;
     }
     });
     }*/
}


