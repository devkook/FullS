package com.diginori.fulls;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends Activity {


    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvivity_list);
    }
}
