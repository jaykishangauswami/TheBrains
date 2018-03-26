package com.the.brain.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 25-Mar-18.
 */

public class ApiClient {

    private static final String BASE_URL = "http://admin.thebrainsolutions.in/api/";
    private static final String UPLOAD_URL = "http://cliniclink.in/phr/uploads/";
    private static Retrofit retrofit = null;

    public static String getUploadUrl() {
        return UPLOAD_URL;
    }

    public static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(240, TimeUnit.SECONDS)
            .connectTimeout(240, TimeUnit.SECONDS)
            .build();
    public static Retrofit getRetrofit() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        }
        return retrofit;
    }

}
