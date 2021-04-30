package com.breakdown.bilader.models;

import androidx.annotation.NonNull;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the user,sets its properties and when needed manipulate it.
 * @author breakDown
 * @version 14.04.2021
 */
public class User implements Serializable, IUser {

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
    @Override
    public String getName() {
        return userName;
    }

    /**
     * Returns the avatar URL of the current user
     *
     * @return String value of avatar URL.
     */
    @Override
    public String getAvatar() {
        return userAvatar;
    }

    /**
     * Returns the id number of the current user
     *
     * @return String value of the user id
     */
    @Override
    public String getId() { return userId; }

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
