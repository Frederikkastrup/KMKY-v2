package com.kmky.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.analytics.tracking.android.EasyTracker;
import com.kmky.R;
import com.kmky.data.DataModel;
import com.kmky.service.ListenerService;
import com.kmky.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The Main activity:
 *  - implements an interface (sendName) that receives an argument from the MyRelationships fragment. This way data can be transfered from the fragment to the activity.
 *  - instantiates a databasemodel, services and actionbar onCreate
 *  - declares a listener to the actionbar that replaces fragments according to the tabs that are clicked
 *  - catches results from the startActivityForResult method
 *  - declares public onClick methods for the activity layout
 */
public class Main extends Activity {

    private DataModel mDataModel;

    // Identifier for getting contacts and inflating the info into the Find Fragment
//    static final int PICK_CONTACT_REQUEST_FOR_FIND = 1;
//    static final int PICK_CONTACT_REQUEST_FOR_FAVORITESEMPTY = 2;


    @Override
    public void onStart()
    {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // get reference to data model
        mDataModel = DataModel.getInstance(this);

        // Start service
        startService(new Intent(this, ListenerService.class));

        // Initiate Actionbar
        ActionBar actionbar = getActionBar();

        // Set ActionBar to use tabs
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Initiate three tabs in the actionbar and set text to them
        ActionBar.Tab MyRelationshipTab = actionbar.newTab().setText("Relationships");
        ActionBar.Tab FavoritesTab = actionbar.newTab().setText("Favorites");
        ActionBar.Tab FindTab = actionbar.newTab().setText("Find");

        // Create the three fragments that we want to use for displaying content
        ListFragment MyRelationships = new com.kmky.activity.MyRelationships();
        ListFragment Favorites = new Favorites();
        Fragment Find = new Find();

        // Set tab listener so we are able to listen for clicks
        MyRelationshipTab.setTabListener(new MyTabsListener(MyRelationships));
        FavoritesTab.setTabListener(new MyTabsListener(Favorites));
        FindTab.setTabListener(new MyTabsListener(Find));

        // Add tabs to the actionbar
        actionbar.addTab(MyRelationshipTab);
        actionbar.addTab(FavoritesTab);
        actionbar.addTab(FindTab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class MyTabsListener implements ActionBar.TabListener {
        public Fragment fragment;



        public MyTabsListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // Toast.makeText(MyRelationships_Activity.appContext,
            // "Reselected!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            ft.replace(R.id.fragment_container, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            // If the back button is pressed, data equals null, and therefore we can't se the uri in the next scope. Therefore this if sentence checks which case is true.
            if (data != null){

                Uri contactUri = data.getData();

                // Defines the columns to return for each row
                String[] projection_number = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                String[] projection_name = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                if (resultCode == Activity.RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.
                    Cursor cursor_number = null;
                    Cursor cursor_name = null;

                    try{
                        cursor_number = getContentResolver().query(contactUri, projection_number, null, null, null);
                        cursor_number.moveToFirst();

                        cursor_name = getContentResolver().query(contactUri, projection_name, null, null, null);
                        cursor_name.moveToFirst();

                        int column_number = cursor_number.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int column_name = cursor_name.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                        String number = cursor_number.getString(column_number);
                        String name = cursor_name.getString(column_name);

                        Find fragment = new Find();

                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("number", number);
                        fragment.setArguments(bundle);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back.
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                    catch(CursorIndexOutOfBoundsException e){
                        Log.i(Constants.TAG, "Main : Get contacts", e);
                    }
                    finally {
                        cursor_name.close();
                        cursor_number.close();
                    }
                }
            }
        }

        if (requestCode == 2){

            // Make sure the request was successful
            // If the back button is pressed, data equals null, and therefore we can't se the uri in the next scope. Therefore this if sentence checks which case is true.
            if (data != null){
                Uri contactUri = data.getData();

                // Defines the columns to return for each row
                String[] projection_number = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                if (resultCode == Activity.RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.
                    Cursor cursor_number = null;

                    try{
                        cursor_number = getApplicationContext().getContentResolver().query(contactUri, projection_number, null, null, null);
                        cursor_number.moveToFirst();

                        int column_number = cursor_number.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                        String number = cursor_number.getString(column_number).replace(" ", "");

                        //Set the queried name to SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("FAVORITES", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(number, number);
                        editor.commit();

                        // Inflate Favorites again - This time, the list will populate from the new SharedPreference data and will not be empty
                        Favorites fragment = new Favorites();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                    catch(CursorIndexOutOfBoundsException e){
                        Log.i(Constants.TAG, "Main : Get contacts", e);
                    }
                    finally {
                        cursor_number.close();
                    }
                }
            }

        }

    }



    /**
     * starts the native peoples activity when the button in 'view' is clicked and broadcasts the result (the name and number of the person clicked)
     * @param view
     */
    public void findContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, 1);
    }

    /**
     * starts the native peoples activity when the button in 'view' is clicked and broadcasts the result (the name and number of the person clicked)
     * @param view
     */
    public void addFavoriteRelationship(View view){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, 2);
    }

    public long getStartDate(){
        EditText et = (EditText)this.findViewById(R.id.start_date);
        String startdate = "0000-00-00";
        long result = 0;

        if (!et.getText().toString().equals("Start Date")) {
            startdate = et.getText().toString();
        }
        else {
            Log.i(Constants.TAG, "RelationshipZoom: getStartDate: The user has not picked a date yet!");
        }

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(startdate);
            result = date.getTime();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }

    public long getEndDate(){
        EditText et = (EditText)this.findViewById(R.id.end_date);
        String startdate = "05.10.2011";
        long result = 0;

        if (!et.getText().toString().equals("Start Date")) {
            startdate = et.getText().toString();
        }
        else {
            Log.i(Constants.TAG, "RelationshipZoom: getStartDate: The user has not picked a date yet!");
        }

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
            Date date = sdf.parse(startdate);
            result = date.getTime();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }


    public String getPhoneNumber(String name, Context context) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if(ret==null)
            ret = "Unsaved";
        return ret;
    }

}