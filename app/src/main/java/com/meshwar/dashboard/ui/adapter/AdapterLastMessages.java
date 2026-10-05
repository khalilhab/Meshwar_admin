package com.meshwar.dashboard.ui.adapter;

import static com.meshwar.dashboard.utils.AppSharedConstants.CHAT_ID;
import static com.meshwar.dashboard.utils.AppSharedConstants.LAST_MESSAGE;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER_ID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.databinding.ItemChatBinding;
import com.meshwar.dashboard.ui.activities.supportChat.view.ChatSupportActivity;

import java.util.List;

public class AdapterLastMessages  extends RecyclerView.Adapter<AdapterLastMessages.ViewHolder> {

    private final Context context;
    List<LastMessage> lastMessages;

    public AdapterLastMessages(Context context, List<LastMessage> lastMessages) {
        this.context = context;
        this.lastMessages = lastMessages;
    }


    @NonNull
    @Override
    public AdapterLastMessages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterLastMessages.ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLastMessages.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(lastMessages.get(position).getImage()).centerCrop().placeholder(R.drawable.avatar).into(holder.binding.imvDriver);

        holder.binding.tvSince.setText(lastMessages.get(position).getTime());
        holder.binding.tvDriverName.setText(lastMessages.get(position).getName());
        holder.binding.tvLasMsg.setText(lastMessages.get(position).getLastMessage());
        holder.binding.getRoot().setOnClickListener(v -> context.startActivity(new Intent(context, ChatSupportActivity.class)
                .putExtra(LAST_MESSAGE,new Gson().toJson(lastMessages.get(position)))
        ));
    }


    @Override
    public int getItemCount() {
        return lastMessages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemChatBinding binding;

        public ViewHolder(ItemChatBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}
