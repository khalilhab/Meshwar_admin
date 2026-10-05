package com.meshwar.dashboard.ui.activities.intro.view;


import com.meshwar.dashboard.data.network.model.Intro;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface IntroView extends BaseView {
    void InitIntroductions(List<Intro> intros);
}
