 package com.lacucaracha.musible.sheetlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lacucaracha.musible.R;

public class SheetListFragment extends Fragment {

    private SheetListViewModel mViewModel;

    public static SheetListFragment newInstance() {
        return new SheetListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SheetListViewModel.class);
        setupFab();
        // TODO: Use the ViewModel
    }
    private void setupFab(){
        FloatingActionButton fab= getActivity().findViewById(R.id.add_image_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddImage(v);
            }
        });
    }
    private void navigateToAddImage(View v){
        Navigation.findNavController(v).navigate(R.id.action_sheetListFragment_to_sheetDetailFragment);
    }
}