package com.breakdown.bilader.models;

import android.app.Activity;

public class ProductReport extends Report {


    /**
     * Constructor
     *
     * @param description String value of description.
     * @param reportedId  String value of reported product ID.
     */
    public ProductReport( String description, String reportedId ) {
        super( description, reportedId );
        reportType = 1;
    }

    /**
     * @see Report
     */
    public void report( Activity activity ) {
        super.report( activity );
    }
}
