package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.databinding.ItemNotificationBinding;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ViewHolder> {

    private Context context;

    public AdapterNotifications(Context context, onItemClicked onItemClicked) {
        this.context = context;
        this.onItemClicked = onItemClicked;
    }

    public interface onItemClicked{
        void onItemClicked();
    }
    private onItemClicked onItemClicked;
    @NonNull
    @Override
    public AdapterNotifications.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterNotifications.ViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterNotifications.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemView.setOnClickListener(v -> {
            onItemClicked.onItemClicked();
        });
    }


    @Override
    public int getItemCount() {
        return 7;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public ViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}