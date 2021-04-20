package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends Activity {
    private EditText reportText;
    private Button submitButton;
    private ProgressDialog loadingBar;
    private final String SESSION_TOKEN_KEY = "SESSION_TOKEN";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_report );

        reportText =
                ( EditText ) findViewById( R.id.editText_report_typing_place );
        submitButton = ( Button ) findViewById( R.id.button_report_submit );

        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendReport();
            }
        } );
    }

    private void sendReport() {
        String reportDesc;
        String reportedId;
        int reportType;
        Intent intent;
        Map< String, String > params;

        params = new HashMap< String, String >();
        intent = getIntent();

        reportedId = intent.getStringExtra( "id" );
        reportType = intent.getIntExtra( "report-type", -1 );
        reportDesc = reportText.getText().toString();


        params.put( "reportType", String.valueOf( reportType ) );
        params.put( "reported_id", reportedId );
        params.put( "description", reportDesc );
        // reportedId =

        loadingBar.setTitle( "Report" );
        loadingBar.setMessage( "Please wait while we send the report!" );
        loadingBar.setCanceledOnTouchOutside( false );
        loadingBar.show();

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( ReportActivity.this, object.getString(
                            "message" ), Toast.LENGTH_SHORT ).show();
                    if ( object.getBoolean( "success" ) ) {
                        ReportActivity.this.finish();
                    }
                } catch ( JSONException e ) {
                    Toast.makeText( ReportActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( ReportActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
            }
        }, RequestType.REPORT, params, this );

    }


}
