package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LogActivity.class));
            finish();
        });
    }
}
