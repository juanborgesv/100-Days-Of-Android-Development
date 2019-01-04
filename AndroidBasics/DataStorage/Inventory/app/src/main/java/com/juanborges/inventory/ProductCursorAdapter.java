package com.juanborges.inventory;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.button.MaterialButton;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juanborges.inventory.data.ProductContract.ProductEntry;
import com.juanborges.inventory.data.ProductDbHelper;

public class ProductCursorAdapter extends CursorAdapter {
    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageView imageView = view.findViewById(R.id.image);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        MaterialButton saleButton = view.findViewById(R.id.sale);

        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        final int id = cursor.getInt(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        float price = cursor.getFloat(priceColumnIndex);
        String mImageUri = cursor.getString(imageColumnIndex);
        Uri imageUri = Uri.parse(mImageUri);
        final int quantity = cursor.getInt(quantityColumnIndex);

        nameTextView.setText(name);
        priceTextView.setText("$"+ String.valueOf(price));
        imageView.setImageURI(null);
        imageView.setImageURI(imageUri);

        if (quantity > 0) {
            quantityTextView.setText("Quantity available: "+ String.valueOf(quantity));
            quantityTextView.setTextColor(Color.BLACK);
        } else {
            quantityTextView.setText(context.getString(R.string.out_of_stock));
            quantityTextView.setTextColor(Color.RED);
        }

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {

                    Uri currentUri = Uri.withAppendedPath(ProductEntry.CONTENT_URI, String.valueOf(id));

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity-1);

                    context.getContentResolver().update(currentUri, contentValues, null,
                            null);
                } else {
                    Toast.makeText(context, context.getString(R.string.sale_failed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
