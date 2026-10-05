package com.meshwar.dashboard.ui.activities.chatList.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.ui.activities.chatList.view.ChatListView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class ChatListPresenter extends BasePresenter {

    private final ChatListView view;

    public ChatListPresenter(Context context, ChatListView view) {
        super(context, view);
        this.view = view;
    }




    public void getLastMessages(String usersType,String nameSearch) {
        requestArray(true,mService.getMessagesUsers(getUserBearerToken(),usersType,nameSearch), "getMessages", LastMessage.class, (apiResponse, item) -> {
            view.getChats(item);
        });
    }
}
