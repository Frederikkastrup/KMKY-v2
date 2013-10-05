package com.kmky.activity;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kmky.R;
import com.kmky.data.Relations;

/**
 * Created by W520 on 18-09-13.
 */
public class MyRelationships extends ListFragment
{

    OnRowSelectedListener mCallback;

    //Interface that can be implemented by the container activity
    public interface OnRowSelectedListener
    {
        public void sendName(String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRowSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRowSelectedListener");
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


/*-------------------------------------- Arrayadapter------------------------------------*/
        Relations relations_data[] = new Relations[]
                {
                        new Relations(R.drawable.outsideheart100, R.drawable.insideheart50, "Frederik", R.drawable.outsideheart70, R.drawable.insideheart30),
                        new Relations(R.drawable.outsideheart90, R.drawable.insideheart40, "Frederik", R.drawable.outsideheart100, R.drawable.insideheart40),
                        new Relations(R.drawable.outsideheart80, R.drawable.insideheart40, "Frederik", R.drawable.outsideheart60, R.drawable.insideheart50),
                        new Relations(R.drawable.outsideheart100, R.drawable.insideheart50, "Frederik", R.drawable.outsideheart60, R.drawable.insideheart30),
                        new Relations(R.drawable.outsideheart70, R.drawable.insideheart50, "Frederik", R.drawable.outsideheart80, R.drawable.insideheart50),
                        new Relations(R.drawable.outsideheart60, R.drawable.insideheart35, "Frederik", R.drawable.outsideheart100, R.drawable.insideheart35),
                        new Relations(R.drawable.outsideheart100, R.drawable.insideheart45, "Frederik", R.drawable.outsideheart70, R.drawable.insideheart35)
                };


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), R.layout.listview_row, relations_data);

        View header = (View)inflater.inflate(R.layout.listview_header, null);
        setListAdapter(null); // Saettes saa headeren ikke gentagende bliver tilfoejet efter setListAdapter er aktiveret - For det bliver den hver gang fragmentet bliver inflated.
        getListView().addHeaderView(header);

        setListAdapter(adapter);

    }
/*---------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.myrelationships, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Finder textview i row der blev clicket
        TextView tv = (TextView) v.findViewById(R.id.textview2);
        String name = tv.getText().toString();

        // Sender navnet til interfacet - Det ryger derefter videre til vores main activity der implementerer dette interface og inflater det relationshipzoomfragment
        mCallback.sendName(name);
    }
}
