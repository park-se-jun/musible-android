package com.lacucaracha.musible.data.source.remote;

import android.util.Log;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

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
    public byte[] MakeMidi(List<MultipartBody.Part>file){
        callMakeMidiApi(file);
        try{
            Thread.sleep(4000);
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
    private void callMakeMidiApi( List<MultipartBody.Part> file){
        Call<byte[]> call = mMyApi.MakeMidi(file);
        call.enqueue(new Callback<byte[]>() {
            @Override
            public void onResponse(Call<byte[]> call, Response<byte[]> response) {

                response.body();
            }

            @Override
            public void onFailure(Call<byte[]> call, Throwable t) {

            }
        });
    }
    private void callTestApi(){
        Log.d("RetrofitClient","startCallTestApi");
        Call<String> call = mMyApi.getTest();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    mResponseBody= response.body();
                    Log.d("RetrofitClient","response is"+ (String)mResponseBody);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
