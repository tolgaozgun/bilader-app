package com.breakdown.bilader.models;

/**
 * A class that represents for a product, contain its properties and can change those properties
 * @author breakDown
 * @version 13.04.2021
 */
public class Product {

    private String picture;
    private String title;
    private String description;
    private double price;
    private User seller;
    private Category category;
    private boolean isSold;
    private String productId;

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
     * Gives the picture URL of the product
     *
     * @return String value of the picture URL.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Gives the title of the product
     *
     * @return String value of the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gives the user selling the product
     *
     * @return User (Object) value of the seller.
     */
    public User getSeller() {
        return seller;
    }

    /**
     * Gives the price of the product
     *
     * @return Double value of the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gives the description of the product
     *
     * @return String value of the description.
     */
    public String getDescription() { return this.description; }

    /**
     * Gives the category of the product
     *
     * @return Category (Object) value of the category.
     */
    public Category getCategory() { return this.category; }

    /**
     * Shows if the product is sold or not
     *
     * @return boolean value of status of saleability
     */
    public boolean getIsSold() { return isSold; }

    /**
     * Gives the id of the product
     *
     * @return String value of the id.
     */
    public String getProductId() { return productId; }


    /**
     * Changes the saleability status of the product
     *
     * @return boolean value of the reverse saleability status of the product.
     */
    public boolean changeSoldSituation() {
        isSold = !isSold; //I think we may use is sold in somewhere in future
        // so I didnt directly return !isSold.
        return isSold;
    }

    /**
     * Sets the picture URL of the product
     *
     * @param picture  String value of the picture URL.
     */
    public void setPicture( String picture ) {
        this.picture = picture;
    }

    /**
     * Sets the title of the product
     *
     * @param title  String value of the title.
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * Sets the description of the product
     *
     * @param description  String value of the description.
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     * Sets the price of the product
     *
     * @param price  double value of the price.
     */
    public void setPrice( double price ) {
        this.price = price;
    }

    /**
     * Sets the category of the product
     *
     * @param category  Category (Object) value of the category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }


}
