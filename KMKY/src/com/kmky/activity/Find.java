package com.kmky.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kmky.R;
import com.kmky.util.Constants;

/**
 * Created by W520 on 18-09-13.
 */
public class Find extends Fragment
{

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Whenever the container for the Find Fragment is created, the Find Fragment inflates together with any bundled data.
     * If the bundle contains data (a name), buttons in the Find Fragment Layout will become visible to perform actions on this name.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.find, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            String name = bundle.getString("name");
            String number = bundle.getString("number");
            EditText edittext = (EditText) v.findViewById(R.id.find_edittext);
            edittext.setText(name + " - " + number);

            Button addtofavorites = (Button)v.findViewById(R.id.addtofavorites_button);
            Button seerelationship = (Button)v.findViewById(R.id.seerelationship_button);

            addtofavorites.setVisibility(View.VISIBLE);
            seerelationship.setVisibility(View.VISIBLE);
        }
        else {Log.i(Constants.TAG, "Find: onCreateView: Bundle empty");
        }

        return v;
    }

}
