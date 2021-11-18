package com.example.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>{

    Context context;
    ArrayList<Order> orders;

    public OrderListAdapter(Context context, ArrayList<Order> orders){
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_screen_item, parent, false);
        return new OrderListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.orderno.setText("Order no: #" + String.valueOf(order.getId()));
        holder.money.setText("đ" + String.format("%,d", order.getTien()) + " VNĐ");
        holder.status.setText(order.getStatus());
        if (order.getStatus().equals("Pending") || order.getStatus().equals("Delivery")){
            holder.status.setTextColor(Color.YELLOW);
        }
        if (order.getStatus().equals("Received")){
            holder.status.setTextColor(Color.GREEN);
        }
        if (order.getStatus().equals("Canceled") || order.getStatus().equals("Request cancel")){
            holder.status.setTextColor(Color.RED);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailScreen.class);
                intent.putExtra("orderno", order.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView orderno, status, money;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            orderno = itemView.findViewById(R.id.textView11);
            status = itemView.findViewById(R.id.textView13);
            money = itemView.findViewById(R.id.textView12);
        }
    }
}
