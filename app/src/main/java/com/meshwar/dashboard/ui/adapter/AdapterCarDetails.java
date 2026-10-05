package com.meshwar.dashboard.ui.adapter;

import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.data.network.model.CarPrice;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.databinding.ItemAddCarBinding;
import com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity;
import com.meshwar.dashboard.ui.activities.carType.view.CarTypeActivity;
import com.meshwar.dashboard.ui.base.BaseActivity;

import java.util.List;

public class AdapterCarDetails extends RecyclerView.Adapter<AdapterCarDetails.ViewHolder> {

    private final Context context;
    private List<CarType> carTypes;
    private List<City> cities;
    boolean outsideCities = false;
    boolean carTypeSpinnerTouched = false;
    Car carDetails;
    ArrayAdapter<City> citiesAdapterFrom;
    ArrayAdapter<City> citiesAdapterTo;
    ArrayAdapter<CarType> brandsAdapter;

    public AdapterCarDetails(Context context, Car carDetails, List<CarType> carTypes, List<City> cities) {
        this.context = context;
        this.carTypes = carTypes;
        this.carDetails = carDetails;
        this.cities = cities;

        this.brandsAdapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_layout, carTypes);
        this.citiesAdapterFrom = new ArrayAdapter<>(context, R.layout.spinner_dropdown_layout, cities);
        this.citiesAdapterTo = new ArrayAdapter<>(context, R.layout.spinner_dropdown_layout, cities);

