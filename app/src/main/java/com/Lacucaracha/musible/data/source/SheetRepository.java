package com.lacucaracha.musible.data.source;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.RequestImages;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;
import com.lacucaracha.musible.data.source.remote.MyApi;
import com.lacucaracha.musible.util.FileUtil;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.MultipartBody;


public class SheetRepository {
    private static  SheetRepository INSTANCE;
    private SheetDao mSheetDao;
    private LiveData<List<MusicSheet>> mAllSheets;
    private Context mContext;
    private Retrofit mRetrofit;
    private MyApi mMyApi;
    private SheetRepository(Application application){
        SheetDatabase db = SheetDatabase.getDatabase(application);
        mContext=application.getApplicationContext();
        mSheetDao=db.sheetDao();
        mAllSheets=mSheetDao.getMusicSheetOrderByTitle();

        setupRetrofit();
    }
    public static SheetRepository getSheetRepository(Application application){
        if(INSTANCE == null){
            INSTANCE = new SheetRepository(application);
        }
        return INSTANCE;
    }

    public LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }

    private void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
    public LiveData<MusicSheet> getMusicSheetWithId(String musicSheetId){
        return mSheetDao.getMusicSheetWithId(musicSheetId);
    }
    private void setupRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        mRetrofit=new Retrofit.Builder()
                .baseUrl(MyApi.base_URL)
                .client(client)
                .build();
        mMyApi = mRetrofit.create(MyApi.class);
    }

//    public void getTest(){
//        mRetrofitClient.test();
//    }
    public void makeMusicSheet(List<Uri> uriList) {
        List<MultipartBody.Part> requestBody = RequestImages.create(uriList);
        mMyApi.MakeMidi((ArrayList)requestBody).enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean writeToDisk =writeResponseBodyToDisk(response.body());
                if(writeToDisk){
                    Toast.makeText(mContext,"변환에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(mContext,"변환에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext,"변환에 실패했습니다.",Toast.LENGTH_SHORT).show();
            }
        });
//        insert(sheet);
    }
    private boolean writeResponseBodyToDisk(ResponseBody body){
        String filename = FileUtil.dateName(System.currentTimeMillis());
        File file = new File(mContext.getFilesDir(),filename+".midi");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.write(body.bytes(),fileOutputStream);
            MusicSheet musicSheet = new MusicSheet(file.getName(),file.getCanonicalPath());
            insert(musicSheet);
            return true;
        }catch(Exception e){
            file.delete();
            return false;
        }finally {
        }
    }
}
