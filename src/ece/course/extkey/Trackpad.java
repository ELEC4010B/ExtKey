package ece.course.extkey;

import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Trackpad extends SurfaceView{
	final int X = 0;
	final int Y = 1;
	final float X_THRESH = 30;
	final float Y_THRESH = 30;
	OutputStream mOutputStream;
	String mMessage;
	float tmpX;
	float tmpY;
	float prevX;
	float prevY;
	float dX;
	float dY;
	
	
	public Trackpad(Context context) {
		super(context);
		dX = 0;
		dY = 0;
		mOutputStream = ConnectActivity.mOutputStream;
		setWillNotDraw(false);
	}
	
	public void onDraw(Canvas canvas){
		if (canvas == null)
			return;
		canvas.drawColor(Color.CYAN);
	}
	
	public boolean onTouchEvent(MotionEvent motionEvent){
		int action = motionEvent.getAction();
		switch (action){
		case MotionEvent.ACTION_DOWN : 
			prevX = motionEvent.getX();
			prevY = motionEvent.getY();
//			Log.i("Touch screen", "Pressed @X: " + prevX + ", Y: " + prevY);
			break;
			
		case MotionEvent.ACTION_MOVE :
			tmpX = motionEvent.getX();
			tmpY = motionEvent.getY();
			dX = dX + (tmpX - prevX);
			dY = dY + (tmpY - prevY);
			prevX = tmpX;
			prevY = tmpY;
			action = motionEvent.getAction();
//			Log.i("Touch screen", "Moved @X: " + tmpX + ", Y: " + tmpY);
			
			if (dX >= X_THRESH){
				dX = 0;
				mMessage = "R";
				try {
					mOutputStream.write(mMessage.getBytes());
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
			else if (dX <= -X_THRESH){
				dX = 0;
				mMessage = "L";
				try {
					mOutputStream.write(mMessage.getBytes());
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
			if (dY >= Y_THRESH){
				dY = 0;
				mMessage = "D";
				try {
					mOutputStream.write(mMessage.getBytes());
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
			if (dY <= -Y_THRESH){
				dY = 0;
				mMessage = "U";
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
//			Log.i("Touch screen", "Released @dX: " + dX + ", dY: " + dY);
			break;
		}
		return true;
	}
}