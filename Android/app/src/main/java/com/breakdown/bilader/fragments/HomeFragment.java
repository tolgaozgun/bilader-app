package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private ImageView sortMenuImage;
    private ImageView categoryMenuImage;

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

        Product product1 = new Product( "product_sample", "The Epic of Gilgamesh", "demo1", 12, user1 ,false, "10", new Category("0") );
        Product product2 = new Product( "product_sample2", "Zara Dress", "demo1", 100, user2, false, "11", new Category("2"));
        Product product3 = new Product( "product_sample3", "Basys - 3", "demo1" , 800, user3, false, "12", new Category("1"));
        Product product4 = new Product( "product_sample", "L'oreal Mascara", "demo1", 120, user4,false, "13", new Category("4"));
        Product product5 = new Product( "product_sample", "HP Wireless Mouse", "demo1", 500, user5, false, "14", new Category("1"));

        productList = new ArrayList<>();

        productList.add( product1 );
        productList.add( product2 );
        productList.add( product3 );
        productList.add( product4 );
        productList.add( product5 );

        adapter = new ProductAdapter (getActivity(), productList);
        recyclerView.setAdapter( adapter );

        ArrayList< Product > fullProductList = new ArrayList<>( productList );

        sortMenuImage = view.findViewById( R.id.imageView2 );
        sortMenuImage.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {

                PopupMenu sortMenu = new PopupMenu( getActivity(), view );

                sortMenu.getMenuInflater().inflate( R.menu.menu_biltrader_sort, sortMenu.getMenu() );
                sortMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        if ( item.getItemId() == R.id.sort_alphabetically ) {
                            Collections.sort( productList, new Comparator<Product>() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return p1.getTitle().compareToIgnoreCase( p2.getTitle() );
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_reverse_alphabetically ) {
                            Collections.sort( productList, new Comparator<Product>() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return p1.getTitle().compareToIgnoreCase( p2.getTitle() );
                                }
                            });
                            Collections.reverse( productList );
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_price_low_to_high ) {
                            Collections.sort( productList, new Comparator<Product>() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return (int) (p1.getPrice() - p2.getPrice());
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                        if ( item.getItemId() == R.id.sort_price_high_to_low ) {
                            Collections.sort( productList, new Comparator<Product>() {
                                @Override
                                public int compare( Product p1, Product p2 ) {
                                    return (int) (p1.getPrice() - p2.getPrice());
                                }
                            });
                            Collections.reverse( productList );
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                sortMenu.show();
            }
        });

        categoryMenuImage = view.findViewById( R.id.imageView );
        categoryMenuImage.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {

                PopupMenu categoryMenu = new PopupMenu( getActivity(), view );

                categoryMenu.getMenuInflater().inflate( R.menu.menu_biltrader_category, categoryMenu.getMenu() );
                categoryMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        ArrayList< Product > newList = new ArrayList<>();

                        if ( item.getItemId() == R.id.menu_book ) {
                            for ( Product product : productList ) {
                                if ( product.getCategory().toString().equals( "Book" ) ) {
                                    newList.add( product );
                                }
                            }
                            productList.clear();
                            for ( Product book: newList ) {
                                productList.add( book );
                            }
                        }
                        else if ( item.getItemId() == R.id.menu_clothing ) {
                            for ( Product product : productList ) {
                                if ( product.getCategory().toString().equals( "Clothing" ) ) {
                                    newList.add( product );
                                }
                            }
                            productList.clear();
                            for ( Product clothing: newList ) {
                                productList.add( clothing );
                            }
                        }
                        else if ( item.getItemId() == R.id.menu_electronics ) {
                            for ( Product product : productList ) {
                                if ( product.getCategory().toString().equals( "Electronics" ) ) {
                                    newList.add( product );
                                }
                            }
                            productList.clear();
                            for ( Product electronics: newList ) {
                                productList.add( electronics );
                            }
                        }
                        else if ( item.getItemId() == R.id.menu_hobby ) {
                            for ( Product product : productList ) {
                                if ( product.getCategory().toString().equals( "Hobby Items" ) ) {
                                    newList.add( product );
                                }
                            }
                            productList.clear();
                            for ( Product hobby: newList ) {
                                productList.add( hobby );
                            }
                        }
                        else if ( item.getItemId() == R.id.menu_other ) {
                            for ( Product product : productList ) {
                                if ( product.getCategory().toString().equals( "Other" ) ) {
                                    newList.add( product );
                                }
                            }
                            productList.clear();
                            for ( Product other: newList ) {
                                productList.add( other );
                            }
                        }
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });

                categoryMenu.show();

            }
        });

        for ( Product product: fullProductList ) {
            productList.add( product );
        }

        adapter.notifyDataSetChanged();

        return view;
    }
}