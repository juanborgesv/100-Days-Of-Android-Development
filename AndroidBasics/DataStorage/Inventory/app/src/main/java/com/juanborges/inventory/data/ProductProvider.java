package com.juanborges.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;

public class ProductProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ProductProvider.class.getSimpleName();

    private ProductDbHelper dbHelper;

    /**
     * URI matcher code for the content URI for the products table
     */
    private static final int PRODUCTS = 100;

    /**
     * URI matcher code for the content URI for a single product in the products table
     */
    private static final int PRODUCT_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCTS, PRODUCTS);

        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCTS + "/#", PRODUCT_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        dbHelper = new ProductDbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                cursor = database.query(
                        ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(
                        ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a product into the database with the given content values.
     * Return the new content URI for that specific row in the database.
     */
    private Uri insertProduct(Uri uri, ContentValues contentValues) {

        // Data validation
        String productName = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
        Float productPrice = contentValues.getAsFloat(ProductEntry.COLUMN_PRODUCT_PRICE);
        String productImage = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_IMAGE);
        Integer productQuantity = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        String supplierName = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        String supplierEmail = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);

        if (productName == null)
            throw new IllegalArgumentException("Product requires a name");
        if (productPrice == null || productPrice < 0)
            throw new IllegalArgumentException("Product requires a valid price");
        if (productImage == null)
            throw new IllegalArgumentException("Product requires an image");
        if (productQuantity == null)
            throw new IllegalArgumentException("Product requires a valid quantity");
        if (supplierName == null)
            throw new IllegalArgumentException("Product requires a supplier name");
        if (supplierEmail == null)
            throw new IllegalArgumentException("Product requires a supplier email");

        // Get writable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long id = database.insert(ProductEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update products in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more products).
     * Return the number of rows that were successfully updated.
     */
    private int updateProduct(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if (contentValues.size() == 0)
            return 0;

        boolean isSomethingNull = false;

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_NAME)) {
            String productName = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
            if (productName == null)
                isSomethingNull = true;
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_PRICE)) {
            Float productPrice = contentValues.getAsFloat(ProductEntry.COLUMN_PRODUCT_PRICE);
            if (productPrice == null || productPrice < 0)
                isSomethingNull = true;
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_IMAGE)) {
            String productImage = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_IMAGE);
            if (productImage == null)
                isSomethingNull = true;
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_QUANTITY)) {
            Integer productQuantity = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            if (productQuantity == null)
                isSomethingNull = true;
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME)) {
            String supplierName = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            if (supplierName == null)
                isSomethingNull = true;

        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL)) {
            String supplierEmail = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            if (supplierEmail == null)
                isSomethingNull = true;
        }

        // If there is only one value expected to change, it means it is changing the quantity
        // from the MainActivity. Set isSomethingNull to false to avoid null data errors.
        if (contentValues.size() == 1) {
            isSomethingNull = false;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rowsUpdated = 0;

        if (!isSomethingNull) {
            rowsUpdated = database.update(ProductEntry.TABLE_NAME, contentValues, selection,
                    selectionArgs);

            if (rowsUpdated != 0)
                getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Toast.makeText(getContext(), "Error updating. Check there is not null data.",
                    Toast.LENGTH_LONG).show();

            rowsUpdated = -1;
        }

        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rowsDeleted = 0;

        final int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI. ??
     */
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}