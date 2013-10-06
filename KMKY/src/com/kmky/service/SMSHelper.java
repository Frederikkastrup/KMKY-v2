package com.kmky.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.kmky.data.DataModel;
import com.kmky.util.Constants;

/**
 * Created by FrederikKastrup on 19/09/13.
 */
public class SMSHelper
{

	private Context mContext;
	private IncomingSMS incomingSMS;
	private ContentObserver outgoingSms;
	private Handler handle;

	public SMSHelper(Context ctx) {
		this.mContext = ctx;

		incomingSMS = new IncomingSMS();
		outgoingSms = new OutgoingSMS(handle);
	}

	private class IncomingSMS extends BroadcastReceiver
	{
		// Get object of Smsmanager
		final SmsManager sms = SmsManager.getDefault();

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// Retrieves a map of extended data from the intent.
			final Bundle bundle = intent.getExtras();
			try
			{
				if (bundle != null)
				{
					final Object[] pdusObj = (Object[]) bundle.get("pdus");

					for (int i = 0; i < pdusObj.length; i++)
					{

						SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
						String incomingSMS = currentMessage.getDisplayOriginatingAddress();

						String subString = incomingSMS.substring(0, 3);


						if (subString.equals("+61"))
						{
							incomingSMS = incomingSMS.substring(3, incomingSMS.length());
						}

						long timeInMillisecond = getTime();

                        Log.d(Constants.TAG, "SMSHelper: incomingSms: " + "0".concat(incomingSMS));

						DataModel.getInstance(context).addLog("0".concat(incomingSMS), "sms", timeInMillisecond, 1, 0);


					} // end for loop
				} // bundle is null

			} catch (Exception e)
			{
				Log.e(Constants.TAG, "SMSHelper: incomingSMS ", e);
			}

		}
	}

	private class OutgoingSMS extends ContentObserver
	{
		public OutgoingSMS(Handler handler) {
			super(handler);
		}

		@Override
		public boolean deliverSelfNotifications()
		{
			return true;
		}

		@Override
		public void onChange(boolean selfChange)
		{
			super.onChange(selfChange);

			Uri uri = Uri.parse("content://sms/outbox");

			Cursor cur = mContext.getContentResolver().query(uri, null, null, null, null);

			String outgoingSMS = null;

            if (cur != null && cur.moveToFirst())
            {

			try
			{
				// Moves cursor to address
				cur.moveToFirst();

				outgoingSMS = cur.getString(cur.getColumnIndex("address"));

				String subString = outgoingSMS.substring(0, 3);

				if (subString.equals("+61"))
				{
					outgoingSMS = outgoingSMS.substring(3, outgoingSMS.length());
				}

                Log.d(Constants.TAG, "CallHelper: outgoingSMS substring" + subString);

			} catch (CursorIndexOutOfBoundsException e)
			{
				Log.e(Constants.TAG, "SMSHelper: outgoingSMS", e);
			}

			finally
			{

				cur.close();
			}

			if (outgoingSMS != null)
			{
				// Getting the number
				long timeInMillisecond = getTime();

				DataModel.getInstance(mContext).addLog("0".concat(outgoingSMS), "sms", timeInMillisecond, 0, 1);

                Log.d(Constants.TAG, "SMSHelper: outgoingSMS: " + outgoingSMS);
			}
		}
        }

	}

	public void start()
	{
		// Setting the BroadcastReciever for Incoming SMS
		String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
		IntentFilter IntentFilter = new IntentFilter();
		IntentFilter.addAction(ACTION_SMS_RECEIVED);
		mContext.registerReceiver(incomingSMS, IntentFilter);

		// Outgoing SMS
		ContentResolver contentResolver = mContext.getContentResolver();
		contentResolver.registerContentObserver(Uri.parse("content://sms"), true, outgoingSms);

	}

	public long getTime()
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
