package com.meshwar.dashboard.ui.activities.addCar.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.CAR_ID;
import static com.meshwar.dashboard.utils.AppSharedConstants.CAR_TYPES;
import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.IS_UPDATE;
import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Car;
import com.meshwar.dashboard.data.network.model.CarPrice;
import com.meshwar.dashboard.data.network.model.CarPriceSide;
import com.meshwar.dashboard.data.network.model.CarType;
import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.databinding.ActivityAddCarBinding;
import com.meshwar.dashboard.ui.activities.addCar.presenter.AddCarPresenter;
import com.meshwar.dashboard.ui.adapter.AdapterCarDetails;
import com.meshwar.dashboard.ui.adapter.AdapterCarPrice;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;

import okhttp3.RequestBody;

public class AddCarActivity extends BaseActivity implements AddCarView, AdapterCarPrice.PriceDeleted {

    private ActivityAddCarBinding binding;
    AddCarPresenter presenter;
    List<City> cities = new ArrayList<>();
    List<CarType> carTypes = new ArrayList<>();
    AdapterCarDetails adapterCarDetails;
    String carUuid = "";
    boolean isUpdate = false;
    String globalMaxDistance = "0";
    public String getGlobalMaxDistance() {
        return globalMaxDistance;
    }
    Car carDetails = new Car();

    Uri carUri;
    public ActivityResultLauncher<Intent> addCarTypeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new AddCarPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    // UUIDs المحمية — زر الحذف يُخفى لهذه السيارات
    private static final java.util.Set<String> PROTECTED_CAR_UUIDS = new java.util.HashSet<>(
            java.util.Arrays.asList(
                    "92bdda6c-9d9b-4c54-b5b7-e41e01c72327",
                    "060f83b4-4f2a-45be-a777-94f06408f0ea"
            )
    );

    @Override
    public void initView() {
        if (getIntent() != null)
            if (getIntent().hasExtra(IS_UPDATE))
                isUpdate = getIntent().getBooleanExtra(IS_UPDATE, false);
        if (getIntent().hasExtra(CAR_ID))
            carUuid = getIntent().getStringExtra(CAR_ID);

        binding.btnAddCar.setText(isUpdate ? getString(R.string.refresh) : getString(R.string.add_car));

        // أخفِ زر الحذف إذا كانت السيارة محمية أو في وضع الإضافة
        boolean isProtected = PROTECTED_CAR_UUIDS.contains(carUuid);
        binding.btnDeleteCar.setVisibility((isUpdate && !isProtected) ? View.VISIBLE : View.GONE);
        binding.btnToggleCar.setVisibility(isUpdate ? View.VISIBLE : View.GONE);

        // أخفِ تاب "خارج المدينة" (التاب الثاني) إذا كان الـ UUID هو 060f83b4-4f2a-45be-a777-94f06408f0ea
        if ("060f83b4-4f2a-45be-a777-94f06408f0ea".equals(carUuid)) {
            if (binding.tabLayoutServiceLocation.getTabCount() > 1) {
                binding.tabLayoutServiceLocation.removeTabAt(1);
            }
        }

        binding.rcCarPrices.setAdapter(adapterCarDetails = new AdapterCarDetails(context, carDetails, carTypes, cities));
    }


    @Override
    public void initData() {
        presenter.getMaxDistanceCars();
        presenter.getCarTypes();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initListener() {

        ActivityResultLauncher<Intent> launcherImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Uri uri = AppSharedMethod.getSelectedImageUri(context, result.getResultCode(), result.getData());
            if (uri != null) {
                // through Glide, so the car image request of edit mode can't overwrite the picked image
                Glide.with(context).load(uri).into(binding.imvCar);
                carUri = uri;
            }
        });

        binding.imvCar.setOnClickListener(v -> {
            Intent intent = AppSharedMethod.checkPermissionStorage(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE, AppSharedConstants.TYPE_IMAGE, AppSharedConstants.TYPE_ALL);
            if (intent != null) {
                launcherImage.launch(intent);
            }
        });

        binding.tvAddImage.setOnClickListener(v -> {
            Intent intent = AppSharedMethod.checkPermissionStorage(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE, AppSharedConstants.TYPE_IMAGE, AppSharedConstants.TYPE_ALL);
            if (intent != null) {
                launcherImage.launch(intent);
            }
        });

        addCarTypeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null && result.getResultCode() == RESULT_OK) {
                initCarTypes(new Gson().fromJson(result.getData().getStringExtra(CAR_TYPES), TypeToken.getParameterized(List.class, CarType.class).getType()));
                adapterCarDetails.notifyDataSetChanged(carDetails);
            }
        });


        binding.tabLayoutServiceLocation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapterCarDetails.showCitiesInput(tab.getPosition() == 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.linearAddCarType.setOnClickListener(v -> {
            if (binding.tabLayoutServiceLocation.getSelectedTabPosition() == 1) {
                carDetails.getCarPriceSide().getCarPriceOutSideList().add(new CarPriceSide(context, false, this));
            } else {
                carDetails.getCarPriceSide().getCarPriceInSideList().add(new CarPriceSide(context, true, this));
            }

            adapterCarDetails.notifyDataSetChanged(carDetails);
        });

        binding.btnAddCar.setOnClickListener(v -> {
            AppSharedMethod.hideKeyboard(context);

            if (carUri == null && !isUpdate) {
                showMessage(getString(R.string.add_the_car_image), INFO_ALERT);
                return;
            }
            if (binding.carName.getText() != null && TextUtils.isEmpty(binding.carName.getText().toString().trim())) {
                showMessage(getString(R.string.enter_car_name), INFO_ALERT);
                return;
            }


            List<MultipartBody.Part> carPricesInSideList = new java.util.ArrayList<>();
            for (int i = 0; i < carDetails.getCarPriceSide().getCarPriceInSideList().size(); i++) {
                String carType_uuid = carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarTypeUuid().equals("0") ? null : carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarTypeUuid();
                for (int s = 0; s < carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().size(); s++) {
                    carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s).setCarTypeUuid(carType_uuid);
                    if (checkValidPrice(carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s))) {
                        String jsonStr = carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s).getJson();
                        Log.e("UploadData", "inside_cars[" + carPricesInSideList.size() + "] = " + jsonStr);
                        carPricesInSideList.add(AppSharedMethod.getPartString("inside_cars[" + carPricesInSideList.size() + "]", jsonStr));
                    }
                }
            }


