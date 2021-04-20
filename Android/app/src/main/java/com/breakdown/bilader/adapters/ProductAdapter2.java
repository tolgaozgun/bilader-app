package com.breakdown.bilader.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.*;
import com.google.gson.Gson;

import java.util.*;

/**
 * The fragment class that makes connection between UI component and data source
 * of the products that helps us to fill data in UI component.
 *
 * @author Yahya Eren Demirel
 * @version 18.04.2021
 */

public class ProductAdapter2 extends RecyclerView.Adapter< ProductAdapter2.ProductHolder2 > {
    private Context mContext;
    private ArrayList< Product > products;
    private OnNOteListener onNOteListener;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext is the location of the current fragment and its internal
     *                 elements and methods
     * @param products list of the product
     */
    public ProductAdapter2( Context mContext, ArrayList< Product > products, OnNOteListener onNOteListener ) {
        this.mContext = mContext;
        this.products = products;
        this.onNOteListener = onNOteListener;
    }


    /**
     * A class that finds xml id's of layout elements
     */
    public class ProductHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageProductSeller;
        public ImageView imageProduct;
        public TextView textUserName;
        public TextView textProductName;
        public TextView textProductPrice;
        public CardView cardView;
        public TextView textCategoryName;

        OnNOteListener onNOteListener;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ProductHolder2( @NonNull View itemView, OnNOteListener onNOteListener ) {
            super( itemView );
            textProductName = itemView.findViewById( R.id.text_product_name );
            imageProduct = itemView.findViewById( R.id.image_product );
            imageProductSeller = itemView.findViewById( R.id.image_avatar_product_seller );
            textUserName = itemView.findViewById( R.id.text_product_seller_name );
            cardView = itemView.findViewById( R.id.card_product );
            textProductPrice = itemView.findViewById( R.id.priceProduct );
            textCategoryName = itemView.findViewById( R.id.categoryProduct );
            this.onNOteListener = onNOteListener;


            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick( View v ) {
            onNOteListener.onNoteClick( getAdapterPosition() );
        }
    }

    public interface OnNOteListener {
        void onNoteClick(int position);
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
    public ProductHolder2 onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_products, parent, false );

        return new ProductHolder2( itemView, onNOteListener );
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
    public void onBindViewHolder( @NonNull ProductHolder2 holder, int position ) {
        Product product;

        product = products.get( position );

        holder.textUserName.setText( product.getSeller().getUserName() );
        holder.textProductName.setText( product.getTitle() );
        holder.textProductPrice.setText( String.valueOf( product.getPrice() ) );

        holder.imageProduct.setImageResource( mContext.getResources().getIdentifier( product.getPicture(), "drawable", mContext.getPackageName() ) );
        holder.imageProductSeller.setImageResource( mContext.getResources().getIdentifier( product.getSeller().getUserAvatar(), "drawable", mContext.getPackageName() ) );


        isWishlisted( product.getProductId() );
        holder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                Gson gson;
                String myJson;

                intent = new Intent( mContext, ProductActivity.class );
                gson = new Gson();
                myJson = gson.toJson( product );
                intent.putExtra( "product", myJson );
                mContext.startActivity( intent );
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
