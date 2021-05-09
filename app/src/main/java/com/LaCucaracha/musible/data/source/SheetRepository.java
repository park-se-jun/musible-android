package com.lacucaracha.musible.data.source;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;

import java.util.List;

public class SheetRepository {
    private SheetDao mSheetDao;
    private LiveData<List<MusicSheet>> mAllSheets;
    public SheetRepository(Application application){
        SheetDatabase db = SheetDatabase.getDatabase(application);
        mSheetDao=db.sheetDao();
        mAllSheets=mSheetDao.getMusicSheetOrderByTitle();
    }

    LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }

    void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
}
