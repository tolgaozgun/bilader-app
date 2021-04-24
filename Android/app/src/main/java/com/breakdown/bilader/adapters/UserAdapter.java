package com.breakdown.bilader.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.OthersProfileActivity;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The fragment class that makes connection between UI component and data source
 * of the followers that helps us to fill data in UI component.
 *
 * @author Yahya Eren Demirel
 * @version 18.04.2021
 */

public class UserAdapter extends
                         RecyclerView.Adapter< UserAdapter.FollowerHolder > {
    private Context mContext;
    private ArrayList< User > followers;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext  is the location of the current fragment and its internal
     *                  elements and methods
     * @param followers list of the follower
     */
    public UserAdapter( Context mContext, ArrayList< User > followers ) {
        this.mContext = mContext;
        this.followers = followers;
    }

    /**
     * A class that finds xml id's of layout elements
     */
    public class FollowerHolder extends RecyclerView.ViewHolder {
        public ImageView imageFollowerScreenAvatar;
        public TextView textUserName;
        public CardView userCard;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public FollowerHolder( @NonNull View itemView ) {
            super( itemView );
            imageFollowerScreenAvatar =
                    itemView.findViewById( R.id.image_follower_screen_avatar );
            textUserName = itemView.findViewById( R.id.text_user_name );
            userCard = itemView.findViewById( R.id.card_followers );
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
    public FollowerHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                              int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_followers, parent, false );

        return new FollowerHolder( itemView );
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
    public void onBindViewHolder( @NonNull FollowerHolder holder,
                                  int position ) {
        User user;

        user = followers.get( position );

        holder.textUserName.setText( user.getUserName() );
        Picasso.get().load( user.getUserAvatar() ).fit().centerInside().into( holder.imageFollowerScreenAvatar );

        holder.userCard.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {
                Intent intent;
                Gson gson;
                String myJson;


                intent = new Intent( mContext, OthersProfileActivity.class );
                gson = new Gson();
                myJson = gson.toJson( user );
                intent.putExtra( "user", myJson );
                mContext.startActivity( intent );

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
        if ( followers != null ) {
            return followers.size();
        }
        return 0;
    }


}
