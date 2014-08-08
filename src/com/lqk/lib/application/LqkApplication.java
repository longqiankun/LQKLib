package com.lqk.lib.application;

import java.util.Locale;

import android.app.Application;

import com.lqk.framework.app.Ioc;
import com.lqk.framework.db.sqlite.SqliteDBManager;


/**
 * @ClassName: LqkApplication
 * @Description: TODO
 * @author longqiankun
 * @date 2014-7-31 下午2:27:43
 * 
 */

public class LqkApplication extends Application {
public static LqkApplication app;
public String lauguage = "";//语言
public static String EN = "en";//英文
private SqliteDBManager dbManager;
	@Override
	public void onCreate() {
		app = this;
		//
		Ioc.getIoc().init(this);
		dbManager = SqliteDBManager.getInstance(this);
		lauguage = Locale.getDefault().getLanguage();
		new Thread() {
			public void run() {
//				dbManager.open();
			}
		}.start();
	    super.onCreate();
	}
	
	/**
	 * 当终止应用程序对象时调用，不保证一定被调用，当程序是被内核终止以便为其他应用程序释放资源，那
     *么将不会提醒，并且不调用应用程序的对象的onTerminate方法而直接终止进   程
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
	/*	if (dbManager != null) {
			new Thread() {
				public void run() {
					dbManager.close();
				};
			}.start();
		}*/
	}
}
