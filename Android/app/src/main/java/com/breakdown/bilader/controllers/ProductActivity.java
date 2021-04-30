package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
/**
 * A class that shows the properties of a product. With its specified click listeners, it interacts
 * with and sends information to other activity classes such as OthersProfileActivity, BiltraderActivity
 * and ReportActivity.
 *
 * @author breakDown
 * @version 29.04.2021
 */
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
    private int categoryId;
    private String sellerAvatar;
    private String sellerName;
    private String currentUserId;
    private boolean isWishlisted;
    private double price;
    private SharedPreferences sharedPreferences;
    private User seller;
    private Gson gson;


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
            setView();
        } else if ( getIntent().hasExtra( "product_id" ) ) {
            retrieveProduct( getIntent().getStringExtra( "product_id" ) );
        }

        userAvatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( ProductActivity.this,
                        OthersProfileActivity.class );
                intent.putExtra( "user_id",
                        currentProduct.getSeller().getId() );
                intent.putExtra( "user_name",
                        currentProduct.getSeller().getName() );
                intent.putExtra( "user_avatar",
                        currentProduct.getSeller().getAvatar() );
                startActivity( intent );
            }
        } );

        ownerName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = new Intent( ProductActivity.this,
                        OthersProfileActivity.class );
                intent.putExtra( "user_id",
                        currentProduct.getSeller().getId() );
                intent.putExtra( "user_name",
                        currentProduct.getSeller().getName() );
                intent.putExtra( "user_avatar",
                        currentProduct.getSeller().getAvatar() );
                startActivity( intent );
            }
        } );
        if ( currentProduct.getSeller().getId().equals( currentUserId ) ) {
            settingsButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    PopupMenu settingsMenu;
                    settingsMenu = new PopupMenu( ProductActivity.this, v );

                    settingsMenu.getMenuInflater().inflate( R.menu.second_menu, settingsMenu.getMenu() );
                    settingsMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick( MenuItem item ) {
                            int id;
                            Intent newIntent;

                            id = item.getItemId();

                            if ( id == R.id.editMenu ) {

                                newIntent = new Intent( ProductActivity.this,
                                        EditProductActivity.class );
                                startActivity( newIntent );
                                //TODO
                            } else if ( id == R.id.removeMenu ) {
                                //TODO
                            }
                            return true;
                        }
                    } );

                    settingsMenu.show();
                }
            } );
        } else {
            settingsButton.setBackgroundResource( R.drawable.baseline_report_problem_24 );
            settingsButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    Intent intent;
                    intent = new Intent( ProductActivity.this,
                            ReportActivity.class );
                    intent.putExtra( "id", currentProduct.getProductId() );
                    intent.putExtra( "title", currentProduct.getTitle() );
                    intent.putExtra( "image_url", currentProduct.getPicture() );
                    intent.putExtra( "report-type", 1 );
                    startActivity( intent );
                }
            } );
        }


        addWishlistButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( isWishlisted ) {
                    removeFromWishlist();
                } else {
                    addToWishlist();
                }
                updateWishlistButton();
            }
        } );

        directChatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String myJson;
                Intent intent;
                if ( sellerId == null || currentUserId == null ) {
                    Toast.makeText( ProductActivity.this,
                            "Error retrieving " + "info",
                            Toast.LENGTH_SHORT ).show();
                }
                if ( sellerId.equals( currentUserId ) ) {
                    Toast.makeText( ProductActivity.this, "You cannot " +
                            "message" + " yourself!", Toast.LENGTH_SHORT ).show();
                } else {
                    intent = new Intent( ProductActivity.this,
                            PrivateChatActivity.class );
                    gson = new Gson();
                    myJson = gson.toJson( currentProduct.getOwner() );
                    intent.putExtra( "user", myJson );
                    startActivity( intent );
                }
            }
        } );
    }


    @Override
    public void onBackPressed() {
        Intent intent;
        if ( getIntent().getBooleanExtra( "goBack", true ) ) {
            super.onBackPressed();
        } else {
            intent = new Intent( ProductActivity.this,
                    BiltraderActivity.class );
            startActivity( intent );
        }
    }

    /**
     * Sets the properties (seller, name, description, category, price, picture)
     * of a product by getting the entered values and adjusting them as features
     * of the product
     *
     */
    private void setView() {

        if ( currentProduct != null ) {
            System.out.println( "XOXOXOX" );
            sellerId = currentProduct.getSeller().getId();
            seller = currentProduct.getSeller();
            productName.setText( currentProduct.getTitle() );
            productDescription.setText( currentProduct.getDescription() );
            priceText.setText( String.valueOf( currentProduct.getPrice() ) );
            ownerName.setText( currentProduct.getOwner().toString() );
            categoryText.setText( currentProduct.getCategory().toString() );
            if ( currentProduct.getPicture() != null && !currentProduct.getPicture().equals( "" ) ) {
                Picasso.get().load( currentProduct.getPicture() ).fit().centerCrop().into( productImage );
            }
            if ( currentProduct.getOwner().getAvatar() != null && !currentProduct.getOwner().getAvatar().equals( "" ) ) {
                Picasso.get().load( currentProduct.getOwner().getAvatar() ).fit().centerCrop().into( userAvatar );
            }
        }
        checkIfWishlisted();
    }

    /**
     * Adds the selected product to the wishlist of the current user
     *
     */
    public void addToWishlist() {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "product_id", currentProduct.getProductId() );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( ProductActivity.this, object.getString(
                            "message" ), Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( ProductActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
                checkIfWishlisted();
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( ProductActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
                checkIfWishlisted();
            }
        }, RequestType.ADD_WISHLIST, params, this, true );
    }

    /**
     * Removes the selected product from the wishlist of the current user
     *
     */
    public void removeFromWishlist() {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "product_id", currentProduct.getProductId() );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( ProductActivity.this, object.getString(
                            "message" ), Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( ProductActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
                checkIfWishlisted();
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( ProductActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
                checkIfWishlisted();
            }
        }, RequestType.REMOVE_WISHLIST, params, this, true );
    }

    /**
     * Returns the Product object with the desired product id and the properties
     * that it has
     * @param productId is the String version of the product id
     * @return Product object that has the entered product id.
     */
    private Product retrieveProduct( String productId ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "product_id", productId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                JSONObject tempJson;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        tempJson =
                                object.getJSONObject( "result" ).getJSONObject( "0" );
                        title = tempJson.getString( "title" );
                        pictureURL = tempJson.getString( "picture_url" );
                        description = tempJson.getString( "description" );
                        sellerId = tempJson.getString( "seller_id" );
                        sellerAvatar = tempJson.getString( "seller_avatar_url"
                        );
                        sellerName = tempJson.getString( "seller_name" );
                        categoryId = tempJson.getInt( "category_id" );
                        price = tempJson.getDouble( "price" );
                        seller = new User( sellerName, sellerAvatar, sellerId );
                        category = new Category( categoryId,
                                ProductActivity.this );
                        currentProduct = new Product( pictureURL, title,
                                description, price, seller, false, productId,
                                category );
                        setView();
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.PRODUCT, params, this, true );


        return currentProduct;
    }

    /**
     * Checks if the product is in the wishlist of the current user
     *
     */
    public void checkIfWishlisted() {
        isWishlisted = false;
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "product_id", currentProduct.getProductId() );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    isWishlisted = object.getBoolean( "wishlisted" );
                    updateWishlistButton();
                } catch ( JSONException e ) {
                    e.printStackTrace();
                    updateWishlistButton();
                }
            }

            @Override
            public void onFail( String message ) {
                updateWishlistButton();
            }
        }, RequestType.CHECK_WISHLIST, params, this, true );
    }

    /**
     * Re-draw the addToWishlist button if the product is added to the wishlist
     * of the current user
     *
     */
    private void updateWishlistButton() {
        if ( isWishlisted ) {
            addWishlistButton.setBackgroundResource( R.drawable.added_to_wishlist );
        } else {
            addWishlistButton.setBackgroundResource( R.drawable.add_to_wishlist );
        }
    }
}
