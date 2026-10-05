package com.meshwar.dashboard.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;


import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Item;
import com.meshwar.dashboard.databinding.ItemSelectListBinding;

import java.util.List;

public class AdapterListSelect extends
        RecyclerView.Adapter<AdapterListSelect.ViewHolder> {
    private Context context;
    List<Item> items;


    public interface OnItemClicked{
        void onItemClicked(Item item);
    }

    OnItemClicked onItemClicked;

    public AdapterListSelect(Context context, List<Item> items, OnItemClicked onItemClicked) {
        this.context = context;
        this.items = items;
        this.onItemClicked = onItemClicked;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ItemSelectListBinding.inflate(LayoutInflater.from(context), parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down));

        holder.binding.text1.setText(items.get(position).getName());

        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClicked.onItemClicked(items.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectListBinding binding;
        public ViewHolder(ItemSelectListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
