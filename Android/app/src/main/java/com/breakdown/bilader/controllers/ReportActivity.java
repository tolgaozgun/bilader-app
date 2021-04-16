package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;

public class ReportActivity extends Activity {
    private EditText reportText;
    private Button submitButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_report );

        reportText = ( EditText ) findViewById( R.id.editText_report_typing_place );
        submitButton = ( Button ) findViewById( R.id.button_report_submit );

        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendReport();
            }
        } );
    }

    private void sendReport() {
        String report;
        String userId;

        report = reportText.getText().toString();
        // userId =

        // TODO: Send the report to us.

        loadingBar.setTitle( "report" );
        loadingBar.setMessage( "Please wait while we send the report!" );
        loadingBar.setCanceledOnTouchOutside( false );
        loadingBar.show();
    }
}
