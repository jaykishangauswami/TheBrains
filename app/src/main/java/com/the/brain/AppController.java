package com.the.brain;

import android.app.Application;

import com.the.brain.api.ApiClient;
import com.the.brain.api.ApiInterface;

/**
 * Created by HP on 25-Mar-18.
 */

public class AppController extends Application {

    private static AppController instance;
    private ApiInterface apiInterface;

    public static AppController getInstance() {
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
//        MultiDex.install(this);
        instance = this;
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }

}
