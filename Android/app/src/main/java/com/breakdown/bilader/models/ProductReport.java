package com.breakdown.bilader.models;

public class ProductReport extends Report {

    private Product reported;

    public ProductReport( int reportId, String description, User reporter, Product reported ) {
        super( reportId, description, reporter );
        this.reported = reported;
    }

    @Override
    public void report() {
        // TODO
    }
}
