package de.itintouch.smartcoach;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class Workout extends Activity {

	private static final String TAG = Workout.class.getSimpleName();

	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON = "extrasBeacon";

	private static final int REQUEST_ENABLE_BT = 1234;
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
			null, null, null);

	private TextView tv_devicecount;
	private ListView lv_device_list;
	private Button btn_statistic;
	private Button btn_cancelWorkout;
	private LeDeviceListAdapter adapter;
	private BeaconManager beaconManager;
	private StopWatch stopWatch = new StopWatch();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);

		stopWatch.start();

		// Set Id
		tv_devicecount = (TextView) findViewById(R.id.tv_devicescount);
		lv_device_list = (ListView) findViewById(R.id.lv_device_list);
		btn_statistic = (Button) findViewById(R.id.btn_showStats);
		btn_cancelWorkout = (Button) findViewById(R.id.btn_endWorkout);

		// Listener

		btn_statistic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		btn_cancelWorkout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beaconManager.disconnect();
				Intent i = new Intent(getBaseContext(), TrainingBeenden.class);
				i.putExtra("Stunden", stopWatch.getStunden());
				i.putExtra("Minuten", stopWatch.getMinuten());
				i.putExtra("Sekunden", stopWatch.getSekunden());
				startActivity(i);
				finish(); 
			}
		});

		// List
		adapter = new LeDeviceListAdapter(this);
		lv_device_list.setAdapter(adapter);

		// Beacon Manager
		beaconManager = new BeaconManager(this);
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {
				// Note that results are not delivered on UI thread.
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// Note that beacons reported here are already sorted by
						// estimated
						// distance between device and beacon.
						tv_devicecount.setText("Geräte in der Nähe: "
								+ beacons.size());
						adapter.replaceWith(beacons);
					}
				});
			}
		});
		
		//Itemlistener
	}

	
	@Override
	protected void onStart() {
		super.onStart();

		// Check if device supports Bluetooth Low Energy.
		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(this, "Device does not have Bluetooth Low Energy",
					Toast.LENGTH_LONG).show();
			return;
		}

		// If Bluetooth is not enabled, let user enable it.
		if (!beaconManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			connectToService();
		}
	}

	@Override
	protected void onStop() {
		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
		} catch (RemoteException e) {
			Log.d(TAG, "Error while stopping ranging", e);
		}

		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				connectToService();
			} else {
				Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG)
						.show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void connectToService() {
		tv_devicecount.setText("Suche nach Geräten...");
		adapter.replaceWith(Collections.<Beacon> emptyList());
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
				} catch (RemoteException e) {
					Toast.makeText(
							Workout.this,
							"Cannot start ranging, something terrible happened",
							Toast.LENGTH_LONG).show();
					Log.e(TAG, "Cannot start ranging", e);
				}
			}
		});
	}

}
