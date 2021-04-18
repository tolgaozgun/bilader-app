package com.breakdown.bilader.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.FollowersActivity;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
    Activity context;
    private Button followersButton;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button bt = (Button) getActivity().findViewById(R.id.followersButton);
        bt.setOnClickListener((View.OnClickListener)this);
    }
            @Override
            public void onClick( View v ) {
                Intent newIntent = new Intent(getActivity(), FollowersActivity.class);
               startActivity(newIntent);
            }
        }
