package com.example.shopping;
//

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    ArrayList<Cart> cart;
    Context context;
    String username;

    public CartAdapter(Context context, ArrayList<Cart> cart, String username){
        this.cart = cart;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart_ = cart.get(position);
        holder.name.setText(cart_.getName());
        holder.price.setText("đ" + String.format("%,d", cart_.getPrice()) + " VNĐ");
        holder.size.setText("Size: " + cart_.getSize());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "This function is being developed", Toast.LENGTH_SHORT).show();
            }
        });
        holder.edit.setVisibility(View.INVISIBLE);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("user").child(username)
                        .child("cart")
                        .child(cart_.getId()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error occurred when deleting", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Picasso.get().load(cart_.getImage()).into(holder.image);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetail.class);
                Product product = new Product(cart_.getId(), cart_.getImage(), cart_.getName(), cart_.getPrice(), cart_.getSize());
                intent.putExtra("username", username);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, size;
        Button edit, remove;
        ImageView image;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView3);
            price = itemView.findViewById(R.id.textView4);
            size = itemView.findViewById(R.id.textView5);
            edit = itemView.findViewById(R.id.button);
            remove = itemView.findViewById(R.id.button2);
            image = itemView.findViewById(R.id.imageView3);
            card = itemView.findViewById(R.id.card);
        }
    }
}
