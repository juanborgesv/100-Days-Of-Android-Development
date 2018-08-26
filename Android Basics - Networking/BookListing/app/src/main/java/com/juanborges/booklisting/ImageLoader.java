/*package com.juanborges.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

public class ImageLoader extends AsyncTaskLoader<Bitmap> {

    View myView;
    Book myBook;

    public ImageLoader(Context context, View convertView, Book currentBook) {
        super(context);
        myView = convertView;
        myBook = currentBook;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {
        BookAdapter.setImage(myView, myBook);
    }
}
*/