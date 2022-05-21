package com.example.ass2_myaddress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
    }

    public void closeOk(View v) {

        finish();
    }
}
