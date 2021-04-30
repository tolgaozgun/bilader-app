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

    public CommentAdapter( Context mContext, ArrayList< String > comments) {

        this.mContext = mContext;
        this.comments = comments;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        public TextView textComment;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public CommentHolder( @NonNull View itemView ) {
            super( itemView );

            textComment= itemView.findViewById( R.id.text_card_reviews_comment);
        }
    }


    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_type_review, parent, false );

        return new CommentHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull CommentAdapter.CommentHolder  holder, int position ) {
        String comment;
        comment = comments.get( position );

        holder.textComment.setText( comment );
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}