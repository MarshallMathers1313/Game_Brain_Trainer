package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    LinearLayout topLinearLayout;
    GridLayout centerGridLayout;
    LinearLayout bottomLinearLayout;
    TextView timerTextView;
    Button restartButton;
    int a, b;
    TextView expressionTextView;
    int locationCorrectAnswer;
    int incorrectAnswer;
    Button answerButton1, answerButton2, answerButton3, answerButton4;
    TextView resultTextView;
    CountDownTimer timer;
    TextView counterTextView;
    int counterCorrect, counterClickedAnswer;

    public void randomButton(){
        ArrayList<Integer> answers = new <Integer> ArrayList();
        Random random = new Random();
        a = random.nextInt(21);
        b = random.nextInt(21);
        expressionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b) + " = ");

        locationCorrectAnswer = random.nextInt(4);

        for(int i = 0; i < 4; i++){
            if(i == locationCorrectAnswer){
                answers.add(a + b);
            }else{
                incorrectAnswer = random.nextInt(41);
                while(incorrectAnswer == a + b){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        answerButton1.setText(Integer.toString(answers.get(0)));
        answerButton2.setText(Integer.toString(answers.get(1)));
        answerButton3.setText(Integer.toString(answers.get(2)));
        answerButton4.setText(Integer.toString(answers.get(3)));
    }


    public void updateTime(int millisUntilFinished) {
        timerTextView.setText(Integer.toString(millisUntilFinished));
    }



    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        topLinearLayout.setVisibility(View.VISIBLE);
        centerGridLayout.setVisibility(View.VISIBLE);
        bottomLinearLayout.setVisibility(View.VISIBLE);

        counterClickedAnswer = 0;
        counterCorrect = 0;

        randomButton();

       timer = new CountDownTimer(30000 + 100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                updateTime((int)millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                restartButton.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.VISIBLE);
                answerButton1.setEnabled(false);
                answerButton2.setEnabled(false);
                answerButton3.setEnabled(false);
                answerButton4.setEnabled(false);
                resultTextView.setText("Your score " + Integer.toString(counterCorrect) + "/" + Integer.toString(counterClickedAnswer));
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                mediaPlayer.start();
            }
        }.start();
    }


    public void buttonAnswerClicked(View view){
        if(view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))){
            counterCorrect++;
            counterClickedAnswer++;
            counterTextView.setText(Integer.toString(counterCorrect) + "/" + Integer.toString(counterClickedAnswer));
            resultTextView.setVisibility(View.VISIBLE);
            resultTextView.setText("Correct!");
            randomButton();
        }
        else{
            counterClickedAnswer++;
            counterTextView.setText(Integer.toString(counterCorrect) + "/" + Integer.toString(counterClickedAnswer));
            resultTextView.setText("Incorrect!");
        }
    }


    public void restart(View view){
        counterTextView.setText("0/0");
        counterClickedAnswer = 0;
        counterCorrect = 0;
        answerButton1.setEnabled(true);
        answerButton2.setEnabled(true);
        answerButton3.setEnabled(true);
        answerButton4.setEnabled(true);
        updateTime(30);
        timer.start();
        randomButton();
        restartButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.startButton);
        topLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        centerGridLayout = (GridLayout) findViewById(R.id.centerGridLayout);
        bottomLinearLayout = (LinearLayout) findViewById(R.id.bottomLinearLayout);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        restartButton = (Button) findViewById(R.id.restartButton);
        expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        answerButton1 = (Button) findViewById(R.id.answerButton1);
        answerButton2 = (Button) findViewById(R.id.answerButton2);
        answerButton3 = (Button) findViewById(R.id.answerButton3);
        answerButton4 = (Button) findViewById(R.id.answerButton4);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        counterTextView = (TextView) findViewById(R.id.counterTextView);
    }
}