package com.lacucaracha.musible;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lacucaracha.musible.data.source.SheetRepository;
import com.lacucaracha.musible.data.source.remote.MyApi;
import com.lacucaracha.musible.sheetdetail.SheetDetailViewModel;
import com.lacucaracha.musible.sheetlist.SheetListViewModel;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile  ViewModelFactory INSTANCE;
    private final SheetRepository mSheetRepository;
    private  final SF2Soundbank mSf;
    public static ViewModelFactory getInstance(MyApplication application){
        if(INSTANCE==null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE==null){
                    INSTANCE = new ViewModelFactory(SheetRepository.getSheetRepository(application), application.getSf());
                }
            }
        }
        return INSTANCE;
    }

    public SheetRepository getSheetRepository(){return mSheetRepository;}

    public static void destroyInstance(){INSTANCE=null;}

    private ViewModelFactory(SheetRepository mSheetRepository,SF2Soundbank sf) {
        this.mSheetRepository = mSheetRepository;
        this.mSf = sf;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(SheetListViewModel.class)){
            return (T) new SheetListViewModel(mSheetRepository);
        }else if(modelClass.isAssignableFrom(SheetDetailViewModel.class)){
            return (T) new SheetDetailViewModel(mSheetRepository,mSf);
        }

        throw new IllegalArgumentException("Unknown ViewModel class:" + modelClass.getName());
    }

}
