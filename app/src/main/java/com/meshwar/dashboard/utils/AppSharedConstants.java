package com.meshwar.dashboard.utils;

public interface AppSharedConstants {

    String TAG = "ttt";
    String BEARER = "Bearer ";
    String AUTHORIZATION = "Authorization";
    String MOBILE = "mobile";
    String DEVICE = "android";
    String TYPE_ALL = "all";
    String TYPE_IMAGE = "image";
    String TYPE_VIDEO = "video";
    String TYPE_CAMERA = "camera";
    String TYPE_GALLERY = "gallery";
    String INTRODUCTION_TYPE = "INTRODUCTION_TYPE";
    String USER_TYPE = "USER_TYPE";
    String CHAT_ID = "CHAT_ID";
    String USER_ID = "USER_ID";
    String LAST_MESSAGE = "LastMessage";
    String ORDER_ID = "ORDER_ID";
    String USER = "user";
    String DRIVER = "driver";
    String USER_INTRODUCTIONS = "1";
    String TYPE_INTRODUCTIONS = "2";
    String CAR_TYPES = "car_types";

    //alert type
    public static final int SUCCESS_ALERT = 1;
    public static final int FAIL_ALERT = 2;
    public static final int INFO_ALERT = 3;

    int REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE = 1001;
    int REQUEST_PERMISSIONS_CODE_IMAGE_COVER = 1002;
    int REQUEST_PERMISSIONS_CODE_IMAGE_DRIVING_LICENSE_IMAGE = 1003;
    int REQUEST_PERMISSIONS_CODE_IMAGE_ID_IMAGE = 1004;
    int REQUEST_PERMISSIONS_CODE_IMAGE_CAR_FRONT_IMAGE = 1005;
    int REQUEST_PERMISSIONS_CODE_IMAGE_CAR_BACK_IMAGE = 1006;
    int REQUEST_PERMISSIONS_CODE_IMAGE_CAR_REGISTRATION_FORM_IMAGE = 1007;
    int REQUEST_PERMISSIONS_CODE_CAMERA = 1003;
    int REQUEST_PERMISSIONS_CODE_GALLERY = 1004;
    int REQUEST_PERMISSIONS_CODE_LOCATION = 1005;
    int REQUEST_PERMISSIONS_CODE_AUDIO = 1006;
    int REQUEST_CHECK_SETTINGS = 214;

    String DRIVERS = "Drivers";
    String DRIVER_ID = "DRIVER_ID";
    String CAR_ID = "CAR_ID";
    String IS_UPDATE = "IS_UPDATE";


    interface SourceType {
        String DRAW = "draw";
        String PAY = "pay";
        String WALLET = "wallet";
        String AGENTS = "agents";
    }

    interface PaidStatus {
        String UNPAID = "unpaid";
    }

    interface Notifications {

        String SEND_MSG = "send_msg";

        String ACCEPT_ORDER = "accept_order";

    }


}
