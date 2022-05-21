package com.example.paid;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String LOG_TAG = "MYSIGNATURE";

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "Jawad Mashuque #154995161");

        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.layout_viewlist);

        this.getListView().setDividerHeight(2);

        fillData();

        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.menu_clear).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;

        switch (item.getItemId()) {

            case R.id.menu_insert:

                createTask();

                return true;

            case R.id.menu_about:

                i = new Intent(this, AboutActivity.class);
                startActivity(i);

                break;

            case R.id.menu_exit:

                this.FunctionExit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case DELETE_ID:

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(MyBookContentProvider.CONTENT_URI + "/" + info.id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void FunctionExit() {

        finish();
        System.exit(0);
    }

    private void createTask() {

        Intent i = new Intent(this, AddActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, AddActivity.class);
        Uri bookUri = Uri.parse(MyBookContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(MyBookContentProvider.CONTENT_ITEM_TYPE, bookUri);

        // Activity returns an result if called with startActivityForResult
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { BookTableHandler.COLUMN_FIRST };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.layout_row, null, from, to, 0);

        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.list_delete);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = { BookTableHandler.COLUMN_ID, BookTableHandler.COLUMN_FIRST };
        CursorLoader cursorLoader = new CursorLoader(this, MyBookContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapter.swapCursor(null);
    }
}
