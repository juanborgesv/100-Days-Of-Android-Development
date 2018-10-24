package com.juanborges.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;
import com.juanborges.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity/* implements LoaderManager.LoaderCallbacks<Cursor> */{

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private final static int PRODUCT_LOADER = 0;

    ProductCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.list);

        adapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(adapter);

        displayDatabaseInfo();

        //getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the products database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        ProductDbHelper dbHelper = new ProductDbHelper(this);

        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_IMAGE,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL
        };

        Cursor cursor = getContentResolver().query(
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        Log.i(LOG_TAG, ProductEntry._ID + " - " +
                ProductEntry.COLUMN_PRODUCT_NAME + " - " +
                ProductEntry.COLUMN_PRODUCT_PRICE + " - " +
                ProductEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                ProductEntry.COLUMN_PRODUCT_IMAGE + " - " +
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);

        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                float price = cursor.getFloat(priceColumnIndex);
                String imageUri = cursor.getString(imageColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                Log.i(LOG_TAG,
                        currentId + " - " +
                                name + " - " +
                                price + " - " +
                                imageUri + " - " +
                                quantity);
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                Toast.makeText(this, "Delete All", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                break;
        }
        
        
        if (item.getItemId() == R.id.action_delete_all) {
            // TODO: Delete all data in the database.
            Toast.makeText(this, "Delete All", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertProduct() {
        ProductDbHelper dbHelper = new ProductDbHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, "BLU R1 HD");
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, 9.99f);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_IMAGE, "content://com.android.providers.media.documents/document/image%3A419678");
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 2);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Lucas");
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, "lucas59@gmail.com");

        long newRowId = database.insert(ProductEntry.TABLE_NAME, null, contentValues);
    }


   /* @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY
        };

        return new CursorLoader(
                this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }*/
}
