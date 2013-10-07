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

import java.util.List;

/**
 * Created by W520 on 21-09-13.
 */
public class CustomListAdapter extends ArrayAdapter<Relations>
{
	private Context mContext;
	private int mlayoutresourceid;
	private List<Relations> mdata = null;

	/**
	 * Instantiates the adapter object when created.
	 * 
	 * @param mContext
	 * @param mlayoutresourceid
	 * @param mdata
	 */
	public CustomListAdapter(Context mContext, int mlayoutresourceid, List<Relations> mdata) {
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

            holder.mysmsheart = (ImageView)row.findViewById(R.id.mysmsheart);
            holder.mycallheart = (ImageView)row.findViewById(R.id.mycallheart);
            holder.yoursmsheart = (ImageView)row.findViewById(R.id.yoursmsheart);
            holder.yourcallheart = (ImageView)row.findViewById(R.id.yourcallheart);
            holder.name = (TextView)row.findViewById(R.id.rowname);

			row.setTag(holder);
		}

        else {
			holder = (RelationsHolder) row.getTag();
		}

		Relations relation = mdata.get(position);

        holder.mysmsheart.setImageDrawable(relation.mysmsheart);
        holder.mycallheart.setImageDrawable(relation.mycallheart);
        holder.yoursmsheart.setImageDrawable(relation.yoursmsheart);
        holder.yourcallheart.setImageDrawable(relation.yourcallheart);
        holder.name.setText(relation.name);

		return row;
	}

    // The holder is used to increase performance when scrolling through the listview. Instead of finding view by id in repeated use, the adapter can retrieve the views from a holder.
	static class RelationsHolder {
        ImageView mysmsheart;
        ImageView mycallheart;
        ImageView yoursmsheart;
        ImageView yourcallheart;
        TextView name;
	}

}
