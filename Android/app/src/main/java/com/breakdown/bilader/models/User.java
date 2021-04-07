package com.breakdown.bilader.models;

import java.util.List;

public class User {
    String userName;
    String eMail;
    String userAvatar;
    ProductList userWishlist;
    List<User> userFollowers;
    List<User> userFollowings;
    List<Product> userProducts;

    public User( String userName, String eMail, String userAvatar){
        this.userName = userName;
        this.eMail = eMail;
        this.userAvatar = userAvatar;

        userWishlist = new ProductList();

        userFollowers = new List<User>;
        userFollowings = new List<User>;

        userProducts = new List<Product>;

    }

    public String getUserName(){
        return userName;
    }

    public String geteMail()
    {
        return eMail;
    }

    public String getUserAvatar()
    {
        return userAvatar;
    }

    public ProductList getWishlist()
    {
        return userWishlist;
    }

    public void addToFollowings( User user)
    {
        //TODO
    }


}
