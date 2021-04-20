package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends Activity {
    private EditText inputEmail;
    private Button sendCodeButton;
    private TextView alreadyRecievedText;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forgotpassword );

        sendCodeButton =
                ( Button ) findViewById( R.id.button_forgot_password_button );
        inputEmail =
                ( EditText ) findViewById( R.id.editText_forgot_password_mail );

        alreadyRecievedText =
                ( TextView ) findViewById( R.id.text_already_received );

        alreadyRecievedText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                startActivity( intent );
            }
        } );

        sendCodeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String email;
                Map< String, String > params;

                email = inputEmail.getText().toString();

                if ( TextUtils.isEmpty( email ) ) {
                    Toast.makeText( ForgotPasswordActivity.this, "Please " +
                            "enter your email address!", Toast.LENGTH_SHORT ).show();
                } else {
                    params = new HashMap< String, String >();
                    params.put( "email", email );

                    HttpAdapter.getRequestJSON( new VolleyCallback() {
                        @Override
                        public void onSuccess( JSONObject object ) {
                            Intent intent;
                            intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                            intent.putExtra( "email", email );
                            startActivity( intent );
                            try {
                                Toast.makeText( ForgotPasswordActivity.this,
                                        object.getString( "message" ),
                                        Toast.LENGTH_SHORT ).show();
                            } catch ( JSONException e ) {
                                Toast.makeText( ForgotPasswordActivity.this,
                                        e.getMessage(), Toast.LENGTH_SHORT ).show();
                            }
                        }

                        @Override
                        public void onFail( String message ) {
                            Toast.makeText( ForgotPasswordActivity.this,
                                    message, Toast.LENGTH_SHORT ).show();
                        }
                    }, RequestType.FORGOT_PASSWORD, params,
                            ForgotPasswordActivity.this );
                }
            }
        } );
    }
}
