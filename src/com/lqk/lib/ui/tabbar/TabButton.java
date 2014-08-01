package com.lqk.lib.ui.tabbar;

import com.lqk.lib.R;
import com.lqk.lib.utils.AnimUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @ClassName: TabButton
 * @Description: 标签,该标签由图标、文本、标记组成
 * @author longqiankun
 * @date 2014-7-31 下午4:11:35
 * 
 */

public class TabButton extends FrameLayout {

	Context mContext;
	// 标签配置属性
	TabConfig mConfig;
	// 图标
	ImageView iv_icon;
	// 文本
	TextView tv_text;
	// 提示标记
	ImageView iv_promptIcon;
	// 标签对应的意图
	Intent intent;

	public TabButton(Context context) {
		super(context);
		init(context, null);
	}

	public TabButton(Context context, TabConfig mConfig) {
		super(context);
		init(context, mConfig);
	}

	public TabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, null);
	}

	public Intent getIntent() {
		return intent;
	}

	public TabConfig getTabConfig() {
		return mConfig;
	}

	/**
	 * @Title: init
	 * @Description: 初始化
	 * @param
	 * @return void
	 * @throws
	 */

	private void init(Context context, TabConfig mConfig) {
		this.mContext = context;
		this.mConfig = mConfig;
		setPadding(8, 8, 8, 8);

		LinearLayout mLinearLayout = getLinearLayout();
		FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		mLayoutParams.gravity = Gravity.RIGHT;
		FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		mParams.gravity = Gravity.CENTER;
		iv_promptIcon = new ImageView(context);

		addView(mLinearLayout, mParams);
		addView(iv_promptIcon, mLayoutParams);
		showData();
	}

	/**
	 * @Title: getLinearLayout
	 * @Description: 布局图标和文本
	 * @param @return
	 * @return LinearLayout
	 * @throws
	 */

	private LinearLayout getLinearLayout() {
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams miconParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		miconParams.weight = 1;
		miconParams.bottomMargin = 8;
		miconParams.gravity = Gravity.CENTER;
		iv_icon = new ImageView(mContext);

		LinearLayout.LayoutParams mtextParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		mtextParams.gravity = Gravity.CENTER_HORIZONTAL;
		tv_text = new TextView(mContext);
		tv_text.setTextSize(13);
		tv_text.setTextColor(Color.parseColor("#ffffff"));

		layout.addView(iv_icon, miconParams);
		layout.addView(tv_text, mtextParams);

		return layout;
	}

	public void updateView(TabConfig mConfig) {
		this.mConfig = mConfig;
		showData();
	}

	public void showData() {
		if (mConfig != null) {
			// 显示文本
			if (mConfig.textRes > 0) {
				tv_text.setText(mConfig.textRes);
			} else if (!TextUtils.isEmpty(mConfig.strText)) {
				tv_text.setText(mConfig.strText);
			}

			// 显示提示
			if (mConfig.isPrompt) {
				iv_promptIcon.setVisibility(View.VISIBLE);

				if (mConfig.promptRes > 0) {
					iv_promptIcon.setImageResource(mConfig.promptRes);
				} else if (mConfig.promptBitamp != null) {
					iv_promptIcon.setImageBitmap(mConfig.promptBitamp);
				}

				if (mConfig.isFlashing) {
					AnimUtils.startShanShuoAnim(iv_promptIcon);
				} else {
					AnimUtils.stopAnim(iv_promptIcon);
				}
			} else {
				iv_promptIcon.setVisibility(View.GONE);
				AnimUtils.stopAnim(iv_promptIcon);
			}

			// 显示图标

			if (mConfig.isSelected) {
				if (mConfig.selectedRes > 0) {
					iv_icon.setImageResource(mConfig.selectedRes);
				} else if (mConfig.selectedBitamp != null) {
					iv_icon.setImageBitmap(mConfig.selectedBitamp);
				}
			} else {
				if (mConfig.normalRes > 0) {
					iv_icon.setImageResource(mConfig.normalRes);
				} else if (mConfig.normalBitamp != null) {
					iv_icon.setImageBitmap(mConfig.normalBitamp);
				}
			}

		}

	}
}
