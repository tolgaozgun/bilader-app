package com.breakdown.bilader.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.breakdown.bilader.R;
import com.breakdown.bilader.controllers.PrivateChatActivity;
import com.breakdown.bilader.models.Message;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONObject;

import java.util.List;

public class PrivateChatAdapter extends RecyclerView.Adapter< PrivateChatAdapter.MessageHolder> {
    private List < Message > messageList;

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param messageList list of the messages
     */
    public PrivateChatAdapter (List <Message> messageList ) {
        this.messageList = messageList;
    }

    /**
     * A class that finds xml id's of layout elements
     */
    public class MessageHolder extends RecyclerView.ViewHolder {

        public TextView receiverMessage;
        public TextView senderMessage;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public MessageHolder( @NonNull View itemView ) {
            super( itemView );
            receiverMessage = itemView.findViewById( R.id.text_receiver_message );
            senderMessage = itemView.findViewById( R.id.text_sender_message );
        }
    }

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                             int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_private_chat, parent, false );

        return new MessageHolder( itemView );
    }

    /**
     * a method called by RecyclerView to display the data at the specified
     * position
     *
     * @param holder   is the ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position is The position of the item within the adapter's data
     *                 set.
     */
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of messages in this adapter.
     */
    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        }
        return 0;
    }


}
