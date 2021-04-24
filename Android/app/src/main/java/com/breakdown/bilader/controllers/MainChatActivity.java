package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends Activity {
    private View groupFragmentVİew;
    private RecyclerView recyclerView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfLastMessages = new ArrayList<>();

    public MainChatActivity() {

    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mainchat );

        recyclerView = findViewById( R.id.recyclerMainChat);


    }
}



