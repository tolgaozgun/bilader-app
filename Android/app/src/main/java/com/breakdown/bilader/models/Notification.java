package com.breakdown.bilader.models;

/**
 * A class that represents notifications, to be used to notify users when there is message etc.
 * @author breakDown
 * @version 19.04.2021
 */

public class Notification {

    private String text;
    //private long time;
    private String avatarUrl;
    //private Context context;

    /**
     * Constructor
     *
     * @param avatarUrl     String value of the avatar URL.
     * @param text          String value of the notification text.
     */
    public Notification( String avatarUrl, String text) {
        this.avatarUrl = avatarUrl;
        this.text = text;

    }

    /**
     * Returns the String value of the avatar URL.
     *
     * @return String value of avatar URL.
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Returns the String value of the notification text.
     *
     * @return String value of the notification text.
     */
    public String getText() {
        return text;
    }

}
