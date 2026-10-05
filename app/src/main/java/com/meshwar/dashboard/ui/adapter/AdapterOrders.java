package com.meshwar.dashboard.ui.adapter;

import static com.meshwar.dashboard.utils.AppSharedConstants.ORDER_ID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Order;
import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.meshwar.dashboard.databinding.ItemOrderBinding;
import com.meshwar.dashboard.ui.activities.orderDetails.view.DriverOrderDetailsActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder> {

    private Context context;
    private String status; //[1=>current , 2=>in_progress ,3=>completed]
    List<Order> orders;

    AppPreferencesHelper preferencesHelper;
    public interface onItemClicked{
        void onPaidClicked(int position);
    }
    private onItemClicked onItemClicked;



    public AdapterOrders(Context context, List<Order> orders, String status, onItemClicked onItemClicked) {
        this.context = context;
        this.orders = orders;
        this.status = status;
        this.onItemClicked = onItemClicked;
        this.preferencesHelper = new AppPreferencesHelper();

    }


    @NonNull
    @Override
    public AdapterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterOrders.ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrders.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvSinceTime.setText(orders.get(position).getAddTime());
        holder.binding.tvOrderNumber.setText(orders.get(position).getOrderNum());
        holder.binding.tvOrderPrice.setText(String.format("%s %s",orders.get(position).getPrice(),context.getString(R.string.riyal)));
        Glide.with(context).load(orders.get(position).getUser().getImage()).centerCrop().placeholder(R.drawable.logo).into(holder.binding.imvUser);
        holder.binding.tvUserName.setText(orders.get(position).getUser().getName());
        holder.binding.tvUserPhone.setText(orders.get(position).getUser().getMobile());
        holder.binding.tvOrderDescription.setText(orders.get(position).getDescription());


        holder.binding.tvUserPhone.setOnClickListener(v -> AppSharedMethod.call(context,orders.get(position).getUser().getMobile()));
        holder.binding.tvUserCall.setOnClickListener(v -> AppSharedMethod.call(context,orders.get(position).getUser().getMobile()));


        Glide.with(context).load(orders.get(position).getDriver().getImage()).centerCrop().placeholder(R.drawable.logo).into(holder.binding.imvDriver);
        holder.binding.tvDriverName.setText(orders.get(position).getDriver().getName());
        holder.binding.tvDriverCarName.setText(String.format("%s %s",context.getString(R.string.driver),orders.get(position).getDriver().getCarName()));
        holder.binding.tvDriverPhone.setText(orders.get(position).getDriver().getMobile());
        holder.binding.ratingBar.setRating(Float.parseFloat(orders.get(position).getDriver().getRatingAvg()));

        holder.binding.tvDriverPhone.setOnClickListener(v -> AppSharedMethod.call(context,orders.get(position).getDriver().getMobile()));
        holder.binding.tvDriverCall.setOnClickListener(v -> AppSharedMethod.call(context,orders.get(position).getDriver().getMobile()));


        holder.binding.tvOrderPaid.setVisibility((preferencesHelper.getUser().getSuperAdmin()&&status.equals("3")&&!orders.get(position).isPaid())?View.VISIBLE:View.GONE);

        holder.binding.tvOrderPaid.setOnClickListener(v -> onItemClicked.onPaidClicked(position));
        holder.binding.tvOrderDetails.setOnClickListener(v -> context.startActivity(new Intent(context, DriverOrderDetailsActivity.class).putExtra(ORDER_ID,orders.get(position).getUuid())));
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStatus(String status){
        this.status=status;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public ViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}

