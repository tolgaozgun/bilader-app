package com.breakdown.bilader.models;

public abstract class Report {

    private String description;
    private User reporter;
    private int reportId;

    /**
     * Constructor
     *
     * @param reportId    Integer value of report id.
     * @param description String value of description.
     * @param reporter    User instance of reporter.
     */
    public Report( int reportId, String description, User reporter ) {
        this.reportId = reportId;
        this.description = description;
        this.reporter = reporter;
    }

    /**
     * Report method that sends server the request to report a content.
     */
    public abstract void report();
}
