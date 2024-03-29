package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.raycoarana.codeinputview.CodeInputView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for sending a verification email to the user that
 * wants to register We used an external library for six-digit verification
 * code. The necessary commands and external library's link is below:
 * https://github.com/raycoarana/material-code-input
 *
 * @author breakDown
 * @version 13.04.2021
 */
public class VerificationActivity extends Activity {

    private CodeInputView codeView;
    private Button resetButton;
    private String email;

    // send - resend button
    private Button resendButton;

    /**
     * this is the method where the initialization of UI properties made and set
     * an action to each of them
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verification );

        Intent intent;
        intent = getIntent();
        email = intent.getStringExtra( "email" );

        // resend button here
        resendButton = findViewById( R.id.button_verification_code_resend );

        codeView = ( CodeInputView ) findViewById( R.id.codeInputView );
        resetButton = findViewById( R.id.button_verification_submit );


        resetButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendRequest();
            }
        } );
    }

    /**
     * This method send request for users to enter the six digit-verification
     * code and checks if the code is correct
     */
    private void sendRequest() {
        Map< String, String > params;
        String code;
        code = codeView.getCode();

        if ( code == null || code == "" ) {
            Toast.makeText( this, "Please enter code!", Toast.LENGTH_SHORT ).show();
            return;
        }

        if ( code.length() < 6 ) {
            Toast.makeText( this, "Code cannot be less than 6 digits!",
                    Toast.LENGTH_SHORT ).show();
            return;
        }

        params = new HashMap< String, String >();
        params.put( "email", email );
        params.put( "code", code );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Intent intent;
                try {
                    Toast.makeText( VerificationActivity.this,
                            object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                    if ( object.getBoolean( "success" ) ) {
                        intent = new Intent( VerificationActivity.this,
                                LoginActivity.class );
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
                Toast.makeText( VerificationActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
            }
        }, RequestType.VERIFICATION, params, VerificationActivity.this, false );

    }
}