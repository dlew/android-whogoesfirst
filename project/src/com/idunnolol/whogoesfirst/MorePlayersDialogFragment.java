package com.idunnolol.whogoesfirst;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MorePlayersDialogFragment extends DialogFragment {

	public static final String TAG = MorePlayersDialogFragment.class.getName();

	private PlayerCountListener mListener;

	private static final int DEFAULT_VALUE = 13;

	private static final String INSTANCE_VALUE = "INSTANCE_VALUE";

	// Will either be an EditText or a NumberPicker
	private View mPickerView;

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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.more_title);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.dialog_more_players, null);
		builder.setView(view);

		int value = savedInstanceState == null ? DEFAULT_VALUE : savedInstanceState.getInt(INSTANCE_VALUE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			NumberPicker numPicker = Ui.findView(view, R.id.number_picker);
			numPicker.setMinValue(1);
			numPicker.setMaxValue(Integer.MAX_VALUE);
			numPicker.setValue(value);
			numPicker.setWrapSelectorWheel(false);

			mPickerView = numPicker;
		}
		else {
			EditText editText = Ui.findView(view, R.id.edit_text);
			editText.setText(Integer.toString(value));
			editText.selectAll();
			editText.requestFocus();

			mPickerView = editText;
		}

		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onPlayerCountSelected(getValue());
			}
		});

		builder.setNegativeButton(android.R.string.cancel, null);

		return builder.create();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(INSTANCE_VALUE, getValue());
	}

	private int getValue() {
		mPickerView.clearFocus();
		if (mPickerView instanceof EditText) {
			return Integer.parseInt(((EditText) mPickerView).getText().toString());
		}
		else {
			return ((NumberPicker) mPickerView).getValue();
		}
	}

}
