package com.lacucaracha.musible.sheetdetail;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lacucaracha.musible.R;
import com.lacucaracha.musible.sheetlist.SheetListViewModel;

public class SheetDetailFragment extends Fragment {

    private SheetDetailViewModel mViewModel;
    public static SheetDetailFragment newInstance() {
        return new SheetDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SheetDetailViewModel.class);
        return inflater.inflate(R.layout.sheet_detail_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SheetDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}