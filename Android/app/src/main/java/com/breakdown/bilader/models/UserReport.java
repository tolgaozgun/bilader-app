package com.breakdown.bilader.models;

import android.app.Activity;

public class UserReport extends Report {


    /**
     * Constructor
     *
     * @param description String value of description.
     * @param reportedId  String value of reported user ID.
     */
    public UserReport( String description, String reportedId ) {
        super( description, reportedId );
        reportType = 0;
    }

    /**
     * @see Report
     */
    public void report( Activity activity ) {
        super.report( activity );
    }
}
