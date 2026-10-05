package com.meshwar.dashboard.ui.activities.drivers.view;


import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface DriversView extends BaseView {

    void initDrivers(List<Driver> item);
}
