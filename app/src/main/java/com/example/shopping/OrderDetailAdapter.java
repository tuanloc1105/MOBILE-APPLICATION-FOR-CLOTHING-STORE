package com.example.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder>{

    ArrayList<Cart> cart;
    Context context;

    public OrderDetailAdapter(Context context, ArrayList<Cart> cart){
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart_ = cart.get(position);
        holder.name.setText(cart_.getName());
        holder.price.setText("đ" + String.format("%,d", cart_.getPrice()) + " VNĐ");
        holder.size.setText("Size: " + cart_.getSize());
        Picasso.get().load(cart_.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, size;
        ImageView image;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView3);
            price = itemView.findViewById(R.id.textView4);
            size = itemView.findViewById(R.id.textView5);
            image = itemView.findViewById(R.id.imageView3);
            card = itemView.findViewById(R.id.card);
        }
    }
}
