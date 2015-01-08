package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter<ToDoItem> {

    public ToDoAdapter(Context context, ArrayList<ToDoItem> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        ToDoItem item = getItem(position);
        TextView itemText = (TextView) convertView.findViewById(R.id.toDoText);
        itemText.setText(item.getText());
        TextView dueDate = (TextView) convertView.findViewById(R.id.dueDate);
        if (item.getDueDate().isEmpty()) {
            dueDate.setText("(no due date)");
        } else {
            dueDate.setText(item.getDueDate());
        }
        return convertView;
    }
}