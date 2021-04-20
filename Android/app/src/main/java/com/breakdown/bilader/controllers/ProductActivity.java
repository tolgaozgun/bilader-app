package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Product;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        settingsButton = findViewById(R.id.settingsProduct);
        addWishlistButton = findViewById(R.id.addToWishlist);
        directChatButton = findViewById(R.id.directChat);
        productName = findViewById(R.id.titleProduct);
        productDescription = findViewById(R.id.description);
        ownerName = findViewById(R.id.ownerName);
        price = findViewById(R.id.price);
        category = findViewById(R.id.categoryName);

        currentProduct = (Product) getIntent().getSerializableExtra("product");
        productName.setText(currentProduct.getTitle());
        productDescription.setText(currentProduct.getDescription());
        price.setText(String.valueOf(currentProduct.getPrice()));
        ownerName.setText(currentProduct.getOwner().toString());
        category.setText(currentProduct.getCategory().toString());

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This will identify who is the clicker than display two diff pop-ups.
                //TODO
            }
        });

        addWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        directChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });


    }
}
