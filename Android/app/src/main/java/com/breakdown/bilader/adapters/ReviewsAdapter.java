package com.breakdown.bilader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends
                            RecyclerView.Adapter< ReviewsAdapter.ReviewsHolder > {
    private Fragment mContext;
    private List< Review > reviews;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext  is the location of the current fragment and its internal
     *                  elements and methods
     * @param reviews list of the entered reviews
     */
    public ReviewsAdapter( Fragment mContext, ArrayList< Review > reviews ) {

        this.mContext = mContext;
        this.reviews = reviews;
    }

    /**
     * A class that holds id's of elements in layout
     */
    public class ReviewsHolder extends RecyclerView.ViewHolder {

        public ImageView imageUserAvatar;
        public TextView userName;
        public TextView content;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ReviewsHolder( @NonNull View itemView ) {
            super( itemView );

            //itemView.finViewByIds();
        }
    }

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                             int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_reviews, parent, false );

        return new ReviewsHolder( itemView );
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
    public void onBindViewHolder( @NonNull ReviewsHolder holder,
                                  int position ) {
        Review enteredReview;

        enteredReview = reviews.get( position );
        holder.userName.setText( enteredReview.getSentBy().getName() );
        Picasso.get().load( enteredReview.getSentBy().getAvatar() ).fit().centerInside().into( holder.imageUserAvatar );

        // holder.content
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of entered reviews in this adapter.
     */
    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
