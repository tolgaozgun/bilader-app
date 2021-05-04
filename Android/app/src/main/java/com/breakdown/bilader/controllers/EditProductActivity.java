package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class that helps edit a existing product's properties such as its title or
 * price.
 *
 * @author Korhan Kemal Kaya
 * @version 22.04.2021
 */

public class EditProductActivity extends Activity {

    private EditText editPrice;
    private EditText editTitle;
    private EditText editDescription;
    private TextView editCategory;
    private Button editButton;
    private Button onSaleButton;
    private Button soldButton;
    private Uri imageUri;
    private Gson gson;
    private ImageView productImage;
    private Product currentProduct;
    private PopupMenu popupMenu;
    private Category category;


    /**
     * this is the method where most initialization made such as UI and widgets
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_editproduct );

        gson = new Gson();
        currentProduct =
                gson.fromJson( getIntent().getStringExtra( "product" ),
                        Product.class );

        editButton = findViewById( R.id.editButton );
        onSaleButton = findViewById( R.id.onSale );
        soldButton = findViewById( R.id.soldButton );
        productImage = ( ImageView ) findViewById( R.id.edit_product_image );
        editDescription = findViewById( R.id.editDescription );
        editTitle = findViewById( R.id.editTitle );
        editPrice = findViewById( R.id.editPrice );
        editCategory = findViewById( R.id.editCategory );

        editDescription.setText( currentProduct.getDescription() );
        editPrice.setText( String.valueOf( currentProduct.getPrice() ) );
        editTitle.setText( currentProduct.getTitle() );
        if ( currentProduct.getPicture() != null && !currentProduct.getPicture().equals( "" ) ) {
            Picasso.get().load( currentProduct.getPicture() ).fit().centerCrop().into( productImage );
        }
        editCategory.setText( currentProduct.getCategory().getName() );


        soldButton.setOnClickListener( new View.OnClickListener() {
            /**
             * When the button clicked, it changes the current product's sold
             * situation as sold.
             * @param v , refers to view of button
             */
            @Override
            public void onClick( View v ) {
                //editedProduct.changeSoldSituation();
            }
        } );

        onSaleButton.setOnClickListener( new View.OnClickListener() {
            /**
             * When the button clicked, it changes the current product's sold
             * situation as on sale.
             * @param v , refers to view of button
             */
            @Override
            public void onClick( View v ) {
            }
        } );

        editButton.setOnClickListener( new View.OnClickListener() {
            @Override
            /**
             * When the button clicked, it applies changes to product and
             * display it in Biltrader screen with new properties.
             * @param v , refers to view of button
             */ public void onClick( View v ) {
                editProduct();
            }

        } );

        productImage.setOnClickListener( new View.OnClickListener() {
            /**
             * starts action accordingly its clicked view
             * @param view is the view that was clicked
             */
            public void onClick( View view ) {
                Intent galery;
                galery = new Intent();
                galery.setType( "image/*" );
                galery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galery, "Select"
                        + " Picture" ), 1 );
            }
        } );


        editCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                popupMenu = new PopupMenu( EditProductActivity.this, view );
                popupMenu.getMenuInflater().inflate( R.menu.category_popup_menu, popupMenu.getMenu() );
                createMenu( popupMenu );
                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        category = new Category( item.getItemId(),
                                EditProductActivity.this );
                        editCategory.setText( item.getTitle() );
                        return false;
                    }
                } );
            }
        } );
    }


    /**
     * Creates the PopupMenu that shows the category options of the products by
     * getting the options that are found in the server.
     *
     * @param popupMenu is a PopupMenu object
     */
    private void createMenu( PopupMenu popupMenu ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Iterator< String > keys;
                String name;
                int id;
                JSONObject tempJson;
                try {
                    if ( object.getBoolean( "success" ) ) {

                        keys = object.getJSONObject( "categories" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "categories" ).getJSONObject( key );

                            name = tempJson.getString( "name" );
                            id = tempJson.getInt( "id" );
                            // id 0 is reserved for category "all" which no
                            // product should possess.
                            if ( id != 0 ) {
                                popupMenu.getMenu().add( 1, id, id, name );
                            }
                        }
                    }
                    popupMenu.show();
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.CATEGORIES, params, EditProductActivity.this, true );

    }

    private void editProduct() {
        Intent intent;
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "picture_url", currentProduct.getPicture() );
        params.put( "title", currentProduct.getTitle() );
        params.put( "description", currentProduct.getDescription() );
        params.put( "price", String.valueOf( currentProduct.getPrice() ) );
        params.put( "seller_id", currentProduct.getSeller().getId() );
        params.put( "category_id", String.valueOf( category.getId() ) );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( EditProductActivity.this,
                            object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( EditProductActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( EditProductActivity.this, message,
                        Toast.LENGTH_SHORT ).show();
            }
        }, RequestType.EDIT_PRODUCT, params, EditProductActivity.this, true );


        intent = new Intent( EditProductActivity.this, ProductActivity.class );
        intent.putExtra( "product_id", currentProduct.getProductId() );
        startActivity( intent );

    }

    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 1 && resultCode == -1 ) {
            imageUri = data.getData();
            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap( this.getContentResolver(), imageUri );
                Picasso.get().load( imageUri ).fit().centerCrop().into( productImage );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
