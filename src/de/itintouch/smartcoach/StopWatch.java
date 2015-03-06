package de.itintouch.smartcoach;

import android.widget.Toast;

public class StopWatch {

	private long startTime ; 
	private long stopTime = 0;
	private boolean running = false;
	long sekunden;
	long minuten;
	long stunden;

	public void start() {
		this.startTime = System.currentTimeMillis();
		sekunden = (System.currentTimeMillis() - startTime);
		minuten = sekunden / 60;
		stunden = minuten % 60;
		this.running = true;
	}

	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	// elaspsed time in milliseconds
	public long getElapsedTime() {

		if (running) {

		} else {
			sekunden = (stopTime - startTime);
		}
		return sekunden;
	}

	public long getStartTime() {
		this.startTime = System.currentTimeMillis();
		return startTime;
	}

	// elaspsed time in seconds
	public long getElapsedTimeSecs() {
		long elapsed;
		if (running) {
			elapsed = ((System.currentTimeMillis()) / 1000);
		} else {
			elapsed = ((stopTime - startTime) / 1000);
		}
		return elapsed;
	}

	public long getSekunden() {
		sekunden = (System.currentTimeMillis() - startTime) / 1000;
		return sekunden % 60;
	}

	public long getMinuten() {
		
		sekunden = (System.currentTimeMillis() - startTime)/1000;
		minuten = sekunden / 60;
		return minuten;
	}

	public long getStunden() {
		sekunden = (System.currentTimeMillis() - startTime)/1000;
		minuten = sekunden / 60;
		stunden = minuten / 60;
		return stunden;
	}

}