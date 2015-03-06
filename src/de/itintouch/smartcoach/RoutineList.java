package de.itintouch.smartcoach;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class RoutineList extends Activity {

	ListView lv;
	private RoutineHandler handler;
	private EditText filter;
	List<Routine> routine;
	private Button btn_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routinelist);
		getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;

		lv = (ListView) findViewById(R.id.lv_workoutlist);
		handler = new RoutineHandler(getApplicationContext());
		filter = (EditText) findViewById(R.id.et_filter);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), AddRoutine.class);
				startActivity(intent);
			}
		});

		loadRoutineData();

	}

	private void loadRoutineData() {
		routine = handler.readAllRoutines();

		// Sort list alphabetically
		Collections.sort(routine, new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				Routine r1 = (Routine) o1;
				Routine r2 = (Routine) o2;
				return r1.getName().compareToIgnoreCase(r2.getName());
			}

		});

		final CustomAdapter adapter = new CustomAdapter(this, routine);
		lv.setAdapter(adapter);

		// for (Routine c : routine) {
		// String record = "ID=" + c.getID() + " | Name=" + c.getName()
		// + " | " + c.getMuscleGroup() + " | " + c.getVideo();
		// Log.d("Record", record);
		// }

		filter.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				adapter.filter(s);
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {

				for (Routine c : routine) {

					if (c.getName() == routine.get(position).getName()) {
						Intent intent = new Intent(getBaseContext(),
								SingleRoutineView.class);
						intent.putExtra("id", c.getID());
						intent.putExtra("name", c.getName());
						intent.putExtra("musclegroup_name", c.getMuscleGroup());
						switch (c.getMuscleGroup()) {
						case "Bizeps":
							intent.putExtra("musclegroup_picture",
									"mg/Biceps.png");
							break;
						case "Brust/Trizeps":
							intent.putExtra("musclegroup_picture",
									"mg/Chest_Triceps.png");
							break;
						case "Waden":
							intent.putExtra("musclegroup_picture",
									"mg/Calves.png");
							break;
						case "Brust":
							intent.putExtra("musclegroup_picture",
									"mg/Chest.png");
							break;
						case "Beine":
							intent.putExtra("musclegroup_picture",
									"mg/Quadriceps.png");
							break;
						case "Bauchmuskulatur":
							intent.putExtra("musclegroup_picture", "mg/Abs.png");
							break;
						case "Rücken/Beine":
							intent.putExtra("musclegroup_picture",
									"mg/Leg_Back.png");
							break;
						default:
							intent.putExtra("musclegroup_picture",
									"mg/default.png");
							break;
						}
						intent.putExtra("description", c.getDescription());
						intent.putExtra("video", c.getVideo());
						intent.putExtra("photo", c.getPhotograph());
						startActivity(intent);
					}
				}

			}
		});

	}

	@SuppressWarnings("unused")
	private void addRoutinesToList() {
		lv = (ListView) findViewById(R.id.lv_workoutlist);
		loadRoutineData();
	}
}
