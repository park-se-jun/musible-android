package com.lacucaracha.musible.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity(tableName="sheet_table",indices = {@Index(value={"title"},unique = true)})
public final class MusicSheet {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    private final String  mId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    @NonNull
    @ColumnInfo(name = "midiPath")
    private final String mMidiPath;
    @NonNull
    @ColumnInfo(name = "mxlPath")
    private final String mMxlPath;
    //constructor
    @Ignore
    public MusicSheet(@NonNull String title, @NonNull String midiPath,@NonNull String mxlPath) {
        this(UUID.randomUUID().toString(),title,midiPath,mxlPath);
    }

    public MusicSheet(@NonNull String mId, @NonNull String mTitle, @NonNull String midiPath,@NonNull String mxlPath) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mMidiPath = midiPath;
        this.mMxlPath = mxlPath;
    }

    //getter
    @NonNull
    public String getId() { return mId; }

    @NonNull
    public String getTitle() { return mTitle; }

    @NonNull
    public String getMidiPath() { return mMidiPath; }
    @NonNull
    public String getMxlPath() { return mMxlPath;}

}
