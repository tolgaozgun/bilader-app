package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.breakdown.bilader.R;

public class ForgotPasswordActivity extends Activity {
    private EditText inputEmail;
    private Button sendCodeButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forgotpassword );

        sendCodeButton = (Button) findViewById( R.id.button_forgot_password_button );
        inputEmail = (EditText) findViewById( R.id.editText_forgot_password_mail );

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String email;

                email = inputEmail.getText().toString();

                if( TextUtils.isEmpty( email ) ){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();
                }
                else{
                    // TODO: Send verification code to the email of the user, and direct the user to Change Password Activity.
                }
            }
        });
    }
}
