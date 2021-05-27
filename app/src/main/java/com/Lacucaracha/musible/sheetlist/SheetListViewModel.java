package com.lacucaracha.musible.sheetlist;

import android.media.Image;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lacucaracha.musible.Event;
import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.SheetRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SheetListViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final SheetRepository mRepository;

    private List<MultipartBody.Part> mMultiPartBody;

    private final LiveData<List<MusicSheet>> mItems;

    private SheetFilterType mCurrentFilter=SheetFilterType.LIST;

    private final MutableLiveData <Event<String>> mOpenMusicSheetEvent = new MutableLiveData<>();

    private List<Uri> ImageUriList;

    public SheetListViewModel(SheetRepository repository) {
        this.mRepository = repository;
        //set initial state
        setFilter(SheetFilterType.LIST);
        mItems = mRepository.getAllSheets();
    }
    //
    //set Filter
//    public void getTest(){
//        mRepository.getTest();
//    }
    public void setFilter(SheetFilterType requestType) {
        this.mCurrentFilter = mCurrentFilter;
        switch(requestType){
            case GRID:
                break;
            case LIST:
                break;
        }
    }
    public LiveData<Event<String>>getOpenMusicSheetEvent(){return mOpenMusicSheetEvent;}
    void openMusicSheet(String MusicSheetID){
        mOpenMusicSheetEvent.setValue(new Event<>(MusicSheetID));
    }
    public LiveData<List<MusicSheet>> getItems() {
        return mItems;
    }

    public void setSelectedImageUri(List<Uri> uriList) {
        this.ImageUriList = uriList;
    }
    private void flushSelectedImageUri(){
        this.ImageUriList = null;
    }

    public void makeMusicSheet(List<Uri> uriList){
        mRepository.makeMusicSheet(uriList);
    }

}