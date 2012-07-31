/*
 * Best Principles & Sample Code.
 * GalleryDemo FileUtils.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * @author jiangzm
 * 
 */
public class FileUtils {
	public static final String CACHE_FOLDER_NAME = "jiangzm";
	static final String LOG_TAG = "FileUtils";
	static Resources resources;
	static DisplayMetrics metrics;

	private static void prepareResources(Context context) {
		if (metrics != null && resources != null)
			return;
		metrics = new DisplayMetrics();
		Activity act = (Activity) context;
		act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		AssetManager mgr = context.getAssets();
		resources = new Resources(mgr, metrics, context.getResources().getConfiguration());
	}

	public static BitmapDrawable loadDrawableFromStream(Context context, InputStream stream) {
		prepareResources(context);
		final Bitmap bitmap = BitmapFactory.decodeStream(stream);
		return new BitmapDrawable(resources, bitmap);
	}

	public static BitmapDrawable loadDrawableFromFile(Context context, String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		prepareResources(context);
		final Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		return new BitmapDrawable(resources, bitmap);
	}

	public static int copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] stuff = new byte[1024];
		int read = 0;
		int total = 0;
		while ((read = input.read(stuff)) != -1) {
			output.write(stuff, 0, read);
			total += read;
		}
		return total;
	}

	public static boolean checkSDCardState() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static String checkSDCardCacheFolder() {
		boolean checkSDCardState = checkSDCardState();
		if (checkSDCardState) {
			String folder = "/sdcard/" + CACHE_FOLDER_NAME;
			File path = new File(folder);
			if (!path.exists()) {
				path.mkdirs();
			}
			return folder;
		} else {
			return null;
		}
	}

	public static String getCacheFilePathForUrl(Context context, String url) {
		String fileName = url.hashCode() + ".image";
		String sdcardCacheFolder = checkSDCardCacheFolder();
		if (sdcardCacheFolder == null) {
			return context.getFilesDir().getAbsolutePath() + "/" + fileName;
		}
		return sdcardCacheFolder + "/" + fileName;
	}

}
