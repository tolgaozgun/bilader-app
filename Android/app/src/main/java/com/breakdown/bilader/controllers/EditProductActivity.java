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

public class EditProductActivity extends Activity {

    private EditText editPrice;
    private EditText editTitle;
    private EditText editDescription;
    private Button editButton;
    private Button onSaleButton;
    private Button soldButton;
    private Product editedProduct;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproduct);


        //temporary values
        editedProduct = new Product( new User("Korhan","mail","avatar_male","12"), false ,"2");

        editButton = findViewById(R.id.editButton);
        onSaleButton = findViewById(R.id.onSale);
        soldButton = findViewById(R.id.soldButton);


        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedProduct.changeSoldSituation();
            }
        });

        onSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedProduct.changeSoldSituation();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });
    }

    private void editProduct( Product editedProduct ) {
        String editedDescription;
        String editedTitle;
        String editedPrice;

        editDescription = findViewById(R.id.editDescription);
        editTitle = findViewById(R.id.editTitle);
        editPrice = findViewById(R.id.editPrice);

        editedDescription = editDescription.getText().toString();
        editedTitle = editTitle.getText().toString();
        editedPrice = editPrice.getText().toString();



        Intent intent;
        Gson gson;
        String myJson;
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
