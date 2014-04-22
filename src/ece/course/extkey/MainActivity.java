package ece.course.extkey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {
	final int DEVICE_FOUND = 101;
	final int REQUEST_ENABLE_BT = 100;
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
	BluetoothDevice mServer;
	BluetoothSocket mSocket;
	BluetoothSocket tmp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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

			btnConnect.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					btnConnect.setText("Cancel");
					Discover();
				}
			});
		} else {
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("No Bluetooth Adapter Found!")
					.setMessage("Press back to exit.")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).create();
			alertDialog.show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED
				&& !mAdapter.isEnabled())
			finish();
	}

	private void Discover() {
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
				BluetoothDevice btServer = btList.get(position);
				Intent intent = new Intent(MainActivity.this,
						ConnectActivity.class);
				intent.putExtra(TAG_SERVER, btServer);
				startActivity(intent);
			}
		});

		btnConnect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				btnConnect.setText("Connect");
				unregisterReceiver(mReceiver);
				mAdapter.cancelDiscovery();
			}
		});
	}
	
	private boolean tryConnect(){
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

	protected void onDestroy() {
		super.onDestroy();
		if (mAdapter != null)
			mAdapter.cancelDiscovery();
		if (mReceiver != null)
			unregisterReceiver(mReceiver);
	}
}
