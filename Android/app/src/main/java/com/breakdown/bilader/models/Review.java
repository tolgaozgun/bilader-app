package com.breakdown.bilader.models;

/**
 * A class that represents review objects, reviews will be comments for users written by other users who traded or chatted with the user.
 *This class mainly has review id, content of review and the owner of review.
 * @author breakDown
 * @version 19.04.2021
 * */

public class Review {

    private String id;
    private User sentBy;
    private String content;

    /**
     * Constructor for User class
     *
     * @param sentBy  User (Object) value of the writer of review.
     * @param content String value of the content of the review
     */
    public Review( String id, User sentBy, String content ) {
        this.sentBy = sentBy;
        this.content = content;
        this.id = id;
    }

    /**
     * Returns the user writing the review
     *
     * @return User (Object) value of the writer.
     */
    public User getSentBy() {
        return sentBy;
    }

    /**
     * Returns the content of the entered review
     *
     * @return String value of the content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the review id
     *
     * @return String value of review ID.
     */
    public String getId() {
        return id;
    }
}
