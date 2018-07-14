package com.juanborges.basketballlive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView celticsScore;
    TextView warriorsScore;

    int bcScore;
    int gswScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        celticsScore = findViewById(R.id.team1_score);
        warriorsScore = findViewById(R.id.team2_score);

        bcScore = 0;
        gswScore = 0;
    }

    public void addThreePoints(View view)
    {
        if (view.getId() == R.id.team1_3points_button)
        {
            bcScore += 3;
            celticsScore.setText(""+ bcScore);
            return;
        }

        gswScore += 3;
        warriorsScore.setText(""+ gswScore);
    }

    public void addTwoPoints(View view)
    {
        if (view.getId() == R.id.team1_2points_button)
        {
            bcScore += 2;
            celticsScore.setText(""+ bcScore);
            return;
        }

        gswScore += 2;
        warriorsScore.setText(""+ gswScore);
    }

    public void addOnePoint(View view)
    {
        if (view.getId() == R.id.team1_ft_button)
        {
            bcScore += 1;
            celticsScore.setText(""+ bcScore);
            return;
        }

        gswScore += 1;
        warriorsScore.setText(""+ gswScore);
    }

    public void resetScore(View view)
    {
        bcScore = 0;
        gswScore = 0;

        celticsScore.setText(""+ bcScore);
        warriorsScore.setText(""+ gswScore);
    }
}
