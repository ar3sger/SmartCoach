package de.itintouch.smartcoach.workoutDB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AutoCompleteTextView.Validator;

public class Workout_DB_Handler extends SQLiteOpenHelper {

	// All variables about DB
	// Database name
	private static final String DATABASE_NAME = "workoutDB";
	// Database version
	private static final int DATABASE_VERSION = 1;
	// Contacts table name
	private static final String TABLE_WORKOUT = "workout";
	// Table Column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_SET = "sets";
	private static final String COLUMN_REPETITION = "repetition";
	private static final String COLUMN_WEIGHT = "weight";
	private static final String COLUMN_HOURS = "hours";
	private static final String COLUMN_MINUTES = "minutes";
	private static final String COLUMN_SECONDS = "seconds";

	private String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_SET,
			COLUMN_REPETITION, COLUMN_WEIGHT, COLUMN_HOURS, COLUMN_MINUTES,
			COLUMN_SECONDS };

	// Create database

	public Workout_DB_Handler(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public Workout_DB_Handler(Context applicationContext) {
		super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_WORKOUT + "(" + COLUMN_ID
				+ " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_SET
				+ " INTEGER," + COLUMN_REPETITION + " INTEGER," + COLUMN_WEIGHT
				+ " DOUBLE," + COLUMN_HOURS + " INTEGER," + COLUMN_MINUTES
				+ " INTEGER," + COLUMN_SECONDS + " INTEGER)";
		db.execSQL(CREATE_TABLE);
	}

	// Drop table if older version exist
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
		onCreate(db);
	}

	public boolean addWorkoutDetails(Workout_DB workout) {
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		ContentValues cvalValues = new ContentValues();

		cvalValues.put(COLUMN_NAME, workout.get_name());
		cvalValues.put(COLUMN_SET, workout.get_sets());
		cvalValues.put(COLUMN_REPETITION, workout.get_reps());
		cvalValues.put(COLUMN_WEIGHT, workout.get_weight());
		cvalValues.put(COLUMN_HOURS, workout.get_hours());
		cvalValues.put(COLUMN_MINUTES, workout.get_minutes());
		cvalValues.put(COLUMN_SECONDS, workout.get_seconds());

		long insert = sqlDB.insert(TABLE_WORKOUT, null, cvalValues);
		sqlDB.close();

		if (insert != 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<Workout_DB> readAllWorkout() {

		List<Workout_DB> listWorkout_DB = new ArrayList<Workout_DB>();

		SQLiteDatabase sqlDB = this.getWritableDatabase();

		Cursor cursor = sqlDB.query(TABLE_WORKOUT, columns, null, null, null,
				null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			Workout_DB workout_DB = new Workout_DB();

			workout_DB.set_id(Integer.parseInt(cursor.getString(0)));
			workout_DB.set_name(cursor.getString(1));
			workout_DB.set_sets(cursor.getInt(2));
			workout_DB.set_reps(cursor.getInt(3));
			workout_DB.set_weight(cursor.getDouble(4));
			workout_DB.set_hours(cursor.getInt(5));
			workout_DB.set_minutes(cursor.getInt(6));
			workout_DB.set_seconds(cursor.getInt(7));

			listWorkout_DB.add(workout_DB);
			cursor.moveToNext();

		}
		cursor.close();
		return listWorkout_DB;

	}

	public boolean deleteWorkout(int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		int i = db.delete(TABLE_WORKOUT, COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

}
