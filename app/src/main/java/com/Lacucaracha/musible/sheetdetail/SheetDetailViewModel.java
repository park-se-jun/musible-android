package com.lacucaracha.musible.sheetdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.SheetRepository;
import com.lacucaracha.musible.sheetlist.SheetFilterType;

import java.util.List;

public class SheetDetailViewModel extends ViewModel {
    public static String URL = "file:///android_asset/index.html";
    private final SheetRepository mRepository;
    private LiveData<MusicSheet> mMusicSheet;

    // TODO: Implement the ViewModel

    public SheetDetailViewModel(SheetRepository repository) {
        mRepository= repository;
    }


    public void start(String MusicSheetId){
        if(MusicSheetId!=null){
            mMusicSheet = mRepository.getMusicSheetWithId(MusicSheetId);
        }
    }
    public LiveData<MusicSheet> getMusicSheet(){
        return mMusicSheet;
    }
}