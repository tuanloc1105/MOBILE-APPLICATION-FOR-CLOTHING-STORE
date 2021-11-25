package com.example.shopping.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping.R;
import com.example.shopping.cart.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDetailScreen extends AppCompatActivity {

    ImageView back, backbg;
    Long id;
    ArrayList<Cart> productlist;
    TextView orderno;
    RecyclerView option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_screen);
        getID();
        Bundle extra = getIntent().getExtras();
        id = extra.getLong("orderno");

        option.setHasFixedSize(true);
        option.setLayoutManager(new LinearLayoutManager(OrderDetailScreen.this));
        productlist = new ArrayList<>();
        OrderDetailAdapter adapter = new OrderDetailAdapter(OrderDetailScreen.this, productlist);
        option.setAdapter(adapter);

        orderno.setText("Order #" + String.valueOf(id));

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("order")
                .child("order" + String.valueOf(id))
                .child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    productlist.add(data.getValue(Cart.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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


    }

    public void getID(){
        option = findViewById(R.id.productlist);
        back = findViewById(R.id.back4);
        backbg = findViewById(R.id.backbg4);
        orderno = findViewById(R.id.orderno);
    }
}