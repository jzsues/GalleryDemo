/*
 * Best Principles & Sample Code.
 * GalleryDemo HttpHelper.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.jiangzm.file.FileUtils;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;

/**
 * @author jiangzm
 * 
 */
public class HttpHelper {
	protected static final String LOG_TAG = "ImageLoader";

	public static InputStream downloadFile(Context context, String url) {
		AndroidHttpClient client = AndroidHttpClient.newInstance("jiangzm.image.loader");
		FileOutputStream fos = null;
		try {
			HttpGet get = new HttpGet(url);
			final HttpParams httpParams = new BasicHttpParams();
			HttpClientParams.setRedirecting(httpParams, true);
			get.setParams(httpParams);
			HttpResponse resp = client.execute(get);
			int status = resp.getStatusLine().getStatusCode();
			if (status != HttpURLConnection.HTTP_OK) {
				return null;
			}
			HttpEntity entity = resp.getEntity();
			InputStream is = entity.getContent();
			String cacheFilePathForUrl = FileUtils.getCacheFilePathForUrl(context, url);
			File output = new File(cacheFilePathForUrl);
			fos = new FileOutputStream(output);
			// write image data to file
			FileUtils.copyStream(is, fos);
			return new FileInputStream(output);
		} catch (IOException e) {
			Log.e(LOG_TAG, "download data form url " + url, e);
		} finally {
			client.close();
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				Log.e(LOG_TAG, "download data form url " + url, e);
			}

		}
		return null;
	}
}
