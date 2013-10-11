package com.kmky.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmky.R;
import com.kmky.data.DataModel;
import com.kmky.data.LogEntry;
import com.kmky.logic.Calculate;
import com.kmky.util.Constants;

import java.util.Calendar;

/**
 * Created by W520 on 05-10-13.
 */
public class RelationshipZoom extends Fragment implements View.OnClickListener {

    private DataModel mdb = DataModel.getInstance(getActivity());

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
        ImageView smsHeartYou = (ImageView) v.findViewById(R.id.smsHeartMe);
        ImageView callHeartYou = (ImageView) v.findViewById(R.id.callHeartMe);
        ImageView smsHeartMe = (ImageView) v.findViewById(R.id.smsHeartYou);
        ImageView callHeartMe = (ImageView) v.findViewById(R.id.callHeartYou);
        TextView text = (TextView) v.findViewById(R.id.yourname_textview);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        date1.setText("" + year + "-" + month + "-" + day);
        date2.setText("" + year + "-" + month + "-" + day);

        date1.setOnClickListener(this);
        date2.setOnClickListener(this);

        // If the RelationshipZoom Fragment is inflated with a bundle, the data will be set to a textview in the Fragment Layout.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            final String name = bundle.getString("name");
            text.setText(name);
            Log.i(Constants.TAG, "RelationshipZoom: Bundle is populated by: " + name);

            Activity a = getActivity();

            if(a instanceof Main) {
                Log.d(Constants.TAG, "RelationshipZoom: onCreateView: a is instanceof main");
                String phonenumber =((Main) a).getPhoneNumber(name, getActivity());
                phonenumber = phonenumber.replace(" ","");

                String subString = phonenumber.substring(0, 3);

                if (subString.equals("+61")){
                    phonenumber = phonenumber.substring(3, phonenumber.length());
                    phonenumber = "0".concat(phonenumber);
                }

                LogEntry smsLog = mdb.getSmsLogsForPersonToDate(phonenumber,0);
                LogEntry callLog = mdb.getCallLogsForPersonToDate(phonenumber, 0);

                final int outgoingsms = smsLog.getOutgoing();
                final int outgoingcall = callLog.getOutgoing();

                final int incomingsms = smsLog.getIncoming();
                final int incomingcall = callLog.getIncoming();


                Calculate cal = new Calculate(getActivity());

                smsHeartMe.setImageDrawable(cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(), 1, getActivity()));
                callHeartMe.setImageDrawable(cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(),2, getActivity()));
                smsHeartYou.setImageDrawable(cal.calculateHeart(smsLog.getOutgoing(), smsLog.getIncoming(), 3, getActivity()));
                callHeartYou.setImageDrawable(cal.calculateHeart(callLog.getOutgoing(), callLog.getIncoming(), 4, getActivity()));

                final FrameLayout framelayoutme = (FrameLayout) v.findViewById(R.id.frameLayoutMe);
                final FrameLayout framelayoutyou = (FrameLayout) v.findViewById(R.id.frameLayoutYou);

                framelayoutme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Messages sent to " + name + ": " + outgoingsms + "\n" + "Calls made to " + name + ": " + outgoingcall, Toast.LENGTH_LONG).show();
                    }
                });

                framelayoutyou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Messages received from " + name + ": " + incomingsms + "\n" + "Calls received from " + name + ": " + incomingcall, Toast.LENGTH_LONG).show();
                    }
                });
            }
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
                        date1.setText("" + year+ "-" + month + "-" + day);
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
                        date2.setText("" + year+ "-" + month + "-" + day);
                    }
                };
                newFragment2.show(getFragmentManager(), "datepicker");
        }
    }
}
