package com.example.paid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    static final String LOG_TAG = "MYSIGNATURE";

    private Spinner spinner_gender;
    private EditText edit_first;
    private EditText edit_last;
    private EditText edit_address;
    private Spinner spinner_province;
    private EditText edit_country;
    private EditText edit_postal;

    private Uri bookUri;

    @Override
    protected void onCreate(Bundle bundle) {

        Log.i(LOG_TAG, "Jawad Mashuque #154995161");

        super.onCreate(bundle);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.layout_add);

        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance
        bookUri = (bundle == null) ? null : (Uri) bundle.getParcelable(MyBookContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            bookUri = extras.getParcelable(MyBookContentProvider.CONTENT_ITEM_TYPE);
            fillData(bookUri);
        }

        Button btnSubmit = (Button) findViewById(R.id.form_btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (submitCheck() == true) {

                    setResult(RESULT_OK);

                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.menu_insert).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;

        switch (item.getItemId()) {

            case R.id.menu_clear:

                FunctionClear(new View(this));

                break;

            case R.id.menu_about:

                i = new Intent(this, AboutActivity.class);
                startActivity(i);

                break;

            case R.id.menu_exit:

                this.FunctionExit();

                break;
        }
        return true;
    }

    public void FunctionExit() {

        finish();
        System.exit(0);
    }

    public void FunctionClear(View v) {

        edit_first = (EditText) findViewById(R.id.form_edit_first);
        edit_first.setText("");

        edit_last = (EditText) findViewById(R.id.form_edit_last);
        edit_last.setText("");

        edit_address = (EditText) findViewById(R.id.form_edit_address);
        edit_address.setText("");

        edit_country = (EditText) findViewById(R.id.form_edit_country);
        edit_country.setText("");

        edit_postal = (EditText) findViewById(R.id.form_edit_postal);
        edit_postal.setText("");
    }

    public boolean submitCheck() {

//        Intent i = new Intent(this, ViewActivity.class);

        spinner_gender = (Spinner) findViewById(R.id.form_spin_gender);

        edit_first = (EditText) findViewById(R.id.form_edit_first);
        String editValue = edit_first.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type first name", Toast.LENGTH_LONG).show();
            return false;
        }

        edit_last = (EditText) findViewById(R.id.form_edit_last);
        editValue = edit_last.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type last name", Toast.LENGTH_LONG).show();
            return false;
        }

        edit_address = (EditText) findViewById(R.id.form_edit_address);
        editValue = edit_address.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type address", Toast.LENGTH_LONG).show();
            return false;
        }

        spinner_province = (Spinner) findViewById(R.id.form_spin_province);

        edit_country = (EditText) findViewById(R.id.form_edit_country);
        editValue = edit_country.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type country", Toast.LENGTH_LONG).show();
            return false;
        }

        edit_postal = (EditText) findViewById(R.id.form_edit_postal);
        editValue = edit_postal.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type postal code", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

//        startActivity(i);
//        fillData(bookUri);
    }

    private void fillData(Uri uri) {

        String[] projection = { BookTableHandler.COLUMN_GENDER,
                                BookTableHandler.COLUMN_FIRST,
                                BookTableHandler.COLUMN_LAST,
                                BookTableHandler.COLUMN_ADDRESS,
                                BookTableHandler.COLUMN_PROVINCE,
                                BookTableHandler.COLUMN_COUNTRY,
                                BookTableHandler.COLUMN_POSTAL };

        Log.i(LOG_TAG, "REACHED1");

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            Log.i(LOG_TAG, "REACHED2");

            String gender = cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_GENDER));
            spinner_gender = (Spinner) findViewById(R.id.form_spin_gender);

            for (int i = 0; i < spinner_gender.getCount(); i++) {

                String s = (String) spinner_gender.getItemAtPosition(i);
                if (s.equalsIgnoreCase(gender)) {
                    spinner_gender.setSelection(i);
                }
            }

            Log.i(LOG_TAG, "REACHED3");

            String province = cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_PROVINCE));
            spinner_province = (Spinner) findViewById(R.id.form_spin_province);

            for (int i = 0; i < spinner_province.getCount(); i++) {

                String s = (String) spinner_province.getItemAtPosition(i);
                if (s.equalsIgnoreCase(province)) {
                    spinner_province.setSelection(i);
                }
            }

            Log.i(LOG_TAG, "REACHED4");

            edit_first = (EditText) findViewById(R.id.form_edit_first);
            edit_last = (EditText) findViewById(R.id.form_edit_last);
            edit_address = (EditText) findViewById(R.id.form_edit_address);
            edit_country = (EditText) findViewById(R.id.form_edit_country);
            edit_postal = (EditText) findViewById(R.id.form_edit_postal);

            edit_first.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_FIRST)));
            edit_last.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_LAST)));
            edit_address.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_ADDRESS)));
            edit_country.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_COUNTRY)));
            edit_postal.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableHandler.COLUMN_POSTAL)));

            Log.i(LOG_TAG, "REACHED5");

            // Always close the cursor
            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(MyBookContentProvider.CONTENT_ITEM_TYPE, bookUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {

        Log.i(LOG_TAG, "REACHEDA");

        String gender = (String) spinner_gender.getSelectedItem().toString();
        String first = edit_first.getText().toString();
        String last = edit_last.getText().toString();
        String address = (String) edit_address.getText().toString();
        String province = (String) spinner_province.getSelectedItem().toString();
        String country = (String) edit_country.getText().toString();
        String postal = (String) edit_postal.getText().toString();

        if (submitCheck() == false) {

            return;
        }

        Log.i(LOG_TAG, gender + " " + first + " " + last + " " + address + " " + province + " " + country + " " + postal);

        // Only save if either summary or description
        // is available

//        if (description.length() == 0 && summary.length() == 0) {
//            return;
//        }

        Log.i(LOG_TAG, "REACHEDB");

        ContentValues values = new ContentValues();
        values.put(BookTableHandler.COLUMN_GENDER, gender);
        values.put(BookTableHandler.COLUMN_FIRST, first);
        values.put(BookTableHandler.COLUMN_LAST, last);
        values.put(BookTableHandler.COLUMN_ADDRESS, address);
        values.put(BookTableHandler.COLUMN_PROVINCE, province);
        values.put(BookTableHandler.COLUMN_COUNTRY, country);
        values.put(BookTableHandler.COLUMN_POSTAL, postal);

        Log.i(LOG_TAG, "REACHEDC");

        if (bookUri == null) {

            bookUri = getContentResolver().insert(MyBookContentProvider.CONTENT_URI, values);
        }

        else {

            getContentResolver().update(bookUri, values, null, null);
        }

        Log.i(LOG_TAG, "REACHEDD");
    }

//    private void makeToast() {
//        Toast.makeText(ToDoDetailActivity.this, "Please maintain a summary",Toast.LENGTH_LONG).show();
//    }
}
