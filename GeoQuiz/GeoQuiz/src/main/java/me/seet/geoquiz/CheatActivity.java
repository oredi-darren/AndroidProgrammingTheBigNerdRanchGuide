package me.seet.geoquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.Format;


public class CheatActivity extends ActionBarActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "me.seet.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "me.seet.geoquiz.answer_shown";

    private static final String SHOWN_INDEX = "shown";

    private boolean mAnswerIsTrue;
    private boolean mIsCheated;
    private TextView mAnswerTextView;
    private TextView mApiTextView;
    private Button mShowAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if(savedInstanceState != null)  {
            // Handle device config changes
            mIsCheated = savedInstanceState.getBoolean(SHOWN_INDEX);
        } else {
            // Answer will not be shown until the user presses the button
            mIsCheated = false;
        }
        setAnswerShownResult();
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mApiTextView = (TextView)findViewById(R.id.apiTextView);
        mApiTextView.setText(String.format("API level %d", Build.VERSION.SDK_INT));
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheated = true;
                setAnswerShownResult();
                if(mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOWN_INDEX, mIsCheated);
    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mIsCheated);
        setResult(RESULT_OK, data);
    }
}
