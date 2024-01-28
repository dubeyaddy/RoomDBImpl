package com.practiceapp.roomdb.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.practiceapp.roomdb.R;

public class SharedPreferenceMsg extends AppCompatActivity {
    TextView tv_SharedPrefMSg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_msg);
        tv_SharedPrefMSg= findViewById(R.id.tv_sharedPrefMsg);

        SharedPreferences getShared= getSharedPreferences("demo",MODE_PRIVATE);
        String value= getShared.getString("str","Shared Preference");
        tv_SharedPrefMSg.setText(value);
    }
}