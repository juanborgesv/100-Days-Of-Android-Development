package com.juanborges.inventory;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juanborges.inventory.data.ProductContract.ProductEntry;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageView imageView = view.findViewById(R.id.image);
        TextView quantityTextView = view.findViewById(R.id.quantity);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        String name = cursor.getString(nameColumnIndex);
        float price = cursor.getFloat(priceColumnIndex);
        String imageUri = cursor.getString(imageColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);

        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        imageView.setImageURI(Uri.parse(imageUri));
        quantityTextView.setText(String.valueOf(quantity));
    }
}
