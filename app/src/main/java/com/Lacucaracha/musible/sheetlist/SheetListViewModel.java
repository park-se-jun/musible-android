package com.lacucaracha.musible.sheetlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.SheetRepository;

import java.util.List;

public class SheetListViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final SheetRepository mRepository;

    private final MutableLiveData<List<MusicSheet>> mItems = new MutableLiveData<>();
    private SheetFilterType mCurrentFilter=SheetFilterType.LIST;


    public SheetListViewModel(SheetRepository repository) {
        this.mRepository = repository;
        //set initial state
        setFilter(SheetFilterType.LIST);
    }
    //
    //set Filter
    public void getTest(){
        mRepository.getTest();
    }
    public void setFilter(SheetFilterType requestType) {
        this.mCurrentFilter = mCurrentFilter;
        switch(requestType){
            case GRID:
                break;
            case LIST:
                break;
        }
    }
}