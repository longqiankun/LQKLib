package com.lqk.lib.ui.tabbar;

import android.content.Intent;
import android.graphics.Bitmap;


/**
 * @ClassName: TabConfig
 * @Description: 标签属性结构
 * @author longqiankun
 * @date 2014-7-31 下午4:17:36
 * 
 */

public class TabConfig {
/**提示标记的资源*/	
public int promptRes=-1;
public Bitmap promptBitamp;
/**提示标记是否需要提示*/
public boolean isPrompt=false;
/**提示标记是否需要闪烁*/
public boolean isFlashing=true;

/**文本的资源*/	
public int textRes=-1;
public String strText;

/**选中的资源*/	
public int selectedRes=-1;
public Bitmap selectedBitamp;

/**没有选中的资源*/	
public int normalRes=-1;
public Bitmap normalBitamp;

/**是否已选中*/
public boolean isSelected=false;

/**标签对应的意图*/
public Intent intent;

/**类名唯一标示*/
public Class className;


	/**
	 * 创建一个新的实例 TabConfig.
	 *
	 * @param textRes
	 * @param selectedRes
	 * @param normalRes
	 * @param intent
	 * @param className
	 */
	
public TabConfig(int textRes, int selectedRes, int normalRes, Intent intent,
		Class className) {
	super();
	this.textRes = textRes;
	this.selectedRes = selectedRes;
	this.normalRes = normalRes;
	this.intent = intent;
	this.className = className;
}


	
		/**
		 * 创建一个新的实例 TabConfig.
		 *
		 * @param strText
		 * @param selectedRes
		 * @param normalRes
		 * @param intent
		 * @param className
		 */
		
	public TabConfig(String strText, int selectedRes, int normalRes,
			Intent intent, Class className) {
		super();
		this.strText = strText;
		this.selectedRes = selectedRes;
		this.normalRes = normalRes;
		this.intent = intent;
		this.className = className;
	}


	
}
