package com.example.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Ivan_Keung on 2017/1/4.
 */

public class Card extends FrameLayout {
    private TextView label;
    private int num =0;
    public Card(Context context) {
        super(context);

        label=new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x338B8B00);
        label.setTextColor(0x330D0D0D);

        label.setGravity(Gravity.CENTER);


        LayoutParams lp=new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(label,lp);

        setNum(0);

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        label.setBackgroundColor(getBackColor(num));
        label.getBackground().setAlpha(160);
        if(num<=0){
            label.setText("");
        }else {
        label.setText(num+"");
        }
    }

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }



    private int defaultBackColor=0x338B8B00;
    private int getBackColor(int num) {

        int bgcolor = defaultBackColor;
        switch (num) {
            case 0:
                bgcolor = 0xffCCC0B3;
                break;
            case 2:
                bgcolor = 0xffEEE4DA;
                break;
            case 4:
                bgcolor = 0xffEDE0C8;
                break;
            case 8:
                bgcolor = 0xffF2B179;
                break;
            case 16:
                bgcolor = 0xffF49563;
                break;
            case 32:
                bgcolor = 0xffF5794D;
                break;
            case 64:
                bgcolor = 0xffF55D37;
                break;
            case 128:
                bgcolor = 0xffEEE863;
                break;
            case 256:
                bgcolor = 0xffEDB04D;
                break;
            case 512:
                bgcolor = 0xffECB04D;
                break;
            case 1024:
                bgcolor = 0xffEB9437;
                break;
            case 2048:
                bgcolor = 0xffEA7821;
                break;
            default:
                bgcolor = 0xffEA7821;
                break;
        }
        return bgcolor;
    }

}
