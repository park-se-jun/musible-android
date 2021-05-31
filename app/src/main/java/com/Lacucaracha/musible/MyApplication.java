package com.lacucaracha.musible;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;

public class MyApplication extends Application {
    private SF2Soundbank sf ;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            sf = new SF2Soundbank(getAssets().open("test.sf2"));
            copyAssets();
        }catch (IOException e)
        {
            Toast.makeText(this,"sf파일을 찾을 수 없습니다.",Toast.LENGTH_SHORT);

        }

    }
    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(getFilesDir(), filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    public SF2Soundbank getSf(){return  sf;}
}
