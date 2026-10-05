package com.meshwar.dashboard.ui.activities.chatList.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.DRIVER;
import static com.meshwar.dashboard.utils.AppSharedConstants.INTRODUCTION_TYPE;
import static com.meshwar.dashboard.utils.AppSharedConstants.USER_TYPE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.databinding.ActivityCitiesBinding;
import com.meshwar.dashboard.databinding.ActivitySupportChatListBinding;
import com.meshwar.dashboard.databinding.DialogCityBinding;
import com.meshwar.dashboard.ui.activities.chatList.presenter.ChatListPresenter;
import com.meshwar.dashboard.ui.adapter.AdapterCity;
import com.meshwar.dashboard.ui.adapter.AdapterLastMessages;
import com.meshwar.dashboard.ui.adapter.ChatAdapter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class ChatListActivity extends BaseActivity implements ChatListView {

    private ActivitySupportChatListBinding binding;
    private ChatListPresenter presenter;
    List<LastMessage> lastMessageList = new ArrayList<>();
    AdapterLastMessages adapterLastMessages;
    String usersType  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new ChatListPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        if (getIntent() != null)
            if (getIntent().hasExtra(USER_TYPE))
                usersType = getIntent().getStringExtra(USER_TYPE);

        binding.tvTitle.setText(usersType.equals(DRIVER)?getString(R.string.drivers_chats):getString(R.string.clients_chats));
        binding.rcChats.setAdapter(adapterLastMessages = new AdapterLastMessages(context, lastMessageList));

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getLastMessages(usersType,null);
    }

    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // Milliseconds

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                presenter.getLastMessages(usersType, Objects.requireNonNull(binding.search.getText()).toString());
                            }
                        },
                        DELAY
                );
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getChats(List<LastMessage> lastMessages) {
        this.lastMessageList.clear();
        this.lastMessageList.addAll(lastMessages);
        adapterLastMessages.notifyDataSetChanged();
    }
}