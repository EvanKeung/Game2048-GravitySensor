package com.example.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ivan_Keung on 2017/1/3.
 */

public class GameView extends GridLayout {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    private float x=0,y=0,z=0;
    private static final int UPTATE_INTERVAL_TIME=800;
    private long lastUpdateTime;

    public GameView(final Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
private void initGameView(){


    sensorManager= (SensorManager) MainActivity.instance.getSystemService(Context.SENSOR_SERVICE);
    sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            long currentUpdateTime=System.currentTimeMillis();
            long timeInterval=currentUpdateTime-lastUpdateTime;
            if(timeInterval<UPTATE_INTERVAL_TIME)
                return;
            lastUpdateTime=currentUpdateTime;

            x=event.values[SensorManager.DATA_X];
            y=event.values[SensorManager.DATA_Y];
            z=event.values[SensorManager.DATA_Z];

            if(x<-5){
                swipeRight();
            }
            else if(x>5){
                swipeLeft();
            }
            else if(y<-5){
                swipeUp();
            }
            else if(y>5){
                swipeDown();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_GAME);


    setColumnCount(4);

    setOnTouchListener(new OnTouchListener() {
        private float startX,startY,offsetX,offsetY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX=event.getX();
                    startY=event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    offsetX=event.getX()-startX;
                    offsetY=event.getY()-startY;
                    if(Math.abs(offsetX)>Math.abs(offsetY)){
                        if (offsetX<-5){
                            swipeLeft();
                        }else if(offsetX>5){
                            swipeRight();
                        }
                    }else {
                        if(offsetY<-5){
                            swipeUp();
                        }else if (offsetY>5){
                            swipeDown();
                        }
                    }
                    break;

            }
            return true;
        }

    });

}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();

    }
    private void addCards(int cardWidth,int cardHeight){
        Card c;
        for (int y=0 ; y<4; y++){
            for(int x=0;x<4;x++){
               c=new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y]=c;
            }
        }
    }
    private void startGame(){
        MainActivity.getMainActivity().clearScore();
        for(int y=0;y<4;y++){
            for(int x=0; x<4; x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){
        emptyPoint.clear();

        for (int y=0 ; y<4; y++){
            for(int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoint.add(new Point(x,y));

                }

            }
        }
        Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    private void swipeLeft(){
        boolean marge=false;
        for(int y=0; y<4; y++){
            for(int x=0; x<4; x++){

                for(int x1=x+1;x1<4;x1++){
                    if(cardsMap[x1][y].getNum()>0){

                       if(cardsMap[x][y].getNum()<=0){
                           cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                           cardsMap[x1][y].setNum(0);

                           x--;
                           marge=true;
                       }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x1][y].setNum(0);
                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           marge=true;
                       }
                        break;
                    }
                }
            }
        }
        if(marge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean marge=false;
        for(int y=0; y<4; y++){
            for(int x=3; x>=0; x--){

                for(int x1=x-1;x1>=0;x1--){
                    if(cardsMap[x1][y].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            marge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(marge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean marge=false;
        for(int x=0; x<4; x++){
            for(int y=0; y<4; y++){

                for(int y1=y+1;y1<4;y1++){
                    if(cardsMap[x][y1].getNum()>0){

                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;
                            marge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(marge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean marge=false;
        for(int x=0; x<4; x++){
            for(int y=3; y>=0; y--){
                for(int y1=y-1;y1>=0;y1--){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            marge=true;

                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            marge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(marge){
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete(){

        boolean complete=true;
        All:
        for(int y=0; y<4; y++){
            for(int x=0; x<4; x++){
                if(cardsMap[x][y].getNum()==0
                        ||(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))
                        ||(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))
                        ||(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))
                        ||(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete=false;
                    break All;
                }
            }
        }
        if (complete){

            new AlertDialog.Builder(getContext())
                    .setTitle("游戏结束")
                    .setMessage("您的得分为："+MainActivity.score)
                    .setCancelable(false)
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           MainActivity.getMainActivity().finish();

                        }
                    })
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startGame();
                        }
                    }).show();
        }


    }
    private  Card[][]cardsMap = new Card[4][4];
    private List<Point>emptyPoint=new ArrayList<Point>();


}
