package com.cxh.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cxh.library.R;
import com.cxh.library.manager.ActivityManager;
import com.cxh.library.utils.UIUtil;
import com.cxh.library.widget.LoadingPage;
import com.cxh.library.widget.LoadingPage.*;

import java.util.List;

public abstract class BaseActivity extends Activity {
	public ActivityManager activityManager;

	protected LoadingPage mContentView;
	protected ImageButton imgBtn_back;
	protected ImageButton imgBtn_setting;
	protected TextView tv_title;
	protected View mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityManager = ActivityManager.getInstance();
		activityManager.pushOneActivity(this);

		mContentView = new LoadingPage(UIUtil.getContext()) {
			@Override
			public LoadResult load() {
				return BaseActivity.this.load();
			}

			@Override
			public View createLoadedView() {
				return BaseActivity.this.createLoadedView();
			}
		};

		mActionBar = View.inflate(this, R.layout.include_actionbar, null);
		imgBtn_back = (ImageButton) mActionBar.findViewById(R.id.imgBtn_back);
		imgBtn_setting = (ImageButton) mActionBar.findViewById(R.id.imgBtn_setting);
		tv_title = (TextView) mActionBar.findViewById(R.id.tv_title);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mContentView.setLayoutParams(layoutParams);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(mActionBar);
		linearLayout.addView(mContentView);

		setActionBar();
		setContentView(linearLayout);

		show();
	}
	
	/** 当显示的时候，加载该页面 */
	public void show() {
		if (mContentView != null) {
			mContentView.show();
		}
	}

	public LoadResult check(Object obj) {
		if (obj == null) {
			return LoadResult.ERROR;
		}
		if (obj instanceof List) {
			List list = (List) obj;
			if (list.size() == 0) {
				return LoadResult.EMPTY;
			}
		}
		return LoadResult.SUCCEED;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity&从栈中移除该Activity
		activityManager.popOneActivity(this);
	}

	/** 加载数据 */
	protected abstract LoadResult load();

	/** 加载完成的View */
	protected abstract View createLoadedView();

	/** 导航栏设置 */
	protected abstract void setActionBar();

}
