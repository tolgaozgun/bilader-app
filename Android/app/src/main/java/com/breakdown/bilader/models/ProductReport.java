package com.breakdown.bilader.models;

<<<<<<< Updated upstream
public class ProductReport {
=======
public class ProductReport extends Report {

    private Product reported;

    /**
     * Constructor
     * @param reportId Integer value of report id.
     * @param description String value of description.
     * @param reporter User instance of reporter.
     * @param reported Product instance of reported product.
     */
    public ProductReport(int reportId, String description, User reporter,
                         Product reported) {
        super(reportId, description, reporter);
        this.reported = reported;
    }


    @Override
    public void report() {
        // TODO: Implement a report method that sends the report to the server.
        // Using the connection class we will implement (!!)
    }
>>>>>>> Stashed changes
}
