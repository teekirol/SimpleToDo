package com.codepath.simpletodo;

import org.joda.time.DateTime;

public class ToDoItem {

    private long id;
    private String text;
    private DateTime dueDate;
    public static String DATE_FORMAT = "EEE MMM d yyyy";

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

    public String getDueDate() {
        if (this.dueDate == null) {
            return "";
        } else {
            return this.dueDate.toString(DATE_FORMAT);
        }
    }
}