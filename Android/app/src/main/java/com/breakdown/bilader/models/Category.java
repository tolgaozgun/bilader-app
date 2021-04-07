package com.breakdown.bilader.models;

public class Category {
    String name;
    int id;

    public Category( String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }
}
