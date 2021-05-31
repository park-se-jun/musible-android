package com.lacucaracha.musible.sheetdetail;

import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.lacucaracha.musible.data.MusicSheet;

public class WebAppInterface {
    MusicSheet mSheet;

    WebAppInterface(MusicSheet m){
        mSheet = m;
    }

    @JavascriptInterface
    public String getMidiPath(){
        String midiPath = mSheet.getMidiPath();
        return midiPath;
    }
    @JavascriptInterface
    public String getMxlPath(){
        String mxlPath = mSheet.getMxlPath();
        return  mxlPath;
    }
    @JavascriptInterface
    public String getExternalStorage(){
        String ext = Environment.getExternalStorageState();
        String sdPath;
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        }else {
            sdPath = Environment.MEDIA_UNMOUNTED;
        }
        return sdPath;
    }
}
