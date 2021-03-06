package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.joda.time.DateTime;

public class DatePickerDialogFragment extends DialogFragment {

    public DatePickerDialogFragment() { }

    public DatePickerDialogFragment newInstance() {
        return new DatePickerDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime now = DateTime.now();
        DatePickerDialog datepicker;
        // this is probably not great but I want to re-use this fragment in 2 activities
        try {
            datepicker = new DatePickerDialog(getActivity(),
                    (ToDoActivity) getActivity(), now.getYear(),
                    now.getMonthOfYear() - 1, now.getDayOfMonth());
        } catch (ClassCastException e) {
            datepicker = new DatePickerDialog(getActivity(),
                    (EditItemActivity) getActivity(), now.getYear(),
                    now.getMonthOfYear() - 1, now.getDayOfMonth());
        }
        return datepicker;
    }

}
