package com.practiceapp.roomdb.firebaseActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practiceapp.roomdb.Post;
import com.practiceapp.roomdb.R;
import com.practiceapp.roomdb.daoFile.WordDao;
import com.practiceapp.roomdb.sharedpreference.SharedPreferenceMsg;
import com.practiceapp.roomdb.table.Word;
import com.practiceapp.roomdb.viewmodel.WordViewModel;

import java.util.HashMap;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity {
    private EditText mEditWord;
    private Button btn_save;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DataSnapshot snapshot;
    private static final String TAG= "GoogleActivity";
    private String wordId,word;
    public static final String Extra_reply="demo";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordViewModel mWordViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

//        WordDao wordDao=new WordDao();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mEditWord=findViewById(R.id.edit_word);
        btn_save=findViewById(R.id.button_save);

        mFirebaseInstance= FirebaseDatabase.getInstance();
        mFirebaseDatabase= mFirebaseInstance.getReference("New word");

        mFirebaseInstance.getReference().child("New word").setValue("test");

        btn_save.setOnClickListener(new View.OnClickListener() {

            Word word=new Word(mEditWord.getText().toString());
            @Override
            public void onClick(View v) {
                Intent replyIntent= new Intent();
                String word = mEditWord.getText().toString();
                    setResult(RESULT_CANCELED,replyIntent);
                    writeNewPost(word);
//                    mFirebaseInstance.getReference().child("New word").setValue(word);

                String msg= mEditWord.getText().toString().trim();
                SharedPreferences shrd = getSharedPreferences("demo",MODE_PRIVATE);
                SharedPreferences.Editor editor= shrd.edit();

                editor.putString("str",msg);
                editor.commit();
                Intent pref_intent= new Intent(FirebaseActivity.this, SharedPreferenceMsg.class);
                pref_intent.putExtra("message_pref",msg);
                startActivity(pref_intent);
                }
            }
        );
    }
    private void writeNewPost(String word) {

        String key = mFirebaseDatabase.push().getKey();
        Post post = new Post(word);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/New word/" + key, postValues);
//        childUpdates.put("/user-posts/" + word + "/" + key, postValues);

        mFirebaseDatabase.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"new database on success");
                        Toast.makeText(FirebaseActivity.this,"New database on success",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"new database on failure");
                        Toast.makeText(FirebaseActivity.this,"New database on failure",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void toggleButton() {
        if (TextUtils.isEmpty(wordId)){
            btn_save.setText("Save");
        } else {
            btn_save.setText("Update");
        }
    }

//    public void createWord(String wordref){
////        if (TextUtils.isEmpty(wordId)){
////            wordId=mFirebaseDatabase.push().getKey();
////            addWordChangeListener();
////        }
//        Word word = new Word(wordref);
//
//        mFirebaseDatabase.child("New word").setValue(word).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Log.e(TAG,"On complete: "+task.isSuccessful());
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG,"On Failure: "+e.getMessage());
//            }
//        });
//    }
//
//    private void addWordChangeListener(){
////
//        ValueEventListener wordListener=new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Word wordref= snapshot.getValue(Word.class);
//
//                if (wordref==null){
//                    Log.e(TAG,"Word is blank");
//                    Toast.makeText(getApplicationContext(),"Word is Blank",Toast.LENGTH_SHORT).show();
//                }else {
//                    Log.e(TAG, "User data is changed" + wordref);
//                    Toast.makeText(getApplicationContext(), "User data is changed" + wordref, Toast.LENGTH_SHORT).show();
//                    toggleButton();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(TAG,"Failed to read word", error.toException());
//                Toast.makeText(getApplicationContext(),"Failed to read word",Toast.LENGTH_SHORT);
//
//            }
//        };
//        mFirebaseDatabase.addValueEventListener(wordListener);
//
//    }
//
//    private void updateWord(String word){
//        if (!TextUtils.isEmpty(wordId))
//            mFirebaseDatabase.child("Word").setValue(word).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Log.e(TAG,"update"+task.isSuccessful());
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e(TAG,"Failure: "+e.getMessage());
//                }
//            }).addOnCanceledListener(new OnCanceledListener() {
//                @Override
//                public void onCanceled() {
//                    Log.e(TAG,"Cancelled: ");
//                }
//            });
//    }


}
