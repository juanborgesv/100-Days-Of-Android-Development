package com.juanborges.inventory;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;
import com.juanborges.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = MainActivity.class.getSimpleName();

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

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProductEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            Log.i(LOG_TAG, "Rows in database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
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


}
