package com.lacucaracha.musible.data.source;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;
import com.lacucaracha.musible.data.source.remote.ApiService;

import java.util.List;

public class SheetRepository {
    private SheetDao mSheetDao;
    private ApiService mApiService;
    private LiveData<List<MusicSheet>> mAllSheets;
    public SheetRepository(Application application){
        SheetDatabase db = SheetDatabase.getDatabase(application);
        mSheetDao=db.sheetDao();
        mAllSheets=mSheetDao.getMusicSheetOrderByTitle();
    }

    LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }
    byte[] imageToMidi(List<Bitmap> braileSheetMusic){
        byte[] resultMidi;
        resultMidi = mApiService.imageToMidi(braileSheetMusic);
        return resultMidi;
    }
    void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
}
