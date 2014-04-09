package me.seet.crimeintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class DatePickerFragment extends DateTimePickerFragment {
    public static final String EXTRA_DATE = "me.seet.criminalintent.date";
    public DatePickerFragment() {
        // Required empty public constructor
        this(new Date());
    }

    public DatePickerFragment(Date date) {
        super(date, EXTRA_DATE);
    }

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment fragment = new DatePickerFragment(date);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializedDate(EXTRA_DATE);
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(getYear(), getMonth(), getDay(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                // Translate year, month, day into a Date object using a calendar
                setDate(new GregorianCalendar(year, month, day, getHour(), getMin()).getTime());

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, getDate());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        sendResult(Activity.RESULT_OK, EXTRA_DATE);
                    }
                })
                .create();
    }
}
