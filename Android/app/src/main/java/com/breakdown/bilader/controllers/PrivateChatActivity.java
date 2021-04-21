package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.breakdown.bilader.R;
import com.breakdown.bilader.models.Message;
import com.breakdown.bilader.models.MessageWrapper;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class PrivateChatActivity extends Activity {

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat );

        Intent intent = getIntent();

        //TODO
        if (intent != null) {

        }

        String senderId = "12";
        MessageInput inputView = findViewById( R.id.input );
        MessagesList messagesList = findViewById( R.id.messagesList );
        MessagesListAdapter<MessageWrapper> adapter = new MessagesListAdapter<>(senderId, null);
        messagesList.setAdapter(adapter);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                sendMessage(input.toString());
                return true;
            }
        });

    }

    //TODO
    private void sendMessage( String message ) {
        Message textMessage = new Message(null, ( long ) 0,null,null);
    }


    private void addMessages ( Message message) {
       // adapter.addToStart (new MessageWrapper(  message ),true);
    }

 
}
