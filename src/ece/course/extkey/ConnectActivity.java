package ece.course.extkey;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.*;
import android.content.Intent;

public class ConnectActivity extends Activity{
	final String TAG_SERVER = "tagServer";
	final BluetoothDevice mServer;
	final BluetoothSocket mSocket;
	final UUID MY_UUID = UUID.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	BluetoothSocket tmp;
	final int BUTTON_NO = 10;
	Button[] btn;
	int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
	
	ConnectActivity(){
		super();
		Intent intent = getIntent();
		mServer = intent.getParcelableExtra(TAG_SERVER);
		try {
			tmp = mServer.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) { }
		mSocket = tmp;
		
		try {
		        // Connect the device through the socket. This will block until it succeeds or throws an exception
		    mSocket.connect();
		} catch (IOException connectException) {
		    // Unable to connect; close the socket and get out
			try {
				mSocket.close();
			} catch (IOException closeException) { }
			//TODO: Pass back control to MainActivity if connection fails
			return;
		}
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numpad);
		for (int i=0; i<BUTTON_NO; i++){
			btn[i] = (Button) findViewById(ids[i]);
		}
	}
}
