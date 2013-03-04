package com.idunnolol.whogoesfirst;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ArrowView extends View {

	private Paint mPaint;
	private Path mPath;

	private int mWidth;
	private int mHeight;

	public ArrowView(Context context) {
		this(context, null, 0);
	}

	public ArrowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArrowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mPaint = new Paint();
		mPaint.setColor(context.getResources().getColor(android.R.color.holo_blue_light));
		mPaint.setStyle(Style.FILL);
		mPaint.setAntiAlias(true);

		mPath = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPath.rewind();
		mPath.moveTo(0, mHeight / 2);
		mPath.lineTo(mWidth / 2, 0);
		mPath.lineTo(mWidth, mHeight / 2);
		mPath.lineTo(mWidth * 3 / 4, mHeight / 2);
		mPath.lineTo(mWidth * 3 / 4, mHeight);
		mPath.lineTo(mWidth / 4, mHeight);
		mPath.lineTo(mWidth / 4, mHeight / 2);
		mPath.lineTo(0, mHeight / 2);

		canvas.drawPath(mPath, mPaint);
	}
}
