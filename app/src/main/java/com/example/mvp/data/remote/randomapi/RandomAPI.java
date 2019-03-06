package com.example.mvp.data.remote.randomapi;

import com.example.mvp.data.remote.randomapi.to.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomAPI {

    String BASE_URL = "https://randomuser.me/";

    @GET("api")
    Call<ApiResponse> getRandomUsers(@Query("results") int results);

}
