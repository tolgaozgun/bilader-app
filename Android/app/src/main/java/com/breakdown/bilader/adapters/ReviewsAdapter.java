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
import com.breakdown.bilader.models.*;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This adapter is used for displaying, listing and updating a user's reviews.
 *
 * @author breakDown
 * @version 02.05.2021
 */

public class ReviewsAdapter extends
                            RecyclerView.Adapter< ReviewsAdapter.ReviewHolder > {
    private Context mContext;
    private List< Review > reviewList;

    public ReviewsAdapter( Context mContext, ArrayList< Review > reviews ) {

        this.mContext = mContext;
        this.reviewList = reviews;
    }

    /**
     * It is an holder class for reviews, it mainly includes review's content,
     * user' who wrote review image and name
     */
    public class ReviewHolder extends RecyclerView.ViewHolder {
        public ImageView imageReviewScreenAvatar;
        public TextView textUserName;
        public TextView textReviewContent;
        public TextView textReviewTime;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ReviewHolder( @NonNull View itemView ) {
            super( itemView );
            imageReviewScreenAvatar =
                    itemView.findViewById( R.id.image_card_reviews_avatar );
            textUserName =
                    itemView.findViewById( R.id.text_card_reviews_user_name );
            textReviewContent = itemView.findViewById( R.id.reviewsTextView );
            textReviewTime = itemView.findViewById( R.id.reviewsTimeView );
        }
    }


    @NonNull
    @Override

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */ public ReviewsAdapter.ReviewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_reviews, parent, false );

        return new ReviewHolder( itemView );
    }

    /**
     * This method sets holder's properties in the list by determining which
     * position it have
     *
     * @param holder,   object that holds properties
     * @param position, position of the current review of list
     */
    @Override
    public void onBindViewHolder( @NonNull ReviewsAdapter.ReviewHolder holder
            , int position ) {
        Review review;
        PrettyTime prettyTime;
        prettyTime = new PrettyTime();
        review = reviewList.get( position );

        holder.textUserName.setText( review.getSentBy().getName() );
        Picasso.get().load( review.getSentBy().getAvatar() ).fit().centerCrop().into( holder.imageReviewScreenAvatar );
        holder.textReviewContent.setText( review.getContent() );
        holder.textReviewTime.setText( prettyTime.format( new Date( review.getTime() ) ) );
    }

    @Override
    public int getItemCount() {
        if ( reviewList == null ) {
            return 0;
        }
        return reviewList.size();
    }

}