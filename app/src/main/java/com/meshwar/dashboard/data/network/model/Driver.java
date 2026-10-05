package com.meshwar.dashboard.data.network.model;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.meshwar.dashboard.R;

import java.util.List;

public class Driver {

    @Expose
    @SerializedName("cities_ids")
    private List<String> cities_ids;
    @Expose
    @SerializedName("rating_avg")
    private String ratingAvg;
    @Expose
    @SerializedName("car_name")
    private String carName;
    @Expose
    @SerializedName("available_text")
    private String availableText;
    @Expose
    @SerializedName("is_available")
    private boolean isAvailable;
    @Expose
    @SerializedName("license_image2")
    private String licenseImage2;
    @Expose
    @SerializedName("license_image1")
    private String licenseImage1;
    @Expose
    @SerializedName("identity_image2")
    private String identityImage2;
    @Expose
    @SerializedName("identity_image1")
    private String identityImage1;
    @Expose
    @SerializedName("city_uuid")
    private String city_uuid;
    @Expose
    @SerializedName("service_type")
    private String service_type;
    @Expose
    @SerializedName("city_name")
    private String city_name;
    @Expose
    @SerializedName("license")
    private String license;
    @Expose
    @SerializedName("is_accepted")
    private boolean isAccepted;
    @Expose
    @SerializedName("bio")
    private String bio;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("car_uuid")
    private String carUuid;
    @Expose
    @SerializedName("uuid")
    private String uuid;
    @Expose
    @SerializedName("car_type_uuid")
    private String carTypeUuid;
    @Expose
    @SerializedName("role")
    private String role;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("mobile_without_prefix")
    private String mobileWithoutPrefix;
    @Expose
    @SerializedName("car_type_name")
    private String carTypeName;
    @Expose
    @SerializedName("car_type")
    private CarType carType;

    public List<String> getCities_ids() {
        return cities_ids;
    }

    public void setCities_ids(List<String> cities_ids) {
        this.cities_ids = cities_ids;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getCity_uuid() {
        return city_uuid;
    }

    public void setCity_uuid(String city_uuid) {
        this.city_uuid = city_uuid;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(String ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getAvailableText() {
        return availableText;
    }

    public void setAvailableText(String availableText) {
        this.availableText = availableText;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getLicenseImage2() {
        return licenseImage2;
    }

    public void setLicenseImage2(String licenseImage2) {
        this.licenseImage2 = licenseImage2;
    }

    public String getLicenseImage1() {
        return licenseImage1;
    }

    public void setLicenseImage1(String licenseImage1) {
        this.licenseImage1 = licenseImage1;
    }

    public String getIdentityImage2() {
        return identityImage2;
    }

    public void setIdentityImage2(String identityImage2) {
        this.identityImage2 = identityImage2;
    }

    public String getIdentityImage1() {
        return identityImage1;
    }

    public void setIdentityImage1(String identityImage1) {
        this.identityImage1 = identityImage1;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarUuid() {
        return carUuid;
    }

    public void setCarUuid(String carUuid) {
        this.carUuid = carUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getService_type_text(Context context) {
        if (getService_type().equals("inside")){
            return String.format("%s",context.getString(R.string.inside_city));
        }else if (getService_type().equals("outside"))
            return String.format("%s",context.getString(R.string.outside_city));
        else
            return String.format("%s",context.getString(R.string.outside_inside_city));
    }

    public String getCarTypeUuid() {
        return carTypeUuid;
    }

    public void setCarTypeUuid(String carTypeUuid) {
        this.carTypeUuid = carTypeUuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMobileWithoutPrefix() {
        return mobileWithoutPrefix;
    }

    public void setMobileWithoutPrefix(String mobileWithoutPrefix) {
        this.mobileWithoutPrefix = mobileWithoutPrefix;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarTypeNameHelper() {
        if (carTypeName != null && !carTypeName.trim().isEmpty()) {
            return carTypeName;
        }
        if (carType != null && carType.getName() != null && !carType.getName().trim().isEmpty()) {
            return carType.getName();
        }
        return null;
    }
}
