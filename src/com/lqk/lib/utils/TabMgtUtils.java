package com.lqk.lib.utils;

import java.util.Stack;

import android.app.Activity;
/**
 * 
* @ClassName: ActivityManagerUtils
* @Description: activity的管理
* @author longqiankun
* @date 2014-7-7 上午10:32:50
*
 */
public class TabMgtUtils { 
  
    /** 
     * 视图管理器，用于完全退出 
     *  
     * @return 
     */
    public static TabMgtUtils getScreenManager() { 
        if (instance == null) { 
            instance = new TabMgtUtils(); 
        } 
        return instance; 
    } 
  
    /** 
     * 回收堆栈中指定的activity 
     *  
     * @param activity 
     */
    public void popActivity(Activity activity) { 
        if (activity != null) { 
            activity.finish(); 
            activityStack.remove(activity); 
            activity = null; 
        } 
    } 
  
    /** 
     * 获取堆栈的栈顶activity 
     *  
     * @return 栈顶activity 
     */
    public Activity currentActivity() { 
        Activity activity = null; 
        try { 
            if (!activityStack.isEmpty()) { 
                activity = activityStack.pop(); 
            } 
            return activity; 
        } catch (Exception ex) { 
            return activity; 
        } finally { 
            activity = null; 
        } 
    } 
   /**
    * 
   * @Title: previousActivity
   * @Description: 获取管理器中的上一个activity
   * @return Activity
   * @throws
    */
    public Activity previousActivity() { 
    	
        Activity activity = null; 
        try { 
            if (!activityStack.isEmpty()) { 
////                activity = activityStack.pop(); 
            	activity = activityStack.get(activityStack.size()-1);
            } 
            return activity; 
        } catch (Exception ex) { 
            return activity; 
        } finally { 
            activity = null; 
        } 
    } 
    /** 
     * 将activity压入堆栈 
     *  
     * @param activity 
     *            需要压入堆栈的activity 
     */
    public void pushActivity(Activity activity) { 
        if (activityStack == null) { 
            activityStack = new Stack<Activity>(); 
        } 
        activityStack.push(activity); 
    } 
    /** 
     * 回收堆栈中所有Activity 
     */
    public void popAllActivity() { 
        Activity activity = null; 
        try { 
            while (!activityStack.isEmpty()) { 
                activity = currentActivity(); 
                if (activity != null) { 
                    popActivity(activity); 
                } 
            } 
        } finally { 
            activity = null; 
        } 
    } 
    private TabMgtUtils() { 
    } 
    private static Stack<Activity> activityStack; 
    private static TabMgtUtils instance; 
} 
