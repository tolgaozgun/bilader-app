package com.breakdown.bilader.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.breakdown.bilader.controllers.SettingsActivity;
import com.breakdown.bilader.fragments.OthersProfileFragment;
import com.breakdown.bilader.models.*;

import java.util.*;

/**
 * The fragment class that makes connection between UI component and data source
 * of the followers that helps us to fill data in UI component.
 *
 * @author Yahya Eren Demirel
 * @version 18.04.2021
 */

public class FollowersAdapter extends
                              RecyclerView.Adapter< FollowersAdapter.FollowerHolder > {
    private Context mContext;
    private ArrayList< User > followers;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext  is the location of the current fragment and its internal
     *                  elements and methods
     * @param followers list of the follower
     */
    public FollowersAdapter( Context mContext, ArrayList< User > followers ) {
        this.mContext = mContext;
        this.followers = followers;
    }

    /**
     * A class that finds xml id's of layout elements
     */
    public class FollowerHolder extends RecyclerView.ViewHolder {
        public ImageView imageFollowerScreenAvatar;
        public TextView textUserName;
        public CardView followerCard;

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
            followerCard = itemView.findViewById( R.id.card_followers );
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
        User follower;

        follower = followers.get( position );

        holder.textUserName.setText( follower.getUserName() );
        holder.imageFollowerScreenAvatar.setImageResource( mContext.getResources().getIdentifier( follower.getUserAvatar(), "drawable", mContext.getPackageName() ) );

        holder.followerCard.setOnClickListener( new View.OnClickListener() {

            public void onClick( View view ) {
                Intent intent;

                //To
                intent = new Intent( mContext, OthersProfileActivity.class );

                intent.putExtra( "follower", follower  );

                Log.d("followersdebug", "debug");

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

        int followersSize;

        followersSize = followers.size();

        return followersSize;
    }



}
