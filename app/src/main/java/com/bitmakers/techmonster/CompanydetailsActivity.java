package com.bitmakers.techmonster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitmakers.techmonster.app_data.AppData;
import com.bitmakers.techmonster.model_class.CompanyInfo;
import com.bitmakers.techmonster.model_class.JobDetailsList;
import com.bitmakers.techmonster.model_class.JobList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompanydetailsActivity extends AppCompatActivity {

    ImageView coverImg, comIcon;
    TextView comName,comLocation, comWeb,comEmpl, comCountry, comFacebook, companyOverview;
    FrameLayout fbButton;
    LinearLayout comBenL, whoL;

    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))
                .build();

        initializeUI();

        updateData();
    }

    void initializeUI(){


        coverImg = (ImageView)findViewById(R.id.cover_img) ;
        comIcon = (ImageView)findViewById(R.id.company_icon) ;

        comName = (TextView) findViewById(R.id.company_title);
        comLocation = (TextView) findViewById(R.id.company_location);
        comWeb = (TextView) findViewById(R.id.company_web);
        comEmpl = (TextView) findViewById(R.id.company_employee);
        comCountry = (TextView) findViewById(R.id.company_country);
        comFacebook = (TextView) findViewById(R.id.company_fb);
        companyOverview = (TextView) findViewById(R.id.com_desc_text);
        fbButton =(FrameLayout) findViewById(R.id.fb_btn);

        comBenL =(LinearLayout)findViewById(R.id.com_benifit_text_area);
        whoL =(LinearLayout)findViewById(R.id.com_who_rl);
    }

    void updateData(){
        CompanyInfo jobDet = AppData.jobDetailsLists.get(0).getCompany_info();

        comName.setText(jobDet.getCom_name());
        comLocation.setText(jobDet.getFormatted_address());
        comWeb.setText(jobDet.getCom_website());
        comEmpl.setText(jobDet.getCom_size());
        comCountry.setText(jobDet.getCom_country());
        comFacebook.setText(jobDet.getCom_facebook());
        companyOverview.setText(jobDet.getCom_desc());

        for (int j = 0; j < jobDet.getCom_benefit().length; j++) {

            RelativeLayout tr_head = (RelativeLayout) getLayoutInflater().inflate(R.layout.desc_text, null);

            TextView label_date = (TextView) tr_head.findViewById(R.id.desc_text);
            label_date.setText(jobDet.getCom_benefit()[j]);

            comBenL.addView(tr_head);

        }

        try {
            JSONArray contacts = new JSONArray(jobDet.getCom_who_we_are());

            System.out.println("FFFFFFFFFFFFFFFFFF _JSONNEWSL  rr.........  "+contacts.length());

            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                String name="",des="",summary="";

                try{name = c.getString("name");}catch (Exception e){}
                try{des = c.getString("position");}catch (Exception e){}
                try{summary = c.getString("content");}catch (Exception e){}

                LinearLayout tr_head2 = (LinearLayout) getLayoutInflater().inflate(R.layout.who_text, null);
                TextView nameww = (TextView) tr_head2.findViewById(R.id.employee_name);
                TextView desww = (TextView) tr_head2.findViewById(R.id.employee_desg);
                TextView sumww = (TextView) tr_head2.findViewById(R.id.employee_info);
                nameww.setText(name);
                desww.setText(des);
                sumww.setText(summary);
//
                whoL.addView(tr_head2);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        ImageLoader.getInstance().displayImage(jobDet.getCom_thumbnail(), coverImg, options, new ImageLoadingListener() {
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

        ImageLoader.getInstance().displayImage(jobDet.getCom_logo(), comIcon, options, new ImageLoadingListener() {
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





}
