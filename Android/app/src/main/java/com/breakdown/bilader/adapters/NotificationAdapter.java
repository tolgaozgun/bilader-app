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
import com.breakdown.bilader.models.Notification;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter< NotificationAdapter.NotificationHolder> {

    private Context mContext;
    private List< Notification > notificationList;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param mContext  is the location of the current fragment and its internal
     *                  elements and methods
     * @param notificationList list of the received notifications
     */
    public NotificationAdapter( Context mContext, List<Notification> notificationList ) {
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

            imageAvatar = itemView.findViewById( R.id.image_notifications_avatar );
            contentOfTheNotification = itemView.findViewById( R.id.text_notifications_context );
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
        Notification notifications;
        notifications = notificationList.get( position );

        holder.contentOfTheNotification.setText( notifications.getContent() );
        //holder.time.setText( notifications.);
        holder.imageAvatar.setImageResource( mContext.getResources().getIdentifier( notifications.getAvatarURL(),
                "drawable",
                mContext.getPackageName() ) );
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of notifications in this adapter.
     */
    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
