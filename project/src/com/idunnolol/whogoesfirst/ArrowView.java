package com.idunnolol.whogoesfirst;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class ArrowView extends View {

	private Region mClipRegion;

	private Paint mArrowPaint;
	private Path mArrowPath;
	private Region mArrowRegion;

	private TextPaint mTextPaint;
	private Rect mBounds;
	private Region mTextRegion;

	private int mWidth;
	private int mHeight;

	private int mMultiplier = 0;

	public ArrowView(Context context) {
		this(context, null, 0);
	}

	public ArrowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArrowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mArrowPaint = new Paint();
		mArrowPaint.setColor(context.getResources().getColor(android.R.color.holo_blue_light));
		mArrowPaint.setStyle(Style.FILL);
		mArrowPaint.setAntiAlias(true);

		mTextPaint = new TextPaint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setStyle(Style.FILL);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextAlign(Align.CENTER);

		mArrowPath = new Path();
		mBounds = new Rect();

		mClipRegion = new Region();
		mArrowRegion = new Region();
		mTextRegion = new Region();
	}

	public void setMultiplier(int multiplier) {
		mMultiplier = multiplier;

		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;
		mClipRegion.set(0, 0, w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Draw an arrow
		mArrowPath.rewind();
		mArrowPath.moveTo(0, mHeight / 2);
		mArrowPath.lineTo(mWidth / 2, 0);
		mArrowPath.lineTo(mWidth, mHeight / 2);
		mArrowPath.lineTo(mWidth * 3 / 4, mHeight / 2);
		mArrowPath.lineTo(mWidth * 3 / 4, mHeight);
		mArrowPath.lineTo(mWidth / 4, mHeight);
		mArrowPath.lineTo(mWidth / 4, mHeight / 2);
		mArrowPath.lineTo(0, mHeight / 2);

		canvas.drawPath(mArrowPath, mArrowPaint);

		// Draw a multiplier (if non-zero)
		if (mMultiplier > 0) {
			// Always orient up (even if the View is rotated)
			canvas.save();
			canvas.rotate(-getRotation(), mWidth / 2.0f, mHeight / 2.0f);

			String text = getContext().getString(R.string.multiplier_TEMPLATE, mMultiplier);

			// We need to adapt the text size to the space we have (and also
			// find out its height so we can properly center align it)
			mTextPaint.setTextSize(Math.min(mWidth, mHeight) / 3.5f);
			mArrowRegion.setPath(mArrowPath, mClipRegion);

			getTextRegion(text);
			while (mArrowRegion.op(mTextRegion, Region.Op.REVERSE_DIFFERENCE) && mTextPaint.getTextSize() > 1) {
				mTextPaint.setTextSize(mTextPaint.getTextSize() - 1);
				getTextRegion(text);
			}

			canvas.drawText(text, mWidth / 2.0f, (mHeight + mBounds.height()) / 2.0f, mTextPaint);

			canvas.restore();
		}
	}

	private void getTextRegion(String text) {
		mTextPaint.getTextBounds(text, 0, text.length(), mBounds);
		mTextRegion.set((int) (mBounds.left + (mWidth / 2.0f) - mBounds.width() / 2.0f),
				(int) (mBounds.top + (mHeight + mBounds.height()) / 2.0f),
				(int) (mBounds.right + (mWidth / 2.0f) - mBounds.width() / 2.0f),
				(int) (mBounds.bottom + (mHeight + mBounds.height()) / 2.0f));
	}
}
