package com.luoye.chat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.luoye.apptool.viewbinding.BaseViewAdapter;
import com.luoye.chat.R;
import com.luoye.chat.databinding.AdapterChatBubbleBinding;
import com.luoye.dblibrary.db.Message;

import java.util.List;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user:
 */
public class ChatBubbleAdapter extends BaseViewAdapter<Message, AdapterChatBubbleBinding> {


    public ChatBubbleAdapter(Context context, LifecycleOwner owner, List<Message> objectArrayList) {
        super(context, owner, objectArrayList);
    }

    @Override
    protected void initAdapter(BaseViewAdapter<Message, AdapterChatBubbleBinding>.ViewHolder viewHolder, Message message, int i) {
        Log.i("---", "message: " + message.toString());
        if (message.getRole().equals(Message.Role.ASSISTANT.getValue()) || message.getRole().equals(Message.Role.SYSTEM.getValue())) {
            viewHolder.binding.layout.setBackgroundResource(R.drawable.chat_bubble_ai);
            Glide.with(viewHolder.binding.imageView).load(R.mipmap.ai_image).error(R.mipmap.ai_image).into(viewHolder.binding.imageView);
            viewHolder.binding.value.setText(message.getContent());
            viewHolder.binding.valueUser.setText("");
            viewHolder.binding.value.setVisibility(View.VISIBLE);
            viewHolder.binding.imageView.setVisibility(View.VISIBLE);
            viewHolder.binding.imageViewUser.setVisibility(View.INVISIBLE);
            viewHolder.binding.valueUser.setVisibility(View.INVISIBLE);
        } else if (message.getRole().equals(Message.Role.USER.getValue())) {
            Glide.with(viewHolder.binding.imageViewUser).load(R.mipmap.user_image).error(R.mipmap.user_image).into(viewHolder.binding.imageViewUser);
            viewHolder.binding.layout.setBackgroundResource(R.drawable.chat_bubble_user);
            viewHolder.binding.value.setText("");
            viewHolder.binding.valueUser.setText(message.getContent());
            viewHolder.binding.value.setVisibility(View.INVISIBLE);
            viewHolder.binding.imageView.setVisibility(View.INVISIBLE);
            viewHolder.binding.imageViewUser.setVisibility(View.VISIBLE);
            viewHolder.binding.valueUser.setVisibility(View.VISIBLE);
        }
    }


}
