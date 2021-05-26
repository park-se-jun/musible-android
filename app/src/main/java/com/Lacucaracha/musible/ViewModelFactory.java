package com.lacucaracha.musible;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lacucaracha.musible.data.source.SheetRepository;
import com.lacucaracha.musible.sheetdetail.SheetDetailViewModel;
import com.lacucaracha.musible.sheetlist.SheetListViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile  ViewModelFactory INSTANCE;
    private final SheetRepository mSheetRepository;
    public static ViewModelFactory getInstance(Application application){
        if(INSTANCE==null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE==null){
                    INSTANCE = new ViewModelFactory(
                            new SheetRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    public SheetRepository getSheetRepository(){return mSheetRepository;}

    public static void destroyInstance(){INSTANCE=null;}

    private ViewModelFactory(SheetRepository mSheetRepository) {
        this.mSheetRepository = mSheetRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(SheetListViewModel.class)){
            return (T) new SheetListViewModel(mSheetRepository);
        }else if(modelClass.isAssignableFrom(SheetDetailViewModel.class)){
            return (T) new SheetDetailViewModel(mSheetRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class:" + modelClass.getName());
    }

}
