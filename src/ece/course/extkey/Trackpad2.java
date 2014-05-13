package ece.course.extkey;

import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Trackpad2 extends SurfaceView{
	final int MAX_TOUCH = 2;
	final String UP = "U";
	final String DOWN = "D";
	final String LEFT = "L";
	final String RIGHT = "R";
	final String S_LEFT = "N";
	final String S_RIGHT = "M";
	final String S_END = "P";
	final String END = "O";
	final int X = 0;
	final int Y = 1;
	final float X_THRESH = 30;
	final float Y_THRESH = 50;
	final int DELAY = 250;
	OutputStream mOutputStream;
	boolean isEnabled;
	String mMessage;
	float[] tmpX;
	float[] tmpY;
	float[] prevX;
	float[] prevY;
	float[] dX;
	float[] dY;
	Paint paint;
	Handler delay;
	
	public Trackpad2(Context context) {
		super(context);
		isEnabled = true;
		delay = new Handler();
		tmpX = new float[MAX_TOUCH];
		tmpY = new float[MAX_TOUCH];
		prevX = new float[MAX_TOUCH];
		prevY = new float[MAX_TOUCH];
		dX = new float[MAX_TOUCH];
		dY = new float[MAX_TOUCH];
		for (int i=0; i<MAX_TOUCH; i++){
			dX[i] = 0;
			dY[i] = 0;
		}
		mOutputStream = ConnectActivity.mOutputStream;
		setWillNotDraw(false);
	}
	
	public void onDraw(Canvas canvas){
		if (canvas == null)
			return;
		canvas.drawColor(Color.LTGRAY);
		paint = new Paint();
		canvas.drawPaint(paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(48);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText("TrackPad", canvas.getWidth()*1/2, canvas.getHeight()*1/2, paint);
	}
	
	public boolean onTouchEvent(MotionEvent motionEvent){
		if (motionEvent.getPointerCount() == 1 && isEnabled){
			int action = motionEvent.getAction();
			switch (action){
			case MotionEvent.ACTION_DOWN : 
				prevX[0] = motionEvent.getX();
				prevY[0] = motionEvent.getY();
				break;
				
			case MotionEvent.ACTION_MOVE :
				tmpX[0] = motionEvent.getX();
				tmpY[0] = motionEvent.getY();
				dX[0] = dX[0] + (tmpX[0] - prevX[0]);
				dY[0] = dY[0] + (tmpY[0] - prevY[0]);
				prevX[0] = tmpX[0];
				prevY[0] = tmpY[0];
				action = motionEvent.getAction();
				
				if (dX[0] >= X_THRESH){
					dX[0] = 0;
					mMessage = RIGHT;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				else if (dX[0] <= -X_THRESH){
					dX[0] = 0;
					mMessage = LEFT;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				if (dY[0] >= Y_THRESH){
					dY[0] = 0;
					mMessage = DOWN;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				if (dY[0] <= -Y_THRESH){
					dY[0] = 0;
					mMessage = UP;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				
				break;
				
			case MotionEvent.ACTION_UP : 
				mMessage = END;
				try {
					mOutputStream.write(mMessage.getBytes());
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
				break;
			}
			return true;
		}
		else if (motionEvent.getPointerCount() == 2 && isEnabled){
			int action = motionEvent.getAction();
			switch (action){
			case MotionEvent.ACTION_DOWN : 
				prevX[0] = motionEvent.getX();
				prevY[0] = motionEvent.getY();
	//			Log.i("Touch screen", "Pressed @X: " + prevX + ", Y: " + prevY);
				break;
				
			case MotionEvent.ACTION_POINTER_1_DOWN :
				prevX[1] = motionEvent.getX(1);
				prevY[1] = motionEvent.getY(1);
				break;
				
			case MotionEvent.ACTION_MOVE :
				tmpX[0] = motionEvent.getX();
				tmpY[0] = motionEvent.getY();
				tmpX[1] = motionEvent.getX(1);
				tmpY[1] = motionEvent.getY(1);
				dX[0] = dX[0] + (tmpX[0] - prevX[0]);
				dY[0] = dY[0] + (tmpY[0] - prevY[0]);
				prevX[0] = tmpX[0];
				prevY[0] = tmpY[0];
				dX[1] = dX[1] + (tmpX[1] - prevX[1]);
				dY[1] = dY[1] + (tmpY[1] - prevY[1]);
				prevX[1] = tmpX[1];
				prevY[1] = tmpY[1];
				action = motionEvent.getAction();
	//			Log.i("Touch screen", "Moved @X: " + tmpX + ", Y: " + tmpY);
				
				if (dX[0] >= X_THRESH || dX[1] >= X_THRESH){
					dX[0] = 0;
					dX[1] = 0;
					mMessage = S_RIGHT;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				else if (dX[0] <= -X_THRESH || dX[1] <= -X_THRESH){
					dX[0] = 0;
					dX[1] = 0;
					mMessage = S_LEFT;
					try {
						mOutputStream.write(mMessage.getBytes());
						Log.i("Sent ", mMessage);
					} catch (IOException e) {
						Log.i("Failed sending ", mMessage);
						// e.printStackTrace();
					}
				}
				break;
				
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_1_UP:
				isEnabled = false;
				mMessage = S_END;
				try {
					mOutputStream.write(mMessage.getBytes());
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
				delay.postDelayed(dummy, DELAY);
				break;
			}
			return true;
		}
		else return false;
	}
	
	private Runnable dummy = new Runnable() {
		@Override
		public void run() {
			isEnabled = true;
		}
	};
}