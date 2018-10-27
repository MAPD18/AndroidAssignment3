package com.example.rodrigosilva.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodrigosilva.shoppingapp.data.CustomerDao;
import com.example.rodrigosilva.shoppingapp.data.SalesRepresentativeDao;
import com.example.rodrigosilva.shoppingapp.model.Customer;
import com.example.rodrigosilva.shoppingapp.model.SalesRepresentative;
import com.example.rodrigosilva.shoppingapp.utility.Constants;

public class MainActivity extends AppCompatActivity {

    private EditText userNameEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private boolean isCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Spinner userTypeSpinner = findViewById(R.id.userTypeSpinner);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isCustomer = position == 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isCustomer = true;
            }
        });

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        signUpButton = findViewById(R.id.registerButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
    }

    private void attemptLogin() {
        if (isCustomer) {
            CustomerDao customerDao = new CustomerDao(getApplicationContext());
            Customer customer = customerDao.findCustomer(userNameEditText.getText().toString(), passwordEditText.getText().toString());
            if (customer != null) {
                Toast.makeText(this, "LOGADOO", Toast.LENGTH_LONG).show();
                getPreferences(Context.MODE_PRIVATE).edit().putString(Constants.USERNAME_KEY, userNameEditText.getText().toString()).apply();
            }
        } else {
            SalesRepresentativeDao salesRepresentativeDao = new SalesRepresentativeDao(getApplicationContext());
            SalesRepresentative salesRepresentative = salesRepresentativeDao.findSalesRepresentative(userNameEditText.getText().toString(), passwordEditText.getText().toString());
            if (salesRepresentative != null) {
                Toast.makeText(this, "LOGADOO", Toast.LENGTH_LONG).show();
                getPreferences(Context.MODE_PRIVATE).edit().putString(Constants.USERNAME_KEY, userNameEditText.getText().toString()).apply();
                startActivity(new Intent(this, ShoeManagementActivity.class));
            }
        }
    }

    private void startRegisterActivity() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(Constants.USERNAME_KEY, userName);
        intent.putExtra(Constants.PASSWORD_KEY, password);

        startActivity(intent);
    }
}
