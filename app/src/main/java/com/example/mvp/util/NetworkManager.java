package com.example.mvp.util;

import com.example.mvp.BuildConfig;
import com.example.mvp.data.remote.randomapi.RandomAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    public NetworkManager() {
        // empty
        // Retrofit:
        // 1) Base url
        // 2) Converter Factory
        // 3) Http Client
        // 4) API Interface
    }

    private HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG) { // If we are building the debug version of the app
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return interceptor;
    }

    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(provideInterceptor()).build();
    }

    private GsonConverterFactory provideGsonFactory() {
        return GsonConverterFactory.create();
    }

    private Retrofit.Builder provideRetrofit() {
        return new Retrofit.Builder()
                .client(provideOkHttpClient())
                .addConverterFactory(provideGsonFactory());
    }

    public RandomAPI provideRandomAPI(String baseUrl) {
        return provideRetrofit().baseUrl(baseUrl).build().create(RandomAPI.class);
    }

}
