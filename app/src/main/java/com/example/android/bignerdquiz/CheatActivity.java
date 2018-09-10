package com.example.android.bignerdquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.android.bignerdquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.android.bignerdquiz.answer_shown";
    private static final String KEY_ANSWER_TEXT_VIEW = "com.example.android.bignerdquiz.answer_text_view";
    private static final String KEY_CHEATER_COUNTER = "cheatCount";
    private boolean answerIsTrue;
    private Button showAnswerButton;
    private TextView answerTextView;
    private TextView sdkTextView;
    private TextView cheatCounter;
    private int cheaterCounterNumber;

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
        cheatCounter = findViewById(R.id.cheat_counter);
        cheaterCounterNumber = getIntent().getExtras().getInt("cheatThreeTimesIntent");
        sdkTextView = findViewById(R.id.sdk_text_view);
        sdkTextView.setText("API level is " + Build.VERSION.SDK_INT);
        answerTextView = findViewById(R.id.answer_text_view);
        cheatCounter.setText("" + cheaterCounterNumber);
        if(savedInstanceState != null && savedInstanceState.getString(KEY_ANSWER_TEXT_VIEW) != null){
            answerTextView.setText(savedInstanceState.getString(KEY_ANSWER_TEXT_VIEW));
            setAnswerShownResult(true);

        }
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        showAnswerButton = findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cheaterCounterNumber >= 3){
                    Toast.makeText(CheatActivity.this, "You've already cheated three times!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(answerIsTrue){
                        answerTextView.setText(R.string.True);
                    } else {
                        answerTextView.setText(R.string.False);
                    }
                    setAnswerShownResult(true);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        int cx = showAnswerButton.getWidth() / 2;
                        int cy = showAnswerButton.getHeight() / 2;

                        float radius = showAnswerButton.getWidth();

                        Animator anim = ViewAnimationUtils
                                .createCircularReveal(showAnswerButton,cx,cy,radius,0);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                showAnswerButton.setVisibility(View.INVISIBLE);
                            }
                        });

                        anim.start();

                    } else {
                        showAnswerButton.setVisibility(View.INVISIBLE);
                    }
                }
                cheaterCounterNumber++;
                cheatCounter.setText("" + cheaterCounterNumber);
            }
        });
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
        savedInstanceState.putInt(KEY_CHEATER_COUNTER, cheaterCounterNumber);
    }
}
