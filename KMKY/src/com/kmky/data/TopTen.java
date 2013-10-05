package com.kmky.data;

/**
 * Created by FrederikKastrup on 28/09/13.
 */
public class TopTen implements Comparable<TopTen>
{

	private final int BEFORE = -1;
	private final int EQUAL = 0;
	private final int AFTER = 1;

	private String phonenumber;
	private int outgoing;
	private int incoming;

	/**
	 * Constructor. what do i do here?
	 * 
	 * @param phonenumber
	 * @param outgoing
	 * @param incoming
	 */
	public TopTen(String phonenumber, int outgoing, int incoming) {
		this.phonenumber = phonenumber;
		this.incoming = incoming;
		this.outgoing = outgoing;

	}

	public String getPhonenumber()
	{
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}

	public int getOutgoing()
	{
		return outgoing;
	}

	public void setOutgoing(int outgoing)
	{
		this.outgoing = outgoing;
	}

	public int getIncoming()
	{
		return incoming;
	}

	public void setIncoming(int incoming)
	{
		this.incoming = incoming;
	}

	/**
	 * Comment me. what am i doing here?
	 */
	@Override
	public int compareTo(TopTen other)
	{

		if (other.getOutgoing() != 0)
		{

			if (this == other)
				return EQUAL;

			if (this.getOutgoing() > other.getOutgoing())
			{
				return AFTER;
			} else if (this.getOutgoing() < other.getOutgoing())
			{
				return BEFORE;
			} else
			{
				return EQUAL;
			}

		}

		else
		{

			if (this == other)
				return EQUAL;

			if (this.getIncoming() > other.getIncoming())
			{
				return AFTER;
			} else if (this.getIncoming() < other.getIncoming())
			{
				return BEFORE;
			} else
			{
				return EQUAL;
			}
		}

	}
}
