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

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends
                            RecyclerView.Adapter< ReviewsAdapter.ReviewHolder > {
    private Context mContext;
    private List< Review > reviewList;

    public ReviewsAdapter( Context mContext, ArrayList< Review > reviews ) {

        this.mContext = mContext;
        this.reviewList = reviews;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public ImageView imageReviewScreenAvatar;
        public TextView textUserName;
        public TextView textReviewContent;

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
            textReviewContent =
                    itemView.findViewById( R.id.reviewsTextView );
        }
    }


    @NonNull
    @Override
    public ReviewsAdapter.ReviewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_reviews, parent, false );

        return new ReviewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull ReviewsAdapter.ReviewHolder holder
            , int position ) {
        Review reviews;
        reviews = reviewList.get( position );

        holder.textUserName.setText( reviews.getSentBy().getName() );
        Picasso.get().load( reviews.getSentBy().getAvatar() ).fit().centerCrop().into( holder.imageReviewScreenAvatar );
        holder.textReviewContent.setText( reviews.getContent() );
    }

    @Override
    public int getItemCount() {
        if ( reviewList == null ) {
            return 0;
        }
        return reviewList.size();
    }

}