package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email_btn2,password_btn2;
    private TextView submit_btn2, register_btn;
    private FirebaseAuth mAuthLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        mAuthLogin = FirebaseAuth.getInstance();

        email_btn2 = findViewById(R.id.editTextTextEmailAddress2);
        password_btn2 = findViewById(R.id.editTextTextPassword2);
        submit_btn2 = findViewById(R.id.editText_signin);
        register_btn = findViewById(R.id.registration_redirect);

        submit_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_btn2.getText().toString();
                String pass = password_btn2.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this, "Please Input All Fields.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    loginUserAccount(email, pass, new OnLoginCompleteListener() {
                        @Override
                        public void onLoginSuccess() {
                            Intent newIntent = new Intent(Login.this, MainActivity.class);
                            startActivity(newIntent);
                        }

                        @Override
                        public void onLoginFailure() {
                            Toast.makeText(Login.this, "Failed Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(Login.this, Registration.class);
                startActivity(newIntent);
            }
        });

    }

    private void loginUserAccount(String user, String pass, OnLoginCompleteListener listener) {
        mAuthLogin
                .signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Succesful Login", Toast.LENGTH_SHORT).show();
                            listener.onLoginSuccess();
                        }else{
                            Toast.makeText(Login.this, "Failed Login", Toast.LENGTH_SHORT).show();
                            listener.onLoginFailure();
                        }
                    }
                });

    }

    interface OnLoginCompleteListener {
        void onLoginSuccess();
        void onLoginFailure();
    }
}