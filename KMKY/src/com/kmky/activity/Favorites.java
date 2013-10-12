package com.kmky.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kmky.R;
import com.kmky.data.DataModel;
import com.kmky.data.LogEntry;
import com.kmky.data.Relations;
import com.kmky.logic.Calculate;
import com.kmky.logic.Heart;
import com.kmky.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Favorites extends ListFragment implements  AdapterView.OnItemSelectedListener
{
    private int m_intSpinnerInitiCount = 0;
    private static final int NO_OF_EVENTS = 1;
    private int mstate;
    private Heart heart = new Heart(getActivity());


    // Spinner listener
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (m_intSpinnerInitiCount < NO_OF_EVENTS) {
            m_intSpinnerInitiCount++;
        }
        else {
            mstate = pos;
            Bundle bundle = new Bundle();
            bundle.putInt("mstate", mstate);

            Favorites fragment = new Favorites();
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * When the View is created, an if sentence checks if the List in ListFragment is empty or not. Depending on this condition another fragment is inflated.
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mstate = bundle.getInt("mstate");
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("FAVORITES", Activity.MODE_PRIVATE);
        Map<String,?> keys = preferences.getAll();
        List<String> numberslist = new ArrayList<String>();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            numberslist.add(entry.getValue().toString().replace(" ", ""));
            Log.d(Constants.TAG, "Favorites: onViewCreated: SharedPreferences indeholder: " + entry.getValue().toString().replace(" ", ""));
        }

        Log.d(Constants.TAG, "Favorites: onViewCreated: State: " + mstate);

        List<Relations> list = heart.HeartSizeFavorites(mstate, numberslist, getActivity());

        if (list.isEmpty()) {
            FavoritesEmpty fragment = new FavoritesEmpty();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

        else {
            // Create and set spinner
            Spinner spinner = (Spinner) getActivity().findViewById(R.id.favorites_spinner);

            // Set listener to spinner
            spinner.setOnItemSelectedListener(this);

            ArrayAdapter<CharSequence> spinneradapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.sort_array, android.R.layout.simple_spinner_item);

            spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinneradapter);
            spinner.setSelection(mstate);

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

        View v = inflater.inflate(R.layout.favorites, container, false);
        return v;
    }


        /**
         * Since this listener is shared between the headerview and the rowview - if the ListView header is clicked, the listener will also react.
         * But View v won't match up with the view that we try to assign and this will cause and exception.
         * Therefore, if this happens, a try catch handles the exception.
         */
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            try{
                TextView tv = (TextView) v.findViewById(R.id.rowname);
                String name = tv.getText().toString();

                RelationshipZoom fragment = new RelationshipZoom();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
            catch (NullPointerException e){
                Log.i(Constants.TAG, "MyRelationship: HeaderOnClick", e);
            }
        }
    }
