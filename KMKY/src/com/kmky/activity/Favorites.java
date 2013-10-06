package com.kmky.activity;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kmky.R;
import com.kmky.data.Relations;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends ListFragment
{
    /**
     * When the activity is created, an if sentence checks if the List in ListFragment is empty or not. Depending on this condition another fragment is inflated.
     * @param savedInstanceState
     */

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Drawable drawable1 = getResources().getDrawable(R.drawable.outsideheart90);
        Drawable drawable2 = getResources().getDrawable(R.drawable.insideheart50);
        Drawable drawable3 = getResources().getDrawable(R.drawable.outsideheart90);
        Drawable drawable4 = getResources().getDrawable(R.drawable.insideheart50);
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something with the data
    }
}
