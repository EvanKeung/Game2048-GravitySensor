package com.example.game2048;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_main);



        text= (TextView) findViewById(R.id.text);
        tvScore = (TextView) findViewById(R.id.tvScore);
        BestScore bs=new BestScore(this);
        currentBestScore=bs.getBestScore();
        tvScore.setText(currentBestScore+"");


    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        text.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
        if(score>currentBestScore){
            currentBestScore=score;
            BestScore bs=new BestScore(this);
            bs.setBestScore(currentBestScore);
            tvScore.setText(currentBestScore+"");
        }
    }
    private TextView text;
    private int currentBestScore;
    public static int score;
    private TextView tvScore;
    private int current_score = 0;
    private static MainActivity mainActivity = null;
    public static MainActivity getMainActivity() {
        return mainActivity;
    }



}
