package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bitmakers.techmonster.adapter_pkg.HomePageAdapter;
import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try
//                {
//                    Thread.sleep(2000);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//                finally {
//                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });
//
//        thread.start();

        getCityCountyWeb(false);


    }



    public void getCityCountyWeb(final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(SplashActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;
                String cityJson=null;
                String countryJson=null;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        cityJson = new JSONParser().thePostRequest(
                                AppUrl.cityUrl, "");
                        AppData.cityLists= new JSON().parseCity(cityJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(SplashActivity.this, "",
                                "Loading. Please Wait...");
                        progressDialog.setCancelable(false);
                    }
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (showProgress == true) {
                        progressDialog.dismiss();
                    }

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(SplashActivity.this);
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
                            ((Activity) SplashActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

}
