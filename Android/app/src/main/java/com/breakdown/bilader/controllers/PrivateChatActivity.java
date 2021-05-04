package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import java.util.List;
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

    /**
     * this is the method where most initialization made such as UI and widgets
     *
     * @param savedInstanceState: If the activity is being re-initialized after
     *                            previously being shut down then this Bundle
     *                            contains the data it most recently supplied
     *                            in
     */
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat_chatkit );

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
            //TODO
            userChatted = gson.fromJson( getIntent().getStringExtra( "user" )
                    , User.class );
            userName = userChatted.getName();
            userAvatar = userChatted.getAvatar();
            userId = userChatted.getId();
            userNameView.setText( userName );
            Picasso.get().load( userAvatar ).fit().centerCrop().into( userAvatarView );
            System.out.println( "AA" );
            setChatId();
        } else {
            Toast.makeText( this, "User not found!", Toast.LENGTH_SHORT ).show();
            return;
        }

        inputView.setInputListener( new MessageInput.InputListener() {

            /**
             * When the button clicked, it checks whether input empty if it
             * is not, message is delivered.
             * @param input is te typed message
             */
            @Override
            public boolean onSubmit( CharSequence input ) {
                //validate and send message
                sendMessage( input.toString() );
                return true;
            }
        } );

    }

    /**
     * If users have messaged before, it shows old messages, otherwise it
     * creates a private chat environment for users.
     */
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

    /**
     * fetch previous messages and creates chat environment to both users
     */
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

    /**
     * syncs recycler view with sent message simultaneously
     *
     * @param message is the typed message by sender
     */
    private void sendMessage( String message ) {

        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "content", message );
        params.put( "chat_id", chatId );

        //TODO
        Message message1 = new Message( ( long ) 0, userChatted, message,
                chatId );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                String messageId;
                try {
                    if ( object.getBoolean( "success" ) ) {
                        messageId = object.getString( "message_id" );
                        // message1 = new Message
                        adapter.addToStart( message1, true );
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail( String message ) {

            }
        }, RequestType.SEND_MESSAGE, params, this, true );

    }

    /**
     * takes sender id from the server
     *
     * @param id is the id of user
     * @return sender id
     */
    private User getUser( String id ) {
        if ( currentUserId.equals( id ) ) {
            return gson.fromJson( getIntent().getStringExtra( "user" ),
                    User.class );
        }
        return userChatted;
    }


    //TODO

    /**
     * Syncs previously sent messages and sets receiver's user avatar
     */
    private void fetchPreviousMessages() {
        Map< String, String > params;
        params = new HashMap< String, String >();
        params.put( "chat_id", chatId );
        params.put( "time", "0" );

        HttpAdapter.getRequestJSON( new VolleyCallback() {
            @Override
            public void onSuccess( JSONObject object ) {
                ArrayList< Message > previousMessages =
                        new ArrayList< Message >();
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

                            //TODO
                            sender =
                                    gson.fromJson( getIntent().getStringExtra( "user" ), User.class );

                            message = new Message( time, sender, content,
                                    messageId );

                            previousMessages.add( message );
                        }
                        System.out.println( "mm" );
                        adapter.addToEnd( previousMessages, true );
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