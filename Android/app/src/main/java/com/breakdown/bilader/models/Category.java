package com.breakdown.bilader.models;

import androidx.annotation.NonNull;

public class Category implements Comparable {

    private String name;
    private String id;
    /**
     * Constructor
     *
     * @param id   The category id as integer
     */
    public Category (String id ) {
        if (id.equals("0") ) {
            this.id = id;
            this.name = "Book";
        }
        else if ( id.equals("1") ) {
            this.id = id;
            this.name = "Electronic";
        }
        else if ( id.equals("2") ) {
            this.id = id;
            this.name = "Clothes";
        }
        else if ( id.equals("3")) {
            this.id = id;
            this.name = "Hoby";
        }
        else  {
            this.id = id;
            this.name = "Others";
        }
    }

    /**
     * Returns the name of the current category.
     *
     * @return String name of the current category.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the current category.
     *
     * @return Integer id of the current category.
     */
    public String getId() {
        return id;
    }

    /**
     * Takes a Category instance as a parameter and compares its name with
     * current Category instance's name.
     *
     * @param o Another Category object
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     * @throws ClassCastException if given parameter is not type Category
     */
    @Override
    public int compareTo( Object o ) {
        Category other;
        if ( o instanceof Category ) {
            other = ( Category ) o;
            return other.name.compareTo( this.name );
        }
        throw new ClassCastException();
    }

    @NonNull
    @Override
    /**
     * Returns category's name to identify category.
     */
    public String toString() {
        return this.name;
    }
}
