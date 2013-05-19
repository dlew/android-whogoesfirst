package com.idunnolol.whogoesfirst;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements PlayerCountListener {

	private PlayerCountFragment mPlayerCountFragment;
	private WhoGoesFragment mWhoGoesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				supportInvalidateOptionsMenu();
			}
		});

		if (savedInstanceState == null) {
			mPlayerCountFragment = new PlayerCountFragment();

			FragmentManager fm = getSupportFragmentManager();
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

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(canGoBack);

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				actionBar.setHomeButtonEnabled(canGoBack);
			}
		}

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
	// PlayerCountListener

	@Override
	public void onPlayerCountSelected(int count) {
		// Catch if someone entered an invalid number somehow
		if (count < 1) {
			Toast.makeText(this, getString(R.string.template_invalid_number_players, count), Toast.LENGTH_SHORT).show();
			return;
		}

		// Pick the player (0 == current player, 1 == 1 clockwise, 2 == 2 clockwise, etc.)
		Random rand = new Random();
		int player = rand.nextInt(count);

		mWhoGoesFragment = WhoGoesFragment.newInstance(count, player);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		ft.remove(mPlayerCountFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.add(android.R.id.content, mWhoGoesFragment, WhoGoesFragment.TAG);
		ft.addToBackStack("whoGoes");
		ft.commit();
	}

}
