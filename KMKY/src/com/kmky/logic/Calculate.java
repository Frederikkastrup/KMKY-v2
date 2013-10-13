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
        Drawable callHeart = null;

        if (threshold == 0){
            callHeart = context.getResources().getDrawable(R.drawable.blank);
        }

        else if ((threshold > 1) && (threshold <= 5)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart5);
        }

        else if ((threshold > 5) && (threshold <= 10)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart10);
        }

        else if ((threshold > 10) && (threshold <= 15)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart15);
        }

        else if ((threshold > 15) && (threshold <= 20)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart20);
        }

        else if ((threshold > 20) && (threshold <= 25)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart25);
        }

        else if ((threshold > 25) && (threshold <= 30)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart30);
        }

        else if ((threshold > 30) && (threshold <= 35)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart35);
        }

        else if ((threshold > 35) && (threshold <= 40)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart40);
        }

        else if ((threshold > 40) && (threshold <= 45)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart45);
        }

        else if ((threshold > 45) && (threshold <= 50)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart50);
        }

        else if ((threshold > 50) && (threshold <= 55)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart55);
        }

        else if ((threshold > 55) && (threshold <= 60)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart60);
        }

        else if ((threshold > 60) && (threshold <= 65)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart65);
        }

        else if ((threshold > 65) && (threshold <= 70)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart70);
        }

        else if ((threshold > 70) && (threshold <= 75)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart75);
        }

        else if ((threshold > 75) && (threshold <= 80)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart80);
        }

        else if ((threshold > 80) && (threshold <= 85)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart85);
        }

        else if ((threshold > 85) && (threshold <= 90)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart90);
        }

        else if ((threshold > 90) && (threshold <= 95)) {
            callHeart = context.getResources().getDrawable(R.drawable.callheart95);
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
//        Log.d(Constants.TAG, "Calculate: getSmsHeart: Threshold: " + threshold);

        if (threshold == 0){
            smsHeart = context.getResources().getDrawable(R.drawable.blank);
//            Log.d(Constants.TAG, "Calculate: getSmsHeart: Blank");
        }
        else if ((threshold > 1) && (threshold <= 5)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart5);
        }

        else if ((threshold > 5) && (threshold <= 10)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart10);
        }

        else if ((threshold > 10) && (threshold <= 15)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart15);
        }

        else if ((threshold > 15) && (threshold <= 20)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart20);
        }

        else if ((threshold > 20) && (threshold <= 25)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart25);
        }

        else if ((threshold > 25) && (threshold <= 30)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart30);
        }

        else if ((threshold > 30) && (threshold <= 35)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart35);
        }

        else if ((threshold > 35) && (threshold <= 40)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart40);
        }

        else if ((threshold > 40) && (threshold <= 45)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart45);
        }

        else if ((threshold > 45) && (threshold <= 50)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart50);
        }

        else if ((threshold > 50) && (threshold <= 55)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart55);
        }

        else if ((threshold > 55) && (threshold <= 60)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart60);
        }

        else if ((threshold > 60) && (threshold <= 65)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart65);
        }

        else if ((threshold > 65) && (threshold <= 70)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart70);
        }

        else if ((threshold > 70) && (threshold <= 75)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart75);
        }

        else if ((threshold > 75) && (threshold <= 80)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart80);
        }

        else if ((threshold > 80) && (threshold <= 85)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart85);
        }

        else if ((threshold > 85) && (threshold <= 90)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart90);
        }

        else if ((threshold > 90) && (threshold <= 95)) {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart95);
        }

        else {
            smsHeart = context.getResources().getDrawable(R.drawable.smsheart100);
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

//        Log.d(Constants.TAG, "Calculate: incomingThreshold: incoming: " + incoming + " outgoing: " + outgoing);
        if ((outgoing + incoming) * 100 != 0) {
                int totalCommunication = incoming + outgoing;
                incomingThreshold = (int) (((double)incoming/(double)totalCommunication) * 100);
//              Log.d(Constants.TAG, "Calculate: calculateHeart: incomingThreshold: " + incomingThreshold);
        }
        return incomingThreshold;
    }

    private int getOutgoingThreshold(int incoming, int outgoing) {
//        Log.d(Constants.TAG, "Calculate: outgoingThreshold: incoming: " + incoming + " outgoing: " + outgoing);
        int outgoingThreshold = 0;

        if ((outgoing + incoming) * 100 != 0) {
                int totalCommunication = incoming + outgoing;
//                Log.d(Constants.TAG, "Calculate: outgoingThreshold: total communication: " + totalCommunication);
                outgoingThreshold = (int) (((double)outgoing/(double)totalCommunication) * 100);
//                Log.d(Constants.TAG, "Calculate: calculateHeart: outgoingThreshold: " + outgoingThreshold);
        }
        return outgoingThreshold;
    }
 }

