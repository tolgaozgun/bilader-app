package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.breakdown.bilader.R;
import com.breakdown.bilader.fragments.MyProfileFragment;

public class SettingsActivity extends Activity {
    private Button changePasswordButton;
    private Switch followSwitch;
    private Switch messageSwitch;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        changePasswordButton =
                ( Button ) findViewById( R.id.button_change_password );
        followSwitch =
                ( Switch ) findViewById( R.id.switch_follow_notifications );
        messageSwitch =
                ( Switch ) findViewById( R.id.switch_message_notifications );

        changePasswordButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( SettingsActivity.this,
                        ForgotPasswordActivity.class );
                startActivity( intent );
            }
        } );

        followSwitch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                // TODO
            }
        } );

        messageSwitch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                // TODO
            }
        } );
    }

}
