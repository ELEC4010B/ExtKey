package ece.course.extkey;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.*;

public class MainActivity extends Activity {
		BluetoothAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
