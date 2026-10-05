package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.databinding.ItemPriceBinding;

public class AdapterPrice extends RecyclerView.Adapter<AdapterPrice.ViewHolder> {

    private Context context;

    public AdapterPrice(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterPrice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterPrice.ViewHolder(ItemPriceBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPrice.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        Picasso.get().load(adsList.get(position).getImage()).fit().into(holder.binding.imvIntro);
    }


    @Override
    public int getItemCount() {
        return 8;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPriceBinding binding;

        public ViewHolder(ItemPriceBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}