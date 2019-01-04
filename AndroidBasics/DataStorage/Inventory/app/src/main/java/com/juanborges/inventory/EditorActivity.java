package com.juanborges.inventory;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;

/**
 * Allows user to store a new product or edit an existing one in the database.
 */
public class EditorActivity
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String LOG_TAG = EditorActivity.class.getSimpleName();

    static final int PICK_IMAGE = 1;

    // Identifier for the product loader.
    static final int EXISTING_PRODUCT_LOADER = 0;

    // Current URI for the existing product (null if it is a new product).
    private Uri currentUri;

    // EditText and Layout to enter product's name and price.
    private TextInputEditText productNameEditText;
    private TextInputLayout productNameInput;
    private TextInputEditText productPriceEditText;
    private TextInputLayout productPriceInput;

    // ImageView that displays an stored image in gallery and the Uri that it is related.
    private ImageView productImageView;
    private String imageUri;

    // TextView to modify
    private TextView productQuantityEditText;

    // EditText and Layout to enter supplier's name and email.
    private TextInputEditText supplierNameEditText;
    private TextInputLayout supplierNameInput;
    private TextInputEditText supplierEmailEditText;
    private TextInputLayout supplierEmailInput;

    private boolean productHasChanged = false;

    //TODO: Fix warning (MaterilaButton -> local variable)
    private MaterialButton orderMoreButton;

    // OnTouchListener that listens for any user touches on a View, implying that they are modifying
    // the view, and we change the productHasChanged boolean to true.
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

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new product or editing an existing one.
        Intent intent = getIntent();
        currentUri = intent.getData();

        // If the intent DOES NOT contain a product content URI, change the app bar to say
        // "Add a Product" and hide "Delete" option menu. If it results to be an existing
        // product, kick off the loader to get its data.
        if (currentUri == null) {
            setTitle(R.string.editor_activity_title_new_product);
            invalidateOptionsMenu();
        } else {
            setTitle(R.string.editor_activity_title_edit_product);
            LoaderManager.getInstance(this).initLoader
                    (EXISTING_PRODUCT_LOADER, null, this);
        }

        // Find all relevant views needed to read user input.
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
        orderMoreButton = findViewById(R.id.order_button);
        MaterialButton minusButton = findViewById(R.id.minus_button);
        MaterialButton plusButton = findViewById(R.id.plus_button);

        productNameEditText.setOnTouchListener(mTouchListener);
        productPriceEditText.setOnTouchListener(mTouchListener);
        supplierNameEditText.setOnTouchListener(mTouchListener);
        supplierEmailEditText.setOnTouchListener(mTouchListener);

        productImageView.setImageURI(null);

        //TODO: Test and change setImageURI
        //productImageView.setImageURI(Uri.parse("content://com.android.providers.media.documents/document/image%3A428112"));

        // Opens the gallery in order to select an image
        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        // Decrease current quantity in 1 unit if it is greater than 0.
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

        // Increase current quantity.
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

        // Setup OnClickListener, send an email if there is supplier data.
        orderMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSupplierSpecified()) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setDataAndType(
                            Uri.parse("mailto:"+ supplierEmailEditText.getText().toString().trim()),
                            "text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "New order - "+
                            productNameEditText.getText().toString().trim());

                    String message = "Hi " + supplierNameEditText.getText().toString().trim() +
                            ",\n\n" + "We need more " +
                            productNameEditText.getText().toString().trim() +
                            ", please confirm if you have this product available and when you" +
                            " could send more to us. \n\n Thank you.";

                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditorActivity.this,
                            getString(R.string.supplier_missing_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check supplier name and email fields to see if all the supplier info required is specified.
    private boolean isSupplierSpecified() {
        String supplierName = supplierNameEditText.getText().toString();
        String supplierEmail = supplierEmailEditText.getText().toString();

        if (supplierName == null || supplierName.length() == 0) {
            return false;
        }
        if (supplierEmail == null || supplierEmail.length() == 0) {
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        //If the product has not changed, go back on Back Pressed.
        if (!productHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that
        // changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };

        // Show a dialog that notifies the user they have unsaved changes.
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // If this is a new product, hide the "Delete" menu item.
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
                // Save product to database.
                saveProduct();
                return true;
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                // If the product hasn't changed, continue with navigating up to parent activity.
                if (!productHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
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
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_dialog_msg));

        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the product in the database.
     */
    private void deleteProduct() {
        // Only perform the delete if this is an existing product.
        if (currentUri != null) {
            // Call the ContentResolver to delete the product at the given content URI.
            // Pass in null for the selection and selection args because the currentUri
            // content URI already identifies the product that we want.
            int rowsDeleted = getContentResolver().delete(currentUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
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
                // Get Uri from the Intent and show it on the ImageView
                imageUri = data.getData().toString();
                productImageView.setImageURI(data.getData()/*Uri.parse(imageUri)*/);

                if (!productHasChanged && imageUri != null) {
                    productHasChanged = true;
                }
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Something went wrong while trying to get image path", e);
            }
        }
    }

    /**
     * Get user input from editor and save product into database.
     */
    private void saveProduct() {

        boolean isAtLeastOneEmpty = false;

        // Read from input fields.
        String productName = productNameEditText.getText().toString().trim();
        String productPrice = productPriceEditText.getText().toString().trim();
        String productQuantity = productQuantityEditText.getText().toString().trim();
        String supplierName = supplierNameEditText.getText().toString().trim();
        String supplierEmail = supplierEmailEditText.getText().toString().trim();
        // Trim used to eliminate leading or trailing white space.

        // If it is a new product and there is not even one data specified, finish the activity.
        if (currentUri == null && TextUtils.isEmpty(productName) && TextUtils.isEmpty(productPrice)
                && productQuantity.equals("0") && TextUtils.isEmpty(supplierName)
                && TextUtils.isEmpty(supplierEmail)) {
            finish();
            return;
        }

        // Display a message if the product's name field is empty.
        if (TextUtils.isEmpty(productName)) {
            isAtLeastOneEmpty = true;
            productNameInput.setError(getString(R.string.error_product_name));
        } else {
            productNameInput.setError(null);
        }

        float price = 0f;
        // Display a message if the product's price field is empty.
        if (TextUtils.isEmpty(productPrice)) {
            isAtLeastOneEmpty = true;
            productPriceInput.setError(getString(R.string.error_product_price));
        } else {
            productPriceInput.setError(null);
            price = Float.parseFloat(productPrice);
        }

        // Display a message if the supplier's name field is empty.
        if (TextUtils.isEmpty(supplierName)) {
            isAtLeastOneEmpty = true;
            supplierNameInput.setError(getString(R.string.error_supplier_name));
        } else {
            supplierNameInput.setError(null);
        }

        // Display a message if the supplier's email field is empty.
        if (TextUtils.isEmpty(supplierEmail)) {
            isAtLeastOneEmpty = true;
            supplierEmailInput.setError(getString(R.string.error_supplier_email));
        } else {
            supplierEmailInput.setError(null);
        }

        // Show a Toast in case imageUri is null.
        if (TextUtils.isEmpty(imageUri)) {
            isAtLeastOneEmpty = true;
            Toast.makeText(this, getString(R.string.error_product_image),
                    Toast.LENGTH_SHORT).show();
        }

        // If there is at least one empty field, return. (Don't save)
        if (isAtLeastOneEmpty)
            return;

        Integer quantity = Integer.parseInt(productQuantity);

        // Create a ContentValues object where column names are the keys,
        // and product attributes from the editor are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imageUri);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, supplierEmail);

        // Determine if this is a new or existing product by checking if currentUri is null or not.
        // Insert or update depending on currentUri's value.
        if (currentUri == null) {
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_product_successful),
                        Toast.LENGTH_SHORT).show();
            }

            finish();
        } else {
            int rowsAffected = getContentResolver().update(currentUri, contentValues, null,
                    null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "Error Updating", Toast.LENGTH_SHORT).show();
            }

            if (rowsAffected != -1)
                finish();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                ProductEntry._ID,
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
        // Return if the cursor has no data to be loaded.
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int sNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int sEmailColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);

            // Extract out the value from the Cursor for the given column index
            String productName = cursor.getString(nameColumnIndex);
            String productPrice = String.valueOf(cursor.getFloat(priceColumnIndex));
            imageUri = cursor.getString(imageColumnIndex);
            Uri productImage = Uri.parse(imageUri);
            String productQuantity = String.valueOf(cursor.getInt(quantityColumnIndex));
            String supplierName = cursor.getString(sNameColumnIndex);
            String supplierEmail = cursor.getString(sEmailColumnIndex);

            Log.i(LOG_TAG, "\nIMAGE URI: "+ productImage +"\n");

            // Update the views on the screen with the values from the database
            productNameEditText.setText(productName);
            productPriceEditText.setText(productPrice);
            productImageView.setImageURI(productImage);
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
}
