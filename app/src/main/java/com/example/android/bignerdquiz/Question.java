package com.example.android.bignerdquiz;

public class Question {

    private boolean answerTrue;
    private int textResId;

    public Question(int textResId, boolean answerTrue){
        this.textResId = textResId;
        this.answerTrue = answerTrue;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }


}
