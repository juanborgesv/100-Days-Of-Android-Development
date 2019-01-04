package com.juanborges.inventory;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;

/**
 * Displays list of products that were entered and stored in the app.
 */
public class MainActivity
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    // Identifier for the inventory data loader
    private final static int PRODUCT_LOADER = 0;

    private static final int MY_PERMISSIONS_REQUEST = 1;

    // Adapter for the ListView
    ProductCursorAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int wesPermissionCheck = ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int resPermissionCheck = ContextCompat.checkSelfPermission
                (this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionCheck = ContextCompat.checkSelfPermission
                (this, Manifest.permission.CAMERA);

        if (wesPermissionCheck != PackageManager.PERMISSION_GRANTED ||
                resPermissionCheck != PackageManager.PERMISSION_GRANTED ||
                cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {

            Log.i(LOG_TAG, "Requesting permissions...");
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS, MY_PERMISSIONS_REQUEST);
        } else {
            Log.i(LOG_TAG, "Every permission required are already is granted");
        }

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Setup ListView and its adapter to create a list a populate it with product data.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        listView = findViewById(R.id.list);
        adapter = new ProductCursorAdapter(this, null);
        listView.setAdapter(adapter);

        // Setup the item click listener that will open the EditorActivity and send data through
        // the intent that will determine which product will be loaded.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                Uri currentUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, l);

                intent.setData(currentUri);

                startActivity(intent);
            }
        });

        // Setup a view that will be displayed when there is no data to be shown in the ListView.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        // Kick off the loader
        LoaderManager.getInstance(this).initLoader(PRODUCT_LOADER, null, this);
    }

    /**
     * Inflate the menu options from the res/menu/menu_main.xml file.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles the option selected in the m
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all) {
            if (adapter.getCount() != 0)
                showDeleteConfirmationDialog();
            else
                Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.main_delete_dialog_msg));

        builder.setPositiveButton(getString(R.string.delete_all), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked the "Delete" button, so delete the product.
                deleteAllProducts();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked the "Cancel" button, so dismiss the dialog and continue editing
                // the product.
                if (dialogInterface != null)
                    dialogInterface.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Delete all products data in the database.
     */
    private void deleteAllProducts() {
        getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define which columns from the database we want to get.
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_IMAGE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
        };

        // This loader will execute the ContentProvider's query method on a background thread.
        return new CursorLoader(
                this,               // Parent activity context.
                ProductEntry.CONTENT_URI,   // Provider content URI to query.
                projection,                 // Columns to include in the resulting Cursor.
                null,               // No selection clause.
                null,           // No selection args.
                null);              // Default sort order.
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        // Update the adapter with a new Cursor containing updated product data.
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted.
        adapter.swapCursor(null);
    }
}
