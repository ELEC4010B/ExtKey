package ece.course.extkey;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.bluetooth.*;
import android.content.Intent;

public class ConnectActivity extends Activity{
	final String TAG_SERVER = "tagServer";
	final BluetoothDevice mServer;
	final BluetoothSocket mSocket;
	final UUID MY_UUID = UUID.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	BluetoothSocket tmp;
	
	ConnectActivity(){
		super();
		Intent intent = getIntent();
		mServer = intent.getParcelableExtra(TAG_SERVER);
		try {
			tmp = mServer.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) { }
		mSocket = tmp;
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
}
