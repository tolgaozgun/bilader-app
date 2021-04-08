package com.breakdown.bilader.models;

public class Product {

    private String picture;
    private String title;
    private String description;
    private double price;
    private User seller;
    private Category category;

    /**
     * Constructor
     * @param picture String value of picture URL.
     * @param title String value of title.
     * @param description String value of description.
     * @param price double value of price.
     * @param seller User instance of seller.
     * @param category Category instance of this product's category.
     */
    public Product(String picture, String title, String description,
                   double price, User seller, Category category) {
        this.picture = picture;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }

    /**
     * Returns the User instance of seller.
     * @return User instance of seller
     */
    public User getOwner() {
        return seller;
    }

    /**
     * Sends a request to add this product to the user's wishlist.
     * @return Boolean whether the operation was successful.
     */
    public boolean addToWishlist(User user) {
        // TODO: Change the user to something like sessionID, we want this
        // client to send messages only for themselves not on behalf of
        // someone else.
        return false;
    }
}
