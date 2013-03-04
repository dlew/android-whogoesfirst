package com.idunnolol.whogoesfirst;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.NumberPicker;

public class MorePlayersDialogFragment extends DialogFragment {

	public static final String TAG = MorePlayersDialogFragment.class.getName();

	private PlayerCountListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof PlayerCountListener) {
			mListener = (PlayerCountListener) activity;
		}
		else {
			throw new RuntimeException("MorePlayersDialogFragment Activity needs to implement listener!");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Construct number picker
		final NumberPicker numPicker = new NumberPicker(getActivity());
		numPicker.setMinValue(1);
		numPicker.setMaxValue(Integer.MAX_VALUE);
		numPicker.setValue(13);
		numPicker.setWrapSelectorWheel(false);

		// Build and return dialog
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.more_title);
		builder.setView(numPicker);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onPlayerCountSelected(numPicker.getValue());
			}
		});
		builder.setNegativeButton(android.R.string.cancel, null);
		return builder.create();
	}
}
