package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.NotificationAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Notification;
import com.breakdown.bilader.models.NotificationType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A class that makes connection between its layout and data
 *
 * @author breakDown
 * @version 27.04.2021
 */

public class NotificationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List< Notification > notificationList;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater            is the LayoutInflater object that can be used
     *                            to inflate any views in the fragment
     * @param container:          If non-null, this is the parent view that the
     *                            fragment's UI should be attached to. The
     *                            fragment should not add the view itself, but
     *                            this can be used to generate the LayoutParams
     *                            of the view.
     * @param savedInstanceState: If non-null, this fragment is being
     *                            re-constructed from a previous saved state as
     *                            given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        View view;

        view = inflater.inflate( R.layout.fragment_notifications, container,
                false );

        recyclerView = view.findViewById( R.id.notificationRecycler );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        notificationAdapter = new NotificationAdapter( getContext(),
                notificationList );
        recyclerView.setAdapter( notificationAdapter );
        retrieveNotifications();
        return view;
    }

    private void retrieveNotifications() {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        params.put( "time", "0" );
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Iterator< String > keys;
                Notification notification;
                JSONObject tempJson;
                String content;
                String typeString;
                String extraId;
                String smallContent;
                String title;
                String image;
                long time;
                int id;
                try {
                    if ( object.getBoolean( "success" ) ) {

                        notificationList = new ArrayList< Notification >();
                        keys = object.getJSONObject( "notifications" ).keys();
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "notifications" ).getJSONObject( key );

                            content = tempJson.getString( "content" );
                            smallContent = tempJson.getString( "small_content"
                            );
                            extraId = tempJson.getString( "extra_id" );
                            typeString = tempJson.getString( "type" );
                            title = tempJson.getString( "title" );
                            image = tempJson.getString( "image" );
                            time = tempJson.getLong( "time" );
                            id = tempJson.getInt( "notification_id" );
                            notification = new Notification( id, content,
                                    smallContent, title, image, extraId, time
                                    , getContext(),
                                    NotificationType.valueOf( typeString ) );
                            notificationList.add( notification );
                        }
                    }
                } catch ( JSONException exc ) {
                    Toast.makeText( getContext(), exc.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    exc.printStackTrace();
                }
                Collections.sort( notificationList );
                Collections.reverse( notificationList );
                notificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail( String message ) {
                Toast.makeText( getContext(), message, Toast.LENGTH_SHORT ).show();

            }
        }, RequestType.NOTIFICATION, params, getContext(), true );
    }
}
