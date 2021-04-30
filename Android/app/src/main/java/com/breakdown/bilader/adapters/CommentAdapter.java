package com.breakdown.bilader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Review;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends
                                   RecyclerView.Adapter< CommentAdapter.CommentHolder > {
    private Context mContext;
    private List< String > comments;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext  is the location of the current fragment and its internal
     *                  elements and methods
     * @param comments list of the comments
     */
    public CommentAdapter( Context mContext, ArrayList< String > comments) {

        this.mContext = mContext;
        this.comments = comments;
    }

    /**
     * A class that holds id's of elements in layout
     */
    public class CommentHolder extends RecyclerView.ViewHolder {
        public TextView textComment;

        /**
         * A constructor that holds id's of itemViews
         *
         * @param itemView is the references of an item
         */
        public CommentHolder( @NonNull View itemView ) {
            super( itemView );

            textComment= itemView.findViewById( R.id.text_card_reviews_comment);
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
    public void onBindViewHolder( @NonNull CommentAdapter.CommentHolder  holder, int position ) {
        String comment;
        comment = comments.get( position );
        holder.textComment.setText( comment );
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return comments.size();
    }

}