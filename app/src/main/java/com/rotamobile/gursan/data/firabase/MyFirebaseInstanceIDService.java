package com.rotamobile.gursan.data.firabase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rotamobile.gursan.data.retrofitservice.APIClient;
import com.rotamobile.gursan.data.retrofitservice.APIService;
import com.rotamobile.gursan.model.deviceId.ModelDeviceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private APIService apiService;

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    // token'ı servise gönderme işlemlerini bu methodda yapmalısınız

    //Request Service throught Retrofit Rest
        apiService = APIClient.getClient().create(APIService.class);

        final Call<ModelDeviceId> call3 = apiService.at(23,token);
        call3.enqueue(new Callback<ModelDeviceId>() {
            @Override
            public void onResponse(Call<ModelDeviceId> call, Response<ModelDeviceId> response) {

                Log.i("response","" + response.body());
                ModelDeviceId modelDeviceId = response.body();
                String get_mesaj = modelDeviceId.getSuccessful();
                Log.i("mesaj",get_mesaj);
            }

            @Override
            public void onFailure(Call<ModelDeviceId> call, Throwable t) {
                call3.cancel();
            }
        });
    }

}