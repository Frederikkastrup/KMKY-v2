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

    public Calculate(Context context)
    {
        this.mContext = context;
    }

    /**
     * getCallHeart returns the appropriate heart drawable according to the threshold passed to it.
     * @param threshold
     * @param context
     * @return
     */
    private Drawable getCallHeart(int threshold, Context context)
    {
        Log.d(Constants.TAG, "Calculate: getCallHeart: Threshold: " + threshold);
        Drawable callHeart = null;

        if (threshold == 0){
            callHeart = context.getResources().getDrawable(R.drawable.blank);
            Log.d(Constants.TAG, "Calculate: getCallHeart: Blank");
        }
        else if ((threshold > 1) && (threshold <= 20)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart20);
            Log.d(Constants.TAG, "Calculate: getCallHeart: 20%");
        }

        else if ((threshold > 20) && (threshold <= 40)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart40);
            Log.d(Constants.TAG, "Calculate: getCallHeart: 40%");
        }

        else if ((threshold > 40) && (threshold <= 60)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart60);
            Log.d(Constants.TAG, "Calculate: getCallHeart: 60%");
        }

        else if ((threshold > 60) && (threshold <= 80)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart80);
            Log.d(Constants.TAG, "Calculate: getCallHeart: 80%");
        }

        else {
            callHeart = context.getResources().getDrawable(R.drawable.callheart100);
            Log.d(Constants.TAG, "Calculate: getCallHeart: 100%");
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
//        Log.d(Constants.TAG, "Calculate: getSmsHeart: Threshold: " + threshold);

        if (threshold == 0){
            smsHeart = context.getResources().getDrawable(R.drawable.blank);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: Blank");
        }
        else if ((threshold > 1) && (threshold <= 20)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart20);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: 20%");
        }

        else if ((threshold > 20) && (threshold <= 40)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart40);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: 40%");
        }

        else if ((threshold > 40) && (threshold <= 60)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart60);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: 60%");
        }

        else if ((threshold > 60) && (threshold <= 80)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart80);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: 80%");
        }

        else {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart100);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: 100%");
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
    public Drawable calculateHeart(int outgoing, int incoming, int state, Context context){

        int incomingThreshold = 0;
        int outgoingThreshold = 0;
        Drawable drawable = null;

        switch (state)
        {
            case 1: // smsheartme

                incomingThreshold = getIncomingThreshold(incoming, outgoing);
                drawable = getSmsHeart(incomingThreshold, context);

                break;

            case 2: // callheartme

                incomingThreshold = getIncomingThreshold(incoming, outgoing);
//                Log.d(Constants.TAG, "Calculate: calculateHeart: callHeartMe Threshold: " + incomingThreshold);
                drawable = getCallHeart(incomingThreshold, context);

                break;

            case 3: // smshearyou

                outgoingThreshold = getOutgoingThreshold(incoming, outgoing);
                drawable = getSmsHeart(outgoingThreshold, context);

                break;

            case 4: // callheart you

                 outgoingThreshold = getOutgoingThreshold(incoming, outgoing);
//                 Log.d(Constants.TAG, "Calculate: calculateHeart: callHeartYou Threshold: " + outgoingThreshold);
                 drawable = getCallHeart(outgoingThreshold, context);

                break;
        }
        return drawable;
    }

    private int getIncomingThreshold(int incoming, int outgoing) {
        int incomingThreshold = 0;

        Log.d(Constants.TAG, "Calculate: incomingThreshold: incoming: " + incoming + " outgoing: " + outgoing);
        if ((outgoing + incoming) * 100 != 0) {
                int totalCommunication = incoming + outgoing;
                incomingThreshold = (int) (((double)incoming/(double)totalCommunication) * 100);
              Log.d(Constants.TAG, "Calculate: calculateHeart: incomingThreshold: " + incomingThreshold);
        }
        return incomingThreshold;
    }

    private int getOutgoingThreshold(int incoming, int outgoing) {
        Log.d(Constants.TAG, "Calculate: outgoingThreshold: incoming: " + incoming + " outgoing: " + outgoing);
        int outgoingThreshold = 0;

        if ((outgoing + incoming) * 100 != 0) {
                int totalCommunication = incoming + outgoing;
                Log.d(Constants.TAG, "Calculate: outgoingThreshold: total communication: " + totalCommunication);
                outgoingThreshold = (int) (((double)outgoing/(double)totalCommunication) * 100);
                Log.d(Constants.TAG, "Calculate: calculateHeart: outgoingThreshold: " + outgoingThreshold);
        }
        return outgoingThreshold;
    }
 }

