package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.MainChatAdapter;
import com.breakdown.bilader.adapters.UserAdapter;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends Activity {
    private View groupFragmentVİew;
    private RecyclerView recyclerView;
    private ArrayList< User > chatUsers= new ArrayList<>();
    private MainChatAdapter adapter;

    public MainChatActivity() {

    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mainchat );
        recyclerView = findViewById( R.id.recyclerMainChat);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );


        //TODO
        /*public ArrayList < Message > getLastMessages() {

        }*/
    }

    private void printView( RecyclerView recyclerView ) {
        adapter = new MainChatAdapter(this, chatUsers   );
        recyclerView.setAdapter( adapter );
    }
}



