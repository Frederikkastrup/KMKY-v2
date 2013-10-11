package com.kmky.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.kmky.R;
import com.kmky.data.DataModel;
import com.kmky.data.Relations;
import com.kmky.logic.Heart;
import com.kmky.service.ListenerService;
import com.kmky.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.google.analytics.tracking.android.EasyTracker;


/**
 * The Main activity:
 *  - implements an interface (sendName) that receives an argument from the MyRelationships fragment. This way data can be transfered from the fragment to the activity.
 *  - instantiates a databasemodel, services and actionbar onCreate
 *  - declares a listener to the actionbar that replaces fragments according to the tabs that are clicked
 *  - catches results from the startActivityForResult method
 *  - declares public onClick methods for the activity layout
 */
public class Main extends Activity implements MyRelationships.OnRowSelectedListener, Favorites.OnRowSelectedListener {

	private DataModel mDataModel;

    static final int PICK_CONTACT_REQUEST = 1;

    /**
     * The implemented sendNameFromMyRelations interface receives a name variable from the MyRelationships fragment, sets it to a bundle and creates and inflates a new fragment containing this variable.
     * @param name
     */
    public void sendNameFromMyRelations(String name)
    {
        //set Fragmentclass Arguments
        RelationshipZoom fragment = new RelationshipZoom();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

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

    /**
     * The implemented sendNameFromFavorites interface receives a name variable from the Favorites fragment, sets it to a bundle and creates and inflates a new fragment containing this variable.
     * @param name
     */
    public void sendNameFromFavorites(String name)
    {
        //set Fragmentclass Arguments
        RelationshipZoom fragment = new RelationshipZoom();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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
        if (requestCode == PICK_CONTACT_REQUEST) {
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
}

    /**
     * starts the native peoples activity when the button in 'view' is clicked and broadcasts the result (the name and number of the person clicked)
     * @param view
     */
    public void findContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    public void addToFavorites(View view){

        EditText et = (EditText) this.findViewById(R.id.find_edittext);

        // Gets name and number from EditText
        String nameandnumber = et.getText().toString();

        // Splits name and number up into two strings
        StringTokenizer tokens = new StringTokenizer(nameandnumber, "-");
        String name = tokens.nextToken();
        String number = tokens.nextToken();

            Bundle bundle = new Bundle();
            bundle.putString("number", number);

            Favorites fragment = new Favorites();
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.add(R.id.fragment_container, fragment);
            transaction.hide(fragment);
            transaction.commit();

        Toast.makeText(getApplicationContext(), nameandnumber + " added to favorites!", Toast.LENGTH_SHORT).show();
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

    /**
     * Method that takes startdate and enddate strings as arguments, passes them to floats. Passes these floats to calendar objects, and uses a method in these objects to pass date values to a list object.
     * @return
     */
    public List<Date> getTimestamps(){

        EditText sd = (EditText)this.findViewById(R.id.start_date);
        EditText ed = (EditText)this.findViewById(R.id.end_date);
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

    public List<Relations> showTimestamps(View view){

        List<Relations> relations = new ArrayList<Relations>();
        List<Date> timestamps  = getTimestamps();
        TextView tw = (TextView)this.findViewById(R.id.yourname_textview);
        String number = getPhoneNumber(tw.toString(), this);
        Heart heart = new Heart(this);

        for (Date timestamp : timestamps ){
            
            long milliseconds = timestamp.getTime();
            relations.add(heart.HeartSizeSingleDay(number, milliseconds));
        }
        return relations;
    }

    private String getPhoneNumber(String name, Context context) {
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