package me.seet.crimeintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;

import java.security.KeyStore;

/**
 * Created by dseet on 4/9/2014.
 */
public class DateTimeChoiceFragment extends DialogFragment {
    public static final String EXTRA_DATETIME_CHOICE = "me.seet.criminalintent.datetime_choice";
    private RadioButton mDateRadioButton;
    private RadioButton mTimeRadioButton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datetime_choice, null);
        mDateRadioButton = (RadioButton)v.findViewById(R.id.dateRadioButton);
        mTimeRadioButton = (RadioButton)v.findViewById(R.id.timeRadioButton);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.datetime_choice_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(getTargetFragment() == null)
                            return;


                        Intent i = new Intent();
                        if(mDateRadioButton.isChecked())
                            i.putExtra(EXTRA_DATETIME_CHOICE, CrimeFragment.DIALOG_DATE);
                        else if(mTimeRadioButton.isChecked())
                            i.putExtra(EXTRA_DATETIME_CHOICE, CrimeFragment.DIALOG_TIME);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                    }
                })
                .create();
    }
}
