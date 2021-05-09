package com.lacucaracha.musible.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lacucaracha.musible.data.MusicSheet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MusicSheet.class}, version = 1)
public abstract class SheetDatabase extends RoomDatabase {

    public abstract SheetDao sheetDao();

    //싱글톤 패턴을 위한 것
    private static SheetDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SheetDatabase getDatabase(final Context context)  {
        if(INSTANCE==null) {
            synchronized (SheetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SheetDatabase.class, "sheet.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
