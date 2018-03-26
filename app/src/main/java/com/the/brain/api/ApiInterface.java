package com.the.brain.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by HP on 25-Mar-18.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> postLogin(@Field("mobile")String mobile,@Field("password")String password);

    @Multipart
    @POST("fileupload")
    Call<ResponseBody> uploadSurvey(@Part MultipartBody.Part[] surveyImage);

}
