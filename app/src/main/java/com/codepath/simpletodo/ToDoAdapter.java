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
        TextView itemText = (TextView) convertView.findViewById(R.id.toDoText);
        itemText.setText(getItem(position).getText());
        return convertView;
    }
}