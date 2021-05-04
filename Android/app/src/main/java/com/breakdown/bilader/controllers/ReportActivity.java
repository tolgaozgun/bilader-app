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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.ProductReport;
import com.breakdown.bilader.models.Report;
import com.breakdown.bilader.models.UserReport;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for the report activity when a product or a user
 * are reported.
 *
 * @author Deniz Gökçen
 * @author Tolga Özgün
 * @version 13.04.2021
 */

public class ReportActivity extends Activity {
    private EditText reportText;
    private TextView title;
    private ImageView imageView;
    private Button submitButton;
    private ProgressDialog loadingBar;
    private String reportDesc;
    private String reportedId;
    private String reportTitle;
    private String reportImage;
    private Intent intent;
    private Report report;
    private boolean reportType;
    private final String SESSION_TOKEN_KEY = "SESSION_TOKEN";

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
        setContentView( R.layout.activity_report );

        intent = getIntent();

        reportText =
                ( EditText ) findViewById( R.id.editText_report_typing_place );
        submitButton = ( Button ) findViewById( R.id.button_report_submit );
        title = findViewById( R.id.text_report_user_name );
        imageView = findViewById( R.id.image_report_avatar );

        reportedId = intent.getStringExtra( "id" );
        reportTitle = intent.getStringExtra( "title" );
        reportImage = intent.getStringExtra( "image_url" );
        reportType = intent.getBooleanExtra( "report-type", true );

        title.setText( reportTitle );

        Picasso.get().load( reportImage ).fit().centerInside().into( imageView );

        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendReport();
            }
        } );
    }

    /**
     * Sends the report in the edit text section.
     */
    private void sendReport() {
        Map< String, String > params;

        params = new HashMap< String, String >();
        reportDesc = reportText.getText().toString();

        params.put( "report_type", String.valueOf( reportType ) );
        params.put( "reported_id", reportedId );
        params.put( "description", reportDesc );

        // user report --> 0 / false
        // product report --> 1 / true
        if ( reportType ) {
            report = new ProductReport( reportDesc, reportedId );
        }else{
            report = new UserReport( reportDesc, reportedId );
        }

        report.report( this );
    }


}
