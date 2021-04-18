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

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>{
    private Fragment mContext;
    private List< Review > reviews;

    public ReviewsAdapter( Fragment mContext, ArrayList< Review > reviews) {
        this.mContext = mContext;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                             int viewType ) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.card_reviews,parent,false);

        return new ReviewsHolder( itemView);
    }

    @Override
    public void onBindViewHolder( @NonNull ReviewsHolder holder,
                                  int position ) {
        Review enteredReview;

        enteredReview = reviews.get( position);
        holder.userName.setText( enteredReview.getSentBy().getUserName());
        holder.imageUserAvatar.setImageResource(mContext.getResources().getIdentifier(enteredReview.getSentBy().getUserAvatar(),
                "drawable", mContext.getActivity().getPackageName()));
        // holder.content
    }

    @Override
    public int getItemCount() {

        return reviews.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {

        public ImageView imageUserAvatar;
        public TextView userName;
        public TextView content;

        public ReviewsHolder( @NonNull View itemView ) {
            super( itemView );

            //itemView.finViewByIds();
        }
    }
}
