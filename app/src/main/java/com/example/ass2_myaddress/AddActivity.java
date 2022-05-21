package com.example.ass2_myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    static final String LOG_TAG = "MYSIGNATURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "Jawad Mashuque #154995161");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_about:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;

            case R.id.menu_clear:
                this.FunctionClear(new View(this));
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

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.form_radio_group);
        radioGroup.clearCheck();

        EditText editText = (EditText) findViewById(R.id.form_edit_first);
        editText.setText("");

        editText = (EditText) findViewById(R.id.form_edit_last);
        editText.setText("");

        editText = (EditText) findViewById(R.id.form_edit_address);
        editText.setText("");

        editText = (EditText) findViewById(R.id.form_edit_country);
        editText.setText("");

        editText = (EditText) findViewById(R.id.form_edit_postal);
        editText.setText("");
    }

    public void submitCheck(View v) {

        Intent i = new Intent(this, ViewActivity.class);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.form_radio_group);
        RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        if (radioGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this, "Select a gender", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_GENDER", radioButton.getText().toString());

        EditText editText = (EditText) findViewById(R.id.form_edit_first);
        String editValue = editText.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type first name", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_FIRST", editValue);

        editText = (EditText) findViewById(R.id.form_edit_last);
        editValue = editText.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type last name", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_LAST", editValue);

        editText = (EditText) findViewById(R.id.form_edit_address);
        editValue = editText.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type address", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_ADDRESS", editValue);

        Spinner spinner = (Spinner) findViewById(R.id.form_spin_province);
        i.putExtra("VALUE_PROVINCE", spinner.getSelectedItem().toString());

        editText = (EditText) findViewById(R.id.form_edit_country);
        editValue = editText.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type country", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_COUNTRY", editValue);

        editText = (EditText) findViewById(R.id.form_edit_postal);
        editValue = editText.getText().toString();

        if (TextUtils.isEmpty(editValue)) {

            Toast.makeText(this, "Type postal code", Toast.LENGTH_LONG).show();
            return;
        }

        i.putExtra("VALUE_POSTAL", editValue);

        startActivity(i);
    }
}
