package com.idunnolol.whogoesfirst;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ArrowRow extends LinearLayout {

	private List<ArrowView> mArrowViews;

	public ArrowRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		mArrowViews = new ArrayList<ArrowView>();
		mArrowViews.add((ArrowView) Ui.findView(this, R.id.arrow_view1));
		mArrowViews.add((ArrowView) Ui.findView(this, R.id.arrow_view2));
		mArrowViews.add((ArrowView) Ui.findView(this, R.id.arrow_view3));
	}

	public void bind(int rotation, int num) {
		if (num < 0 || num > 3) {
			throw new IllegalArgumentException("Num has to be between 0 and 3, you chose " + num);
		}

		for (int a = 0; a < mArrowViews.size(); a++) {
			ArrowView arrowView = mArrowViews.get(a);
			arrowView.setRotation(rotation);
			arrowView.setVisibility((a < num) ? View.VISIBLE : View.GONE);
		}
	}
}
