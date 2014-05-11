package ece.course.extkey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {
	private PowerManager mPowerManager;
	private WakeLock mWakeLock;
	final int DEVICE_FOUND = 101;
	final int REQUEST_ENABLE_BT = 100;
	final int RETURN = 102;
	final String TAG_SERVER = "tagServer";
	int RESULT_CODE;
	Intent ResultData;
	BluetoothAdapter mAdapter;
	final UUID MY_UUID = UUID
			.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	Button btnConnect;
	BroadcastReceiver mReceiver = null;
	ListView mListView;
	ArrayAdapter<String> nameArray;
	List<BluetoothDevice> btList = new ArrayList<BluetoothDevice>();
	BluetoothDevice btServer;
	static BluetoothSocket mSocket;
	BluetoothSocket tmp;
	boolean isDiscovering;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
		
		setContentView(R.layout.main);
		isDiscovering = false;
		nameArray = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(nameArray);
		btnConnect = (Button) findViewById(R.id.btnConnect);

		mAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mAdapter != null) {
			if (!mAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		} else {
			Toast.makeText(MainActivity.this, "No Bluetooth adapter found",
					Toast.LENGTH_LONG).show();
			finish();
		}
		
		btnConnect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (isDiscovering == false){
					btnConnect.setText("Cancel");
					Discover();
				}
				else if (isDiscovering == true){
					btnConnect.setText("Connect");
					stopSearch();
					btList.clear();
					nameArray.clear();
					isDiscovering = false;
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED
				&& !mAdapter.isEnabled())
			finish();
		if (requestCode == RETURN){
			try {
				mSocket.getOutputStream().write('Q');
				Log.i("Sent", "DC Request");
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	private void Discover() {
		isDiscovering = true;
		mReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Get the BluetoothDevice object from the Intent
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// Add the name and address to an array adapter to show in a
					// ListView
					nameArray
							.add(device.getName() + "\n" + device.getAddress());
					btList.add(device);
				}
			}
		};
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister
												// during onDestroy

		mAdapter.startDiscovery();

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				stopSearch();
				BluetoothDevice btServer = btList.get(position);
				if (tryConnect(btServer)) {
					Intent intent = new Intent(MainActivity.this,
							ConnectActivity.class);
					intent.putExtra(TAG_SERVER, btServer);
					startActivityForResult(intent, RETURN);
				} else {
				}
			}
		});
	}

	private boolean tryConnect(BluetoothDevice server) {
		try {
			tmp = server.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) {
			Toast.makeText(MainActivity.this, "Failed to create socket",
					Toast.LENGTH_LONG).show();
			return false;
		}
		mSocket = tmp;

		try {
			// Connect the device through the socket. This will block until it
			// succeeds or throws an exception
			mSocket.connect();
			return true;
		} catch (IOException connectException) {
			// Unable to connect; close the socket and get out
			try {
				mSocket.close();
				Toast.makeText(MainActivity.this, "Failed to connect socket",
						Toast.LENGTH_LONG).show();
				return false;
			} catch (IOException closeException) {
				return false;
			}
		}
	}

	public void stopSearch() {
		if (mAdapter != null) {
			mAdapter.cancelDiscovery();
		}
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	public synchronized void onResume() {
		super.onResume();
		mWakeLock.acquire();
	}

	public synchronized void onPause() {
		super.onPause();
		mWakeLock.release();
	}	
	protected void onDestroy() {
		stopSearch();
		super.onDestroy();
	}
}
