package com.example.android.bignerdquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Toast toast =  Toast.makeText(MainActivity.this, getText(R.string.correct_toast),
                        Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.CENTER,0,0);
               toast.show();
            }
        });


        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, getText(R.string.incorrect_toast),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
