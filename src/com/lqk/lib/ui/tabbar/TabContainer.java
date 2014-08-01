package com.lqk.lib.ui.tabbar;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @ClassName: TabContainer
 * @Description: 标签tab容器
 * @author longqiankun
 * @date 2014-7-31 下午4:14:04
 * 
 */

public class TabContainer extends LinearLayout {

	LinkedList<TabButton> mTabButtons;
	Context mContext;

	public TabContainer(Context context) {
		super(context);
		init(context);
	}

	public TabContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.HORIZONTAL);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		mTabButtons = new LinkedList<TabButton>();
	}

	public void updateView(TabConfig mConfig) {
		Class clazz = mConfig.className;
		for (int i = 0; i < mTabButtons.size(); i++) {
			Class clazz2 = mTabButtons.get(i).mConfig.className;
			if (clazz2.getName().equals(clazz.getName())) {
				mTabButtons.get(i).updateView(mConfig);
				break;
			}
		}
	}

	/**
	 * 
	 * @Title: addTabButton
	 * @Description: 添加标签到容器中
	 * @param @param mConfig
	 * @return void
	 * @throws
	 */
	public void addTabButton(TabConfig mConfig) {
		final TabButton mTabButton = new TabButton(mContext, mConfig);
		mTabButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mTabButtons.size(); i++) {
					TabButton mTabButton = mTabButtons.get(i);
					TabConfig mConfig = mTabButton.getTabConfig();
					mConfig.isSelected = false;
					mTabButton.updateView(mConfig);
				}

				TabConfig mConfig = mTabButton.getTabConfig();
				mConfig.isSelected = true;
				mConfig.isPrompt = false;
				mTabButton.updateView(mConfig);
				if (mItemSelectListener != null) {
					mItemSelectListener.setCurrentTab(mConfig.className,
							mConfig.intent);
				}
			}
		});
		mTabButtons.add(mTabButton);
	}

	/**
	 * 
	 * @Title: commit
	 * @Description: 在布局中添加标签
	 * @param
	 * @return void
	 * @throws
	 */
	public void commit() {
		LinearLayout.LayoutParams miconParams = new LinearLayout.LayoutParams(
				0, LinearLayout.LayoutParams.WRAP_CONTENT);
		miconParams.weight = 1;
		for (int i = 0; i < mTabButtons.size(); i++) {
			TabButton mTabButton = mTabButtons.get(i);
			addView(mTabButton, miconParams);
		}
		this.postInvalidate();
	}

	private OnTabItemSelectListener mItemSelectListener;

	public void setOnTabItemSelectListener(
			OnTabItemSelectListener mItemSelectListener) {
		this.mItemSelectListener = mItemSelectListener;
	}

	public interface OnTabItemSelectListener {
		void setCurrentTab(Class className, Intent intent);
	}
}
