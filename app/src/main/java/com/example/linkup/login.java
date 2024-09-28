package com.example.linkup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button button;
    EditText email, password;
    FirebaseAuth auth;
    String emailPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z0-9-]+(\\.[a-z0-9-]+)*";
    TextView signupView;
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Redirecting...");
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.loginbutton);
        email = findViewById(R.id.editTextLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        signupView = findViewById(R.id.signUp);

        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, sign_up.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(pass)){
                    progressDialog.dismiss();
                    Toast.makeText(login.this, "Either Email or Password is empty!", Toast.LENGTH_SHORT).show();
                } else if(!Email.matches(emailPattern)){
                    progressDialog.dismiss();
                    email.setError("Invalid Email!");
                } else if(password.length() < 8){
                    progressDialog.dismiss();
                    password.setError("Password should be at least 8 characters long!");
                } else {
//                    progressDialog.dismiss();
                        auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.show();
                                    try{
                                        Intent intent = new Intent(login.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch(Exception E){
                                        Toast.makeText(login.this, E.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

    }
}