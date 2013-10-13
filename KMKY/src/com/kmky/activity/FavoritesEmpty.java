package com.kmky.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmky.R;

/**
 * Created by W520 on 05-10-13.
 */
public class FavoritesEmpty extends Fragment {

    // Identifier for getting contacts and inflating the info into the FavoritesEmpty
    static final int PICK_CONTACT_REQUEST = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.favoritesempty, container, false);
        return v;
    }
}