package com.kmky.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kmky.R;

/**
 * Created by W520 on 21-09-13.
 */
public class CustomArrayAdapter extends ArrayAdapter<Relations>
{
	private Context mContext;
	private int layoutResourceID;
	private Relations data[] = null;

	/**
	 * fgdfghdggdf
	 * 
	 * @param context
	 * @param layoutResourceID
	 * @param data
	 */
	public CustomArrayAdapter(Context context, int layoutResourceID, Relations[] data) {
		super(context, layoutResourceID, data);
		this.layoutResourceID = layoutResourceID;
		this.mContext = context;
		this.data = data;
	}

	/**
	 * English please :-)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		View row = convertView;
		RelationsHolder holder = null;

		if (row == null)
		{
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(layoutResourceID, parent, false);

			holder = new RelationsHolder();
			holder.pic1 = (ImageView) row.findViewById(R.id.pic1);
			holder.txt = (TextView) row.findViewById(R.id.textview2);
			holder.pic2 = (ImageView) row.findViewById(R.id.pic2);

			row.setTag(holder);
		} else
		{
			holder = (RelationsHolder) row.getTag();
		}

		Relations relation = data[position];

		holder.txt.setText(relation.name);
		holder.pic1.setImageResource(relation.icon1);
		holder.pic2.setImageResource(relation.icon2);

		return row;
	}

	static class RelationsHolder
	{
		ImageView pic1;
		ImageView pic2;
		TextView txt;
	}

}
