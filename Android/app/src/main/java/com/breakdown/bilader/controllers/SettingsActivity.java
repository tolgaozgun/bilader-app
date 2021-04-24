package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );


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

        followSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView,
                                          boolean isChecked ) {
                sharedPreferences.edit().putBoolean( "FOLLOW_NOTIFICATIONS", isChecked );

            }
        } );

        messageSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView,
                                          boolean isChecked ) {
                sharedPreferences.edit().putBoolean( "MESSAGE_NOTIFICATIONS", isChecked );

            }
        } );
    }

}
