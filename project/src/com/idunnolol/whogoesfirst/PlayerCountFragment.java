package com.idunnolol.whogoesfirst;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerCountFragment extends Fragment {

	public static final String TAG = PlayerCountFragment.class.getName();

	private PlayerCountFragmentListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof PlayerCountFragmentListener) {
			mListener = (PlayerCountFragmentListener) activity;
		}
		else {
			throw new RuntimeException("PlayerCountFragment Activity needs to implement listener!");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_player_count, container, false);

		// Rig up the button click listener
		Ui.findView(v, R.id.two_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.three_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.four_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.five_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.six_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.seven_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.eight_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.nine_button).setOnClickListener(mButtonClickListener);
		Ui.findView(v, R.id.ten_button).setOnClickListener(mButtonClickListener);

		// Rig up more button
		Ui.findView(v, R.id.more_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Implement "more" functionality
				Toast.makeText(getActivity(), getString(R.string.more_warning), Toast.LENGTH_SHORT).show();
			}
		});

		// Highlight part of the inquiry text
		String text = getString(R.string.player_count_inquiry);
		String highlight = getString(R.string.player_count_inquiry_highlight);
		Spannable spannable = Spannable.Factory.getInstance().newSpannable(getString(R.string.player_count_inquiry));
		int start = text.indexOf(highlight);
		int color = getResources().getColor(android.R.color.holo_blue_light);
		spannable.setSpan(new ForegroundColorSpan(color), start, start + highlight.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		TextView inquiryTextView = Ui.findView(v, R.id.inquiry_text_view);
		inquiryTextView.setText(spannable);

		return v;
	}

	private OnClickListener mButtonClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.two_button:
				mListener.onPlayerCountSelected(2);
				break;
			case R.id.three_button:
				mListener.onPlayerCountSelected(3);
				break;
			case R.id.four_button:
				mListener.onPlayerCountSelected(4);
				break;
			case R.id.five_button:
				mListener.onPlayerCountSelected(5);
				break;
			case R.id.six_button:
				mListener.onPlayerCountSelected(6);
				break;
			case R.id.seven_button:
				mListener.onPlayerCountSelected(7);
				break;
			case R.id.eight_button:
				mListener.onPlayerCountSelected(8);
				break;
			case R.id.nine_button:
				mListener.onPlayerCountSelected(9);
				break;
			case R.id.ten_button:
				mListener.onPlayerCountSelected(10);
				break;
			}
		}
	};

	public interface PlayerCountFragmentListener {
		public void onPlayerCountSelected(int count);
	}
}
