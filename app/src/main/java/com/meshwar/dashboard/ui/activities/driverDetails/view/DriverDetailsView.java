package com.meshwar.dashboard.ui.activities.driverDetails.view;


import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.Driver;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface DriverDetailsView extends BaseView {

    void initDriver(Driver item);

    void driverAccepted();

    void getCities(List<City> item);
}
