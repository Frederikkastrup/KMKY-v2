package com.kmky.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
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
import com.kmky.data.Relations;
import com.kmky.logic.Heart;
import com.kmky.util.Constants;

import java.util.List;

/**
 * Created by W520 on 18-09-13.
 */
public class MyRelationships extends ListFragment implements  AdapterView.OnItemSelectedListener
{
    private int m_intSpinnerInitiCount = 0;
    private static final int NO_OF_EVENTS = 1;
    private int mstate;

    private OnRowSelectedListener mCallback;

    /**
     * Implements the interface from AdapterView.OnItemSelectedListener to get access to methods that listen for onClicks in the spinner adapterview. In onItemSelected we check the position of the spinner and assigns this to a class variable.
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (m_intSpinnerInitiCount < NO_OF_EVENTS) {
            m_intSpinnerInitiCount++;
        }
        else {
            mstate = pos;
            Bundle bundle = new Bundle();
            bundle.putInt("mstate", mstate);

            MyRelationships fragment = new MyRelationships();
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     *  Interface that can be implemented by the container activity and hereby allow the transfer of data from this fragment to the parent activity.
      */
    public interface OnRowSelectedListener
    {
        public void sendFromMyRelations(String name);
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
     * When the Activity is created, a listadapter is set to this ListFragment (MyRelationships).
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);

        // Set listener to spinner
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> spinneradapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_array, android.R.layout.simple_spinner_item);

        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
        spinner.setSelection(mstate);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mstate = bundle.getInt("mstate");
        }

        Heart heart = new Heart(getActivity());
        List<Relations> list = heart.HeartSizesMyRelationships(mstate);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.listview_row, list);

        View header = (View)inflater.inflate(R.layout.listview_header, null);

        setListAdapter(null); // We assign null to the listadapter because, the header is assigned every time the fragment is inflated. The header can't be assigned twice, so we reset the adapter.
        getListView().addHeaderView(header);
        setListAdapter(adapter);
    }

    /**
     * Inflates the MyRelationships fragment when the container view (main.xml) is created and sets a listener to the spinner inside it.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.myrelationships, container, false);

        return v;
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

            // Gets name from row
            TextView tv = (TextView) v.findViewById(R.id.rowname);
            String name = tv.getText().toString();

            mCallback.sendFromMyRelations(name);
        }
        catch (NullPointerException e){
            Log.i(Constants.TAG, "Favorites: HeaderOnClick", e);
        }
    }
}
