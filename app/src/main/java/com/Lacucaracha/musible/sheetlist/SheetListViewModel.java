package com.lacucaracha.musible.sheetlist;

import android.media.Image;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private final MutableLiveData<List<MusicSheet>> mItems = new MutableLiveData<>();
    private SheetFilterType mCurrentFilter=SheetFilterType.LIST;
    private List<Uri> ImageUriList;

    public SheetListViewModel(SheetRepository repository) {
        this.mRepository = repository;
        //set initial state
        setFilter(SheetFilterType.LIST);
    }
    //
    //set Filter

    public void setFilter(SheetFilterType requestType) {
        this.mCurrentFilter = mCurrentFilter;
        switch(requestType){
            case GRID:
                break;
            case LIST:
                break;
        }
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