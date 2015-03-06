package de.itintouch.smartcoach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainWindow extends Activity {

	private Button btn_showRoutines;
	private Button btn_startWorkout;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        
        btn_showRoutines = (Button) findViewById(R.id.btn_showRoutines);
        btn_showRoutines.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), RoutineList.class);
				startActivity(intent);
			}
		});
        
        btn_startWorkout = (Button) findViewById(R.id.btn_startWorkout);
        btn_startWorkout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), Workout.class);
				startActivity(intent);
				
			}
		});
    }

}
