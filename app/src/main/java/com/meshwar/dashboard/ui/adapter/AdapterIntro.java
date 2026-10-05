package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Intro;
import com.meshwar.dashboard.databinding.ItemIntroBinding;

import java.util.List;

public class AdapterIntro extends RecyclerView.Adapter<AdapterIntro.ViewHolder> {

    private Context context;
    private List<Intro> intros;

    int currentPosition = 0;

    public AdapterIntro(Context context, List<Intro> intros, onItemClicked onItemClicked) {
        this.context = context;
        this.intros = intros;
        this.onItemClicked = onItemClicked;

    }

    public int getSelectedPosition() {
        return currentPosition;
    }

    public void setSelectedPosition() {
        currentPosition = 0;
    }

    public interface onItemClicked {
        void setActionType(String actionType);
    }

    private onItemClicked onItemClicked;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterIntro.ViewHolder(ItemIntroBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.btnAdd.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
        holder.binding.btnDelete.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.binding.btnEdit.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);

        // the view may be recycled with a running Glide request, so every image goes through Glide (or clears it)
        if (position == 0) {
            if (intros.get(position).getNewImageUri() == null) {
                Glide.with(context).clear(holder.binding.imvIntro);
                holder.binding.imvIntro.setImageResource(R.drawable.add);
            } else
                Glide.with(context).load(intros.get(position).getNewImageUri()).into(holder.binding.imvIntro);
        }else {
            if (intros.get(position).getNewImageUri() == null){
                Log.e("1cccccc","cccc"+position);
                Glide.with(context).load(intros.get(position).getImage()).into(holder.binding.imvIntro);
            }
            else{
                Log.e("2cccccc","cccc"+position);
                Glide.with(context).load(intros.get(position).getNewImageUri()).into(holder.binding.imvIntro);
            }
        }


        holder.binding.edDescription.setText(intros.get(position).getDescription());

        holder.binding.linearAddImage.setOnClickListener(v -> {
            currentPosition = position;
            onItemClicked.setActionType("add_image");
        });
        holder.binding.tvAddImage.setOnClickListener(v -> {
            currentPosition = position;
            onItemClicked.setActionType("add_image");
        });
        holder.binding.btnAdd.setOnClickListener(v -> {
            currentPosition = position;
            onItemClicked.setActionType("add");
        });
        holder.binding.btnDelete.setOnClickListener(v -> {
            currentPosition = position;
            onItemClicked.setActionType("delete");
        });
        holder.binding.btnEdit.setOnClickListener(v -> {
            currentPosition = position;
            onItemClicked.setActionType("edit");
        });

        intros.get(position).setTextWatcher(holder.binding.edDescription);

    }


    @Override
    public int getItemCount() {
        return intros.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemIntroBinding binding;

        public ViewHolder(ItemIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}