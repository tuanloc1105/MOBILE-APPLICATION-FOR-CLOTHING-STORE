package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderScreen extends AppCompatActivity {

    ImageView back, backbg;
    String username;
    ArrayList<Order> orderlist;
    RecyclerView option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);
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
        option.setHasFixedSize(true);
        option.setLayoutManager(new LinearLayoutManager(OrderScreen.this));

        orderlist = new ArrayList<>();
        OrderListAdapter orderListAdapterAdapter = new OrderListAdapter(OrderScreen.this, orderlist);
        option.setAdapter(orderListAdapterAdapter);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        Query findOrder = myRef.child("order").orderByChild("username").equalTo(username);
        findOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    orderlist.clear();
                    for (DataSnapshot data : snapshot.getChildren()){
                        Long id = data.child("id").getValue(Long.class);
                        String user = data.child("username").getValue(String.class);
                        String paymentmethod = data.child("paymentMethod").getValue(String.class);
                        String address = data.child("address").getValue(String.class);
                        String status = data.child("status").getValue(String.class);
                        int money = data.child("tien").getValue(Integer.class);

                        Order current = new Order(id, user, paymentmethod, address, status, money, null);
                        orderlist.add(current);
                    }
                    orderListAdapterAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getID(){
        back = findViewById(R.id.back5);
        backbg = findViewById(R.id.backbg5);
        option = findViewById(R.id.productlist);
    }
}