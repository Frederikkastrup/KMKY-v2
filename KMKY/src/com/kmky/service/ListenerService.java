package com.kmky.service;

/**
 * What does this class do? describe it!
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.kmky.data.DataModel;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class ListenerService extends Service
{
	private CallHelper mCallHelper;
	private SMSHelper mSmsHelper;
	private DataModel mDataModel;

	public void onCreate()
	{
		super.onCreate();
		mDataModel = DataModel.getInstance(getApplicationContext());
	}

	/**
	 * Invokes the CallHelper.class and SMSHelper.class. Restarts them if shut
	 * down.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);

		mSmsHelper = new SMSHelper(this);
		mCallHelper = new CallHelper(this);

		mCallHelper.start();
		mSmsHelper.start();

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}