package com.breakdown.bilader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.NotificationAdapter;
import com.breakdown.bilader.adapters.NotificationService;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.controllers.MainChatActivity;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class that makes connection between its layout and data
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList< Product > productList;
    private ArrayList< Product > holderList;
    private ProductAdapter adapter;
    private ImageView sortMenuImage;
    private ImageView categoryMenuImage;
    private ImageView chatButton;
    private PopupMenu categoryMenu;
    private int currentCategoryIndex;
    private SearchView searchView;

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
        LinearLayoutManager layoutManager;

        view = inflater.inflate( R.layout.fragment_home, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.biltraderRecycler );

        layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );


        holderList = new ArrayList< Product >();
        getProductList( recyclerView );
        /*searchView = view.findViewById( R.id.searchView );
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener
        () {
            @Override
            public boolean onQueryTextSubmit( String query ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange( String newText ) {
                adapter.getFilter().filter( newText );
                return false;
            }
        });*/

        sortMenuImage = view.findViewById( R.id.imageView2 );
        sortMenuImage.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {

                PopupMenu sortMenu = new PopupMenu( getActivity(), view );

                sortMenu.getMenuInflater().inflate( R.menu.menu_biltrader_sort, sortMenu.getMenu() );
                sortMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        if ( item.getItemId() == R.id.sort_alphabetically ) {
                            Collections.sort( productList,
                                    new Comparator< Product >() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return p1.getTitle().compareToIgnoreCase( p2.getTitle() );
                                }
                            } );
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_reverse_alphabetically ) {
                            Collections.sort( productList,
                                    new Comparator< Product >() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return p1.getTitle().compareToIgnoreCase( p2.getTitle() );
                                }
                            } );
                            Collections.reverse( productList );
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_price_low_to_high ) {
                            Collections.sort( productList,
                                    new Comparator< Product >() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return ( int ) ( p1.getPrice() - p2.getPrice() );
                                }
                            } );
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_price_high_to_low ) {
                            Collections.sort( productList,
                                    new Comparator< Product >() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return ( int ) ( p1.getPrice() - p2.getPrice() );
                                }
                            } );
                            Collections.reverse( productList );
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                } );

                sortMenu.show();
            }
        } );

        chatButton = view.findViewById( R.id.home_chat_button );
        chatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( getContext(), MainChatActivity.class );
                startActivity( intent );
            }
        } );

        categoryMenuImage = view.findViewById( R.id.imageView );
        categoryMenuImage.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {
                System.out.println( "AA" );

                categoryMenu = new PopupMenu( getActivity(), view );
                System.out.println( "AA" );

                categoryMenu.getMenuInflater().inflate( R.menu.category_popup_menu, categoryMenu.getMenu() );
                System.out.println( "AA" );
                createMenu( categoryMenu );
                System.out.println( "AA" );
                categoryMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        System.out.println( "p " + productList.size() );
                        System.out.println( "h: " + holderList.size() );
                        productList.clear();
                        for ( Product p : holderList ) {
                            System.out.println( "CC" );
                            if ( p.getCategory() != null  ) {
                                System.out.println( "Item: " + item.getTitle() );
                                System.out.println( "P: " + p + " ? " + p.getTitle() );
                                System.out.println( "Product: " + p.getCategory().getId() );
                                System.out.println( "11: " + item.getItemId()  );
                                if ( p.getCategory().getId() == item.getItemId() ) {
                                    System.out.println( "DD" );
                                    productList.add( p );
                                }
                            }
                        }
                        currentCategoryIndex = item.getItemId();
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                } );


            }
        } );

        return view;
    }

    private void printView( RecyclerView recyclerView ) {
        if ( productList != null ) {
            for ( Product product : productList ) {
                holderList.add( product );
            }
        }
        adapter = new ProductAdapter( getActivity(), productList );
        recyclerView.setAdapter( adapter );
    }

    private void createMenu( PopupMenu categoryMenu ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Iterator< String > keys;
                String name;
                int id;
                JSONObject tempJson;
                System.out.println( "DD" );
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println( "DD" );

                        keys = object.getJSONObject( "categories" ).keys();
                        while ( keys.hasNext() ) {
                            System.out.println( "EE" );
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "categories" ).getJSONObject( key );

                            name = tempJson.getString( "name" );
                            id = tempJson.getInt( "id" );
                            categoryMenu.getMenu().add( 1, id, id, name );
                            System.out.println( "FF" );
                        }
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
                categoryMenu.show();
            }

            @Override
            public void onFail( String message ) {
                categoryMenu.show();

            }
        }, RequestType.CATEGORIES, params, this.getContext(), true );

    }

    private void getProductList( RecyclerView recyclerView ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Product product;
                String pictureUrl;
                String sellerName;
                int categoryId;
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
                                    productId, new Category( categoryId, getContext() ) );
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
        }, RequestType.PRODUCT, params, this.getContext(), true );


    }
}