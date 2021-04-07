package com.breakdown.bilader.models;

public class UserReport extends Report {
    private String description;
    private User reporter;
    private User reported;

    public UserReport( int reportId, String description, User reporter, User reported ) {
        super( reportId, description, reporter );
        this.reported = reported;
    }

    @Override
    public void report() {
        // TODO
    }
}
