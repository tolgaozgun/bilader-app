package com.breakdown.bilader.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.MyProductsActivity;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Notification;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NotificationAdapter extends
                                 RecyclerView.Adapter< NotificationAdapter.NotificationHolder > {

    private Context mContext;
    private List< Notification > notificationList;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext         is the location of the current fragment and its
     *                         internal elements and methods
     * @param notificationList list of the received notifications
     */
    public NotificationAdapter( Context mContext,
                                List< Notification > notificationList ) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    /**
     * A class that finds xml id's of layout elements
     */
    public class NotificationHolder extends RecyclerView.ViewHolder {
        public ImageView imageAvatar;
        public TextView contentOfTheNotification;
        public TextView time;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public NotificationHolder( @NonNull View itemView ) {
            super( itemView );

            imageAvatar =
                    itemView.findViewById( R.id.image_notifications_avatar );
            contentOfTheNotification =
                    itemView.findViewById( R.id.text_notifications_context );
            time = itemView.findViewById( R.id.text_notifications_time );
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
    public NotificationAdapter.NotificationHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_notifications, parent, false );

        return new NotificationAdapter.NotificationHolder( itemView );
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
    public void onBindViewHolder( @NonNull NotificationHolder holder,
                                  int position ) {
        Notification notification;
        PrettyTime prettyTime;
        notification = notificationList.get( position );
        prettyTime = new PrettyTime();

        holder.contentOfTheNotification.setText( notification.getSmallContent() );
        holder.time.setText( prettyTime.format( new Date( notification.getTime() ) ) );
        Picasso.get().load( notification.getAvatarURL() ).fit().centerCrop().into( holder.imageAvatar );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent;
                intent = notification.buildIntent();
                mContext.startActivity( intent );
            }
        } );
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of notifications in this adapter.
     */
    @Override
    public int getItemCount() {
        if ( notificationList == null ) {
            return 0;
        }
        return notificationList.size();
    }


}
