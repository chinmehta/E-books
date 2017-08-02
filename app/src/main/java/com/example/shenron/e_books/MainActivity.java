package com.example.shenron.e_books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    EditText mEditText;
    Button searchButton;
    ListView mListView;
    BookAdapter mAdapter;
    TextView mTextView;
    int BOOK_LOADER_ID=0;
    String url;
    LoaderManager loaderManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button)findViewById(R.id.search_button);
        mEditText = (EditText)findViewById(R.id.edit_text_view);
        mListView = (ListView)findViewById(R.id.list);
        mAdapter = new BookAdapter(this,new ArrayList<Book>());
        mTextView = (TextView)findViewById(R.id.empty_list_view);

        mListView.setAdapter(mAdapter);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url= getURL();
                mAdapter.clear();
                connect();

            }
        });

    }

    private void connect()
    {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
//            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

            loaderManager.restartLoader(BOOK_LOADER_ID,null,this);
//            QueryUtils.fetchBookData(url);
        } else {

            // Update empty state with no connection error message
            mTextView.setText("NO INTERNET CONNECTION");
        }
    }



    private String getURL()
    {
        String keyword="";
        if(!mEditText.getText().toString().equals(""))
        {
            keyword = mEditText.getText().toString();
            Toast.makeText(MainActivity.this, "Search in process ..", Toast.LENGTH_SHORT).show();
            return  "https://www.googleapis.com/books/v1/volumes?"+"q=" + keyword;
        }
        else {
            mTextView.setText("NO RESULT FOUND");
            mTextView.setVisibility(View.VISIBLE);
        }
        return keyword;
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> BOOKS) {
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if ( BOOKS!= null && !BOOKS.isEmpty()) {
            mAdapter.addAll(BOOKS);
//            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();

    }
}
