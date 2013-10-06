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

/**
 * Comment properly please
 * 
 * @author peter
 * 
 */
public class Favorites extends ListFragment
{

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*-------------------------------------- ListAdapter------------------------------------*/
        Drawable drawable1 = getResources().getDrawable(R.drawable.outsideheart90);
        Drawable drawable2 = getResources().getDrawable(R.drawable.insideheart50);
        Drawable drawable3 = getResources().getDrawable(R.drawable.outsideheart90);
        Drawable drawable4 = getResources().getDrawable(R.drawable.insideheart50);
        String name = "Frederik";

        Relations one = new Relations(drawable1, drawable2, name, drawable3, drawable4);

        List<Relations> list = new ArrayList<Relations>();
        list.add(one);

        // Tjekker om listen er tom. Hvis den er tom, så inflater den EmptyFavoritesFragment. Hvis den ikke er tom, inflater den listen i FavoritesFragment

        if (list.isEmpty())
        {
            FavoritesEmpty fragment = new FavoritesEmpty();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, fragment);

            // Commit the transaction
            transaction.commit();
        }

        else
        {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.listview_row, list);

            View header = (View)inflater.inflate(R.layout.listview_header, null);
            setListAdapter(null); // Saettes saa headeren ikke gentagende bliver tilfoejet efter setListAdapter er aktiveret - For det bliver den hver gang fragmentet bliver inflated.
            getListView().addHeaderView(header);

            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something with the data

    }
}
