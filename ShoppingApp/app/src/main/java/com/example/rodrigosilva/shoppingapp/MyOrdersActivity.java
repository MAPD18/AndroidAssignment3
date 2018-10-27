package com.example.rodrigosilva.shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.adapter.OrderListAdapter;
import com.example.rodrigosilva.shoppingapp.data.OrderDao;
import com.example.rodrigosilva.shoppingapp.listener.CustomItemClickListener;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private OrderListAdapter adapter;
    private OrderDao orderDao;
    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.my_orders_title);

        orderList = findViewById(R.id.orderList);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        orderDao = new OrderDao(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        boolean isSales = preferences.getBoolean(Constants.USER_IS_SALES_KEY, false);
        int customerId = preferences.getInt(Constants.USER_ID_KEY, 0);

        if (isSales)
            adapter = new OrderListAdapter(orderDao.findAllOrdersByCustomer(customerId), new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    startCartActivity(position);
                }
            });
        else
            adapter = new OrderListAdapter(orderDao.findAllOrdersByCustomer(customerId));

        orderList.setAdapter(adapter);

        emptyListTextView = findViewById(R.id.emptyListTextView);
        emptyListTextView.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText(String.format(getString(R.string.welcome_message),
                preferences.getString(Constants.USERNAME_KEY, "")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_my_orders_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.makeOrder: {
                startOrderActivity();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    private void startOrderActivity() {
        startActivity(new Intent(this, OrderActivity.class));
    }

    private void startCartActivity(int position) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra(Constants.ORDER_KEY, adapter.getItemByPosition(position));
        startActivity(intent);
    }
}
