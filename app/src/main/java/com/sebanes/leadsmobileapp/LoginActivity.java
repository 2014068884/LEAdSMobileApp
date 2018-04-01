package com.sebanes.leadsmobileapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btnLogin, btnReg;
    private ProgressBar loginProgress;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);
        loginProgress = findViewById(R.id.login_progress);

        auth = FirebaseAuth.getInstance();

        //if user clicks login btn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                //if both input fields are not empty
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                    loginProgress.setVisibility(View.VISIBLE);

                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //validates user
                            if(task.isSuccessful()) {
                                sendToMain();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            }
                            loginProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();

        //if user is logged in
        if(currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
