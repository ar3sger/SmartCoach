package de.itintouch.smartcoach;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.itintouch.smartcoach.MainWindow;
import de.itintouch.smartcoach.R;
import de.itintouch.smartcoach.Workout;
import de.itintouch.smartcoach.workoutDB.WorkoutAdapter;
import de.itintouch.smartcoach.workoutDB.Workout_DB;
import de.itintouch.smartcoach.workoutDB.Workout_DB_Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TrainingBeenden extends Activity {

	private TextView tv_text;
	private Button btn_zurueck, btn_probe;
	private long stunden;
	private long minuten;
	private long sekunden;
	private ListView lv_list;
	private Workout_DB_Handler work_dbHandler;
	private List<Workout_DB> workout_DBs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trainingbeenden);
		//getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;

		tv_text = (TextView) findViewById(R.id.zeitAnzeigen);
		btn_zurueck = (Button) findViewById(R.id.btn_zurueck);
		work_dbHandler = new Workout_DB_Handler(getApplicationContext());
		lv_list = (ListView) findViewById(R.id.lv_workoutlist);
//
		Intent intent = getIntent();
		// String zeit = intent.getStringExtra("Time");
		stunden = intent.getLongExtra("Stunden", 0);
		minuten = intent.getLongExtra("Minuten", 0);
		sekunden = intent.getLongExtra("Sekunden", 0);
		tv_text.setText("H: " + stunden + " M: " + minuten + " S: " + sekunden);

	Log.d("Zeit", "H: " + stunden + " M: " + minuten + " S: " + sekunden);

		btn_zurueck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getBaseContext(), MainWindow.class);
				startActivity(intent);

			}
		});
		loadWorkoutDate();
	}

	private void loadWorkoutDate() {

		workout_DBs = work_dbHandler.readAllWorkout();

//		Collections.sort(workout_DBs, new Comparator<Object>() {
//
//			@Override
//			public int compare(Object lhs, Object rhs) {
//				Workout_DB w1 = (Workout_DB) lhs;
//				Workout_DB w2 = (Workout_DB) rhs;
//				return w1.get_name().compareToIgnoreCase(w2.get_name());
//			}
//		});

		// final CustomAdapter adapter = new CustomAdapter(this, workout_DBs);
		// lv_list.setAdapter(adapter);
		
		final WorkoutAdapter adapter = new WorkoutAdapter(this,workout_DBs); 
		lv_list.setAdapter(adapter); 
	}

	@SuppressWarnings("unused")
	private void addWorkoutToList() {
		lv_list = (ListView) findViewById(R.id.lv_workoutlist); 
		loadWorkoutDate();
	}

}
