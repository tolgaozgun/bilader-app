package com.breakdown.bilader.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.PrivateChatActivity;
import com.breakdown.bilader.models.Message;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.List;

public class PrivateChatAdapter extends RecyclerView.Adapter< PrivateChatAdapter.MessageHolder> {
    private List < Message > messageList;

    public PrivateChatAdapter (List <Message> messageList ) {
        this.messageList = messageList;

    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        public TextView receiverMessage;
        public TextView senderMessage;



        public MessageHolder( @NonNull View itemView ) {
            super( itemView );
            receiverMessage = itemView.findViewById( R.id.text_receiver_message );
            senderMessage = itemView.findViewById( R.id.text_sender_message );
        }
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                             int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_private_chat, parent, false );

        return new MessageHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull MessageHolder holder,
                                  int position ) {

        Message message;
        String fromUserID;
        String messageSenderId; // from data

        if (messageList != null && messageList.size() > position) {
            message = messageList.get( position );
            fromUserID = message.getUser().getId();


            holder.receiverMessage.setVisibility( View.GONE );
            holder.senderMessage.setVisibility( View.GONE );

            if ( fromUserID.equals( "messageSenderId" ) ) {
                holder.senderMessage.setVisibility( View.VISIBLE );

                holder.senderMessage.setBackgroundResource( R.drawable.sender_chat_bubble );
                holder.senderMessage.setText( message.getText() + "\n" + message.getMessageTime() + " - " + message.getCreatedAt() );
            } else {
                holder.receiverMessage.setVisibility( View.VISIBLE );

                holder.receiverMessage.setBackgroundResource( R.drawable.receiver_chat_bubble );
                holder.receiverMessage.setText( message.getText() + "\n" + message.getMessageTime() + " - " + message.getCreatedAt() );
            }
        }

    }

    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        }
        return 0;
    }

}
