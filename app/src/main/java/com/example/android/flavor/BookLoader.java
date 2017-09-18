package com.example.android.flavor;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by bahubali on 9/18/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<BookList>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    @Override
    public List<BookList> loadInBackground() {
        //Checking whether URL is null
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parse the response, and extract a list of Books.
        List<BookList> books = QueryUtils.fetchBookListData(mUrl);
        //return the list of books to the loader in the MainActivity
        return books;
    }



}
