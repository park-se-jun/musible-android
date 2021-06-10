package com.lacucaracha.musible.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lacucaracha.musible.data.MusicSheet;

import java.util.List;

@Dao
public interface SheetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MusicSheet musicSheet);

    @Query("DELETE FROM sheet_table")
    void deleteAll();

    @Query("SELECT * FROM sheet_table WHERE id = :MusicSheetId")
    LiveData<MusicSheet> getMusicSheetWithId(String MusicSheetId);

    @Query("SELECT * FROM sheet_table ORDER BY title ASC")
    LiveData<List<MusicSheet>> getMusicSheetOrderByTitle();
}
