package com.breakdown.bilader.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String userName;
    private String userAvatar;
    private String userId;
    private ProductList userWishlist;

    /**
     * Constructor for User class
     *
     * @param userName   String value of username.
     * @param userAvatar String value of avatar URL.
     * @param userId     String value of user id.
     */
    public User( String userName, String userAvatar, String userId ) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userId = userId;

        userWishlist = new ProductList();
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
     * Returns the avatar URL of the current user
     *
     * @return String value of avatar URL.
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * Returns the id number of the current user
     *
     * @return String value of the user id
     */
    public String getUserId() { return userId; }

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

    @NonNull
    @Override
    /**
     * Returns user's name to identify user.
     */
    public String toString() {
        return userName;
    }
}
