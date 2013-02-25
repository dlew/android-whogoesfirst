package com.idunnolol.whogoesfirst;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

/**
 * Ui utils.  Inspired by some Code 42 love.
 */
public class Ui {
	
	@SuppressWarnings("unchecked")
	public static <T extends View> T findView(Activity activity, int id) {
		return (T) activity.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T findView(View view, int id) {
		return (T) view.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Fragment> T findFragment(Activity activity, String tag) {
		return (T) activity.getFragmentManager().findFragmentByTag(tag);
	}
}
