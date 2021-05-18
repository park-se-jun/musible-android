package com.lacucaracha.musible.data.source.remote;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static RetrofitClient INSTANCE=null;
    private Retrofit mRetrofit;
    private MyApi mMyApi;
    private String mResponseBody;

    private RetrofitClient(){
        mRetrofit=new Retrofit.Builder()
                .baseUrl(MyApi.base_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mMyApi = mRetrofit.create(MyApi.class);
    }

    public static RetrofitClient getInstance(){
        if(INSTANCE==null){
            INSTANCE = new RetrofitClient();
        }
        return INSTANCE;
    }
    public String getTest(){
        callTestApi();
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();;
        }
        return mResponseBody;

    }
    private void callMakeMidiApi(Map<String, RequestBody> partMap, MultipartBody.Part file){
        Call<byte[]> call = mMyApi.MakeMidi(partMap,file);
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
        Call<String> call = mMyApi.getTest();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    mResponseBody= response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
