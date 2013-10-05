package com.kmky.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.kmky.R;
import com.kmky.data.DataModel;
import com.kmky.service.ListenerService;

/**
 * Comment me
 * 
 * @author peter
 * 
 */
public class Main extends Activity
{

	private DataModel mDataModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myrelationships_layout);

		// get reference to data model
		mDataModel = DataModel.getInstance(this);
	
		// Start service
		startService(new Intent(this, ListenerService.class));

		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Initiate three tabs and set text to them
		ActionBar.Tab MyRelationshipTab = actionbar.newTab().setText("Relationships");
		ActionBar.Tab FavoritesTab = actionbar.newTab().setText("Favorites");
		ActionBar.Tab FindTab = actionbar.newTab().setText("Find");

		// Create the three fragments that we want to use for displaying content
		Fragment MyRelationships = new MyRelationshipsFragment();
		Fragment Favorites = new Favorites();
		Fragment Find = new FindContacts();

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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	public class MyTabsListener implements ActionBar.TabListener
	{
		public Fragment fragment;

		public MyTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
		{

			// Toast.makeText(MyRelationships_Activity.appContext,
			// "Reselected!", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
		{
			ft.replace(R.id.fragment_container, fragment);
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
		{
			ft.remove(fragment);
		}

	}
}