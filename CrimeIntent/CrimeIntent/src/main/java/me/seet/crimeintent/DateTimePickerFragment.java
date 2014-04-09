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
 * Created by dseet on 4/9/2014.
 */
public class DateTimePickerFragment extends DialogFragment {
    protected Date getDate() {
        return mDate;
    }

    protected void setDate(Date date) {
        mDate = date;
        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMin = calendar.get(Calendar.MINUTE );
    }

    private Date mDate;
    private int mYear;

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    public int getHour() {
        return mHour;
    }

    public int getMin() {
        return mMin;
    }

    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMin;

    protected void initializedDate(String ArgumentName) {
        setDate((Date) getArguments().getSerializable(ArgumentName));
    }

    protected void sendResult(int resultCode, String ArgumentName) {
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(ArgumentName, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    protected DateTimePickerFragment(Date date, String ArgumentName) {
        Bundle args = new Bundle();
        args.putSerializable(ArgumentName, date);
        setArguments(args);
    }
}
