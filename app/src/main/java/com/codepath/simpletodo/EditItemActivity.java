package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends Activity {

    EditText input;
    Button submit;
    Button cancel;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemPosition = getIntent().getIntExtra("position", -1);
        String itemText = getIntent().getStringExtra("text");
        input = (EditText) findViewById(R.id.editText);
        input.setText(itemText);

        // Set the cursor at the end of the line
        input.setSelection(itemText.length());
        submit = (Button) findViewById(R.id.editItemSave);
        cancel = (Button) findViewById(R.id.cancelButton);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("text", input.getText().toString());
                setResult(itemPosition, i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(-1, i);
                finish();
            }
        });
    }

}
