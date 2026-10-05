package com.meshwar.dashboard.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.ChatMessage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final Context context;
    List<ChatMessage> messages;

    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String DATE = "date";

    private static final String ADMIN_TO_USER = "admin_to_user";
    private static final String USER_TO_ADMIN = "user_to_admin";

    public ChatAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
//        preferencesHelper = new PreferencesHelper(context);

    }


    @Override
    public int getItemViewType(int position) {

        //type = admin_to_user , type = user_to_admin, type = 0 this date group message
        //contentType =  Text , contentType = image, contentType = date

        if (messages.get(position).getContentType().equals(DATE))
            return 0;
        else if (messages.get(position).getType().equals(ADMIN_TO_USER) && messages.get(position).getContentType().equals(TEXT))
            return 1;
        else if (messages.get(position).getType().equals(ADMIN_TO_USER) && messages.get(position).getContentType().equals(IMAGE))
            return 2;
        else if (messages.get(position).getType().equals(USER_TO_ADMIN) && messages.get(position).getContentType().equals(TEXT))
            return 3;
        else if (messages.get(position).getType().equals(USER_TO_ADMIN) && messages.get(position).getContentType().equals(IMAGE))
            return 4;

        else return 0;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_chat_sender_text, parent, false);

        if (viewType == 0)
            view = inflater.inflate(R.layout.item_chat_date, parent, false);
        else if (viewType == 1) {
            view = inflater.inflate(R.layout.item_chat_sender_text, parent, false);
        } else if (viewType == 2) {
            view = inflater.inflate(R.layout.item_chat_sender_image, parent, false);
        } else if (viewType == 3) {
            view = inflater.inflate(R.layout.item_chat_receiver_text, parent, false);
        } else if (viewType == 4) {
            view = inflater.inflate(R.layout.item_chat_receiver_image, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            holder.txt_message_date.setText(messages.get(position).getCreated_at());
        } else if (viewType == 1 || viewType == 3) {
            holder.txt_message_text.setText(messages.get(position).getContent());
            holder.tv_message_time.setText(messages.get(position).getCreatedTime());

        } else if (viewType == 2 || viewType == 4) {

            holder.tv_message_time.setText(messages.get(position).getCreatedTime());
            holder.imv_message_image.setOnClickListener(v -> showImageDialog(messages.get(position).getContent()));

            holder.progress.setVisibility(View.VISIBLE);
            holder.imv_message_image.setVisibility(View.GONE);
            Picasso.get().load(messages.get(position).getContent()).placeholder(R.drawable.logo).into(holder.imv_message_image, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progress.setVisibility(View.GONE);
                    holder.imv_message_image.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    holder.progress.setVisibility(View.GONE);
                    holder.imv_message_image.setVisibility(View.VISIBLE);
                    Picasso.get().load(R.drawable.logo).into(holder.imv_message_image);
                }

            });

        }


        if (getItemViewType(position) != 0) {

            Picasso.get().load(messages.get(position).getSenderImage()).into(holder.img_user);

            holder.img_user.setVisibility(View.INVISIBLE);
            if (
                    position != 0 &&
                            ((messages.get(position).getType().equals("1") && messages.get(position - 1).getType().equals("1")) ||
                                    (messages.get(position).getType().equals("2") && messages.get(position - 1).getType().equals("2")))
            ) {
                holder.img_user.setVisibility(View.INVISIBLE);
            } else {
                holder.img_user.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_user, imv_message_image;
        TextView txt_message_text, tv_message_time, txt_message_date;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_message_date = itemView.findViewById(R.id.txt_message_date);
            img_user = itemView.findViewById(R.id.img_user);
            imv_message_image = itemView.findViewById(R.id.imv_message_image);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            txt_message_text = itemView.findViewById(R.id.txt_message_text);
            progress = itemView.findViewById(R.id.progress);

        }
    }

    private void showImageDialog(String media) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        @SuppressLint("InflateParams") View mView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null);
        dialog.getWindow().setContentView(mView);
        ImageView imv_full = mView.findViewById(R.id.imv_full);
        ImageView imv_close = mView.findViewById(R.id.imv_close);
        Picasso.get().load(media).into(imv_full);
        imv_close.setOnClickListener(view -> dialog.dismiss());
    }

}

