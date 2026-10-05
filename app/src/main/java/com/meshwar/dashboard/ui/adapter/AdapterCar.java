package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.databinding.ItemCarBinding;

import java.util.List;

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.ViewHolder> {

    private Context context;
    private List<Car> cars;


    public OnBottomReached onBottomReached;

    public interface OnBottomReached {
        void onBottomReached(int position);
    }

    public AdapterCar(Context context, List<Car> cars,onItemClicked onItemClicked,OnBottomReached onBottomReached) {
        this.context = context;
        this.onItemClicked =onItemClicked;
        this.onBottomReached =onBottomReached;
        this.cars =cars;
    }

    public interface onItemClicked{
        void onItemClicked(int position);
    }
    private onItemClicked onItemClicked;

    @NonNull
    @Override
    public AdapterCar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCar.ViewHolder(ItemCarBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterCar.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(cars.get(position).getImage()).into(holder.binding.imvCar);
        holder.binding.tvCarName.setText(cars.get(position).getName());
        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClicked.onItemClicked(position);
        });

        holder.binding.viewDisabledOverlay.setVisibility(
                cars.get(position).isActive() ? android.view.View.GONE : android.view.View.VISIBLE
        );

        if (position == cars.size() - 1) {
            onBottomReached.onBottomReached(position);
        }

    }


    @Override
    public int getItemCount() {
        return cars.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarBinding binding;

        public ViewHolder(ItemCarBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}