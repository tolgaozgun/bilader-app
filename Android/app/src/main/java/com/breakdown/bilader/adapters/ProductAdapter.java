package com.breakdown.bilader.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Product;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * The fragment class that makes connection between UI component and data source
 * of the products that helps us to fill data in UI component.
 *
 * @author Yahya Eren Demirel
 * @version 18.04.2021
 */

public class ProductAdapter extends
                            RecyclerView.Adapter< ProductAdapter.ProductHolder > {
    private int controller;
    private Fragment mContext;
    private Activity mmmContext;
    private ArrayList< Product > products;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext is the location of the current fragment and its internal
     *                 elements and methods
     * @param products list of the product
     */
    public ProductAdapter( Fragment mContext, ArrayList< Product > products ) {
        this.mContext = mContext;
        this.products = products;
        controller = 0;
    }

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mmmContext is the location of the current activity and its
     *                   internal elements and methods
     * @param products   list of the product
     */
    public ProductAdapter( Activity mmmContext,
                           ArrayList< Product > products ) {
        controller = 1;
        this.mmmContext = mmmContext;
        this.products = products;
    }

    /**
     * A class that finds xml id's of layout elements
     */
    public class ProductHolder extends RecyclerView.ViewHolder {
        public ImageView imageProductSeller;
        public ImageView imageProduct;
        public TextView textUserName;
        public TextView textProductName;
        public TextView textProductPrice;
        public CardView cardView;
        public TextView textCategoryName;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ProductHolder( @NonNull View itemView ) {
            super( itemView );
            textProductName = itemView.findViewById( R.id.text_product_name );
            imageProduct = itemView.findViewById( R.id.image_product );
            imageProductSeller =
                    itemView.findViewById( R.id.image_avatar_product_seller );
            textUserName =
                    itemView.findViewById( R.id.text_product_seller_name );
            cardView = itemView.findViewById( R.id.card_product );
            textProductPrice = itemView.findViewById( R.id.priceProduct );
            textCategoryName = itemView.findViewById( R.id.categoryProduct );

        }
    }

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                             int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_products, parent, false );

        return new ProductHolder( itemView );
    }

    /**
     * a method called by RecyclerView to display the data at the specified
     * position
     *
     * @param holder   is the ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position is The position of the item within the adapter's data
     *                 set.
     */
    @Override
    public void onBindViewHolder( @NonNull ProductHolder holder,
                                  int position ) {
        Product product;

        product = products.get( position );
        holder.textUserName.setText( product.getSeller().getUserName() );
        holder.textProductName.setText( product.getTitle() );
        holder.textProductPrice.setText( String.valueOf( product.getPrice() ) );
        if ( controller == 0 ) {
            holder.imageProduct.setImageResource( mContext.getResources().getIdentifier( product.getPicture(), "drawable", mContext.getActivity().getPackageName() ) );
            holder.imageProductSeller.setImageResource( mContext.getResources().getIdentifier( product.getSeller().getUserAvatar(), "drawable", mContext.getActivity().getPackageName() ) );
        } else {
            holder.imageProduct.setImageResource( mmmContext.getResources().getIdentifier( product.getPicture(), "drawable", mmmContext.getPackageName() ) );
            holder.imageProductSeller.setImageResource( mmmContext.getResources().getIdentifier( product.getSeller().getUserAvatar(), "drawable", mmmContext.getPackageName() ) );
        }

        isWishlisted( product.getProductId() );

        holder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                Gson gson;
                String myJson;

                intent = new Intent( mmmContext, ProductActivity.class );
                gson = new Gson();
                myJson = gson.toJson( product );
                intent.putExtra( "product", myJson );
                mmmContext.startActivity( intent );
            }
        } );

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        int productsSize;

        productsSize = products.size();

        return productsSize;
    }

    public void isWishlisted( String productId ) {
        //TODO
        // dye the heart if the post is added to the wishlist

    }
}
