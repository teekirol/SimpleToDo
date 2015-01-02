package com.codepath.simpletodo;

import org.joda.time.DateTime;

public class ToDoItem {

    private long id;
    private String text;
    private DateTime dueDate;

    public ToDoItem(long id, String text, DateTime dueDate) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public DateTime getDueDate() { return this.dueDate; }

}