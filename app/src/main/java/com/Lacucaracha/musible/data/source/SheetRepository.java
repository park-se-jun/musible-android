package com.lacucaracha.musible.data.source;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;
import com.lacucaracha.musible.data.source.remote.MyApi;
import com.lacucaracha.musible.data.source.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
    LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }
    void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
    public void getTest(){
        mRetrofitClient.getTest();
    }
//    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri){
//        File file = FileUtil.getPath(fileUri,this);
//        //requestBody 생성
//        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
//        return MultipartBody.Part.createFormData(partName,file.getName(),requestFile);
//    }
}
