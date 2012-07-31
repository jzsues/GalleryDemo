/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageGridView.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * @author jiangzm
 * 
 */
public class GridImageView extends ImageView {

	Paint paint;

	/**
	 * @param context
	 */
	public GridImageView(Context context) {
		super(context);
		setBackgroundColor(Color.GREEN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
