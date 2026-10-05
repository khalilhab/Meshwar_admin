package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.databinding.ItemDriverBinding;

import java.util.List;

public class AdapterDriver  extends RecyclerView.Adapter<AdapterDriver.ViewHolder> {

    private Context context;
    List<Driver> drivers;
    public AdapterDriver(Context context,List<Driver> drivers,onItemClicked onItemClicked) {
        this.context = context;
        this.drivers = drivers;
        this.onItemClicked = onItemClicked;
    }

    public interface onItemClicked{
        void onItemClicked(int position);
    }
    private onItemClicked onItemClicked;

    @NonNull
    @Override
    public AdapterDriver.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterDriver.ViewHolder(ItemDriverBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDriver.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(drivers.get(position).getImage()).centerCrop().placeholder(R.drawable.avatar).into(holder.binding.imvDriver);
        holder.binding.tvDriverName.setText(drivers.get(position).getName());
        holder.binding.tvCarName.setText(drivers.get(position).getCarName());
        holder.binding.tvDriverAvailable.setText(drivers.get(position).getAvailableText());
        holder.binding.tvDriverAvailable.setTextColor(ContextCompat.getColorStateList(context, drivers.get(position).getIsAvailable()?R.color.colorAccent:R.color.red));
        holder.binding.ratingBar.setRating(Float.parseFloat(drivers.get(position).getRatingAvg()));

        holder.itemView.setOnClickListener(v -> {
            onItemClicked.onItemClicked(position);
        });
    }


    @Override
    public int getItemCount() {
        return drivers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDriverBinding binding;

        public ViewHolder(ItemDriverBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}