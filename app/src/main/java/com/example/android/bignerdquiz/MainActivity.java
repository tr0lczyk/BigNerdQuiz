package com.example.android.bignerdquiz;

import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private Button resetButton;
    private int alreadyWon = 0;
    private TextView questionTextView;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_canberra, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asiaa, true)
    };
    private int currentIndex = 0;

    private final String TAG = "MainActivity";
    private final String KEY_INDEX = "index";
    private final String KEY_LIST = "list";
    private final String KEY_SUM = "sum";
    private int pointSum = 0;
    private List<Integer> haveIbeenThere = new ArrayList<>();
    private TextView questionsNumber;
    private TextView questionIndex;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            haveIbeenThere = (List<Integer>) savedInstanceState.getSerializable(KEY_LIST);
            pointSum = savedInstanceState.getInt(KEY_SUM);
        }
        questionsNumber = findViewById(R.id.textView5);
        questionsNumber.setText("/"+questionBank.length);
        questionIndex = findViewById(R.id.textView3);
        setCurrentIndex();
        score = findViewById(R.id.textView4);
        score.setText("" + pointSum);
        questionTextView = findViewById(R.id.question_text_view);
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveIbeenThere.contains(currentIndex)) {
                    checkAnswer(true);
                } else if (alreadyWon == 1) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("BigNerdQuiz")
                            .setMessage(getString(R.string.dialog_message))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.start_over), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pointSum = 0;
                                    haveIbeenThere = new ArrayList<>();
                                    alreadyWon = 0;
                                    currentIndex = 0;
                                    updateQuestion();
                                    score.setText("" + pointSum);
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton(getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.shall_not_more_tries),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveIbeenThere.contains(currentIndex)) {
                    checkAnswer(false);
                } else if (alreadyWon == 1) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("BigNerdQuiz")
                            .setMessage(getString(R.string.dialog_message))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.start_over), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pointSum = 0;
                                    haveIbeenThere = new ArrayList<>();
                                    alreadyWon = 0;
                                    currentIndex = 0;
                                    updateQuestion();
                                    score.setText("" + pointSum);
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton(getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.shall_not_more_tries),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        previousButton = findViewById(R.id.prvious_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = questionBank.length - 1;
                }
                updateQuestion();
            }
        });

        resetButton = findViewById(R.id.restart_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("BigNerdQuiz")
                        .setMessage(getString(R.string.restart_message))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.reset), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pointSum = 0;
                                haveIbeenThere = new ArrayList<>();
                                alreadyWon = 0;
                                currentIndex = 0;
                                updateQuestion();
                                score.setText("" + pointSum);
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton(getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
        updateQuestion();
    }

    private void updateQuestion() {
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
        setCurrentIndex();
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            pointSum++;
            haveIbeenThere.add(currentIndex);
        } else {
            messageResId = R.string.incorrect_toast;
            haveIbeenThere.add(currentIndex);
        }
        score.setText("" + pointSum);
        int percentage = pointSum * 100 / questionBank.length;
        if (haveIbeenThere.size() == questionBank.length) {
            Toast.makeText(MainActivity.this, getString(R.string.guessed_correctly) +
                            percentage + "%",
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG,"final score is: " + percentage);
            alreadyWon = 1;
        } else {
            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentIndex(){
        questionIndex.setText(""+ (currentIndex + 1));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "OnSaveInstance");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        savedInstanceState.putSerializable(KEY_LIST, (Serializable) haveIbeenThere);
        savedInstanceState.putInt(KEY_SUM, pointSum);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }
}
