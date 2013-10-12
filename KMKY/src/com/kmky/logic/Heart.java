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
    private Calculate mCal;

    /**
     * Constructor for Heart class
     * @param context
     */

    public Heart(Context context) {
        this.mContext = context;
        mDM = DataModel.getInstance(mContext);
        mCal = new Calculate(context);
    }

    /**
     * Returns lists of Relations depending on the chosen state
     * @param state
     * @return
     */

    public List<Relations> HeartSizesMyRelationships(int state)
    {

        List<Relations> relations = new ArrayList<Relations>();
        long timeStamp = getTime();
        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;
        LogEntry smsLog, callLog;

        switch (state)
        {
            case 0:

                mMostContacted = mDM.getNumbersForMostContacted(mDM.getUniquePhoneNumbers(), 2);

                for (String number : mMostContacted){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, mContext), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.e(Constants.TAG, "Heart: heartSize nullpointerexception", e);
                    }
                }
                break;

            case 1:
                mLeastContacted = mDM.getNumbersForLeastContacted(mDM.getUniquePhoneNumbers(), 2);

                for (String number : mLeastContacted){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, mContext), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 2:

                mMostContactedYou = mDM.getNumbersForMostContactedYou(mDM.getUniquePhoneNumbers(), 2);

                for (String number : mMostContactedYou){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, mContext), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 3:

                mLeastContactedYou = mDM.getNumbersForLeastContactedYou(mDM.getUniquePhoneNumbers(), 2);

                for (String number : mLeastContactedYou){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, mContext);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, mContext);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, mContext);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,mContext);
                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, mContext), smsHeartYou, callHeartYou));
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
     * Method for getting a Relations object for a single day for a single phonenumber. Used for animations
     * @param number
     * @param timestamp
     * @param context
     * @return
     */
    public Relations HeartSizeSingleDay(String number, long timestamp, Context context)
    {

        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;

        LogEntry smsLog = mDM.getSmsLogsForPeronOnSpecificDate(number, timestamp);
        LogEntry callLog = mDM.getCallLogsForPeronOnSpecificDate(number, timestamp);

        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);

        Relations relation = new Relations(smsHeartMe, callHeartMe, number, smsHeartYou, callHeartYou);

        return relation;
    }

    /**
     * Method for getting a Relations object to date for a single phone number. Used in favorites.
     * @param phonenumber
     * @param context
     * @return
     */

    public Relations HeartSizeToDateSinglePerson(String phonenumber, Context context){

        long timestamp = getTime();
        Relations relation = null;
        String subString = phonenumber.substring(0, 3);
        phonenumber = phonenumber.replace(" ","");

        if (subString.equals("+61")){
            phonenumber = phonenumber.substring(3, phonenumber.length());
            phonenumber = "0".concat(phonenumber);
        }

        LogEntry smsLog = mDM.getSmsLogsForPersonToDate(phonenumber,timestamp);
        LogEntry callLog = mDM.getCallLogsForPersonToDate(phonenumber, timestamp);

        try {

            Drawable smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
            Drawable callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
            Drawable smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
            Drawable callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);

            relation = new Relations(smsHeartMe, callHeartMe, phonenumber, smsHeartYou,callHeartYou);
        }
        catch (NullPointerException e) {
            Log.e(Constants.TAG, "Heart: HeartSizeToDateSinglePerson: NullPointerException", e);
        }
        return relation;
    }

    /**
     * Gets number of incoming sms and calls. Used in my RelationshipZoom
     * @param phonenumber
     * @param state
     * @return
     */
    public LogEntry SmsAndCallCount(String phonenumber, int state)
    {
        LogEntry smsOrCallLog = null;
        String subString = phonenumber.substring(0, 3);
        phonenumber = phonenumber.replace(" ","");

        if (subString.equals("+61")){
            phonenumber = phonenumber.substring(3, phonenumber.length());
            phonenumber = "0".concat(phonenumber);
        }

        switch (state) {

            case 1:
                smsOrCallLog = mDM.getSmsLogsForPersonToDate(phonenumber, getTime());
                break;

            case 2:
                smsOrCallLog = mDM.getCallLogsForPersonToDate(phonenumber, getTime());
                break;
        }
        return smsOrCallLog;
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

    /**
     * Gets Contact name from a phone number
     * @param number
     * @return
     */
    public String getContactNameFromNumber(String number, Context context) {

        String name = "";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            cursor.close();
        }

        if (name == ""){
            return number;
        }
        else {
            return name;
        }
    }

    /**
     * Gets a phone number from a contact name
     * @param name
     * @param context
     * @return
     */
    public String getPhoneNumberFromName(String name, Context context) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if(ret==null)
            ret = "Unsaved";
        return ret;
    }

    public List<Relations> HeartSizeFavorites(int state, List<String> numbers, Context context)
    {

        List<Relations> relations = new ArrayList<Relations>();
        long timeStamp = getTime();
        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;
        LogEntry smsLog, callLog;

        switch (state)
        {
            case 0:

                mDM.getNumbersForMostContacted(numbers, 1);

                for (String number : numbers){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);

                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, context), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.e(Constants.TAG, "Heart: heartSize nullpointerexception", e);
                    }
                }
                break;

            case 1:

                mDM.getNumbersForLeastContacted(numbers, 1);

                for (String number : numbers){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, context), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 2:

                mDM.getNumbersForMostContactedYou(numbers, 1);

                for (String number : numbers){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);


                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, context), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;

            case 3:

                mDM.getNumbersForLeastContactedYou(numbers, 1);

                for (String number : numbers){

                    smsLog = mDM.getSmsLogsForPersonToDate(number, timeStamp);
                    callLog = mDM.getCallLogsForPersonToDate(number, timeStamp);

                    try {

                        smsHeartMe = mCal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, context);
                        callHeartMe = mCal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, context);
                        smsHeartYou = mCal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, context);
                        callHeartYou = mCal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,context);
                        relations.add(new Relations(smsHeartMe, callHeartMe , getContactNameFromNumber(number, context), smsHeartYou, callHeartYou));
                    }
                    catch (NullPointerException e){
                        Log.i(Constants.TAG, "Heart: heartSize ", e);
                    }
                }
                break;
        }
        return relations;
    }
}
