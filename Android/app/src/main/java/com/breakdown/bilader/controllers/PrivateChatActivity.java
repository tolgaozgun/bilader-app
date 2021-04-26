package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.MessageWrapper;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrivateChatActivity extends Activity {

    private MessagesListAdapter< MessageWrapper > adapter;
    private String chatId;
    private String userName;
    private String userAvatar;
    private String userId;
    private TextView userNameView;
    private ImageView userAvatarView;
    private User userChatted;
    private Gson gson;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat );

        userNameView = findViewById( R.id.private_chat_userName );
        userAvatarView = findViewById( R.id.private_chat_userAvatar );
      /*  Intent intent = getIntent();
        gson = new Gson();


        //TODO
        if ( intent != null ) {
            userChatted = gson.fromJson( getIntent().getStringExtra( "user" )
                    , User.class );
            userName = userChatted.getUserName();
            userAvatar = userChatted.getUserAvatar();
            userId = userChatted.getUserId();
            setChatId();
        } else {
            Toast.makeText( this, "User not found!", Toast.LENGTH_SHORT ).show();
            return;
        }
        System.out.println( "NEW_THING" );
        userNameView.setText( userName );
        Picasso.get().load( userAvatar ).fit().centerInside().into( userAvatarView );

        MessageInput inputView = findViewById( R.id.input );
        MessagesList messagesList = findViewById( R.id.messagesList );

        inputView.setInputListener( new MessageInput.InputListener() {
            @Override
            public boolean onSubmit( CharSequence input ) {
                //validate and send message
                sendMessage( input.toString() );
                return true;
            }
        } );

        //TODO
        String senderId = "";


        adapter = new MessagesListAdapter<>( senderId, null );
        messagesList.setAdapter( adapter );
        //TODO
        //addListener();
        //fetchPreviousMessages();

    }
    //TODO
    *//*private void addListener() {
        String listemerID = "listener 1";
        ChatData.addMessageListener (listemerID, new ChatData.MessageListener
        () ) {
            public void OnTextMessageReceived(Message message) {
                addMessage( message );
            }
        }
    }*//*

    private void setChatId() {

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "participant_one", userId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        chatId =
                                object.getJSONObject( "chats" ).getJSONObject( "0" ).getString( "chat_id" );
                    }else{
                        createChat();
                    }
                } catch ( JSONException e ) {
                    createChat();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {
                createChat();
            }
        }, RequestType.MAIN_CHAT, params, this, true );
    }

    private void createChat(){

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "participant_one", userId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if(object.getBoolean("success")){
                        chatId = object.getString( "chat_id" );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.CREATE_CHAT, params, this, true );
    }

    private void sendMessage( String message ) {

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "content", message );
        params.put( "chat_id", chatId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {

            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.SEND_MESSAGE, params, this, true );
        //TODO
        //Message messageServer = new Message(  )
        // addMessage(  messageServer);
    }

    //TODO
    private void addMessage( Message message ) {
        adapter.addToStart( new MessageWrapper( message ), true );
    }

    //TODO
   *//*private void addMessages ( List<IMessage> previousMessages) {
        IMessage message;

        adapter.addToEnd ( list,true);
    }*//*


    //TODO
    private void fetchPreviousMessages() {


        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "chat_id", chatId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {

            }

            @Override
            public void onFail( String message ) {

            }

        }, RequestType.RETRIEVE_MESSAGES, params, this, true );*/
    }


}

