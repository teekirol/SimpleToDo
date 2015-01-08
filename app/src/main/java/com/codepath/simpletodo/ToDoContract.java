package com.codepath.simpletodo;

import android.provider.BaseColumns;

public final class ToDoContract {

    public ToDoContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ToDoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_DUE_DATE = "due";
    }
}
