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
import com.kmky.data.Relations;

/**
 * Created by W520 on 21-09-13.
 */
public class CustomArrayAdapter extends ArrayAdapter<Relations>
{
	private Context mContext;
	private int mlayoutresourceid;
	private Relations mdata[] = null;

	/**
	 * Instantiates the adapter object when created.
	 * 
	 * @param mContext
	 * @param mlayoutresourceid
	 * @param mdata
	 */
	public CustomArrayAdapter(Context mContext, int mlayoutresourceid, Relations[] mdata) {
		super(mContext, mlayoutresourceid, mdata);
		this.mlayoutresourceid = mlayoutresourceid;
		this.mContext = mContext;
		this.mdata = mdata;
	}

	/**
	 * Overrides the adapter view and defines the new views to be used in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		RelationsHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mlayoutresourceid, parent, false);

            holder = new RelationsHolder();

            holder.outsideheart1 = (ImageView)row.findViewById(R.id.outsideheart1);
            holder.insideheart1 = (ImageView)row.findViewById(R.id.insideheart1);
            holder.outsideheart2 = (ImageView)row.findViewById(R.id.outsideheart2);
            holder.insideheart2 = (ImageView)row.findViewById(R.id.insideheart2);
            holder.name = (TextView)row.findViewById(R.id.textview2);

			row.setTag(holder);
		}

        else {
			holder = (RelationsHolder) row.getTag();
		}

		Relations relation = mdata[position];

        holder.outsideheart1.setImageResource(relation.outsideheart1);
        holder.insideheart1.setImageResource(relation.insideheart1);
        holder.outsideheart2.setImageResource(relation.outsideheart2);
        holder.insideheart2.setImageResource(relation.insideheart2);
        holder.name.setText(relation.name);

		return row;
	}

    // The holder is used to increase performance when scrolling through the listview. Instead of finding view by id in repeated use, the adapter can retrieve the views from a holder.
	static class RelationsHolder {
        ImageView outsideheart1;
        ImageView insideheart1;
        ImageView outsideheart2;
        ImageView insideheart2;
        TextView name;
	}

}
