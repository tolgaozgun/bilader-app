package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;

/**
 * A class that helps edit a existing product's properties such as its title or price.
 * @author Korhan Kemal Kaya
 * @version 22.04.2021
 */

public class EditProductActivity extends Activity {

    private EditText editPrice;
    private EditText editTitle;
    private EditText editDescription;
    private Button editButton;
    private Button onSaleButton;
    private Button soldButton;
    private Product editedProduct;


    /**
     * this is the method where most initialization made such as UI and widgets
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproduct);

        editButton = findViewById(R.id.editButton);
        onSaleButton = findViewById(R.id.onSale);
        soldButton = findViewById(R.id.soldButton);


        soldButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the button clicked, it changes the current product's sold situation as sold.
             * @param v , refers to view of button
             */
            @Override
            public void onClick(View v) {
                editedProduct.changeSoldSituation();
            }
        });

        onSaleButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the button clicked, it changes the current product's sold situation as on sale.
             * @param v , refers to view of button
             */
            @Override
            public void onClick(View v) {
                editedProduct.changeSoldSituation();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * When the button clicked, it applies changes to product and display it in Biltrader screen with new properties.
             * @param v , refers to view of button
             */
            public void onClick(View v) {
                editProduct( editedProduct);
            }

        });
    }

    //TODO
    private void editProduct( Product editedProduct ) {
        String editedDescription;
        String editedTitle;
        String editedPrice;
        Intent intent;
        Gson gson;
        String myJson;

        editDescription = findViewById(R.id.editDescription);
        editTitle = findViewById(R.id.editTitle);
        editPrice = findViewById(R.id.editPrice);

        editedDescription = editDescription.getText().toString();
        editedTitle = editTitle.getText().toString();
        editedPrice = editPrice.getText().toString();

        editedProduct.setDescription(editedDescription);
        editedProduct.setPicture("pic");//It will change
        editedProduct.setTitle(editedTitle);
        editedProduct.setPrice(Double.parseDouble(editedPrice));

        intent = new Intent( EditProductActivity.this, ProductActivity.class );
        gson = new Gson();
        myJson = gson.toJson( editedProduct );
        intent.putExtra( "product", myJson );
        startActivity( intent );

        //TODO
        //Those above is here the reason same as add new product
    }
}
