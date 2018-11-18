package com.juanborges.inventory;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;

public class EditorActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String LOG_TAG = EditorActivity.class.getSimpleName();
    static final int PICK_IMAGE = 1;

    static final int EXISTING_PRODUCT_LOADER = 0;

    private Uri currentUri;

    private TextInputEditText productNameEditText;
    private TextInputLayout productNameInput;

    private TextInputEditText productPriceEditText;
    private TextInputLayout productPriceInput;

    private ImageView productImageView;
    private String imageUri;

    private TextView productQuantityEditText;

    private TextInputEditText supplierNameEditText;
    private TextInputLayout supplierNameInput;

    private TextInputEditText supplierEmailEditText;
    private TextInputLayout supplierEmailInput;

    private boolean productHasChanged = false;

    // OnTouchListener that listens for any user touches on a View, implying that they are modifying
    // the view, and we change the mPetHasChanged boolean to true.
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            productHasChanged = true;
            return false;
        }
    };

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

            LoaderManager.getInstance(this).initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        productNameEditText = findViewById(R.id.product_name_edit_text);
        productNameInput = findViewById(R.id.product_name_text_input);
        productPriceEditText = findViewById(R.id.product_price_edit_text);
        productPriceInput = findViewById(R.id.product_price_text_input);
        productImageView = findViewById(R.id.product_image);
        productQuantityEditText = findViewById(R.id.quantity_text);
        supplierNameEditText = findViewById(R.id.supplier_name_edit_text);
        supplierNameInput = findViewById(R.id.supplier_name_text_input);
        supplierEmailEditText = findViewById(R.id.supplier_email_edit_text);
        supplierEmailInput = findViewById(R.id.supplier_email_text_input);

        productNameEditText.setOnTouchListener(mTouchListener);
        productPriceEditText.setOnTouchListener(mTouchListener);
        supplierNameEditText.setOnTouchListener(mTouchListener);
        supplierEmailEditText.setOnTouchListener(mTouchListener);


        MaterialButton minusButton = findViewById(R.id.minus_button);
        MaterialButton plusButton = findViewById(R.id.plus_button);

        productImageView.setImageURI(null);
        productImageView.setImageURI(Uri.parse("content://com.android.providers.media.documents/document/image%3A428112"));

        productImageView.setOnClickListener(new View.OnClickListener() {
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

                if (!productHasChanged)
                    productHasChanged = true;
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantityEditText.getText().toString());
                ++quantity;

                productQuantityEditText.setText(String.valueOf(quantity));

                if (!productHasChanged)
                    productHasChanged = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!productHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_dialog_msg));

        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProduct();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        if (currentUri != null) {
            int rowsDeleted = getContentResolver().delete(currentUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_product_successful),
                        Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {
            try {
                imageUri = data.getData().toString();
                Log.i(LOG_TAG, "Picture path: " + imageUri);
                productImageView.setImageURI(/*data.getData()*/Uri.parse(imageUri));

                if (!productHasChanged) {
                    productHasChanged = true;
                }
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Something went wrong while trying to get image path", e);
            }
        }
    }

    private void saveProduct() {

        boolean isAtLeastOneEmpty = false;

        String productName = productNameEditText.getText().toString().trim();
        String productPrice = productPriceEditText.getText().toString().trim();
        String productQuantity = productQuantityEditText.getText().toString().trim();
        String supplierName = supplierNameEditText.getText().toString().trim();
        String supplierEmail = supplierEmailEditText.getText().toString().trim();

        // If it is a new product and there is not even one
        // data specified, finish the activity.
        if (currentUri == null && TextUtils.isEmpty(productName) && TextUtils.isEmpty(productPrice)
                && productQuantity.equals("0") && TextUtils.isEmpty(supplierName)
                && TextUtils.isEmpty(supplierEmail)) {
            finish();
            return;
        }

        if (TextUtils.isEmpty(productName)) {
            isAtLeastOneEmpty = true;
            productNameInput.setError(getString(R.string.error_product_name));
        } else {
            productNameInput.setError(null);
        }

        float price = 0f;
        if (TextUtils.isEmpty(productPrice)) {
            isAtLeastOneEmpty = true;
            productPriceInput.setError(getString(R.string.error_product_price));
        } else {
            productPriceInput.setError(null);
            price = Float.parseFloat(productPrice);
        }

        if (TextUtils.isEmpty(supplierName)) {
            isAtLeastOneEmpty = true;
            supplierNameInput.setError(getString(R.string.error_supplier_name));
        } else {
            supplierNameInput.setError(null);
        }

        if (TextUtils.isEmpty(supplierEmail)) {
            isAtLeastOneEmpty = true;
            supplierEmailInput.setError(getString(R.string.error_supplier_email));
        } else {
            supplierEmailInput.setError(null);
        }

        if (isAtLeastOneEmpty)
            return;

        Integer quantity = Integer.parseInt(productQuantity);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imageUri);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, supplierEmail);

        // Determine if this is a new or existing product by checking if currentUri is null or not
        if (currentUri == null) {
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                ProductEntry._ID, /** Why is ID always in projection */
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_IMAGE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL};

        return new CursorLoader(
                this, currentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.i(LOG_TAG, "onLoadFinished Method");

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }


        Log.i(LOG_TAG, "Before moveToFirst()");

        if (cursor.moveToFirst()) {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int sNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int sEmailColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);

            String productName = cursor.getString(nameColumnIndex);
            String productPrice = String.valueOf(cursor.getFloat(priceColumnIndex));
            String productImage = cursor.getString(imageColumnIndex);
            Uri imageUri = Uri.parse(productImage);
            String productQuantity = String.valueOf(cursor.getInt(quantityColumnIndex));
            String supplierName = cursor.getString(sNameColumnIndex);
            String supplierEmail = cursor.getString(sEmailColumnIndex);

            Log.i(LOG_TAG, "\nIMAGE URI: "+ productImage +"\n");

            productNameEditText.setText(productName);
            productPriceEditText.setText(productPrice);
            productImageView.setImageURI(imageUri);
            productQuantityEditText.setText(productQuantity);
            supplierNameEditText.setText(supplierName);
            supplierEmailEditText.setText(supplierEmail);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        productNameEditText.setText("");
        productPriceEditText.setText("");
        productQuantityEditText.setText(0);
        supplierNameEditText.setText("");
        supplierEmailEditText.setText("");
    }

    @Override
    public void onBackPressed() {
        if (!productHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };

        showUnsavedChangesDialog(discardButtonClickListener);
    }
}
