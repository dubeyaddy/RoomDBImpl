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

public class RegisterActivity extends AppCompatActivity {

    TextView tv_reg;
    EditText et_regId;
    EditText et_regPass;
    Button bt_reg;
    FirebaseAuth mAuth;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_reg=findViewById(R.id.tv_reg);
        et_regId=findViewById(R.id.et_regId);
        et_regPass= findViewById(R.id.et_regPass);
        bt_reg=findViewById(R.id.bt_register);
        mAuth=FirebaseAuth.getInstance();

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regis(email,password);
            }
        });
    }

    public void regis(String email, String password){
        email=et_regId.getText().toString();
        password=et_regPass.getText().toString();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this,"Enter both fields",Toast.LENGTH_SHORT).show();
        }else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register Unsuccessfull"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}