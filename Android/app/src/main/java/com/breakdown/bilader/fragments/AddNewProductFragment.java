package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.ImageLoadAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A class that makes connection between its layout and data
 *
 * @author breakDown
 * @version 29.04.2021
 */
public class AddNewProductFragment extends Fragment {

    private EditText price;
    private EditText title;
    private EditText description;
    private Button postButton;
    private Button categoryButton;
    private Activity mContext;
    private PopupMenu popupMenu;
    private ImageView pickPhotoButton;
    private Category category;
    private String productId;
    private SharedPreferences sharedPreferences;

    /**
     * Called to have the fragment instantiate its user interface properties and
     * give them specialized actions.
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
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {

        View view;

        view = inflater.inflate( R.layout.fragment_addnewproduct, container,
                false );


        mContext = getActivity();
        price = view.findViewById( R.id.priceAddNewProduct );
        title = view.findViewById( R.id.titleAddNewProduct );
        description = view.findViewById( R.id.descriptionAddNewProduct );
        postButton = view.findViewById( R.id.postButton );
        categoryButton = view.findViewById( R.id.category_button );
        pickPhotoButton = view.findViewById( R.id.button_add );


        categoryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                popupMenu = new PopupMenu( getActivity(), v );
                popupMenu.getMenuInflater().inflate( R.menu.category_popup_menu, popupMenu.getMenu() );
                createMenu( popupMenu );
                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        category = new Category( item.getItemId(),
                                getContext() );
                        categoryButton.setText( item.getTitle() );
                        return false;
                    }
                } );
            }
        } );


        postButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                submitProduct();
            }
        } );

        pickPhotoButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent gallery;

                gallery = new Intent();
                gallery.setType( "image/*" );
                gallery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( gallery,
                        "Select" + " Picture" ), 1 );
            }
        } );

        return view;
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
        }, RequestType.CATEGORIES, params, this.getContext(), true );

    }

    /**
     * Called when an activity that is launched exits, it gives the requestCode
     * to started it with, the resultCode it returned, and any additional data
     * from it
     *
     * @param requestCode: is the int object that allows to identify who this
     *                     result came from.
     * @param resultCode:  is the int object that is returned by the child
     *                     activity through its setResult().
     * @param data:        If non-null, this intent is being used to return
     *                     result data to the caller
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        /*if ( requestCode == 1 && resultCode == -1 ) {
            imageUri = data.getData();
            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap( mContext.getContentResolver(), imageUri );
                Picasso.get().load( imageUri ).fit().centerCrop().into( pickPhotoButton );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        if ( requestCode == 1 && resultCode == -1 ) {
            imageUri = data.getData();

            String filePath = getPath( imageUri );
            String file_extn =
                    filePath.substring( filePath.lastIndexOf( "." ) + 1 );


            try {
                if ( file_extn.equals( "img" ) || file_extn.equals( "jpg" ) || file_extn.equals( "jpeg" ) || file_extn.equals( "gif" ) || file_extn.equals( "png" ) ) {
                  //  uploadImage( new URI( filePath ) );
                } else {
                    //NOT IN REQUIRED FORMAT
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }*/

    }

    public String getPath( Uri uri ) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query( uri, proj,
                null, null, null );
        if ( cursor.moveToFirst() ) {
            int column_index =
                    cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
            res = cursor.getString( column_index );
        }
        cursor.close();
        return res;
    }

    /**
     * Called when a product is wanted to be added. It sends the properties of
     * the product to the ProductActivity class where all of the data is hold.
     */
    private void submitProduct() {
        String priceText;
        String titleText;
        String userId;
        String descriptionText;
        HashMap< String, String > params;

        params = new HashMap< String, String >();
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( mContext );
        userId = sharedPreferences.getString( "id", "" );
        priceText = price.getText().toString();
        titleText = title.getText().toString();
        descriptionText = description.getText().toString();
        params.put( "picture_url", "" );
        params.put( "title", titleText );
        params.put( "description", descriptionText );
        params.put( "price", priceText );
        params.put( "seller_id", userId );
        params.put( "category_id", String.valueOf( category.getId() ) );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Intent intent;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        productId = object.getString( "product_id" );
                        intent = new Intent( mContext, ProductActivity.class );
                        intent.putExtra( "product_id", productId );
                        intent.putExtra( "goBack", false );
                        startActivity( intent );
                        mContext.finish();
                    }
                    Toast.makeText( mContext, object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( mContext, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( mContext, message, Toast.LENGTH_SHORT ).show();

            }
        }, RequestType.ADD_PRODUCT, params, mContext, true );
    }

    /*private void uploadImage( URI uri ) {
        JSONObject json;
        File sourceFile = new File( uri.getPath() );
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );

        try {
            final com.squareup.okhttp.MediaType MEDIA_TYPE_PNG =
                    com.squareup.okhttp.MediaType.parse( "image/*" );

            com.squareup.okhttp.RequestBody requestBody =
                    new MultipartBuilder().type( MultipartBuilder.FORM ).addFormDataPart( "image", "image.png", com.squareup.okhttp.RequestBody.create( MEDIA_TYPE_PNG, sourceFile ) ).build();

            com.squareup.okhttp.Request request =
                    new com.squareup.okhttp.Request.Builder().url( "http://88"
                            + ".99.11.149:8080/server/index.jsp" ).put( requestBody ).addHeader( "Content-Type", "application/x-www-formurlencoded" ).build();

            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.Response response =
                    client.newCall( request ).execute();


            System.out.println( "Response data " + response );
            json = new JSONObject(response.toString());
            imageUrl = json.getString( "url" );


        } catch ( Exception e ) {

            System.out.println( "Response error is" + e );

        }
    }*/
}
