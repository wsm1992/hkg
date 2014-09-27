package com.siuming.hkg.view.component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.siuming.hkg.R;
import com.siuming.hkg.R.id;
import com.siuming.hkg.R.layout;
import com.siuming.hkg.R.string;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新，底部更多
 * 
 */
public class RefreshListView extends ListView implements OnScrollListener {

	private float mDownY;
	private float mMoveY;

	private int mHeaderHeight;

	private int mCurrentScrollState;

	private Boolean isListViewMoving = false;
	private Boolean isFlingDown = false;

	private final static int NONE_PULL_REFRESH = 0; // 正常状态
	private final static int ENTER_PULL_REFRESH = 1; // 进入下拉刷新状态
	private final static int OVER_PULL_REFRESH = 2; // 进入松手刷新状态
	private final static int EXIT_PULL_REFRESH = 3; // 松手后反弹和加载状态
	private int mPullRefreshState = 0; // 记录刷新状态

	private final static int REFRESH_BACKING = 0; // 反弹中
	private final static int REFRESH_BACED = 1; // 达到刷新界限，反弹结束后
	private final static int REFRESH_RETURN = 2; // 没有达到刷新界限，返回
	private final static int REFRESH_DONE = 3; // 加载数据结束

	private LinearLayout mHeaderLinearLayout = null;
	private LinearLayout mFooterLinearLayout = null;
	private TextView mHeaderTextView = null;
	private TextView mHeaderUpdateText = null;
	private ImageView mHeaderPullDownImageView = null;
	private ImageView mHeaderReleaseDownImageView = null;
	private ProgressBar mHeaderProgressBar = null;
	/*
	 * private TextView mFooterTextView = null; private ProgressBar
	 * mFooterProgressBar = null;
	 */

	private SimpleDateFormat mSimpleDateFormat;
	private RefreshListener refreshListener;

