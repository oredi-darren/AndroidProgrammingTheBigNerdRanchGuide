package me.seet.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {
    private static final String TAG = "QuizActivity";
    private static final String PERSISTENT_INDEX = "index";
    private static final String DATA_INDEX = "data";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Called when activity is initially created, goes into Created state
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            // restore from bundle
            mCurrentIndex = savedInstanceState.getInt(PERSISTENT_INDEX);
            mQuestionBank = (TrueFalse[])savedInstanceState.getSerializable(DATA_INDEX);
        } else {
            mQuestionBank = new TrueFalse[] {
                    new TrueFalse(R.string.question_ocean, true),
                    new TrueFalse(R.string.question_mideast, false),
                    new TrueFalse(R.string.question_africa, false),
                    new TrueFalse(R.string.question_americas, true),
                    new TrueFalse(R.string.question_asia, true)
            };
        }
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion(1);
            }
        });
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion(1);
            }
        });

        mPreviousButton = (ImageButton)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion(-1);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].isTrueQuestion());
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onStart() {
        // Called before activity becomes visible from Created state or Stopped state (after onRestart)
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onRestart() {
        // Called before activity becomes visible from Stopped state (before onRestart)
        super.onRestart();
        Log.d(TAG, "onRestart() called");
    }

    @Override
    protected void onResume() {
        // Called before activity becomes active from Started State or Paused state
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        // Called before activity becomes inactive from Resumed state
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        // Called before activity becomes stopped from Paused state
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        // Called before activity becomes destroyed from Stopped state
        super.onResume();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Called before activity onPause, onStop and onDestroy.
        // Used to persist data across configuration changes
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");
        outState.putInt(PERSISTENT_INDEX, mCurrentIndex);
        outState.putSerializable(DATA_INDEX, mQuestionBank);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)
            return;
        mQuestionBank[mCurrentIndex].setCheater(data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false));
    }

    private void getQuestion(int offset) {
        mCurrentIndex += offset;                                           // Add offset + or - 1
        if(mCurrentIndex < 0) mCurrentIndex = mQuestionBank.length - 1;    // check lower boundary
        mCurrentIndex = mCurrentIndex % mQuestionBank.length;              // check upper boundary
        updateQuestion();
    }

    private void checkAnswer(boolean userPressedTrue) {
        TrueFalse question = mQuestionBank[mCurrentIndex];
        int messageResId;
        if (question.isCheater()) {
            messageResId = R.string.judgment_toast;
        } else {
            if(userPressedTrue == question.isTrueQuestion())
                messageResId = R.string.correct_toast;
            else
                messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }
}