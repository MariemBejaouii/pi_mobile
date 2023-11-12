package com.example.piapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.piapp.entities.UserEntity;

public class profile extends AppCompatActivity {

    TextView tEmail, tFullName, tPhoneNumber;
    AppCompatButton buttonUpdate;

    AppCompatButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout=findViewById(R.id.logoutBtn);

        tEmail = findViewById(R.id.email);
        String email = getIntent().getStringExtra("email");
        tEmail.setText(email);

        tFullName = findViewById(R.id.fullName);
        String fullName = getIntent().getStringExtra("fullName");
        tFullName.setText(fullName);

        tPhoneNumber = findViewById(R.id.phoneNumber);
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        tPhoneNumber.setText(phoneNumber);

        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rediriger vers la page de mise à jour de profil
                Intent intent = new Intent(profile.this, updateUser.class);

                // Transmettre les informations du profil actuel si nécessaire
                intent.putExtra("email", email);
                intent.putExtra("fullName", fullName);
                intent.putExtra("phoneNumber", phoneNumber);

                startActivity(intent);
            }

        });



        ///logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                finish();
            }
        });
    }





}
