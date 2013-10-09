package com.kmky.service;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.kmky.data.DataModel;
import com.kmky.util.Constants;

/**
 * Created by FrederikKastrup on 19/09/13.
 */
public class SMSHelper{

    private static  final String CONTENT_SMS = "content://sms/";
    private Context mContext;
    private IncomingSMS mIncomingSms;
    private ContentObserver mOutgoingSms;
    private Handler mHandle;
    private String mLastSentSMSMd5 = "";

    public SMSHelper(Context context) {
        this.mContext = context;
        mIncomingSms = new IncomingSMS();
        mOutgoingSms = new OutgoingSMS(mHandle);
    }

    /**
     * Sets the broadcast reciever for incoming sms.
     * Registers contentobserver for outgoing sms.
     */

    public void start(){

        String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        IntentFilter IntentFilter = new IntentFilter();
        IntentFilter.addAction(ACTION_SMS_RECEIVED);
        mContext.registerReceiver(mIncomingSms, IntentFilter);

        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms/"), true, mOutgoingSms);
    }

    /**
     * Gets the current time as a long. Used for incoming or outgoing sms
     * @return
     */

    public long getTime() {
        Date now = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try{
            date = dateFormat.parse(timeStamp);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        long timeMilliseconds = date.getTime();
        return timeMilliseconds;
    }

    /**
     * Broadcast receiver for incoming sms.
     */
    private class IncomingSMS extends BroadcastReceiver{

        // Get object of Smsmanager
        final SmsManager sms = SmsManager.getDefault();

        @Override
        public void onReceive(Context context, Intent intent){
            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();

            try{
                if (bundle != null){
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++){

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phonenumber = currentMessage.getDisplayOriginatingAddress();
                        String subString = phonenumber.substring(0, 3);

                        if (subString.equals("+61")){
                            phonenumber = phonenumber.substring(3, phonenumber.length());
                            phonenumber = "0".concat(phonenumber);
                        }
                        Log.e(Constants.TAG, "SMSHelper: incoming sms: number " + phonenumber);

                        long timeInMillisecond = getTime();
                        DataModel.getInstance(context).addLog(phonenumber, "sms", timeInMillisecond, 1, 0);
                    }
                }
            }
            catch (Exception e){
                Log.e(Constants.TAG, "SMSHelper: incoming sms ", e);
            }
        }
    }

    /**
     * Content observer for outgoing sms
     */
    private class OutgoingSMS extends ContentObserver {
        public OutgoingSMS(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications(){
            return true;
        }

        @Override
        public void onChange(boolean selfChange){
            super.onChange(selfChange);

            Uri smsuri = Uri.parse(CONTENT_SMS);
            Cursor cursor = mContext.getContentResolver().query(smsuri, null, null, null, null);
            String phonenumber = null;


            if (cursor != null && cursor.moveToFirst()){

                try{
                    // Moves cursor to address
                    cursor.moveToFirst();

                    String type = cursor.getString(cursor.getColumnIndex("type"));

                    if (type.equals("2")){

                        String protocol = cursor.getString(cursor.getColumnIndex("protocol"));

                        if (protocol == null){

                            long messageId = cursor.getLong(cursor.getColumnIndex("_id"));
                            String timeStamp = cursor.getString(cursor.getColumnIndex("date"));
                            String body = cursor.getString(cursor.getColumnIndex("body"));
                            String md5 = md5(timeStamp + body + messageId);

//                            Log.d(Constants.TAG, "SMSHelper: OutgoingSMS: id: " + messageId);
//                            Log.d(Constants.TAG, "SMSHelper: OutgoingSMS: timestamp: " + timeStamp);

                            if (md5 != null){

                                if (!mLastSentSMSMd5.equalsIgnoreCase(md5)){

                                    mLastSentSMSMd5 = md5;
                                    phonenumber = cursor.getString(cursor.getColumnIndex("address"));
                                    String subString = phonenumber.substring(0, 3);

                                    //Trimming phone number to get 0xxxxxxxxx
                                    if (subString.equals("+61")){
                                        phonenumber = phonenumber.substring(3, phonenumber.length());
                                        phonenumber = "0".concat(phonenumber);
                                    }

                                    if (phonenumber != null ){
                                        long timeInMillisecond = getTime();
                                        DataModel.getInstance(mContext).addLog(phonenumber, "sms", timeInMillisecond, 0, 1);
                                        Log.d(Constants.TAG, "SMSHelper: outgoingSMS: " + phonenumber);
                                        cursor.close();
                                    }
                                }
                            }
                        }
                    }
                }
                catch (CursorIndexOutOfBoundsException e){
                    Log.e(Constants.TAG, "SMSHelper: outgoingSMS", e);
                }
                finally{
                    cursor.close();
                }
            }
        }
    }


    /**
     * Method for hashing strings. Used for outgoing sms to avoid multiple fires from onChange
     * @param in
     * @return
     */
    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Log.d(Constants.TAG, "SMSHelper: md5 exception :", e);
        }
        return null;
    }
}
