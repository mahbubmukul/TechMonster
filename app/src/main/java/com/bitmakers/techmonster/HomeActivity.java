package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bitmakers.techmonster.adapter_pkg.HomePageAdapter;
import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;
import com.bitmakers.techmonster.jsonparser.WebApiBoardResult;
import com.bitmakers.techmonster.model_class.JobList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Menu menu;

    public static Boolean signIn = false;

    ListView lv;// = (ListView) findViewById(R.id.listView);
    HomePageAdapter ad;// = new HomePageAdapter(this);
    private ArrayList<JobList> data;
    private String webServiceResult = null;

    public static DisplayImageOptions options;
    SharedPreferences pref;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))
                .build();

        pref = getApplicationContext().getSharedPreferences("TechMonster_Login", 0);


        signIn = pref.getBoolean("Loged_in", false);
        String name = pref.getString("user_name", null);
        String imag = pref.getString("user_avatar", null);
       token = pref.getString("user_token", null);

        System.out.println("WWWWWWWWW  HHHHHH  "+signIn.toString()+" "+name+"  "+imag);

        if(signIn){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View hView =  navigationView.inflateHeaderView(R.layout.nav_header_home);
            ImageView imgvw = (ImageView)hView.findViewById(R.id.menu_img);
            TextView tv = (TextView)hView.findViewById(R.id.menu_name);
            tv.setText(name.toUpperCase());
            ImageLoader.getInstance().displayImage(imag, imgvw, options);
        }


        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getDetailsWeb(AppData.homeJobList.get(position).getId(), token, true);
            }
        });


        getLoadDataFromWeb(AppUrl.homJobUrl,"0",token,true);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if(signIn)
            getMenuInflater().inflate(R.menu.home, menu);
        else
            getMenuInflater().inflate(R.menu.home_2, menu);
        this.menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat) {
            startActivity(new Intent(HomeActivity.this, ChatListActivity.class));
            return true;
        }

        if (id == R.id.action_noti) {
            startActivity(new Intent(HomeActivity.this, NotificationListActivity.class));
            return true;
        }

        if (id == R.id.action_sign_in) {
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            return true;
        }

        if (id == R.id.action_sign_up) {
            startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_prof) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_change_pass) {
            startActivity(new Intent(HomeActivity.this, ChangePasswordActivity.class));

        } else if (id == R.id.nav_saved_job) {
            startActivity(new Intent(HomeActivity.this, SavedJobActivity.class));
        } else if (id == R.id.nav_applied) {
            startActivity(new Intent(HomeActivity.this, AppliedJobActivity.class));

        } else if (id == R.id.nav_sign_out) {
            this.signIn=false;
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
//            this.getMenuInflater().inflate(R.menu.home_2,null);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getLoadDataFromWeb(final String theUrl, final String pageNo, final String token, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(HomeActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void>{
                ProgressDialog progressDialog;
                //&page=0&token=5762323e6a49d00bd73de8bd"
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        AppData.finalResultFromServer = new JSONParser().thePostRequest(
                                theUrl+"&page="+pageNo+"&token="+token, "");
                        AppData.homeJobList= new JSON().parseHomeNews(AppData.finalResultFromServer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(HomeActivity.this, "",
                                "Loading. Please Wait...");
                        progressDialog.setCancelable(false);
                    }
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if (showProgress == true) {
                        progressDialog.dismiss();
                    }

                    ad = new HomePageAdapter(HomeActivity.this, AppData.homeJobList);
                    lv.setAdapter(ad);

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(HomeActivity.this);
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
                            ((Activity) HomeActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

    public void getDetailsWeb(final String jobId, final String token, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(HomeActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void>{
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
                        progressDialog = ProgressDialog.show(HomeActivity.this, "",
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
                    startActivity(new Intent(HomeActivity.this, JobDetailsActivity.class));

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(HomeActivity.this);
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
                            ((Activity) HomeActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }





}
