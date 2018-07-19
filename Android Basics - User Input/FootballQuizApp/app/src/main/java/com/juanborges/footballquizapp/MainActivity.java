package com.juanborges.footballquizapp;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    int level;
    int score;

    String[] question = new String[5];
    int[] correctAnswer = {3, 2, 4, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = 0;
        score = 0;

        question[0] = "Which team won the Russia World Cup 2018?";
        //question[1] = "Who was the top scorer of the 2017/2018 season?";
    }

    public void checkAnswer(View view)
    {
        if (correctAnswer[level] == Integer.parseInt(view.getTag().toString()))
            Log.i("checkAnswer", "correct");
        else
            Log.i("checkAnswer", "incorrect");
    }

}
