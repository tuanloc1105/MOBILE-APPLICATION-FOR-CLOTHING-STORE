//package com.example.shopping;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class HatCatagory extends AppCompatActivity {
//    RecyclerView option;
//    ArrayList<Product> product;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        getSupportActionBar().hide();
//        setContentView(R.layout.activity_hat_catagory);
//        option = findViewById(R.id.recycleOption);
//        option.setHasFixedSize(true);
//        option.setLayoutManager(new LinearLayoutManager(this));
//
//        /*ArrayList<Product>*/ product = new ArrayList<>();
//        ProductAdapter myAdapter = new ProductAdapter(this, product);
//        option.setAdapter(myAdapter);
//
//
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
//        myRef.child("product").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                product.clear();
//                for (DataSnapshot data : snapshot.getChildren()){
//                    Product currentProduct = data.getValue(Product.class);
//                    product.add(currentProduct);
//                }
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//}