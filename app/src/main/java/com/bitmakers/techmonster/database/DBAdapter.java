// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.bitmakers.techmonster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bitmakers.techmonster.model_class.CompanyInfo;


/**
 * @author Saif MCC on 1/13/2016.
 */

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    private String[] job_skill;
    private String repost_num;

    public static final String KEY_ID = "job_id";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_EXPIRE = "expire";
    public static final String KEY_JOB_DIST = "job_dist";
    public static final String KEY_JOB_EXP = "job_exp";
    public static final String KEY_JOB_STATUS = "job_status";
    public static final String KEY_CITY = "city";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_JOB_COUNTRY = "country";
    public static final String KEY_SALARY = "salary";
    public static final String KEY_IMG = "img";
    public static final String KEY_SKILL = "skill";


    public static final int COL_ID = 1;
    public static final int COL_SUMMARY = 2;
    public static final int COL_DETAILS = 3;
    public static final int COL_EXPIRE = 4;
    public static final int COL_JOB_DIST = 5;
    public static final int COL_JOB_EXP = 6;
    public static final int COL_JOB_STATUS = 7;
    public static final int COL_CITY = 8;
    public static final int COL_TYPE = 9;
    public static final int COL_NAME = 10;
    public static final int COL_JOB_COUNTRY = 11;
    public static final int COL_SALARY = 12;
    public static final int COL_IMG = 13;
    public static final int COL_SKILL = 13;


    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_ID ,KEY_SUMMARY, KEY_DETAILS, KEY_EXPIRE,
            KEY_JOB_DIST, KEY_JOB_EXP,KEY_JOB_STATUS,KEY_CITY, KEY_TYPE, KEY_NAME
            , KEY_JOB_COUNTRY, KEY_SALARY, KEY_IMG, KEY_SKILL};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "JobsMonsterDB";
    //public static final String DATABASE_TABLE = "multimedia";
    public static final String DATABASE_TABLE = "favourite";


    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_ID + " text not null, "
                    + KEY_SUMMARY + " text not null, "
                    + KEY_DETAILS + " text not null, "
                    + KEY_EXPIRE + " text not null, "
                    + KEY_JOB_DIST + " text not null, "
                    + KEY_JOB_EXP + " text not null, "
                    + KEY_JOB_STATUS + " text not null, "
                    + KEY_CITY + " text not null, "
                    + KEY_TYPE + " text not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_JOB_COUNTRY + " text not null, "
                    + KEY_SALARY + " text not null, "
                    + KEY_IMG + " text not null, "
                    + KEY_SKILL + " text not null"
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String job_id,String summary, String details, String expire, String job_dist,
                          String job_exp, String job_status, String city, String type, String name,
                          String country, String salary, String img, String skill) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, job_id);
        initialValues.put(KEY_SUMMARY, summary);
        initialValues.put(KEY_DETAILS, details);
        initialValues.put(KEY_EXPIRE, expire);
        initialValues.put(KEY_JOB_DIST, job_dist);
        initialValues.put(KEY_JOB_EXP, job_exp);
        initialValues.put(KEY_JOB_STATUS, job_status);
        initialValues.put(KEY_CITY, city);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_JOB_COUNTRY, country);
        initialValues.put(KEY_SALARY, salary);
        initialValues.put(KEY_IMG, img);
        initialValues.put(KEY_SKILL, skill);


        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    // Delete a row from the database, by title
    public boolean deleteRow(String newsID) {
        String where = KEY_ID + "=" + newsID;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        String mSortOrder = KEY_ROWID + " DESC";
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, mSortOrder, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)o
    public Cursor getRow(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean isNewsExists(String newsId) {
        String where = KEY_ID + "=" + newsId;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        if(c != null && c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:

        ContentValues newValues = new ContentValues();
		/*newValues.put(KEY_CONTENT_ID, name);
		newValues.put(KEY_CONTENT_TITLE, studentNum);
		newValues.put(KEY_DESCROPTION, favColour);*/

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
