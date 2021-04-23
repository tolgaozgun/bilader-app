package com.breakdown.bilader.adapters;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationService extends Service {
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private Runnable mStatusChecker;
    private SharedPreferences sharedPreferences;
    private Map<String, String> params;
    private String token;
    private String userId;

    void updateStatus(){

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
        params = new HashMap<String, String>();
        params.put( "time", "0" );
        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                try {
                    updateStatus(); //this function can change value of mInterval.
                } finally {
                    if(sharedPreferences.contains( "session_token" ) && sharedPreferences.contains( "id" )){
                        token = sharedPreferences.getString( "session_token", "" );
                        userId = sharedPreferences.getString( "id", "" );
                        HttpAdapter.getRequestJSON( new VolleyCallback() {
                            @Override
                            public void onSuccess( JSONObject object ) {
                                System.out.println(object.toString());
                            }

                            @Override
                            public void onFail( String message ) {

                            }
                        }, RequestType.NOTIFICATION, params, getApplicationContext() );
                    }
                    // 100% guarantee that this always happens, even if
                    // your update method throws an exception
                    mHandler.postDelayed(mStatusChecker, mInterval);
                }
            }
        };
        startRepeatingTask();
        return START_STICKY;
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
        mHandler.removeCallbacks(mStatusChecker);
    }
}
