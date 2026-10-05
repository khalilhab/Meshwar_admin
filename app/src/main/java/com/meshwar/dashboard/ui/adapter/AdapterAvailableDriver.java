package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.data.network.model.User;
import com.meshwar.dashboard.databinding.ItemDriverAvatarBinding;

import java.util.List;

public class AdapterAvailableDriver extends RecyclerView.Adapter<AdapterAvailableDriver.ViewHolder> {

    private Context context;
    private List<User> drivers;

    public AdapterAvailableDriver(Context context, List<User> drivers) {
        this.context = context;
        this.drivers =drivers;
    }


    @NonNull
    @Override
    public AdapterAvailableDriver.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterAvailableDriver.ViewHolder(ItemDriverAvatarBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterAvailableDriver.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        Picasso.get().load(adsList.get(position).getImage()).fit().into(holder.binding.imvIntro);
    }


    @Override
    public int getItemCount() {
        return 8;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDriverAvatarBinding binding;

        public ViewHolder(ItemDriverAvatarBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}