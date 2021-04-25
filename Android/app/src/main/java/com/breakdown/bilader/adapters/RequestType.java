package com.breakdown.bilader.adapters;

public enum RequestType {

    LOGIN( "LoginRequest" ), REGISTER( "RegisterRequest" ), PROFILE(
            "ProfileRequest" ), PRODUCT( "ProductRequest" ), FOLLOWERS(
                    "FollowersRequest" ), FOLLOWINGS( "FollowingRequest" ),
    FOLLOW( "FollowRequest" ), REPORT( "AddReportRequest" ),
    FORGOT_PASSWORD( "ForgotPasswordRequest" ), CHANGE_PASSWORD(
            "ChangePasswordRequest" ), NOTIFICATION( "NotificationRequest" ),
    SEND_MESSAGE( "SendMessageRequest" ), RETRIEVE_MESSAGES(
            "RetrieveMessagesRequest" ), TOKEN( "TokenRequest" ), WISHLIST(
                    "WishlistRequest" ), ADD_PRODUCT( "AddProductRequest" ),
    ADD_WISHLIST( "AddWishlistRequest" ), CHECK_WISHLIST(
            "CheckWishlistRequest" );

    private String path;

    /**
     * Constructor
     *
     * @param path String value of the URL path to request.
     */
    RequestType( String path ) {
        this.path = path;
    }

    /**
     * Get the path for current enum.
     *
     * @return String value of the URL path to request.
     */
    public String getPath() {
        return path;
    }

}
