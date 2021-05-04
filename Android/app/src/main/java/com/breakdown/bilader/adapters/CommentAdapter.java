package com.breakdown.bilader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentAdapter extends
                            RecyclerView.Adapter< CommentAdapter.CommentHolder > {
    private Context mContext;
    private List< String > comments;
    private String currentUserId;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext is the location of the current fragment and its internal
     *                 elements and methods
     * @param comments list of the comments
     */
    public CommentAdapter( Context mContext, ArrayList< String > comments,
                           String currentUserId ) {

        this.mContext = mContext;
        this.comments = comments;
        this.currentUserId = currentUserId;
    }

    /**
     * A class that holds id's of elements in layout
     */
    public class CommentHolder extends RecyclerView.ViewHolder {
        public EditText textComment;
        public Button submitReviewButton;


        /**
         * A constructor that holds id's of itemViews
         *
         * @param itemView is the references of an item
         */
        public CommentHolder( @NonNull View itemView ) {
            super( itemView );

            textComment = itemView.findViewById( R.id.reviewsEditText );
            submitReviewButton =
                    itemView.findViewById( R.id.submitReviewButton );
        }
    }

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given card_type_review
     */
    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_type_review, parent, false );


        return new CommentHolder( itemView );
    }

    /**
     * a method called by RecyclerView to display the data at the specified
     * position
     *
     * @param holder   is the ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position is The position of the item within the adapter's data
     *                 set.
     */
    @Override
    public void onBindViewHolder( @NonNull CommentAdapter.CommentHolder holder, int position ) {
        String comment;
        comment = comments.get( position );
        holder.textComment.setText( comment );
        holder.submitReviewButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                submitReview( holder.textComment.getText().toString(),
                        currentUserId );
            }
        } );
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if ( comments == null ) {
            return 0;
        }
        return comments.size();
    }


    private void submitReview( String content, String userId ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "content", content );
        params.put( "receiver_id", userId );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    Toast.makeText( mContext, object.getString( "message" ),
                            Toast.LENGTH_SHORT ).show();
                } catch ( JSONException e ) {
                    Toast.makeText( mContext, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( mContext, message, Toast.LENGTH_SHORT ).show();

            }
        }, RequestType.ADD_REVIEW, params, mContext, true );
    }

}