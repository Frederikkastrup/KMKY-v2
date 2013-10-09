package com.kmky.logic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kmky.R;
import com.kmky.util.Constants;

import java.text.DecimalFormat;

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
     * getCallHeart returns the appropriate heart drawable according to the threshold passed to it.
     * @param threshold
     * @param context
     * @return
     */
    private Drawable getCallHeart(int threshold, Context context)
    {
        Drawable callHeart = null;

        if (threshold == 0){
            callHeart = context.getResources().getDrawable(R.drawable.blank);
        }
        else if ((threshold > 1) && (threshold <= 20)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart20);
        }

        else if ((threshold > 20) && (threshold <= 40)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart40);
        }

        else if ((threshold > 40) && (threshold <= 60)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart60);
        }

        else if ((threshold > 60) && (threshold <= 80)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart80);
        }

        else {
            callHeart = context.getResources().getDrawable(R.drawable.callheart100);
        }
        return callHeart;
    }

    /**
     * getSmsHeart returns the appropriate heart drawable according to the threshold passed to it
     * @param threshold
     * @param context
     * @return
     */
    private Drawable getSmsHeart(int threshold, Context context)
    {
        Drawable smsHeart = null;

        if (threshold == 0){
            smsHeart = context.getResources().getDrawable(R.drawable.blank);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: Blank");
        }
        else if ((threshold > 1) && (threshold <= 20)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart20);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: 20%");
        }

        else if ((threshold > 20) && (threshold <= 40)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart40);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: 40%");
        }

        else if ((threshold > 40) && (threshold <= 60)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart60);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: 60%");
        }

        else if ((threshold > 60) && (threshold <= 80)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart80);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: 80%");
        }

        else {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart100);
            Log.d(Constants.TAG, "Calculate: getSmsHeart: 100%");
        }
        return smsHeart;
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
    Drawable calculateHeart(int outgoing, int incoming, int state, Context context){

        int threshold = 0;
        Drawable drawable = null;

//        if ((outgoing + incoming) * 100 != 0)
//        {
//            Log.d(Constants.TAG, " Threshold smsHeartMe "+ (int)(((double)incoming/((double)outgoing + (double)incoming)) * 100));
//        }

        switch (state)
        {
            case 1: // smsheartme

                if ((outgoing + incoming) * 100 != 0){
                    try{
                        threshold = (int)(((double)outgoing/((double)outgoing + (double)incoming)) * 100);
                        Log.d(Constants.TAG, "Calculate: calculateHeart: smsHeartMe: threshold: " + threshold);
                    }
                    catch (ArithmeticException e) {
                        threshold = 0;
                        Log.e(Constants.TAG, "Calculate: calculateHeart exception: ", e);
                    }
                    drawable = getSmsHeart(threshold, context);
                }
                else{
                    drawable = getSmsHeart(0, context);
                }
                break;

            case 2: // callheartme

                if ((outgoing + incoming) * 100 != 0){

                    try{
                        threshold = (int)(((double)outgoing/((double)outgoing + (double)incoming)) * 100);
                        Log.d(Constants.TAG, "Calculate: calculateHeart: callHeartMe: threshold: " + threshold);
                    }
                    catch (ArithmeticException e) {
                        threshold = 0;
                        Log.e(Constants.TAG, "Calculate: calculateHeart exception: ", e);
                    }
                    drawable = getCallHeart(threshold, context);
                }
                else{
                    drawable = getCallHeart(0, context);
                }
                break;

            case 3: // smshearyou

                if ((outgoing + incoming) * 100 != 0){

                    try{
                        threshold = (int)(((double)incoming/((double)outgoing + (double)incoming)) * 100);
                        Log.d(Constants.TAG, "Calculate: calculateHeart: smsHeartYou: threshold: " + threshold);
                    }
                    catch (ArithmeticException e) {
                        threshold = 0;
                        Log.e(Constants.TAG, "Calculate: calculateHeart exception: ", e);
                    }
                    getSmsHeart(threshold, context);
                }
                else drawable = getSmsHeart(0, context);
                break;

            case 4: // callheart you

                if ((outgoing + incoming) * 100 != 0){

                    try{
                        threshold = (int)(((double)incoming/((double)outgoing + (double)incoming)) * 100);
                        Log.d(Constants.TAG, "Calculate: calculateHeart: callHeartYou: threshold: " + threshold);
                    }
                    catch (ArithmeticException e) {
                        threshold = 0;
                        Log.e(Constants.TAG, "Calculate: calculateHeartYou exception: ", e);
                    }
                    drawable = getCallHeart(threshold, context);
                }
                else drawable = getCallHeart(0, context);
                break;
        }
        return drawable;
    }
 }

