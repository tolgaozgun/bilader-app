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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUpRegisterButton = (Button) findViewById(R.id.button_sign_up_register);
        inputName = (EditText) findViewById(R.id.editText_full_name);
        inputEmail = (EditText) findViewById(R.id.editText_mail_register);
        inputPasswordOnce = (EditText) findViewById(R.id.editText_password_register);
        inputPasswordAgain = (EditText) findViewById(R.id.editText_password_again_register);
        loadingBar = new ProgressDialog(this );

        signUpRegisterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccount();
            }
        });
    }

    private void RegisterAccount() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String passwordOne = inputPasswordOnce.getText().toString();
        String passwordTwo = inputPasswordAgain.getText().toString();

        if( TextUtils.isEmpty( name ) ){
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty( email ) ){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty( passwordOne ) ){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty( passwordTwo ) ){
            Toast.makeText(this, "Please enter password again!", Toast.LENGTH_SHORT).show();
        }
        else if( !passwordOne.equals( passwordTwo ) ){
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle( "sign up");
            loadingBar.setMessage( "Please wait while we check your credentials!");
            loadingBar.setCanceledOnTouchOutside( false );
            loadingBar.show();

            ValidateName(name, email, passwordOne, passwordTwo);
            ValidateEmail(name, email, passwordOne, passwordTwo);
        }
    }

    private void ValidateEmail(String name, final String email, String passwordOne, String passwordTwo) {
        // TODO: Check if the email exists in the database.
        // TODO: If exists, return to login screen.
        // TODO: if the email is a bilkent email.
    }

    private void ValidateName(final String name, String email, String passwordOne, String passwordTwo) {
        // TODO: Check if the name exists in the database.
    }
}