//            Map<String, CarPrice> carPricesOutSideList1 = new HashMap<>();
            List<MultipartBody.Part> carPricesOutSideList = new java.util.ArrayList<>();

            for (int i = 0; i < carDetails.getCarPriceSide().getCarPriceOutSideList().size(); i++) {
                String carType_uuid = carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarTypeUuid().equals("0") ? null : carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarTypeUuid();
                String cityFrom_uuid = null;
                String cityTo_uuid = null;

                for (int s = 0; s < carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().size(); s++) {
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCarTypeUuid(carType_uuid);
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCityFromUuid(cityFrom_uuid);
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCityToUuid(cityTo_uuid);
                    if (checkValidPrice(carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s))) {
                        String jsonStr = carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).getJson();
                        carPricesOutSideList.add(AppSharedMethod.getPartString("outside_cars[" + carPricesOutSideList.size() + "]", jsonStr));
                    }
                }
            }


            for (MultipartBody.Part part : inside_cars_deleted) {
                Log.e("UploadData", "inside_cars_deleted (UUID value is sent)");
            }
            for (MultipartBody.Part part : outside_cars_deleted) {
                Log.e("UploadData", "outside_cars_deleted (UUID value is sent)");
            }
            Log.e("UploadData", "uuid = " + carDetails.getUuid());
            Log.e("UploadData", "name = " + binding.carName.getText().toString());
            Log.e("UploadData", "is_active = " + (carDetails.isActive() ? "1" : "0"));

            if (isUpdate)
                presenter.editCar(
                        AppSharedMethod.getPartString("uuid", carDetails.getUuid()),
                        AppSharedMethod.getPartString("name", binding.carName.getText().toString()),
                        carDetails.isActive() ? "1" : "0",
                        carUri == null ? null : AppSharedMethod.getPart(context, carUri, "image"),
                        carPricesInSideList,
                        carPricesOutSideList,
                        inside_cars_deleted,
                        outside_cars_deleted);
            else
                presenter.addCar(carUri == null ? null : AppSharedMethod.getPart(context, carUri, "image"), AppSharedMethod.getPartString("name", binding.carName.getText().toString()), carPricesInSideList, carPricesOutSideList);
        });
        binding.btnDeleteCar.setOnClickListener(v -> presenter.deleteCar(carUuid));

        binding.btnToggleCar.setOnClickListener(v -> {
            AppSharedMethod.hideKeyboard(context);

            // بناء قوائم الأسعار نفسها المستخدمة في زر التحديث
            List<MultipartBody.Part> carPricesInSideList = new java.util.ArrayList<>();
            for (int i = 0; i < carDetails.getCarPriceSide().getCarPriceInSideList().size(); i++) {
                String carType_uuid = carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarTypeUuid().equals("0") ? null : carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarTypeUuid();
                for (int s = 0; s < carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().size(); s++) {
                    carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s).setCarTypeUuid(carType_uuid);
                    if (checkValidPrice(carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s))) {
                        String jsonStr = carDetails.getCarPriceSide().getCarPriceInSideList().get(i).getCarPrice().get(s).getJson();
                        Log.e("UploadDataToggle", "inside_cars[" + carPricesInSideList.size() + "] = " + jsonStr);
                        carPricesInSideList.add(AppSharedMethod.getPartString("inside_cars[" + carPricesInSideList.size() + "]", jsonStr));
                    }
                }
            }

            List<MultipartBody.Part> carPricesOutSideList = new java.util.ArrayList<>();
            for (int i = 0; i < carDetails.getCarPriceSide().getCarPriceOutSideList().size(); i++) {
                String carType_uuid = carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarTypeUuid().equals("0") ? null : carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarTypeUuid();
                String cityFrom_uuid = null;
                String cityTo_uuid = null;
                for (int s = 0; s < carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().size(); s++) {
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCarTypeUuid(carType_uuid);
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCityFromUuid(cityFrom_uuid);
                    carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).setCityToUuid(cityTo_uuid);
                    if (checkValidPrice(carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s))) {
                        String jsonStr = carDetails.getCarPriceSide().getCarPriceOutSideList().get(i).getCarPrice().get(s).getJson();
                        Log.e("UploadDataToggle", "outside_cars[" + carPricesOutSideList.size() + "] = " + jsonStr);
                        carPricesOutSideList.add(AppSharedMethod.getPartString("outside_cars[" + carPricesOutSideList.size() + "]", jsonStr));
                    }
                }
            }

            // نعكس قيمة is_active ونرسل الريكويست مباشرةً
            boolean newIsActive = !carDetails.isActive();
            presenter.editCar(
                    AppSharedMethod.getPartString("uuid", carDetails.getUuid()),
                    AppSharedMethod.getPartString("name", binding.carName.getText().toString()),
                    newIsActive ? "1" : "0",
                    carUri == null ? null : AppSharedMethod.getPart(context, carUri, "image"),
                    carPricesInSideList,
                    carPricesOutSideList,
                    inside_cars_deleted,
                    outside_cars_deleted);
        });

        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }

    private boolean checkValidPrice(CarPrice carPrice) {
        if (TextUtils.isEmpty(carPrice.getName()) && TextUtils.isEmpty(carPrice.getPrice()) && TextUtils.isEmpty(carPrice.getCommission()))
            return false;
        else return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initCarDetails(Car car) {
        this.carDetails = car;

        if (!TextUtils.isEmpty(car.getImage()))
            Glide.with(context).load(car.getImage()).into(binding.imvCar);

        binding.carName.setText(car.getName());

        // ضبط نص زر التعطيل/التفعيل بناءً على حالة السيارة الحالية
        updateToggleButton(car.isActive());

        for (CarPriceSide carPriceSide : carDetails.getCarPriceSide().getCarPriceOutSideList(context, false, this))
            carPriceSide.setAdapterCarPrice(context, false, this);

        for (CarPriceSide carPriceSide : carDetails.getCarPriceSide().getCarPriceInSideList(context, true, this))
            carPriceSide.setAdapterCarPrice(context, true, this);

        adapterCarDetails.notifyDataSetChanged(carDetails);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initCarTypes(List<CarType> carTypes) {
        Log.e("sssss", carTypes.size() + "");
        this.carTypes.clear();
        this.carTypes.addAll(carTypes);
        this.carTypes.add(0, new CarType("0", getString(R.string.select_type)));
        this.carTypes.add(new CarType("-1", getString(R.string.other)));
    }

    @Override
    public void getCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.cities.add(0, new City("0", getString(R.string.select_city)));

        if (!isUpdate) {
            initCarDetails(new Car());
        } else {
            presenter.getCarDetails(carUuid);
        }
    }

    @Override
    public void carAdded() {
        showMessage(getString(R.string.the_car_has_been_added), SUCCESS_ALERT);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void carEdited() {
        showMessage(getString(R.string.modified), SUCCESS_ALERT);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void carDeleted() {
        showMessage(getString(R.string.deleted_successfully), SUCCESS_ALERT);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onMaxDistanceLoaded(String maxDistance) {
        if (!TextUtils.isEmpty(maxDistance)) {
            this.globalMaxDistance = maxDistance;
        }
    }

    private void updateToggleButton(boolean isActive) {
        if (isActive) {
            binding.btnToggleCar.setText(getString(R.string.deactivate));
              } else {
            binding.btnToggleCar.setText(getString(R.string.activate));
         }
    }


    private List<okhttp3.MultipartBody.Part> inside_cars_deleted = new java.util.ArrayList<>();
    private List<okhttp3.MultipartBody.Part> outside_cars_deleted = new java.util.ArrayList<>();

    @Override
    public void onPriceDeleted(String uuid, boolean inside) {
        if (TextUtils.isEmpty(uuid))
            return;

        if (inside)
            inside_cars_deleted.add(AppSharedMethod.getPartString("inside_cars_deleted[" + inside_cars_deleted.size() + "]", uuid));
        else
            outside_cars_deleted.add(AppSharedMethod.getPartString("outside_cars_deleted[" + outside_cars_deleted.size() + "]", uuid));

    }
}