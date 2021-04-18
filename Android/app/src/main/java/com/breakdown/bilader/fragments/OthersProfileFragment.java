package com.breakdown.bilader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.adapters.ReviewsAdapter;
import com.breakdown.bilader.controllers.ReportActivity;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.Review;
import com.breakdown.bilader.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OthersProfileFragment extends Fragment {

    private ImageView profilePhoto;
    private TextView userName;
    private TextView numberOfFollowers;
    private TextView numberOfFollowings;
    private TextView numberOfReviews;
    private TextView numberOfProducts;
    private EditText contentOfTheReview;
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewReviews;
    private Button onSale;
    private Button reviews;
    private Button follow;
    private Button sendMessage;
    private Button reportButton;
    private Button submitReport;
    private User userOne;
    private List< Product > userProducts;
    private List< Review > userReviews;
    private ProductAdapter productAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        View view;
        // this is required for variable view might not have been initialized
        // for now.
        view = null;

        view = inflater.inflate(R.layout.activity_othersprofile, container, false);

        userProducts = new ArrayList<>();
        userReviews = new ArrayList<>();

        profilePhoto = view.findViewById(R.id.profilePhoto);
        onSale = view.findViewById(R.id.onSale);
        reviews = view.findViewById(R.id.reviews);
        follow = view.findViewById(R.id.follow);

        getUserInfo();
        userProducts();
        userReviews();
        getProductCount();
        getFollowersCount();
        getFollowingsCount();

        recyclerViewProducts.setVisibility( View.VISIBLE );
        recyclerViewReviews.setVisibility( View.GONE );
        // contentOfTheReview.setVisibility( View.GONE);

        onSale.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                recyclerViewProducts.setVisibility( View.VISIBLE );
                recyclerViewReviews.setVisibility( View.GONE );
                // contentOfTheReview.setVisibility( View.GONE);
            }
        } );

        reviews.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                recyclerViewProducts.setVisibility( View.GONE );
                recyclerViewReviews.setVisibility( View.VISIBLE );
                // contentOfTheReview.setVisibility( View.VISIBLE);
            }
        } );

        reportButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent newIntent;

                newIntent = new Intent( getActivity(), ReportActivity.class );

                // To be continue...
                newIntent.putExtra( "userName", "Hello" );
                startActivity( newIntent );
            }
        } );

        return view;
    }

    /**
     * Sets user information of the current user.
     *
     */
    public void getUserInfo() {
        userName.setText(userOne.getUserName());
        numberOfReviews.setText(userReviews.size());
        // profilePhoto
        // rating?
    }

    /**
     * Sets the followings count of the current user.
     *
     */
    public void getFollowingsCount() {
        //TODO
        //numberOfFollowings.setText( count);
    }

    /**
     * Sets the followers count of the current user.
     *
     */
    public void getFollowersCount() {
        //TODO
        //numberOfFollowers.setText( count);
    }

    /**
     * Shows the products of the current user.
     *
     */
    public void userProducts() {

        productAdapter = new ProductAdapter( this, ( ArrayList< Product > ) userProducts );
        recyclerViewProducts.setAdapter( productAdapter );
    }

    /**
     * Shows the reviews of the current user.
     *
     */
    public void userReviews() {

        reviewsAdapter = new ReviewsAdapter( this, ( ArrayList< Review > ) userReviews );
        recyclerViewReviews.setAdapter( reviewsAdapter );
    }

    /**
     * Sets the product count of the current user.
     *
     */
    public void getProductCount() {

        numberOfProducts.setText( userProducts.size() );
    }
}
