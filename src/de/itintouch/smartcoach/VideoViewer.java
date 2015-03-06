package de.itintouch.smartcoach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewer extends Activity implements OnClickListener {
	
	private String videoLink;
	private ProgressDialog pDialog;
	private int position;
	private VideoView vv_video;
	private boolean allowed;
	private String title;
	private String message;
	private String accept;
	private String decline;

	private DialogInterface.OnClickListener listenerAccept;
	private DialogInterface.OnClickListener listenerDecline;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_video);
		vv_video = (VideoView) findViewById(R.id.vv_video);
		
		title = "Internetnutzung";
		message = "Das Laden der Videos erfordert eine mobile Datenverbindung. Möchten Sie fortfahren?";
		accept = "Ja";
		decline = "Nein";
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setPositiveButton(accept, this);
		dialog.setNegativeButton(decline, this);
		dialog.setCancelable(false);
		dialog.show();
		
		listenerAccept = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				allowed = true;
				loadVideo();
			}
		};
			
		listenerDecline = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				allowed = false;
			}
		};
	}
	
	private void loadVideo() {
		try {
			Intent intent = getIntent();
			videoLink = intent.getStringExtra("videoLink");
			Log.d("Videolink", "Link: " + videoLink);
			
			// Create a progressbar
			pDialog = new ProgressDialog(VideoViewer.this);
			// Set progressbar title
			pDialog.setTitle("Ladevorgang");
			// Set progressbar message
			pDialog.setMessage("Puffer füllen...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			// Show progressbar
			pDialog.show();
			vv_video.setVideoPath(videoLink);
			MediaController mediaController = new 
					MediaController(this);
			mediaController.setAnchorView(vv_video);
			vv_video.setMediaController(mediaController);
			vv_video.requestFocus();
			
			vv_video.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					pDialog.dismiss();
					vv_video.start();
				}
			});
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Video nicht gefunden!", Toast.LENGTH_SHORT);
			Log.d("Video Error", "VIDEO NOT FOUND?");
			e.printStackTrace();
			//pDialog.cancel();
			pDialog.dismiss();
		}
		pDialog.dismiss();
		
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch(which) {
		case DialogInterface.BUTTON_POSITIVE:
			allowed = true;
			loadVideo();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			allowed = false;
			vv_video.setEnabled(false);
			break;
		default: 
			break;
		}
	}
}
