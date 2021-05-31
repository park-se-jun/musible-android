package com.lacucaracha.musible.sheetdetail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.webkit.WebViewAssetLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
//        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
//                .addPathHandler("/assets/",new WebViewAssetLoader.AssetsPathHandler(getContext()))
//                .addPathHandler(getContext().getFilesDir().getPath(),new WebViewAssetLoader.InternalStoragePathHandler(getContext(),getContext().getFilesDir()))
//                .build();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        webView.loadUrl(mViewModel.URL);
//        webView.setWebViewClient(new WebViewClient(){
//
//            @Nullable
//            @Override
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                return assetLoader.shouldInterceptRequest(request.getUrl());
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
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