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
import android.widget.ListView;
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

public class Favorites extends ListFragment
{
    private OnRowSelectedListener mCallback;
    private DataModel mDM = DataModel.getInstance(getActivity());

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


        List<Relations> list = getRelations();

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

        View v = inflater.inflate(R.layout.favorites, container, false);

        return v;
    }

    private List<Relations> getRelations()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("FAVORITES", Activity.MODE_PRIVATE);
        Map<String,?> keys = preferences.getAll();
        List<String> numberslist = new ArrayList<String>();
        List<Relations> relations = new ArrayList<Relations>();
        Calculate cal = new Calculate(getActivity());
        Drawable smsHeartMe, callHeartMe, smsHeartYou, callHeartYou;
        LogEntry smsLog, callLog;
        Heart heart = new Heart(getActivity());

        for(Map.Entry<String,?> entry : keys.entrySet()){

            Log.i(Constants.TAG, "Favorites: getRelations: This is in SharedPreferences when the list for the list adapter is created: " + entry.getValue().toString().replace(" ", ""));

            numberslist.add(entry.getValue().toString().replace(" ", ""));
        }

        // Goes through the list with numbers to perform operations on them
        for (String number : numberslist){


            smsLog = mDM.getSmsLogsForPersonToDate(number, 0);
            callLog = mDM.getCallLogsForPersonToDate(number, 0);


            smsHeartMe = cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(),1, getActivity());
            callHeartMe = cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, getActivity());
            smsHeartYou = cal.calculateHeart(smsLog.getOutgoing(),smsLog.getIncoming(),3, getActivity());
            callHeartYou = cal.calculateHeart(callLog.getOutgoing(),callLog.getIncoming(),4,getActivity());
            relations.add(new Relations(smsHeartMe, callHeartMe , heart.getContactNameFromNumber(number), smsHeartYou, callHeartYou));
        }

        return relations;
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
