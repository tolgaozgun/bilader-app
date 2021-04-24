package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.CommentAdapter;
import com.breakdown.bilader.adapters.NotificationAdapter;
import com.breakdown.bilader.adapters.ReviewsAdapterByYahya;
import com.breakdown.bilader.models.Notification;
import com.breakdown.bilader.models.Review;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

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
