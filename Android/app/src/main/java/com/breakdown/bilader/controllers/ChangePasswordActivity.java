package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.raycoarana.codeinputview.CodeInputView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends Activity {

    CodeInputView codeView;
    EditText emailText;
    EditText passwordOneView;
    EditText passwordTwoView;
    Button resetButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_changepassword );

        Intent intent;
        String email;
        intent = getIntent();

        codeView =
                ( CodeInputView ) findViewById( R.id.code_reset_password_pin_code );

        emailText = findViewById( R.id.editText_reset_password_email );
        passwordOneView = findViewById( R.id.editText_reset_password_password );
        passwordTwoView =
                findViewById( R.id.editText_reset_password_password_again );
        resetButton = findViewById( R.id.button_reset_password );


        if ( intent != null && intent.hasExtra( "email" ) ) {
            email = intent.getStringExtra( "email" );
            emailText.setText( email );
        }

        resetButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendRequest();
            }
        } );
    }

    private void sendRequest() {
        Map< String, String > params;
        String email;
        String password;
        String passwordTwo;
        String code;

        email = emailText.getText().toString();
        password = passwordOneView.getText().toString();
        passwordTwo = passwordTwoView.getText().toString();
        code = codeView.getCode();


        if ( email == null || email == "" ) {
            Toast.makeText( this, "Please enter mail!", Toast.LENGTH_SHORT ).show();

        }

        if ( password == null || password == "" ) {
            Toast.makeText( this, "Please enter password!",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        if ( code == null || code == "" ) {
            Toast.makeText( this, "Please enter code!", Toast.LENGTH_SHORT ).show();
            return;
        }

        if ( code.length() < 6 ) {
            Toast.makeText( this, "Code cannot be less than 6 digits!",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        if ( !password.equals( passwordTwo ) ) {
            Toast.makeText( this, "Passwords do not match!",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        params = new HashMap< String, String >();
        params.put( "email", email );
        params.put( "password", password );
        params.put( "code", code );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Intent intent;
                try {
                    Toast.makeText( ChangePasswordActivity.this,
                            object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                    if ( object.getBoolean( "success" ) ) {
                        intent = new Intent( ChangePasswordActivity.this,
                                LoginActivity.class );
                        intent.putExtra( "email", email );
                        startActivity( intent );
                    } else {
                        codeView.setEditable( true );
                        codeView.setError( object.getString( "message" ) );
                    }
                } catch ( JSONException e ) {
                    codeView.setEditable( true );
                    codeView.setError( e.getMessage() );
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( ChangePasswordActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
            }
        }, RequestType.CHANGE_PASSWORD, params, ChangePasswordActivity.this, false );

    }
}
