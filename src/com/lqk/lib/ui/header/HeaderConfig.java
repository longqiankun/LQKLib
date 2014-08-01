package com.lqk.lib.ui.header;



/**
 * @ClassName: HeaderConfig
 * @Description: 顶部的配置信息
 * @author longqiankun
 * @date 2014-8-1 下午1:22:36
 * 
 */

public class HeaderConfig {
	public enum BtnClick{
		LEFTBTN,LEFTIMGBTN,LEFT,RIGHTBTN,RIGHTIMGBTN
	}
	/**顶部中间文本资源*/
  public int middleTitleId=-1;
    /**顶部中间文本*/
  public String middleTile;
  
  /**顶部左边资源*/
  public int ib_top_leftbg=-1;
  public int ib_top_leftsrc=-1;
  public boolean isShowIbLeft=false;
  
  public int btn_top_leftbg=-1;
  public int btn_top_leftTextId=-1;
  public boolean isShowbtnLeft=false;
  
  public int top_leftbg=-1;
  
  /**顶部右边资源*/
  public int ib_top_rightbg=-1;
  public int ib_top_rightsrc=-1;
  public boolean isShowIbRight=false;
  
  public int btn_top_rightbg=-1;
  public int btn_top_rightTextId=-1;
  public boolean isShowbtnRight=false;
  
  public int top_bg=-1;
}
