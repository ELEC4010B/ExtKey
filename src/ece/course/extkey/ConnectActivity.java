package ece.course.extkey;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.bluetooth.*;
import android.content.res.Configuration;

public class ConnectActivity extends Activity{
	final int X = 0;
	final int Y = 1;
	final boolean LANDSCAPE = true;
	final boolean PORTRAIT = false;
	final String TAG_SERVER = "tagServer";
	BluetoothDevice mServer;
	BluetoothSocket mSocket;
	Trackpad mTrackpad;
	final UUID MY_UUID = UUID.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	BluetoothSocket tmp;
	final int BUTTON_NO = 10;
	Button[] btn = new Button[BUTTON_NO];
	int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
	OutputStream mOutputStream;
	String mMessage;
	boolean Mode;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mSocket = MainActivity.mSocket;
		mTrackpad = new Trackpad(this);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			Mode = PORTRAIT;
			numActivity();
		}
		else {
			Mode = LANDSCAPE;
			trackActivity();
		}		
	}
	
	public void numActivity(){
		setContentView(R.layout.numpad);
		try {
			mOutputStream = mSocket.getOutputStream();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}

		for (int i=0; i<BUTTON_NO; i++){
			btn[i] = (Button) findViewById(ids[i]);
		}
		
		btn[0].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "0";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[1].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "1";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[2].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "2";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[3].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "3";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[4].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "4";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[5].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "5";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[6].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "6";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[7].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "7";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[8].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "8";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					//e.printStackTrace();
				}
			}
		});
		
		btn[9].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "9";
				try {
					mOutputStream.write(mMessage.getBytes());
//					mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					//e.printStackTrace();
				}
			}
		});
		

	}
	
	public void trackActivity(){
    	setContentView(mTrackpad);
		while (Mode == LANDSCAPE){
			int[] data = mTrackpad.getData();
			if (data[X] == 0 && data[Y] == 0)
				continue;
			if (data[X] == 1){
				mMessage = "L";
			}
			else if (data[X] == -1){
				mMessage = "R";
			}
			if (data[Y] == 1){
				mMessage = "U";
			}
			else if (data[Y] == -1){
				mMessage = "D";
			}
			try {
				mOutputStream.write(mMessage.getBytes());
//				mOutputStream.flush();
				Log.i("Sent ", mMessage);
			} catch (IOException e) {
				Log.i("Failed sending ", mMessage);
				//e.printStackTrace();
			}
		}
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
	    	Log.i("Config Change:", "Orientation Changed to Landscape");
	    	Mode = LANDSCAPE;
	    	trackActivity();
	    } else {
	    	Mode = PORTRAIT;
	    	Log.i("Config Change:", "Orientation Changed to Portrait");
	    	numActivity();
	    }
	}
}
