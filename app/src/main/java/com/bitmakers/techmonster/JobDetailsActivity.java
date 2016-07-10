package com.bitmakers.techmonster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.app_data.AppUrl;
import com.bitmakers.techmonster.app_data.XInternetServices;
import com.bitmakers.techmonster.database.DBActions;
import com.bitmakers.techmonster.jsonparser.JSON;
import com.bitmakers.techmonster.jsonparser.JSONParser;
import com.bitmakers.techmonster.model_class.JobDetailsList;
import com.google.android.flexbox.FlexboxLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.xml.sax.XMLReader;

public class JobDetailsActivity extends AppCompatActivity {

    ImageView coverImg;
    TextView jobTitle,jobCompany, jobLocation, jobSalary,jobExpire;
    FrameLayout fbButton, saveJob;
    LinearLayout keywordL,descTextL, reqTextL;

    FlexboxLayout fbl;

    DisplayImageOptions options;

    private boolean isFavourited = false;
    private DBActions dbActions;

    String result;
    SharedPreferences pref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("TechMonster_Login", 0);
        token = pref.getString("user_token", "");


        dbActions = new DBActions(getApplicationContext());

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))
                .build();

        ((FrameLayout)findViewById(R.id.apply_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDetailsActivity.this, ApplyJobActivity.class));
            }
        });

        initializeUI();

        updateData();


        jobCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDetailsActivity.this, CompanydetailsActivity.class));
            }
        });

        saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteNews();
            }
        });
    }


    void initializeUI(){
        coverImg = (ImageView)findViewById(R.id.cover_img) ;

        jobTitle = (TextView) findViewById(R.id.job_title);
        jobCompany = (TextView) findViewById(R.id.job_company);
        jobLocation = (TextView) findViewById(R.id.job_location);
        jobSalary = (TextView) findViewById(R.id.job_sign_in);
        jobExpire = (TextView) findViewById(R.id.job_expie);

        fbButton =(FrameLayout) findViewById(R.id.fb_btn);
        saveJob =(FrameLayout) findViewById(R.id.save_job_btn);

        keywordL =(LinearLayout)findViewById(R.id.tags);
        descTextL =(LinearLayout)findViewById(R.id.desc_text_area);
        reqTextL =(LinearLayout)findViewById(R.id.req_text_area);

        fbl = (FlexboxLayout) findViewById(R.id.flexbox_layout);
    }

    void updateData(){
        final JobDetailsList jobDet =AppData.jobDetailsLists.get(0);

        jobTitle.setText(jobDet.getName());
        jobCompany.setText(jobDet.getCompany_info().getCom_name());
        jobLocation.setText(jobDet.getCompany_info().getFormatted_address());
        jobSalary.setText(jobDet.getSalary());
        jobExpire.setText(jobDet.getExpire_time());

        for (int j = 0; j < jobDet.getKeywords().length; j++) {
            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.tag_lay, null);

            TextView label_date = (TextView) tr_head.findViewById(R.id.tag_name);
            label_date.setText(jobDet.getKeywords()[j].toUpperCase());

            final String sss =jobDet.getKeywords()[j];
            fbl.addView(tr_head);

            tr_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(JobDetailsActivity.this, sss,Toast.LENGTH_LONG ).show();
                    getSearchWeb("&token="+token+"&keywords="+sss,true);

                }
            });
        }

        for (int j = 0; j < jobDet.getJob_skill().length; j++) {

            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.desc_text, null);

            TextView label_date = (TextView) tr_head.findViewById(R.id.desc_text);
            label_date.setText(jobDet.getJob_skill()[j]);

            descTextL.addView(tr_head);



        }

        for (int j = 0; j < jobDet.getJob_exp().length; j++) {

            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.desc_text, null);

            TextView label_date = (TextView) tr_head.findViewById(R.id.desc_text);
            label_date.setText(jobDet.getJob_exp()[j]);

            reqTextL.addView(tr_head);

        }

        ImageLoader.getInstance().displayImage(jobDet.getJob_cover_image(), coverImg, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                int width = loadedImage.getWidth();
                int height = loadedImage.getHeight();
                System.out.println("TestDDDD   " + width + "  " + height);
                if (width > height)
                    coverImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }


    public void favouriteNews() {


        String job_id = AppData.jobDetailsLists.get(0).getId();
        String summary = AppData.jobDetailsLists.get(0).getSummary();
        String details = AppData.jobDetailsLists.get(0).getDetail();
        String expire = AppData.jobDetailsLists.get(0).getExpire_time();
        String job_dist = AppData.jobDetailsLists.get(0).getJob_dist();
        String job_exp ="";
        for(int i=0;i<AppData.jobDetailsLists.get(0).getJob_exp().length;i++)
        {
            if(i!=0)
                job_exp = job_exp+"_______"+AppData.jobDetailsLists.get(0).getJob_exp()[i];
            else
                job_exp = job_exp+""+AppData.jobDetailsLists.get(0).getJob_exp()[i];
        }

        String job_status  = AppData.jobDetailsLists.get(0).getApplicant_job_status();
        String city = AppData.jobDetailsLists.get(0).getJob_city();
        String type = AppData.jobDetailsLists.get(0).getJob_type();
        String name = AppData.jobDetailsLists.get(0).getName();
        String country = AppData.jobDetailsLists.get(0).getJob_country();
        String salary = AppData.jobDetailsLists.get(0).getSalary();
        String img = AppData.jobDetailsLists.get(0).getJob_cover_image();
        String skill ="";
        for(int i=0;i<AppData.jobDetailsLists.get(0).getJob_skill().length;i++)
        {
            if(i!=0)
                skill = skill+"_______"+AppData.jobDetailsLists.get(0).getJob_skill()[i];
            else
                skill = skill+""+AppData.jobDetailsLists.get(0).getJob_skill()[i];
        }

        toggleFavorite(job_id,summary, details, expire, job_dist,
                job_exp, job_status, city, type, name,
                country, salary, img, skill);

    }

    private void toggleFavorite(String job_id,String summary, String details, String expire, String job_dist,
                                String job_exp, String job_status, String city, String type, String name,
                                String country, String salary, String img, String skill) {
        if (!isFavourited) {
            try {
                long d = dbActions.insertData(job_id,summary, details, expire, job_dist,
                        job_exp, job_status, city, type, name,
                        country, salary, img, skill);
            } catch (Exception e) {
                e.printStackTrace();
            }

            isFavourited = true;
        }
    }



    public void getSearchWeb(final String data, final boolean showProgress) {

        if (XInternetServices.isNetworkAvailable(JobDetailsActivity.this)) {
            class  LoadHomeData extends AsyncTask<Void,Void,Void> {
                ProgressDialog progressDialog;

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        result = new JSONParser().thePostRequest(
                                AppUrl.searchJob+data, "");
                        System.out.println("WWWWWW >>>"+AppData.searchJobList.size());
                        AppData.searchJobList= new JSON().parseHomeNews(result );

                        System.out.println("WWWWWW >>>"+AppData.searchJobList.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    if (showProgress == true) {
                        progressDialog = ProgressDialog.show(JobDetailsActivity.this, "",
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
                    startActivity(new Intent(JobDetailsActivity.this, SearchResultActivity.class));

                    super.onPostExecute(aVoid);
                }
            }
            new LoadHomeData().execute();
        } else {
            android.support.v7.app.AlertDialog.Builder builder1;
            builder1 = new android.support.v7.app.AlertDialog.Builder(JobDetailsActivity.this);
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
                            ((Activity) JobDetailsActivity.this).finish();
                        }
                    });
            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }
}




