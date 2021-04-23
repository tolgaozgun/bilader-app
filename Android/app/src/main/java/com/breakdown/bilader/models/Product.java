package com.breakdown.bilader.models;

public class Product {

    private String picture;
    private String title;
    private String description;
    private double price;
    private User seller;
    private Category category;
    private boolean isSold;
    private String productId;
    // CATEGORY IS ERASED



    public Product( User seller, boolean isSold, String productId ) {
        this.isSold = false;
        this.seller = seller;
        this.productId = productId;
    }

    /**
     * Constructor
     *
     * @param picture     String value of picture URL.
     * @param title       String value of title.
     * @param description String value of description.
     * @param price       double value of price.
     * @param seller      User instance of seller.
     * @param isSold      whether product is sold.
     */
    public Product( String picture, String title, String description,
                    double price, User seller, boolean isSold, String productId, Category category ) {
        this.picture = picture;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = null;
        this.isSold = false;
        this.productId = productId;
        this.category = category;
    }

    /**
     * Returns the User instance of seller.
     *
     * @return User instance of seller
     */
    public User getOwner() {
        return seller;
    }

    /**
     * Sends a request to add this product to the user's wishlist.
     *
     * @return Boolean whether the operation was successful.
     */
    public boolean addToWishlist( User user ) {
        // TODO: Change the user to something like sessionID, we want this
        // client to send messages only for themselves not on behalf of
        // someone else.
        return false;
    }

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public User getSeller() {
        return seller;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() { return this.description; }

    public Category getCategory() { return this.category; }

    public boolean getIsSold() { return isSold; }

    public String getProductId() { return productId; }


    public boolean changeSoldSituation() {
        isSold = !isSold; //I think we may use is sold in somewhere in future
        // so I didnt directly return !isSold.
        return isSold;
    }

    public void setPicture( String picture ) {
        this.picture = picture;
    }


    public void setTitle( String title ) {
        this.title = title;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setPrice( double price ) {
        this.price = price;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


}
