package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ImageLoadAdapter;
import com.breakdown.bilader.controllers.ProductActivity;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

    private ArrayList<Uri> uriList;
    private List<String> imagesEncodedList;
    private ImageLoadAdapter adapter;
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        view = inflater.inflate( R.layout.fragment_addnewproduct, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.recyclerImageHolder);


        recyclerView.setLayoutManager( new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL ) );

        mContext = getActivity();
        price = view.findViewById(R.id.priceAddNewProduct);
        title = view.findViewById(R.id.titleAddNewProduct);
        description = view.findViewById(R.id.descriptionAddNewProduct);
        postButton = view.findViewById(R.id.postButton);
        categoryButton = view.findViewById(R.id.category_button);
        pickPhotoButton = view.findViewById(R.id.button_add);
        profileImage = view.findViewById( R.id.recyclerImageHolder );
        uriList = new ArrayList<Uri>();
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
                galery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galery.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galery, "Sellect Picture" ), 1 );
            }
        });

        return view;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode,
                                  @Nullable Intent data ) {
        int position;
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 1 && resultCode == -1 && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    uriList.add(imageurl);
                }
                position = 0;
            } else {
                Uri imageurl = data.getData();
            }
        } else {
            // show this if no image is selected
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
        adapter = new ImageLoadAdapter( getContext(), uriList );
        recyclerView.setAdapter( adapter );
    }

}
