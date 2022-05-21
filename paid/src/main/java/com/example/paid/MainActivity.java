package com.example.paid;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        int index=0;
        ViewListFragment f = ViewListFragment.newInstance(index);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mylist, f);
        ft.commit();

    }
}