        this.citiesAdapterFrom.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.citiesAdapterTo.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.brandsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
    }


    @NonNull
    @Override
    public AdapterCarDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCarDetails.ViewHolder(ItemAddCarBinding.inflate(LayoutInflater.from(context), parent, false), new CarTypesOnItemSelectedListener(), new CityFromOnItemSelectedListener(), new CityToOnItemSelectedListener());
    }


    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull AdapterCarDetails.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (outsideCities) {
            holder.binding.spFromPlace.setAdapter(citiesAdapterFrom);
            holder.binding.spToPlace.setAdapter(citiesAdapterTo);

            holder.cityFromOnItemSelectedListener.updatePosition(position);
            holder.cityToOnItemSelectedListener.updatePosition(position);

            holder.binding.spFromPlace.setOnItemSelectedListener(holder.cityFromOnItemSelectedListener);
            holder.binding.spToPlace.setOnItemSelectedListener(holder.cityToOnItemSelectedListener);

            holder.binding.spFromPlace.setSelection(carDetails.getCarPriceSide().getCarPriceOutSideList().get(position).getFromCitySelectedPosition(cities));
            holder.binding.spToPlace.setSelection(carDetails.getCarPriceSide().getCarPriceOutSideList().get(position).getToCiTySelectedPosition(cities));
        }


        holder.binding.spCarType.setAdapter(brandsAdapter);
        holder.carTypeSOnItemSelectedListener.updatePosition(position);
        holder.binding.spCarType.setSelection(outsideCities ? carDetails.getCarPriceSide().getCarPriceOutSideList().get(position).getCarTypeSelectedPosition(carTypes) :
                carDetails.getCarPriceSide().getCarPriceInSideList().get(position).getCarTypeSelectedPosition(carTypes));

        holder.binding.spCarType.setOnItemSelectedListener(holder.carTypeSOnItemSelectedListener);


        // تم تعطيل اختيار المدن (من - إلى) حالياً بناءً على طلب المستخدم
        holder.binding.tvSelectCity.setVisibility(View.GONE);
        holder.binding.spFromPlace.setVisibility(View.GONE);
        holder.binding.spToPlace.setVisibility(View.GONE);
        holder.binding.imvArrowDown1.setVisibility(View.GONE);
        holder.binding.imvArrowDown2.setVisibility(View.GONE);

        holder.binding.tvAddPrice.setOnClickListener(v -> {

            if (outsideCities) {
                CarPrice price = new CarPrice();
                if (context instanceof com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) {
                    price.setMaxDistance(((com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) context).getGlobalMaxDistance());
                }
                price.setDistanceScale("bigger");
                carDetails.getCarPriceSide()
                        .getCarPriceOutSideList().get(position).getCarPrice()
                        .add(price);


                carDetails.getCarPriceSide().getCarPriceOutSideList().get(position).getAdapterCarPrice().notifyDataSetChanged();
            } else {
                CarPrice price = new CarPrice();
                if (context instanceof com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) {
                    price.setMaxDistance(((com.meshwar.dashboard.ui.activities.addCar.view.AddCarActivity) context).getGlobalMaxDistance());
                }
                price.setDistanceScale("less_or_equal");
                carDetails.getCarPriceSide()
                        .getCarPriceInSideList().get(position).getCarPrice()
                        .add(price);

                carDetails.getCarPriceSide().getCarPriceInSideList().get(position).getAdapterCarPrice().notifyDataSetChanged();

            }

        });

        holder.binding.rcPrices.setAdapter(outsideCities ? carDetails.getCarPriceSide().getCarPriceOutSideList().get(position).getAdapterCarPrice() :
                carDetails.getCarPriceSide().getCarPriceInSideList().get(position).getAdapterCarPrice());

    }


    @Override
    public int getItemCount() {
        if (outsideCities) {
            return carDetails.getCarPriceSide().getCarPriceOutSideList().size();
        } else {
            return carDetails.getCarPriceSide().getCarPriceInSideList().size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showCitiesInput(boolean showCities) {
        this.outsideCities = showCities;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDataSetChanged(Car carDetails) {
        this.carDetails = carDetails;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAddCarBinding binding;
        CarTypesOnItemSelectedListener carTypeSOnItemSelectedListener;
        CityFromOnItemSelectedListener cityFromOnItemSelectedListener;
        CityToOnItemSelectedListener cityToOnItemSelectedListener;

        @SuppressLint("ClickableViewAccessibility")
        public ViewHolder(ItemAddCarBinding binding, CarTypesOnItemSelectedListener carTypeSOnItemSelectedListener, CityFromOnItemSelectedListener cityFromOnItemSelectedListener, CityToOnItemSelectedListener cityToOnItemSelectedListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.carTypeSOnItemSelectedListener = carTypeSOnItemSelectedListener;
            this.cityFromOnItemSelectedListener = cityFromOnItemSelectedListener;
            this.cityToOnItemSelectedListener = cityToOnItemSelectedListener;


            binding.spCarType.setOnTouchListener((View v, MotionEvent event) -> {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    carTypeSpinnerTouched = true; // User DID touched the spinner!
                }

                return false;
            });
        }
    }

    private class CarTypesOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int itemPosition;

        public void updatePosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (outsideCities) {
                if (carTypeSpinnerTouched && carDetails.getCarPriceSide().getSelectedCarTypes(false).contains(carTypes.get(position).getUuid())) {
                    ((BaseActivity) context).showMessage(context.getString(R.string.Duplicate_car_types), INFO_ALERT);
                } else
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(itemPosition).setCarTypeUuid(carTypes.get(position).getUuid());
            } else {
                if (carTypeSpinnerTouched && carDetails.getCarPriceSide().getSelectedCarTypes(true).contains(carTypes.get(position).getUuid())) {
                    ((BaseActivity) context).showMessage(context.getString(R.string.Duplicate_car_types), INFO_ALERT);
                } else
                    carDetails.getCarPriceSide().getCarPriceInSideList().get(itemPosition)
                            .setCarTypeUuid(carTypes.get(position).getUuid());
            }

            if (position == carTypes.size() - 1 && carTypeSpinnerTouched) {
                if (context instanceof AddCarActivity) {
                    ((AddCarActivity) context).addCarTypeLauncher.launch(new Intent(context, CarTypeActivity.class));
                }
            }
                carTypeSpinnerTouched = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class CityFromOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int itemPosition;

        public void updatePosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (outsideCities) {
                carDetails.getCarPriceSide().getCarPriceOutSideList().get(itemPosition).setCityFromUuid(cities.get(position).getUuid());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class CityToOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int itemPosition;

        public void updatePosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (outsideCities) {
                carDetails.getCarPriceSide().getCarPriceOutSideList().get(itemPosition).setCityToUuid(cities.get(position).getUuid());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}