package com.breakdown.bilader.models;

public class Category implements Comparable {
    String name;
    int id;

    private String name;
    private int id;

    /**
     * Constructor
     * @param name The category name as String
     * @param id The category id as integer
     */
    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Returns the name of the current category.
     * @return String name of the current category.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the current category.
     * @return Integer id of the current category.
     */
    public int getId() {
        return id;
    }

    /**
     * Takes a Category instance as a parameter and compares its name with
     * current Category instance's name.
     *
     * @param o Another Category object
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if given parameter is not type Category
     */
    @Override
    public int compareTo(Object o) {
        Category other;
        if (o instanceof Category) {
            other = (Category) o;
            return other.name.compareTo(this.name);
        }
        throw new ClassCastException();
    }
}
