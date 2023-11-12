package com.example.piapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piapp.entities.UserDao;
import com.example.piapp.entities.UserDatabase;
import com.example.piapp.entities.UserEntity;

public class login extends AppCompatActivity {

    EditText email , password;
    AppCompatButton buttonLogin , buttonSignup;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);
        remember=findViewById(R.id.rememberMe);

        SharedPreferences preferences = getSharedPreferences("checkbox" , MODE_PRIVATE);
        String checkbox=preferences.getString("remember", "");


        if(checkbox.equals("true")) {
            Intent intent=new Intent(login.this , profile.class);
            startActivity(intent);

        }
        else if(checkbox.equals("false")) {
            Toast.makeText(this , "Please sign in .", Toast.LENGTH_SHORT).show();
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(emailText, passwordText);

                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String email = userEntity.getEmail();
                                String fullName = userEntity.getFullName();
                                String phoneNumber = userEntity.getPhoneNumber();

                                // Mettre à jour les SharedPreferences ici si "rememberMe" est coché
                                if (remember.isChecked()) {
                                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("remember", "true");
                                    editor.apply();
                                }

                                startActivity(new Intent(
                                                login.this, profile.class
                                        ).putExtra("email", email)
                                                .putExtra("fullName", fullName)
                                                .putExtra("phoneNumber", phoneNumber)
                                );
                            }
                        }
                    }).start();
                }
            }
        });


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this , signup.class));
            }
        });


        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton , boolean b ) {

                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(login.this, "Checked", Toast.LENGTH_SHORT).show();

                }
                else if(!compoundButton.isChecked()) {
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(login.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}