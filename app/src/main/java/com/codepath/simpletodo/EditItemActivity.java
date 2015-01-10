package com.codepath.simpletodo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import org.joda.time.DateTime;

public class EditItemActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    EditText input;
    EditText dueDate;
    Button submit;
    Button cancel;
    long itemId;
    int position;

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dueDate.setText(new DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0).toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemId = getIntent().getLongExtra("id", -1);
        position = getIntent().getIntExtra("position", -1);

        dueDate = (EditText) findViewById(R.id.editDate);

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
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialogFragment datepicker = new DatePickerDialogFragment();
                datepicker.show(fm, "date_picker");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("id", itemId);
                i.putExtra("text", input.getText().toString());
                i.putExtra("position", position);
                setResult(ToDoActivity.SUCCESS, i);
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
