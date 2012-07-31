/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageLoader.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.jiangzm.cache.ImageCache;
import com.jiangzm.file.FileUtils;
import com.jiangzm.net.HttpHelper;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author jiangzm
 * 
 */
public class ImageLoader {
	static final String LOG_TAG = "ImageLoader";

	static HashMap<ImageView, String> pendingViews = new HashMap<ImageView, String>();
	static HashMap<String, ArrayList<ImageView>> pendingDownloads = new HashMap<String, ArrayList<ImageView>>();

	public static void asyncLoadImageToView(final ImageView imageView, final String url, final int defaultResource,
			final ImageCallback callback) {
		if (imageView == null) {
			return;
		}
		if (url == null) {
			return;
		}
		final Context context = imageView.getContext();
		pendingViews.remove(imageView);
		Drawable defaultDrawable = null;
		if (defaultResource != 0) {
			defaultDrawable = context.getResources().getDrawable(defaultResource);
		}
		final ImageCache cache = ImageCache.getInstance();
		Drawable drawable = cache.get(url);
		if (drawable != null) {
			// load image from cache
			imageView.setImageDrawable(drawable);
			if (callback != null) {
				callback.onLoaded(imageView, drawable, url, true);
			}
			return;
		}
		final String cacheFilePathForUrl = FileUtils.getCacheFilePathForUrl(context, url);
		Log.d(LOG_TAG, "image cache path:" + cacheFilePathForUrl);
		if (cacheFilePathForUrl != null) {
			drawable = FileUtils.loadDrawableFromFile(context, cacheFilePathForUrl);
			if (drawable != null) {
				imageView.setImageDrawable(drawable);
				cache.put(url, drawable);
				if (callback != null) {
					callback.onLoaded(imageView, drawable, url, true);
				}
				return;
			}
		}

		// set loading image while it is HTTP downloading
		if (defaultDrawable != null) {
			imageView.setImageDrawable(defaultDrawable);
		}
		pendingViews.put(imageView, url);

		ArrayList<ImageView> currentDownload = pendingDownloads.get(url);
		if (currentDownload != null) {
			currentDownload.add(imageView);
			return;
		}
		final ArrayList<ImageView> downloads = new ArrayList<ImageView>();
		downloads.add(imageView);
		pendingDownloads.put(url, downloads);

		AsyncTask<String, Integer, BitmapDrawable> loader = new AsyncTask<String, Integer, BitmapDrawable>() {
			@Override
			protected BitmapDrawable doInBackground(String... params) {
				InputStream fis = HttpHelper.downloadFile(context, url);
				BitmapDrawable res = null;
				try {
					if (fis != null) {
						res = FileUtils.loadDrawableFromStream(context, fis);
					}
				} catch (Exception e) {
					Log.e(LOG_TAG, "download data form url " + url, e);
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							Log.e(LOG_TAG, "download data form url " + url, e);
						}
					}
				}
				return res;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(BitmapDrawable result) {
				pendingDownloads.remove(url);
				for (ImageView image : downloads) {
					String pendingUrl = pendingViews.get(image);
					if (!url.equals(pendingUrl)) {
						continue;
					}
					pendingViews.remove(image);
					if (result != null) {
						cache.put(url, result);
						final Drawable newImage = result;
						final ImageView imageView = image;
						imageView.setImageDrawable(newImage);
						if (callback != null) {
							callback.onLoaded(imageView, result, url, false);
						}
					}
				}
			}

		};
		loader.execute();
	}
}
