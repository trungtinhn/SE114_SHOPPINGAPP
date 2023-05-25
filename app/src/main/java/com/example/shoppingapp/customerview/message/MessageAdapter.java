package com.example.shoppingapp.customerview.message;

import static com.example.shoppingapp.R.drawable.background_message_blue;
import static com.example.shoppingapp.R.drawable.background_message_white;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Message> messageList;
    private Context context;

    public MessageAdapter(Context context)
    {
        this.context = context;
    }

    public void setData(List<Message> messageList)
    {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);


        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message == null) return;

        holder.txtContentMessage.setText(message.getContent());
        holder.txtTimeMessage.setText(message.getTime());
        if (message.getType() == 1)
        {
            holder.layout_message_item.setGravity(Gravity.RIGHT);
            holder.layout_rowMessage.setBackgroundResource(background_message_blue);
            holder.txtContentMessage.setTextColor((ContextCompat.getColor(context,R.color.white)));
            holder.txtTimeMessage.setTextColor((ContextCompat.getColor(context,R.color.white)));
        }else {
            holder.layout_message_item.setGravity(Gravity.LEFT);
            holder.layout_rowMessage.setBackgroundResource(background_message_white);
            holder.txtContentMessage.setTextColor((ContextCompat.getColor(context,R.color.black)));
            holder.txtTimeMessage.setTextColor((ContextCompat.getColor(context,R.color.black)));
        }


    }

    @Override
    public int getItemCount() {
        if (messageList != null) return messageList.size();
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout_message_item;
        private LinearLayout layout_rowMessage;
        private TextView txtContentMessage;
        private TextView txtTimeMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_message_item = itemView.findViewById(R.id.layout_message_item);
            layout_rowMessage = itemView.findViewById(R.id.layout_rowMessage);
            txtContentMessage = itemView.findViewById(R.id.txtContentMessage);
            txtTimeMessage = itemView.findViewById(R.id.txtTimeMessage);
        }
    }
}
