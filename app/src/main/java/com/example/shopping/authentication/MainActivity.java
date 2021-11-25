package com.example.shopping.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.homescreen.HomeScreen;
import com.example.shopping.R;
import com.example.shopping.receiver.WifiDetection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private WifiDetection wifiDetection;
    EditText username, password;
    Button login;
    TextView create, forgot;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.login_screen);
        username = (EditText)findViewById(R.id.fullName);
        password = (EditText)findViewById(R.id.PasswordforCreate);
        login = findViewById(R.id.loginButton);
        create = findViewById(R.id.createAccount);
        forgot = findViewById(R.id.forgot);

        wifiDetection = new WifiDetection();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starCreateScreen();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!wifiDetection.isStatus()){
                    Toast.makeText(MainActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                String usern = username.getText().toString();
                String pass = password.getText().toString();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                Query checkuser = myRef.child("user").orderByChild("username").equalTo(usern);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String passFromDB = snapshot.child(usern).child("password").getValue(String.class);
                            System.out.println(passFromDB + " " + pass);
                            if (passFromDB.equals(pass)){
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                String usernameFromDB = snapshot.child(usern).child("username").getValue(String.class);
                                String positionFromDB = snapshot.child(usern).child("position").getValue(String.class);

                                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                                intent.putExtra("user", usernameFromDB);
                                intent.putExtra("position", positionFromDB);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong password or username", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "This username does not exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPasword.class);
                startActivity(intent);
            }
        });

    }
    public void starCreateScreen(){
        Intent intent = new Intent(MainActivity.this, createAccount.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit app", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiDetection, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiDetection);
    }
}