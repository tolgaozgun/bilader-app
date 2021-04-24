package com.breakdown.bilader.adapters;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.breakdown.bilader.R;

import java.util.UUID;

public class NotificationAdapter {

    private String content;
    private String avatarURL;
    private long time;
    private Context context;
    private int notificationId;
    private final String CHANNEL_ID;

    public NotificationAdapter( int notificationId, String content,
                                String avatarURL, long time, Context context ) {
        this.content = content;
        this.avatarURL = avatarURL;
        this.time = time;
        this.context = context;
        this.notificationId = notificationId;
        CHANNEL_ID = UUID.randomUUID().toString();
        createNotificationChannel();
    }


    public void buildNotification() {
        NotificationCompat.Builder builder;
        NotificationManagerCompat notificationManager;
        builder = new NotificationCompat.Builder( context, CHANNEL_ID )
                .setSmallIcon( R.drawable.vector_logo )
                .setContentTitle( "Bilader" )
                .setContentText( content )
                .setPriority( NotificationCompat.PRIORITY_DEFAULT );
        notificationManager = NotificationManagerCompat.from( context );
        notificationManager.notify( notificationId, builder.build() );
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            CharSequence name = context.getString( R.string.channel_name );
            String description =
                    context.getString( R.string.channel_description );
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel( CHANNEL_ID
                    , name, importance );
            channel.setDescription( description );
            // Register the channel with the system; you can't change the
            // importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    context.getSystemService( NotificationManager.class );
            notificationManager.createNotificationChannel( channel );
        }
    }

}
