package com.meshwar.dashboard.ui.activities.cars.view;


import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface CarsView extends BaseView {
    void initCars(List<Car> item, int currentPage, int lastPage);
}