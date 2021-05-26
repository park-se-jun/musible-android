package com.lacucaracha.musible.data.source;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.RequestImages;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;
import com.lacucaracha.musible.data.source.remote.MyApi;
import com.lacucaracha.musible.data.source.remote.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SheetRepository {
    private SheetDao mSheetDao;
    private Retrofit mRetrofit;
    private MyApi mMyApi;
    private RetrofitClient mRetrofitClient;
    private LiveData<List<MusicSheet>> mAllSheets;
    public SheetRepository(Application application){
        SheetDatabase db = SheetDatabase.getDatabase(application);
        mSheetDao=db.sheetDao();
        mAllSheets=mSheetDao.getMusicSheetOrderByTitle();
        mRetrofitClient=RetrofitClient.getInstance();
    }

    public LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }

    void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
    public LiveData<MusicSheet> getMusicSheetWithId(String musicSheetId){
        return mSheetDao.getMusicSheetWithId(musicSheetId);
    }
    public void getTest(){
        mRetrofitClient.getTest();
    }
    public void makeMusicSheet(List<Uri> uriList) {
        List<MultipartBody.Part> requestBody = RequestImages.create(uriList);
        /*
        *
        * midi를 얻는 코드
        *
        */
        byte[] midi = new byte[1];
        MusicSheet sheet = new MusicSheet(midi);
//        insert(sheet);
    }
}
