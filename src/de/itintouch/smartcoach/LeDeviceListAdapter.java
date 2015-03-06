package de.itintouch.smartcoach;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;


/**
 * Displays basic information about beacon.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class LeDeviceListAdapter extends BaseAdapter {

  private ArrayList<Beacon> beacons;
  private LayoutInflater inflater;
  private List<Routine> routine;
  private RoutineHandler handler;
  private Context context;

  public LeDeviceListAdapter(Context context) {
	this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.beacons = new ArrayList<Beacon>();
    handler = new RoutineHandler(context);
    routine = handler.readAllRoutines();
    
    for (Routine r : routine) {
    	Log.d("ID:" + r.getID(),"Name: " + r.getName());
    }
  }

  public void replaceWith(Collection<Beacon> newBeacons) {
    this.beacons.clear();
    this.beacons.addAll(newBeacons);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return beacons.size();
  }

  @Override
  public Beacon getItem(int position) {
    return beacons.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    view = inflateIfRequired(view, position, parent);
    bind(getItem(position), view);
    return view;
  }

  private void bind(Beacon beacon, View view) {
    ViewHolder holder = (ViewHolder) view.getTag();
    int id = (beacon.getMinor()%100) - 1;
    String photo;
    
    switch(routine.get(id).getMuscleGroup()) {
		case "Bizeps":
			photo = "mg/Biceps.png";
			break;
		case "Brust/Trizeps":
			photo = "mg/Chest_Triceps.png";
			break;
		case "Waden":
			photo = "mg/Calves.png";
			break;
		case "Brust":
			photo = "mg/Chest.png";
			break;
		case "Beine":
			photo = "mg/Quadriceps.png";
			break;
		case "Bauchmuskulatur":
			photo = "mg/Abs.png";
			break;
		case "Rücken/Beine":
			photo = "mg/Leg_Back.png";
			break;
		default:
			photo = "mg/no_picture.png";
			break;
    }
	try {
		InputStream is = context.getAssets().open(photo);
		Drawable d = Drawable.createFromStream(is, null);
		holder.mgImageView.setImageDrawable(d);
	} catch (IOException e) {
		e.printStackTrace();
		Log.d("ERROR:","PICTURE NOT FOUND?");
	}
    holder.nameTextView.setText(routine.get(id).getName());
    holder.setsTextView.setText("" + routine.get(id).getSets() + " Sätze");
    holder.repsTextView.setText("" + routine.get(id).getReps() + " Wiederholungen");
  }

  private View inflateIfRequired(View view, int position, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.device_item, null);
      view.setTag(new ViewHolder(view));
    }
    return view;
  }

  static class ViewHolder {
	final ImageView mgImageView;
    final TextView nameTextView;
    final TextView setsTextView;
    final TextView repsTextView;


    ViewHolder(View view) {
      mgImageView = (ImageView) view.findViewWithTag("mg");
      nameTextView = (TextView) view.findViewWithTag("name");
      setsTextView = (TextView) view.findViewWithTag("sets");
      repsTextView = (TextView) view.findViewWithTag("reps");
    }
  }
}
