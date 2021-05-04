package com.breakdown.bilader.models;

import android.content.Context;

import androidx.annotation.NonNull;

import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A class that helps us to create category objects to enable products have
 * categories
 *
 * @author Korhan Kemal Kaya
 * @version 13.04.2021
 */
public class Category implements Comparable {

    private String name;
    private int id;

    /**
     * Constructor
     *
     * @param id The category id as integer
     */
    public Category( int id, Context context ) {
        this.id = id;
        retrieveCategory( context );
    }

    public Category( int id, String name ) {
        this.id = id;
        this.name = name;
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
    public int getId() {
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
     */ public String toString() {
        return this.name;
    }

    /**
     * Retrieves category objects by working with context
     *
     * @param context, required context to retrieve categories
     */
    private void retrieveCategory( Context context ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "category_id", String.valueOf( id ) );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        name = object.getJSONObject( "categories" ).getJSONObject( "0" ).getString( "name" );
                    } else {
                        name = "ERROR";
                    }
                } catch ( JSONException e ) {
                    name = "ERROR";
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                name = "ERROR";
            }
        }, RequestType.CATEGORIES, params, context, true );

    }


}
