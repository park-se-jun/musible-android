package com.lacucaracha.musible.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName="sheet_table",indices = {@Index(value={"title"},unique = true)})
public final class MusicSheet {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="entryid")
    private final String  mId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    @NonNull
    @ColumnInfo(name = "midi")
    private final byte[] mMidi;

    //constructor
    @Ignore
    public MusicSheet(@NonNull String title, @NonNull byte[] midi) {
        this(UUID.randomUUID().toString(),title,midi);
    }

    public MusicSheet(@NonNull String mId, @NonNull String mTitle, @NonNull byte[] mMidi) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mMidi = mMidi;
    }

    //getter
    @NonNull
    public String getId() { return mId; }

    @NonNull
    public String getTitle() { return mTitle; }

    @NonNull
    public byte[] getMidi() { return mMidi; }
}
