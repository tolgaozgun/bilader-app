package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.MessageWrapper;
import com.breakdown.bilader.models.User;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrivateChatActivity extends Activity {

    private MessagesListAdapter<MessageWrapper> adapter;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat );

        Intent intent = getIntent();

        //TODO
        if (intent != null) {
            // HER CHAT KONUSMASINA GROUP ID VERIP ONU KONTROL ETMIS BI TUTORIAL DA
        }

        MessageInput inputView = findViewById( R.id.input );
        MessagesList messagesList = findViewById( R.id.messagesList );

        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                sendMessage( input.toString() );
                return true;
            }
        });

        //TODO
        String senderId = "";


        adapter = new MessagesListAdapter<>(senderId, null);
        messagesList.setAdapter(adapter);
        //TODO
        //addListener();
        //fetchPreviousMessages();

    }
    //TODO
    /*private void addListener() {
        String listemerID = "listener 1";
        ChatData.addMessageListener (listemerID, new ChatData.MessageListener() ) {
            public void OnTextMessageReceived(Message message) {
                addMessage( message );
            }
        }
    }*/

    private void sendMessage(String message) {
        //TODO
       //Message messageServer = new Message(  )
        // addMessage(  messageServer);
    }

    //TODO
    private void addMessage ( Message message) {
        adapter.addToStart (new MessageWrapper(  message ),true);
    }

    //TODO
 /*   private void addMessages ( List<PreviousMessages> previousMessages) {
        List<IMessage> list;
        adapter.addToEnd ( list,true);
    }*/



    //TODO
    private void fetchPreviousMessages() {
    }

 
}

