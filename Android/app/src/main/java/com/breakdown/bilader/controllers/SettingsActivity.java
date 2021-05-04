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
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * This class is responsible for the settings activity in a profile.
 *
 * @author breakDown
 * @version 24.04.2021
 */

public class SettingsActivity extends Activity {
    private Button changePasswordButton;
    private SwitchMaterial followSwitch;
    private SwitchMaterial messageSwitch;
    private SharedPreferences sharedPreferences;

    /**
     * Initializes the UI properties and sets an action to each of them
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle
     *                           contains the data it most recently supplied in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );

        changePasswordButton =
                ( Button ) findViewById( R.id.button_change_password );
        followSwitch =
                ( SwitchMaterial ) findViewById( R.id.switch_follow_notifications );
        messageSwitch =
                ( SwitchMaterial ) findViewById( R.id.switch_message_notifications );

        changePasswordButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( SettingsActivity.this,
                        PrivateChatActivity.class );
                startActivity( intent );
            }
        } );

        /**
         * Sets an on change listener so that when the follow notifications button
         * is pressed, the switch changes.
         */
        followSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView,
                                          boolean isChecked ) {
                sharedPreferences.edit().putBoolean( "FOLLOW_NOTIFICATIONS", isChecked );

            }
        } );

        /**
         * Sets an on change listener so that when the message notifications button
         * is pressed, the switch changes.
         */
        messageSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView,
                                          boolean isChecked ) {
                sharedPreferences.edit().putBoolean( "MESSAGE_NOTIFICATIONS", isChecked );

            }
        } );
    }

}
