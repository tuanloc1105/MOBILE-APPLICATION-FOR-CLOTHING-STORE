package com.example.shopping.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping.model.Cart;
import com.example.shopping.payment.PaymentScreen;
import com.example.shopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CartScreen extends AppCompatActivity {


    ArrayList<Cart> cartlist;
    RecyclerView option;
    String username;
    ImageView back, backbg;
    TextView tong, cartstatus;
    Button checkout;
    int tien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);
        getID();
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");

        option.setHasFixedSize(true);
        option.setLayoutManager(new LinearLayoutManager(CartScreen.this));

        try{
            cartlist = new ArrayList<>();
            CartAdapter cartAdapter = new CartAdapter(CartScreen.this, cartlist, username);
            option.setAdapter(cartAdapter);
            // dragged item in RecyclerView
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                    int positionDragged = dragged.getBindingAdapterPosition();
                    int positionTaggert = target.getBindingAdapterPosition();

                    Collections.swap(cartlist, positionDragged, positionTaggert);
                    cartAdapter.notifyItemMoved(positionDragged, positionTaggert);

                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                }
            });
            helper.attachToRecyclerView(option);


            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

            myRef.child("user").child(username).child("cart").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartlist.clear();
                    tien = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Cart current = data.getValue(Cart.class);
//                        System.out.println(current.toString());
                        cartlist.add(current);
                        tien += current.getPrice() * current.getCount();
                    }
                    cartAdapter.notifyDataSetChanged();
                    tong.setText("Tổng: " + String.format("%,d", tien) + " VNĐ");
                    if (tien != 0){
                        cartstatus.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

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

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tien != 0){
                    Intent intent = new Intent(CartScreen.this, PaymentScreen.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                else{
                    return;
                }
            }
        });

    }

    public void getID(){
        option = findViewById(R.id.cart_list);
        back = findViewById(R.id.back);
        backbg = findViewById(R.id.backbg);
        tong = findViewById(R.id.tong);
        checkout = findViewById(R.id.button4);
        cartstatus = findViewById(R.id.textView6);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}