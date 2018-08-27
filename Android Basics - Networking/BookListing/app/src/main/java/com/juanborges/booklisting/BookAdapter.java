package com.juanborges.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> /*implements LoaderManager.LoaderCallbacks<Bitmap>*/{

    static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());

        TextView year = (TextView) convertView.findViewById(R.id.date);
        year.setText(currentBook.getYear());

        return convertView;
    }

    static URL createUrl(String myUrl) {
        URL url = null;

        try {
            url = new URL(myUrl);
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG, "Malformed Url", e);
        }

        return url;
    }

    public static void setImage(View convertView, Book currentBook) {
        try{
            URL imageUrl = createUrl(currentBook.getImageUrl());
            Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            ImageView image = convertView.findViewById(R.id.image);
            image.setImageBitmap(bmp);
        } catch (IOException e) {
            Log.e(LOG_TAG, "I/O Exception when trying to load the image of the current book");
        }
    }

  /*  @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new ImageLoader(getContext(), );
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {

    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }*/
}
