package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.NotificationAdapter;

/**
 * A class that makes connection between its layout and data
 *
 * @author breakDown
 * @version 27.04.2021
 */

public class NotificationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

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
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view;

        view = inflater.inflate( R.layout.fragment_notifications, container, false );

        recyclerView = view.findViewById( R.id.notificationRecycler );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        //notificationAdapter = new NotificationAdapter(  )
        //recyclerView.setAdapter( notificationAdapter );
           return view;
    }
}
