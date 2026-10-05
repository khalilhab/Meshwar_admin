package com.meshwar.dashboard.ui.activities.supportChat.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.LAST_MESSAGE;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.ChatMessage;
import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.databinding.ActivityChatSupportBinding;
import com.meshwar.dashboard.interfaces.Task;
import com.meshwar.dashboard.ui.activities.supportChat.presenter.ChatSupportPresenter;
import com.meshwar.dashboard.ui.adapter.ChatAdapter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.List;


public class ChatSupportActivity extends BaseActivity implements ChatSupportView, Task {

    private ActivityChatSupportBinding binding;

    private ChatSupportPresenter presenter;

    String chat_id = "";
    ChatAdapter chatAdapter;
    List<ChatMessage> chatMessages = new ArrayList<>();

    LastMessage lastMessage;

    public static Task updateStatus;

    ActivityResultLauncher<Intent> launcherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new ChatSupportPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        if (getIntent() != null)
            if (getIntent().hasExtra(LAST_MESSAGE))
                lastMessage = new Gson().fromJson(getIntent().getStringExtra(LAST_MESSAGE), LastMessage.class);
//            if (getIntent().hasExtra(USER_ID))

        Glide.with(context).load(lastMessage.getImage()).centerCrop().placeholder(R.drawable.logo).into(binding.imvDriver);
        binding.tvDriverName.setText(lastMessage.getName());

        chat_id = lastMessage.getUuid();

        binding.rcChats.setAdapter(chatAdapter = new ChatAdapter(context, chatMessages));
    }

    @Override
    public void initData() {
        updateStatus = this;
        presenter.getChatMessages(chat_id, false);
    }

    @Override
    public void initListener() {

        launcherImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Uri uri = AppSharedMethod.getSelectedImageUri(context, result.getResultCode(), result.getData());
            if (uri != null) {
                presenter.sendMessage("image", "", uri, chat_id);
            }
        });
        binding.imvAttachment.setOnClickListener(v -> {
            Intent intent = AppSharedMethod.checkPermissionStorage(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE, AppSharedConstants.TYPE_IMAGE, AppSharedConstants.TYPE_ALL);
            if (intent != null) {
                launcherImage.launch(intent);
            }
        });

        binding.send.setOnClickListener(v -> {
            if (!binding.edMessage.getText().toString().isEmpty() && !binding.edMessage.getText().toString().trim().isEmpty()) {
                presenter.sendMessage("text", binding.edMessage.getText().toString(), null, chat_id);
            } else
                showMessage(getString(R.string.cannot_send_an_empty_message), INFO_ALERT);
        });

        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.tvFirstMsg.setOnClickListener(v -> {
            binding.linearChatContent.setVisibility(View.VISIBLE);
            binding.tvHelpYou.setVisibility(View.VISIBLE);
            binding.cardWelcomeMsg.setVisibility(View.INVISIBLE);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initMessages(List<ChatMessage> chatMessages, boolean receivedMsg) {
        this.chatMessages.clear();
        this.chatMessages.addAll(chatMessages);
        chatAdapter.notifyDataSetChanged();


        binding.rcChats.scrollToPosition(chatMessages.size()-1);

        if (!receivedMsg)
            binding.edMessage.setText("");
    }

    @Override
    public void onComplete(Object obj) {
        presenter.getChatMessages(chat_id, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateStatus = null;
    }
}