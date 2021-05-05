package com.breakdown.bilader.models;

/**
 * A class that represents review objects, reviews will be comments for users
 * written by other users who traded or chatted with the user. This class mainly
 * has review id, content of review and the owner of review.
 *
 * @author breakDown
 * @version 19.04.2021
 */

public class Review implements Comparable {

    private String id;
    private User sentBy;
    private String content;
    private long time;

    /**
     * Constructor for User class
     *
     * @param sentBy  User (Object) value of the writer of review.
     * @param content String value of the content of the review
     */
    public Review( String id, User sentBy, String content, long time ) {
        this.sentBy = sentBy;
        this.content = content;
        this.id = id;
        this.time = time;
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


    /**
     * Returns the review time
     *
     * @return Long value of review time (in Epoch).
     */
    public long getTime() {
        return time;
    }


    @Override
    public int compareTo( Object o ) {
        if ( o instanceof Review ) {
            Long currentReviewTime = new Long( time );
            return currentReviewTime.compareTo( ( ( Review ) o ).getTime() );
        }
        return 0;
    }
}
