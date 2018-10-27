package com.example.rodrigosilva.shoppingapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodrigosilva.shoppingapp.data.ShoeDao;
import com.example.rodrigosilva.shoppingapp.model.Shoe;

import static com.example.rodrigosilva.shoppingapp.ShoeManagementActivity.REQUEST_ADD_SHOE;

public class RegisterShoeActivity extends AppCompatActivity {

    private EditText shoeNameEditText, sizeEditText, priceEditText;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shoe);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.register_shoe_title);

        init();
    }

    private void init() {
        shoeNameEditText = findViewById(R.id.shoeNameEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        priceEditText = findViewById(R.id.priceEditText);

        categorySpinner = findViewById(R.id.categorySpinner);

        Button saveShoeButton = findViewById(R.id.saveShoeButton);
        saveShoeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveShoe();
            }
        });
    }

    private void saveShoe() {
        if (isFormValid()) {
            ShoeDao shoeDao = new ShoeDao(getApplicationContext());

            int size = Integer.parseInt(sizeEditText.getText().toString());
            float price = Float.parseFloat(priceEditText.getText().toString());

            Shoe shoe = new Shoe(shoeNameEditText.getText().toString(),
                    categorySpinner.getSelectedItemPosition(),
                    size,
                    price);

            shoeDao.insert(shoe);
            finishActivity(REQUEST_ADD_SHOE);
            finish();
        }
    }

    private boolean isFormValid() {
        boolean result = true;
        if (shoeNameEditText.getText().length() == 0) {
            shoeNameEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (sizeEditText.getText().length() == 0) {
            sizeEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (priceEditText.getText().length() == 0) {
            priceEditText.setError(getString(R.string.error_empty_field));
            result = false;
        }
        if (categorySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), R.string.error_category_invalid, Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }
}
