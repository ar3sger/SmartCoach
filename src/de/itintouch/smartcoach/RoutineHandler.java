package de.itintouch.smartcoach;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler.Value;

public class RoutineHandler extends SQLiteOpenHelper {
	// All variables about DB
	// Database name
	private static final String DATABASE_NAME = "contactBook";
	// Database version
	private static final int DATABASE_VERSION = 1;
	// Contacts table name
	private static final String TABLE_ROUTINE = "routines";
	// Table Column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_MUSCLEGROUP = "musclegroup";
	private static final String COLUMN_DESCRIPTION = "desc";
	private static final String COLUMN_VIDEO = "video";
	private static final String COLUMN_PHOTOGRAPH = "photograph";
	private static final String COLUMN_SETS = "sets";
	private static final String COLUMN_REPS = "reps";
	private static final String COLUMN_HOWTO = "howto";
	private String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_MUSCLEGROUP,
			COLUMN_DESCRIPTION, COLUMN_VIDEO, COLUMN_PHOTOGRAPH, COLUMN_SETS, COLUMN_REPS, COLUMN_HOWTO };

	// Create database
	public RoutineHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Create table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_ROUTINE + "(" + COLUMN_ID
				+ " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
				+ COLUMN_MUSCLEGROUP + " TEXT," + COLUMN_DESCRIPTION + " TEXT,"
				+ COLUMN_VIDEO + " TEXT," + COLUMN_PHOTOGRAPH + " TEXT,"
				+ COLUMN_SETS + " INTEGER," + COLUMN_REPS + " INTEGER,"
				+ COLUMN_HOWTO + " TEXT)";
		db.execSQL(CREATE_TABLE);
	}

	// Drop table if older version exist
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
		onCreate(db);
	}

	/*
	 * Handling Contact table using sql queries.
	 */
	// Add Contact
	public boolean addRoutineDetails(Routine contact) {
		// Get db writable
		SQLiteDatabase db = this.getWritableDatabase();
		// Get the values to insert
		ContentValues vals = new ContentValues();
		vals.put(COLUMN_NAME, contact.getName());
		vals.put(COLUMN_MUSCLEGROUP, contact.getMuscleGroup());
		vals.put(COLUMN_DESCRIPTION, contact.getDescription());
		vals.put(COLUMN_VIDEO, contact.getVideo());
		vals.put(COLUMN_PHOTOGRAPH, contact.getPhotograph());
		vals.put(COLUMN_SETS, contact.getSets());
		vals.put(COLUMN_REPS, contact.getReps());
		vals.put(COLUMN_HOWTO, contact.getHowto());
		// Insert values into table
		long i = db.insert(TABLE_ROUTINE, null, vals);
		// Close database
		db.close();
		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Reading all contacts
	public List<Routine> readAllRoutines() {
		// Get db writable
		SQLiteDatabase db = this.getWritableDatabase();
		// Define contacts list
		List<Routine> routines = new ArrayList<Routine>();
		Cursor cursor = db.query(TABLE_ROUTINE, columns, null, null, null,
				null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Routine routine = new Routine();
			routine.setID(Integer.parseInt(cursor.getString(0)));
			routine.setName(cursor.getString(1));
			routine.setMuscleGroup(cursor.getString(2));
			routine.setDescription(cursor.getString(3));
			routine.setzeVideo(cursor.getString(4));
			routine.setPhotograph(cursor.getString(5));
			routine.setSets(cursor.getInt(6));
			routine.setReps(cursor.getInt(7));
			routine.SetHowto(cursor.getString(8));
			routines.add(routine);
			cursor.moveToNext();
		}

		// Make sure to close the cursor
		cursor.close();
			
		
		return routines;
	}

	// Update contact contact
	public boolean editRoutine(Routine routine) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues vals = new ContentValues();
		vals.put(COLUMN_NAME, routine.getName());
		vals.put(COLUMN_MUSCLEGROUP, routine.getMuscleGroup());
		// updating row
		int i = db.update(TABLE_ROUTINE, vals, COLUMN_ID + " = ?",
				new String[] { String.valueOf(routine.getID()) });

		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Deleting contact
	public boolean deleteRoutine(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		int i = db.delete(TABLE_ROUTINE, COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });

		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

}
