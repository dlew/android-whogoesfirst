package com.idunnolol.whogoesfirst;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
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
	public static <T extends Fragment> T findFragment(FragmentActivity activity, String tag) {
		return (T) activity.getSupportFragmentManager().findFragmentByTag(tag);
	}

	public static CharSequence createHighlightedText(String text, String highlight, int highlightColor) {
		Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
		int start = text.indexOf(highlight);
		spannable.setSpan(new ForegroundColorSpan(highlightColor), start, start + highlight.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}
}
