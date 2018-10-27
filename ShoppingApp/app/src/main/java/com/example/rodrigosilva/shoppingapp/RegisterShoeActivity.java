package com.example.rodrigosilva.shoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
