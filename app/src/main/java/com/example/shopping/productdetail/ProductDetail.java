package com.example.shopping.productdetail;

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

import com.example.shopping.R;
import com.example.shopping.cart.Cart;
import com.example.shopping.cart.CartScreen;
import com.example.shopping.homescreen.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {
    ImageView bg, detail, back, backbg;
    TextView name, price;
    Button button;
    Product product;
    String username, size;
    RadioGroup groupsize;
    RadioButton rButton;
    int count = 0;
    long no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getID();
        Bundle extra = getIntent().getExtras();

        product = (Product)getIntent().getSerializableExtra("product");
        username = extra.getString("username");

        name.setText(product.gethatname());
        price.setText("đ" + String.format("%,d", product.getprice()) + " VNĐ");
        Picasso.get().load(product.getimage()).into(bg);//resize(750, 1280).

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkButton();
                count++;
                Cart cart = new Cart(product.gethatid(), product.gethatname(),
                        product.getimage(), size, product.getprice(), count);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                /*
                myRef.child("user").child(username).child("cart").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            no = snapshot.getChildrenCount();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                 */
                myRef.child("user").child(username)
                        .child("cart")
                        .child(product.gethatid() /*+ String.valueOf(no)*/).setValue(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        openDialog(Gravity.CENTER);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetail.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getID(){
        bg = findViewById(R.id.imagedetail);
        detail = findViewById(R.id.imageView);
        name = findViewById(R.id.productname);
        price = findViewById(R.id.price);
        button = findViewById(R.id.addtocart);
        back = findViewById(R.id.back);
        backbg = findViewById(R.id.backbg);
        groupsize = findViewById(R.id.group);
    }

    public void checkButton(){
        int radioID = groupsize.getCheckedRadioButtonId();
        rButton = findViewById(radioID);
        size = rButton.getText().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_when_add_to_cart);
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
        Button goToCartButton = dialog.findViewById(R.id.button_gotocart);
        Button okButton = dialog.findViewById(R.id.button_ok2);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, CartScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}