	public void setOnRefreshListener(RefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public RefreshListView(Context context) {
		this(context, null);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	void init(final Context context) {
		mHeaderLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.refresh_list_header, null);
		addHeaderView(mHeaderLinearLayout);
		mHeaderTextView = (TextView) findViewById(R.id.refresh_list_header_text);
		mHeaderUpdateText = (TextView) findViewById(R.id.refresh_list_header_last_update);
		mHeaderPullDownImageView = (ImageView) findViewById(R.id.refresh_list_header_pull_down);
		mHeaderReleaseDownImageView = (ImageView) findViewById(R.id.refresh_list_header_release_up);
		mHeaderProgressBar = (ProgressBar) findViewById(R.id.refresh_list_header_progressbar);

		mFooterLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.refresh_list_footer, null);
		addFooterView(mFooterLinearLayout);
		/*
		 * mFooterLinearLayout = (LinearLayout)
		 * LayoutInflater.from(context).inflate(R.layout.refresh_list_footer,
		 * null); addFooterView(mFooterLinearLayout); mFooterProgressBar =
		 * (ProgressBar) findViewById(R.id.refresh_list_footer_progressbar);
		 * mFooterTextView = (TextView)
		 * mFooterLinearLayout.findViewById(R.id.refresh_list_footer_text);
		 * mFooterLinearLayout.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if
		 * (context.getString(R.string
		 * .app_list_footer_more).equals(mFooterTextView.getText())) {
		 * mFooterTextView.setText(R.string.app_list_footer_loading);
		 * mFooterProgressBar.setVisibility(View.VISIBLE); if (mRefreshListener
		 * != null) { mRefreshListener.more(); } } } });
		 */

		setSelection(1);
		setOnScrollListener(this);
		measureView(mHeaderLinearLayout);
		mHeaderHeight = mHeaderLinearLayout.getMeasuredHeight();

		mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
		mHeaderUpdateText.setText(context.getString(R.string.app_list_header_refresh_last_update,
				mSimpleDateFormat.format(new Date())));
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			onTouchMove(ev);
			if (mDownY > mMoveY) {
				isFlingDown = true;
			} else {
				isFlingDown = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mPullRefreshState == OVER_PULL_REFRESH) {
				onTouchUpWhenOverPull();
			} else if (mPullRefreshState == ENTER_PULL_REFRESH) {
				onTouchUpWhenEnterPull();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void onTouchMove(MotionEvent ev) {
		mMoveY = ev.getY();
		if (mPullRefreshState == OVER_PULL_REFRESH) {
			mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
					(int) ((mMoveY - mDownY) / 3), mHeaderLinearLayout.getPaddingRight(),
					mHeaderLinearLayout.getPaddingBottom());
		}
	}

	private void onTouchUpWhenOverPull() {
		new Thread() {
			public void run() {
				try {
					Message msg;
					while (mHeaderLinearLayout.getPaddingTop() > 1) {
						msg = mHandler.obtainMessage();
						msg.what = REFRESH_BACKING;
						mHandler.sendMessage(msg);
						try {
							sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					msg = mHandler.obtainMessage();
					msg.what = REFRESH_BACED;
					mHandler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	private void onTouchUpWhenEnterPull() {
		if (isListViewMoving == false) {
			isListViewMoving = true;
			new Thread() {
				public void run() {
					int height = mHeaderLinearLayout.getBottom();
					while (height > 0) {
						Message msg = mHandler.obtainMessage();
						msg.what = REFRESH_RETURN;
						height *= 0.95;
						msg.arg1 = height;
						mHandler.sendMessage(msg);
						try {
							sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					isListViewMoving = false;
				}
			}.start();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		if (isScrolling()) {
			if (firstVisibleItem == 0) {
				if (isEnterPullHeight()) {
					onScrollEnterPull();
				} else if (isOverPullHeight()) {
					onScrollOverPull();
				}
			} else {
				if (mPullRefreshState != NONE_PULL_REFRESH) {
					mPullRefreshState = NONE_PULL_REFRESH;
				}
				updateListView(firstVisibleItem, visibleItemCount, totalItemCount);
			}
		} else if (isFlinging()) {
			if (firstVisibleItem == 0) {
				if (mPullRefreshState == NONE_PULL_REFRESH && isListViewMoving == false) {
					setSelection(1);
				} else if (mPullRefreshState == ENTER_PULL_REFRESH) {
					onTouchUpWhenEnterPull();
				}
			} else {
				if (mPullRefreshState != NONE_PULL_REFRESH) {
					mPullRefreshState = NONE_PULL_REFRESH;
				}
				updateListView(firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}
	}

	private void updateListView(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
		if (isFlingDown) {
			/*if (isShouldLoadUpdate(firstVisibleItem, visibleItemCount, totalItemCount)) {
				refreshListener.loadUpdate();
			}
			if (isShouldShowUpdate(firstVisibleItem, visibleItemCount, totalItemCount)) {
				refreshListener.showUpdate();
			}*/
			if (isWaitUpdate(firstVisibleItem, visibleItemCount, totalItemCount)) {
				refreshListener.waitUpdate();
			}
		}
	}

	private boolean isWaitUpdate(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		return firstVisibleItem + visibleItemCount >= totalItemCount;
	}

	private boolean isShouldShowUpdate(int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		return firstVisibleItem + visibleItemCount >= totalItemCount - 5;
	}

	private boolean isShouldLoadUpdate(int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		return firstVisibleItem + visibleItemCount >= totalItemCount - 15;
	}

	private void onScrollEnterPull() {
		if (mPullRefreshState != ENTER_PULL_REFRESH) {
			mPullRefreshState = ENTER_PULL_REFRESH;
			mHeaderTextView.setText("下拉刷新");// 显示松手刷新
			mHeaderPullDownImageView.setVisibility(View.VISIBLE);// 隐藏"下拉刷新"
			mHeaderReleaseDownImageView.setVisibility(View.GONE);// 显示向上的箭头
		}
	}

	private void onScrollOverPull() {
		if (mPullRefreshState != OVER_PULL_REFRESH) {
			mPullRefreshState = OVER_PULL_REFRESH;
			mDownY = mMoveY; // 为下拉1/3折扣效果记录开始位置
			mHeaderTextView.setText("松手刷新");// 显示松手刷新
			mHeaderPullDownImageView.setVisibility(View.GONE);// 隐藏"下拉刷新"
			mHeaderReleaseDownImageView.setVisibility(View.VISIBLE);// 显示向上的箭头
		}
	}

	private boolean isFlinging() {
		return mCurrentScrollState == SCROLL_STATE_FLING;
	}

	private boolean isScrolling() {
		return mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL;
	}

	private boolean isOverPullHeight() {
		return mHeaderLinearLayout.getBottom() >= mHeaderHeight;
	}

	private boolean isEnterPullHeight() {
		return mHeaderLinearLayout.getBottom() >= 0
				&& mHeaderLinearLayout.getBottom() < mHeaderHeight;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(1);
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_BACKING:
				onRefreshBacking();
				break;
			case REFRESH_BACED:
				onRefreshBaced();
			case REFRESH_RETURN:
				onRefreshReturn(msg);
				break;
			case REFRESH_DONE:
				onRefreshDone();
				break;
			default:
				break;
			}
		}

		private void onRefreshBacking() {
			mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
					(int) (mHeaderLinearLayout.getPaddingTop() * 0.75f),
					mHeaderLinearLayout.getPaddingRight(), mHeaderLinearLayout.getPaddingBottom());
		}

		private void onRefreshBaced() {
			/*
			 * mHeaderTextView.setText("正在加载...");
			 * mHeaderProgressBar.setVisibility(View.VISIBLE);
			 * mHeaderPullDownImageView.setVisibility(View.GONE);
			 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
			 * mPullRefreshState = EXIT_PULL_REFRESH;
			 */

			if (refreshListener != null) {
				refreshListener.refreshing();
			}
			/*
			 * Message msg1 = mHandler.obtainMessage(); msg.what = REFRESH_DONE;
			 * mHandler.sendMessage(msg1);
			 */
			/*
			 * new Thread() { public void run() { if (mRefreshListener != null)
			 * { mRefreshObject = mRefreshListener.refreshing(); } Message msg =
			 * mHandler.obtainMessage(); msg.what = REFRESH_DONE;
			 * mHandler.sendMessage(msg); }; }.start(); break;
			 */
		}

		private void onRefreshReturn(Message msg) {
			mHeaderTextView.setText("下拉刷新");
			mHeaderProgressBar.setVisibility(View.INVISIBLE);
			mHeaderPullDownImageView.setVisibility(View.VISIBLE);
			mHeaderReleaseDownImageView.setVisibility(View.GONE);
			mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(), 0,
					mHeaderLinearLayout.getPaddingRight(), mHeaderLinearLayout.getPaddingBottom());
			mPullRefreshState = NONE_PULL_REFRESH;
			setSelectionFromTop(1, msg.arg1);
		}

		private void onRefreshDone() {
			mHeaderTextView.setText("下拉刷新");
			mHeaderProgressBar.setVisibility(View.INVISIBLE);
			mHeaderPullDownImageView.setVisibility(View.VISIBLE);
			mHeaderReleaseDownImageView.setVisibility(View.GONE);
			mHeaderUpdateText.setText(getContext().getString(
					R.string.app_list_header_refresh_last_update,
					mSimpleDateFormat.format(new Date())));
			mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(), 0,
					mHeaderLinearLayout.getPaddingRight(), mHeaderLinearLayout.getPaddingBottom());
			mPullRefreshState = NONE_PULL_REFRESH;
			setSelection(1);
		}
	};

	public interface RefreshListener {
		void refreshing();

		void loadUpdate();

		void showUpdate();

		void waitUpdate();
	}

	public void finishFootView() {
		/*
		 * mFooterProgressBar.setVisibility(View.GONE);
		 * mFooterTextView.setText(R.string.app_list_footer_more);
		 */
	}

	/*
	 * public void addFootView() { if (getFooterViewsCount() == 0) {
	 * addFooterView(mFooterLinearLayout); } }
	 * 
	 * public void removeFootView() { removeFooterView(mFooterLinearLayout); }
	 */
}