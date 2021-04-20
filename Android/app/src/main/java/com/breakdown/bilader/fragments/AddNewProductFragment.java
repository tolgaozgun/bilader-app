package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.EditProductActivity;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

public class AddNewProductFragment extends Fragment {

    private EditText price;
    private EditText title;
    private EditText description;
    private Button postButton;
    private User currentUser;
    private Activity mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addnewproduct, container, false);

        mContext = getActivity();
        price = view.findViewById(R.id.priceAddNewProduct);
        title = view.findViewById(R.id.titleAddNewProduct);
        description = view.findViewById(R.id.descriptionAddNewProduct);
        postButton = view.findViewById(R.id.postButton);
        //currentUser = getUser();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceText;
                String titleText;
                String descriptionText;
                Product newProduct;

                priceText = price.getText().toString();
                titleText = title.getText().toString();
                descriptionText = description.getText().toString();
                newProduct = new Product("", titleText, descriptionText, Double.parseDouble(priceText),  currentUser, false, "7");
                Intent intent  = new Intent(mContext, ProductActivity.class);
                intent.putExtra("product", newProduct);
            }
        });

        return view;
    }
}
