package com.breakdown.bilader.adapters;

public enum RequestType {

    LOGIN( "login.jsp" ), REGISTER( "register.jsp" ),
    PROFILE( "profile.jsp" ), PRODUCT( "product.jsp" ), FOLLOWERS( "followers"
            + ".jsp" ), FOLLOWINGS( "followings.jsp" ), FOLLOW( "follow.jsp" );

    private String path;

    /**
     * Constructor
     * @param path String value of the URL path to request.
     */
    RequestType( String path ) {
        this.path = path;
    }

    /**
     * Get the path for current enum.
     * @return String value of the URL path to request.
     */
    public String getPath() {
        return path;
    }

}
