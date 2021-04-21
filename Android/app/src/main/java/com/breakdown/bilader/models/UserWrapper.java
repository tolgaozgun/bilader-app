package com.breakdown.bilader.models;

import com.stfalcon.chatkit.commons.models.IUser;

public class UserWrapper implements IUser {
    private User user;

    public UserWrapper( User user ) {
        this.user = user;
    }

    @Override
    public String getId() {
        return user.getUserId();
    }

    @Override
    public String getName() {
        return user.getUserName();
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
