package com.lqk.lib.ui.tabbar;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.lqk.framework.download.UploadDownloadManager;
import com.lqk.framework.inject.InjectInit;
import com.lqk.framework.inject.InjectLayer;
import com.lqk.framework.inject.InjectView;
import com.lqk.lib.R;
import com.lqk.lib.ui.tabbar.TabContainer.OnTabItemSelectListener;

public class TabBarActivity extends ActivityGroup implements OnTabItemSelectListener
{
	private LocalActivityManager mActivityManager;
	// 当前类的实例
	public static TabBarActivity intance;
	// 菜单中的类集合
	private LinkedList<Class> mStartedActivityList;
	private HashMap<Class, Integer> tabSigns;
	
	private LinkedList<TabConfig> mTabConfigs;
	
	LinearLayout fl_body;//主容器
	private TabContainer tc_tabBar;// 子页面的容器
	Class curClass;//当前tab的类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_tabbar);
		fl_body=(LinearLayout) findViewById(R.id.fl_body);
		tc_tabBar=(TabContainer) findViewById(R.id.tc_tabBar);
		tc_tabBar.setOnTabItemSelectListener(this);
		mActivityManager=getLocalActivityManager();
		mStartedActivityList = new LinkedList<Class>();
		tabSigns=new HashMap<Class, Integer>();
		mTabConfigs=new LinkedList<TabConfig>();
	}
	public void addTabButton(TabConfig mConfig){
		mTabConfigs.add(mConfig);
		tc_tabBar.addTabButton(mConfig);
	}
	public void commit(){
		tc_tabBar.commit();
	}
	public void setBottomBg(int resid){
		tc_tabBar.setBackgroundResource(resid);
	}
	public void updateView(TabConfig mConfig){
		tc_tabBar.updateView(mConfig);
	}
	/**
	 * 启动指定的activity
	 * 
	 * @param activityCalss
	 * @param intent
	 */
	public void updateBodyView(Class activityCalss, Intent intent) {
		curClass=activityCalss;
		Window window = mActivityManager.startActivity(activityCalss.getName(),intent);
		fl_body.removeAllViews();
		View view=window.getDecorView();
		if (activityCalss == null) {
			mStartedActivityList.clear();
		} else {
			mStartedActivityList.remove(activityCalss);
			mStartedActivityList.add(activityCalss);
			
			if(intent.hasExtra(TabConfig.TABSIGNKEY)){
				tabSigns.put(activityCalss, intent.getIntExtra(TabConfig.TABSIGNKEY, 0));
			}
		}
		fl_body.addView(view);
		updateBottomStatus(intent);
	}

	
		/**
	* @Title: updateBottomStatus
	* @Description:更新底部状态
	* @param 
	* @return void
	* @throws
	*/
		
	private void updateBottomStatus(Intent intent) {
		if(intent!=null){
			if(intent.hasExtra(TabConfig.TABSIGNKEY)){
				int tabSign=intent.getIntExtra(TabConfig.TABSIGNKEY, 0);
		for (int i = 0; i < mTabConfigs.size(); i++) {
			TabConfig mConfig=mTabConfigs.get(i);
			if(mConfig.tabSign==tabSign){
				mConfig.isSelected=true;
				updateView(mConfig);
				break;
			}
		}
			}
		}
		
	}
	/**
	 * 根据指定的标识消除activity
	 * @param id
	 * @return
	 */
	boolean destroyActivityById(String id) {
		final LocalActivityManager activityManager = this.mActivityManager;
		if (activityManager != null) {
			activityManager.destroyActivity(id, false);
			try {
				final Field mActivitiesField = LocalActivityManager.class
						.getDeclaredField("mActivities");
				if (mActivitiesField != null) {
					mActivitiesField.setAccessible(true);
					@SuppressWarnings("unchecked")
					final Map<String, Object> mActivities = (Map<String, Object>) mActivitiesField
							.get(activityManager);
					if (mActivities != null) {
						mActivities.remove(id);
					}
					final Field mActivityArrayField = LocalActivityManager.class
							.getDeclaredField("mActivityArray");
					if (mActivityArrayField != null) {
						mActivityArrayField.setAccessible(true);
						@SuppressWarnings("unchecked")
						final ArrayList<Object> mActivityArray = (ArrayList<Object>) mActivityArrayField
								.get(activityManager);
						if (mActivityArray != null) {
							for (Object record : mActivityArray) {
								final Field idField = record.getClass()
										.getDeclaredField("id");
								if (idField != null) {
									idField.setAccessible(true);
									final String _id = (String) idField
											.get(record);
									if (id.equals(_id)) {
										mActivityArray.remove(record);
										break;
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	  @Override 
	  public boolean dispatchKeyEvent(KeyEvent event) {
		  if(event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() ==
	  KeyEvent.KEYCODE_BACK) { 
			  onMyGoBack(); // return true; }else
		  }
		  else if(event.getAction() == KeyEvent.ACTION_UP &&event.getKeyCode() ==
	  KeyEvent.KEYCODE_HOME){
		  
	  } return false; }
	  /**
		 * 返回处理
		 */
	  long firstTime = 0;
		public void onMyGoBack() {
			int size = mStartedActivityList.size();
			if (size == 0) {
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
					Toast.makeText(TabBarActivity.this, "再按一次退出程序...",
							Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
					// return true;
				} else {
					finish();// 否则退出程序
				}
			} else if (mTabConfigs.size()>0 && isTabClass() ) {
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
					Toast.makeText(TabBarActivity.this, "再按一次退出程序...",
							Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
				} else {
					finish();// 否则退出程序
				}
			} else {
				removeStartedActivity(mStartedActivityList.getLast());
				size--;
				if (size == 0) {
				} else {
					/*updateBodyView(mStartedActivityList.getLast(), new Intent(
							this, mStartedActivityList.getLast()));*/
					if(!tabSigns.containsKey(mStartedActivityList.getLast())){
						updateBodyView(mStartedActivityList.getLast(), new Intent(
								this, mStartedActivityList.getLast()));
					}else{
					Intent intnet= new Intent(this, mStartedActivityList.getLast());
					intnet.putExtra(TabConfig.TABSIGNKEY, tabSigns.get(mStartedActivityList.getLast()));
					updateBodyView(mStartedActivityList.getLast(),intnet);
					}
					
				}
			}
		}
		/**
		 * 
		* @Title: isTabClass
		* @Description: 检查当前类是否是标签对应类
		* @param @return
		* @return boolean
		* @throws
		 */
		private boolean isTabClass(){
			if(curClass!=null){
			for (int i = 0; i < mTabConfigs.size(); i++) {
			TabConfig mConfig=	mTabConfigs.get(i);
			if(mConfig.className.equals(curClass)){
				return true;
			}
			}
			}
			return false;
		}
	public void removeStartedActivity(Class activityClass) {
		mStartedActivityList.remove(activityClass);
		destroyActivityById(activityClass.getName());
	}

	@Override
	public void setCurrentTab(Class className,Intent intent) {
		updateBodyView(className, intent);
	}
}
