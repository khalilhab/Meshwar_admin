package com.meshwar.dashboard.ui.activities.addCar.view;


import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface AddCarView extends BaseView {

    void initCarDetails(Car car);

    void initCarTypes(List<CarType> item);

    void getCities(List<City> item);

    void carAdded();

    void carEdited();

    void carDeleted();
    
    void onMaxDistanceLoaded(String maxDistance);
}