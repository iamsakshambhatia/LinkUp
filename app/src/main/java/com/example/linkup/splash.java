package com.example.linkup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import androidx.activity.EdgeToEdge;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {
    ImageView applogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        applogo = findViewById(R.id.logo);
        applogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // User is signed in, redirect to the home screen
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(splash.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}