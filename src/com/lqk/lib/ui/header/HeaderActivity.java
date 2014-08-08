package com.lqk.lib.ui.header;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lqk.framework.inject.InjectLayer;
import com.lqk.framework.inject.InjectPLayer;
import com.lqk.framework.util.Handler_Inject;
import com.lqk.lib.R;
import com.lqk.lib.ui.header.HeaderConfig.BtnClick;
import com.lqk.lib.ui.tabbar.TabBarActivity;
import com.lqk.lib.utils.TabMgtUtils;

/**
 * Activity基类
 * @author longqiankun
 *2014-06-29
 */
public abstract class HeaderActivity extends Activity {
	private FrameLayout pageContent; // 存放子view的容器

	private RelativeLayout rl_top_bg; 
	private LinearLayout ll_left;
	
	private Button btn_top_left; // 顶部左边的按钮
	private ImageButton ib_top_left; // 顶部左边的按钮图标
	
	private TextView tv_top_middle;// 顶部文本
	
	private Button btn_top_right;// 顶部右边的按钮
	private ImageButton ib_top_right;// 顶部右边图标
	
	Bundle savedInstanceState;
	
	private HeaderConfig config;// 封装顶部信息的类
	private TabBarActivity mParent;
	Resources res;
/**
 * activity第一次被创建时调用
 */
	boolean isShowTop=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.header);
		initView();
		res=getResources();
		setViewStatus();
		if(getParent() instanceof TabBarActivity){
		mParent=(TabBarActivity) getParent();
		}*/
	}
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		rl_top_bg = (RelativeLayout) findViewById(R.id.rl_top_bg);
		btn_top_left = (Button) findViewById(R.id.btn_top_left);
		ib_top_left = (ImageButton) findViewById(R.id.ib_top_left);
		tv_top_middle = (TextView) findViewById(R.id.tv_top_middle);
		btn_top_right = (Button) findViewById(R.id.btn_top_right);
		ib_top_right = (ImageButton) findViewById(R.id.ib_top_right);
		pageContent=(FrameLayout) findViewById(R.id.page_content);
		ll_left=(LinearLayout) findViewById(R.id.ll_left);
		
	}
	private void initView(View view) {
		rl_top_bg = (RelativeLayout)view. findViewById(R.id.rl_top_bg);
		btn_top_left = (Button)view.  findViewById(R.id.btn_top_left);
		ib_top_left = (ImageButton)view.  findViewById(R.id.ib_top_left);
		tv_top_middle = (TextView)view.  findViewById(R.id.tv_top_middle);
		btn_top_right = (Button)view.  findViewById(R.id.btn_top_right);
		ib_top_right = (ImageButton)view.  findViewById(R.id.ib_top_right);
		pageContent=(FrameLayout)view. findViewById(R.id.page_content);
		ll_left=(LinearLayout) view. findViewById(R.id.ll_left);
		
	}
	/**
	 * 设置顶部样式
	 */
	protected void setMiddleTitle(String title) {
		tv_top_middle.setText(title);
		config.middleTile = title;
	}
	/**
	 * 设置左边文本
	 * @param title
	 */
	protected void setleftTitle(int leftBtnTextId) {
		btn_top_left.setText(res.getString(leftBtnTextId));
		config.btn_top_leftTextId = leftBtnTextId;
	}
	/**
	 * 设置右边文本
	 * @param title
	 */
	protected void setRightTitle(int rightBtnTextId) {
		btn_top_right.setText(res.getString(rightBtnTextId));
		config.btn_top_rightTextId = rightBtnTextId;
	}
	
	/**
	 * 设置顶部初始化状态
	 */
	private void setViewStatus() {
		config = getHeaderConfig();
		if (config == null) {
			return;
		} else {
			//顶部背景
			if(config.top_bg>0){
				rl_top_bg.setBackgroundResource(config.top_bg);
			}
			
			//顶部左边背景
			if(config.top_leftbg>0){
				ll_left.setBackgroundResource(config.top_leftbg);
			}
			if(config.isImpLeft){
				ll_left.setOnClickListener(topClickListener);
			}
			
			//中间文本
			if (!TextUtils.isEmpty(config.middleTile)) {
				tv_top_middle.setText(config.middleTile);
			} else if (config.middleTitleId > 0) {
				tv_top_middle.setText(config.middleTitleId);
			}
			
			//左边按钮
			if (config.isShowIbLeft) {
				ib_top_left.setVisibility(View.VISIBLE);
				if (config.ib_top_leftbg > 0) {
					 ib_top_left.setBackgroundResource(config.ib_top_leftbg);
				}
				
				if (config.ib_top_leftsrc>0){
					 ib_top_left.setImageResource(config.ib_top_leftsrc);
				}
				if(config.isImpIbLeft){
				ib_top_left.setOnClickListener(topClickListener);
				}
			}
			
			//左边图标按钮
			if (config.isShowbtnLeft) {
				btn_top_left.setVisibility(View.VISIBLE);
				if (config.btn_top_leftbg > 0) {
					btn_top_left.setBackgroundResource(config.btn_top_leftbg);
				}
				
				if (config.btn_top_leftTextId>0){
					btn_top_left.setText(config.btn_top_leftTextId);
				}
				if(config.isImpbtnLeft){
				btn_top_left.setOnClickListener(topClickListener);
				}
			}
			
			
			//右边按钮
			
			if (config.isShowIbRight) {
				ib_top_right.setVisibility(View.VISIBLE);
				if (config.ib_top_rightbg > 0) {
					 ib_top_left.setBackgroundResource(config.ib_top_rightbg);
				}
				
				if (config.ib_top_rightsrc>0){
					 ib_top_left.setImageResource(config.ib_top_rightsrc);
				}
				if(config.isImpIbRight){
				ib_top_right.setOnClickListener(topClickListener);
				}
			}
			
			//右边图标按钮
			if (config.isShowbtnRight) {
				btn_top_right.setVisibility(View.VISIBLE);
				if (config.btn_top_rightbg > 0) {
					btn_top_left.setBackgroundResource(config.btn_top_rightbg);
				}
				
				if (config.btn_top_rightTextId>0){
					btn_top_right.setText(config.btn_top_rightTextId);
				}
				if(config.isImpbtnRight){
				btn_top_right.setOnClickListener(topClickListener);
				}
			}
	
		}
	}
	
	/**
	 * 返回到上一个activity
	 */
	protected void backPreviousActivity() {
		if (mParent != null) {
			if(TabMgtUtils.getScreenManager().currentActivity()!=null){
			Class currentActivity = TabMgtUtils.getScreenManager().getClass();
			mParent.removeStartedActivity(currentActivity);
			Activity activity = TabMgtUtils.getScreenManager()
					.previousActivity();
			if (activity != null) {
				Class previosActivity = activity.getClass();
				mParent.updateBodyView(previosActivity, new Intent(
						HeaderActivity.this, previosActivity));
			}
		} else {
			finish();
		}
		}
	}

	/**
	 * 事件处理
	 */
	private View.OnClickListener topClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int id=v.getId();
			if(id==R.id.ib_top_left){
				btnAction(BtnClick.LEFTIMGBTN);
			}else if(id==R.id.ib_top_right){
				btnAction(BtnClick.RIGHTIMGBTN);
			}else if(id==R.id.btn_top_left){
				btnAction(BtnClick.LEFTBTN);
			}else if(id==R.id.btn_top_right){
				btnAction(BtnClick.RIGHTBTN);
			}else if(id==R.id.ll_left){
				btnAction(BtnClick.LEFT);
			}
		}
	};
	/**
	 * 添加子view到当前容器中
	 * @param viewResId
	 * @return
	 */
	protected View addContentView(int viewResId) {
	    View view = getLayoutInflater().inflate(viewResId, null);
	    pageContent.addView(view, 0);
		initData();// 初始化数据
		setListener();// 设置监听
		return view;
	}
	
	/**
	 * 添加子view到当前容器中
	 * 
	 * @param viewResId
	 * @return
	 */
	protected View addContentView(View  view) {
		initView(view);
		setViewStatus();
		res=getResources();
		if(getParent() instanceof TabBarActivity){
		mParent=(TabBarActivity) getParent();
		}
//	    pageContent.addView(view, 0);
		initData();// 初始化数据
		setListener();// 设置监听
		return view;
	}
	protected void initHeader(View view){
		if(view!=null){
		initView(view);
		setViewStatus();
		}
		res=getResources();
		if(getParent() instanceof TabBarActivity){
		mParent=(TabBarActivity) getParent();
		}
		initData();// 初始化数据
		setListener();// 设置监听
	}
	/**
	 * 获取顶部封装信息
	 * 
	 * @return
	 */
	protected abstract HeaderConfig getHeaderConfig();

	/**
	 * 实现左边的事件
	 */
	protected abstract void btnAction(BtnClick btnClick);
	/**
	 * 设置监听
	 */
	public abstract void setListener();

	/**
	 * 设置初始化数据
	 */
	public abstract void initData();
	
}
