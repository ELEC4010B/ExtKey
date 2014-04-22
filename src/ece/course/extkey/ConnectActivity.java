package ece.course.extkey;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.bluetooth.*;

public class ConnectActivity extends Activity{
	final String TAG_SERVER = "tagServer";
	BluetoothDevice mServer;
	BluetoothSocket mSocket;
	final UUID MY_UUID = UUID.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	BluetoothSocket tmp;
	final int BUTTON_NO = 10;
	Button[] btn = new Button[BUTTON_NO];
	int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
	OutputStream mOutputStream;
	String mMessage;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numpad);
		mSocket = MainActivity.mSocket;
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
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
		btn[9].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "9";
				try {
					mOutputStream.write(mMessage.getBytes());
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		});
		
	}
	
}
