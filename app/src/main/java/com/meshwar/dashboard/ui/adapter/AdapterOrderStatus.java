package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.OrderStatus;
import com.meshwar.dashboard.databinding.ItemCarTypeBinding;
import com.meshwar.dashboard.databinding.ItemOrderStatusBinding;

import java.util.List;

public class AdapterOrderStatus extends RecyclerView.Adapter<AdapterOrderStatus.ViewHolder> {

    private Context context;
    private List<OrderStatus> orderStatuses;


    private int selectedPosition = 0;
    private int prev_selectedPosition = 0;

    @SuppressLint("NotifyDataSetChanged")
    public void setStatus(String status) {
        switch (status) {
            case "1":
                itemCheckChanged(0);
                break;
            case "2":
                itemCheckChanged(1);
                break;
            case "3":
                itemCheckChanged(2);
                break;
        }

        notifyDataSetChanged();
    }


    public interface onItemClicked {
        void onOrderStatusClicked(String status);
    }

    private AdapterOrderStatus.onItemClicked onItemClicked;

    public AdapterOrderStatus(Context context, List<OrderStatus> orderStatuses, AdapterOrderStatus.onItemClicked onItemClicked) {
        this.context = context;
        this.orderStatuses = orderStatuses;
        this.onItemClicked = onItemClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderStatusBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.view.setBackgroundColor(ContextCompat.getColor(context, position == selectedPosition ? R.color.black : R.color.colorHint));
        holder.binding.tvCarType.setTextColor(ContextCompat.getColor(context, position == selectedPosition ? R.color.black : R.color.colorHint));
//        holder.binding.tvCarType.setTypeface(holder.binding.tvCarType.getTypeface(), position==selectedPosition ? Typeface.BOLD : Typeface.NORMAL);
        holder.binding.tvCarType.setText(orderStatuses.get(position).getStatus_txt());

        holder.itemView.setOnClickListener(v -> {
            itemCheckChanged(position);
            onItemClicked.onOrderStatusClicked(orderStatuses.get(position).getStatus());
        });
    }


    @Override
    public int getItemCount() {
        return orderStatuses.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderStatusBinding binding;

        public ViewHolder(ItemOrderStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void itemCheckChanged(Integer position) {

        prev_selectedPosition = selectedPosition;
        selectedPosition = position;
        if (prev_selectedPosition != -1) {
            notifyItemChanged(prev_selectedPosition);
        }

        notifyItemChanged(selectedPosition);
    }
}