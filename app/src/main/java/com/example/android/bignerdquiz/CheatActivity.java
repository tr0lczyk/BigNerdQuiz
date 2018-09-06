package com.example.android.bignerdquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.android.bignerdquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.android.bignerdquiz.answer_shown";
    private static final String KEY_ANSWER_TEXT_VIEW = "com.example.android.bignerdquiz.answer_text_view";
    private boolean answerIsTrue;
    private Button showAnswerButton;
    private TextView answerTextView;
    private String alreadyShown;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        initView();
        answerTextView = findViewById(R.id.answer_text_view);
        if(savedInstanceState != null && savedInstanceState.getString(KEY_ANSWER_TEXT_VIEW) != null){
            alreadyShown = savedInstanceState.getString(KEY_ANSWER_TEXT_VIEW);
            answerTextView.setText(alreadyShown);

        }
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        showAnswerButton = findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerIsTrue){
                    answerTextView.setText(R.string.True);
                } else {
                    answerTextView.setText(R.string.False);
                }
                setAnswerShownResult(true);
            }
        });
    }

    private void initView() {

    }


    public void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_ANSWER_TEXT_VIEW,answerTextView.getText().toString());
    }

}
