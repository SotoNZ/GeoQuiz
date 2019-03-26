package com.bignerdranch.android.geoquiz;

public class Questions {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Questions (int TextResId,boolean AnswerTrue){

        this.mAnswerTrue = AnswerTrue;
        this.mTextResId = TextResId;







   //Getter/Setting for text id
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }






    //get setter for answerTrue
    public boolean getAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }








}
