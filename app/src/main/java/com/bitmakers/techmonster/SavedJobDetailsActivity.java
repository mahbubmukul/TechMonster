package com.bitmakers.techmonster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.database.DBActions;
import com.bitmakers.techmonster.model_class.JobDetailsList;
import com.bitmakers.techmonster.model_class.JobFavouriteList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class SavedJobDetailsActivity extends AppCompatActivity {

    ImageView coverImg;
    TextView jobTitle,jobCompany, jobLocation, jobSalary,jobExpire;
    FrameLayout fbButton, saveJob;
    LinearLayout keywordL,descTextL, reqTextL;

    DisplayImageOptions options;

    private boolean isFavourited = false;
    private DBActions dbActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                startActivity(new Intent(SavedJobDetailsActivity.this, ApplyJobActivity.class));
            }
        });

        initializeUI();

        updateData();


        jobCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SavedJobDetailsActivity.this, CompanydetailsActivity.class));
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
    }

    void updateData(){
        JobFavouriteList jobDet =AppData.newsFavListt.get(0);

        jobTitle.setText(jobDet.getName());
//        jobCompany.setText(jobDet.getCompany_info().getCom_name());
//        jobLocation.setText(jobDet.getCompany_info().getFormatted_address());
        jobSalary.setText(jobDet.getSalary());
        jobExpire.setText(jobDet.getExpire_time());

//        for (int j = 0; j < jobDet.getKeywords().length; j++) {
//
//            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.tag_lay, null);
//
//            TextView label_date = (TextView) tr_head.findViewById(R.id.tag_name);
//            label_date.setText(jobDet.getKeywords()[j]);
//
//            keywordL.addView(tr_head, new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT));
//
//        }

//        for (int j = 0; j < jobDet.getJob_skill().length; j++) {
//
//            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.desc_text, null);
//
//            TextView label_date = (TextView) tr_head.findViewById(R.id.desc_text);
//            label_date.setText(jobDet.getJob_skill()[j]);
//
//            descTextL.addView(tr_head);
//
//        }
//
//        for (int j = 0; j < jobDet.getJob_exp().length; j++) {
//
//            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.desc_text, null);
//
//            TextView label_date = (TextView) tr_head.findViewById(R.id.desc_text);
//            label_date.setText(jobDet.getJob_exp()[j]);
//
//            reqTextL.addView(tr_head);
//
//        }

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
}




