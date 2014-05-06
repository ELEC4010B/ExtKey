package ece.course.extkey;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Trackpad extends SurfaceView{
	final int X = 0;
	final int Y = 1;
	final float X_THRESH = 1;
	final float Y_THRESH = 1;
	float dX = 0;
	float dY = 0;
	int[] data = new int[2];
	
	public Trackpad(Context context) {
		super(context);
		setWillNotDraw(false);
	}
	
	public void onDraw(Canvas canvas){
		if (canvas == null)
			return;
		dX = 0;
		dY = 0;
		data[X] = 0;
		data[Y] = 0;
		canvas.drawColor(Color.CYAN);
	}
	
	public boolean onTouchEvent(MotionEvent motionEvent){
		float tmpX = motionEvent.getX();
		float tmpY = motionEvent.getY();
		dX = dX + tmpX;
		dY = dY + tmpY;
		int action = motionEvent.getAction();
		if (action == motionEvent.ACTION_MOVE){
			if (dX >= X_THRESH){
				data[X] = 1;
				dX = 0;
			}
			else if (dX <= -X_THRESH){
				data[X] = -1;
				dX = 0;
			}
			if (dY >= Y_THRESH){
				data[Y] = 1;
				dY = 0;
			}
			else if (dY <= -Y_THRESH){
				data[X] = -1;
				dY = 0;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public int[] getData(){
		int[] tmp = data;
		data[X] = 0;
		data[Y] = 0;
		return tmp;
	
	}
}
