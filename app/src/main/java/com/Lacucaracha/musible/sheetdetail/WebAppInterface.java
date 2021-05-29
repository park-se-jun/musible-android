package com.lacucaracha.musible.sheetdetail;

import android.webkit.JavascriptInterface;

import com.lacucaracha.musible.data.MusicSheet;

public class WebAppInterface {
    MusicSheet mSheet;

    WebAppInterface(MusicSheet m){
        mSheet = m;
    }

    @JavascriptInterface
    public String getPath(){
        String path = mSheet.getMidiPath();
        return path;
    }
}
