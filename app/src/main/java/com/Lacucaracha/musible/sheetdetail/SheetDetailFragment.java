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
import com.lacucaracha.musible.ViewModelFactory;
import com.lacucaracha.musible.databinding.SheetDetailFragmentBinding;
import com.lacucaracha.musible.sheetlist.SheetListViewModel;

public class SheetDetailFragment extends Fragment {
    public static final String ARGUMENT_MUSICSHEET_ID= "MusicSheetId";
    private SheetDetailViewModel mViewModel;
    private SheetDetailFragmentBinding mbinding;
    public static SheetDetailFragment newInstance() {
        return new SheetDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mbinding = SheetDetailFragmentBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(this.getActivity().getApplication()))
                .get(SheetDetailViewModel.class);
        mbinding.setViewModel(mViewModel);
        return mbinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }
    @Override
    public void onResume() {
        super.onResume();
        String cachedId = getArguments().getString(ARGUMENT_MUSICSHEET_ID);
        mViewModel.start(cachedId);
    }
}