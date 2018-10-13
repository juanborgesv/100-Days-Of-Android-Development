package com.juanborges.inventory;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {

    static final String LOG_TAG = EditorActivity.class.getSimpleName();
    static final int PICK_IMAGE = 1;

    private TextInputEditText productName;

    TextInputEditText productPrice;

    ImageView productImage;
    String imageUri;

    TextView productQuantity;

    TextInputEditText supplierName;

    TextInputEditText supplierEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        productName = findViewById(R.id.product_name_edit_text);
        productPrice = findViewById(R.id.product_price_edit_text);
        productImage = findViewById(R.id.product_image);
        productQuantity = findViewById(R.id.quantity_text);
        supplierName = findViewById(R.id.supplier_name_edit_text);
        supplierEmail = findViewById(R.id.supplier_email_edit_text);

        MaterialButton minusButton = findViewById(R.id.minus_button);
        MaterialButton plusButton = findViewById(R.id.plus_button);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantity.getText().toString());

                if (quantity < 1)
                    return;

                --quantity;

                productQuantity.setText(String.valueOf(quantity));
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantity.getText().toString());
                ++quantity;


                productQuantity.setText(String.valueOf(quantity));
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                finish();
                return true;
            case R.id.action_delete:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {
            try {
                imageUri = data.getData().toString();
                Log.i(LOG_TAG, "Picture path: "+ imageUri);
                productImage.setImageURI(data.getData());
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Something went wrong while trying to get image path", e);
            }
        }
    }

    private void saveProduct() {
        Log.i(LOG_TAG, "Product info about to be saved below.");
        try {
            Log.i(LOG_TAG, "Product name: " + productName.getText().toString());
            Log.i(LOG_TAG, "Product price: " + productPrice.getText().toString());
            Log.i(LOG_TAG, "Product image: " + imageUri);
            Log.i(LOG_TAG, "Product quantity: " + productQuantity.getText().toString());
            Log.i(LOG_TAG, "Supplier name: " + supplierName.getText().toString());
            Log.i(LOG_TAG, "Supplier email: " + supplierEmail.getText().toString());
        } catch (NullPointerException exception) {
            Log.e(LOG_TAG, "Something went wrong while saving data.", exception);
        }
    }
}
