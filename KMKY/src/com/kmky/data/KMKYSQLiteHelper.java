package com.kmky.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kmky.util.Constants;

/**
 * Created by FrederikKastrup on 21/09/13.
 */

public class KMKYSQLiteHelper extends SQLiteOpenHelper
{

	// Database name and Version
	private static final String DATABASE_NAME = "kmky_database.db";
	private static final int DATABASE_VERSION = 1;

	// Database Table
	private static final String TABLE_NAME = "logs";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_PHONENUMBER = "phonenumber";
	private static final String COLUMN_TYPE = "type";
	private static final String COLUMN_DATE = "date_time";
	private static final String COLUMN_INCOMING = "incoming";
	private static final String COLUMN_OUTGOING = "outgoing";
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PHONENUMBER + " TEXT, " + COLUMN_TYPE + " TEXT, " + COLUMN_DATE + " INTEGER, " + COLUMN_INCOMING + " INTEGER, " + COLUMN_OUTGOING + " INTEGER);";

	/**
	 * Constructor dfbdfghdfghfgh
	 * 
	 * @param ctx
	 */
	public KMKYSQLiteHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Method called during creation of the database
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
		Log.i(Constants.TAG, "database created");
	}

	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		// database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	/**
	 * Create new entry for a specific person
	 * 
	 * @param phonenumber
	 * @param type
	 * @param date
	 * @param incoming
	 * @param outgoing
	 * @return
	 */
	public long addLog(String phonenumber, String type, long date, int incoming, int outgoing)
	{
		SQLiteDatabase database = getWritableDatabase();
		long result = 0;

		try
		{
			ContentValues values = new ContentValues();

			values.put(COLUMN_PHONENUMBER, phonenumber);
			values.put(COLUMN_TYPE, type);
			values.put(COLUMN_DATE, date);
			values.put(COLUMN_INCOMING, incoming);
			values.put(COLUMN_OUTGOING, outgoing);

			result = database.insert(TABLE_NAME, null, values);

			Log.d(Constants.TAG, "MySQLiteHelper: addLog: " + phonenumber);
		} catch (Exception e)
		{
			Log.e(Constants.TAG, "MySQLiteHelper: addLog: ", e);
		} finally
		{
			database.close();
		}

		return result;
	}

	/**
	 * Update log for a specific person for a specific date
	 * 
	 * @param id
	 * @param incoming
	 * @param outgoing
	 */
	public void updateLog(long id, int incoming, int outgoing)
	{
		SQLiteDatabase database = getWritableDatabase();

		try
		{
			// Checks to see whether to increment incoming or outgoing
			if (incoming != 0)
			{
				database.execSQL("UPDATE logs SET incoming = incoming + 1 WHERE _id = " + id);
				Log.i(Constants.TAG, "Updating incoming log");
			} else
			{
				database.execSQL("UPDATE logs SET outgoing = outgoing + 1 WHERE _id = " + id);
				Log.i(Constants.TAG, "Updating outgoing log");
			}
		} catch (android.database.SQLException e)
		{
			Log.i(Constants.TAG, "Update log exception");
		} finally
		{

			database.close();
		}

	}

	/**
	 * Populate list with data from database
	 * 
	 * @return
	 */
	public ArrayList<LogEntry> getLogs()
	{
		ArrayList<LogEntry> result = new ArrayList<LogEntry>();

		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = null;

		try
		{
			cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

			if (cursor.moveToFirst())
			{
				do
				{
					String phonenumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
					String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
					long date = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
					int incoming = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMING));
					int outgoing = cursor.getInt(cursor.getColumnIndex(COLUMN_OUTGOING));

					LogEntry newLog = new LogEntry(phonenumber, type, date, incoming, outgoing);

					result.add(newLog);

				} while (cursor.moveToNext());
			}

			cursor.close();
		} catch (SQLException e)
		{
			Log.e(Constants.TAG, "KMKYSQLiteHelper: getLogs: ", e);
		} finally
		{
			database.close();
		}

		Log.d(Constants.TAG, "KMKYSQLiteHelper: getLogs: " + result.size());

		return result;
	}

}
