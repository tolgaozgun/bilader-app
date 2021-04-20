package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

public class EditProductActivity extends Activity {

    private EditText editPrice;
    private EditText editTitle;
    private EditText editDescription;
    private Button editButton;
    private Button onSaleButton;
    private Button soldButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproduct);

        Product editedProduct;

        //temporary values
        editedProduct = new Product(new Category("Clothes",12), new User("Korhan","mail","avatar_male","12"));

        editButton = findViewById(R.id.editButton);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        onSaleButton = findViewById(R.id.onSale);
        soldButton = findViewById(R.id.soldButton);
        editPrice = findViewById(R.id.editPrice);

        String editedDescription = editDescription.getText().toString();
        String editedTitle = editTitle.getText().toString();
        String editedPrice = editPrice.getText().toString();

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
                editedProduct.setDescription(editedDescription);
                editedProduct.setPicture("pic");//It will change
                editedProduct.setTitle(editedTitle);
                editedProduct.setPrice(Double.parseDouble(editedPrice));
                Intent intent = new Intent(EditProductActivity.this, ProductActivity.class);
                intent.putExtra("product", editedProduct);

            }

        });
    }
}
