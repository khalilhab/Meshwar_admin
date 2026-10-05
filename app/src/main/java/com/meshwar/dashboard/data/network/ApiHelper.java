package com.meshwar.dashboard.data.network;


import static com.meshwar.dashboard.utils.AppSharedConstants.AUTHORIZATION;

import com.meshwar.dashboard.data.network.model.ApiResponse;
import com.meshwar.dashboard.data.network.model.CarPrice;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiHelper {

    @FormUrlEncoded
    @POST(ApiEndPoint.REGISTER)
    Call<ApiResponse> registerAdmin(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("mobile") String mobile,
                                    @Field("password") String password,
                                    @Field("fcm_token") String fcm_token,
                                    @Field("device") String device);


    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    Call<ApiResponse> loginAdmin(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("fcm_token") String fcm_token,
                                 @Field("device") String device);

    @FormUrlEncoded
    @POST(ApiEndPoint.CHANGE_PASSWORD)
    Call<ApiResponse> changePassword(@Header(AUTHORIZATION) String token,
                                     @Field("current_password") String current_password,
                                     @Field("new_password") String new_password,
                                     @Field("confirm_password") String confirm_password);

    @Multipart
    @POST(ApiEndPoint.EDIT_PROFILE)
    Call<ApiResponse> editProfile(@Header(AUTHORIZATION) String token,
                                  @Part("name") RequestBody name,
                                  @Part("email") RequestBody email,
                                  @Part("mobile") RequestBody mobile,
                                  @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGOUT)
    Call<ApiResponse> logoutAdmin(@Header(AUTHORIZATION) String token,
                                  @Field("fcm_token") String fcm_token);


    @GET(ApiEndPoint.PROFILE)
    Call<ApiResponse> getProfile(@Header(AUTHORIZATION) String token);


    @GET(ApiEndPoint.CARS)
    Call<ApiResponse> getCars(@Header(AUTHORIZATION) String token,
                              @Query("has_driver") String has_driver,
                              @Query("page") int page);


    @GET(ApiEndPoint.CAR_SHOW)
    Call<ApiResponse> getCarDetails(@Header(AUTHORIZATION) String token,
                                    @Query("uuid") String uuid);

    @Multipart
    @POST(ApiEndPoint.ADD_CAR)
    Call<ApiResponse> addCar(@Header(AUTHORIZATION) String token,
                             @Part MultipartBody.Part name,
                             @Part List<MultipartBody.Part> carPricesInSideList,
                             @Part List<MultipartBody.Part> carPricesOutSideList,
                             @Part MultipartBody.Part image);


    @Multipart
    @POST(ApiEndPoint.EDIT_CAR)
    Call<ApiResponse> editCar(@Header(AUTHORIZATION) String token,
                              @Part MultipartBody.Part uuid,
                              @Part MultipartBody.Part name,
                              @Query("is_active") String isActive,
                              @Part List<MultipartBody.Part> carPricesInSideList,
                              @Part List<MultipartBody.Part> carPricesOutSideList,
                              @Part List<MultipartBody.Part> inside_cars_deleted,
                              @Part List<MultipartBody.Part> outside_cars_deleted,
                              @Part MultipartBody.Part image);


    @DELETE(ApiEndPoint.DELETE_CAR)
    Call<ApiResponse> deleteCar(@Header(AUTHORIZATION) String token,
                                @Query("uuid") String uuid);


    @GET(ApiEndPoint.DRIVERS)
    Call<ApiResponse> getDrivers(@Header(AUTHORIZATION) String token,
                                 @Query("name") String name);

    @GET(ApiEndPoint.DRIVER_SHOW)
    Call<ApiResponse> getDriverDetails(@Header(AUTHORIZATION) String token,
                                       @Query("uuid") String uuid);


    @FormUrlEncoded
    @POST(ApiEndPoint.DRIVER_SEND_NOTIFICATION)
    Call<ApiResponse> driverSendNotification(@Header(AUTHORIZATION) String token,
                                             @Field("uuid") String uuid,
                                             @Field("message") String message);

    @FormUrlEncoded
    @POST(ApiEndPoint.ACCEPT_DRIVER)
    Call<ApiResponse> acceptDriver(@Header(AUTHORIZATION) String token,
                                   @Field("uuid") String uuid);


    @GET(ApiEndPoint.CITIES)
    Call<ApiResponse> getCities(@Header(AUTHORIZATION) String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.ADD_CITY)
    Call<ApiResponse> addCity(@Header(AUTHORIZATION) String token,
                              @Field("name") String name);

    @FormUrlEncoded
    @PUT(ApiEndPoint.EDIT_CITY)
    Call<ApiResponse> editCity(@Header(AUTHORIZATION) String token,
                               @Field("uuid") String uuid,
                               @Field("name") String name);

    @DELETE(ApiEndPoint.DELETE_CITY)
    Call<ApiResponse> deleteCity(@Header(AUTHORIZATION) String token,
                                 @Query("uuid") String uuid);

    @GET(ApiEndPoint.CAR_TYPES)
    Call<ApiResponse> getCarTypes(@Header(AUTHORIZATION) String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.ADD_CAR_TYPES)
    Call<ApiResponse> addCarType(@Header(AUTHORIZATION) String token,
                                 @Field("name") String name);

    @FormUrlEncoded
    @PUT(ApiEndPoint.EDIT_CAR_TYPES)
    Call<ApiResponse> editCarType(@Header(AUTHORIZATION) String token,
                                  @Field("uuid") String uuid,
                                  @Field("name") String name);

    @DELETE(ApiEndPoint.DELETE_CAR_TYPES)
    Call<ApiResponse> deleteCarType(@Header(AUTHORIZATION) String token,
                                    @Query("uuid") String uuid);


    @GET(ApiEndPoint.TERM)
    Call<ApiResponse> getTerm(@Header(AUTHORIZATION) String token);


    @FormUrlEncoded
    @PUT(ApiEndPoint.EDIT_TERM)
    Call<ApiResponse> editTerm(@Header(AUTHORIZATION) String token,
                               @Field("description") String description);

    @GET(ApiEndPoint.INTRODUCTIONS)
    Call<ApiResponse> getIntroductions(@Header(AUTHORIZATION) String token,
                                       @Query("type") String type);

    @Multipart
    @POST(ApiEndPoint.ADD_INTRODUCTION)
    Call<ApiResponse> addIntroduction(@Header(AUTHORIZATION) String token,
                                      @Part("type") RequestBody type,
                                      @Part("description") RequestBody description,
                                      @Part MultipartBody.Part image);

    @DELETE(ApiEndPoint.DELETE_INTRODUCTION)
    Call<ApiResponse> deleteIntroduction(@Header(AUTHORIZATION) String token,
                                         @Query("uuid") String uuid);

    @Multipart
    @POST(ApiEndPoint.EDIT_INTRODUCTION)
    Call<ApiResponse> editIntroduction(@Header(AUTHORIZATION) String token,
                                       @Part("uuid") RequestBody uuid,
                                       @Part("description") RequestBody description,
                                       @Part MultipartBody.Part image);


    @GET(ApiEndPoint.GET_MESSAGES_USERS)
    Call<ApiResponse> getMessagesUsers(@Header(AUTHORIZATION) String token,
                                       @Query("type") String type, //user|driver
                                       @Query("name") String name);


    @GET(ApiEndPoint.GET_MESSAGES)
    Call<ApiResponse> getMessages(@Header(AUTHORIZATION) String token,
                                  @Query("user_uuid") String user_uuid //user|driver
    );


    @Multipart
    @POST(ApiEndPoint.SEND_MESSAGES)
    Call<ApiResponse> sendMessages(@Header(AUTHORIZATION) String token,
                                   @Part("user_uuid") RequestBody user_uuid,
                                   @Part("msg_type") RequestBody msg_type, //text|image
                                   @Part("body") RequestBody body,
                                   @Part MultipartBody.Part bodyFile
    );


    @GET(ApiEndPoint.ORDERS)
    Call<ApiResponse> getOrders(@Header(AUTHORIZATION) String token,
                                @Query("type") String type //for filter: [1=>current , 2=>in_progress ,3=>completed]
    );

    @GET(ApiEndPoint.ORDER_DETAILS)
    Call<ApiResponse> getOrderDetails(@Header(AUTHORIZATION) String token,
                                      @Query("uuid") String uuid);


    @FormUrlEncoded
    @POST(ApiEndPoint.ORDER_PAYMENT)
    Call<ApiResponse> orderPayment(@Header(AUTHORIZATION) String token,
                                   @Field("uuid") String uuid);

    @GET(ApiEndPoint.BALANCE)
    Call<ApiResponse> getBalance(@Header(AUTHORIZATION) String token);

    @GET(ApiEndPoint.MAX_DISTANCE_CARS)
    Call<ApiResponse> getMaxDistanceCars(@Header(AUTHORIZATION) String token);

    @FormUrlEncoded
    @PUT(ApiEndPoint.MAX_DISTANCE_CARS)
    Call<ApiResponse> updateMaxDistanceCars(@Header(AUTHORIZATION) String token,
                                           @Field("inside_max_km") String insideMaxKm);

}
