package me.seet.crimeintent;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "me.seet.criminalintent.crime_id";
    public static final String DIALOG_DATETIME_CHOICE = "datetime_choice";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATETIME_CHOICE = 0;
    private static final int REQUEST_DATE = 1;
    private static final int REQUEST_TIME = 2;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public CrimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimeChoiceFragment dialog = new DateTimeChoiceFragment();
                showDialogFragment(dialog, REQUEST_DATETIME_CHOICE, DIALOG_DATETIME_CHOICE);
            }
        });


        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogFragment(DialogFragment dialog, int requestCode, String dialogName) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog.setTargetFragment(this, requestCode);
        dialog.show(fm, dialogName);
    }

    private void updateDate() {
        DateFormat df = new SimpleDateFormat("EEEE, MMM d yyyy hh:mm a");
        mDateButton.setText(df.format(mCrime.getDate()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case REQUEST_DATETIME_CHOICE:
                DialogFragment dialog;
                String choiceResult = (String)data.getSerializableExtra(DateTimeChoiceFragment.EXTRA_DATETIME_CHOICE);
                if(choiceResult == DIALOG_DATE) {
                    dialog = DatePickerFragment.newInstance(mCrime.getDate());
                    showDialogFragment(dialog, REQUEST_DATE, DIALOG_DATE);
                } else if (choiceResult == DIALOG_TIME) {
                    dialog = TimePickerFragment.newInstance(mCrime.getDate());
                    showDialogFragment(dialog, REQUEST_TIME, DIALOG_TIME);
                }
                break;
            case REQUEST_DATE:
                Date dateResult = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(dateResult);
                updateDate();
                break;
            case REQUEST_TIME:
                Date timeResult = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
                mCrime.setDate(timeResult);
                updateDate();
                break;
            default:
                break;
        }
    }
}
