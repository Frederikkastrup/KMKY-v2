package com.kmky.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmky.R;
import com.kmky.data.Relations;
import com.kmky.logic.AnimationHearts;
import com.kmky.logic.Heart;
import com.kmky.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by W520 on 05-10-13.
 */
public class RelationshipZoom extends Fragment implements View.OnClickListener {

    private Heart mHeart = new Heart(getActivity());


    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.relationshipzoom, container, false);

        EditText date1 = (EditText) v.findViewById(R.id.start_date);
        EditText date2 = (EditText) v.findViewById(R.id.end_date);


        // ImageViews are instantiated so the appropriate mHeart sizes can be assigned to them.
        final ImageView smsHeartYou = (ImageView) v.findViewById(R.id.smsHeartMe);
        final ImageView callHeartYou = (ImageView) v.findViewById(R.id.callHeartMe);
        final ImageView smsHeartMe = (ImageView) v.findViewById(R.id.smsHeartYou);
        final ImageView callHeartMe = (ImageView) v.findViewById(R.id.callHeartYou);
        final TextView text = (TextView) v.findViewById(R.id.yourname_textview);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        date1.setText("" + year + "-" + month + "-" + day);
        date2.setText("" + year + "-" + month + "-" + day);

        date1.setOnClickListener(this);
        date2.setOnClickListener(this);

        Button button = (Button)v.findViewById(R.id.showchange_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                List<AnimationHearts> animationsovertime = getAnimationsOverTime(v);
                AnimationDrawable smsHeartMeAnimation  = new AnimationDrawable();
                AnimationDrawable callHeartmeAnimation =  new AnimationDrawable();
                AnimationDrawable smsHeartYouAnimation =  new AnimationDrawable();
                AnimationDrawable callHeartYouAnimation  =  new AnimationDrawable();



//                for (AnimationHearts animation : animationsovertime)
//                {
//                    smsHeartMeAnimation.addFrame(animation.getmSmsHeartMe(), 10);
//                    callHeartmeAnimation.addFrame(animation.getmCallHeartMe(), 10);
//                    smsHeartYouAnimation.addFrame(animation.getmSmsHeartYou(), 10);
//                    callHeartYouAnimation.addFrame(animation.getmCallHeartYou(), 10);
//                    Toast.makeText(getActivity().getApplicationContext(), "Time " + animation.getmTimestamp(), Toast.LENGTH_SHORT).show();
//                }

                smsHeartMeAnimation.addFrame(getResources().getDrawable(R.drawable.green), 1000);
                smsHeartMeAnimation.addFrame(getResources().getDrawable(R.drawable.red), 1000);
                smsHeartMeAnimation.addFrame(getResources().getDrawable(R.drawable.green), 1000);
                smsHeartMeAnimation.addFrame(getResources().getDrawable(R.drawable.red), 1000);

                smsHeartMe.setImageDrawable(getResources().getDrawable(R.drawable.blank));
//                callHeartMe.setImageDrawable(getResources().getDrawable(R.drawable.blank));
//                smsHeartYou.setImageDrawable(getResources().getDrawable(R.drawable.blank));
//                callHeartYou.setImageDrawable(getResources().getDrawable(R.drawable.blank));
                smsHeartMe.setBackground(smsHeartMeAnimation);
                smsHeartMeAnimation.setOneShot(true);
//                callHeartmeAnimation.setOneShot(true);
//                smsHeartYouAnimation.setOneShot(true);
//                callHeartYouAnimation.setOneShot(true);

//                callHeartMe.setBackground(callHeartmeAnimation);
//                smsHeartYou.setBackground(smsHeartYouAnimation);
//                callHeartYou.setBackground(callHeartYouAnimation);

                smsHeartMeAnimation.start();
//                callHeartmeAnimation.start();
//                smsHeartYouAnimation.start();
//                callHeartYouAnimation.start();



            }
        });

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

                Relations relation = mHeart.HeartSizeToDateSinglePerson(phonenumber, getActivity());

                final int outgoingsms = mHeart.SmsAndCallCount(phonenumber, 1).getOutgoing();
                final int outgoingcall = mHeart.SmsAndCallCount(phonenumber, 2).getOutgoing();

                final int incomingsms = mHeart.SmsAndCallCount(phonenumber, 1).getIncoming();
                final int incomingcall = mHeart.SmsAndCallCount(phonenumber, 2).getIncoming();

                smsHeartMe.setImageDrawable(relation.getSmsHeartMe());
                callHeartMe.setImageDrawable(relation.getCallHeartMe());
                smsHeartYou.setImageDrawable(relation.getSmsHeartYou());
                callHeartYou.setImageDrawable(relation.getCallHeartYou());

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

    /**
     * Method that takes startdate and enddate strings as arguments, passes them to floats. Passes these floats to calendar objects, and uses a method in these objects to pass date values to a list object.
     * @return
     */
    public List<Date> getTimestamps(){

        EditText sd = (EditText)getView().findViewById(R.id.start_date);
        EditText ed = (EditText)getView().findViewById(R.id.end_date);
        String startdate = sd.getText().toString();
        String enddate = ed.getText().toString();

        // Create List of dates
        ArrayList<Date> dates = new ArrayList<Date>();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        // Passes the date Strings into SimpleDateFormats
        try {
            date1 = df1.parse(startdate);
            date2 = df1.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create date objects, pass them the SimpleDateFormats..
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }

        return dates; //Returns ArrayList with the inbetween dates.
    }

    public List<AnimationHearts> getAnimationsOverTime(View view){

        List<AnimationHearts> animationhearts = new ArrayList<AnimationHearts>();
        List<Date> timestamps  = getTimestamps();
        TextView tw = (TextView)getView().findViewById(R.id.yourname_textview);
        String number = mHeart.getPhoneNumberFromName(tw.getText().toString(), getActivity());
        number = number.replace(" ", "");
        Drawable blank = getActivity().getResources().getDrawable(R.drawable.blank);

        for (Date timestamp : timestamps ){

            long milliseconds = timestamp.getTime();
            Relations newRelation = mHeart.HeartSizeSingleDay(number, milliseconds, getActivity());

            if (newRelation.getSmsHeartMe().getConstantState().equals(blank.getConstantState()) && newRelation.getCallHeartMe().getConstantState().equals(blank.getConstantState()) && newRelation.getSmsHeartYou().getConstantState().equals(blank.getConstantState()) && newRelation.getCallHeartYou().getConstantState().equals(blank.getConstantState()))
            {
                Log.d(Constants.TAG, "RelationshipZoom: getAnimationsOverTime: all blanks");
            }
            else {

                animationhearts.add(new AnimationHearts(newRelation.getSmsHeartMe(), newRelation.getCallHeartMe(), newRelation.getSmsHeartYou(), newRelation.getCallHeartYou(), timestamp));
                Log.d(Constants.TAG, "RelationshipZoom: getAnimationsOverTime: Data!");
            }
        }
        return animationhearts;
    }
}
