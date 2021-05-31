package com.lacucaracha.musible.data;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public  class RequestImages {
    private RequestImages(){}
    public static List<MultipartBody.Part> create(List<Uri> uriList) {
        return  makeMultiPartBodyList(uriList);
    }
    private static ArrayList<MultipartBody.Part> makeMultiPartBodyList(List<Uri> uriList){
        ArrayList<MultipartBody.Part> multiPartImages = new ArrayList<>();
        for(Uri uri :uriList){
            File file = new File(uri.getPath());
            RequestBody requestBody= RequestBody.create(MediaType.parse("Image/*"),file);
            MultipartBody.Part filePart = MultipartBody.Part
                    .createFormData("files",file.getName(),requestBody);
            multiPartImages.add(filePart);
        }
        return multiPartImages;
    }
}
