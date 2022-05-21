package com.example.paid;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.HashSet;

public class MyBookContentProvider extends ContentProvider {

    private DatabaseHandler database;

    private static final int BOOKS = 10;
    private static final int BOOKS_ID = 20;

    private static final String AUTHORITY = "com.example.books";

    private static final String BASE_PATH = "books";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/books";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/book";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, BOOKS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", BOOKS_ID);
    }

    @Override
    public boolean onCreate() {

        database = new DatabaseHandler(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(BookTableHandler.TABLE_BOOK);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {

            case BOOKS:

                break;

            case BOOKS_ID:

                // Adding the ID to the original query
                queryBuilder.appendWhere(BookTableHandler.COLUMN_ID + "=" + uri.getLastPathSegment());

                break;

            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();

        long id = 0;

        switch (uriType) {

            case BOOKS:

                id = sqlDB.insert(BookTableHandler.TABLE_BOOK, null, values);
                break;

            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {

            case BOOKS:

                rowsDeleted = sqlDB.delete(BookTableHandler.TABLE_BOOK, selection, selectionArgs);

                break;
            case BOOKS_ID:

                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {

                    rowsDeleted = sqlDB.delete(BookTableHandler.TABLE_BOOK, BookTableHandler.COLUMN_ID + "=" + id, null);
                }

                else {

                    rowsDeleted = sqlDB.delete(BookTableHandler.TABLE_BOOK, BookTableHandler.COLUMN_ID + "=" + id
                            + " and " + selection, selectionArgs);
                }

                break;

            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {

            case BOOKS:

                rowsUpdated = sqlDB.update(BookTableHandler.TABLE_BOOK, values, selection, selectionArgs);

                break;

            case BOOKS_ID:

                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {

                    rowsUpdated = sqlDB.update(BookTableHandler.TABLE_BOOK, values,
                            BookTableHandler.COLUMN_ID + "=" + id, null);
                }
                else {

                    rowsUpdated = sqlDB.update(BookTableHandler.TABLE_BOOK,
                            values, BookTableHandler.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }

                break;

            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { BookTableHandler.COLUMN_GENDER,
                BookTableHandler.COLUMN_FIRST,
                BookTableHandler.COLUMN_LAST,
                BookTableHandler.COLUMN_ADDRESS,
                BookTableHandler.COLUMN_PROVINCE,
                BookTableHandler.COLUMN_COUNTRY,
                BookTableHandler.COLUMN_POSTAL,
                BookTableHandler.COLUMN_ID };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
