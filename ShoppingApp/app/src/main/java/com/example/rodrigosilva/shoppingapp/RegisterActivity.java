package com.example.rodrigosilva.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameEditText, passwordEditText, firstNameEditText, lastNameEditText, addressEditText, cityEditText, postalCodeEditText;
    private boolean isCustomer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.register_user_title);

        init();

        Intent intent = getIntent();
        String userName = intent.getStringExtra(Constants.USERNAME_KEY);
        String password = intent.getStringExtra(Constants.PASSWORD_KEY);

        if (!userName.isEmpty())
            userNameEditText.setText(userName);
        if (!password.isEmpty())
            passwordEditText.setText(password);
    }

    private void init() {
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        postalCodeEditText = findViewById(R.id.postalCodeEditText);

        Spinner userTypeSpinner = findViewById(R.id.userTypeSpinner);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showCostumerForm(position == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showCostumerForm(true);
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void showCostumerForm(boolean show) {
        isCustomer = show;
        addressEditText.setVisibility(show ? View.VISIBLE : View.GONE);
        cityEditText.setVisibility(show ? View.VISIBLE : View.GONE);
        postalCodeEditText.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void register() {
        if (isCustomer) {
            registerCustomer();
        } else
            registerSalesRepresentative();
    }

    private void registerCustomer() {
        if (isCustomerFormValid()) {
            CustomerDao customerDao = new CustomerDao(getApplicationContext());
            if (customerDao.checkUserNameAlreadyExists(userNameEditText.getText().toString())) {
                userNameEditText.setError("Username already taken!");
            } else {
                Customer customer = new Customer(userNameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        addressEditText.getText().toString(),
                        cityEditText.getText().toString(),
                        postalCodeEditText.getText().toString());

                customerDao.insert(customer);
                Toast.makeText(getApplicationContext(), R.string.user_registered_successfully, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void registerSalesRepresentative() {
        if (isSalesRepFormValid()) {
            SalesRepresentativeDao salesRepresentativeDao = new SalesRepresentativeDao(getApplicationContext());
            if (salesRepresentativeDao.checkUserNameAlreadyExists(userNameEditText.getText().toString())) {
                userNameEditText.setError("Username already taken!");
            } else {
                SalesRepresentative salesRepresentative = new SalesRepresentative(userNameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString());

                salesRepresentativeDao.insert(salesRepresentative);

                getPreferences(Context.MODE_PRIVATE).edit().putString(Constants.USERNAME_KEY, userNameEditText.getText().toString()).apply();
                Toast.makeText(getApplicationContext(), R.string.user_registered_successfully, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ShoeManagementActivity.class));
                finish();
            }
        }
    }

    private boolean isCustomerFormValid() {
        boolean result = isSalesRepFormValid();

        if (addressEditText.getText().length() == 0) {
            addressEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (cityEditText.getText().length() == 0) {
            cityEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (postalCodeEditText.getText().length() == 0) {
            postalCodeEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        return result;
    }

    private boolean isSalesRepFormValid() {
        boolean result = true;
        if (userNameEditText.getText().length() == 0) {
            userNameEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (passwordEditText.getText().length() == 0) {
            passwordEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (firstNameEditText.getText().length() == 0) {
            firstNameEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (lastNameEditText.getText().length() == 0) {
            lastNameEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        return result;
    }
}
