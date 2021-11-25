package com.example.shopping.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.homescreen.HomeScreen;
import com.example.shopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {

    TextView save;
    ImageView back, backbg;
    String username;
    EditText old, _new, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getID();
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        backbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("user").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String passWordFromDB = snapshot.child("password").getValue(String.class);
                        if (old.getText().toString().isEmpty() || _new.getText().toString().isEmpty() || confirm.getText().toString().isEmpty()){
                            Toast.makeText(ChangePassword.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            if (!old.getText().toString().equals(passWordFromDB)){
                                Toast.makeText(ChangePassword.this, "Your password is wrong", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                if (_new.getText().toString().equals(confirm.getText().toString())) {
                                    myRef.child("user").child(username).child("password").setValue(confirm.getText().toString());
                                    Toast.makeText(ChangePassword.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePassword.this, HomeScreen.class);
                                    intent.putExtra("user", username);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ChangePassword.this, "Your confirm password is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void getID(){
        save = findViewById(R.id.save);
        back = findViewById(R.id.back2);
        backbg = findViewById(R.id.backbg2);
        old = findViewById(R.id.oldpass);
        _new = findViewById(R.id.newpass);
        confirm = findViewById(R.id.confirmnewpass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}