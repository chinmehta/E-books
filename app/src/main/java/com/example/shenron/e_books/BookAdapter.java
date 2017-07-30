package com.example.shenron.e_books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book>
{
    public BookAdapter(Context context, List<Book> books)
    {
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //get current position
        Book currentBook = getItem(position);

        TextView titleTextView = (TextView)listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = (TextView)listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentBook.getAuthor());

        return listItemView;

    }
}
