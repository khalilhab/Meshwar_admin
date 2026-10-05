package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.databinding.ItemCityBinding;

import java.util.List;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.ViewHolder> {

    private Context context;
    List<City> cities;
    public AdapterCity(Context context, List<City> cities, onDeleteClicked onDeleteClicked, onItemClicked onItemClicked) {
        this.context = context;
        this.cities = cities;
        this.onDeleteClicked = onDeleteClicked;
        this.onItemClicked = onItemClicked;
    }

    public interface onDeleteClicked{
        void onDeleteClicked(int position);
    }
    private onDeleteClicked onDeleteClicked;


    public interface onItemClicked{
        void onItemClicked(int position);
    }
    private onItemClicked onItemClicked;

    @NonNull
    @Override
    public AdapterCity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCity.ViewHolder(ItemCityBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterCity.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.binding.tvCityName.setText(cities.get(position).getName());

        holder.binding.imvDelete.setOnClickListener(v -> {
            onDeleteClicked.onDeleteClicked(position);
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClicked.onItemClicked(position);
        });
    }


    @Override
    public int getItemCount() {
        return cities.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCityBinding binding;

        public ViewHolder(ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}