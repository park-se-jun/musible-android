package com.lacucaracha.musible.data.source.remote;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static RetrofitClient INSTANCE=null;
    private Retrofit mRetrofit;
    private MyApi mMyApi;
    private Object mResponseBody;

    private RetrofitClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        mRetrofit=new Retrofit.Builder()
                .baseUrl(MyApi.base_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        mMyApi = mRetrofit.create(MyApi.class);
    }

    public static RetrofitClient getInstance(){
        if(INSTANCE==null){
            INSTANCE = new RetrofitClient();
        }
        return INSTANCE;
    }
    public byte[] MakeMidi(ArrayList<MultipartBody.Part>file){
        callMakeMidiApi(file);
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();;
        }
        return (byte[])mResponseBody;
    }
    public String getTest(){
        callTestApi();
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();;
        }
        return (String)mResponseBody;

    }
    private void callMakeMidiApi( ArrayList<MultipartBody.Part> file){
        Call<ResponseBody> call = mMyApi.MakeMidi(file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                response.body();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void callTestApi(){
        Log.d("RetrofitClient","startCallTestApi");
        Call<ResponseBody> call = mMyApi.getTest();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                InputStream is = response.body().byteStream();
//                BufferedReader  reader = new BufferedReader(new InputStreamReader(is));
//                String result;
//                String line = reader.readLine();
//                result=line;
//                while((line = reader.readLine())!=null)\
                Handler handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg){

                    }
                };
                try {
                    response.body().bytes();
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
