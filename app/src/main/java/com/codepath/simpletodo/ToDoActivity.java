package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import com.codepath.simpletodo.ToDoContract.ToDoEntry;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ToDoActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{

    ArrayList<ToDoItem> items;
    ToDoAdapter itemsAdapter;
    ListView lvItems;
    EditText dueDate;

    static final int SUCCESS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ToDoAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        dueDate = (EditText) findViewById(R.id.etDueDate);
        setupListViewListener();
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime date = new DateTime(year, monthOfYear+1, dayOfMonth, 0, 0, 0);
        dueDate.setText(date.toString(ToDoItem.DATE_FORMAT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        DateTime d = null;
        if(!dueDate.getText().toString().isEmpty()) {
            d = DateTime.parse(dueDate.getText().toString(), DateTimeFormat.forPattern(ToDoItem.DATE_FORMAT));
        }
        long itemId = insert(itemText, d);
        itemsAdapter.add(new ToDoItem(itemId, itemText, d));
        etNewItem.setText("");
        dueDate.setText("");
    }

    private void setupListViewListener() {
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialogFragment datepicker = new DatePickerDialogFragment();
                datepicker.show(fm, "date_picker");
            }
        });
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                markCompleted(items.get(pos));
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra("position", pos);
                i.putExtra("id", items.get(pos).getId());
                i.putExtra("text", items.get(pos).getText());
                i.putExtra("date", items.get(pos).getDueDate());
                startActivityForResult(i, SUCCESS);
            }
        });
    }

    private void readItems() {
          String[] projection = {
                  ToDoEntry.COLUMN_NAME_ID,
                  ToDoEntry.COLUMN_NAME_TEXT,
                  ToDoEntry.COLUMN_NAME_DUE_DATE
          };
          String selection = ToDoEntry.COLUMN_NAME_COMPLETED + " = 0";
          String sortOrder = ToDoEntry.COLUMN_NAME_ID + " ASC";
          ToDoDbHelper dbHelper = new ToDoDbHelper(getApplicationContext());
          Cursor cursor = dbHelper.getReadableDatabase().query(
                  ToDoEntry.TABLE_NAME,
                  projection,
                  selection,
                  null,
                  null,
                  null,
                  sortOrder
          );
          cursor.moveToFirst();
          items = new ArrayList<ToDoItem>();
          while(!cursor.isAfterLast() && cursor.getCount() > 0) {
              int itemId = cursor.getInt(cursor.getColumnIndex(ToDoEntry.COLUMN_NAME_ID));
              String itemText = cursor.getString(cursor.getColumnIndex(ToDoEntry.COLUMN_NAME_TEXT));
              String dateStr = cursor.getString(cursor.getColumnIndex(ToDoEntry.COLUMN_NAME_DUE_DATE));
              DateTime dueDate = null;
              if(dateStr != null && !dateStr.isEmpty()) {
                  dueDate = DateTime.parse(dateStr);
              }
              items.add(new ToDoItem(itemId, itemText, dueDate));
              cursor.moveToNext();
          }
    }

    private long insert(String item, DateTime dueDate) {
        ContentValues values = new ContentValues(1);
        values.put(ToDoEntry.COLUMN_NAME_TEXT, item);
        if(dueDate != null) {
            values.put(ToDoEntry.COLUMN_NAME_DUE_DATE, dueDate.toString());
        }
        ToDoDbHelper dbHelper = new ToDoDbHelper(getApplicationContext());
        return dbHelper.getWritableDatabase().insert(
                ToDoEntry.TABLE_NAME,
                null,
                values
        );
    }

    private void markCompleted(ToDoItem item) {
        ContentValues values = new ContentValues(1);
        values.put(ToDoEntry.COLUMN_NAME_COMPLETED, 1);
        String whereClause = ToDoEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = { item.getId() + "" };
        ToDoDbHelper dbHelper = new ToDoDbHelper(getApplicationContext());
        dbHelper.getWritableDatabase().update(
                ToDoEntry.TABLE_NAME,
                values,
                whereClause,
                whereArgs
        );
    }

    private void update(ToDoItem item) {
        ContentValues values = new ContentValues(1);
        values.put(ToDoEntry.COLUMN_NAME_TEXT, item.getText());
        values.put(ToDoEntry.COLUMN_NAME_DUE_DATE, item.getRawDueDate().toString());
        String whereClause = ToDoEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = { item.getId() + "" };
        ToDoDbHelper dbHelper = new ToDoDbHelper(getApplicationContext());
        dbHelper.getWritableDatabase().update(
                ToDoEntry.TABLE_NAME,
                values,
                whereClause,
                whereArgs
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(requestCode == resultCode) {
            int position = i.getIntExtra("position", -1);
            long updatedId = i.getLongExtra("id", -1);
            String updatedText = i.getStringExtra("text");
            String updatedDate = i.getStringExtra("date");
            DateTime dueDate = null;
            if(updatedDate != null && !updatedDate.isEmpty()) {
                dueDate = DateTime.parse(updatedDate, DateTimeFormat.forPattern(ToDoItem.DATE_FORMAT));
            }
            ToDoItem item = new ToDoItem(updatedId, updatedText, dueDate);
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            update(item);
        }
    }

}
