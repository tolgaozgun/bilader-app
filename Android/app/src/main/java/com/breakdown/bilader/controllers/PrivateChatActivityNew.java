package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.PrivateChatAdapter;
import com.breakdown.bilader.models.Message;

import java.util.List;

public class PrivateChatActivityNew extends Activity {

    private String receiverId;
    private String receiverName;
    private String receiverAvatar;
    private String senderId;

    private TextView senderName;
    private ImageView senderAvatar;

    private Button sendMessageButton;
    private EditText messageInput;

    private List< Message > messageLis;
    private PrivateChatAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_privatechat );


    }

}
