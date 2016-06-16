package com.bitmakers.techmonster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.xml.sax.XMLReader;

public class JobDetailsActivity extends AppCompatActivity {

    String dd ="<p>We are looking for 10 PHP Developers (for both senior and junior position), who is expected to be able to perform all aspects of the development and release cycle, including front-end, back-end, database and system development. You must be able to work well independently or within a small team. </p><br> " +
            "Main duties:\n" +
            "                                \n<br> " +
            "                                     Understand and translate business requirements into advanced technical solutions. \n<br> " +
            "                                     Provide work estimation in points or hours - Participate in designing, developing and implementing project. \n<br> " +
            "                                     Implement web applications in using modern php web frameworks &amp; cms framework \n<br> " +
            "                                     Report on progress and time spent \n<br> " +
            "                                     Prepare technical documentation if required. \n<br> " +
            "                                \n<br> " +
            "                                <p>Tech is a young startup focusing on web development with emerging technologies, providing a lot of opportunities to learn new things. We aim for a friendly and flexible working environment, which could best assist developers to work effectively. Other than that, staff are offered with many benefits: </p>\n" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ((TextView)findViewById(R.id.desc_string)).setText(Html.fromHtml(dd));


        ((FrameLayout)findViewById(R.id.apply_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDetailsActivity.this, ApplyJobActivity.class));
            }
        });
    }





}




