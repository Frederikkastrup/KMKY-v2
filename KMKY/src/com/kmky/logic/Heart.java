package com.kmky.logic;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kmky.data.DataModel;
import com.kmky.data.LogEntry;
import com.kmky.data.Relations;
import com.kmky.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by FrederikKastrup on 6/10/13.
 */
public class Heart {

    private List<String> mMostContacted;
    private List<String> mLeastContacted;
    private List<String> mMostContactedYou;
    private List<String> mLeastContactedYou;
    private Context mContext;
    private DataModel mDM;

    /**
     * Constructor for Heart class
     * @param context
     */

    public Heart(Context context)
    {
        this.mContext = context;
        mDM = DataModel.getInstance(mContext);
    }

    /**
     * Returns lists of Relations depending on the chosen state
     * @param state
     * @return
     */

    public List<Relations> heartSizes(int state)
    {

        List<Relations> relations = new ArrayList<Relations>();
        Calculate cal = new Calculate(mContext);
        long timeStamp = getTime();
        Drawable smsHeartMe;
        Drawable callHeartMe;
        Drawable smsHeartYou;
        Drawable callHeartYou;
        LogEntry smsLog;
        LogEntry callLog;

        switch (state)
        {
            case 0:

                mMostContacted = mDM.fetchNumbersForMostContacted();

                for (String number : mMostContacted){


                    smsLog = mDM.fetchSMSLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.fetchCallLogsForPersonToDate(number, timeStamp);

                    Log.d(Constants.TAG, "Heart: heartSize: SMS: " + smsLog.getPhonenumber() + " incoming: " + smsLog.getIncoming() + " outgoing: " + smsLog.getOutgoing());
                    Log.d(Constants.TAG, "Heart: heartSize: Call: " + callLog.getPhonenumber() + " incoming: " + callLog.getIncoming() + " outgoing: " + callLog.getOutgoing());

                    try {

                    smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                    callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                    smsHeartYou = cal.calculateHeart(smsLog.getIncoming(),smsLog.getOutgoing(),3, mContext);
                    callHeartYou = cal.calculateHeart(callLog.getIncoming(), callLog.getOutgoing(),4,mContext);

                    relations.add(new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.d(Constants.TAG, "Heart: heartSize ", e);
                    }
                }

                break;

            case 1:
                mLeastContacted = mDM.fetchNumbersForLeastContacted();

                for (String number : mLeastContacted){

                    smsLog = mDM.fetchSMSLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.fetchCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getIncoming(),smsLog.getOutgoing(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getIncoming(), callLog.getOutgoing(),4,mContext);

                        relations.add(new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }

                }

                break;

            case 2:

                mMostContactedYou = mDM.fetchNumbersForMostContactedYou();

                for (String number : mMostContactedYou){

                    smsLog = mDM.fetchSMSLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.fetchCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getIncoming(),smsLog.getOutgoing(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getIncoming(), callLog.getOutgoing(),4,mContext);

                        relations.add(new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }

                }

                break;

            case 3:

                mLeastContactedYou = mDM.fetchNumbersForLeastContactedYou();

                for (String number : mLeastContactedYou){

                    smsLog = mDM.fetchSMSLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.fetchCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getIncoming(),smsLog.getOutgoing(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getIncoming(), callLog.getOutgoing(),4,mContext);

                        relations.add(new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }

                }

                break;
        }

        return relations;


    }

    /**
     * Method for returning the time as a long
     * @return
     */

    private long getTime()
    {
        Date now = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;

        try
        {
            date = dateFormat.parse(timeStamp);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        long timeMilliseconds = date.getTime();
        return timeMilliseconds;

    }


}
