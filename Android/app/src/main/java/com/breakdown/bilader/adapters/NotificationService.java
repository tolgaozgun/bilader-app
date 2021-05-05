package com.breakdown.bilader.adapters;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.breakdown.bilader.models.Notification;
import com.breakdown.bilader.models.NotificationType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NotificationService extends Service {
    private int mInterval = 20000; // 20 seconds by default, can be changed
    // later
    private Handler mHandler;
    private Runnable mStatusChecker;
    private SharedPreferences sharedPreferences;
    private Map< String, String > params;
    private String token;
    private String userId;
    private String content;
    private String avatarURL;
    private String title;
    private Notification notification;
    private long time;
    private int notificationId;

    void updateStatus() {

    }


    @Nullable
    @Override
    public IBinder onBind( Intent intent ) {
        return null;
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ) {
        mHandler = new Handler();
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );
        params = new HashMap< String, String >();
        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                try {
                    updateStatus(); //this function can change value of
                    // mInterval.
                } finally {
                    doTask();
                    // 100% guarantee that this always happens, even if
                    // your update method throws an exception
                    mHandler.postDelayed( mStatusChecker, mInterval );
                }
            }
        };
        startRepeatingTask();
        return START_STICKY;
    }

    private void doTask() {
        long lastTime;
        lastTime = sharedPreferences.getLong( "LAST_NOTIFICATION_TIME", 0L );
        params.put( "time", String.valueOf( lastTime ) );
        if ( sharedPreferences.contains( "session_token" ) && sharedPreferences.contains( "id" ) ) {
            token = sharedPreferences.getString( "session_token", "" );
            userId = sharedPreferences.getString( "id", "" );
            HttpAdapter.getRequestJSON( new VolleyCallback() {
                @Override
                public void onSuccess( JSONObject object ) {
                    Iterator< String > keys;
                    JSONObject tempNotif;
                    String smallContent;
                    String extraId;
                    String typeString;

                    try {
                        if ( object.getBoolean( "success" ) ) {
                            keys = object.getJSONObject( "notifications" ).keys();
                            while ( keys.hasNext() ) {
                                String key = keys.next();
                                tempNotif = object.getJSONObject(
                                        "notifications" ).getJSONObject( key );

                                time = tempNotif.getLong( "time" );
                                sharedPreferences.edit().putLong(
                                        "LAST_NOTIFICATION_TIME", time ).apply();
                                content = tempNotif.getString( "content" );
                                smallContent = tempNotif.getString(
                                        "small_content" );
                                extraId = tempNotif.getString( "extra_id" );
                                typeString = tempNotif.getString( "type" );
                                title = tempNotif.getString( "title" );
                                avatarURL = tempNotif.getString( "image" );
                                notificationId = tempNotif.getInt(
                                        "notification_id" );
                                notification =
                                        new Notification( notificationId,
                                                content, smallContent, title,
                                                avatarURL, extraId, time,
                                                NotificationService.this,
                                                NotificationType.valueOf( typeString ) );
                                notification.buildNotification();
                            }
                        }
                    } catch ( JSONException e ) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail( String message ) {

                }
            }, RequestType.NOTIFICATION, params, getApplicationContext(),
                    false );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks( mStatusChecker );
    }
}
