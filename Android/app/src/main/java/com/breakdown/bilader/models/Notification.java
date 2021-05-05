package com.breakdown.bilader.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.controllers.BiltraderActivity;
import com.breakdown.bilader.controllers.MainChatActivity;
import com.breakdown.bilader.controllers.OthersProfileActivity;
import com.breakdown.bilader.controllers.PrivateChatActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * A class that represents notifications, to be used to notify users when there
 * is message etc.
 *
 * @author breakDown
 * @version 19.04.2021
 */

public class Notification implements Comparable {

    private String content;
    private String avatarURL;
    private String extraId;
    private String smallContent;
    private long time;
    private String title;
    private Context context;
    private int notificationId;
    private NotificationType type;
    private final String CHANNEL_ID;

    /**
     * Constructor
     *
     * @param notificationId Integer id of the notification
     * @param content        String content of the notification
     * @param smallContent   String one line content of the notification
     * @param title          String title of the notification
     * @param avatarURL      String image url of the notification
     * @param extraId        String provided extra ID of the notification
     *                       (product_id or user_id based on context)
     * @param time           long epoch time of the notification
     * @param context        Context value of Notification's context.
     * @param type           NotificationType value of type.
     */
    public Notification( int notificationId, String content,
                         String smallContent, String title, String avatarURL,
                         String extraId, long time, Context context,
                         NotificationType type ) {
        this.content = content;
        this.avatarURL = avatarURL;
        this.time = time;
        this.title = title;
        this.context = context;
        this.extraId = extraId;
        this.smallContent = smallContent;
        this.notificationId = notificationId;
        this.type = type;
        CHANNEL_ID = UUID.randomUUID().toString();
        createNotificationChannel();
    }


    /**
     * Builds the push notification and displays it to the user.
     */
    public void buildNotification() {
        PendingIntent contentIntent;
        NotificationCompat.Builder builder;
        NotificationManagerCompat notificationManager;
        builder =
                new NotificationCompat.Builder( context, CHANNEL_ID ).setSmallIcon( R.drawable.vector_logo ).setLargeIcon( getBitmapFromURL( avatarURL ) ).setContentTitle( title ).setContentText( content ).setPriority( NotificationCompat.PRIORITY_DEFAULT );

        contentIntent = PendingIntent.getBroadcast( context,
                UUID.randomUUID().hashCode(), buildIntent(),
                PendingIntent.FLAG_UPDATE_CURRENT );


        builder.setContentIntent( contentIntent );
        notificationManager = NotificationManagerCompat.from( context );
        notificationManager.notify( notificationId, builder.build() );


    }

    /**
     * Builds the intent which lets the user click the notification.
     *
     * @return Intent object of the Notification.
     */
    public Intent buildIntent() {
        User user;
        Gson gson;
        Intent intent;
        String myJson;
        gson = new Gson();
        switch ( type ) {
            case MESSAGE:
                user = new User( title, avatarURL, extraId );
                myJson = gson.toJson( user );
                intent = new Intent( context, PrivateChatActivity.class );
                intent.putExtra( "user", myJson );
                break;
            case FOLLOW:
            case UNFOLLOW:
                user = new User( title, avatarURL, extraId );
                myJson = gson.toJson( user );
                intent = new Intent( context, OthersProfileActivity.class );
                intent.putExtra( "user", myJson );
                break;
            default:
                intent = new Intent( context, BiltraderActivity.class );
                break;
        }
        return intent;
    }


    /**
     * Creates bitmap from URL so we can display images from URL.
     *
     * @param src String URL of the image.
     * @return Bitmap object of image.
     */
    private static Bitmap getBitmapFromURL( String src ) {
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );
        try {
            URL url = new URL( src );
            HttpURLConnection connection =
                    ( HttpURLConnection ) url.openConnection();
            connection.setDoInput( true );
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream( input );
            return myBitmap;
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates notification channel.
     */
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

    /**
     * Returns the Notification content.
     *
     * @return String notification content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the Notification image.
     *
     * @return String notification image URL.
     */
    public String getAvatarURL() {
        return avatarURL;
    }

    /**
     * Returns the Notification time.
     *
     * @return Long value of notification time.
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns the Notification title.
     *
     * @return String notification title.
     */
    public String getTitle() {
        return title;
    }


    /**
     * Returns the Notification context.
     *
     * @return Context value of notification context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Returns the Notification small content.
     *
     * @return String value of notification's one line content.
     */
    public String getSmallContent() {
        return smallContent;
    }

    /**
     * Returns the Notification type.
     *
     * @return NotificationType value of notification type.
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Compares the notifications using their time sent.
     *
     * @param o Another object to be compared with this notification.
     * @return -1,0,1 based on time comparison.
     */
    @Override
    public int compareTo( Object o ) {
        if ( o instanceof Notification ) {
            Long currentTime = new Long( time );
            return currentTime.compareTo( ( ( Notification ) o ).getTime() );
        }
        return 0;
    }
}
