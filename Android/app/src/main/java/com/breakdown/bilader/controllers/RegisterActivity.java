package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;

import com.breakdown.bilader.R;

public class RegisterActivity extends Activity {
    private Button signUpRegisterButton;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPasswordOnce;
    private EditText inputPasswordAgain;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        signUpRegisterButton = (Button) findViewById( R.id.button_sign_up_register );
        inputName = (EditText) findViewById( R.id.editText_full_name );
        inputEmail = (EditText) findViewById( R.id.editText_mail_register );
        inputPasswordOnce = (EditText) findViewById( R.id.editText_password_register );
        inputPasswordAgain = (EditText) findViewById( R.id.editText_password_again_register );
        loadingBar = new ProgressDialog(this );

        signUpRegisterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                registerAccount();
            }
        });
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

        if( TextUtils.isEmpty( name ) ){
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT ).show();
        }
        else if( TextUtils.isEmpty( email ) ){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT ).show();
        }
        else if( TextUtils.isEmpty( passwordOne ) ){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT ).show();
        }
        else if( TextUtils.isEmpty( passwordTwo ) ){
            Toast.makeText(this, "Please enter password again!", Toast.LENGTH_SHORT ).show();
        }
        else if( passwordOne != passwordTwo ){
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT ).show();
        }
        else{
            loadingBar.setTitle( "sign up");
            loadingBar.setMessage( "Please wait while we check your credentials!");
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            validateEmail( name, email, passwordOne, passwordTwo );
        }
    }

    private void validateEmail( String name, final String email, String passwordOne, String passwordTwo ) {
        // TODO: Check if the email exists in the database.
        // TODO: Check if the email is a bilkent email.
        // TODO: If they are true, send a verification code and lead to Verification Activity.
    }
}