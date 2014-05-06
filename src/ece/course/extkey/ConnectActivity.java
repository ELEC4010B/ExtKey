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

public class ConnectActivity extends Activity {
	BluetoothSocket mSocket;
	Trackpad mTrackpad;
	final UUID MY_UUID = UUID
			.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	final int BUTTON_NO = 24;
	Button[] btn;
	int[] ids = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
			R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnAnd,
			R.id.btnApostrophe, R.id.btnAt, R.id.btnBracketLeft,
			R.id.btnBracketRight, R.id.btnColon, R.id.btnComma, R.id.btnDollar,
			R.id.btnDot, R.id.btnDoubleQuotation, R.id.btnExclamation,
			R.id.btnHyphen, R.id.btnQuestion, R.id.btnSemicolon };
	String[] messages = {"0", "1", "2", "3", 
			"4", "5", "6", "7", "8", "9", "&", 
			"'", "@", "(",
			")", ":", ",", "$",
			".", "\"", "!",
			"-", "?", ";"};
	
	static OutputStream mOutputStream;
	String mMessage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSocket = MainActivity.mSocket;
		btn = new Button[BUTTON_NO];
		mTrackpad = new Trackpad(this);
		try {
			mOutputStream = mSocket.getOutputStream();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			setContentView(R.layout.numpad);
			for (int i = 0; i < BUTTON_NO; i++) {
				final int tmp = i;
				btn[tmp] = (Button) findViewById(ids[tmp]);
				btn[tmp].setOnClickListener(new OnClickListener(){
					public void onClick(View view){
						mMessage = messages[tmp];
						try {
							mOutputStream.write(mMessage.getBytes());
							// mOutputStream.flush();
							Log.i("Sent ", mMessage);
						} catch (IOException e) {
							// e.printStackTrace();
						}
					}
				});
			}

			Log.i("", "Set Content View: Numpad");
		} else {
			Log.i("", "Set Content View: Trackpad");
			setContentView(mTrackpad);
		}
	}

	public void onDestroy(){
		super.onDestroy();
		
	}
}
