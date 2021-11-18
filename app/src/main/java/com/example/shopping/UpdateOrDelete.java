package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateOrDelete extends AppCompatActivity {
    EditText id, name;
    Button button, button2;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete);
        id = findViewById(R.id.hatid);
        name = findViewById(R.id.hatname);
        button = findViewById(R.id.buttonUpdate);
        button2 = findViewById(R.id.buttonDelete);
        Bundle extra = getIntent().getExtras(); //getIntent().hasExtra() kiểm tra xem có data hay không
        product = (Product)getIntent().getSerializableExtra("product");
        id.setText(product.gethatid());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setname(name.getText().toString());
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("product").child(product.gethatid()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateOrDelete.this, "Update thanh cong", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateOrDelete.this, "Update khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("product").child(product.gethatid()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateOrDelete.this, "Delete thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateOrDelete.this, "Delete khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
//                super.onBackPressed();
            }
        });

    }

}