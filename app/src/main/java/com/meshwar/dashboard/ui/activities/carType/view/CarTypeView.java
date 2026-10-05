package com.meshwar.dashboard.ui.activities.carType.view;


import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface CarTypeView extends BaseView {

    void getCarTypes(List<CarType> carTypes);
}
