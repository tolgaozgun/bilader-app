package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.MainChatAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.UserAdapter;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Category;
import com.breakdown.bilader.models.Chat;
import com.breakdown.bilader.models.ChatUser;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainChatActivity extends Activity {
    private View groupFragmentVİew;
    private RecyclerView recyclerView;
    private ArrayList< ChatUser > chatUsers;
    private MainChatAdapter adapter;

    public MainChatActivity() {

    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mainchat );
        recyclerView = findViewById( R.id.recyclerMainChat );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        chatUsers = new ArrayList< ChatUser >();

        retrieveMessages(recyclerView);
    }

    private void retrieveMessages( RecyclerView recyclerView ) {
        HashMap< String, String > params;
        params = new HashMap< String, String >();
        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                Iterator<String> keys;
                JSONObject tempJson;
                String id;
                String name;
                String lastMessage;
                String avatar;
                String chatId;
                long lastMessageDate;
                ChatUser chatUser;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println("AAA");
                        keys = object.getJSONObject( "chats" ).keys();
                        System.out.println("AAA");
                        while ( keys.hasNext() ) {
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "chats" ).getJSONObject( key );
                            id = tempJson.getString( "participant_id" );
                            name = tempJson.getString( "participant_name" );
                            avatar = tempJson.getString( "participant_avatar" );
                            chatId = tempJson.getString( "chat_id" );
                            lastMessage = tempJson.getString( "last_message" );
                            lastMessageDate = tempJson.getLong( "last_message_date" );

                            chatUser = new ChatUser( name, avatar, id, lastMessage, chatId, lastMessageDate);
                            chatUsers.add( chatUser );
                        }
                    }
                } catch ( JSONException e ) {
                    Toast.makeText( MainChatActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT ).show();
                    e.printStackTrace();
                }
                printView(recyclerView);
            }

            @Override
            public void onFail( String message ) {
                printView(recyclerView);
            }
        }, RequestType.MAIN_CHAT, params, this, true );
    }


    private void printView( RecyclerView recyclerView ) {
        adapter = new MainChatAdapter( this, chatUsers );
        recyclerView.setAdapter( adapter );
    }
}



