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

/**
 * Created by W520 on 05-10-13.
 */
public class RelationshipZoom extends Fragment implements View.OnClickListener {

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText dato1 = (EditText) getView().findViewById(R.id.editText);
        EditText dato2 = (EditText) getView().findViewById(R.id.editText2);

        ImageView myoutsideheart = (ImageView) getView().findViewById(R.id.myoutsideheart);
        ImageView myinsideheart = (ImageView) getView().findViewById(R.id.myinsideheart);
        ImageView youroutsideheart = (ImageView) getView().findViewById(R.id.youroutsideheart);
        ImageView yourinsideheart = (ImageView) getView().findViewById(R.id.yourinsideheart);

        myoutsideheart.setImageResource(R.drawable.outsideheart100);
        myinsideheart.setImageResource(R.drawable.insideheart50);
        youroutsideheart.setImageResource(R.drawable.outsideheart70);
        yourinsideheart.setImageResource(R.drawable.insideheart35);

        dato1.setText("Start Date");
        dato2.setText("End Date");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.relationshipzoom, container, false);

        EditText dato1 = (EditText) v.findViewById(R.id.editText);
        EditText dato2 = (EditText) v.findViewById(R.id.editText2);

        dato1.setOnClickListener(this);
        dato2.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            String name = bundle.getString("name");
            Log.i("datadata", name);
            TextView text = (TextView) v.findViewById(R.id.textviewyourheart);
            text.setText(name);

            Log.i("textdata", (String)text.getText());
        }

        Log.i("datadata", "Bundle empty");

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText:

                final EditText dato1 = (EditText) v.findViewById(R.id.editText);

                DialogFragment newFragment = new Datepicker()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        dato1.setText("" + day + "/" + month + "/" + year);
                    }
                };

                newFragment.show(getFragmentManager(), "datepicker");

                break;

            case R.id.editText2:

                final EditText dato2 = (EditText) v.findViewById(R.id.editText2);

                DialogFragment newFragment2 = new Datepicker()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        dato2.setText("" + day + "/" + month + "/" + year);
                    }
                };
                newFragment2.show(getFragmentManager(), "datepicker");
        }
    }
}