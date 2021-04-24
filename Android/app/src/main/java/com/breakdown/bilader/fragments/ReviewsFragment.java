package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ConcatAdapter;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.CommentAdapter;
import com.breakdown.bilader.adapters.ReviewsAdapter;
import com.breakdown.bilader.adapters.ReviewsAdapterByYahya;
import com.breakdown.bilader.models.Review;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;

public class ReviewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList< Review > reviewList;
    private ArrayList< String > comments;
    private ReviewsAdapterByYahya adapter;
    private CommentAdapter adapter1;
    private ReviewsAdapterByYahya adapter2;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater            is the LayoutInflater object that can be used
     *                            to inflate any views in the fragment
     * @param container:          If non-null, this is the parent view that the
     *                            fragment's UI should be attached to. The
     *                            fragment should not add the view itself, but
     *                            this can be used to generate the LayoutParams
     *                            of the view.
     * @param savedInstanceState: If non-null, this fragment is being
     *                            re-constructed from a previous saved state as
     *                            given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view;
        RecyclerView recyclerView;

        view = inflater.inflate( R.layout.fragment_reviews, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.reviews_recycler );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        /*
        // sample users for testing
        User user1 = new User( "Yahya Demirel", "mail@mail.com", "avatar_male"
                , "12" );
        User user2 = new User( "Burcu Kaplan", "mail@mail.com",
                "avatar_female", "12" );
        User user3 = new User( "Korhan Kaya", "mail@mail.com", "avatar_male",
                "12" );
        User user4 = new User( "Deniz Gökçen", "mail@mail.com",
                "avatar_female", "12" );
        User user5 = new User( "Tolga Özgün", "mail@mail.com", "avatar_male",
                "12" );

        Review review1 = new Review( user1, "I enjoyed with the trade");
        Review review2 = new Review( user2,"Where is my money bro" );
        Review review3 = new Review( user3,"Economy is just perfect" );
        Review review4 = new Review( user3,"Economy is just perfect" );
        Review review5 = new Review( user3,"Economy is just perfect" );
        */

        comments = new ArrayList<>();

        String comment = "";
        comments.add( comment );

        reviewList = new ArrayList<>();
        /*
        reviewList.add( review1 );
        reviewList.add( review2 );
        reviewList.add( review3 );
        reviewList.add( review4 );
        reviewList.add( review5 );*/

        adapter1 = new CommentAdapter( getContext(), comments );
        adapter2 = new ReviewsAdapterByYahya( getContext(), reviewList );

        ConcatAdapter concatenated = new ConcatAdapter(adapter1, adapter2);

        recyclerView.setAdapter( concatenated );

        return view;
    }

    public int getReviewNumber() {
        return adapter2.getItemCount();
    }
}