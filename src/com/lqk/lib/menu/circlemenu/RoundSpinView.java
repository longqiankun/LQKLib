package com.lqk.lib.menu.circlemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lqk.lib.R;

/**
 * 圆盘式的view
 * @author chroya
 *
 */
public class RoundSpinView extends View {
	private Paint mPaint = new Paint();

	private BigStone[] mStones;
	private static  int STONE_COUNT = 21;
	private int mPointX=0, mPointY=0;
	private int mRadius = 0;
	private int mDegreeDelta;
	private boolean isAllowed = false;
	private boolean isMove = false;
	private BigStone currentbstone = null;
	
	private MenuItem[] menuItems;

	public RoundSpinView(Context context, int px, int py, int radius,int menubg,MenuItem[] menuItems) {
		super(context);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(2);
		setBackgroundResource(menubg);
		this.menuItems=menuItems;
		STONE_COUNT=menuItems.length;
		mPointX = px;
		mPointY = py;
		mRadius = radius;

		setupStones();
		computeCoordinates();
	}

	/**
	 * 初始化
	 */
	private void setupStones() {

		mStones = new BigStone[STONE_COUNT];
		BigStone stone;
		int angle = 0;
		mDegreeDelta = 360/STONE_COUNT;

		for(int index=0; index<STONE_COUNT; index++) {
			stone = new BigStone();
			MenuItem menuItem=menuItems[index];
			stone.index = index;
			stone.content = menuItem.content;
			stone.angle = angle;
			stone.bitmap = drawNumAtBitmap(BitmapFactory.decodeResource(getResources(), menuItem.resource),index);
			//增加点击事件
			stone.action = new ActionEvent(){
				@Override
				public void action(BigStone stone) {
					Toast.makeText(RoundSpinView.this.getContext(), "你点击了我 ! " + stone.content, Toast.LENGTH_SHORT).show();
				}
			};
			angle += mDegreeDelta;
			mStones[index] = stone;
		}
	}

	/**
	 * 重新计算每一个元素的坐标位置
	 * @param x
	 * @param y
	 */
	private void resetStonesAngle(float x, float y) {
		int angle = computeCurrentAngle(x, y);
		for(int index=0; index<STONE_COUNT; index++) {
			//从下标为0的开始
			for(BigStone bs : mStones){
				if(bs.index == index){
					bs.angle = angle;
					break;
				}
			}
			angle += mDegreeDelta;
		}
	}

	private void computeCoordinates() {
		for(BigStone stone : mStones) {
			stone.x = mPointX+ (float)(mRadius * Math.cos(stone.angle*Math.PI/180));
			stone.y = mPointY+ (float)(mRadius * Math.sin(stone.angle*Math.PI/180));
		}
	}

	/**
	 * 计算手指划过的线性距离
	 * @param x
	 * @param y
	 * @return
	 */
	private int computeCurrentAngle(float x, float y) {		
		float distance = (float)Math.sqrt(((x-mPointX)*(x-mPointX) + (y-mPointY)*(y-mPointY)));
		int degree = (int)(Math.acos((x-mPointX)/distance)*180/Math.PI);
		if(y < mPointY) {
			degree = -degree;
		}
		return degree;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			isMove = false;
			int index = 0;
			float min = 320;
			//求出点击坐标X值与那么元素的坐标位置X值最为接近
			for(BigStone bs : mStones){
				if(bs.x >= 0 && bs.x <= 320 && min >= Math.max(bs.x, event.getX()) - Math.min(bs.x, event.getX())){
					min = Math.max(bs.x, event.getX()) - Math.min(bs.x, event.getX());
					index = bs.index;
				}
			}
			//或者该元素内容
			for(BigStone bs : mStones){
				if(bs.index == index){
					currentbstone = bs;
					break;
				}
			}
			//判断Y值是否也为最接近(可根据自己情况是否增加这段逻辑)
			if(currentbstone.y >= 0 && currentbstone.y <= 430 && 80 >= Math.max(currentbstone.y, event.getY()) - Math.min(currentbstone.y, event.getY())){
				isAllowed = true;
				if(index > 0){
					for(BigStone bs : mStones){
						if(bs.index >= index){
							bs.index = bs.index - index;
						}else{
							bs.index += STONE_COUNT - index;
						}
					}
				}
			}else{
				isAllowed = false;
			}
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE && isAllowed){
			isMove = true;
			resetStonesAngle(event.getX(), event.getY());
			computeCoordinates();
			invalidate();
		}
		if(event.getAction() == MotionEvent.ACTION_UP && isAllowed){
			if(!isMove)
				currentbstone.action.action(currentbstone);
		}
		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawPoint(mPointX, mPointY, mPaint);
		for(int index=0; index<STONE_COUNT; index++) {
			for(BigStone bs : mStones){
				if(bs.index == index){
					drawInCenter(canvas, bs.bitmap, bs.x, bs.y);
//					canvas.drawLine(mPointX, mPointY, bs.x, bs.y, mPaint);
					break;
				}
			}
		}
	}

	/**
	 * 把中心点放到中心处
	 * @param canvas
	 * @param bitmap
	 * @param left
	 * @param top
	 */
	void drawInCenter(Canvas canvas, Bitmap bitmap, float left, float top) {
		canvas.drawPoint(left, top, mPaint);
		canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, top-bitmap.getHeight()/2, null);
	}

	interface ActionEvent{
		public void action(BigStone stone);
	}

	class BigStone {

		int index;
		//图片
		Bitmap bitmap;

		//角度
		int angle;

		//x坐标
		float x;

		//y坐标
		float y;

		String content;

		ActionEvent action;
	}
	
	public static class MenuItem{
		public int resource;
		public String content;
		public MenuItem() {
			super();
			// TODO Auto-generated constructor stub
		}
		public MenuItem(int resource, String content) {
			super();
			this.resource = resource;
			this.content = content;
		}
		
	}

	public static Bitmap drawNumAtBitmap(Bitmap bitmap, int num) {
		int x = bitmap.getWidth();
		int y = bitmap.getHeight();
		Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newbit);
		Paint paint = new Paint();
		canvas.drawBitmap(bitmap, 0, 0, paint);
		paint.setColor(Color.RED);
		paint.setTextSize(20);
		if (num >= 10) {
			canvas.drawText(num + "", 9, 25, paint);
		} else {
			canvas.drawText(num + "", 15, 25, paint);
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		bitmap.recycle();
		return newbit;
	}
}
