package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitnessapp.keys.KeysFirebaseStore;
import com.example.fitnessapp.user.AsyncJSON;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonParser();

        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LogActivity.class));
            finish();
        });
    }

    private void jsonParser(){

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        String userID = fAuth.getUid();
        DocumentReference documentReference = fStore.collection(KeysFirebaseStore.COLLECTION_USER).document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String integrationCode = documentSnapshot.getString(KeysFirebaseStore.INTEGRATION_CODE);
                    new AsyncJSON().execute(integrationCode);
                }else {
                    Toast.makeText(MainActivity.this, "Doc not exesite", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
