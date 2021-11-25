package com.example.shopping.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.R;
import com.example.shopping.authentication.ChangePassword;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileScreen extends AppCompatActivity {

    ImageView back, backbg;
    TextView save;
    EditText fullname, email, phone, address;
    String username;
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
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
        myRef.child("user").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullname.setText(snapshot.child("fullname").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
                phone.setText(snapshot.child("phonenum").getValue(String.class));
                try {
                    address.setText(snapshot.child("address").getValue(String.class));
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fullname.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()) {
                    myRef.child("user").child(username).child("fullname").setValue(fullname.getText().toString());
                    myRef.child("user").child(username).child("email").setValue(email.getText().toString());
                    myRef.child("user").child(username).child("phonenum").setValue(phone.getText().toString());
                    myRef.child("user").child(username).child("address").setValue(address.getText().toString());
                    Toast.makeText(ProfileScreen.this, "Update successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProfileScreen.this, "Please enter new information before updating!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileScreen.this, ChangePassword.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    public void getID(){
        back = findViewById(R.id.back);
        backbg = findViewById(R.id.backbg);
        save = findViewById(R.id.save);
        fullname = findViewById(R.id.textPersonName);
        email = findViewById(R.id.fullName);
        phone = findViewById(R.id.phonenum);
        address = findViewById(R.id.address);
        changePassword = findViewById(R.id.button3);
    }
}