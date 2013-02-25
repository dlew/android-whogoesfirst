package com.idunnolol.whogoesfirst;

import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.idunnolol.whogoesfirst.PlayerCountFragment.PlayerCountFragmentListener;

public class MainActivity extends Activity implements PlayerCountFragmentListener {

	private PlayerCountFragment mPlayerCountFragment;
	private WhoGoesFragment mWhoGoesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				invalidateOptionsMenu();
			}
		});

		if (savedInstanceState == null) {
			mPlayerCountFragment = new PlayerCountFragment();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(android.R.id.content, mPlayerCountFragment, PlayerCountFragment.TAG);
			ft.commit();
		}
		else {
			mPlayerCountFragment = Ui.findFragment(this, PlayerCountFragment.TAG);
			mWhoGoesFragment = Ui.findFragment(this, WhoGoesFragment.TAG);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// ActionBar

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean canGoBack = getFragmentManager().getBackStackEntryCount() > 0;
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(canGoBack);
		actionBar.setHomeButtonEnabled(canGoBack);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//////////////////////////////////////////////////////////////////////////
	// PlayerCountFragmentListener

	@Override
	public void onPlayerCountSelected(int count) {
		// Pick the player (0 == current player, 1 == 1 clockwise, 2 == 2 clockwise, etc.)
		Random rand = new Random();
		int player = rand.nextInt(count);

		mWhoGoesFragment = WhoGoesFragment.newInstance(count, player);

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		ft.remove(mPlayerCountFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.add(android.R.id.content, mWhoGoesFragment, WhoGoesFragment.TAG);
		ft.addToBackStack("whoGoes");
		ft.commit();
	}

}
