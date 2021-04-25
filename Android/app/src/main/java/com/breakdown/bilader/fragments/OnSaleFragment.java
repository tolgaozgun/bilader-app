package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class OnSaleFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList< Product > productList;
    private ProductAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Activity mContext;
    private String userId;

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
        Bundle bundle;

        mContext = getActivity();

        view = inflater.inflate( R.layout.fragment_onsale, container, false );
        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.onSaleRecycler );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        productList = new ArrayList<>();
        bundle = getArguments();
        if ( bundle != null ) {
            userId = bundle.getString( "user_id" );
        }
        getProductList( recyclerView );

        return view;
    }


    private void getProductList( RecyclerView recyclerView ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
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
                                    productId, new Category( categoryId,
                                    getContext() ) );
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
        }, RequestType.PRODUCT, params, mContext, true );


    }

    private void printView( RecyclerView recyclerView ) {
        adapter = new ProductAdapter( mContext, productList );
        recyclerView.setAdapter( adapter );
    }
}
