package com.codepath.simpletodo;

public class ToDoItem {

    private long id;
    private String text;

    public ToDoItem(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

}