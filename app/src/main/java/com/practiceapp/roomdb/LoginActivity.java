package com.practiceapp.roomdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView tv_login;
    EditText et_loginID;
    EditText et_loginPass;
    Button bt_login;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        tv_login=findViewById(R.id.tv_login);
        et_loginID=findViewById(R.id.et_loginId);
        et_loginPass= findViewById(R.id.et_loginPass);
        bt_login=findViewById(R.id.bt_login);

        mAuth=FirebaseAuth.getInstance();

        bt_login.setOnClickListener(view-> {
          loginUser();
        });
    }
    private void loginUser(){
        String email=et_loginID.getText().toString();
        String password=et_loginPass.getText().toString();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this,"Enter both fields",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}