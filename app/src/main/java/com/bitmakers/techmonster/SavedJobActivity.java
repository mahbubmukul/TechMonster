package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitmakers.techmonster.adapter_pkg.AppliedAdapter;
import com.bitmakers.techmonster.adapter_pkg.FavouriteAdapter;
import com.bitmakers.techmonster.adapter_pkg.HomePageAdapter;
import com.bitmakers.techmonster.adapter_pkg.SavedJobAdapter;
import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.database.DBActions;
import com.bitmakers.techmonster.database.DBAdapter;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;
import com.bitmakers.techmonster.model_class.JobDetailsList;
import com.bitmakers.techmonster.model_class.JobFavouriteList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedJobActivity extends AppCompatActivity {

    ListView lv;
    private DBActions dbAction;
    private FavouriteAdapter fAdapter;
    //private ArrayList<News> contentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbAction = new DBActions(getApplicationContext());

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // getDetailsWeb(AppData.homeJobList.get(position).getId(), token, true);
            }
        });
        getLoadDataFromWeb();

    }


    public void getLoadDataFromWeb() {

        if (XInternetServices.isNetworkAvailable(SavedJobActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;
                //&page=0&token=5762323e6a49d00bd73de8bd"
                @Override
                protected Void doInBackground(Void... params) {
                    Cursor c = dbAction.getRows();

                    if (c.moveToFirst()) {
                        do {
                            // Process the data:
                            int row_id = c.getInt(DBAdapter.COL_ROWID);

                            String job_id = c.getString(c.getColumnIndex(DBAdapter.KEY_ID));
                            String summary = c.getString(DBAdapter.COL_SUMMARY);
                            String details = c.getString(DBAdapter.COL_DETAILS);
                            String expire = c.getString(DBAdapter.COL_EXPIRE);
                            String job_dist = c.getString(DBAdapter.COL_JOB_DIST);
                            String job_exp = c.getString(DBAdapter.COL_JOB_EXP);
                            String job_status  = c.getString(DBAdapter.COL_JOB_STATUS);
                            String city = c.getString(DBAdapter.COL_CITY);
                            String type = c.getString(DBAdapter.COL_TYPE);
                            String name = c.getString(DBAdapter.COL_NAME);
                            String country = c.getString(DBAdapter.COL_JOB_COUNTRY);
                            String salary = c.getString(DBAdapter.COL_SALARY);
                            String img = c.getString(DBAdapter.COL_IMG);
                            String skill = c.getString(DBAdapter.COL_SKILL);

                            AppData.newsFavListt.add(new JobFavouriteList(row_id, job_id,summary, details, expire, job_dist,
                                    job_exp, job_status, city, type, name,
                                    country, salary, img, skill));

                        } while (c.moveToNext());
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    AppData.newsFavListt.clear();
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (AppData.newsFavListt.size() == 0) {
//                        emptyListTextView.setVisibility(View.VISIBLE);
                    } else {
//                        emptyListTextView.setVisibility(View.GONE);
                        fAdapter = new FavouriteAdapter(SavedJobActivity.this, AppData.newsFavListt);
                        lv.setAdapter(fAdapter);
                    }

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(SavedJobActivity.this);
            builder1.setMessage("Please check your internet connection");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((Activity) SavedJobActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

}
