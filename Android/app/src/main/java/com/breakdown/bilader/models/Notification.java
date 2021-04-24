package com.breakdown.bilader.models;

public class Notification {

    private String text;
    //private long time;
    private String avatarUrl;
    //private Context context;

    public Notification( String avatarUrl, String text) {
        this.avatarUrl = avatarUrl;
        this.text = text;

    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getText() {
        return text;
    }

}
