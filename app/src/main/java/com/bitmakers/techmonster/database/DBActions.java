package com.bitmakers.techmonster.database;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Saif MCC on 1/13/2016.
 */
public class DBActions {
    private DBAdapter multimediaDb;
    private Context context;

    public DBActions(Context context) {
        this.context = context;
        openDB();
    }

    private void openDB() {
        multimediaDb = new DBAdapter(context);
        multimediaDb.open();
    }

    public void closeDB() {
        multimediaDb.close();
    }

    public long insertData(String job_id,String summary, String details, String expire, String job_dist,
                           String job_exp, String job_status, String city, String type, String name,
                           String country, String salary, String img, String skill) {
        return multimediaDb.insertRow(job_id,summary, details, expire, job_dist,
                job_exp, job_status, city, type, name,
                country, salary, img, skill);
    }

    public boolean deleteRow(String newsID) {
        return multimediaDb.deleteRow(newsID);
    }

    public Cursor getRows() {
        return multimediaDb.getAllRows();
    }

    public Cursor getRow(int rowId) {
        return multimediaDb.getRow(rowId);
    }



}
