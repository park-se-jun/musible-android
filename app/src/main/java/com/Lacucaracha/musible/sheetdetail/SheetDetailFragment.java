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

import com.lacucaracha.musible.R;
import com.lacucaracha.musible.ViewModelFactory;
import com.lacucaracha.musible.data.MusicSheet;
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


        mViewModel.start(getArguments().getString(ARGUMENT_MUSICSHEET_ID));

        mbinding.setWebViewModel(mViewModel);
        mbinding.setLifecycleOwner(this);
        setupWebView();
        setupObserve();
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
    private void setupWebView(){
        WebView webView =mbinding.detailSheetMusic;
        WebSettings settings =webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.getAllowUniversalAccessFromFileURLs();
        webView.loadUrl(mViewModel.URL);
//        webView.addJavascriptInterface(
//                new WebAppInterface( mViewModel.getMusicSheet().getValue() )
//                ,"Android");

    }
    private void setupObserve(){
        WebView webView = mbinding.detailSheetMusic;
        mViewModel.getMusicSheet().observe(getViewLifecycleOwner(),new Observer<MusicSheet>(){
            @Override
            public void onChanged(MusicSheet musicSheet) {
                webView.removeJavascriptInterface("Android");
                webView.addJavascriptInterface(new WebAppInterface(musicSheet),"Android");
            }
        });
    }
}