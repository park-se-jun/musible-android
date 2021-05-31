package com.lacucaracha.musible.data.source;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.RequestImages;
import com.lacucaracha.musible.data.source.local.SheetDao;
import com.lacucaracha.musible.data.source.local.SheetDatabase;
import com.lacucaracha.musible.data.source.remote.MyApi;
import com.lacucaracha.musible.util.FileUtil;

import org.apache.commons.io.IOUtils;
import org.jfugue.MidiParser;
import org.jfugue.MusicStringParser;
import org.jfugue.MusicStringRenderer;
import org.jfugue.MusicXmlRenderer;
import org.jfugue.Pattern;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.kshoji.javax.sound.midi.MidiSystem;
import jp.kshoji.javax.sound.midi.Sequence;
import nu.xom.Serializer;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.MultipartBody;


public class SheetRepository {
    private static  SheetRepository INSTANCE;
    private SheetDao mSheetDao;
    private LiveData<List<MusicSheet>> mAllSheets;
    private LiveData<MusicSheet> mSheet;
    private Context mContext;
    private Retrofit mRetrofit;
    private MyApi mMyApi;
    private SheetRepository(Application application){
        SheetDatabase db = SheetDatabase.getDatabase(application);
        mContext=application.getApplicationContext();
        mSheetDao=db.sheetDao();
        mAllSheets=mSheetDao.getMusicSheetOrderByTitle();

        setupRetrofit();
    }
    public static SheetRepository getSheetRepository(Application application){
        if(INSTANCE == null){
            INSTANCE = new SheetRepository(application);
        }
        return INSTANCE;
    }

    public LiveData<List<MusicSheet>> getAllSheets(){ return mAllSheets; }

    private void insert(MusicSheet sheet){
        SheetDatabase.databaseWriteExecutor.execute(()->
        {
            mSheetDao.insert(sheet);
        });
    }
    public LiveData<MusicSheet> getMusicSheetWithId(String musicSheetId){
        mSheet = mSheetDao.getMusicSheetWithId(musicSheetId);
        return mSheet;
    }
    private void setupRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        mRetrofit=new Retrofit.Builder()
                .baseUrl(MyApi.base_URL)
                .client(client)
                .build();
        mMyApi = mRetrofit.create(MyApi.class);
    }

//    public void getTest(){
//        mRetrofitClient.test();
//    }
    public void makeMusicSheet(List<Uri> uriList) {
        List<MultipartBody.Part> requestBody = RequestImages.create(uriList);
        mMyApi.MakeMidi((ArrayList)requestBody).enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean writeToDisk =writeResponseBodyToDisk(response.body());
                if(writeToDisk){
                    Toast.makeText(mContext,"변환에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(mContext,"변환에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext,"변환에 실패했습니다.",Toast.LENGTH_SHORT).show();
            }
        });
//        insert(sheet);
    }
    private boolean writeResponseBodyToDisk(ResponseBody body){
        String filename = FileUtil.dateName(System.currentTimeMillis());
        File midiFile = new File(mContext.getFilesDir(),filename+".midi");
        File musicXMLFile;
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(midiFile);
            IOUtils.write(body.bytes(),fileOutputStream);
            musicXMLFile = writeMidiToMusicXML(midiFile,filename);

            if(musicXMLFile == null) throw new Exception("invalid musicXML");
            MusicSheet musicSheet = new MusicSheet(
                    filename,
                    midiFile.getCanonicalPath(),
                    musicXMLFile.getCanonicalPath());
            insert(musicSheet);
            return true;
        }catch(Exception e){
            midiFile.delete();
            return false;
        }finally {
        }
    }
    private File writeMidiToMusicXML(File midiFile, String filename){
        File musicXMLFile = new File(mContext.getFilesDir(),filename + ".musicxml");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(musicXMLFile);
            MidiParser parser = new MidiParser();
            MusicStringParser stringParser = new MusicStringParser();
            MusicStringRenderer renderer = new MusicStringRenderer();
            MusicXmlRenderer listener = new MusicXmlRenderer();
            parser.addParserListener(renderer);
            stringParser.addParserListener(listener);
            Sequence sequence = MidiSystem.getSequence(midiFile);
            parser.parse(MidiSystem.getSequence(midiFile));
            Pattern pattern = renderer.getPattern();
            stringParser.parse(pattern);
            Serializer serializer = new Serializer(fileOutputStream, "UTF-8");
            serializer.setIndent(4);
            serializer.write(listener.getMusicXMLDoc());
            fileOutputStream.flush();
            fileOutputStream.close();
            return musicXMLFile;
        }catch(Exception e){
            musicXMLFile.delete();
            return null;
        }finally{
        }





    }
}
