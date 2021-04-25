package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddNewProductFragment extends Fragment {

    private EditText price;
    private EditText title;
    private EditText description;
    private Button postButton;
    private Button categoryButton;
    private User currentUser;
    private Activity mContext;
    private PopupMenu popupMenu;
    private Product newProduct;
    private Uri imageUri;
    private RecyclerView profileImage;
    private Button pickPhotoButton;
    private Category category;
    private String productId;

    private ArrayList< Uri > uriList;
    private List< String > imagesEncodedList;
    private ImageLoadAdapter adapter;
    private SharedPreferences sharedPreferences;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {

        View view;

        view = inflater.inflate( R.layout.fragment_addnewproduct, container,
                false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.recyclerImageHolder );


        recyclerView.setLayoutManager( new StaggeredGridLayoutManager( 2,
                StaggeredGridLayoutManager.VERTICAL ) );

        mContext = getActivity();
        price = view.findViewById( R.id.priceAddNewProduct );
        title = view.findViewById( R.id.titleAddNewProduct );
        description = view.findViewById( R.id.descriptionAddNewProduct );
        postButton = view.findViewById( R.id.postButton );
        categoryButton = view.findViewById( R.id.category_button );
        pickPhotoButton = view.findViewById( R.id.button_add );
        profileImage = view.findViewById( R.id.recyclerImageHolder );
        uriList = new ArrayList< Uri >();
        //For now, current user is
        currentUser = new User( "Korhan", "avatar_male", "12" );


        categoryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                popupMenu = new PopupMenu( getActivity(), v );
                popupMenu.getMenuInflater().inflate( R.menu.category_popup_menu, popupMenu.getMenu() );
                createMenu( popupMenu );
                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick( MenuItem item ) {
                        category = new Category( item.getItemId() );
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
                Intent galery;

                galery = new Intent();
                galery.setType( "image/*" );
                galery.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                galery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galery, "Select"
                        + " Picture" ), 1 );
            }
        } );

        return view;
    }

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
                            popupMenu.getMenu().add( 1, id, id, name );
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
        }, RequestType.CATEGORIES, params, this.getContext() );

    }


    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        int position;
        super.onActivityResult( requestCode, resultCode, data );
        if ( requestCode == 1 && resultCode == -1 && null != data ) {
            // Get the Image from data
            if ( data.getClipData() != null ) {
                ClipData mClipData = data.getClipData();
                int count = data.getClipData().getItemCount();
                for ( int i = 0; i < count; i++ ) {
                    // adding imageuri in array
                    Uri imageUri = data.getClipData().getItemAt( i ).getUri();
                    uriList.add( imageUri );
                }
                position = 0;
            } else {
                Uri imageUriSingle = data.getData();
                uriList.add( imageUriSingle );
            }
        } else {
            // show this if no image is selected
            Toast.makeText( getContext(), "You haven't picked any image",
                    Toast.LENGTH_LONG ).show();
        }
        adapter = new ImageLoadAdapter( getContext(), uriList );
        recyclerView.setAdapter( adapter );
    }

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
                try {
                    if ( object.getBoolean( "success" ) ) {
                        Intent intent;
                        productId = object.getString( "product_id" );
                        intent = new Intent( mContext, ProductActivity.class );
                        intent.putExtra( "product_id", productId );
                        intent.putExtra( "goBack",false );
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
        }, RequestType.ADD_PRODUCT, params, mContext );


        //TODO
        //I copied the above  part into this method to not create a problem
        // when clicking the product to go to product screen
    }

}
