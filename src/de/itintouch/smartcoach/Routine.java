package de.itintouch.smartcoach;

import android.util.Log;

public class Routine {
	// Variables
	private int _id;
	private String _name;
	private String _musclegroup;
	private String _description;
	private String _videolink;
	private String _photograph;
	private int _sets;
	private int _reps;
	private String _howto;

	// Constructor
	public Routine() {
	}

	// Constructor
	public Routine(String name, String mg, String desc,
			String video, String photograph, int sets, int reps, String howto) {
		this._name = name;
		this._musclegroup = mg;
		this._description = desc;
		this._videolink = video;
		this._photograph = photograph;
		this._sets = sets;
		this._reps = reps;
		this._howto = howto;
	}

	// ID getter and setter functions
	public String getHowto() {
		return _howto;
	}
	
	public void SetHowto(String howto) {
		this._howto = howto;
	}
	
	public int getSets() {
		return _sets;
	}
	
	public void setSets(int sets) {
		this._sets = sets;
	}
	
	public int getReps() {
		return _reps;
	}
	
	public void setReps(int reps) {
		this._reps = reps;
	}
	public int getID() {
		return _id;
	}

	public void setID(int id) {
		this._id = id;
	}

	// Name getter and setter functions
	public String getName() {
		return this._name;
	}

	public void setName(String name) {
		this._name = name;
	}

	// Phone Number getter and setter functions
	public String getMuscleGroup() {
		return this._musclegroup;
	}

	public void setMuscleGroup(String phone_number) {
		this._musclegroup = phone_number;
	}

	// Email getter and setter functions
	public String getDescription() {
		return this._description;
	}

	public void setDescription(String email) {
		this._description = email;
	}

	// Postal Address getter and setter functions
	public String getVideo() {
		return this._videolink;
	}
	
	public void setzeVideo(String video) {
		this._videolink = video;
	}

	// Photograph getter and setter functions
	public String getPhotograph() {
		return this._photograph;
	}

	public void setPhotograph(String photograph) {
		this._photograph = photograph;
	}
}