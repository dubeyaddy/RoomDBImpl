package com.practiceapp.roomdb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.practiceapp.roomdb.daoFile.WordDao;
import com.practiceapp.roomdb.database.WordRoomDatabase;
import com.practiceapp.roomdb.table.Word;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application){
        WordRoomDatabase db=WordRoomDatabase.getDatabase(application);
        mWordDao= db.wordDao();
        mAllWords= mWordDao.getAlphabetizedWords();
    }

    public LiveData<List<Word>> getAllWords(){return mAllWords;}

    public void insert(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            mWordDao.insert(word);
        });
    }

    public void update(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            mWordDao.update(word);
        });
    }

    public void delete(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            mWordDao.delete(word);
        });
    }

    public void deleteAll(){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            mWordDao.deleteAll();
        });
    }
}
