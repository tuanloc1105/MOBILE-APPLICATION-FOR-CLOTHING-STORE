package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class createAccount extends AppCompatActivity{
    EditText fullname, username, phone, email, password;
    Button register;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.sign_up_screen);
        fullname = (EditText)findViewById(R.id.fullName);
        username = (EditText)findViewById(R.id.userName);
        phone = (EditText)findViewById(R.id.phoneNumber);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.PasswordforCreate);
        register = findViewById(R.id.createButton);

        final ArrayList<User> check = new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    User current = data.getValue(User.class);
                    check.add(current);
                    demo(current);
                    System.out.println(check.size());
//                    System.out.println(check);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("asd" + check.size());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    public void createUser(){
        boolean empty = false;
        String account = username.getText().toString();
        if (account.isEmpty()){
            empty = true;
            username.setError("Required");
        }else{empty = false;}

        String name = fullname.getText().toString();
        if (name.isEmpty()){
            empty = true;
            fullname.setError("Required");
        }else{empty = false;}

        String phonenum = phone.getText().toString();
        if (phonenum.isEmpty()){
            empty = true;
            phone.setError("Required");
        }else{empty = false;}

        String mail = email.getText().toString();
        if (mail.isEmpty()){
            empty = true;
            email.setError("Required");
        }else{empty = false;}

        String pass = password.getText().toString();
        if (pass.isEmpty()){
            empty = true;
            password.setError("Required");
        }else{empty = false;}
        User user = new User(name, account, pass, "user", phonenum, mail);
        if (!empty) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            //String fullname, String username, String password, String postion, String phonenum, String email
            myRef.child("user").child(account).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(createAccount.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(createAccount.this, MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(createAccount.this, "Fail to create account!", Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show();
        }
    }
    public void demo(User user){
        System.out.println(user);
    }
}