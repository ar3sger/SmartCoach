package de.itintouch.smartcoach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRoutine extends Activity {

	private Button btn_abbrechen;
	private Button btn_hinzufuegen;
	private EditText routineName;
	private EditText routineDesc;
	private RoutineHandler handler;
	
	private Spinner spinner;
	private ArrayAdapter<CharSequence> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_routine);
		routineName = (EditText) findViewById(R.id.et_addroutinename);
		routineDesc = (EditText) findViewById(R.id.tv_addroutinedesc);
		handler = new RoutineHandler(getApplicationContext());
		
		btn_hinzufuegen = (Button) findViewById(R.id.btn_hinzufuegen);
		btn_hinzufuegen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name ="";
				String desc ="";
				try {
					
					if (routineName.getText().length() < 1) {
						Toast.makeText(getBaseContext(), "Name der Übung ist zu kurz!", Toast.LENGTH_SHORT).show();
					}
					else {
						name = routineName.getText().toString();
						desc = routineDesc.getText().toString();
						int position = spinner.getSelectedItemPosition();
						String muscleGroup = adapter.getItem(position).toString();				
						handler.addRoutineDetails(new Routine(name,muscleGroup,desc,"","mg/no_picture.png", 3, 12, "Ausführung"));
						Intent intent = new Intent(getBaseContext(), RoutineList.class);
						startActivity(intent);
						finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btn_abbrechen = (Button) findViewById(R.id.btn_abbrechen);
		btn_abbrechen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), RoutineList.class);
				startActivity(intent);
				finish();
			}
		});
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		
	}
}
