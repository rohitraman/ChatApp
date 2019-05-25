package com.example.darthvader.chatapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends BaseAdapter {
    List<Message> messages=new ArrayList<>();
    Context context;

    public MemberAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder messageViewHolder=new MessageViewHolder();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Message message=messages.get(position);
        if(message.isIfBelongstoUser()){
            convertView=inflater.inflate(R.layout.my_message,null);
            messageViewHolder.messageBody=(TextView)convertView.findViewById(R.id.message_body);
            convertView.setTag(messageViewHolder);
            messageViewHolder.messageBody.setText(message.getText());
        }else{
            convertView=inflater.inflate(R.layout.their_message,null);
            messageViewHolder.color=(View)convertView.findViewById(R.id.view);
            messageViewHolder.messageBody=(TextView)convertView.findViewById(R.id.message_body);
            messageViewHolder.name=(TextView)convertView.findViewById(R.id.name);
            messageViewHolder.name.setText(message.getData().getMemberName());
            messageViewHolder.messageBody.setText(message.getText());
            convertView.setTag(messageViewHolder);

            GradientDrawable drawable=(GradientDrawable)messageViewHolder.color.getBackground();
            drawable.setColor(Color.parseColor(message.getData().getMemberColor()));

        }
        return convertView;
    }

}
class MessageViewHolder{
    public View color;
    public TextView name;
    public TextView messageBody;

}