 package com.lacucaracha.musible.sheetlist;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lacucaracha.musible.R;
import com.lacucaracha.musible.ViewModelFactory;
import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.databinding.SheetListFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

 public class SheetListFragment extends Fragment {
    private SheetListViewModel mViewModel;
    private SheetListFragmentBinding binding;
    private RecyclerAdapter mRecyclerAdepter;
    public static SheetListFragment newInstance() {
        return new SheetListFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = SheetListFragmentBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(this.getActivity().getApplication()))
                .get(SheetListViewModel.class);
        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(getActivity());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.sheetlist_fragment_menu,menu);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.menu_filter:
                 showFilteringPopUpMenu();
                 break;
         }
         return true;
     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFab();
        setupListAdapter();
        mViewModel.getItems().observe(getViewLifecycleOwner(),items->{

        });
        // TODO: Use the ViewModel
    }
    private void setupListAdapter(){
        RecyclerView recyclerView= binding.sheetList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerAdepter = new RecyclerAdapter();
        recyclerView.setAdapter(mRecyclerAdepter);

    }
    private void setupFab(){
        FloatingActionButton fab= getActivity().findViewById(R.id.add_image_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpSelectImageDialog();
            }
        });
    }

    private void popUpSelectImageDialog(){
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("선택")
                .setEmptySelectionText("선택된 사진이 없습니다.미디파일로 바꿀 사진을 순서대로 선택해 주세요")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        // here is selected image uri list
//                        mViewModel.makeMusicSheet(uriList);
                        mViewModel.getTest();
                    }
                });
    }

    private void showFilteringPopUpMenu(){
        PopupMenu popup = new PopupMenu(getContext(),getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_sheet,popup.getMenu());
        popup.setOnMenuItemClickListener((item ->
        {
            switch (item.getItemId()){
                case R.id.linear:
                    break;
                case R.id.grid:
                    break;
            }
            return true;
        }));
        popup.show();
    }

}