package com.breakdown.bilader.controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.breakdown.bilader.R;

public class LoginActivity extends Activity
{
    private EditText inputEmail;
    private EditText inputPassword;
    private Button signUpButton;
    private Button logInButton;
    private TextView forgotPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        logInButton = (Button) findViewById( R.id.buttonLogIn );
        signUpButton = (Button) findViewById( R.id.buttonSignUp );
        inputEmail = (EditText) findViewById( R.id.editTextEmail );
        inputPassword = (EditText) findViewById( R.id.editTextPassword );
        forgotPassword = (TextView) findViewById( R.id.forgotPassword );
        loadingBar = new ProgressDialog(this );

        buttonEffect( logInButton, 0xe01c79e4 );
        buttonEffect( signUpButton, 0xe01c79e4 );

        signUpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        forgotPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class );
                startActivity( intent );
            }
        });

        logInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if( TextUtils.isEmpty( email ) ){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT ).show();
        }
        else if( TextUtils.isEmpty( password ) ){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT ).show();
        }
        else{
            loadingBar.setTitle( "log in");
            loadingBar.setMessage( "Please wait while we check your credentials!");
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            allowAccessToAccount( email, password );
        }
    }

    private void allowAccessToAccount( String email, String password ) {
        // TODO: Check if the email exists in database.
        // If exists, if users email = email && users password = password log in successfully.
        // Intent intent = new Intent(LoginActivity.this, BiltraderActivity.class );
        // startActivity(intent);
    }

    /**
     * adds on press effects for buttons
     * @param button that is going to have the effect
     * @param colorHexCode that is hex value of the press color effect
     */
    public static void buttonEffect( View button, int colorHexCode )
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
    }
}


