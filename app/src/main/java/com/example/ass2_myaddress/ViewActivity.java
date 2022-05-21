package com.example.ass2_myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {

    static final String LOG_TAG = "MYSIGNATURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "Jawad Mashuque #154995161");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view);

        showInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.menu_clear).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_about:
                Intent i = new Intent(this, AboutActivity.class);
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

    public void showInfo() {

        Intent i = getIntent();

        TextView textView = (TextView) findViewById(R.id.view_gender_value);
        textView.setText(i.getStringExtra("VALUE_GENDER"));

        textView = (TextView) findViewById(R.id.view_first_value);
        textView.setText(i.getStringExtra("VALUE_FIRST"));

        textView = (TextView) findViewById(R.id.view_last_value);
        textView.setText(i.getStringExtra("VALUE_LAST"));

        textView = (TextView) findViewById(R.id.view_address_value);
        textView.setText(i.getStringExtra("VALUE_ADDRESS"));

        textView = (TextView) findViewById(R.id.view_province_value);
        textView.setText(i.getStringExtra("VALUE_PROVINCE"));

        textView = (TextView) findViewById(R.id.view_country_value);
        textView.setText(i.getStringExtra("VALUE_COUNTRY"));

        textView = (TextView) findViewById(R.id.view_postal_value);
        textView.setText(i.getStringExtra("VALUE_POSTAL"));
    }
}
