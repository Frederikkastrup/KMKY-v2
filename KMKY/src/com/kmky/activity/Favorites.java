package com.kmky.activity;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kmky.R;
import com.kmky.data.Relations;

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

        Relations relations_data[] = new Relations[]
                {

                };

        // Tjekker om listen er tom. Hvis den er tom, så inflater den EmptyFavoritesFragment. Hvis den ikke er tom, inflater den listen i FavoritesFragment

        if (relations_data.length == 0)
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

            CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), R.layout.listview_row, relations_data);

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
