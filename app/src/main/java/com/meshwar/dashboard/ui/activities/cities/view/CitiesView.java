package com.meshwar.dashboard.ui.activities.cities.view;


import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface CitiesView extends BaseView {
    void getCities(List<City> cities);
}
