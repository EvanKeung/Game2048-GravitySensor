package com.example.game2048;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ivan_Keung on 2017/1/8.
 */

public class BestScore {
    private SharedPreferences sp;
    public BestScore(Context context){
        sp=context.getSharedPreferences("BestScore",context.MODE_PRIVATE);
    }
    public int getBestScore(){
        int bestscore=sp.getInt("bestscore",0);
        return bestscore;
    }

    public void setBestScore(int bestScore) {
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("bestscore",bestScore);
        editor.commit();
    }
}
