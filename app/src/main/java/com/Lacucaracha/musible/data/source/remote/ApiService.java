package com.lacucaracha.musible.data.source.remote;

import android.graphics.Bitmap;

import com.lacucaracha.musible.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ApiService {
    final String base_URL= "http://40.76.140.131:8080";
    @GET("/TEST")
    Call<ResponseBody>
    //    @Multipart
//    @POST("/upload")
//    Call<byte[]> imageToMidi(@Body List<Bitmap> braileSheetMusic) ;
}
