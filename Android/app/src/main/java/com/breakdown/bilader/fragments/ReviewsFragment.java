package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ConcatAdapter;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.CommentAdapter;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.ReviewsAdapter;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Review;
import com.breakdown.bilader.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ReviewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList< Review > reviewList;
    private ArrayList< String > comments;
    private ReviewsAdapter adapter;
    private String currentUserId;
    private CommentAdapter commentAdapter;
    private Button submitReviewButton;
    private EditText reviewsEditText;
    private ReviewsAdapter reviewsAdapter;

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
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        Bundle bundle;
        View view;

        view = inflater.inflate( R.layout.fragment_reviews, container, false );

        recyclerView =
                ( RecyclerView ) view.findViewById( R.id.reviews_recycler );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        bundle = getArguments();
        currentUserId = bundle.getString( "user_id" );
        comments = new ArrayList<>();

        String comment = "";
        comments.add( comment );

        retrieveReviews( recyclerView );
        return view;
    }

    public int getReviewNumber() {
        return reviewsAdapter.getItemCount();
    }


    private void retrieveReviews( RecyclerView recyclerView ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "receiver_id", currentUserId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Review review;
                long time;
                String reviewId;
                String senderId;
                String content;
                String userName;
                String userAvatarURL;
                User sender;

                Iterator< String > keys;
                JSONObject tempJson;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        reviewList = new ArrayList< Review >();
                        keys = object.getJSONObject( "reviews" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "reviews" ).getJSONObject( key );

                            reviewId = tempJson.getString( "id" );
                            userName = tempJson.getString( "sender_name" );
                            content = tempJson.getString( "content" );
                            senderId = tempJson.getString( "sender_id" );
                            userAvatarURL = tempJson.getString(
                                    "sender_avatar" );
                            System.out.println( "NAME: " + userName + " " +
                                    "review: " + content );
                            sender = new User( userName, userAvatarURL,
                                    senderId );
                            review = new Review( reviewId, sender, content );
                            reviewList.add( review );
                        }
                    }
                    printView( recyclerView );
                } catch ( JSONException e ) {
                    e.printStackTrace();
                    printView( recyclerView );
                }
            }

            @Override
            public void onFail( String message ) {
                printView( recyclerView );
            }
        }, RequestType.RETRIEVE_REVIEWS, params, this.getContext(), true );
    }

    private void printView( RecyclerView recyclerView ) {


        commentAdapter = new CommentAdapter( getContext(), comments,
                currentUserId );
        reviewsAdapter = new ReviewsAdapter( getContext(), reviewList );

        ConcatAdapter concatenated = new ConcatAdapter( commentAdapter,
                reviewsAdapter );

        recyclerView.setAdapter( concatenated );
    }
}