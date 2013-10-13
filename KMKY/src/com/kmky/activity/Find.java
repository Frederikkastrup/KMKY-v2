package com.kmky.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.kmky.R;
import com.kmky.util.Constants;

import java.util.StringTokenizer;


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

            final String name = bundle.getString("name");
            final String number = bundle.getString("number").replace(" ","");
            final EditText edittext = (EditText) v.findViewById(R.id.find_edittext);
            edittext.setText(name + " - " + number);


            Button addtofavorites = (Button)v.findViewById(R.id.addtofavorites_button);
            Button removefromfravorites = (Button)v.findViewById(R.id.removefromfavorites_button);
            Button seerelationship = (Button)v.findViewById(R.id.seerelationship_button);

            // Sets listener for the "See Relationships" button. If clicked, inflates RelationshipZoom bundled with the name searched for.
            seerelationship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EasyTracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());
                    easyTracker.send(MapBuilder.createEvent("ui_action", "button_press", "See relationship with a contact without adding to Favorites", null).build());

                    RelationshipZoom fragment = new RelationshipZoom();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });

            // Checks if the name searched is already added to favorites. e.g in SharedPreferences. Shows appropriate
            final SharedPreferences preferences = getActivity().getSharedPreferences("FAVORITES", Activity.MODE_PRIVATE);
            String value = preferences.getString(number, "");

            Log.i(Constants.TAG, "Find: onCreateView: Person checked for in SharedPreferences:" + value); // To know which buttons to show

            if (value.equalsIgnoreCase("")) {
                addtofavorites.setVisibility(View.VISIBLE);
                addtofavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EasyTracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());
                        easyTracker.send(MapBuilder.createEvent("ui_action", "button_press", "Added contact to Favorites", null).build());

                        // Gets name and number from EditText
                       String nameandnumber = edittext.getText().toString();

                        // Splits name and number up into two strings
                        StringTokenizer tokens = new StringTokenizer(nameandnumber, "-");
                        String name = tokens.nextToken();
                        String number = tokens.nextToken();
                        number = number.replace(" ", "");

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(number, number);
                        editor.commit();

                        Toast.makeText(getActivity().getApplicationContext(), nameandnumber + " added to favorites!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                removefromfravorites.setText("Remove from Favorites");
                removefromfravorites.setVisibility(View.VISIBLE);
                removefromfravorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EasyTracker easyTracker = EasyTracker.getInstance(getActivity().getApplicationContext());
                        easyTracker.send(MapBuilder.createEvent("ui_action", "button_press", "Remove contact from Favorites", null).build());

                        String nameandnumber = edittext.getText().toString();
                        preferences.edit().remove(number).commit();
                        Toast.makeText(getActivity().getApplicationContext(), nameandnumber + " removed from favorites!", Toast.LENGTH_SHORT).show();
                        Log.i(Constants.TAG, "Find: removeFromFavorites: Number that we want to remove from sharedPreferences: " + number);
                    }
                });
            }
            seerelationship.setVisibility(View.VISIBLE);
        }
        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        EasyTracker tracker = EasyTracker.getInstance(getActivity());
        tracker.set(Fields.SCREEN_NAME, "Find");
        tracker.send(MapBuilder.createAppView().build());
    }

}
