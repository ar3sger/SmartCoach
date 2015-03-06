package de.itintouch.smartcoach.workoutDB;

import java.util.ArrayList;
import java.util.List;

import de.itintouch.smartcoach.R;
import de.itintouch.smartcoach.Routine;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutAdapter extends BaseAdapter {

	private List<Workout_DB> workout_db;
	private Context context;
	private LayoutInflater inflater;
	private ImageView mg;
	private TextView textTest;
	private View view;

	public WorkoutAdapter(Context _context, List<Workout_DB> _items) {
		inflater = LayoutInflater.from(_context);
		this.context = _context;
		this.workout_db = _items;
	}

	@Override
	public int getCount() {
		return workout_db.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Workout_DB workout_DB = workout_db.get(position);
		view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.workoutitem, null);
		}
		TextView name  =(TextView) view.findViewById(R.id.tv_workout_name); 
		name.setText(workout_db.get(position).get_name()); 
		return view;
	}

}
