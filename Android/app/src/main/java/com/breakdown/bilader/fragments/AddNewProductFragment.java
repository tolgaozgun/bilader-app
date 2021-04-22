package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;

import java.io.IOException;

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
    private ImageView profileImage;
    private Button pickPhotoButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addnewproduct, container, false);

        mContext = getActivity();
        price = view.findViewById(R.id.priceAddNewProduct);
        title = view.findViewById(R.id.titleAddNewProduct);
        description = view.findViewById(R.id.descriptionAddNewProduct);
        postButton = view.findViewById(R.id.postButton);
        categoryButton = view.findViewById(R.id.category_button);
        pickPhotoButton = view.findViewById(R.id.button_add);
        profileImage = view.findViewById( R.id.added_image );
        newProduct = new Product(currentUser,false, "1");
        //For now, current user is
        currentUser =    new User("Korhan","mail","avatar_male","12");


        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.category_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if ( item.getItemId() == R.id.book) {
                            newProduct.setCategory( new Category("0") );
                            categoryButton.setText("Books");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.electronic) {
                            newProduct.setCategory(new Category("1") );
                            categoryButton.setText("Electronics");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.clothes ) {
                            newProduct.setCategory(new Category("2"));
                            categoryButton.setText("Clothing");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.hoby) {
                            newProduct.setCategory(new Category("3"));
                            categoryButton.setText("Hobby");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.others ) {
                            newProduct.setCategory(new Category("4"));
                            categoryButton.setText("Other");
                            return true;
                            }
                        return false;
                        }

                });
                popupMenu.show();
            }
            });




        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceText;
                String titleText;
                String descriptionText;
                Category category;

                Intent intent;
                Gson gson;
                String myJson;

                priceText = price.getText().toString();
                titleText = title.getText().toString();
                descriptionText = description.getText().toString();
                newProduct.setTitle(titleText);
                newProduct.setPrice(Double.parseDouble(priceText));
                newProduct.setPicture("");
                newProduct.setDescription(descriptionText);
                intent = new Intent(mContext, ProductActivity.class);
                gson = new Gson();
                myJson = gson.toJson(newProduct);
                intent.putExtra("product", myJson);
                startActivity(intent);
            }
        });

        pickPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent galery;

                galery = new Intent();
                galery.setType( "image/*" );
                galery.setAction( Intent.ACTION_GET_CONTENT );

                startActivityForResult( Intent.createChooser( galery, "Sellect Picture" ), 1 );
            }
        });

        return view;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 1 && resultCode == -1){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( mContext.getContentResolver(), imageUri );

                profileImage.setImageBitmap( bitmap);
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }


    }

}
