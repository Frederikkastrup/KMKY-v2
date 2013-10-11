package com.kmky.logic;


import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
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

    public Heart(Context context) {
        this.mContext = context;
        mDM = DataModel.getInstance(mContext);
    }

    /**
     * Returns lists of Relations depending on the chosen state
     * @param state
     * @return
     */

    public List<Relations> HeartSizesToDate(int state)
    {

        List<Relations> relations = new ArrayList<Relations>();
        Calculate cal = new Calculate(mContext);
        long timeStamp = getTime();
        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;
        LogEntry smsLog, callLog;

        switch (state)
        {
            case 0:

                mMostContacted = mDM.getNumbersForMostContacted();

                for (String number : mMostContacted){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.e(Constants.TAG, "Heart: heartSize nullpointerexception", e);
                    }
                }
                break;

            case 1:
                mLeastContacted = mDM.getNumbersForLeastContacted();

                for (String number : mLeastContacted){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 2:

                mMostContactedYou = mDM.getNumbersForMostContactedYou();

                for (String number : mMostContactedYou){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 3:

                mLeastContactedYou = mDM.getNumbersForLeastContactedYou();

                for (String number : mLeastContactedYou){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;
        }
        return relations;
    }

    public Relations HeartSizeSingleDay(String number, long timestamp)
    {

        Calculate cal = new Calculate(mContext);
        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;

        LogEntry smsLog = mDM.getSmsLogsForPeronOnSpecificDate(number, timestamp);
        LogEntry callLog = mDM.getCallLogsForPeronOnSpecificDate(number, timestamp);

        smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
        callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
        smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
        callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);

        Relations relation = new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou);

        return relation;
    }

    /**
     * Method for returning the time as a long
     * @return
     */
    private long getTime() {
        Date now = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;

        try  {
            date = dateFormat.parse(timeStamp);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        long timeMilliseconds = date.getTime();
        return timeMilliseconds;
    }

    private String getContactNameFromNumber(String number) {

        String name = "";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor = mContext.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);
        if (cursor.moveToFirst()) {
           name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (name == ""){
            return number;
        }
        else {
        return name;
        }
    }
}
