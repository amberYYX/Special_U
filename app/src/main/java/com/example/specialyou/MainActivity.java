package com.example.specialyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.specialyou.Data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText mUserName;
    private EditText mPassword;
    private Button mLogIn;
    private TextView mSignUp;
    private ProgressBar mProgressBar;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.main_username);
        mPassword = findViewById(R.id.main_password);
        mLogIn = findViewById(R.id.main_LogIn);
        mSignUp = findViewById(R.id.main_signUp);
        mProgressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                if(TextUtils.isEmpty(userName)){
                    mUserName.setError("username is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required.");
                    return;
                }

                if(password.length() < 5){
                    mPassword.setError("password at least 6 characters.");
                }

                mProgressBar.setVisibility(View.VISIBLE);

                //authenticate the user
                mAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Log in succeed.", Toast.LENGTH_SHORT).show();

                            // create user
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            User user = new User();
                            user.setUserID(mUser.getUid());
                            FirebaseDatabase database =  FirebaseDatabase.getInstance();
                            DatabaseReference mRef =  database.getReference().child("Users").child(user.getUserID());
                            mRef.setValue(user);


                            Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
                            intent.putExtra("userID",user.getUserID());
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "ERROR!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,SignUpActivity.class);

                startActivity(intent1);
            }
        });


    }
}
