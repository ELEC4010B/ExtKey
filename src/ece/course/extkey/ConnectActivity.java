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
	final int X = 0;
	final int Y = 1;
	final boolean LANDSCAPE = true;
	final boolean PORTRAIT = false;
	final String TAG_SERVER = "tagServer";
	BluetoothDevice mServer;
	BluetoothSocket mSocket;
	Trackpad mTrackpad;
	final UUID MY_UUID = UUID
			.fromString("4e1422d0-c62c-11e3-9c1a-0800200c9a66");
	BluetoothSocket tmp;
	final int BUTTON_NO = 10;
	Button[] btn = new Button[BUTTON_NO];
	int[] ids = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
			R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnAnd,
			R.id.btnApostrophe, R.id.btnAt, R.id.btnBracketLeft,
			R.id.btnBracketRight, R.id.btnColon, R.id.btnComma, R.id.btnDollar,
			R.id.btnDot, R.id.btnDoubleQuotation, R.id.btnExclamation,
			R.id.btnHyphen, R.id.btnQuestion, R.id.btnSemicolon };
	OutputStream mOutputStream;
	String mMessage;
	boolean Mode;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSocket = MainActivity.mSocket;
		mTrackpad = new Trackpad(this);
		try {
			mOutputStream = mSocket.getOutputStream();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			Mode = PORTRAIT;
			numActivity();
		} else {
			Mode = LANDSCAPE;
			trackActivity();
		}
	}

	public void numActivity() {
		setContentView(R.layout.numpad);
	try {
			mOutputStream = mSocket.getOutputStream();
		} catch (IOException e1) {
			// e1.printStackTrace();
		}

		for (int i = 0; i < BUTTON_NO; i++) {
			btn[i] = (Button) findViewById(ids[i]);
		}

		btn[0].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "0";
				Log.i("Sent ", mMessage);
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[1].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "1";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[2].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "2";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[3].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "3";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[4].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "4";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[5].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "5";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[6].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "6";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[7].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "7";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});

		btn[8].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "8";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[9].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "9";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[10].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "&";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[11].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "'";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[12].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "@";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[13].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "(";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[14].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = ")";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[15].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = ":";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[16].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = ",";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[17].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "$";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[18].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = ".";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[19].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "\"";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[20].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "!";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[21].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "-";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[22].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = "?";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});

		btn[23].setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				mMessage = ";";
				try {
					mOutputStream.write(mMessage.getBytes());
					// mOutputStream.flush();
					Log.i("Sent ", mMessage);
				} catch (IOException e) {
					Log.i("Failed sending ", mMessage);
					// e.printStackTrace();
				}
			}
		});
		
	}
	public void trackActivity() {
		setContentView(mTrackpad);
		while (Mode == LANDSCAPE) {
			int[] data = mTrackpad.getData();
			if (data[X] == 0 && data[Y] == 0)
				continue;
			if (data[X] == 1) {
				mMessage = "L";
			} else if (data[X] == -1) {
				mMessage = "R";
			}
			if (data[Y] == 1) {
				mMessage = "U";
			} else if (data[Y] == -1) {
				mMessage = "D";
			}
			try {
				mOutputStream.write(mMessage.getBytes());
				// mOutputStream.flush();
				Log.i("Sent ", mMessage);
			} catch (IOException e) {
				Log.i("Failed sending ", mMessage);
				// e.printStackTrace();
			}
		}
	}
/*	
=======

>>>>>>> 14f5300cc988b2ec35fd345e85e12f2931b14989
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.i("Config Change:", "Orientation Changed to Landscape");
			Mode = LANDSCAPE;
			trackActivity();
		} else {
			Mode = PORTRAIT;
			Log.i("Config Change:", "Orientation Changed to Portrait");
			numActivity();
		}
	}
*/	
	public void onDestroy(){
		super.onDestroy();
		
	}
}
