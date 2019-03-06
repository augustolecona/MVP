package com.example.mvp.data.remote.randomapi;

import com.example.mvp.data.remote.randomapi.to.ApiResponse;
import com.example.mvp.data.remote.randomapi.to.Result;
import com.example.mvp.util.NetworkError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomAPIService {

    private final RandomAPI randomAPI;

    public RandomAPIService(RandomAPI randomAPI) {
        this.randomAPI = randomAPI;
    }

    public void fetchRandomUsers(int count, final RandomUsersCallback callback) {
        randomAPI.getRandomUsers(count).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()) {
                    ApiResponse apiResult = response.body();
                    if (apiResult != null) {
                        callback.OnUsersLoaded(apiResult.getResults());
                    } else {
                        callback.OnAPIError(NetworkError.NO_DATA);
                    }

                } else {
                    switch (response.code()) {
                        case 400:
                            callback.OnAPIError(NetworkError.BAD_REQUEST);
                            break;
                        case 401:
                            callback.OnAPIError(NetworkError.UNAUTHORIZED);
                            break;
                        case 403:
                            callback.OnAPIError(NetworkError.FOBIDDEN);
                            break;
                        case 404:
                            callback.OnAPIError(NetworkError.NOT_FOUND);
                            break;
                        case 500:
                            callback.OnAPIError(NetworkError.SERVER);
                            break;
                    }
                    callback.OnAPIError(NetworkError.BAD_REQUEST);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // log to product crash reporting analytics
                callback.OnAPIError(NetworkError.INTERNET);
                t.printStackTrace();
            }

        });
    }

    public interface RandomUsersCallback {
        void OnUsersLoaded(List<Result> results);
        void OnAPIError(NetworkError error);
    }
}
