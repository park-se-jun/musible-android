package com.lacucaracha.musible.sheetdetail;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lacucaracha.musible.MyApplication;
import com.lacucaracha.musible.R;
import com.lacucaracha.musible.ViewModelFactory;
import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.databinding.SheetDetailFragmentBinding;
import com.lacucaracha.musible.sheetlist.SheetListViewModel;

import org.w3c.dom.Text;

import java.io.File;

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
                ViewModelFactory.getInstance((MyApplication) this.getActivity().getApplication()))
                .get(SheetDetailViewModel.class);


        mViewModel.start(getArguments().getString(ARGUMENT_MUSICSHEET_ID));

        mbinding.setWebViewModel(mViewModel);
        mbinding.setLifecycleOwner(this);
        setupObserve();
        setupFab();
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
//        String cachedId = getArguments().getString(ARGUMENT_MUSICSHEET_ID);
//        mViewModel.start(cachedId);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.destroyMidiProcessor();
    }

    private void setupFab(){
        FloatingActionButton fab = mbinding.playMidi;
        fab.setOnClickListener(v->{
            if(mViewModel.isPlaying()){
                mViewModel.stopMidi();
                fab.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            }else{
                mViewModel.playMidi();
                fab.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });

    }

    private void setupObserve(){
        TextView textViewPath = mbinding.midiPath;
        TextView textViewTitle = mbinding.title;
        mViewModel.getMusicSheet().observe(getViewLifecycleOwner(),new Observer<MusicSheet>(){
            @Override
            public void onChanged(MusicSheet musicSheet) {
                String midiPath =musicSheet.getMidiPath();
                String title = musicSheet.getTitle();
                try {
                    mViewModel.setmMidiProcessor(new File(midiPath));
                }catch (Exception e){
                    Toast.makeText(getContext(),"미디 플레이어를 올바르게 로드하지 못했습니다",Toast.LENGTH_SHORT);
                }
                textViewTitle.setText(title);
                textViewPath.setText(midiPath);

            }
        });
    }
}