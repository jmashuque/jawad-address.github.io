package com.example.paid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.ListFragment;

public class ViewListFragment extends ListFragment {


    public static ViewListFragment newInstance(int index) {

        ViewListFragment f = new ViewListFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.layout_viewlist, container, false);

        return v;

    }

    class LoadProducts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            return null;
        }

        protected void onPostExecute(String file_url) {

        }

    }
}

