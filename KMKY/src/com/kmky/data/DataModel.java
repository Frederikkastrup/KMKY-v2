package com.kmky.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.kmky.util.Constants;

/**
 * Created by FrederikKastrup on 23/09/13.
 */
public class DataModel
{

	private ArrayList<LogEntry> mLogs;
	private static DataModel instance;
	private Context mContext;
	private KMKYSQLiteHelper mDbHelper;

	/**
	 * Singleton. Initialise the model. Load the data from the database into the
	 * model
	 * 
	 * @param ctx
	 */
	private DataModel(Context ctx) {
		mDbHelper = new KMKYSQLiteHelper(ctx);

		// load data into logs
		mLogs = mDbHelper.getLogs();
	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static DataModel getInstance(Context ctx)
	{
		if (instance == null)
			instance = new DataModel(ctx);

		return instance;
	}

	/**
	 * Get the phone numbers
	 * 
	 * @return
	 */
	private List<String> getUniquePhoneNumbers()
	{
		List<String> phonenumberList = new ArrayList<String>();

		for (LogEntry log : mLogs)
		{
			if (phonenumberList.contains(log.getPhonenumber()))
			{
				// this is weird
			} else
			{
				phonenumberList.add(log.getPhonenumber());
			}
		}

		return phonenumberList;
	}

	public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
	{

		// Removes spacing in phonenumber
		phonenumber = phonenumber.replace(" ", "");

		// Check if log exists or else create it.
		LogEntry newLog = new LogEntry(phonenumber, type, date, incoming, outgoing);
		LogEntry oldLog = null;

		// Checks if logs is empty
		if (mLogs.size() == 0)
		{
			Log.i(Constants.TAG, "Logs is empty");
			mLogs.add(newLog);
			mDbHelper.addLog(phonenumber, type, date, incoming, outgoing);

		} else
		{
			// Checks if the logs exists.
			boolean update = false;

			// Id for the log.
			long id = 0;

			android.util.Log.i(Constants.TAG, phonenumber + " " + type + " " + date);

			for (LogEntry log : mLogs)
			{
				if (log.getPhonenumber().equals(phonenumber) && log.getType().equals(type) && log.getDate() == date)
				{
					update = true;
					id = log.getId();
					oldLog = new LogEntry(log.getPhonenumber(), log.getType(), log.getDate(), log.getIncoming(), log.getOutgoing());
				} else
				{
					update = false;
					oldLog = new LogEntry(log.getPhonenumber(), log.getType(), log.getDate(), log.getIncoming(), log.getOutgoing());
				}

			}

			if (update)
			{

				android.util.Log.i(Constants.TAG, "Updating log");
				updateLog(newLog, oldLog, id);
			} else
			{
				android.util.Log.i(Constants.TAG, "Adding log");
				mLogs.add(newLog);
			}
		}
	}

	/**
	 * Remove old log from the list and replace it with a new log
	 * 
	 * @param newLog
	 * @param oldLog
	 * @param id
	 */
	public void updateLog(LogEntry newLog, LogEntry oldLog, long id)
	{
		String phonenumber = oldLog.getPhonenumber();
		String type = oldLog.getType();
		long date = oldLog.getDate();
		int incoming = oldLog.getIncoming();
		int outgoing = oldLog.getOutgoing();

		if (newLog.getIncoming() != 0)
		{
			mLogs.remove(oldLog);
			incoming++;
		} else
		{
			mLogs.remove(oldLog);
			outgoing++;
		}

		Log.i(Constants.TAG, "Adding new update");
		
		mLogs.add(newLog);
	}

	public LogEntry fetchSMSLogsForPeronOnSpecificDate(String phonenumber, long date)
	{

		LogEntry emptyLog = new LogEntry(phonenumber, "sms", date, 0, 0);

		for (LogEntry log : mLogs)
		{
			if (log.getPhonenumber() == phonenumber && log.getDate() == date && log.getType() == "sms")
			{
				return log;
			}
		}

		return emptyLog;
	}

	public LogEntry fetchCallLogsForPeronOnSpecificDate(String phonenumber, long date)
	{
		LogEntry emptyLog = new LogEntry(phonenumber, "call", date, 0, 0);

		for (LogEntry log : mLogs)
		{
			if (log.getPhonenumber() == phonenumber && log.getDate() == date && log.getType() == "sms")
			{
				return log;
			}

		}

		return emptyLog;

	}

	public LogEntry fetchSMSLogsForPersonToDate(String phonenumber, long date)
	{
		int incoming = 0;
		int outgoing = 0;
		String type = "sms";

		for (LogEntry log : mLogs)
		{
			if (log.getPhonenumber() == phonenumber && log.getType() == "sms")
			{
				incoming = incoming + log.getIncoming();
				outgoing = outgoing + log.getOutgoing();
			}
		}

		LogEntry logToDate = new LogEntry(phonenumber, type, date, incoming, outgoing);

		return logToDate;
	}

	public LogEntry fetchCallLogsForPersonToDate(String phonenumber, long date)
	{
		int incoming = 0;
		int outgoing = 0;
		String type = "call";

		for (LogEntry log : mLogs)
		{
			if (log.getPhonenumber() == phonenumber && log.getType() == "call")
			{
				incoming = incoming + log.getIncoming();
				outgoing = outgoing + log.getOutgoing();
			}
		}

		LogEntry logToDate = new LogEntry(phonenumber, type, date, incoming, outgoing);

		return logToDate;
	}

	public List<String> fetchNumbersForLeastContacted()
	{

		// Returns phone list of ten least contacted phone numbers

		List<String> phonenumberList = getUniquePhoneNumbers();
		List<TopTen> SortList = new ArrayList<TopTen>();

		Date now = new Date();
		Long milliSeconds = now.getTime();

		// Find phone numbers for least contacted
		for (String phonenumber : phonenumberList)
		{

			int outgoingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();
			int outgoingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();

			// Calculating the total communication for each phonenumber
			int totalCommunication = outgoingCall + outgoingSMS;

			SortList.add(new TopTen(phonenumber, totalCommunication, 0));
			android.util.Log.i(Constants.TAG, Integer.toString(totalCommunication));

		}

		// Sort the list
		Collections.sort(SortList);

		// Clears phone number list
		phonenumberList.clear();

		// Add the phone numbers to list
		if (SortList.size() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				String phonenumber = null;

				TopTen topTen = SortList.get(i);

				phonenumber = topTen.getPhonenumber();

				phonenumberList.add(phonenumber);
			}

		} else
		{

			for (TopTen topTen : SortList)
			{
				String phonenumner = null;

				phonenumberList.add(topTen.getPhonenumber());
			}

		}

		for (String number : phonenumberList)
		{
			android.util.Log.i(Constants.TAG, number);
		}

		// Returns the top ten least contacted numbers
		return phonenumberList;
	}

