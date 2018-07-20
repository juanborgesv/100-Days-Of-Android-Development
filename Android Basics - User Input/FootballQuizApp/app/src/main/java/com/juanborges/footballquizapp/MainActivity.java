package com.juanborges.footballquizapp;

import android.graphics.Color;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    int level;  // Current question
    int score;  // Correct answers counter.

    String[] question = new String[4];
    int[] correctAnswer = {2, 0, 3, 1};   // Answer sheet.
    int[] myAnswers = new int[4];

    String[][] possibleAnswer = new String[4][4];

    ImageView questionImage;
    TextView questionText;
    Button buttonPressed;

    Button answerA;
    Button answerB;
    Button answerC;
    Button answerD;

    String summary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        level = 0;
        score = 0;

        // Store UI elements references.
        questionText = (TextView) findViewById(R.id.question_text);
        questionImage = (ImageView) findViewById(R.id.question_image);
        answerA = (Button) findViewById(R.id.answer_a_button);
        answerB = (Button) findViewById(R.id.answer_b_button);
        answerC = (Button) findViewById(R.id.answer_c_button);
        answerD = (Button) findViewById(R.id.answer_d_button);

        // Set up Questions below
        // Question 0
        question[0] = "Which team won the last FIFA World Cup?";
        possibleAnswer[0][0] = "Brazil";
        possibleAnswer[0][1] = "England";
        possibleAnswer[0][2] = "France";
        possibleAnswer[0][3] = "Germany";

        // Question 1
        question[1] = "Who is the top scorer of the 2017/2018 season?";
        possibleAnswer[1][0] = "L. Messi";
        possibleAnswer[1][1] = "M. Salah";
        possibleAnswer[1][2] = "H. Kane";
        possibleAnswer[1][3] = "C. Ronaldo";

        // Question 2
        question[2] = "How many FIFA World Cups does Italy have?";
        possibleAnswer[2][0] = "One";
        possibleAnswer[2][1] = "Two";
        possibleAnswer[2][2] = "Three";
        possibleAnswer[2][3] = "Four";

        // Question 3
        question[3] = "Which team won the last UEFA Champions League?";
        possibleAnswer[3][0] = "Liverpool FC";
        possibleAnswer[3][1] = "R. Madrid CF";
        possibleAnswer[3][2] = "AS Roma";
        possibleAnswer[3][3] = "FC Barcelona";

        // Create Question
        loadNextQuestion();
    }

    public void checkAnswer(View view)
    {
        if (correctAnswer[level] == Integer.parseInt(view.getTag().toString()))
        {
            Log.i("checkAnswer", "correct");
            buttonPressed = (Button) view;
            ++score;
        }
        else
        {
            Log.i("checkAnswer", "incorrect");
            buttonPressed = (Button) view;
        }

        // Save answer pressed
        myAnswers[level] = Integer.parseInt(view.getTag().toString());

        // Load next question while the last one is not reached
        if (level < 3)
        {
            level += 1;
            loadNextQuestion();
        }
        else if (level == 3)
        {
            endQuiz();
        }
    }

    private void loadNextQuestion()
    {
        // Set image
        if (level == 0)
            questionImage.setImageResource(R.drawable.russia_worldcup);
        else if (level == 1)
            questionImage.setImageResource(R.drawable.goal);
        else if (level == 2)
            questionImage.setImageResource(R.drawable.italy_worldcup);
        else if (level == 3)
            questionImage.setImageResource(R.drawable.champions_league);

        // Set question
        questionText.setText(""+ question[level]);

        // Set possible answers
        answerA.setText(possibleAnswer[level][0]);
        answerB.setText(possibleAnswer[level][1]);
        answerC.setText(possibleAnswer[level][2]);
        answerD.setText(possibleAnswer[level][3]);
    }

    private void endQuiz()
    {
        TextView endTitle = findViewById(R.id.question_title);
        endTitle.setText(""+ "Resume");

        level = 4;

        questionImage.setImageResource(R.drawable.iniesta);

        summary = "Score: "+ score+ "/4";

        summary += "\n1. "+ possibleAnswer[0][myAnswers[0]];
        if (myAnswers[0] != correctAnswer[0])
            summary += " (Wrong)";

        summary += "\n2. "+ possibleAnswer[1][myAnswers[1]];
        if (myAnswers[1] != correctAnswer[1])
            summary += " (Wrong)";

        summary += "\n3. "+ possibleAnswer[2][myAnswers[2]];
        if (myAnswers[2] != correctAnswer[2])
            summary += " (Wrong)";

        summary += "\n4. "+ possibleAnswer[3][myAnswers[3]];
        if (myAnswers[3] != correctAnswer[3])
            summary += " (Wrong Answer)";

        answerA.setText(""+ "Thanks");
        answerB.setText(""+ "For");
        answerC.setText(""+ "Playing");
        answerD.setText(""+ "!");

        questionText.setText(""+ summary);
    }

}
