package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductActivity extends Activity {

    private ImageView userAvatar;
    private ImageView productImage;
    private Button settingsButton;
    private Button addWishlistButton;
    private Button directChatButton;
    private TextView productName;
    private TextView productDescription;
    private TextView categoryText;
    private TextView ownerName;
    private TextView priceText;
    private Product currentProduct;
    private Category category;
    private String title;
    private String description;
    private String pictureURL;
    private String sellerId;
    private String categoryId;
    private String sellerAvatar;
    private String sellerName;
    private String currentUserId;
    private double price;
    private SharedPreferences sharedPreferences;
    private User seller;
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
        priceText = findViewById( R.id.price );
        categoryText = findViewById( R.id.categoryName );
        productImage = findViewById( R.id.image_product_view );
        userAvatar = findViewById( R.id.userAvatar );
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );
        currentUserId = sharedPreferences.getString( "id", "" );
        gson = new Gson();
        if ( getIntent().hasExtra( "product" ) ) {
            currentProduct = gson.fromJson( getIntent().getStringExtra(
                    "product" ), Product.class );
        } else if ( getIntent().hasExtra( "product_id" ) ) {
            currentProduct = retrieveProduct( getIntent().getStringExtra(
                    "product_id" ) );
        }

        if ( currentProduct != null ) {
            sellerId = currentProduct.getSeller().getUserId();
            productName.setText( currentProduct.getTitle() );
            productDescription.setText( currentProduct.getDescription() );
            priceText.setText( String.valueOf( currentProduct.getPrice() ) );
            ownerName.setText( currentProduct.getOwner().toString() );
            categoryText.setText( currentProduct.getCategory().toString() );
            if ( currentProduct.getPicture() != null && !currentProduct.getPicture().equals( "" ) ) {
                Picasso.get().load( currentProduct.getPicture() ).fit().centerInside().into( productImage );
            }
            if ( currentProduct.getOwner().getUserAvatar() != null && !currentProduct.getOwner().getUserAvatar().equals( "" ) ) {
                Picasso.get().load( currentProduct.getOwner().getUserAvatar() ).fit().centerInside().into( userAvatar );
            }
        }


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
                if ( sellerId == null || currentUserId == null ) {
                    Toast.makeText( ProductActivity.this, "Error retrieving " +
                            "info", Toast.LENGTH_SHORT ).show();
                }
                if ( sellerId.equals( currentUserId ) ) {
                    Toast.makeText( ProductActivity.this, "You cannot message" +
                            " yourself!", Toast.LENGTH_SHORT ).show();
                } else {
                    Intent intent;
                    intent = new Intent( ProductActivity.this,
                            PrivateChatActivity.class );
                    intent.putExtra( "user_id", sellerId );
                    startActivity( intent );
                }
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

    public void addToWishlist() {
        //TODO
    }

    private Product retrieveProduct( String productId ) {

        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "product_id", productId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        title = object.getString( "title" );
                        pictureURL = object.getString( "picture_url" );
                        description = object.getString( "description" );
                        sellerId = object.getString( "seller_id" );
                        sellerAvatar = object.getString( "seller_avatar_url" );
                        sellerName = object.getString( "seller_name" );
                        categoryId = object.getString( "category_id" );
                        price = object.getDouble( "price" );
                        seller = new User( sellerName, sellerAvatar, sellerId );
                        category = new Category( categoryId );
                        currentProduct = new Product( pictureURL, title,
                                description, price, seller, false, productId,
                                category );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.PRODUCT, params, this );


        return currentProduct;
    }

}
