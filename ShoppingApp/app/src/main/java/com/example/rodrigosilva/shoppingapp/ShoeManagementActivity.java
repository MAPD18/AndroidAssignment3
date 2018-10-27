package com.example.rodrigosilva.shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.adapter.ShoeListAdapter;
import com.example.rodrigosilva.shoppingapp.data.ShoeDao;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class ShoeManagementActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private RecyclerView shoesList;
    private ShoeListAdapter adapter;
    public static int REQUEST_ADD_SHOE = 9746;
    private ShoeDao shoeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_management);

        init();
        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        welcomeTextView.setText(String.format(getString(R.string.welcome_message),
                preferences.getString(Constants.USERNAME_KEY, "")));
    }

    private void init() {
        welcomeTextView = findViewById(R.id.welcomeTextView);
        Button addShowButton = findViewById(R.id.addShowButton);
        addShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddShoeActivity();
            }
        });

        shoesList = findViewById(R.id.shoesList);
        shoesList.setHasFixedSize(true);
        shoesList.setLayoutManager(new LinearLayoutManager(this));

        shoeDao = new ShoeDao(getApplicationContext());
        adapter = new ShoeListAdapter(shoeDao.findAllShoes());

        shoesList.setAdapter(adapter);
    }

    private void startAddShoeActivity() {
        startActivityForResult(new Intent(this, RegisterShoeActivity.class), REQUEST_ADD_SHOE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ADD_SHOE) {
            updateShoesList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateShoesList() {
        adapter.resetDataSet(shoeDao.findAllShoes());
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
