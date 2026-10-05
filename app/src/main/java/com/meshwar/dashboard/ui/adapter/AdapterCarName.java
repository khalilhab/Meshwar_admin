package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.databinding.ItemCarNameBinding;

import java.util.List;

public class AdapterCarName extends RecyclerView.Adapter<AdapterCarName.ViewHolder> {

    private Context context;
    private List<Car> cars;

    public AdapterCarName(Context context, List<Car> cars) {
        this.context = context;
        this.cars =cars;
    }


    @NonNull
    @Override
    public AdapterCarName.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCarName.ViewHolder(ItemCarNameBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterCarName.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        Picasso.get().load(adsList.get(position).getImage()).fit().into(holder.binding.imvIntro);
    }


    @Override
    public int getItemCount() {
        return 8;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarNameBinding binding;

        public ViewHolder(ItemCarNameBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}