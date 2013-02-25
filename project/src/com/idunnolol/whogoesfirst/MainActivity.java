package com.idunnolol.whogoesfirst;

import com.idunnolol.whogoesfirst.PlayerCountFragment.PlayerCountFragmentListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements PlayerCountFragmentListener {

	private PlayerCountFragment mPlayerCountFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			mPlayerCountFragment = new PlayerCountFragment();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(android.R.id.content, mPlayerCountFragment, PlayerCountFragment.TAG);
			ft.commit();
		}
		else {
			mPlayerCountFragment = Ui.findFragment(this, PlayerCountFragment.TAG);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// PlayerCountFragmentListener

	@Override
	public void onPlayerCountSelected(int count) {
		// TODO Auto-generated method stub

	}
}
