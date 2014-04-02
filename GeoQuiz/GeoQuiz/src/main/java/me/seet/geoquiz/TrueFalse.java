package me.seet.geoquiz;

/**
 * Created by darren on 4/1/14.
 */
public class TrueFalse implements java.io.Serializable {
    private int mQuestion;
    private boolean mTrueQuestion;
    private boolean mIsCheater;

    public TrueFalse(int question, boolean trueQuestion) {
        mQuestion = question;
        mTrueQuestion = trueQuestion;
        mIsCheater = false;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean isCheater) {
        mIsCheater = isCheater;
    }
}
