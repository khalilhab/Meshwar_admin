package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("super_admin")
    private boolean superAdmin;
    @Expose
    @SerializedName("wallet")
    private String wallet;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("mobile_without_prefix")
    private String mobile_without_prefix;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("uuid")
    private String uuid;

    public String getMobile_without_prefix() {
        return mobile_without_prefix;
    }

    public void setMobile_without_prefix(String mobile_without_prefix) {
        this.mobile_without_prefix = mobile_without_prefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
