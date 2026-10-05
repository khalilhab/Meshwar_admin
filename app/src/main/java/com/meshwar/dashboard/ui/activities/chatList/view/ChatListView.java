package com.meshwar.dashboard.ui.activities.chatList.view;


import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.LastMessage;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface ChatListView extends BaseView {

    void getChats(List<LastMessage> lastMessages);
}
