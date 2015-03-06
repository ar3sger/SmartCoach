package de.itintouch.smartcoach;

import java.io.IOException;
import java.io.InputStream;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.VideoView;

@SuppressWarnings("deprecation")
public class SingleRoutineView extends TabActivity {

	private TabHost tabHost;
	private TabHost.TabSpec spec;
	private TextView tv;
	private ImageView iv_RoutineImage;
	private ImageView iv_RoutineMuscleGroupImage;
	private TextView tv_RoutineMuscleGroup;
	private TextView tv_RoutineDescription;
	private Intent videoIntent;
	private ProgressDialog pDialog;
	private VideoView vv_video;

	private int id;
	private String name;
	private String muscleGroup_name;
	private String muscleGroup_picture;
	private String description;
	private String videoLink;
	private String photo;
	private boolean allowed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_routine_view);

		// Setting Ids
		tv = (TextView) findViewById(R.id.tv_singleRoutine);
		iv_RoutineImage = (ImageView) findViewById(R.id.tabhost_RoutineImage);
		iv_RoutineMuscleGroupImage = (ImageView) findViewById(R.id.tabhost_RoutneMuscleGroupImage);
		tv_RoutineMuscleGroup = (TextView) findViewById(R.id.tabhost_RoutineMuscleGroup);
		tv_RoutineDescription = (TextView) findViewById(R.id.tabhost_RoutineDescription);
		vv_video = (VideoView) findViewById(R.id.vv_video);

		// Getting the Content
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		name = intent.getStringExtra("name");
		muscleGroup_name = intent.getStringExtra("musclegroup_name");
		muscleGroup_picture = intent.getStringExtra("musclegroup_picture");
		description = intent.getStringExtra("description");
		videoLink = intent.getStringExtra("video");
		photo = intent.getStringExtra("photo");

		// Setting the Labels
		tv.setText(name);
		tv_RoutineDescription.setText(description);
		tv_RoutineMuscleGroup.setText("Muskelgruppe: " + muscleGroup_name);

		// Load Routine Image
		try {
			InputStream is = getBaseContext().getAssets().open(photo);
			Drawable d = Drawable.createFromStream(is, null);
			iv_RoutineImage.setImageDrawable(d);
		} catch (IOException e) {
			iv_RoutineImage.setBackgroundResource(R.color.red);
			e.printStackTrace();
			Log.d("ERROR:", name + " PICTURE NOT FOUND?");
		}

		// Load Musclegroup Image
		try {
			InputStream is = getBaseContext().getAssets().open(
					muscleGroup_picture);
			Drawable d = Drawable.createFromStream(is, null);
			iv_RoutineMuscleGroupImage.setImageDrawable(d);
		} catch (IOException e) {
			iv_RoutineMuscleGroupImage.setBackgroundResource(R.color.red);
			e.printStackTrace();
			Log.d("ERROR:", name + " MG-PICTURE NOT FOUND?");
		}
		
		videoIntent = new Intent(this, VideoViewer.class);
		videoIntent.putExtra("videoLink", videoLink);
		
		// Defining the Tabs
		tabHost = getTabHost();

		spec = tabHost.newTabSpec("tab1").setIndicator("Description")
				.setContent(R.id.tabhost_desc_layout);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2").setIndicator("Video")
				.setContent(videoIntent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
	

}
