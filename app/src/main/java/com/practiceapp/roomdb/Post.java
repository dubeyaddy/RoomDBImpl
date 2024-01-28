package com.practiceapp.roomdb;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    public String word;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post(String word) {
        super();
        this.word=word;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("word",word);

        return result;
    }
}