	public List<String> fetchNumbersForMostContacted()
	{
		// Returns phone list of ten most contacted phone numbers

		List<String> phonenumberList = getUniquePhoneNumbers();
		List<TopTen> SortList = new ArrayList<TopTen>();

		Date now = new Date();
		Long milliSeconds = now.getTime();

		// Find phone numbers for least contacted
		for (String phonenumber : phonenumberList)
		{

			int outgoingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();
			int outgoingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();

			// Calculating the total communication for each phonenumber
			int totalCommunication = outgoingCall + outgoingSMS;

			SortList.add(new TopTen(phonenumber, totalCommunication, 0));

		}

		// Sort the list
		Collections.sort(SortList, Collections.reverseOrder());

		// Clears phone number list
		phonenumberList.clear();

		// Add the phone numbers to list
		if (SortList.size() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				String phonenumber = null;

				TopTen topTen = SortList.get(i);

				phonenumber = topTen.getPhonenumber();

				phonenumberList.add(phonenumber);
			}

		} else
		{

			for (TopTen topTen : SortList)
			{
				String phonenumner = null;

				phonenumberList.add(topTen.getPhonenumber());
			}

		}

		for (String number : phonenumberList)
		{
			android.util.Log.i(Constants.TAG, number);
		}

		// Returns the top ten least contacted numbers
		return phonenumberList;
	}

	public List<String> fetchNumbersForLeastContactedYou()
	{
		// Returns phone list of ten most contacted phone numbers

		List<String> phonenumberList = getUniquePhoneNumbers();
		List<TopTen> SortList = new ArrayList<TopTen>();

		Date now = new Date();
		Long milliSeconds = now.getTime();

		// Find phone numbers for least contacted
		for (String phonenumber : phonenumberList)
		{

			int incomingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();
			int incomingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();

			// Calculating the total communication for each phonenumber
			int totalCommunication = incomingCall + incomingSMS;

			SortList.add(new TopTen(phonenumber, totalCommunication, 0));

		}

		// Sort the list
		Collections.sort(SortList);

		// Clears phone number list
		phonenumberList.clear();

		// Add the phone numbers to list
		if (SortList.size() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				String phonenumber = null;

				TopTen topTen = SortList.get(i);

				phonenumber = topTen.getPhonenumber();

				phonenumberList.add(phonenumber);
			}

		} else
		{

			for (TopTen topTen : SortList)
			{
				String phonenumner = null;

				phonenumberList.add(topTen.getPhonenumber());
			}

		}

		for (String number : phonenumberList)
		{
			android.util.Log.i(Constants.TAG, number);
		}

		// Returns the top ten least contacted numbers
		return phonenumberList;
	}

	public List<String> fetchNumbersForMostContactedYou()
	{
		// Returns phone list of ten most contacted phone numbers

		List<String> phonenumberList = getUniquePhoneNumbers();
		List<TopTen> SortList = new ArrayList<TopTen>();

		Date now = new Date();
		Long milliSeconds = now.getTime();

		// Find phone numbers for least contacted
		for (String phonenumber : phonenumberList)
		{

			int incomingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();
			int incomingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();

			// Calculating the total communication for each phonenumber
			int totalCommunication = incomingCall + incomingSMS;

			SortList.add(new TopTen(phonenumber, totalCommunication, 0));

		}

		// Sort the list
		Collections.sort(SortList, Collections.reverseOrder());

		// Clears phone number list
		phonenumberList.clear();

		// Add the phone numbers to list
		if (SortList.size() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				String phonenumber = null;

				TopTen topTen = SortList.get(i);

				phonenumber = topTen.getPhonenumber();

				phonenumberList.add(phonenumber);
			}

		} else
		{

			for (TopTen topTen : SortList)
			{
				String phonenumner = null;

				phonenumberList.add(topTen.getPhonenumber());
			}

		}

		for (String number : phonenumberList)
		{
			android.util.Log.i(Constants.TAG, number);
		}

		// Returns the top ten least contacted numbers
		return phonenumberList;
	}

}
