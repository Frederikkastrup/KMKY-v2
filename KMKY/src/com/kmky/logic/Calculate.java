package com.kmky.logic;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kmky.R;

/**
 * Created by W520 on 05-10-13..
 */
public class Calculate {

    private Context mContext;

    Calculate(Context context)
    {
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Drawable getCallHeart(int treshold, Context context)
    {
        Drawable drawable = null;

        if (treshold == 0){
            drawable = context.getResources().getDrawable(R.drawable.blank);
        }
        else if ((treshold > 1) && (treshold <= 20)) {
            drawable = context.getResources().getDrawable(R.drawable.insideheart30);
        }

        else if ((treshold > 20) && (treshold <= 40)) {
            drawable = context.getResources().getDrawable(R.drawable.insideheart35);
        }

        else if ((treshold > 40) && (treshold <= 60)) {
            drawable = context.getResources().getDrawable(R.drawable.insideheart40);
        }

        else if ((treshold > 60) && (treshold <= 80)) {
            drawable = context.getResources().getDrawable(R.drawable.insideheart45);
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.insideheart50);
        }
        return drawable;
    }

    private Drawable getSmsHeart(int treshold, Context context)
    {
        Drawable drawable = null;

        if (treshold == 0){
            drawable = context.getResources().getDrawable(R.drawable.blank);
        }
        else if ((treshold > 1) && (treshold <= 20)) {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart60);
        }

        else if ((treshold > 20) && (treshold <= 40)) {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart70);
        }

        else if ((treshold > 40) && (treshold <= 60)) {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart80);
        }

        else if ((treshold > 60) && (treshold <= 80)) {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart100);
        }
        return drawable;
    }

    Drawable calculateHeart(int outgoing, int incoming, int state, Context context)
    {
        int treshold = 0;
        Drawable drawable = null;

        switch (state)
        {
            case 1: // smsheartme
                try{
                    treshold = (outgoing/(outgoing + incoming)) * 100;
                }
                catch (ArithmeticException e) {
                    treshold = 0;
                }
                drawable = getSmsHeart(treshold, context);
                break;

            case 2: // callheartme
                try{ treshold = (outgoing/(outgoing + incoming)) * 100;
                }
                catch (ArithmeticException e)
                {
                    treshold = 0;
                }
                drawable = getCallHeart(treshold, context);
                break;

            case 3: // smshearyou
                try{ treshold = (incoming/(outgoing + incoming)) * 100;
                }
                catch (ArithmeticException e) {
                    treshold = 0;
                }
                getSmsHeart(treshold, context);
                break;

            case 4: // callheart you
                try{ treshold = (incoming/(outgoing + incoming)) * 100;
                }
                catch (ArithmeticException e) {
                    treshold = 0;
                }
                drawable = getCallHeart(treshold, context);
                break;
        }
        return drawable;
    }

}

