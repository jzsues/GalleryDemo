package com.jiangzm.cache;

import android.graphics.drawable.Drawable;

public final class ImageCache extends SoftReferenceHashMap<String, Drawable> {
	private static ImageCache mInstance = new ImageCache();

	public static ImageCache getInstance() {
		return mInstance;
	}

	private ImageCache() {
	}
}
