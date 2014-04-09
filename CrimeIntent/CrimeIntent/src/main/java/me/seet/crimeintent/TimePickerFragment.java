package me.seet.crimeintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dseet on 4/9/2014.
 */
public class TimePickerFragment extends DateTimePickerFragment {
    public static final String EXTRA_TIME = "me.seet.criminalintent.time";

    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment fragment = new TimePickerFragment(date);
        return fragment;
    }

    private TimePickerFragment() {
        this(new Date());
    }

    public TimePickerFragment(Date date) {
        super(date, EXTRA_TIME);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializedDate(EXTRA_TIME);
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(getHour());
        timePicker.setCurrentMinute(getMin());
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                setDate(new GregorianCalendar(getYear(), getMonth(), getDay(), hour, minute).getTime());

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_TIME, getDate());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        sendResult(Activity.RESULT_OK, EXTRA_TIME);
                    }
                })
                .create();
    }
}
