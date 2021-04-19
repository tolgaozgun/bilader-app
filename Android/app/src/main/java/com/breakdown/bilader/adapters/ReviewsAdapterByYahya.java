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

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapterByYahya extends
                                   RecyclerView.Adapter< ReviewsAdapterByYahya.ReviewHolder > {
    private Context mContext;
    private List< Review > reviewList;

    public ReviewsAdapterByYahya( Context mContext, ArrayList< Review > reviews) {

        this.mContext = mContext;
        this.reviewList = reviews;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public ImageView imageReviewScreenAvatar;
        public TextView textUserName;
        public TextView textReview;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ReviewHolder( @NonNull View itemView ) {
            super( itemView );
            imageReviewScreenAvatar =
                    itemView.findViewById( R.id.image_card_reviews_avatar );
            textUserName = itemView.findViewById( R.id.text_card_reviews_user_name );
            textReview = itemView.findViewById( R.id.text_card_reviews_comment);
        }
    }


    @NonNull
    @Override
    public ReviewsAdapterByYahya.ReviewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_reviews, parent, false );

        return new ReviewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull ReviewsAdapterByYahya.ReviewHolder holder, int position ) {
        Review reviews;
        reviews = reviewList.get( position );

        holder.textReview.setText( reviews.getContent() );
        holder.textUserName.setText( reviews.getSentBy().getUserName());
        holder.imageReviewScreenAvatar.setImageResource( mContext.getResources().getIdentifier( reviews.getSentBy().getUserAvatar(),
                "drawable",
                mContext.getPackageName() ) );
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

}