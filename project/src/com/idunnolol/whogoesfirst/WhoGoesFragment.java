package com.idunnolol.whogoesfirst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WhoGoesFragment extends Fragment {

	public static final String TAG = WhoGoesFragment.class.getName();

	private static final String ARG_NUM_PLAYERS = "ARG_NUM_PLAYERS";
	private static final String ARG_PLAYER_PICKED = "ARG_PLAYER_PICKED";

	private int mNumPlayers;
	private int mPlayerPicked;

	public static WhoGoesFragment newInstance(int numPlayers, int playerPicked) {
		WhoGoesFragment fragment = new WhoGoesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_NUM_PLAYERS, numPlayers);
		args.putInt(ARG_PLAYER_PICKED, playerPicked);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mNumPlayers = args.getInt(ARG_NUM_PLAYERS);
		mPlayerPicked = args.getInt(ARG_PLAYER_PICKED);

		Log.i("WhoGoesFirst", "numPlayers=" + mNumPlayers + ", playerPicked=" + mPlayerPicked);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_who_goes, container, false);

		TextView whoTextView = Ui.findView(v, R.id.who_text_view);
		whoTextView.setText(createText());

		ArrowRow arrowRow1 = Ui.findView(v, R.id.arrow_row1);
		ArrowRow arrowRow2 = Ui.findView(v, R.id.arrow_row2);
		ArrowView arrowView = Ui.findView(v, R.id.arrow_view);

		int rotation;
		if (mPlayerPicked == 0) {
			// Point down if you were picked
			rotation = 180;
		}
		else if (mPlayerPicked == 1 && mNumPlayers == 2) {
			// Point up if there are only 2 players and the other player was picked
			rotation = 0;
		}
		else if (countToLeft()) {
			rotation = 270;
		}
		else {
			rotation = 90;
		}

		// We've got special configurations for 0-6 spaces away.  After that, fall back
		// to the arrow with a multiplier inside of it.  This could probably be
		// done in a more dynamic fashion but I'm being lazy tonight.
		int spacesAway = howFarAway();
		switch (spacesAway) {
		case 0:
		case 1:
			arrowView.setArrowRotation(rotation);

			arrowRow1.setVisibility(View.GONE);
			arrowRow2.setVisibility(View.GONE);
			break;
		case 2:
		case 3:
			arrowRow1.bind(rotation, spacesAway);

			arrowView.setVisibility(View.GONE);
			arrowRow2.setVisibility(View.GONE);
			break;
		case 4:
			arrowRow1.bind(rotation, 2);
			arrowRow2.bind(rotation, 2);

			arrowView.setVisibility(View.GONE);
			break;
		case 5:
			arrowRow1.bind(rotation, 2);
			arrowRow2.bind(rotation, 3);

			arrowView.setVisibility(View.GONE);
			break;
		case 6:
			arrowRow1.bind(rotation, 3);
			arrowRow2.bind(rotation, 3);

			arrowView.setVisibility(View.GONE);
			break;
		default:
			arrowView.setMultiplier(spacesAway);
			arrowView.setArrowRotation(rotation);

			arrowRow1.setVisibility(View.GONE);
			arrowRow2.setVisibility(View.GONE);
			break;
		}

		return v;
	}

	// Determine the text based on the # of players 
	private CharSequence createText() {
		String text = null;
		String highlight = null;
		int highlightColor = getResources().getColor(android.R.color.holo_blue_light);

		if (mPlayerPicked == 0) {
			// You go first
			text = getString((mNumPlayers == 1) ? R.string.you_go_first_duh : R.string.you_go_first);
			highlight = getString(R.string.you_go_first_highlight);
		}
		else if (mNumPlayers == 2) {
			// Special case; if there are two players but you don't first
			text = getString(R.string.other_player_goes_first);
			highlight = getString(R.string.other_player_goes_first_highlight);
		}
		else if (mPlayerPicked == 1) {
			// Player clockwise (aka, one left) goes first
			text = getString(R.string.player_directly_left);
			highlight = getString(R.string.player_directly_left_highlight);
		}
		else if (mPlayerPicked == mNumPlayers - 1) {
			// Player counterclockwise (aka, one right) goes first
			text = getString(R.string.player_directly_right);
			highlight = getString(R.string.player_directly_right_highlight);
		}
		else {
			int textResId;
			int highlightResId;
			int val = howFarAway();

			// Figure out quickest way to get to the player (left used if equidistance)
			if (countToLeft()) {
				textResId = R.string.player_to_left_TEMPLATE;
				highlightResId = R.string.player_to_left_highlight_TEMPLATE;
			}
			else {
				textResId = R.string.player_to_right_TEMPLATE;
				highlightResId = R.string.player_to_right_highlight_TEMPLATE;
			}

			String textVal;
			switch (val) {
			case 2:
				textVal = getString(R.string.two_full);
				break;
			case 3:
				textVal = getString(R.string.three_full);
				break;
			case 4:
				textVal = getString(R.string.four_full);
				break;
			case 5:
				textVal = getString(R.string.five_full);
				break;
			case 6:
				textVal = getString(R.string.six_full);
				break;
			default:
				textVal = Integer.toString(val);
				break;
			}

			text = getString(textResId, textVal);
			highlight = getString(highlightResId, textVal);
		}

		return Ui.createHighlightedText(text, highlight, highlightColor);
	}

	// If there are > 2 players, tells you which way to count (left or right)
	private boolean countToLeft() {
		return mPlayerPicked <= mNumPlayers / 2;
	}

	// Returns the number of spaces away the person is from you
	private int howFarAway() {
		return Math.min(mPlayerPicked, mNumPlayers - mPlayerPicked);
	}
}
