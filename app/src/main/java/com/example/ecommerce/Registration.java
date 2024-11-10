package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private EditText email_btn,password_btn, retypepassword_btn;
    private TextView submit_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration);

        mAuth = FirebaseAuth.getInstance();

        email_btn = findViewById(R.id.editTextTextEmailAddress);
        password_btn = findViewById(R.id.editTextTextPassword);
        retypepassword_btn = findViewById(R.id.editTextTextPassword2);
        submit_btn = findViewById(R.id.editText_CreateAcc);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_btn.getText().toString();
                String password = password_btn.getText().toString();
                String retypepassword = retypepassword_btn.getText().toString();
                if (email.isEmpty() || password.isEmpty() || retypepassword.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Input All Fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals(retypepassword) && isValidPassword(password)){
                    registerNewUser(email, password);
                    Intent newIntent = new Intent(Registration.this, Login.class);
                    startActivity(newIntent);
                }else{
                    Toast.makeText(Registration.this, "Passwords do not match or Invalid password structure", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }

    private void registerNewUser(String email, String password) {
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this, "Succesfully Registered", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Registration.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidPassword(String password) {
        if(password.length() < 8){
            Toast.makeText(Registration.this, "Password needs to be 8 characters or more.", Toast.LENGTH_SHORT).show();
        }

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%&+=])(?=\\S+$).{4,}$";


        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
