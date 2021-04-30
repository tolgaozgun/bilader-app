package com.breakdown.bilader.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents product lists to display, retrieve and manipulate it
 * @author breakDown
 * @version 18.04.2021
 */
public class ProductList {

    private List< Product > products;

    /**
     * Constructor that initializes the list.
     */
    public ProductList() {
        this.products = new ArrayList< Product >();
    }

    /**
     * Adds the given product to list.
     *
     * @param product Product instance of product to be added.
     */
    public void addProduct( Product product ) {
        products.add( product );
    }

    /**
     * Checks if the given product exists in the list.
     *
     * @param product Product instance of product to be checked.
     * @return Boolean whether it exists.
     */
    public boolean doesExist( Product product ) {
        return products.contains( product );
    }

    /**
     * Returns a list of products that match the searching parameters. TODO: Add
     * parameters here.
     *
     * @return List instance that contains Product
     */
    public List< Product > searchProduct() {
        // TODO: Implement a class and method that searches for a value in a
        // list. Then implement a interface that helps you use that universal
        // method. Make the method usable by many instances, not just specific
        // to this search type.
        return null;
    }

    /**
     * Removes a product from the list if it exists.
     *
     * @param product Product instance to be removed.
     * @return Boolean whether it was successful to remove.
     */
    public boolean removeProduct( Product product ) {
        if ( doesExist( product ) ) {
            products.remove( product );
            return true;
        }
        return false;
    }

    /**
     * Load products into the product list.
     */
    public void retrieveProducts() {
        products.clear();
        /*TODO: Load products from website to List<Product> products
         * Maybe we can do this in an another class and rename this class
         * to loadProducts(List<Product> productList) so that we can
         * reuse the loading products from website method in an another class
         * if needed???
         * */
    }
}
