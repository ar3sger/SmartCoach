package de.itintouch.smartcoach.workoutDB;

public class Workout_DB {

	private int _id;
	private String _name;
	private int _sets;
	private int _reps;
	private int _hours;
	private int _minutes;
	private int _seconds;
	private double _weight;

	// //////////////////////////////////////////////////////////////////
	// Constructors
	// ////////////////////////////////////////////////////////////////////

	public Workout_DB() {
	}

	public Workout_DB(int _id, String _name, int _sets, int _reps, int _hours,
			int _minutes, int _seconds, double weight) {
		super();
		this._id = _id;
		this._name = _name;
		this._sets = _sets;
		this._reps = _reps;
		this._hours = _hours;
		this._minutes = _minutes;
		this._seconds = _seconds;
		this._weight = weight;
	}

	// //////////////////////////////////////////////////////////////////
	// Set and GetMethods
	// //////////////////////////////////////////////////////////////////

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_sets() {
		return _sets;
	}

	public void set_sets(int _sets) {
		this._sets = _sets;
	}

	public int get_reps() {
		return _reps;
	}

	public void set_reps(int _reps) {
		this._reps = _reps;
	}

	public int get_hours() {
		return _hours;
	}

	public void set_hours(int _hours) {
		this._hours = _hours;
	}

	public int get_minutes() {
		return _minutes;
	}

	public void set_minutes(int _minutes) {
		this._minutes = _minutes;
	}

	public int get_seconds() {
		return _seconds;
	}

	public void set_seconds(int _seconds) {
		this._seconds = _seconds;
	}

	public double get_weight() {
		return _weight;
	}

	public void set_weight(double _weight) {
		this._weight = _weight;
	}

}
