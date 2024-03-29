package com.breakdown.bilader.adapters;

public enum RequestType {

    LOGIN( "LoginRequest" ),
    REGISTER( "RegisterRequest" ),
    CATEGORIES( "CategoriesRequest" ),
    PRODUCT( "ProductRequest" ),
    FOLLOWERS( "FollowersRequest" ),
    FOLLOWINGS( "FollowingRequest" ),
    FOLLOWERS_COUNT("FollowersCountRequest"),
    FOLLOWING_COUNT("FollowingCountRequest"),
    FOLLOW( "FollowRequest" ),
    REPORT( "AddReportRequest" ),
    FORGOT_PASSWORD( "ForgotPasswordRequest" ),
    CHANGE_PASSWORD( "ChangePasswordRequest" ),
    NOTIFICATION( "NotificationRequest" ),
    SEND_MESSAGE( "SendMessageRequest" ),
    RETRIEVE_MESSAGES( "RetrieveMessagesRequest" ),
    RETRIEVE_REVIEWS("RetrieveReviewsRequest"),
    EDIT_PRODUCT("EditProductRequest"),
    DELETE_PRODUCT("DeleteProductRequest"),
    ADD_REVIEW("AddReviewRequest"),
    TOKEN( "TokenRequest" ),
    WISHLIST( "WishlistRequest" ),
    ADD_PRODUCT( "AddProductRequest" ),
    ADD_WISHLIST( "AddWishlistRequest" ),
    CHECK_WISHLIST( "CheckWishlistRequest" ),
    REMOVE_WISHLIST( "RemoveWishlistRequest" ),
    MAIN_CHAT("RetrieveChatsRequest"),
    CREATE_CHAT("CreateChatRequest"),
    UPDATE_PHOTO("UpdatePhotoRequest"),
    CHECK_FOLLOWING("IsFollowingRequest"),
    REVIEW_COUNT("ReviewCountRequest"),
    PRODUCT_COUNT("ProductCountRequest"),
    VERIFICATION("VerificationRequest"),
    REMOVE_PRODUCT("RemoveProductRequest"),
    USER("UserRequest");

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
