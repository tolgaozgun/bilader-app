package com.breakdown.bilader.models;

public class Product {

    private String picture;
    private String title;
    private String category;
    private String description;
    private double price;
    private User seller;
    private Category category;
    private List<Report> repors;

    public Product(String picture, String title, String category, String description, double price, User seller, Category category1, List<Report> repors) {
        this.picture = picture;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category1;
        this.repors = repors;
    }

    public User getOwner(){
        return this.seller;
    }

    public boolean editProduct {
        //ToDo
    }

    public boolean removeProduct(){
        //ToDo
    }

    public boolean addToWishlist(){
        //ToDo
    }
}
