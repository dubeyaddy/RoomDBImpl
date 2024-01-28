package com.practiceapp.roomdb.daoFile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practiceapp.roomdb.table.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Update()
    void update(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Delete
    void delete(Word word);

    @Query("Select*from word_table ORDER BY WORD ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
























