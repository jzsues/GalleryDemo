/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageViewActivity.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.gallerydemo;

import com.jiangzm.adapter.ImageSource;
import com.jiangzm.image.ImageLoader;
import com.jiangzm.view.ZoomImageView;
import com.jiangzm.view.ZoomImageView.SwitchListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * @author jiangzm
 * 
 */
public class ImageViewActivity extends Activity {
	public static final String LOG_TAG = "ImageViewActivity";

	public static final String URL_KEY = "_url";
	public static final String POSITION_KEY = "_position";

	ZoomImageView imageView;
	String url;
	int position;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		url = intent.getStringExtra(URL_KEY);
		position = intent.getIntExtra(POSITION_KEY, -1);
		Log.d(LOG_TAG, "image url:" + url + ",position:" + position);
		imageView = new ZoomImageView(this);
		imageView.setSwitchListener(new SwitchListener() {

			public void switched(int switchType) {
				position = position + switchType;
				String item = ImageSource.getInstance().getItem(position);
				if (item != null) {
					ImageLoader.asyncLoadImageToView(imageView, item, R.drawable.loading, null);
				} else {
					if (switchType == SwitchListener.SWITCH_TYPE_LEFT) {
						Toast.makeText(imageView.getContext(), "已到达最后一张图片", 500).show();
					} else {
						Toast.makeText(imageView.getContext(), "已到达第一张图片", 500).show();
					}
				}
			}

		});
		ImageLoader.asyncLoadImageToView(imageView, url, R.drawable.loading, null);
		setContentView(imageView);
	}
}
