package com.example.rodrigosilva.shoppingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.R;
import com.example.rodrigosilva.shoppingapp.listener.CustomItemClickListener;
import com.example.rodrigosilva.shoppingapp.model.Order;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private ArrayList<Order> dataSet;
    private CustomItemClickListener listener;

    public OrderListAdapter(ArrayList<Order> dataSet, CustomItemClickListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public OrderListAdapter(ArrayList<Order> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_list_item, viewGroup, false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);

        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, myViewHolder.getAdapterPosition());
                }
            });
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (getItemCount() > 0) {
            Order order = dataSet.get(i);
            myViewHolder.shoeNameTextView.setText(order.getShoe().getName());
            myViewHolder.shoePriceTextView.setText(String.valueOf(order.getShoe().getPrice()));
            myViewHolder.shoeSizeTextView.setText(String.valueOf(order.getShoe().getSize()));
            myViewHolder.shoeCategoryTextView.setText(getCategory(order.getShoe().getCategory()));
            myViewHolder.orderQuantityTextView.setText(String.valueOf(order.getQuantity()));
            myViewHolder.statusTextView.setText(order.getStatus().name());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void resetDataSet(ArrayList<Order> newDataSet) {
        dataSet = newDataSet;
        notifyDataSetChanged();
    }

    public int getItemIdByPosition(int position) {
        return dataSet.get(position).getId();
    }

    public Order getItemByPosition(int position) {
        return dataSet.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shoeNameTextView, shoePriceTextView, shoeSizeTextView, shoeCategoryTextView, orderQuantityTextView, statusTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shoeNameTextView = itemView.findViewById(R.id.shoeNameTextView);
            shoePriceTextView = itemView.findViewById(R.id.shoePriceTextView);
            shoeSizeTextView = itemView.findViewById(R.id.shoeSizeTextView);
            shoeCategoryTextView = itemView.findViewById(R.id.shoeCategoryTextView);
            orderQuantityTextView = itemView.findViewById(R.id.orderQuantityTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }

    private String getCategory(int categoryId) {
        switch (categoryId) {
            case 1: { return "Men";}
            case 2: { return "Women";}
            case 3: { return "Kids";}
            default: { return "Erro";}
        }
    }
}
