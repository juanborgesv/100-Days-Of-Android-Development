package com.juanborges.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract;
import com.juanborges.inventory.data.ProductContract.ProductEntry;

public class EditorActivity extends AppCompatActivity {

    static final String LOG_TAG = EditorActivity.class.getSimpleName();
    static final int PICK_IMAGE = 1;

    private Uri currentUri;

    private TextInputEditText productNameEditText;

    private TextInputEditText productPriceEditText;

    private ImageView productImage;
    private String imageUri;

    private TextView productQuantityEditText;

    private TextInputEditText supplierNameEditText;

    private TextInputEditText supplierEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currentUri = intent.getData();

        if (currentUri == null) {
            setTitle(R.string.editor_activity_title_new_product);

            invalidateOptionsMenu();
        } else {
            setTitle(R.string.editor_activity_title_edit_product);

            // getLoaderManager().initLoader..........
        }


        productNameEditText = findViewById(R.id.product_name_edit_text);
        productPriceEditText = findViewById(R.id.product_price_edit_text);
        productImage = findViewById(R.id.product_image);
        productQuantityEditText = findViewById(R.id.quantity_text);
        supplierNameEditText = findViewById(R.id.supplier_name_edit_text);
        supplierEmailEditText = findViewById(R.id.supplier_email_edit_text);

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
                int quantity = Integer.parseInt(productQuantityEditText.getText().toString());

                if (quantity < 1)
                    return;

                --quantity;

                productQuantityEditText.setText(String.valueOf(quantity));
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantityEditText.getText().toString());
                ++quantity;


                productQuantityEditText.setText(String.valueOf(quantity));
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
                Log.i(LOG_TAG, "Picture path: " + imageUri);
                productImage.setImageURI(data.getData());
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Something went wrong while trying to get image path", e);
            }
        }
    }

    private void saveProduct() {
        boolean isAllDataValid = true;

        String productName = productNameEditText.getText().toString().trim();
        String productPrice = productPriceEditText.getText().toString().trim();
        String productQuantity = productQuantityEditText.getText().toString().trim();
        String supplierName = supplierNameEditText.getText().toString().trim();
        String supplierEmail = supplierEmailEditText.getText().toString().trim();

        if (currentUri == null && TextUtils.isEmpty(productName) && TextUtils.isEmpty(productPrice)
                && productQuantity == "0" && TextUtils.isEmpty(supplierName)
                && TextUtils.isEmpty(supplierEmail)) {
            return;
        }

        Float price = Float.parseFloat(productPrice);
        Integer quantity = Integer.parseInt(productQuantity);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imageUri);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, supplierEmail);

        if (currentUri == null) {
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(currentUri, contentValues, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.editor_update_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
