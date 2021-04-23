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
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;


    /**
     * The fragment class that makes connection between UI component and data source
     * of the followers that helps us to fill data in UI component.
     *
     * @author Yahya Eren Demirel
     * @version 18.04.2021
     */

    public class MainChatAdapter extends
                                  RecyclerView.Adapter< MainChatAdapter.DialogHolder > {
        private Context mContext;
        private ArrayList< Message > lastMessages;
        private String chatId;


        public MainChatAdapter( Context mContext, ArrayList< Message > lastMessages , String chatId) {
            this.mContext = mContext;
            this.lastMessages = lastMessages;
            this.chatId = chatId;
        }

        /**
         * A class that finds xml id's of layout elements
         */
        public class DialogHolder extends RecyclerView.ViewHolder {
            public ImageView imageMainChatAvatar;
            public TextView textLastMessage;
            public TextView textTimeAgo;
            public CardView cardMainChat;

            /**
             * A constructor that holds id's of views
             *
             * @param itemView is the references of an item
             */
            public DialogHolder( @NonNull View itemView ) {
                super( itemView );
                imageMainChatAvatar =
                        itemView.findViewById( R.id.image_main_chat_avatar );
                textLastMessage = itemView.findViewById( R.id.text_main_last_message );
                textTimeAgo = itemView.findViewById( R.id.text_time_ago );
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
        public com.breakdown.bilader.adapters.MainChatAdapter.DialogHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                                                                                  int viewType ) {
            View itemView;

            itemView =
                    LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_main_chat, parent, false );

            return new com.breakdown.bilader.adapters.MainChatAdapter.DialogHolder( itemView );
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
        public void onBindViewHolder( @NonNull com.breakdown.bilader.adapters.MainChatAdapter.DialogHolder holder,
                                      int position ) {
            Message follower;

            follower = lastMessages.get( position );
            
            holder.cardMainChat.setOnClickListener( new View.OnClickListener() {

                public void onClick( View view ) {
                    Intent intent;

                    intent = new Intent( mContext, OthersProfileActivity.class );

                    //intent.putExtra( "follower", follower  );


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

            int size;

            size = lastMessages.size();

            return size;
        }



    }
