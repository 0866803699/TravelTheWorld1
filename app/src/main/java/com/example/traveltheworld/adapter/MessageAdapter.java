package com.example.traveltheworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveltheworld.R;
import com.example.traveltheworld.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHoder> {
    List<Message> messageList;
    public void setData(List<Message> messageList){
        this.messageList=messageList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MessageViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHoder holder, int position) {
        Message message = messageList.get(position);
        if (message== null){
            return;
        }
        holder.tvMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        if (messageList!=null){
            return messageList.size();
        }
        return 0;

    }

    public class MessageViewHoder extends RecyclerView.ViewHolder{
        private TextView tvMessage;
        public MessageViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
