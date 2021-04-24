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

public class NotificationScreenAdapter extends RecyclerView.Adapter< NotificationScreenAdapter.NotificationHolder> {

    private Context mContext;
    private List< Notification > notificationList;

    public NotificationScreenAdapter( Context mContext, List<Notification> notificationList ) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        public ImageView imageAvatar;
        public TextView contentOfTheNotification;
        public TextView time;

        public NotificationHolder( @NonNull View itemView ) {
            super( itemView );

            imageAvatar = itemView.findViewById( R.id.image_notifications_avatar );
            contentOfTheNotification = itemView.findViewById( R.id.text_notifications_context );
            time = itemView.findViewById( R.id.text_notifications_time );
        }
    }

    @NonNull
    @Override
    public NotificationScreenAdapter.NotificationHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_notifications, parent, false );

        return new NotificationScreenAdapter.NotificationHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull NotificationHolder holder,
                                  int position ) {
        Notification notifications;
        notifications = notificationList.get( position );

        holder.contentOfTheNotification.setText( notifications.getText() );
        //holder.time.setText( notifications.);
        holder.imageAvatar.setImageResource( mContext.getResources().getIdentifier( notifications.getAvatarUrl(),
                "drawable",
                mContext.getPackageName() ) );
    }




    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
