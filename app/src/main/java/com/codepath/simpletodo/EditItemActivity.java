package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
        DateTime date = new DateTime(year, monthOfYear+1, dayOfMonth, 0, 0, 0);
        dueDate.setText(date.toString(ToDoItem.DATE_FORMAT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemId = getIntent().getLongExtra("id", -1);
        position = getIntent().getIntExtra("position", -1);

        String itemText = getIntent().getStringExtra("text");
        input = (EditText) findViewById(R.id.editText);
        input.setText(itemText);

        String dueDateText = getIntent().getStringExtra("date");
        dueDate = (EditText) findViewById(R.id.editDate);
        dueDate.setText(dueDateText);

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
                i.putExtra("date", dueDate.getText().toString());
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
