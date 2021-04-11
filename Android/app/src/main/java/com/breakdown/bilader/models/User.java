package com.breakdown.bilader.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String eMail;
    private String userAvatar;
    private ProductList userWishlist;
    private List< User > userFollowers;
    private List< User > userFollowings;
    private List< Product > userProducts;

    /**
     * Constructor for User class
     *
     * @param userName   String value of username.
     * @param eMail      String value of email address.
     * @param userAvatar String value of avatar URL.
     */
    public User( String userName, String eMail, String userAvatar ) {
        this.userName = userName;
        this.eMail = eMail;
        this.userAvatar = userAvatar;
        userWishlist = new ProductList();
        userFollowers = new ArrayList< User >();
        userFollowings = new ArrayList< User >();
        userProducts = new ArrayList< Product >();
    }

    /**
     * Returns the username of the current user
     *
     * @return String value of username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the email of the current user.
     *
     * @return String value of email address.
     */
    public String getEmail() {
        return eMail;
    }

    /**
     * Returns the avatar URL of the current user
     *
     * @return String value of avatar URL.
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * Returns the wishlist of the current user.
     *
     * @return ProductList instance of wishlist.
     */
    public ProductList getWishlist() {
        return userWishlist;
    }

    /**
     * Adds current user to the following people list for the user in the
     * parameter. The person who did the action is provided in the parameter.
     *
     * @param user User instance of person who followed.
     * @return Boolean whether the operation was successful.
     */
    public boolean addToFollowings( User user ) {
        // TODO: Change the user to something like sessionID, we want this
        // client to send messages only for themselves not on behalf of
        // someone else.
        //TODO check if this method returns true in the method this is called
        //from so that we can display a success or fail message.
        return false;
    }
}
