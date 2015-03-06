package de.itintouch.smartcoach;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.itintouch.smartcoach.workoutDB.Workout_DB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {

	private List<Routine> items;
	private ArrayList<Routine> itemsCopy;
	private Context context;
	private LayoutInflater inflater;
	private ImageView mg;
	private View view;

	public CustomAdapter(Context _context, List<Routine> _items) {
		inflater = LayoutInflater.from(_context);
		this.context = _context;
		this.items = _items;
		this.itemsCopy = new ArrayList<Routine>();
		itemsCopy.addAll(_items);
	}

//	public CustomAdapter(TrainingBeenden _context, List<Workout_DB> wo_item) {
//		inflater = LayoutInflater.from(_context);
//		this.context = _context;
//		this.workout_db = wo_item;
//		this.wo_itemsCopy = new ArrayList<Workout_DB>();
//		wo_itemsCopy.addAll(wo_item);
//	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Routine routine = items.get(position);
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.contact_item, null);
		TextView name = (TextView) view.findViewById(R.id.tv_full_name);
		TextView musclegroup = (TextView) view
				.findViewById(R.id.tv_musclegroup);
		ImageView photo = (ImageView) view.findViewById(R.id.list_image);
		mg = (ImageView) view.findViewById(R.id.list_mg);
		name.setText(routine.getName());
		musclegroup.setText(routine.getMuscleGroup());

		// //////////////////////////////////////////////////////////
		// ////////// Picture Routine
		// //////////////////////////////////////////////////////////
		try {
			InputStream is = context.getAssets().open(routine.getPhotograph());
			Drawable d = Drawable.createFromStream(is, null);
			photo.setImageDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("ERROR:", routine.getName() + " PICTURE NOT FOUND?");
		}

		// //////////////////////////////////////////////////////////
		// ////////// Picture Musclegroup
		// //////////////////////////////////////////////////////////
		switch (routine.getMuscleGroup()) {
		case "Bizeps":
			setMuscleGroupImage("Biceps");
			break;
		case "Brust/Trizeps":
			setMuscleGroupImage("Chest_Triceps");
			break;
		case "Waden":
			setMuscleGroupImage("Calves");
			break;
		case "Brust":
			setMuscleGroupImage("Chest");
			break;
		case "Beine":
			setMuscleGroupImage("Quadriceps");
			break;
		case "Bauchmuskulatur":
			setMuscleGroupImage("Abs");
			break;
		case "Rücken/Beine":
			setMuscleGroupImage("Leg_Back");
			break;
		default:
			mg.setBackgroundResource(R.color.red);
			break;
		}

		return view;
	}

	// Filter
	@SuppressLint("DefaultLocale")
	public void filter(CharSequence s) {
		String text = s.toString();
		items.clear();
		if (text.length() == 0) {
			items.addAll(itemsCopy);
		} else {
			for (Routine r : itemsCopy) {
				if (r.getName().toLowerCase()
						.contains(s.toString().toLowerCase())
						|| r.getMuscleGroup().toLowerCase()
								.contains(s.toString().toLowerCase())) {
					items.add(r);
				}
			}
		}

		if (items.size() == 0) {
			Toast.makeText(context, "No Routines found!", Toast.LENGTH_SHORT)
					.show();
		}

		// Send changes to ListView
		notifyDataSetChanged();
	}

	private void setMuscleGroupImage(String s) {
		String _musclegroup = s + ".png";
		try {
			InputStream is = context.getAssets().open("mg/" + _musclegroup);
			Drawable d = Drawable.createFromStream(is, null);
			mg.setImageDrawable(d);
		} catch (IOException e) {
			mg.setBackgroundResource(R.color.red);
			e.printStackTrace();
			Log.d("ERROR:", _musclegroup + " MG-PICTURE NOT FOUND?");
		}

	}

}
