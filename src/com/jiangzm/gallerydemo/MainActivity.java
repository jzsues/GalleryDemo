package com.jiangzm.gallerydemo;

import com.jiangzm.adapter.ImageGridAdapter;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class MainActivity extends Activity implements OnItemLongClickListener, OnItemClickListener {
	final private String LOG_TAG = "MainActivity";
	public static final int SDK_INT = Build.VERSION.SDK_INT;
	ImageGridAdapter adapter;
	GridView imageGrid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageGrid = (GridView) findViewById(R.id.imageGrid);
		adapter = new ImageGridAdapter(this);
		imageGrid.setAdapter(adapter);
		AutoLoadListener autoLoadListener = new AutoLoadListener(new AutoLoadCallBack() {
			public boolean nextPage() {
				boolean nextPage = adapter.nextPage();
				if (nextPage) {
					imageGrid.invalidateViews();
					return false;
				} else {
					return true;
				}

			}
		});
		imageGrid.setOnScrollListener(autoLoadListener);
		if (SDK_INT > 7) {
			imageGrid.setOnTouchListener(new FingerTracker(autoLoadListener));
		}
		imageGrid.setOnItemLongClickListener(this);
		imageGrid.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
		Log.d(LOG_TAG, "onItemLongClick");
		ImageView image = (ImageView) view;
		image.setPadding(5, 5, 5, 5);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		int paddingTop = view.getPaddingTop();
		if (paddingTop > 0) {
			view.setPadding(0, 0, 0, 0);
		} else {
			String url = (String) adapterView.getItemAtPosition(position);
			Log.d(LOG_TAG, "item url:" + url);
			Intent viewItent = new Intent(this, ImageViewActivity.class);
			viewItent.putExtra(ImageViewActivity.URL_KEY, url);
			viewItent.putExtra(ImageViewActivity.POSITION_KEY, position);
			startActivity(viewItent);
		}

	}

	public interface AutoLoadCallBack {
		public boolean nextPage();
	}

	public class AutoLoadListener implements OnScrollListener {
		private int getLastVisiblePosition = 0;
		private int lastVisiblePositionY = 0;
		private AutoLoadCallBack mCallback;
		private boolean lastPage;

		public boolean isLastPage() {
			return lastPage;
		}

		public void setLastPage(boolean lastPage) {
			this.lastPage = lastPage;
		}

		public AutoLoadListener(AutoLoadCallBack mCallback) {
			super();
			this.mCallback = mCallback;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(
		 * android.widget.AbsListView, int)
		 */
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				// 滚动到底部
				if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
					View subView = (View) view.getChildAt(view.getChildCount() - 1);
					int[] location = new int[2];
					subView.getLocationOnScreen(location);
					int y = location[1];

					if (view.getLastVisiblePosition() != getLastVisiblePosition && lastVisiblePositionY != y)// 第一次
					{
						if (!lastPage) {
							Toast.makeText(view.getContext(), "再次拖动加载更多", 500).show();
						}
						getLastVisiblePosition = view.getLastVisiblePosition();
						lastVisiblePositionY = y;
						return;
					} else if (view.getLastVisiblePosition() == getLastVisiblePosition && lastVisiblePositionY == y)// 第二次
					{
						lastPage = mCallback.nextPage();
						if (lastPage) {
							Toast.makeText(imageGrid.getContext(), "已到达最后一页", 500).show();
						}
					}
				}

				// 初始化
				getLastVisiblePosition = 0;
				lastVisiblePositionY = 0;
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AbsListView.OnScrollListener#onScroll(android.widget
		 * .AbsListView, int, int, int)
		 */
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		}
	}

	public class FingerTracker implements View.OnTouchListener {
		private OnScrollListener myOnScrollListener;

		public FingerTracker(OnScrollListener onScrollListener) {
			myOnScrollListener = onScrollListener;
		}

		public boolean onTouch(View view, MotionEvent event) {
			final int action = event.getAction();
			boolean mFingerUp = action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL;
			if (mFingerUp) {
				myOnScrollListener.onScrollStateChanged((AbsListView) view, OnScrollListener.SCROLL_STATE_FLING);
				myOnScrollListener.onScrollStateChanged((AbsListView) view, OnScrollListener.SCROLL_STATE_IDLE);
			}
			return false;
		}
	}
}
