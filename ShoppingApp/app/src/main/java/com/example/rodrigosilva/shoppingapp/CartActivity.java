package com.example.rodrigosilva.shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rodrigosilva.shoppingapp.data.OrderDao;
import com.example.rodrigosilva.shoppingapp.data.ShoeDao;
import com.example.rodrigosilva.shoppingapp.model.Customer;
import com.example.rodrigosilva.shoppingapp.model.Order;
import com.example.rodrigosilva.shoppingapp.model.Shoe;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class CartActivity extends AppCompatActivity {

    TextView shoeNameTextView, shoePriceTextView, shoeSizeTextView, shoeCategoryTextView;
    EditText quantityEditText;
    Button checkoutButton;
    private ShoeDao shoeDao;
    private Shoe shoe;
    private int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        int shoeId = getIntent().getIntExtra(Constants.SHOE_ID_KEY, 0);
        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        customerId = preferences.getInt(Constants.USER_ID_KEY, 0);
        shoeDao = new ShoeDao(getApplicationContext());
        shoe = shoeDao.findShoeById(shoeId);

        init();
    }
    
    private void init() {
        shoeNameTextView = findViewById(R.id.shoeNameTextView);
        shoeNameTextView.setText(shoe.getName());
        shoePriceTextView = findViewById(R.id.shoePriceTextView);
        shoePriceTextView.setText(String.valueOf(shoe.getPrice()));
        shoeSizeTextView = findViewById(R.id.shoeSizeTextView);
        shoeSizeTextView.setText(String.valueOf(shoe.getSize()));
        shoeCategoryTextView = findViewById(R.id.shoeCategoryTextView);
        shoeCategoryTextView.setText(getCategory(shoe.getCategory()));

        quantityEditText = findViewById(R.id.quantityEditText);
        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });
    }

    private void saveOrder() {
        OrderDao orderDao = new OrderDao(getApplicationContext());
        Order order = new Order(new Customer(customerId),
                shoe,
                Integer.parseInt(quantityEditText.getText().toString()));

        orderDao.insert(order);
        startMyOrdersActivity();
    }

    private String getCategory(int categoryId) {
        switch (categoryId) {
            case 1: { return "Men";}
            case 2: { return "Women";}
            case 3: { return "Kids";}
            default: { return "Erro";}
        }
    }

    private void startMyOrdersActivity() {
        startActivity(new Intent(this, MyOrdersActivity.class));
    }

}
