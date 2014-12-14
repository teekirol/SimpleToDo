package com.codepath.simpletodo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class EditItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        int itemPosition = getIntent().getIntExtra("position", -1);
        String itemText = getIntent().getStringExtra("text");
    }

}
