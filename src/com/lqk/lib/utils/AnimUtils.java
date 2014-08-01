package com.lqk.lib.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.lqk.lib.R;

/** 
 * @Company: Dilitech
 * @author longqiankun
 * @email qiankun.long@dilitech.com
 * @Title: TranslationAnimUtils.java
 * @Description:
 * @version 1.0  
 * @created 2013-12-12 下午3:18:16 
 */

public class AnimUtils {
	/**
	 * 
	* @Title: showleftMenu
	* @Description: 从左边显示动画
	* @param context 上下文
	* @param  v  动画显示的控件
	* @return void
	* @throws
	 */
	public static  void showleftMenu(Context context,View v) {
		Animation anim_show;
		anim_show = AnimationUtils.loadAnimation(context,R.anim.enter2_alpha);
		anim_show.setFillAfter(true);
		v.startAnimation(anim_show);
		v.setVisibility(View.VISIBLE);
	}
	/**
	 * 
	* @Title: dismissleftMenu
	* @Description: 从左边消失的动画
	* @param context 上下文
	* @param v 动画的控件
	* @return void
	* @throws
	 */
	public static void dismissleftMenu(Context context,final View v) {
		Animation anim_dismiss;
		anim_dismiss =AnimationUtils.loadAnimation(context,R.anim.out_alpha);
		anim_dismiss.setFillAfter(true);
		v.startAnimation(anim_dismiss);
		v.setVisibility(View.INVISIBLE);
	}
	/**
	 * 
	* @Title: showrightMenu
	* @Description: 从右边显示的动画
	* @param context 上下文
	* @param v 动画显示的控件
	* @return void
	* @throws
	 */
	public static  void showrightMenu(Context context,View v) {
		Animation anim_show;
		anim_show = AnimationUtils.loadAnimation(context,R.anim.enter_alpha);
		anim_show.setFillAfter(true);
		v.startAnimation(anim_show);
		v.setVisibility(View.VISIBLE);
	}
	/**
	 * 
	* @Title: dismissrightMenu
	* @Description: 右边消失的动画
	* @param context 上下文
	* @param v 动画显示控件
	* @return void
	* @throws
	 */
	public static  void dismissrightMenu(Context context,final View v) {
		Animation anim_dismiss;
		anim_dismiss =AnimationUtils.loadAnimation(context,R.anim.out2_alpha);
		anim_dismiss.setFillAfter(true);
		v.startAnimation(anim_dismiss);
		v.setVisibility(View.GONE);
	
	}
	
	/**
	 * 代码实现旋转的菊花效果
	 * 
	 * @param imageView
	 *            需要旋转的图片
	 * @param drawable
	 *            旋转菊花
	 * @return void
	 */
	public static void startChrysanthemumAnim(View imageView) {
		startChrysanthemumAnim(imageView,-1);
	}
	/**
	 * 代码实现旋转的菊花效果
	 * 
	 * @param imageView
	 *            需要旋转的图片
	 * @param drawable
	 *            旋转菊花
	 * @return void
	 */
	public static void startChrysanthemumAnim(View view, int drawable) {
		try {
			if(view instanceof ImageView){
				((ImageView)view).setScaleType(ImageView.ScaleType.CENTER);
			if(drawable>0){
				((ImageView)view).setImageResource(drawable);
			}
			}
			AnimationSet animationSet = new AnimationSet(false);
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnimation.setDuration(2000);
			rotateAnimation.setInterpolator(new LinearInterpolator());
			rotateAnimation.setRepeatMode(Animation.RESTART);
			rotateAnimation.setRepeatCount(Animation.INFINITE);
			animationSet.addAnimation(rotateAnimation);
			view.setAnimation(animationSet);
		} catch (Exception e) {

		}
	}
	/**
	 * 启动闪烁动画
	 */
	public static void startShanShuoAnim(View imageView) {
		startShanShuoAnim(imageView,-1);
	}
	/**
	 * 启动闪烁动画
	 */
	public static void startShanShuoAnim(View view, int drawable) {
		if(view instanceof ImageView){
			((ImageView)view).setScaleType(ImageView.ScaleType.CENTER);
		if(drawable>0){
			((ImageView)view).setImageResource(drawable);
		}
		}
		AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation1.setDuration(1000);
		alphaAnimation1.setRepeatCount(Animation.INFINITE);
		alphaAnimation1.setRepeatMode(Animation.REVERSE);
		view.setAnimation(alphaAnimation1);
		alphaAnimation1.start();
	}
	/**
	 * 停止自定义菊花的旋转
	 * 
	 * @param imageView
	 * @return void
	 */
	public static void stopAnim(View view) {
		try {
			view.clearAnimation();
		} catch (Exception e) {
		}
	}
}
