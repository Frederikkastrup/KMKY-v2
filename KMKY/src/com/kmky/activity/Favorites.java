package com.kmky.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kmky.R;
import com.kmky.data.Relations;
import com.kmky.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends ListFragment
{
    private OnRowSelectedListener mCallback;

    /**
     *  Interface that can be implemented by the container activity and hereby allow the transfer of data from this fragment to the parent activity.
     */
    public interface OnRowSelectedListener {
        public void sendNameFromFavorites(String name);
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

    /**
     * When the activity is created, an if sentence checks if the List in ListFragment is empty or not. Depending on this condition another fragment is inflated.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Drawable drawable1 = getResources().getDrawable(R.drawable.smsheart80);
        Drawable drawable2 = getResources().getDrawable(R.drawable.callheart100);
        Drawable drawable3 = getResources().getDrawable(R.drawable.smsheart80);
        Drawable drawable4 = getResources().getDrawable(R.drawable.callheart100);
        String name = "Frederik";

        Relations one = new Relations(drawable1, drawable2, name, drawable3, drawable4);

        List<Relations> list = new ArrayList<Relations>();
        list.add(one);

        if (list.isEmpty()) {
            FavoritesEmpty fragment = new FavoritesEmpty();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

        else {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.listview_row, list);

            View header = (View)inflater.inflate(R.layout.listview_header, null);
            setListAdapter(null);
            getListView().addHeaderView(header);

            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorites, container, false);
    }

    /**
     * onListItemClick finds the TextView in the ListView row that gets clicked. Then sends the name  from this row to the fragment interface.
     * When this interface is implemented by the main activity, the name can be bundled and inflated into the RelationshipZoom fragment.
     * Since this listener is shared between the headerview and the rowview - if the ListView header is clicked, the listener will also react.
     * But View v won't match up with the view that we try to assign and this will cause and exception.
     * Therefore, if this happens, a try catch handles the exception.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try{

            TextView tv = (TextView) v.findViewById(R.id.rowname);
            String name = tv.getText().toString();

            mCallback.sendNameFromFavorites(name);
        }
        catch (NullPointerException e){
            Log.i(Constants.TAG, "MyRelationship: HeaderOnClick", e);
        }
    }
}
