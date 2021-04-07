package com.breakdown.bilader.models;

public abstract class Report {

    private String description;
    private User reporter;
    private int reportId;

    public Report( int reportId, String description, User reporter ) {
        this.reportId = reportId;
        this.description = description;
        this.reporter = reporter;
    }

    public abstract void report();
}
