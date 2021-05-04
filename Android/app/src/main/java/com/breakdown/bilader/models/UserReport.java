package com.breakdown.bilader.models;

import android.app.Activity;

/**
 * A class that extends report class to be used reports of users. Its report type is specified to 0.
 * @author breakDown
 * @version 19.04.2021
 */

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
