package com.breakdown.bilader.models;

<<<<<<< Updated upstream
public class UserReport {
=======
public class UserReport extends Report {

    private User reported;

    /**
     * Constructor
     * @param reportId Integer value of report id.
     * @param description String value of description.
     * @param reporter User instance of reporter.
     * @param reported User instance of reported user.
     */
    public UserReport(int reportId, String description, User reporter, User reported) {
        super(reportId, description, reporter);
        this.reported = reported;
    }

    @Override
    public void report() {
        // TODO
    }
>>>>>>> Stashed changes
}
