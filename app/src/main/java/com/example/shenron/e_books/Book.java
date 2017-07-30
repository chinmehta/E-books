package com.example.shenron.e_books;


public class Book
{
    //Title of the book
    private String mTitle;

    //Author of the book
    private String mAuthor;

    public Book(String title,String author)
    {
        mTitle=title;
        mAuthor=author;
    }

    public String getTitle()
    {
        return mTitle;
    }
    public String getAuthor()
    {
        return mAuthor;
    }
}
