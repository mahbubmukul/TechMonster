package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitmakers.techmonster.adapter_pkg.FavouriteAdapter;
import com.bitmakers.techmonster.adapter_pkg.HomePageAdapter;
import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.database.DBActions;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;

public class SearchResultActivity extends AppCompatActivity {

    ListView lv;
    private HomePageAdapter fAdapter;
    SharedPreferences pref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("TechMonster_Login", 0);
        token = pref.getString("user_token", "");

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 getDetailsWeb(AppData.homeJobList.get(position).getId(), token, true);
            }
        });

        fAdapter = new HomePageAdapter(SearchResultActivity.this, AppData.searchJobList);
        lv.setAdapter(fAdapter);



    }


    public void getDetailsWeb(final String jobId, final String token, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(SearchResultActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        AppData.finalDeatilsResultFromServer = new JSONParser().thePostRequest(
                                AppUrl.dtailsJob+"&id_job="+jobId+"&token="+token, "");
                        AppData.jobDetailsLists= new JSON().parseDetailsNews(AppData.finalDeatilsResultFromServer );

                        System.out.println("WWWWWW >>>"+AppData.jobDetailsLists.get(0).getCompany_info().getCom_name());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(SearchResultActivity.this, "",
                                "Loading. Please Wait...");
                        progressDialog.setCancelable(false);
                    }
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (showProgress == true) {
                        progressDialog.dismiss();
                    }

                    // startActivity(new );
                    startActivity(new Intent(SearchResultActivity.this, JobDetailsActivity.class));

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(SearchResultActivity.this);
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
                            ((Activity) SearchResultActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

}
