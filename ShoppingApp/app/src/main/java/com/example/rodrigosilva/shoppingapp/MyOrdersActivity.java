package com.example.rodrigosilva.shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rodrigosilva.shoppingapp.adapter.OrderListAdapter;
import com.example.rodrigosilva.shoppingapp.data.OrderDao;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private OrderListAdapter adapter;
    private OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        orderList = findViewById(R.id.orderList);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        orderDao = new OrderDao(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        int customerId = preferences.getInt(Constants.USER_ID_KEY, 0);

        adapter = new OrderListAdapter(orderDao.findAllOrdersByCustomer(customerId));

        orderList.setAdapter(adapter);
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
}
