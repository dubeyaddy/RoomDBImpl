package com.practiceapp.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practiceapp.roomdb.sharedpreference.SharedPreferenceMsg;

import java.util.HashMap;

public class NewWordActivity extends AppCompatActivity {

    public static final String Extra_reply="demo";

    private EditText mEditWordView;
    FirebaseDatabase db;
    DatabaseReference myRef= db.getReference("advance-engine-333703-default-rtdb");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView=findViewById(R.id.edit_word);

        final Button button=findViewById(R.id.button_save);
        button.setOnClickListener(view->{

            String msg= mEditWordView.getText().toString().trim();
            SharedPreferences shrd = getSharedPreferences("demo",MODE_PRIVATE);
            SharedPreferences.Editor editor= shrd.edit();

            editor.putString("str",msg);
            editor.apply();
            Intent pref_intent= new Intent(this, SharedPreferenceMsg.class);
            pref_intent.putExtra("message_pref",msg);
            startActivity(pref_intent);


//            Intent replyIntent= new Intent();
//            String word= mEditWordView.getText().toString();
//            if (word.isEmpty()){
//                Toast.makeText(NewWordActivity.this,"Word field is empty",Toast.LENGTH_SHORT).show();
//            }else {
//                FirebaseDatabase.getInstance().getReference().child("Words").child("word").setValue(mEditWordView);
//                replyIntent.putExtra(Extra_reply,word);
//                setResult(RESULT_OK,replyIntent);
//            }
//            finish();

            Intent replyIntent= new Intent();
            if(TextUtils.isEmpty(mEditWordView.getText())){
                setResult(RESULT_CANCELED,replyIntent);
            }else {
//////                HashMap<String,Object> mHashMap= new HashMap<>();
//////                mHashMap.put("Word:",mEditWordView);
////
////                db.getInstance().getReference().child("Words").push().setValue(mEditWordView).toString();
////                myRef.child("words").push().updateChildren(mHashMap);
                String word= mEditWordView.getText().toString();
                replyIntent.putExtra(Extra_reply,msg);
                setResult(RESULT_OK,replyIntent);
            }
            finish();
        });
    }
}