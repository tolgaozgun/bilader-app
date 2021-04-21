package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
                            categoryButton.setText("Book");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.electronic) {
                            newProduct.setCategory(new Category("1") );
                            categoryButton.setText("Electronıc");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.clothes ) {
                            newProduct.setCategory(new Category("2"));
                            categoryButton.setText("Clothes");
                            return true;
                        }
                        else if (  item.getItemId() == R.id.hoby) {
                            newProduct.setCategory(new Category("3"));
                            categoryButton.setText("Hoby");
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

        return view;
    }

}
