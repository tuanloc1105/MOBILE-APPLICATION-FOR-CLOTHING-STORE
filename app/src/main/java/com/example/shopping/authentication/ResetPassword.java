package com.example.shopping.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetPassword extends AppCompatActivity {

    private String phone, username;
    TextView save;
    EditText newpass, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        phone = getIntent().getStringExtra("phonenumber");
        phone = phone.replace("+84", "0");
        getID();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newpass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty()){
                    Toast.makeText(ResetPassword.this, "Please enter new password", Toast.LENGTH_SHORT).show();
                }
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                Query finduser = myRef.child("user").orderByChild("phonenum").equalTo(phone);
                finduser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot data : snapshot.getChildren()){
                                username = data.child("username").getValue(String.class);
                                System.out.println(username);
                                if (newpass.getText().toString().equals(confirmpass.getText().toString())) {
                                    myRef.child("user").child(username).child("password").setValue(confirmpass.getText().toString());
                                    Toast.makeText(ResetPassword.this, "Reset password successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ResetPassword.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(ResetPassword.this, "Please enter new password", Toast.LENGTH_SHORT).show();
                                    return;
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
        save = findViewById(R.id.save2);
        newpass = findViewById(R.id.newpass);
        confirmpass = findViewById(R.id.confirmnewpass);
    }
}