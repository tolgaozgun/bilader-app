package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class that makes connection between its layout and data
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class WishlistFragment extends Fragment {

    private Activity context;
    private RecyclerView recyclerView;
    private ArrayList< Product > productList;
    private ProductAdapter adapter;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater            is the LayoutInflater object that can be used
     *                            to inflate any views in the fragment
     * @param container:          If non-null, this is the parent view that the
     *                            fragment's UI should be attached to. The
     *                            fragment should not add the view itself, but
     *                            this can be used to generate the LayoutParams
     *                            of the view.
     * @param savedInstanceState: If non-null, this fragment is being
     *                            re-constructed from a previous saved state as
     *                            given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        View view;
        RecyclerView recyclerView;

        context = getActivity();

        view = inflater.inflate( R.layout.fragment_wishlist, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.wishlistRecycler );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        /*
        // sample users for testing
        User user1 = new User( "Yahya Demirel", "avatar_male", "12" );
        User user2 = new User( "Burcu Kaplan", "avatar_female", "12" );
        User user3 = new User( "Korhan Kaya", "avatar_male", "12" );
        User user4 = new User( "Deniz Gökçen", "avatar_female", "12" );
        User user5 = new User( "Tolga Özgün", "avatar_male", "12" );

        // sample products for testing
        Product product1 = new Product( "product_sample", "The Epic of " +
                "Gilgamesh", "demo1", 120, user1, false, "10", new Category(
                        "0" ) );
        Product product2 = new Product( "product_sample2", "brand new dress",
                "demo1", 120, user2, false, "11", new Category( "2" ) );
        Product product3 = new Product( "product_sample3", "basys-3", "demo1"
                , 120, user3, false, "12", new Category( "1" ) );
        Product product4 = new Product( "product_sample", "random", "demo1",
                120, user4, false, "13", new Category( "0" ) );
        Product product5 = new Product( "product_sample", "random", "demo1",
                120, user5, false, "14", new Category( "0" ) );

        productList.add( product1 );
        productList.add( product2 );
        productList.add( product3 );
        productList.add( product4 );
        productList.add( product5 );*/
        getProductList( recyclerView );

        return view;
    }

    private void printView( RecyclerView recyclerView ) {
        adapter = new ProductAdapter( getActivity(), productList );
        recyclerView.setAdapter( adapter );
    }

    private void getProductList( RecyclerView recyclerView ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Product product;
                String pictureUrl;
                String categoryId;
                String sellerName;
                int price;
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
                            productId = tempJson.getString( "product_id" );
                            productTitle = tempJson.getString( "title" );
                            sellerId = tempJson.getString( "seller_id" );
                            sellerAvatarURL = tempJson.getString(
                                    "seller_avatar_url" );
                            categoryId = tempJson.getString( "category_id" );
                            seller = new User( sellerName, sellerAvatarURL,
                                    sellerId );
                            product = new Product( pictureUrl, productTitle,
                                    description, price, seller, false,
                                    productId, new Category( categoryId ) );
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
        }, RequestType.WISHLIST, params, this.getContext() );


    }
}