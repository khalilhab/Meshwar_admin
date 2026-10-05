package com.meshwar.dashboard.ui.activities.supportChat.view;


import com.meshwar.dashboard.data.network.model.ChatMessage;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface ChatSupportView extends BaseView {

    void initMessages(List<ChatMessage> chatMessages, boolean receivedMsg);
}
