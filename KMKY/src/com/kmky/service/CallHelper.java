package com.kmky.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kmky.data.DataModel;
import com.kmky.util.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class CallHelper
{
	private Context mContext;
	private TelephonyManager mtelephonyManager;
	private IncomingCalls mCallStateListener;
	private OutgoingCalls mOutgoingReceiver;

	public CallHelper(Context context) {
		this.mContext = context;
		mCallStateListener = new IncomingCalls();
		mOutgoingReceiver = new OutgoingCalls();
	}

	// Sets the BroadcastReciever and Telephonymanager
	public void start()
	{
		mtelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		mtelephonyManager.listen(mCallStateListener, PhoneStateListener.LISTEN_CALL_STATE);

		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		mContext.registerReceiver(mOutgoingReceiver, intentFilter);
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

    /**
     * PhoneStateListner for incoming calls
     */
	private class IncomingCalls extends PhoneStateListener
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			switch (state)
			{
			case TelephonyManager.CALL_STATE_RINGING:

				String subString = incomingNumber.substring(0, 3);

				if (subString.equals("+61"))
				{
					incomingNumber = incomingNumber.substring(3, incomingNumber.length());
				}

                Log.d(Constants.TAG, "CallHelper: incomingCalls substring" + subString);
				long timeInMilliseconds = getTime();

				DataModel.getInstance(mContext).addLog(incomingNumber, "call", timeInMilliseconds, 1, 0);
                Log.d(Constants.TAG, "CallHelper: incomingCall: " + incomingNumber);
				break;
			}
		}
	}

    /**
     * BroadcastReciever for outgoing calls
     */
	public class OutgoingCalls extends BroadcastReceiver{

        public OutgoingCalls() {
		}

		@Override
		public void onReceive(Context context, Intent intent)
		{
			String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

			// Getting the time
			long timeMilliseconds = getTime();

			// Sorting away 0
			String subString = outgoingNumber.substring(0, 3);

            Log.d(Constants.TAG, "CallHelper: outgoingCalls substring" + subString);

			if (subString.equals("+61"))
			{
				outgoingNumber = outgoingNumber.substring(3, outgoingNumber.length());
			}

			DataModel.getInstance(mContext).addLog((outgoingNumber), "call", timeMilliseconds, 0, 1);
            Log.d(Constants.TAG, "CallHelper: outgoingCalls: " + outgoingNumber);
		}
	}
}
