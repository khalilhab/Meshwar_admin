package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.CarPrice;
import com.meshwar.dashboard.databinding.ItemCarPriceBinding;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.List;

public class AdapterCarPrice extends RecyclerSwipeAdapter<AdapterCarPrice.ViewHolder> {

    private final Context context;
    private List<CarPrice> carPrices;
    boolean inside;

    private static final String[] DISTANCE_SCALES_UI = {"أقل من أو يساوي", "أكبر من"};
    private static final String[] DISTANCE_SCALES_API = {"less_or_equal", "bigger"};

    public interface PriceDeleted {
        void onPriceDeleted(String uuid, boolean inside);
    }

    private PriceDeleted priceDeleted;


    public AdapterCarPrice(Context context, List<CarPrice> carPrices, boolean inside, PriceDeleted priceDeleted) {
        this.context = context;
        this.carPrices = carPrices;
        this.inside = inside;
        this.priceDeleted = priceDeleted;
    }


    @NonNull
    @Override
    public AdapterCarPrice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCarPriceBinding binding = ItemCarPriceBinding.inflate(LayoutInflater.from(context), parent, false);
        
        ArrayAdapter<String> scaleAdapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_layout, DISTANCE_SCALES_UI);
        scaleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        binding.spDistanceScale.setAdapter(scaleAdapter);

        return new AdapterCarPrice.ViewHolder(binding, new NameEditTextListener(), new PriceEditTextListener(), new CommissionEditTextListener(), new MaxDistanceEditTextListener(), new DistanceScaleOnItemSelectedListener());
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull AdapterCarPrice.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameEditTextListener.updatePosition(position);
        holder.priceEditTextListener.updatePosition(position);
        holder.commissionEditTextListener.updatePosition(position);
        holder.maxDistanceEditTextListener.updatePosition(position);
        holder.distanceScaleOnItemSelectedListener.updatePosition(position);

        holder.binding.serviceName.setText(carPrices.get(position).getName());
        holder.binding.servicePrice.setText(carPrices.get(position).getPrice());
        holder.binding.serviceCommission.setText(carPrices.get(position).getCommission());
        holder.binding.maxDistance.setText(carPrices.get(position).getMaxDistance() != null ? carPrices.get(position).getMaxDistance() : "");

        holder.binding.spDistanceScale.setOnItemSelectedListener(null);
        if ("bigger".equals(carPrices.get(position).getDistanceScale())) {
            holder.binding.spDistanceScale.setSelection(1);
        } else {
            holder.binding.spDistanceScale.setSelection(0);
        }
        holder.binding.spDistanceScale.setOnItemSelectedListener(holder.distanceScaleOnItemSelectedListener);

        holder.binding.imvDelete.setOnClickListener(v -> {
            AppSharedMethod.hideKeyboard(context);
            priceDeleted.onPriceDeleted(carPrices.get(position).getUuid(), inside);
            mItemManger.closeItem(position);
            carPrices.remove(position);
            notifyDataSetChanged();
        });

    }


    @Override
    public int getItemCount() {
        return carPrices.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        holder.enableTextWatcher();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.disableTextWatcher();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarPriceBinding binding;
        public NameEditTextListener nameEditTextListener;
        public PriceEditTextListener priceEditTextListener;
        public CommissionEditTextListener commissionEditTextListener;
        public MaxDistanceEditTextListener maxDistanceEditTextListener;
        public DistanceScaleOnItemSelectedListener distanceScaleOnItemSelectedListener;

        public ViewHolder(ItemCarPriceBinding binding, NameEditTextListener nameEditTextListener, PriceEditTextListener priceEditTextListener, CommissionEditTextListener commissionEditTextListener, MaxDistanceEditTextListener maxDistanceEditTextListener, DistanceScaleOnItemSelectedListener distanceScaleOnItemSelectedListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.nameEditTextListener = nameEditTextListener;
            this.priceEditTextListener = priceEditTextListener;
            this.commissionEditTextListener = commissionEditTextListener;
            this.maxDistanceEditTextListener = maxDistanceEditTextListener;
            this.distanceScaleOnItemSelectedListener = distanceScaleOnItemSelectedListener;
        }


        void enableTextWatcher() {
            binding.serviceName.addTextChangedListener(nameEditTextListener);
            binding.servicePrice.addTextChangedListener(priceEditTextListener);
            binding.serviceCommission.addTextChangedListener(commissionEditTextListener);
            binding.maxDistance.addTextChangedListener(maxDistanceEditTextListener);
        }

        void disableTextWatcher() {
            binding.serviceName.removeTextChangedListener(nameEditTextListener);
            binding.servicePrice.removeTextChangedListener(priceEditTextListener);
            binding.serviceCommission.removeTextChangedListener(commissionEditTextListener);
            binding.maxDistance.removeTextChangedListener(maxDistanceEditTextListener);
        }

    }

    private class NameEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            carPrices.get(position).setName(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class PriceEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            carPrices.get(position).setPrice(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class CommissionEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            carPrices.get(position).setCommission(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
    
    private class MaxDistanceEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            carPrices.get(position).setMaxDistance(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class DistanceScaleOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            carPrices.get(position).setDistanceScale(i == 0 ? "less_or_equal" : "bigger");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}