package com.kmky.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kmky.R;
import com.kmky.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by W520 on 05-10-13.
 */
public class RelationshipZoom extends Fragment implements View.OnClickListener {

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.relationshipzoom, container, false);

        EditText date1 = (EditText) v.findViewById(R.id.start_date);
        EditText date2 = (EditText) v.findViewById(R.id.end_date);

        // ImageViews are instantiated so the appropriate heart sizes can be assigned to them.
        ImageView mysmsheart = (ImageView) v.findViewById(R.id.mysmsheart);
        ImageView mycallheart = (ImageView) v.findViewById(R.id.mycallheart);
        ImageView yoursmsheart = (ImageView) v.findViewById(R.id.yoursmsheart);
        ImageView yourcallheart = (ImageView) v.findViewById(R.id.yourcallheart);

        date1.setText("Start Date");
        date2.setText("End Date");

        date1.setOnClickListener(this);
        date2.setOnClickListener(this);

        // If the RelationshipZoom Fragment is inflated with a bundle, the data will be set to a textview in the Fragment Layout.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            Log.i(Constants.TAG, "RelationshipZoom: onCreateView: " + name);
            TextView text = (TextView) v.findViewById(R.id.yourname_textview);
            text.setText(name);
        }
        Log.i(Constants.TAG, "RelationshipZoom: Bundle empty");

        return v;
    }

    /**
     * onClickListener for the datepicker.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:

                final EditText date1 = (EditText) v.findViewById(R.id.start_date);

                DialogFragment newFragment = new Datepicker()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date1.setText("" + month + "." + day + "." + year);
                    }
                };

                newFragment.show(getFragmentManager(), "datepicker");

                break;

            case R.id.end_date:

                final EditText date2 = (EditText) v.findViewById(R.id.end_date);

                DialogFragment newFragment2 = new Datepicker()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date2.setText("" + month+ "." + day + "." + year);
                    }
                };
                newFragment2.show(getFragmentManager(), "datepicker");
        }
    }
}
