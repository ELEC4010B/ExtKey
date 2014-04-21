package ece.course.extkey;

import java.io.IOException;
import java.util.UUID;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {
	final int DEVICE_FOUND = 101;
	final int REQUEST_ENABLE_BT = 100;
	final String TAG_SERVER = "tagServer";
	int RESULT_CODE;
	Intent ResultData;
	BluetoothAdapter mAdapter;
	final UUID MY_UUID = UUID.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	Button btnConnect;
	BroadcastReceiver mReceiver = null;
	ListView mListView;
	ArrayAdapter<String> mArrayAdapter;
	ArrayAdapter<BluetoothDevice> btArray;
	BluetoothDevice btServer;
	BluetoothSocket mSocket;
	BluetoothSocket tmp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mArrayAdapter = new ArrayAdapter<String>(this, R.layout.main);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(mArrayAdapter);
		btnConnect = (Button) findViewById(R.id.btnConnect);
		
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mAdapter.isEnabled()){
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		btnConnect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				btnConnect.setText("Cancel");
				Discover();
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_ENABLE_BT && resultCode== RESULT_CANCELED && !mAdapter.isEnabled())
			finish();
	}
	
	private void Discover(){
		mAdapter.startDiscovery();
		mReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            // Get the BluetoothDevice object from the Intent
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            // Add the name and address to an array adapter to show in a ListView
		            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		            btArray.add(device);
		        }
		    }
		};
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		
		mListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView parent, View v, int position, long id){
				
				BluetoothDevice btServer = btArray.getItem(position);
				Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
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
	
	protected void onDestroy(){
		super.onDestroy();
		mAdapter.cancelDiscovery();
		if (mReceiver != null)
			unregisterReceiver(mReceiver);
	}
}
