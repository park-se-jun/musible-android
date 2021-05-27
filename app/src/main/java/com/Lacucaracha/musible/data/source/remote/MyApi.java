package com.lacucaracha.musible.data.source.remote;

import android.graphics.Bitmap;

import com.lacucaracha.musible.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;

public interface MyApi {
    final String base_URL = "http://40.76.140.131:8080";

    @GET("test/download")
    Call<ResponseBody> getTest();

    @Multipart
    @Streaming
    @POST("test/request")
    Call<ResponseBody> MakeMidi(@Part ArrayList<MultipartBody.Part> file);
}
