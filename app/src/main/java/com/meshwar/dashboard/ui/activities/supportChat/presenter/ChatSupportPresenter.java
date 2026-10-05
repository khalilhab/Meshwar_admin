package com.meshwar.dashboard.ui.activities.supportChat.presenter;

import android.content.Context;
import android.net.Uri;

import com.meshwar.dashboard.data.network.model.ChatMessage;
import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.ui.activities.supportChat.view.ChatSupportView;
import com.meshwar.dashboard.ui.base.BasePresenter;
import com.meshwar.dashboard.utils.AppSharedMethod;


public class ChatSupportPresenter extends BasePresenter {

    private final ChatSupportView view;

    public ChatSupportPresenter(Context context, ChatSupportView view) {
        super(context, view);
        this.view = view;
    }


    public void getChatMessages(String user_uuid,boolean receivedMsg) {
            requestArray(true,mService.getMessages(getUserBearerToken(),user_uuid), "getMessages", ChatMessage.class, (apiResponse, item) -> {
                view.initMessages(item,receivedMsg);
            });

    }


    public void sendMessage(String msgType, String msgText, Uri file,String user_uuid) {
        requestArray(true, mService.sendMessages(getUserBearerToken(),AppSharedMethod.getPart(user_uuid), AppSharedMethod.getPart(msgType), msgType.equals("text") ? AppSharedMethod.getPart(msgText) : null,
                msgType.equals("text") ? null: AppSharedMethod.getPart(context, file,"body" )), "sendMessages", ChatMessage.class, (apiResponse, item) -> {
            getChatMessages(user_uuid,false);
        });
    }
}
