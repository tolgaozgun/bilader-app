package com.breakdown.bilader.models;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.controllers.ReportActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents report objects, which has what is written in text(description), id, and report type( will be specified
 *                                                                                                            if user = 0, else if product = 1)
 * @author breakDown
 * @version 19.04.2021
 */

public abstract class Report {

    protected String description;
    protected String reportedId;
    protected int reportType;

    /**
     * Constructor
     *
     * @param description String value of description.
     */
    public Report( String description, String reportedId ) {
        this.description = description;
        this.reportedId = reportedId;
    }

    /**
     * Report method that sends server the request to report a content.
     */
    public void report( Activity activity ) {

        Map< String, String > params;

        params = new HashMap< String, String >();

        params.put( "report_type", String.valueOf( reportType ) );
        params.put( "reported_id", reportedId );
        params.put( "description", description );
        /*
        loadingBar = new ProgressDialog( this );
        loadingBar.setTitle( "Report" );
        loadingBar.setMessage( "Please wait while we send the report!" );
        loadingBar.setCanceledOnTouchOutside( false );
        loadingBar.show();*/

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( activity, object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                    if ( object.getBoolean( "success" ) ) {
                        activity.finish();
                    }
                } catch ( JSONException e ) {
                    Toast.makeText( activity, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( activity, message, Toast.LENGTH_SHORT ).show();
            }
        }, RequestType.REPORT, params, activity, true );
    }
}
