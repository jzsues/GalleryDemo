/*
 * Best Principles & Sample Code.
 * GalleryDemo ImageGridAdapter.java
 * jiangzm 2012-7-31
 * All rights reserved.
 */
package com.jiangzm.adapter;

import com.jiangzm.gallerydemo.R;
import com.jiangzm.image.ImageLoader;
import com.jiangzm.view.GridImageView;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout.LayoutParams;

/**
 * @author jiangzm
 * 
 */
public class ImageGridAdapter extends BaseAdapter {
	public static final String LOG_TAG = "ImageGridAdapter";

	private Context mContext;

	private int page = 0;

	private ImageSource source = ImageSource.getInstance();
	OnLongClickListener onLongClickListener;
	OnTouchListener onTouchListener;

	public ImageGridAdapter(Context mContext) {
		this.mContext = mContext;
		onLongClickListener = new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView image = (ImageView) v;
				image.setPadding(5, 5, 5, 5);
				return false;
			}
		};
		onTouchListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
					ImageView image = (ImageView) v;
					image.setPadding(0, 0, 0, 0);
					return false;
				}
				return false;
			}
		};
	}

	public boolean nextPage() {
		int totalPage = source.getTotalPage();
		if (page < totalPage) {
			page++;
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (source != null) {
			return source.getCount(page);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return source.getItem(position).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return source.getItem(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		GridImageView imageView;
		if (convertView == null) {
			convertView = imageView = new GridImageView(mContext);
			//imageView.setOnLongClickListener(onLongClickListener);
			//imageView.setOnTouchListener(onTouchListener);
		} else {
			imageView = (GridImageView) convertView;
		}
		int hight = getPixels(80);
		imageView.setLayoutParams(new GridView.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, hight)));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(0, 0, 0, 0);
		ImageLoader.asyncLoadImageToView(imageView, source.getItem(position), R.drawable.loading, null);
		return imageView;
	}

	private int getPixels(int dipValue) {
		Resources r = mContext.getResources();
		DisplayMetrics displayMetrics = r.getDisplayMetrics();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, displayMetrics);
		return px;
	}

}
