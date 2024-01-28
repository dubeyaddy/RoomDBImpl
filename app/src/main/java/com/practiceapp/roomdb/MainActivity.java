package com.practiceapp.roomdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.practiceapp.roomdb.deligate.DeleteWordListener;
import com.practiceapp.roomdb.firebaseActivity.FirebaseActivity;
import com.practiceapp.roomdb.firebasePushNotification.PushNotification;
import com.practiceapp.roomdb.signingIn.SignInActivity;
import com.practiceapp.roomdb.table.Word;
import com.practiceapp.roomdb.viewHolder.WordListAdapter;
import com.practiceapp.roomdb.viewmodel.WordViewModel;

import java.net.HttpCookie;

public class MainActivity extends AppCompatActivity {
    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public WordListAdapter adapter;
    private static final String TAG= "GoogleActivity";
    private static final int RC_SIGN_IN=9001;
    private DataSnapshot dataSnapshot;
    private DatabaseError error;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference("message");
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        adapter= new WordListAdapter(new WordListAdapter.WordDiff(), new DeleteWordListener() {
            @Override
            public void onDeleteWord(Word word) {
                mWordViewModel.delete(word);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel=new ViewModelProvider(this).get(WordViewModel.class);

        mWordViewModel.getAllWords().observe(this,words -> {
            adapter.submitList(words);
        });

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(view->{
            Intent intent= new Intent(MainActivity.this, FirebaseActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        FirebaseMessaging.getInstance().subscribeToTopic("sample")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg= "Message Recieved succesfully";
                        if (!task.isSuccessful()){
                            msg="Failed to recieve";
                        }
                    }
                });

        SharedPreferences getShared= getSharedPreferences("demo",MODE_PRIVATE);
        String value= getShared.getString("str","Saved notes in Roomdb");


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser=mAuth.getCurrentUser();
//
//        if (currentUser==null){
//            Log.d(TAG,"Currently Signed in:"+currentUser.getEmail());
//            Toast.makeText(MainActivity.this,"Currently Looged in: "+currentUser.getEmail(),Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
//            startActivity(intent);
//        }
//    }



    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        SignInActivity firebaseAuthWithGoogle=new SignInActivity();

//        if(requestCode==NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
//            Word word= new Word(data.getStringExtra(FirebaseActivity.Extra_reply));
//            mWordViewModel.insert(word);
//        }else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }

        if (requestCode==RC_SIGN_IN && resultCode==RESULT_OK){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(),"Google Sign In Succeeded",Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle.firebaseAuthWithGoogle(account);
                adapter.notifyDataSetChanged();

            } catch (ApiException e){
                Log.w(TAG,"Google Sign In failed",e);
                Toast.makeText(this,"Google Sign In failed",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wordmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.deleteAll){
            mWordViewModel.deleteAll();
            Toast.makeText(MainActivity.this,"Delete All selected",Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.test1)
        {
            Toast.makeText(MainActivity.this,"SignIn selected",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
            item.setTitle("Sign out");
        }else if(item.getItemId()==R.id.test2)
        {
            Toast.makeText(MainActivity.this,"PushNotification selected",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(MainActivity.this, PushNotification.class);
            startActivity(intent);
            item.setTitle("push notification");
        }
        return super.onOptionsItemSelected(item);
    }

//    myRef.addValueEventLisetener(new void ValueEventListener(){
//
//    });
//
//    @Override
//    public void onDataChange(DataSnapshot datasnapshot){
//        String value= dataSnapshot.getValue(word);
//        Log.d(TAG,"Value is:"+value);
//    }
//
//    public void onCancelled(DatabaseError error){
//        Log.w(TAG,"Failed to read value.",error.toException());
//    }

}