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

    /**
     * getCallHeart returns the appropriate heart drawable according to the treshold passed to it.
     * @param treshold
     * @param context
     * @return
     */
    private Drawable getCallHeart(int treshold, Context context)
    {
        Drawable drawable = null;

        if (treshold == 0){
            drawable = context.getResources().getDrawable(R.drawable.blank);
        }
        else if ((treshold > 1) && (treshold <= 20)) {
            drawable = context.getResources().getDrawable(R.drawable.callheart20);
        }

        else if ((treshold > 20) && (treshold <= 40)) {
            drawable = context.getResources().getDrawable(R.drawable.callheart40);
        }

        else if ((treshold > 40) && (treshold <= 60)) {
            drawable = context.getResources().getDrawable(R.drawable.callheart60);
        }

        else if ((treshold > 60) && (treshold <= 80)) {
            drawable = context.getResources().getDrawable(R.drawable.callheart80);
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.callheart100);
        }
        return drawable;
    }

    /**
     * getSmsHeart returns the appropriate heart drawable according to the treshold passed to it
     * @param treshold
     * @param context
     * @return
     */
    private Drawable getSmsHeart(int treshold, Context context)
    {
        Drawable drawable = null;

        if (treshold == 0){
            drawable = context.getResources().getDrawable(R.drawable.blank);
        }
        else if ((treshold > 1) && (treshold <= 20)) {
            drawable = context.getResources().getDrawable(R.drawable.smsheart20);
        }

        else if ((treshold > 20) && (treshold <= 40)) {
            drawable = context.getResources().getDrawable(R.drawable.smsheart40);
        }

        else if ((treshold > 40) && (treshold <= 60)) {
            drawable = context.getResources().getDrawable(R.drawable.smsheart60);
        }

        else if ((treshold > 60) && (treshold <= 80)) {
            drawable = context.getResources().getDrawable(R.drawable.smsheart80);
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.smsheart100);
        }
        return drawable;
    }

    /**
     * calculate heart returns the appropriate heart drawable according to the amount of outgoing and incoming message retrieved from the database. The state argument
     * passed determine which heart type is needed and who it is referring too.
     * @param outgoing
     * @param incoming
     * @param state
     * @param context
     * @return
     */
    Drawable calculateHeart(int outgoing, int incoming, int state, Context context)
    {
        int treshold = 0;
        Drawable drawable = null;

        switch (state)
        {
            case 1: // smsheartme
                try{ treshold = (outgoing/(outgoing + incoming)) * 100;
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

            case 3: // smsheartyou
                try{ treshold = (incoming/(outgoing + incoming)) * 100;
                }
                catch (ArithmeticException e) {
                    treshold = 0;
                }
                drawable = getSmsHeart(treshold, context);
                break;

            case 4: // callheartyou
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

