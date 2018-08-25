package com.juanborges.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

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
}
