package com.rotamobile.gursan.data.retrofitservice;

import com.rotamobile.gursan.model.deviceId.ModelDeviceId;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("/api/UserService/UserDeviceUpdate")
    Call<ModelDeviceId> at(@Field("UserID") Integer userID,
                           @Field("DeviceID") String deviceID);

}
