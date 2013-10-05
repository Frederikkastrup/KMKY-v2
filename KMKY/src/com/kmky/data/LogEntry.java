package com.kmky.data;

/**
 * Created by FrederikKastrup on 21/09/13.
 */
public class LogEntry
{

	private String phoneNumber;
	private String type;
	private long timeStamp;
	private int incoming;
	private int outgoing;
	private long id;

	/**
	 * Constructor for incoming sms or call
	 * 
	 * @param Phonenumber
	 * @param Type
	 * @param Date
	 * @param Incoming
	 * @param Outgoing
	 */
	public LogEntry(String phoneNumber, String type, long timeStamp, int incoming, int outgoing) {

		this.phoneNumber = phoneNumber;
		this.type = type;
		this.timeStamp = timeStamp;
		this.incoming = incoming;
		this.outgoing = outgoing;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getPhonenumber()
	{
		return phoneNumber;
	}

	public void setPhonenumer(String phonenumber)
	{
		this.phoneNumber = phonenumber;
	}

	public String getType()
	{
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	public long getDate()
	{
		return timeStamp;
	}

	public void setDate(long date)
	{
		this.timeStamp = date;
	}

	public int getIncoming()
	{
		return incoming;
	}

	public void setIncoming(int incoming)
	{

		this.incoming = incoming;

	}

	public int getOutgoing()
	{
		return outgoing;
	}

	public void setOutgoing(int outgoing)
	{

		this.outgoing = outgoing;
	}
}
