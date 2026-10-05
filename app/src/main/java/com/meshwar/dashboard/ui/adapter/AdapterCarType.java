package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.databinding.ItemCarTypeBinding;
import com.meshwar.dashboard.databinding.ItemCityBinding;

import java.util.List;

public class AdapterCarType  extends RecyclerView.Adapter<AdapterCarType.ViewHolder> {

    private Context context;
    List<CarType> carTypes;
    public AdapterCarType(Context context, List<CarType> carTypes, AdapterCarType.onDeleteClicked onDeleteClicked, AdapterCarType.onItemClicked onItemClicked) {
        this.context = context;
        this.carTypes = carTypes;
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
    private AdapterCarType.onItemClicked onItemClicked;

    @NonNull
    @Override
    public AdapterCarType.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCarType.ViewHolder(ItemCarTypeBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterCarType.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.binding.tvCarName.setText(carTypes.get(position).getName());

        holder.binding.imvDelete.setOnClickListener(v -> {
            onDeleteClicked.onDeleteClicked(position);
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClicked.onItemClicked(position);
        });
    }


    @Override
    public int getItemCount() {
        return carTypes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarTypeBinding binding;

        public ViewHolder(ItemCarTypeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}