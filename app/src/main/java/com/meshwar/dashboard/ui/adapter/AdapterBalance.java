package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Balance;
import com.meshwar.dashboard.databinding.ItemOrderBalanceBinding;

import java.util.ArrayList;
import java.util.List;


public class AdapterBalance extends RecyclerView.Adapter<AdapterBalance.ViewHolder> {

    private Context context;


    public interface onItemClicked{
        void onItemClicked(int position);
    }
    private onItemClicked onItemClicked;

    List<Balance> balances = new ArrayList<>();

    public AdapterBalance(Context context, List<Balance> balances, onItemClicked onItemClicked) {
        this.context = context;
        this.balances = balances;
        this.onItemClicked = onItemClicked;
    }


    @NonNull
    @Override
    public AdapterBalance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterBalance.ViewHolder(ItemOrderBalanceBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterBalance.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.tvBalance.setText(String.format("%s %s" , balances.get(position).getPrice(),context.getString(R.string.riyal)));
        holder.binding.tvOrderID.setText(String.format("%s %s" ,context.getString(R.string.order_balance), balances.get(position).getOrderNum()));

        holder.itemView.setOnClickListener(v -> {
            onItemClicked.onItemClicked(position);
        });

    }


    @Override
    public int getItemCount() {
        return balances.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBalanceBinding binding;

        public ViewHolder(ItemOrderBalanceBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}