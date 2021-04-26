package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.HttpAdapter;
import com.breakdown.bilader.adapters.RequestType;
import com.breakdown.bilader.adapters.VolleyCallback;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PrivateChatActivity extends Activity {

    private MessagesListAdapter< Message > adapter;
    private String chatId;
    private String userName;
    private String userAvatar;
    private String userId;
    private TextView userNameView;
    private ImageView userAvatarView;
    private User userChatted;
    private MessagesList messagesList;
    private SharedPreferences sharedPreferences;
    private String currentUserId;
    private MessageInput inputView;
    private Intent incomingIntent;
    private Gson gson;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat );

        userNameView = findViewById( R.id.private_chat_userName );
        userAvatarView = findViewById( R.id.private_chat_userAvatar );
        inputView = findViewById( R.id.input );
        messagesList = findViewById( R.id.messagesList );
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( this );

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage( ImageView imageView, @Nullable String url,
                                   @Nullable Object payload ) {

                Picasso.get().load( url ).fit().centerInside().into( imageView );
            }
        };
        currentUserId = sharedPreferences.getString( "id", "" );
        gson = new Gson();
        incomingIntent = getIntent();
        adapter = new MessagesListAdapter<>( currentUserId, imageLoader );
        messagesList.setAdapter( adapter );

        if ( incomingIntent != null ) {
            userChatted = gson.fromJson( getIntent().getStringExtra( "user" )
                    , User.class );
            userName = userChatted.getName();
            userAvatar = userChatted.getAvatar();
            userId = userChatted.getId();
            userNameView.setText( userName );
            Picasso.get().load( userAvatar ).fit().centerInside().into( userAvatarView );
            System.out.println( "AA" );
            setChatId();
        } else {
            Toast.makeText( this, "User not found!", Toast.LENGTH_SHORT ).show();
            return;
        }


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


        //TODO
        //addListener();

    }
    //TODO
    /*private void addListener() {
        String listemerID = "listener 1";
        ChatData.addMessageListener (listemerID, new ChatData.MessageListener
        () ) {
            public void OnTextMessageReceived(Message message) {
                addMessage( message );
            }
        }
    }*/

    private void setChatId() {

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "participant_one", userId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                System.out.println( "bb" );
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println( "cc" );
                        chatId =
                                object.getJSONObject( "chats" ).getJSONObject( "0" ).getString( "chat_id" );
                        System.out.println( "dd" );
                        fetchPreviousMessages();
                        System.out.println( "ee" );
                    } else {
                        createChat();
                        System.out.println( "ff" );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                    createChat();
                    System.out.println( "gg" );
                }
            }

            @Override
            public void onFail( String message ) {
                createChat();
            }
        }, RequestType.MAIN_CHAT, params, this, true );
    }

    private void createChat() {

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "participant_one", userId );
        System.out.println( "hh" );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println( "ii" );
                        chatId = object.getString( "chat_id" );
                        fetchPreviousMessages();
                        System.out.println( "jj" );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail( String message ) {
                fetchPreviousMessages();
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
                String messageId;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        messageId = object.getString( "message_id" );

                        adapter.addToStart( message, true );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
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
        adapter.addToStart( message, true );
    }

    //TODO
   /*private void addMessages ( List<IMessage> previousMessages) {
        IMessage message;

        adapter.addToEnd ( list,true);
    }*/

    private User getUser( String id ) {
        if ( currentUserId.equals( id ) ) {
            return gson.fromJson( getIntent().getStringExtra( "current_user" ), User.class );
        }
        return userChatted;
    }


    //TODO
    private void fetchPreviousMessages() {
        if(true){
            return;
        }

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "chat_id", chatId );
        params.put( "time", "0" );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                ArrayList< Message > previousMessages;
                Iterator< String > keys;
                JSONObject tempJson;
                String senderId;
                String content;
                String messageId;
                long time;
                User sender;
                Message message;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        System.out.println( "kk" );
                        previousMessages = new ArrayList< Message >();
                        keys = object.getJSONObject( "messages" ).keys();
                        while ( keys.hasNext() ) {
                            System.out.println( "ll" );
                            String key = keys.next();
                            tempJson =
                                    object.getJSONObject( "messages" ).getJSONObject( key );

                            time = tempJson.getLong( "time" );
                            senderId = tempJson.getString( "sender_id" );
                            content = tempJson.getString( "content" );
                            messageId = tempJson.getString( "message_id" );
                            System.out.println( "time" + time + " senderId " + senderId + " content " + content + " messageId " + messageId );
                            //sender = getUser( senderId );
                            sender = getUser( currentUserId );
                            System.out.println( "WHAT?" + sender == null );
                            message = new Message( time, sender, content,
                                    messageId );
                            System.out.println( "-->" + message == null );
                            previousMessages.add( message );
                        }
                        System.out.println( "mm" );
                        System.out.println( "-->" + adapter == null );
                        adapter.addToEnd( previousMessages, false );
                        System.out.println( "nn" );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFail( String message ) {

            }

        }, RequestType.RETRIEVE_MESSAGES, params, this, true );
    }


}

