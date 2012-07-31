/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageCallback.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @author jiangzm
 * 
 */
public interface ImageCallback {
	void onLoaded(ImageView imageView, Drawable loadedDrawable, String url, boolean loadedFromCache);
}
