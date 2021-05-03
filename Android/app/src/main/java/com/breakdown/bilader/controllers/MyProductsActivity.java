package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 * A class that shows the products that the current user has added before as a list.
 *
 * @author breakDown
 * @version 29.04.2021
 */
public class MyProductsActivity extends Activity {
    private RecyclerView recyclerView;
    private ArrayList< Product > productList;
    private ProductAdapter adapter;
    private SharedPreferences sharedPreferences;

    /**
     * this is the method where the initialization of UI properties made and
     * set an action to each of them
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_myproducts );

        recyclerView = findViewById( R.id.myProductsRecycler );
        recyclerView.setHasFixedSize( true );

        recyclerView.setLayoutManager( new StaggeredGridLayoutManager( 2,
                StaggeredGridLayoutManager.VERTICAL ) );

        getProductList( recyclerView );

        adapter = new ProductAdapter( this, productList );
        recyclerView.setAdapter( adapter );

    }

    /**
     * Gets the each product and its properties that the current
     * user has in a list form
     *
     * @param recyclerView is RecyclerView that holds the products
     */
    private void getProductList( RecyclerView recyclerView ) {
        String userId;
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );
        userId = sharedPreferences.getString( "id", "" );
        params.put( "seller_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Product product;
                int price;
                int categoryId;
                String pictureUrl;
                String sellerName;
                String description;
                String productId;
                String productTitle;
                String sellerId;
                String sellerAvatarURL;
                User seller;

                Iterator< String > keys;
                JSONObject tempJson;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        productList = new ArrayList< Product >();
                        keys = object.getJSONObject( "result" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "result" ).getJSONObject( key );

                            price = tempJson.getInt( "price" );
                            pictureUrl = tempJson.getString( "picture_url" );
                            sellerName = tempJson.getString( "seller_name" );
                            description = tempJson.getString( "description" );
                            productId = tempJson.getString( "id" );
                            productTitle = tempJson.getString( "title" );
                            sellerId = tempJson.getString( "seller_id" );
                            sellerAvatarURL = tempJson.getString(
                                    "seller_avatar_url" );
                            categoryId = tempJson.getInt( "category_id" );
                            seller = new User( sellerName, sellerAvatarURL,
                                    sellerId );
                            product = new Product( pictureUrl, productTitle,
                                    description, price, seller, false,
                                    productId, new Category( categoryId, MyProductsActivity.this ) );
                            productList.add( product );
                        }
                    }
                    printView( recyclerView );
                } catch ( JSONException e ) {
                    e.printStackTrace();
                    printView( recyclerView );
                }
            }

            @Override
            public void onFail( String message ) {
                printView( recyclerView );
            }
        }, RequestType.PRODUCT, params, this ,true);


    }

    /**
     * Initializes the ProductAdapter and set it to the entered recyclerView
     *
     * @param recyclerView is RecyclerView that holds the products
     */
    private void printView( RecyclerView recyclerView ) {
        adapter = new ProductAdapter( this, productList );
        recyclerView.setAdapter( adapter );
    }

}
