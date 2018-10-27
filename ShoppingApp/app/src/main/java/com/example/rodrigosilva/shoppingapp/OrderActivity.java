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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.adapter.ShoeListAdapter;
import com.example.rodrigosilva.shoppingapp.data.ShoeDao;
import com.example.rodrigosilva.shoppingapp.listener.CustomItemClickListener;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class OrderActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    Spinner categorySpinner;
    private RecyclerView shoesList;
    private ShoeListAdapter adapter;
    private ShoeDao shoeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();

        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        welcomeTextView.setText(String.format(getString(R.string.welcome_message),
                preferences.getString(Constants.USERNAME_KEY, "")));
    }

    private void init() {
        welcomeTextView = findViewById(R.id.welcomeTextView);

        shoesList = findViewById(R.id.shoesList);
        shoesList.setHasFixedSize(true);
        shoesList.setLayoutManager(new LinearLayoutManager(this));

        shoeDao = new ShoeDao(getApplicationContext());
        adapter = new ShoeListAdapter(shoeDao.findAllShoes(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                startCartActivity(adapter.getItemIdByPosition(position));
            }
        });

        shoesList.setAdapter(adapter);

        categorySpinner = findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    adapter.resetDataSet(shoeDao.findAllShoes());
                else
                    adapter.resetDataSet(shoeDao.findAllShoes(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void startCartActivity(int shoeId) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra(Constants.SHOE_ID_KEY, shoeId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myOrders: {
                startMyOrdersActivity();
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    private void startMyOrdersActivity() {
        startActivity(new Intent(this, MyOrdersActivity.class));
    }
}
