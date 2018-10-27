package com.example.rodrigosilva.shoppingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.R;
import com.example.rodrigosilva.shoppingapp.listener.CustomItemClickListener;
import com.example.rodrigosilva.shoppingapp.model.Shoe;

import java.util.ArrayList;

public class ShoeListAdapter extends RecyclerView.Adapter<ShoeListAdapter.MyViewHolder> {

    private ArrayList<Shoe> dataSet;
    private CustomItemClickListener listener;

    public ShoeListAdapter(ArrayList<Shoe> dataSet, CustomItemClickListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public ShoeListAdapter(ArrayList<Shoe> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shoe_list_item, viewGroup, false);

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
            Shoe shoe = dataSet.get(i);
            myViewHolder.shoeNameTextView.setText(shoe.getName());
            myViewHolder.shoePriceTextView.setText("CAD$"+String.valueOf(shoe.getPrice()));
            myViewHolder.shoeSizeTextView.setText("Size: "+String.valueOf(shoe.getSize()));
            myViewHolder.shoeCategoryTextView.setText(getCategory(shoe.getCategory()));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void resetDataSet(ArrayList<Shoe> newDataSet) {
        dataSet = newDataSet;
        notifyDataSetChanged();
    }

    public int getItemIdByPosition(int position) {
        return dataSet.get(position).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shoeNameTextView, shoePriceTextView, shoeSizeTextView, shoeCategoryTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shoeNameTextView = itemView.findViewById(R.id.shoeNameTextView);
            shoePriceTextView = itemView.findViewById(R.id.shoePriceTextView);
            shoeSizeTextView = itemView.findViewById(R.id.shoeSizeTextView);
            shoeCategoryTextView = itemView.findViewById(R.id.shoeCategoryTextView);
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
