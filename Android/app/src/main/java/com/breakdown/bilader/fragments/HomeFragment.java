package com.breakdown.bilader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;

/**
 * A class that makes connection between its layout and data
 *
 * @author Yahya Eren Demirel
 * @version 16.04.2021
 */

public class HomeFragment extends Fragment  {

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

        view = inflater.inflate( R.layout.fragment_home, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.biltraderRecycler );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        // sample users for testing
        User user1 = new User( "Yahya Demirel", "mail@mail.com", "avatar_male"
                , "12" );
        User user2 = new User( "Burcu Kaplan", "mail@mail.com",
                "avatar_female", "12" );
        User user3 = new User( "Korhan Kaya", "mail@mail.com", "avatar_male",
                "12" );
        User user4 = new User( "Deniz Gökçen", "mail@mail.com",
                "avatar_female", "12" );
        User user5 = new User( "Tolga Özgün", "mail@mail.com", "avatar_male",
                "12" );

        // sample products for testing
        Product product1 = new Product( "product_sample", "The Epic of " +
                "Gilgamesh", "demo1", 120, user1 ,false, "10");
        Product product2 = new Product( "product_sample2", "brand new dress",
                "demo1", 120, user2, false, "11");
        Product product3 = new Product( "product_sample3", "basys-3", "demo1"
                , 120, user3, false, "12");
        Product product4 = new Product( "product_sample", "random", "demo1",
                120, user4,false, "13" );
        Product product5 = new Product( "product_sample", "random", "demo1",
                120, user5, false, "14");


        productList = new ArrayList<>();

        productList.add( product1 );
        productList.add( product2 );
        productList.add( product3 );
        productList.add( product4 );
        productList.add( product5 );


        adapter = new ProductAdapter (getActivity(), productList);
        recyclerView.setAdapter( adapter );

        return view;
    }

}