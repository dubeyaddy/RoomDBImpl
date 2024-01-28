package com.practiceapp.roomdb.table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "word_table")
public class Word {


        @NonNull
        @PrimaryKey
        @ColumnInfo(name = "word")
//        private int wordId;
//        @NonNull
//        @ColumnInfo(name = "word")
        private String mWord;


        public Word(@NonNull String word){this.mWord=word;}
        public String getWord(){return this.mWord;}
//        public Word(@NonNull int wordId){this.wordId=wordId;}
//        public int getWordId(){return this.wordId;}
}
