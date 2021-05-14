 package com.lacucaracha.musible.sheetlist;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

 public class SheetListFragment extends Fragment {

    private SheetListViewModel mViewModel;
    public static SheetListFragment newInstance() {
        return new SheetListFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sheet_list_fragment, container, false);
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

        mViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(this.getActivity().getApplication()))
                .get(SheetListViewModel.class);
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
        Navigation.findNavController(v).navigate(R.id.action_sheetListFragment_to_imageListDialogFragment);
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