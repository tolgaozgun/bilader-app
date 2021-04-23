package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Product;
import com.google.gson.Gson;

public class ProductActivity extends Activity {

    private Button settingsButton;
    private Button addWishlistButton;
    private Button directChatButton;
    private TextView productName;
    private TextView productDescription;
    private TextView category;
    private TextView ownerName;
    private TextView price;
    private Product currentProduct;
    private Gson gson;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product );

        settingsButton = findViewById( R.id.settingsProduct );
        addWishlistButton = findViewById( R.id.addToWishlist );
        directChatButton = findViewById( R.id.directChat );
        productName = findViewById( R.id.titleProduct );
        productDescription = findViewById( R.id.description );
        ownerName = findViewById( R.id.ownerName );
        price = findViewById( R.id.price );
        //category = findViewById(R.id.categoryName);
        gson = new Gson();
        currentProduct =
                gson.fromJson( getIntent().getStringExtra( "product" ),
                        Product.class );
        productName.setText( currentProduct.getTitle() );
        productDescription.setText( currentProduct.getDescription() );
        price.setText( String.valueOf( currentProduct.getPrice() ) );
        ownerName.setText( currentProduct.getOwner().toString() );
        //category.setText(currentProduct.getCategory().toString());

        settingsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //This will identify who is the clicker than display two diff
                // pop-ups.
                //TODO
            }
        } );

        addWishlistButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //TODO
                addToWishlist();
            }
        } );

        directChatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //TODO
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        String userId = "CURRENT_STRING_ID";

        if ( currentProduct.getSeller().getUserId().equals( userId ) ) {
            getMenuInflater().inflate( R.menu.second_menu, menu );
        } else {
            getMenuInflater().inflate( R.menu.first_menu, menu );
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        int id;
        Intent newIntent;

        id = item.getItemId();

        if ( id == R.id.reportMenu ) {

            newIntent = new Intent( ProductActivity.this,
                    ReportActivity.class );
            startActivity( newIntent );
            //TODO

        } else if ( id == R.id.editMenu ) {

            newIntent = new Intent( ProductActivity.this,
                    EditProductActivity.class );
            startActivity( newIntent );
            //TODO
        } else if ( id == R.id.removeMenu ) {
            //TODO
        }
        return true;
    }

    public void addToWishlist(){
        //TODO
    }
}
