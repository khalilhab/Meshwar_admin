package com.meshwar.dashboard.data.network;

public interface ApiEndPoint {
    String REGISTER = "admin/register";
    String LOGIN = "admin/login";
    String PROFILE = "admin/profile";
    String EDIT_PROFILE = "admin/edit_profile";
    String CHANGE_PASSWORD = "admin/change_password";
    String LOGOUT = "admin/logout";

    String CARS = "admin/cars";
    String CAR_SHOW = "admin/cars/show";
    String ADD_CAR = "admin/cars/store";
    String EDIT_CAR = "admin/cars/edit";
    String DELETE_CAR = "admin/cars/destroy";

    String DRIVERS = "admin/drivers";
    String DRIVER_SHOW = "admin/drivers/show";
    String DRIVER_SEND_NOTIFICATION = "admin/drivers/send_notification";
    String ACCEPT_DRIVER = "admin/drivers/accept";

    String CITIES = "cities";
    String ADD_CITY = "admin/cities/store";
    String EDIT_CITY = "admin/cities/update";
    String DELETE_CITY = "admin/cities/destroy";

    String CAR_TYPES = "admin/car_types";
    String ADD_CAR_TYPES = "admin/car_types/store";
    String EDIT_CAR_TYPES = "admin/car_types/update";
    String DELETE_CAR_TYPES = "admin/car_types/destroy";

    String TERM = "admin/term";
    String EDIT_TERM = "admin/edit_terms";

    String INTRODUCTIONS = "introductions";
    String ADD_INTRODUCTION = "admin/introductions/store";
    String DELETE_INTRODUCTION = "admin/introductions/destroy";
    String EDIT_INTRODUCTION = "admin/introductions/update";

    String GET_MESSAGES_USERS = "admin/messages/users";
    String GET_MESSAGES = "admin/messages";
    String SEND_MESSAGES = "admin/messages/send";

    String ORDERS = "admin/orders";
    String ORDER_DETAILS = "admin/orders/show";
    String ORDER_PAYMENT = "admin/orders/payment";

    String BALANCE = "admin/balance";
    String MAX_DISTANCE_CARS = "admin/setting/max-distance-cars";
}