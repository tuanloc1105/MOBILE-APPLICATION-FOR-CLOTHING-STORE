package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentScreen extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView back, backbg;
    TextView address;
    String username;
    String _address;
    String paymentMethod;
    Button button;
    long num_id = 0;
    int tien = 0;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    String emptyAddressMessage = "You did not provide your address, please update your address in order to exactly delivery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
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


        myRef.child("user").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    _address = snapshot.child("address").getValue(String.class);
                    address.setText(_address);
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                num_id = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tien = 0;

                getCheckButton();
                Order order = new Order(num_id, username, paymentMethod, _address, "Pending", tien, null);

                myRef.child("user").child(username).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child("order").child("order" + String.valueOf(num_id)).setValue(order);
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Cart current = data.getValue(Cart.class);
                            tien += current.getPrice() * current.getCount();
                            myRef.child("order").child("order" + String.valueOf(num_id)).child("product").child(current.getId()).setValue(current);
                            myRef.child("order").child("order" + String.valueOf(num_id)).child("tien").setValue(tien);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                openDialog(Gravity.CENTER);
            }
        });

        Query findOrder = myRef.child("order").orderByChild("username").equalTo(username);
        findOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    System.out.println("co");
                    System.out.println(snapshot.child("order0").child("username").getValue().toString());
                    for (DataSnapshot data : snapshot.getChildren()){
                        System.out.println("==================");
                        System.out.println(data.child("id").getValue(Long.class));
                        System.out.println(data.child("username").getValue(String.class));
                        System.out.println(data.child("paymentMethod").getValue(String.class));
                        System.out.println(data.child("address").getValue(String.class));
                        System.out.println(data.child("status").getValue(String.class));
                        System.out.println(data.child("tien").getValue(Integer.class));
                        System.out.println(data.child("product").getValue(Cart.class));
                        myRef.child("order")
                                .child("order" + String.valueOf(data.child("id").getValue(Long.class)))
                                .child("product").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()){
                                    System.out.println(data.getValue(Cart.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                else{
                    Toast.makeText(PaymentScreen.this, "no", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getID(){
        back = findViewById(R.id.back3);
        backbg = findViewById(R.id.backbg3);
        radioGroup = findViewById(R.id.paymentgroup);
        address = findViewById(R.id.textView10);
        button = findViewById(R.id.confirm);
    }

    public void getCheckButton(){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        paymentMethod = radioButton.getText().toString();
    }

    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_when_payment_is_done);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else{dialog.setCancelable(false);}
        Button okButton = dialog.findViewById(R.id.button_ok2);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(PaymentScreen.this, HomeScreen.class);
                intent.putExtra("user", username);
                startActivity(intent);
                myRef.child("user").child(username).child("cart").setValue(null);
                finish();
            }
        });
        dialog.show();
    }
}